package cn.appsys.controller.backend;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.appCategory.AppCategoryService;
import cn.appsys.service.appinfo.AppinfoService;
import cn.appsys.service.dataDictionary.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/backend/app")
public class AppInfoController {

	@Resource(name="appinfoService")
	private AppinfoService appinfoService;
	
	@Resource(name="dataDictionaryService")
	private DataDictionaryService dataDictionaryService;
	
	@Resource(name="appCategoryService")
	private AppCategoryService appCategoryService;
	
	@RequestMapping("/list")
	public String applist(@RequestParam(value="softwareName",required = false)String softwareName,
			              @RequestParam(value="status",required = false)Integer status,
			              @RequestParam(value="flatformId", required = false)Integer flatformId,
			              @RequestParam(value="categoryLevel1", required = false)String categoryLevel1,
			              @RequestParam(value="categoryLevel2", required = false)String categoryLevel2,
			              @RequestParam(value="categoryLevel3", required = false)String categoryLevel3,
			              @RequestParam(value = "currPageNo", required = false,defaultValue = "1") Integer currPageNo,
			              Model model){
		System.out.println("===================================list");
		int totalCount = appinfoService.getAppInfoCount(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3);
		PageSupport page = new PageSupport();
		page.setPageSize(Constants.pageSize);  // 每页显示的数据行数
		page.setCurrentPageNo(currPageNo);     // 当前页码
		page.setTotalCount(totalCount);        // 总的记录数 
		// 3、计算分页SQL中的位置偏移量
		Integer from = (page.getCurrentPageNo() - 1) * page.getPageSize();
		// 4、调用分页、按条件查询用户列表方法
		List<AppInfo> appInfoList = appinfoService.getAppInfoList(softwareName, 1, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, from, page.getPageSize());
		List<AppCategory> categoryLevel1List =appCategoryService.queryCategories(1);
		List<AppCategory> categoryLevel2List =appCategoryService.queryCategories(2);
		List<AppCategory> categoryLevel3List =appCategoryService.queryCategories(3);
		List<DataDictionary> flatFormList =dataDictionaryService.queryFlatFormList();
		
		// 6、将相关数据保存到Model对象中，用于在页面上进行显示
		model.addAttribute("softwareName", softwareName);
		model.addAttribute("status",status);
		model.addAttribute("flatformId",flatformId);
		model.addAttribute("categoryLevel1",categoryLevel1);
		model.addAttribute("categoryLevel2",categoryLevel2);
		model.addAttribute("categoryLevel3",categoryLevel3);
		model.addAttribute("pages",page);
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("categoryLevel2List", categoryLevel2List);
		model.addAttribute("categoryLevel3List", categoryLevel3List);
		model.addAttribute("flatFormList", flatFormList);
		return "/backend/applist";
	}
	
}
