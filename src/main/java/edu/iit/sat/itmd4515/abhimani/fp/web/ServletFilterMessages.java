/*

THIS IS A BAD IDEA AND WORKS VERY POORLY WITH FACES

*/


//package edu.iit.sat.itmd4515.abhimani.fp.web;
//
//import java.io.IOException;
//import static java.lang.System.out;
//import javax.annotation.PostConstruct;
//import javax.enterprise.context.Dependent;
//import javax.faces.application.FacesMessage;
//import javax.inject.Named;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import org.apache.commons.lang3.StringEscapeUtils;
//
///**
// *
// * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
// */
//@WebFilter(urlPatterns = {"/*"})
//@Named
//@Dependent
//public class ServletFilterMessages
//	extends AbstractServletExt
//	implements Filter{
//    public String outputMsg;
//
//    @Override
//    public void init(FilterConfig filterConfig)
//	    throws ServletException{
//	this.outputMsg="";
//    }
//
//    @Override
//    @PostConstruct
//    protected void postConstruct(){
//	super.postConstruct();
//    }
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//	    throws IOException, ServletException{
//	try{
//	    if(req.getAttribute("msg")!=null)
//		this.outputMsg=req.getAttribute("msg").toString();
//	    else{
//		String msg=StringEscapeUtils.escapeHtml4(req.getParameter("msg"));
//		String success=req.getParameter("success");
//		if(!(msg==null)&&(msg.length()>0))
//		    this.outputMsg=msg;
//		if(!(success==null)&&(success.equals("true")))
//		    this.outputMsg=("<span class=\"green bold\">"+msg+"</span>");
//	    }
//	}catch(Exception ex){
//	}
//	if(this.outputMsg.length()>0){
//	    this.ctxFaces.addMessage(null, new FacesMessage(this.outputMsg));
//	    req.setAttribute("msg", outputMsg);
//	}
//	out.println(this.outputMsg);
//	chain.doFilter(req, res);
//    }
//
//    @Override
//    public void destroy(){
//    }
//}
