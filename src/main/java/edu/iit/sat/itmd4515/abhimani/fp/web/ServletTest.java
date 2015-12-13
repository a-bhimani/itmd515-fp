package edu.iit.sat.itmd4515.abhimani.fp.web;

import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvDepartments;
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
@WebServlet(name="ServletTest", urlPatterns={"/Test"})
public class ServletTest
	extends HttpServlet{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     *
     */
    @EJB
    SrvDepartments srvDepartments;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
	response.setContentType("text/html;charset=UTF-8");
	try(PrintWriter out=response.getWriter()){
	    /* TODO output your page here. You may use following sample code. */
	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Servlet ServletTest</title>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Servlet ServletTest at "+request.getContextPath()+"</h1>");
//	    out.println(EncryptText.crypt("test")+"<br />");
//	    out.println((EncryptText.hashEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", "test") ? "1" : "0")+"<br />");
//	    out.println("<br />");
//	    out.println(EncryptText.crypt("scott")+"<br />");
//	    out.println((EncryptText.hashEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", "scott") ? "1" : "0")+"<br />");
//	    for(Department d:srvDepartments.retrieveAll())
//		out.println(d.toString()+"<br />");
	    out.println("Test succeeded!");
	    out.println("</body>");
	    out.println("</html>");
	}catch(Exception ex){
	    response.getWriter().println(ex.getMessage());
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
	processRequest(request, response);
    }

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
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo(){
	return "TESTING...";
    }// </editor-fold>
}
