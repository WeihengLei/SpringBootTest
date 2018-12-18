/*
package com.example.test.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.gt.projects.interview.entity.Role;
import org.gt.projects.interview.entity.User;
import org.gt.projects.interview.exception.BaseException;
import org.gt.projects.interview.service.UserService;
import org.gt.projects.interview.util.InterviewConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//自定义一个权限拦截器, 继承HandlerInterceptorAdapter类
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	// 在调用方法之前执行拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {


		User user = null;
		 user = (User) request.getSession().getAttribute("user");
		 if(user==null){
			// 判断是否登录
				ApplicationContext webAppContext = WebApplicationContextUtils
						.getRequiredWebApplicationContext(request.getServletContext());
				UserService userService = (UserService) webAppContext.getBean("userService");
				
				String parameter = request.getParameter("userId");
				System.out.println("=========================================");
				System.out.println("userId:==="+parameter);
				System.out.println("=========================================");
				if (StringUtils.isNotBlank(parameter)) {
					if(!"null".equals(parameter)){
					user = userService.findByActiveId(Integer.valueOf(parameter));
					}
		 }
		
		}
		
		if (user == null) {
			// 如果没有登录就跳转到登录页面 不再进行任何逻辑判断(由前端实现跳转)
			throw new BaseException(InterviewConstants.CMS_API_NOT_LOGIN, "您尚未登录或者登录状态已过期");
//			response.sendRedirect(request.getContextPath() + "/CMS/login.html");
//			return false;
		 }
		if (access.authorities().length > 0) {
			// 如果权限配置不为空, 则取出配置值
			String[] authorities = access.authorities();
			Set<String> authSet = new HashSet<>();
			for (String authority : authorities) {
				// 将权限加入一个set集合中
				authSet.add(authority);
			}
			// 这里我为了方便是直接参数传入权限, 在实际操作中应该是从参数中获取用户Id
			// 到数据库权限表中查询用户拥有的权限集合, 与set集合中的权限进行对比完成权限校验
			// 能获取user就进行权限验证
			List<Role> roles = user.getRoles();
			if (roles != null && roles.size() > 0) {
				Role role = roles.get(0);
				String name = role.getName();
				if (StringUtils.isNotBlank(name)) {
					if (authSet.contains(name)) {
						// 校验通过返回true, 否则拦截请求
						return true;
					}
				}
			}
		}
		// 拦截之后应该返回公共结果, 这里没做处理
		// 如果没有权限 则抛403异常 springboot会处理，跳转到 /error/403 页面
		// 理论上说这里应该是做一个页面 说着是说返回状态 触发前端的时间 来显示ui
		throw new BaseException("sorry!! you don't have the  authentication~~~~");
	}

}
*/
