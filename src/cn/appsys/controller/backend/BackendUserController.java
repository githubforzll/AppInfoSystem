package cn.appsys.controller.backend;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		return "/backendlogin";
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
	
	@RequestMapping("/logout")
	public void close(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.sendRedirect(request.getContextPath()+"/index.jsp");//重定向到欢迎页(首页)
		request.getSession().invalidate();//使session失效
	}
}
