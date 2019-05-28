package cn.appsys.controller.developer;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONArray;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.appCategory.developer.AppCategoryService;
import cn.appsys.service.appInfo.developer.AppInfoService;
import cn.appsys.service.appVersion.developer.AppVersionService;
import cn.appsys.service.dataDictionary.developer.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

/**
 * AppInfo控制器: 用于AppInfo的分页显示和对AppInfo进行增删改查操作
 * 
 * @author zll
 *
 */
@RequestMapping("/dev/flatform/app")
@Controller
public class AppInfoController {
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appCategoryService;
	@Resource
	private AppVersionService appVersionService;

	private Logger logger = Logger.getLogger(AppInfoController.class);// 日志记录器

	
	/**
	 * 上架/下架操作
	 * @param appId
	 * @return
	 */
	@RequestMapping(value="/{appId}/sale.json",method=RequestMethod.PUT)
	@ResponseBody
	public Object switchSale(@PathVariable Integer appId){
		logger.info("appId--->>>"+appId);
		Map<String, Object> map=new HashMap<String, Object>();
		if(appId==null){
			map.put("errorCode", "param000001");
		}else{
			try {
				map.put("errorCode", "0");
				AppInfo appInfo=appInfoService.queryAppInfoById(appId);
				if(appInfo.getStatus()==2 || appInfo.getStatus()==4){
					appInfo.setStatus(5);
				}else if(appInfo.getStatus()==2 || appInfo.getStatus()==5){
					appInfo.setStatus(4);
				}else{
					;
				}
				boolean result=appInfoService.modifyAppInfoById(appInfo);
				if(result){
					map.put("resultMsg", "success");
				}else{
					map.put("resultMsg", "failed");
				}
			} catch (Exception e) {
				map.replace("errorCode", "0", "exception000001");
				e.getMessage();
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 根据id删除的AppInfo
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delapp.json", method = RequestMethod.GET)
	@ResponseBody
	public Object delApp(@RequestParam("id") Integer id) {
		logger.info("id--->>>" + id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == null) {
			map.put("delResult", "notexist");
		} else {
			boolean result = appInfoService.removeAppInfoById(id);
			if (result) {
				map.put("delResult", "true");
			} else {
				map.put("delResult", "false");
			}
		}
		return map;
	}
	
	/**
	 * 保存修改最新版本信息
	 * @param request
	 * @param appVersion
	 * @param multipartFile
	 * @return
	 */
	@RequestMapping(value = "/appversionmodifysave", method = RequestMethod.POST)
	public String appVersionModifySave(AppVersion appVersion,HttpServletRequest request, 
									   @RequestParam(value = "attach", required = false) MultipartFile multipartFile) {
		logger.info("versionNo--->>>" + appVersion.getVersionNo());
		String apkFileName = null;
		String apkLocPath = null;
		String downloadLink = null;
		String path = null;// 上下文
		String oldFileName = null;
		String newFileName = null;
		String suffix = null;
		int filesize = 500000000;
		boolean flag = true;// 默认是Multipart请求,支持文件上传
		AppInfo appInfo = appInfoService.queryAppInfoById(appVersion.getAppId());
		if (!multipartFile.isEmpty()) {
			path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			logger.info("path--->>>" + path);
			oldFileName = multipartFile.getOriginalFilename();
			logger.info("oldFileName--->>>" + oldFileName);
			suffix = FilenameUtils.getExtension(oldFileName);
			logger.info("suffix--->>>" + suffix);
			if (multipartFile.getSize() > filesize) {
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
				flag = false;
			} else if (("apk").equals(suffix)) {
				newFileName = appInfo.getAPKName() + "-" + appVersion.getVersionNo() + "." + suffix;
				File targetFile = new File(path, newFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					multipartFile.transferTo(targetFile);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
					flag = false;
				}
				downloadLink = request.getContextPath() + File.separator + "statics" + File.separator + "uploadfiles"
						+ File.separator + newFileName;
				logger.info("logoPicPath--->>>" + downloadLink);
				apkLocPath = path + newFileName;
				logger.info("logoLocPath--->>>" + apkLocPath);
				apkFileName = newFileName;
				logger.info("apkFileName--->>>" + apkFileName);
			} else {
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
				flag = false;
			}
		}
		if (flag) {
			appVersion.setModifyBy(((DevUser) request.getSession().getAttribute(Constants.DevUSER_SESSION)).getId());
			appVersion.setModifyDate(new Date());
			appVersion.setApkLocPath(apkLocPath);
			appVersion.setDownloadLink(downloadLink);
			appVersion.setApkFileName(apkFileName);
			boolean result = appVersionService.modifyLastestAppVersion(appVersion);
			if (result) {
				return "redirect:/dev/flatform/app/list";
			}
		}
		return "developer/ppversionmodify";
	}

	/**
	 * 跳转到修改最新版本信息页面
	 * 
	 * @param vid
	 * @param aid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/appversionmodify", method = RequestMethod.GET)
	public String appVersionModify(@RequestParam(value = "vid", required = false) Integer vid,
								   @RequestParam(value = "aid", required = false) Integer aid,
								   Model model) {
		AppVersion appVersion = appVersionService.queryAppVersionById(vid);
		List<AppVersion> appVersionList = appVersionService.queryAppVersionInfoByAppId(aid);
		model.addAttribute("appVersion", appVersion);
		model.addAttribute("appVersionList", appVersionList);
		return "developer/appversionmodify";
	}

	/**
	 * 保存新增版本信息
	 * 
	 * @param request
	 * @param appVersion
	 * @param multipartFile
	 * @return
	 */
	@RequestMapping(value = "/addversionsave", method = RequestMethod.POST)
	public String appVersionAddSave(HttpServletRequest request, AppVersion appVersion,
			@RequestParam(value = "a_downloadLink", required = false) MultipartFile multipartFile) {
		logger.info("versionNo--->>>" + appVersion.getVersionNo());
		String apkFileName = null;
		String apkLocPath = null;
		String downloadLink = null;
		String path = null;// 上下文
		String oldFileName = null;
		String newFileName = null;
		String suffix = null;
		int filesize = 500000000;
		boolean flag = true;// 默认是Multipart请求,支持文件上传
		AppInfo appInfo = appInfoService.queryAppInfoById(appVersion.getAppId());
		if (!multipartFile.isEmpty()) {
			path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			logger.info("path--->>>" + path);
			oldFileName = multipartFile.getOriginalFilename();
			logger.info("oldFileName--->>>" + oldFileName);
			suffix = FilenameUtils.getExtension(oldFileName);
			logger.info("suffix--->>>" + suffix);
			if (multipartFile.getSize() > filesize) {
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
				flag = false;
			} else if (("apk").equals(suffix)) {
				newFileName = appInfo.getAPKName() + "-" + appVersion.getVersionNo() + "." + suffix;
				File targetFile = new File(path, newFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					multipartFile.transferTo(targetFile);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
					flag = false;
				}
				downloadLink = request.getContextPath() + File.separator + "statics" + File.separator + "uploadfiles"
						+ File.separator + newFileName;
				logger.info("logoPicPath--->>>" + downloadLink);
				apkLocPath = path + newFileName;
				logger.info("logoLocPath--->>>" + apkLocPath);
				apkFileName = newFileName;
				logger.info("apkFileName--->>>" + apkFileName);
			} else {
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
				flag = false;
			}
		}
		if (flag) {
			appVersion.setCreatedBy(((DevUser) request.getSession().getAttribute(Constants.DevUSER_SESSION)).getId());
			appVersion.setCreationDate(new Date());
			appVersion.setApkLocPath(apkLocPath);
			appVersion.setDownloadLink(downloadLink);
			appVersion.setApkFileName(apkFileName);
			boolean result = appVersionService.addAppVersionUnderAppId(appVersion);
			if (result) {
				return "redirect:/dev/flatform/app/list";
			}
		}
		return "developer/appversionadd";
	}

	/**
	 * 跳转到新增App版本信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "appversionadd", method = RequestMethod.GET)
	public String appVersionAdd(@RequestParam("id") Integer id, Model model) {
		List<AppVersion> appVersionList = appVersionService.queryAppVersionInfoByAppId(id);
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("id", id);
		return "developer/appversionadd";
	}
	
	/**
	 * 根据id查看AppInfo
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/appview/{appinfoid}", method = RequestMethod.GET)
	public String viewApp(@PathVariable Integer appinfoid, Model model) {
		AppInfo appInfo = appInfoService.queryAppInfoById(appinfoid);
		List<AppVersion> appVersionList = appVersionService.queryAppVersionInfoByAppId(appinfoid);
		model.addAttribute("appInfo", appInfo);
		model.addAttribute("appVersionList", appVersionList);
		return "developer/appinfoview";
	}

	/**
	 * 保存修改的App基础信息
	 * 
	 * @param appInfo
	 * @return
	 */
	@RequestMapping(value = "/appinfomodifysave", method = RequestMethod.POST)
	public String modifyAppInfoSave(AppInfo appInfo, HttpServletRequest request,
			@RequestParam(value = "attach", required = false) MultipartFile multipartFile) {
		String logoPicPath = null;
		String logoLocPath = null;
		String path = null;// 上下文
		String oldFileName = null;
		String newFileName = null;
		String suffix = null;
		int filesize = 500000;
		boolean flag = true;// 默认是Multipart请求,支持文件上传
		if (!multipartFile.isEmpty()) {
			path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			logger.info("path--->>>" + path);
			oldFileName = multipartFile.getOriginalFilename();
			logger.info("oldFileName--->>>" + oldFileName);
			suffix = FilenameUtils.getExtension(oldFileName);
			logger.info("suffix--->>>" + suffix);
			if (multipartFile.getSize() > filesize) {
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
				flag = false;
			} else if (("jpg").equals(suffix) || ("jpeg").equals(suffix) || ("png").equals(suffix)
					|| ("pneg").equals(suffix)) {
				newFileName = appInfo.getAPKName() + "." + suffix;
				File targetFile = new File(path, newFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					multipartFile.transferTo(targetFile);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
					flag = false;
				}
				logoPicPath = request.getContextPath() + File.separator + "statics" + File.separator + "uploadfiles"
						+ File.separator + newFileName;
				logger.info("logoPicPath--->>>" + logoPicPath);
				logoLocPath = path + newFileName;
				logger.info("logoLocPath--->>>" + logoLocPath);
			} else {
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
				flag = false;
			}
		}
		if (flag) {
			appInfo.setModifyBy(((DevUser) request.getSession().getAttribute(Constants.DevUSER_SESSION)).getId());
			appInfo.setModifyDate(new Date());
			appInfo.setUpdateDate(new Date());
			appInfo.setLogoLocPath(logoLocPath);
			appInfo.setLogoPicPath(logoPicPath);
			logger.info("updateDate--->>>"+appInfo.getUpdateDate());
			boolean result = appInfoService.modifyAppInfoById(appInfo);
			if (result == true) {
				appInfo=appInfoService.queryAppInfoById(appInfo.getId());
				logger.info("updateDate--->>>"+appInfo.getUpdateDate());
				return "redirect:/dev/flatform/app/list";
			}
		}
		return "developer/appinfomodify";
	}

	/**
	 * 删除修改页面中已上传的文件
	 * 包括AppInfo修改页面和APPVersion修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delfile.json", method = RequestMethod.GET)
	@ResponseBody
	public Object delFile(@RequestParam(value = "id", required = false) Integer id,
						  @RequestParam(value = "flag", required = false) String flag) {
		logger.info("id--->>>" + id);
		logger.info("flag--->>>" + flag);
		Map<String, Object> map = new HashMap<String, Object>();
		if(("logo").equals(flag)){
			boolean result = appInfoService.delFile(id);
			AppInfo appInfo = appInfoService.queryAppInfoById(id);
			logger.info("result" + result + "\n--->>>" + appInfo.getLogoPicPath()+ "\n--->>>" +appInfo.getLogoLocPath());
			if (result == true) {
				map.put("result", "success");
			} else {
				map.put("result", "failed");
			}
		}else if(("apk").equals(flag)){
			boolean result = appVersionService.delFile(id);
			AppVersion appVersion = appVersionService.queryAppVersionById(id);
			logger.info("result" + result + "\n--->>>" + appVersion.getApkLocPath()
				+"\n--->>>" +appVersion.getDownloadLink()+"\n--->>>" +appVersion.getApkFileName());
			if (result == true) {
				map.put("result", "success");
			} else {
				map.put("result", "failed");
			}
		}else{
			;
		}
		return map;
	}

	/**
	 * 进入修改App基础信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/appinfomodify", method = RequestMethod.GET)
	public String modifyAppInfo(@RequestParam(value = "id", required = false) Integer id, Model model) {
		AppInfo appInfo = appInfoService.queryAppInfoById(id);
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfomodify";
	}

	/**
	 * 支持文件上传的AppInfo新增
	 * 
	 * @param request
	 * @param multipartFile
	 * @return
	 */
	@RequestMapping(value = "/appinfoaddsave", method = RequestMethod.POST)
	public String addAppInfoSave(AppInfo appInfo, HttpServletRequest request,
			@RequestParam(value = "a_logoPicPath", required = false) MultipartFile multipartFile) {
		String logoPicPath = null;
		String logoLocPath = null;
		String path = null;// 上下文
		String oldFileName = null;
		String newFileName = null;
		String suffix = null;
		int filesize = 500000;
		boolean flag = true;// 默认是Multipart请求,支持文件上传
		if (!multipartFile.isEmpty()) {
			path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			logger.info("path--->>>" + path);
			oldFileName = multipartFile.getOriginalFilename();
			logger.info("oldFileName--->>>" + oldFileName);
			suffix = FilenameUtils.getExtension(oldFileName);
			logger.info("suffix--->>>" + suffix);
			if (multipartFile.getSize() > filesize) {
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
				flag = false;
			} else if (("jpg").equals(suffix) || ("jpeg").equals(suffix) || ("png").equals(suffix)
					|| ("pneg").equals(suffix)) {
				newFileName = appInfo.getAPKName() + "." + suffix;
				File targetFile = new File(path, newFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					multipartFile.transferTo(targetFile);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
					flag = false;
				}
				logoPicPath = request.getContextPath() + File.separator + "statics" + File.separator + "uploadfiles"
						+ File.separator + newFileName;
				logger.info("logoPicPath--->>>" + logoPicPath);
				logoLocPath = path + newFileName;
				logger.info("logoLocPath--->>>" + logoLocPath);
			} else {
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
				flag = false;
			}
		}
		if (flag) {
			appInfo.setCreatedBy(((DevUser) request.getSession().getAttribute(Constants.DevUSER_SESSION)).getId());
			appInfo.setDevId(((DevUser) request.getSession().getAttribute(Constants.DevUSER_SESSION)).getId());
			appInfo.setCreationDate(new Date());
			appInfo.setLogoLocPath(logoLocPath);
			appInfo.setLogoPicPath(logoPicPath);
			boolean result = appInfoService.addAppInfo(appInfo);
			if (result == true) {
				return "redirect:/dev/flatform/app/list";
			}
		}
		return "developer/appinfoadd";
	}

	/**
	 * 验证APKName是否已经存在,是否非空
	 * 
	 * @param APKName
	 * @return
	 */
	@RequestMapping(value = "/apkexist.json", method = RequestMethod.GET)
	@ResponseBody
	public Object apkExists(@RequestParam(value = "APKName", required = false) String APKName) {
		logger.info("APKName--->>>" + APKName);
		Map<String, Object> map = new HashMap<String, Object>();
		if (APKName == null || ("").equals(APKName)) {
			map.put("APKName", "empty");
		} else {
			boolean result = appInfoService.APKNameExists(APKName);
			if (result == true) {
				map.put("APKName", "exist");
			} else {
				map.put("APKName", "noexist");
			}
		}
		return map;
	}

	/**
	 * 获取所有的平台列表
	 * 
	 * @param tcode
	 * @return
	 */
	@RequestMapping(value = "/datadictionarylist.json", method = RequestMethod.GET)
	@ResponseBody
	public Object getDatadictionarylist(@RequestParam(value = "tcode", required = false) String tcode) {
		return JSONArray.toJSONString(getDataDictionaryList(tcode));
	}

	/**
	 * 进入新增APP基础信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appinfoadd", method = RequestMethod.GET)
	public String addAppInfo() {
		return "developer/appinfoadd";
	}

	/**
	 * 根据上一级分类列表中的信息获取下一级中的信息
	 * 
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/categorylevellist.json", method = RequestMethod.GET)
	@ResponseBody
	public Object getCategorylevellist(@RequestParam(value = "pid", required = false) Integer pid) {
		List<AppCategory> appCategories = appCategoryService.queryAppCategoryListByParentId(pid);
		return JSONArray.toJSONString(appCategories);
	}

	/**
	 * 分页显示App信息列表
	 * 
	 * @param session
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryFlatformId
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/list")
	public String getAppInfoListByPage(HttpSession session, Model model,
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "queryStatus", required = false) String queryStatus,
			@RequestParam(value = "queryFlatformId", required = false) String queryFlatformId,
			@RequestParam(value = "queryCategoryLevel1", required = false) String queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String queryCategoryLevel3,
			@RequestParam(value = "pageIndex", required = false) Integer pageIndex) {
		logger.info("+++++++++++querySoftwareName+++++++++++" + querySoftwareName);
		logger.info("+++++++++++queryStatus+++++++++++" + queryStatus);
		logger.info("+++++++++++queryFlatformId+++++++++++" + queryFlatformId);
		logger.info("+++++++++++queryCategoryLevel1+++++++++++" + queryCategoryLevel1);
		logger.info("+++++++++++queryCategoryLevel2+++++++++++" + queryCategoryLevel2);
		logger.info("+++++++++++queryCategoryLevel3+++++++++++" + queryCategoryLevel3);
		logger.info("+++++++++++pageIndex+++++++++++" + pageIndex);
		// 变量定义,用于后台查询中作为参数
		Integer _queryStatus = 0;
		Integer _queryFlatformId = 0;
		Integer _queryCategoryLevel1 = 0;
		Integer _queryCategoryLevel2 = 0;
		Integer _queryCategoryLevel3 = 0;
		Integer currentPageNo = 1;
		Integer totalCount = 0;
		Integer totalPageCount = 0;
		Integer pageSize = Constants.pageSize;// 页面容量
		Integer devId = ((DevUser) session.getAttribute(Constants.DevUSER_SESSION)).getId();
		// 判断查询参数是否为空
		if (querySoftwareName == null) {
			querySoftwareName = "";
		}
		if (queryStatus != null && !"".equals(queryStatus)) {
			_queryStatus = Integer.parseInt(queryStatus);
		}
		if (queryFlatformId != null && !"".equals(queryFlatformId)) {
			_queryFlatformId = Integer.parseInt(queryFlatformId);
		}
		if (queryCategoryLevel1 != null && !"".equals(queryCategoryLevel1)) {
			_queryCategoryLevel1 = Integer.parseInt(queryCategoryLevel1);
		}
		if (queryCategoryLevel2 != null && !"".equals(queryCategoryLevel2)) {
			_queryCategoryLevel2 = Integer.parseInt(queryCategoryLevel2);
		}
		if (queryCategoryLevel3 != null && !"".equals(queryCategoryLevel3)) {
			_queryCategoryLevel3 = Integer.parseInt(queryCategoryLevel3);
		}
		if (pageIndex != null) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 创建分页支持类的实例对象
		PageSupport pageSupport = new PageSupport();
		pageSupport.setCurrentPageNo(currentPageNo);
		pageSupport.setPageSize(pageSize);
		totalCount = appInfoService.queryCountByConditions(querySoftwareName, _queryStatus, _queryFlatformId,
				_queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, devId);
		pageSupport.setTotalCount(totalCount);
		totalPageCount = pageSupport.getTotalPageCount();
		// 控制首页和末页
		if (currentPageNo < 1) {
			currentPageNo = 1;
		}
		if (currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}
		// 获取集合信息,用户在下拉列表中显示
		List<DataDictionary> statusList = getDataDictionaryList("APP_STATUS");
		List<DataDictionary> flatFormList = getDataDictionaryList("APP_FLATFORM");
		List<AppCategory> categoryLevel1List = appCategoryService.queryAppCategoryListByParentId(null);
		List<AppCategory> categoryLevel2List = null;
		List<AppCategory> categoryLevel3List = null;
		List<AppInfo> appInfoList = appInfoService.queryAppInfoListByConditions(querySoftwareName, _queryStatus,
				_queryFlatformId, _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, devId,
				(currentPageNo - 1) * pageSize, pageSize);
		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("queryStatus", queryStatus);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		model.addAttribute("currentPageNo", currentPageNo);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("categoryLevel2List", categoryLevel2List);
		model.addAttribute("categoryLevel3List", categoryLevel3List);
		model.addAttribute("appInfoList", appInfoList);
		return "developer/appinfolist";
	}

	/**
	 * 根据类型编码获取数据字典表的相关信息
	 * 
	 * @param typeCode
	 * @return
	 */
	List<DataDictionary> getDataDictionaryList(String typeCode) {
		return dataDictionaryService.queryDataDictionaryList(typeCode);
	}

}
