package com.daxia.generator.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * Aug 31, 2012
 *
 */
public class ContextFilter implements Filter {
	private static Logger logger = Logger.getLogger(ContextFilter.class);
	@Override
    public void destroy() {
    }

	@Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException,
            ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpSession session = request.getSession();
	    String ctxPath = (String) session.getAttribute("ctx");
	    if (ctxPath == null) {
	    		if (request.getServerPort() == 80) {
	    			ctxPath = "http://" + request.getServerName() + request.getContextPath();
	    		} else {
	    			ctxPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    		}
		    	if (logger.isDebugEnabled()) {
		    		logger.debug("ctx path = " + ctxPath);
		    	}
	    	session.setAttribute("ctx", ctxPath);
	    }
	    
	    chain.doFilter(arg0, arg1);
    }

	@Override
    public void init(FilterConfig arg0) throws ServletException {
	    
    }

}
