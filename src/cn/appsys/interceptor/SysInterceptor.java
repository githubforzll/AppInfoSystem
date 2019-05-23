package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.appsys.pojo.DevUser;
import cn.appsys.tools.Constants;

/**
 * 自定义拦截器
 * @author zll
 *
 */
public class SysInterceptor extends HandlerInterceptorAdapter {

	private Logger logger=Logger.getLogger(SysInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request,
							HttpServletResponse response,
							Object handler)throws Exception{
		logger.info("++++++++++SysInterceptor preHandle!+++++++++");
		HttpSession session=request.getSession();
		DevUser user=(DevUser)session.getAttribute(Constants.DevUSER_SESSION);
		logger.info("+++++++++"+user);
		if(user==null){
			response.sendRedirect(request.getContextPath()+"/403.jsp");
			return false;
		}
		return true;
	}
}
