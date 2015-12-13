<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div id="right_wrap">
<div id="msgBox" class="red"><%
    try{
	String msg=StringEscapeUtils.escapeHtml4(request.getParameter("msg").toString());
	String success=request.getParameter("success");
	if(!(msg==null)&&(msg.length()>0))
	    if(!(success==null)&&(success.equals("true")))
		out.println("<span class=\"green bold\">"+msg+"</span>");
	    else
		out.println(msg);
    }catch(Exception ex){
    }
    %></div>
</div>

