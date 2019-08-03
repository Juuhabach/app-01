package br.upf.fabsoft.jmkidsquiosque.beans;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebFilter(urlPatterns = { "/faces/Privado/*" })
public class FiltrarLogin implements Filter {

	public FiltrarLogin() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession sessao = httpRequest.getSession();
		String contextPath = httpRequest.getContextPath();
		LoginBean lb = (LoginBean) sessao.getAttribute("loginBean");
		if ((lb == null) || (lb.getUsuarioLogado() == null)) {
			// System.out.println("Redirecionar para : " + contextPath +
			// "/faces/Login/LoginForm.xhtml");
			httpResponse.sendRedirect(contextPath + "/faces/Login/LoginForm.xhtml");
		} else
			chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}