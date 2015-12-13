package edu.iit.sat.itmd4515.abhimani.fp.web.students.ajax;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Event;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvEvents;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvStudents;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = {"/Students/ajax/register_for_event"})
public class ServletAttendEvent
	extends HttpServlet{
    @EJB
    protected SrvStudents srvStudents;

    @EJB
    private SrvEvents srvEvents;

    private Student currStudent;
    private Event currEvent;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
	try(PrintWriter out=response.getWriter()){
	    try{
		int Event_Id=0;
		Event_Id=Integer.parseInt(request.getParameter("id"));
		HttpSession session=request.getSession(false);
		currStudent=srvStudents.findByUsername(session.getAttribute("auth_username").toString());
		for(Event evt:srvEvents.retrieveAllActive())
		    if(evt.getPID()==Event_Id&&evt.getRawEBegin().after(new Date()))
			currEvent=evt;
		if(currEvent.getPID()>0){
		    if(!(this.currEvent.getStudentList().contains(currStudent))){
			currStudent.attendEvent(currEvent);
			srvStudents.update(currStudent);
		    }else{
			currStudent.unAttendEvent(currEvent);
			srvStudents.update(currStudent);
		    }
		    response.setContentType("text/plain");
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
