package edu.iit.sat.itmd4515.abhimani.fp.web.administration.users.ajax;

import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.Authentication;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvAuthUsers;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@WebServlet(urlPatterns = {"/Administrators/Users/ajax/delete_admin"})
public class ServletDeleteAdministrator
	extends HttpServlet{
    @EJB
    private SrvAuthUsers srvAuthUsers;

    private Authentication adminUser;

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
    @SuppressWarnings("empty-statement")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
	response.setContentType("text/plain");
	try(PrintWriter out=response.getWriter()){
	    try{
		adminUser=srvAuthUsers.findByID(Integer.parseInt(request.getParameter("id")));
		if(adminUser!=null){
		    srvAuthUsers.delete(adminUser);
		    out.print(1);
		    return;
		}
		out.print(-1);
	    }catch(Exception ex){
		out.print(0);
	    }finally{
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
