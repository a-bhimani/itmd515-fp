package edu.iit.sat.itmd4515.abhimani.fp.web.administration.venues.ajax;

import edu.iit.sat.itmd4515.abhimani.fp.domain.EventState;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Event;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvEvents;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvStudents;
import edu.iit.sat.itmd4515.abhimani.fp.web.cdi.BeanEmailer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@WebServlet(urlPatterns = {"/Administrators/Venues/ajax/approve_event"})
public class ServletApproveEvent
	extends HttpServlet{
    @Inject
    BeanEmailer emailer;

    @EJB
    private SrvEvents srvEvents;

    @EJB
    private SrvStudents srvStudents;

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
		Event evt=srvEvents.findByID(Integer.parseInt(request.getParameter("id")));
		if(evt!=null){
		    evt.setAState(EventState.APPROVED);
		    srvEvents.update(evt);
		    try{
			for(Student stud:srvStudents.retrieveAll())
			    if(stud.isNotifyEvents())
				emailer.sendMail(stud.getEmailID(), ("A new Event "+evt.getTitle()+" is now available."), ("Dear "+stud.getFName()+" "+stud.getLName()+", An Event "+evt.getTitle()+" is now available on the Student Portal for registrations."));
		    }catch(Exception ex){
			;
		    }
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
