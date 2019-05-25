package cn.appsys.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.service.appinfo.AppinfoServiceImpl;

@RequestMapping("/backend/app")
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
			              @RequestParam("from")Integer from,
			              @RequestParam("pageSize")Integer pageSize){
		
		return "/backend/applist";
	}
	
}
