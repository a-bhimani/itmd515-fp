package edu.iit.sat.itmd4515.abhimani.fp.web.students;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvStudents;
import java.io.IOException;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@WebServlet(name = "Auth", urlPatterns = {"/doLogin"})
public class ServletAuth
	extends HttpServlet{
    @EJB
    protected SrvStudents srvStudents;

    private Student authStudent;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
	try{
	    if(!(request.getParameter("frmChk")==null)&&(Integer.parseInt(request.getParameter("frmChk"))==1)){
		String username=request.getParameter("txtUsername"),
			password=request.getParameter("txtPassword");
		if(!(username==null)&&(username.length()>0)){
		    authStudent=srvStudents.findByUsername(username);
		    if(authStudent==null)
			authStudent=srvStudents.findByEmailID(username);
		    if(!(authStudent==null)&&(!(password==null)&&(password.length()>0)))
			//match credentials
			if(authStudent.getAuth().matchLogin(password)){
			    //deal with session
			    HttpSession session=request.getSession(true);
			    request.login(authStudent.getAuth().getUsername(), password);
			    session.setAttribute("auth_loginid", authStudent.getAuth().getLoginID());
			    session.setAttribute("auth_username", username);
			    session.setAttribute("auth_student_number", authStudent.getStudent_Number());
			    session.setAttribute("auth_email_id", authStudent.getEmailID());
			    session.setAttribute("auth_timestamp", new Date());
			    //redirect to welcome
			    response.sendRedirect("/Students/index.xhtml");
			}
		    response.sendRedirect("login.jsp?msg=Invalid username/password.");
		}
	    }
	    throw new Exception("Someone attempted to bypass the form.");
	}catch(Exception ex){
	    ;
	}finally{
	    try{
		response.sendRedirect("login.jsp?msg=Invalid username/password.");
	    }catch(Exception ex){
		;
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
