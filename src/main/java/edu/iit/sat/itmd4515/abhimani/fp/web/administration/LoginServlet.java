package edu.iit.sat.itmd4515.abhimani.fp.web.administration;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/Administrators/Login"})
public class LoginServlet extends HttpServlet{
    @Override
    @SuppressWarnings("empty-statement")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
	response.setContentType("text/html;charset=UTF-8");
	try(PrintWriter out=response.getWriter()){
	    try{
		String username=request.getParameter("j_username"),
			password=request.getParameter("j_password");
		if((!(username==null)&&(username.length()>0))&&(!(password==null)&&(password.length()>0))){
		    request.logout();
		    request.login(username, password);
		    //IF ALL ROLES REDIRECT TO A NEW PAGE WHICH WILL ASK WHAT TO DO - DEPARTMENT/VENUE/STUDENT
		    if(request.isUserInRole("SUPER_ADMIN"))
			response.sendRedirect("Users/Welcome.xhtml");
		    if(request.isUserInRole("DEPT_ADMIN"))
			response.sendRedirect("Departments/Welcome.xhtml");
		    if(request.isUserInRole("VENUE_ADMIN"))
			response.sendRedirect("Venues/Welcome.xhtml");
		}
	    }catch(ServletException|IOException ex){
		;
	    }finally{
		try{
		    response.sendRedirect("login.jsp?msg=Invalid username/password.");
		}catch(Exception ex){
		    ;
		}
	    }
	}
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo(){
	return "Short description";
    }// </editor-fold>
}

