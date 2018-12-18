package com.example.test.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Enumeration;

public class RequestLogInterceptor extends HandlerInterceptorAdapter {

	private final Logger requestLogger = LoggerFactory.getLogger("requestLog");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute("_startTime", System.currentTimeMillis());


		//response.addDateHeader("preHandle" ,System.currentTimeMillis());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		this.writeLog(this.requestLogger, request);
	}

	private void writeLog(Logger logger, HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		String method = request.getMethod();
		String link = this.getLinkWithQuery(request);


	/*	String sessionId = "-";
		try {
			sessionId = request.getSession().getId();
		} catch (IllegalStateException e) {
			sessionId = "-";
		}*/
		String userAgent = this.getUserAgent(request);
		String log = null;
		if (request.getAttribute("_startTime") != null) {
			Long startTime = (Long) request.getAttribute("_startTime");
			log = ip + "\t" + method + "\t" + link + "\t" + userAgent + "\t" + (System.currentTimeMillis() - startTime);
		} else {
			log = ip + "\t" + method + "\t" + link + "\t" + userAgent;
		}


		logger.info(log);
	}

	private String getLinkWithQuery(HttpServletRequest request) {
		String link = request.getRequestURI();
		StringBuffer stringBuffer = new StringBuffer();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameter = parameterNames.nextElement();
			try {
				String[] values = ServletRequestUtils.getStringParameters(request, parameter);
				for (int i = 0; i < values.length; i++)
					stringBuffer.append(parameter).append("=").append(URLEncoder.encode(values[i], "UTF-8")).append("&");
			} catch (Exception e) {
				stringBuffer.append(parameter).append("=").append("[NON-STRING-OBJECT]").append("&");
			}
		}

		if (!stringBuffer.toString().equals(""))
			link += ("?" + stringBuffer.substring(0, stringBuffer.length() - 1));

		return link;
	}

	private String getUserAgent(HttpServletRequest httpServletRequest) {
		String ua = httpServletRequest.getHeader("User-Agent");
		if (ua == null || ua.trim().length() == 0)
			ua = "_UNKNOWN_";
		return ua;
	}

}
