package com.duck.owlapi.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.duck.owlapi.service.UserService;
import com.duck.owlapi.util.TokenUtil;
import com.duck.owlapi.vo.User;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	private static final String[] ignoreUriArr = {"/owlapi/auth", "/owlapi/users"};
	
	@Resource(name="userService")
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// �α���, ȸ�������� �����Ѵ�
		for (String ignoreUri : ignoreUriArr) {
			if (ignoreUri.equals(request.getRequestURI())) {
				return super.preHandle(request, response, handler);		
			}
		}
		
		// ���� URI�� �ƴ϶�� ��ū�� �˻��Ѵ�
		String authToken = this.extractAuthToken(request);
		String email = TokenUtil.getEmailFromToken(authToken);
		if (email != null) {
			User user = this.userService.getOneByEmail(email);
			if (TokenUtil.validateToken(authToken, user) == true) {
				return super.preHandle(request, response, handler);
			}
		}
		return false;
	}
	
	private String extractAuthToken(HttpServletRequest httpReq) {
		
		String authToken = httpReq.getHeader("X-Auth Token");
		if (authToken == null) {
			authToken = httpReq.getParameter("X-Auth Token");
		}
		return authToken;
	}
	
}
