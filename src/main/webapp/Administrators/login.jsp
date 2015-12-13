<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<head>
<title>Student Event Portal :: Administrator Login</title>
<%@include file='../WEB-INF/abstracts/pre.htm'%>
<style type="text/css" media="all">

</style>
</head>
<body>
<%@include file='../WEB-INF/abstracts/mmenu.jsp'%>
<div id="wrapper_main" data-role="page">
    <div id="wrap_head" data-role="header"><%@include file='../WEB-INF/abstracts/header.jsp'%></div>
    <div id="wrap_content" data-role="content">
	<h2 class="heading">Adminsitrator Login</h2>
    	<form name="x0" method="post" action="Login">
	    <table id="tblx">
		<tbody>
		    <tr><td style="border-top:1px solid #bbb;">&nbsp;</td></tr>
		    <tr>
			<td><label><span class="bold">Username</span> <span class="red">*</span> <br /><input type="text" name="j_username" id="j_username" title="Enter Username" alt="1" placeholder="Enter Username" maxlength="155" <% if(request.getRemoteUser()!=null){ %>readonly="readonly"<% } %> /></label><% if(request.getRemoteUser()!=null){ %><span class="red">You cannot log in with multiple users.</span><% } %>
			<% if(request.getRemoteUser()!=null){ %><br /><a href="/Administrators/Logout">logout</a><% } %>
			</td>
		    </tr>
		    <tr>
			<td><label><span class="bold">Password</span> <span class="red">*</span> <br /><input size="6" type="password" name="j_password" id="txtPassword" title="Enter Password" alt="1" placeholder="Enter Password" maxlength="155" /></label></td>
		    </tr>
		    <tr><td style="border-bottom:1px solid #bbb;">&nbsp;</td></tr>
		    <tr><input name="frmChk" type="hidden" value="0" />
			<td style="color:#bbb;">
			    <input type="submit" id="btnLogin" value="login" style="margin-left:0;padding-left:0;" /> | <input type="reset" id="reset" value="clear" />
			</td>
		    </tr>
		</tbody>
	    </table>
	</form>
    </div>
</div>
<%@include file='../WEB-INF/abstracts/post.htm'%>
<script type="text/javascript">
var x=document.x0;
try{fncFrmLoad(x);
    var validate=function(x){
	var t=fncValidateFrm(x);
	    if(t){
		//additional stuff
	    }
	    return t;
	}
    x.onsubmit=function(){
	var t=validate(this);
	if(t) x.submit();
	return false;
    }
}catch(e){alert(e);}
</script>
</body>
</html>

