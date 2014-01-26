package com.helloweenvsfei.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageRedirectFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// �T��֨�O����
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragrma", "no-cache");
		response.setDateHeader("Expires", 0);

		// �챵�ӷ��a�}
		String referer = request.getHeader("referer");
		System.out.println("referer="+referer+"\n");

		if (referer == null || !referer.contains(request.getServerName())) {

			/**
			 * �p�G �챵�a�}�Ӧۨ�L�����A�h�Ǧ^��~�Ϥ�
			 */
			request.getRequestDispatcher("/error.gif").forward(request,response);
			System.out.println("有人盜連圖片");

		} else {

			/**
			 * �Ϥ�`���
			 */
			chain.doFilter(request, response);
		}

	}

	public void destroy() {
	}
}
