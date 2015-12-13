package edu.iit.sat.itmd4515.abhimani.fp.web.students;

import java.io.IOException;
import static java.lang.System.out;
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

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@WebFilter(filterName = "fltrAuthTerminate", urlPatterns = {"/Students/*"})
public class FilterAuthTerminate
	implements Filter{

    @Override
    public void init(FilterConfig filterConfig)
	    throws ServletException{
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
	    throws IOException, ServletException{
	try{
	    HttpServletRequest hReq=((HttpServletRequest)req);
	    HttpSession session=((HttpServletRequest)req).getSession(false);
	    //out.println(hReq.getSession().getAttribute("auth_username").toString());
	    if((hReq.getRequestedSessionId()!=null)&&!(hReq.isRequestedSessionIdValid())
		    &&(hReq.getSession().getAttribute("auth_username")!=null)&&(hReq.getSession().getAttribute("auth_username").toString().length()==0))
		((HttpServletResponse)res).sendRedirect("/AuthTerminate");
	}catch(Exception ex){
	    ((HttpServletResponse)res).sendRedirect("/AuthTerminate");
	}finally{
	    chain.doFilter(req, res);
	}
    }

    @Override
    public void destroy(){
    }
}
