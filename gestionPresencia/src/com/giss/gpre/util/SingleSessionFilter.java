package com.giss.gpre.util;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

public class SingleSessionFilter implements Filter{

	// Map to store user -> HttpSession
    private static final ConcurrentHashMap<String, HttpSession> userSessionMap = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
		ESAPI.httpUtilities().setCurrentHTTP(httpRequest, httpResponse);
		
        HttpSession currentSession = ESAPI.currentRequest().getSession(false);

        if (currentSession != null && currentSession.getAttribute("tDni") != null) {
            String usuario = (String) currentSession.getAttribute("tDni");

            HttpSession oldSession = userSessionMap.put(usuario, currentSession);

            if (oldSession != null && !oldSession.getId().equals(currentSession.getId())) {
                // Kill the old session securely
            	oldSession.invalidate();
            }
        }

        chain.doFilter(request, response);
    }
    
	@Override
	public void destroy() {
		// Apéndice de método generado automáticamente	
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// Apéndice de método generado automáticamente
	};
	
}
