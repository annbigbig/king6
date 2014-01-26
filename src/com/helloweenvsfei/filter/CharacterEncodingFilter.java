package com.helloweenvsfei.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {

	private String characterEncoding;
	//private boolean enabled;
	private String enabled;

	@Override
	public void init(FilterConfig config) throws ServletException {

		characterEncoding = config.getInitParameter("characterEncoding");
		enabled = config.getInitParameter("enabled");

		//enabled = "true".equalsIgnoreCase(characterEncoding.trim()) || "1".equalsIgnoreCase(characterEncoding.trim());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (enabled.equals("true") && characterEncoding.length()>0) {
			request.setCharacterEncoding(characterEncoding);
			response.setCharacterEncoding(characterEncoding);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		characterEncoding = null;
	}
}
