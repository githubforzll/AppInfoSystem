package cn.appsys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.dao.backendUser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/backendUser")
public class BackendUserController {

	@Resource
	private BackendUserMapper backendUserMapper;

	@RequestMapping("/login")
	public String login() {
		return "backendlogin";
	}

	@RequestMapping("/doLogin")
	public String main(@RequestParam("userCode") String userCode, @RequestParam("userPassword") String userPassword,
			HttpSession session) {
		BackendUser backendUser = backendUserMapper.queryBackendUserByConditions(userCode, userPassword);
		DataDictionary dataDictionary = backendUserMapper.queryBackendUserBydictionary(userCode);
		session.setAttribute("dataDictionary", dataDictionary);
		session.setAttribute(Constants.BackendUSER_SESSION, backendUser);
		if (backendUser != null) {
			return "/backend/main";
		} else {
			return "backendlogin";
		}
	}
}
