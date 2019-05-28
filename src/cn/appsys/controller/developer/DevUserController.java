package cn.appsys.controller.developer;


import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.devUser.DevUserService;
import cn.appsys.tools.Constants;

/**
 * 开发者用户控制器:
 * 用于开发者用户的登录和注销
 * @author zll
 *
 */
@Controller
@RequestMapping("/dev")
public class DevUserController {
	@Resource
	private DevUserService devUserService;
	
	private Logger logger=Logger.getLogger(DevUserController.class);
	
	

	
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.sendRedirect(request.getContextPath()+"/index.jsp");//重定向到欢迎页(首页)
		request.getSession().invalidate();//使session失效
	}
	
	/**
	 * 处理登录情况
	 * @param session
	 * @return
	 */
	@RequestMapping("/main")
	public String main(HttpSession session){
		DevUser devUser=(DevUser)session.getAttribute(Constants.DevUSER_SESSION);
		if(devUser !=null){
			return "developer/main";
		}else{
			return "devlogin";
		}
	}

	/**
	 * 处理开发者用户登录
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam(value="devCode",required=false)String devCode,
						@RequestParam(value="devPassword",required=false)String devPassword,
						HttpSession session){
		logger.info("devCode+++++"+devCode+"\ndevPassword+++++"+devPassword);
		DevUser devUser=devUserService.queryDevUserByConditions(devCode, devPassword);		
		logger.info(devUser);
		//将开发者用户信息保存到sess中
		session.setAttribute(Constants.DevUSER_SESSION, devUser);
		if(devUser !=null){
			return "redirect:/dev/main";
		}else{
			session.setAttribute("error", "开发者用户编号或者密码输入错误!");
			return "devlogin";
		}
	}
	
	/*
	 * 跳转到登录页面
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){		
		return "devlogin";
	}
	
}
