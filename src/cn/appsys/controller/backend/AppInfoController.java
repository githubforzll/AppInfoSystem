package cn.appsys.controller.backend;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.appCategory.backend.AppCategoryService;
import cn.appsys.service.appInfo.backend.AppInfoService;
import cn.appsys.service.appVersion.backend.AppVersionService;
import cn.appsys.service.dataDictionary.backend.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/backend/app")
public class AppInfoController {
	private Logger logeger=Logger.getLogger(AppInfoController.class);

	@Resource
	private AppInfoService appInfoService;
	
	@Resource
	private DataDictionaryService dataDictionaryService;
	
	@Resource
	private AppCategoryService appCategoryService;
	
	@Resource
	private AppVersionService appVersionServie;
	
	@RequestMapping("/list")
	public String applist(@RequestParam(value="querySoftwareName",required = false)String querySoftwareName,
			              @RequestParam(value="queryStatus",required = false)Integer queryStatus,
			              @RequestParam(value="queryFlatformId", required = false)Integer queryFlatformId,
			              @RequestParam(value="queryCategoryLevel1", required = false)Integer queryCategoryLevel1,
			              @RequestParam(value="queryCategoryLevel2", required = false)Integer queryCategoryLevel2,
			              @RequestParam(value="queryCategoryLevel3", required = false)Integer queryCategoryLevel3,
			              @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
			              Model model){
		logeger.info("pageIndex--->>>"+pageIndex);
		logeger.info("querySoftwareName--->>>"+querySoftwareName);
		logeger.info("queryStatus--->>>"+queryStatus);
		logeger.info("queryFlatformId--->>>"+queryFlatformId);
		logeger.info("queryCategoryLevel1--->>>"+queryCategoryLevel1);
		logeger.info("queryCategoryLevel2--->>>"+queryCategoryLevel2);
		logeger.info("queryCategoryLevel3--->>>"+queryCategoryLevel3);
		Integer currentPageNo=1;
		
		if(querySoftwareName==null){
			querySoftwareName="";
		}
		if(pageIndex !=null){
			currentPageNo=pageIndex;
		}
		
		int totalCount = appInfoService.getAppInfoCount(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
		PageSupport page = new PageSupport();
		page.setPageSize(Constants.pageSize);  // 每页显示的数据行数
		page.setCurrentPageNo(currentPageNo);     // 当前页码
		page.setTotalCount(totalCount);        // 总的记录数 
		// 3、计算分页SQL中的位置偏移量
		Integer from = (page.getCurrentPageNo() - 1) * page.getPageSize();
		// 4、调用分页、按条件查询用户列表方法
		List<AppInfo> appInfoList = appInfoService.getAppInfoList(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, from, page.getPageSize());
		logeger.info("appInfoList.size--->>>"+appInfoList.size());
		List<AppCategory> categoryLevel1List =appCategoryService.queryCategories(null);
		logeger.info("categoryLevel1List.size--->>>"+categoryLevel1List.size());
		List<AppCategory> categoryLevel2List =null;
		List<AppCategory> categoryLevel3List =null;
		List<DataDictionary> flatFormList =dataDictionaryService.queryFlatFormList();
		
		// 6、将相关数据保存到Model对象中，用于在页面上进行显示
		model.addAttribute("softwareName", querySoftwareName);
		model.addAttribute("status",queryStatus);
		model.addAttribute("flatformId",queryFlatformId);
		model.addAttribute("categoryLevel1",queryCategoryLevel1);
		model.addAttribute("categoryLevel2",queryCategoryLevel2);
		model.addAttribute("categoryLevel3",queryCategoryLevel3);
		model.addAttribute("pages",page);
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("categoryLevel2List", categoryLevel2List);
		model.addAttribute("categoryLevel3List", categoryLevel3List);
		model.addAttribute("flatFormList", flatFormList);
		return "backend/applist";
	}
	
	/**
	 * 根据上一级分类的选项获取下一级分类信息
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public Object categoryLevelList(@RequestParam(value="pid",required=false)Integer pid){
		List<AppCategory> list = appCategoryService.queryCategories(pid);
		return JSONArray.toJSONString(list);
	}
	
	/**
	 * 审核
	 * @param versionid
	 * @param appinfoid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/check",method=RequestMethod.GET)
	public String check(@RequestParam(value="vid",required=false)Integer versionid,
			            @RequestParam(value="aid",required=false)Integer appinfoid,
			            Model model){
		logeger.info("versionid--->>>"+versionid+"\nappinfoid--->>>"+appinfoid);
		AppInfo appInfo = appInfoService.getAppInfoIdView(appinfoid);
		AppVersion appVersion = appVersionServie.getVersionByView(versionid);
		model.addAttribute("appInfo",appInfo);
		model.addAttribute("appVersion", appVersion);
		return "backend/appcheck";
	} 
	
	/**
	 * 保存审核内容
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/checksave",method=RequestMethod.POST)
	public String checksave(@RequestParam(value="id",required=false)Integer id,
							@RequestParam(value="status",required=false)Integer status){
		logeger.info("status====================>>>>>"+status);
		logeger.info("id====================>>>>>"+id);
		AppInfo appInfo=appInfoService.getAppInfoIdView(id);
		appInfo.setStatus(status);
		boolean result_modAppInfo=appInfoService.modifyAppInfoById(appInfo);
		logeger.info("--->>>"+result_modAppInfo);
		return "redirect:/backend/app/list";
	}
	
}
