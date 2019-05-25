package cn.appsys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.AppInfo;
import cn.appsys.service.appinfo.AppinfoServiceImpl;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@RequestMapping("/app")
public class AppInfoController {

	@Resource
	private AppinfoServiceImpl appinfoServiceImpl;
	
	@RequestMapping("/list")
	public String applist(@RequestParam("softwareName")String softwareName,
			              @RequestParam("status")String status,
			              @RequestParam("flatformId")Integer flatformId,
			              @RequestParam("categoryLevel1")String categoryLevel1,
			              @RequestParam("categoryLevel2")String categoryLevel2,
			              @RequestParam("categoryLevel3")String categoryLevel3,
			              @RequestParam(value = "currPageNo", required = false, defaultValue = "1") Integer currPageNo,
			              Model model){
		int totalCount = appinfoServiceImpl.getAppInfoCount(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3);
		PageSupport page = new PageSupport();
		page.setPageSize(Constants.pageSize);  // 每页显示的数据行数
		page.setCurrentPageNo(currPageNo);     // 当前页码
		page.setTotalCount(totalCount);        // 总的记录数
		// 3、计算分页SQL中的位置偏移量
		Integer from = (page.getCurrentPageNo() - 1) * page.getPageSize();
		// 4、调用分页、按条件查询用户列表方法
		List<AppInfo> applist = appinfoServiceImpl.getAppInfoList(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, from,page.getPageSize());
		// 6、将相关数据保存到Model对象中，用于在页面上进行显示
		model.addAttribute("softwareName", softwareName);
		model.addAttribute("status",status);
		model.addAttribute("flatformId",flatformId);
		model.addAttribute("categoryLevel1",categoryLevel1);
		model.addAttribute("categoryLevel2",categoryLevel2);
		model.addAttribute("categoryLevel3",categoryLevel3);
		model.addAttribute("page",page);
		model.addAttribute("applist", applist);
		return "/backend/applist";
	}
	
}
