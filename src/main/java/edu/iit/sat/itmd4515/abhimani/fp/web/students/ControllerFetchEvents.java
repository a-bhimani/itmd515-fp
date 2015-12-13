package edu.iit.sat.itmd4515.abhimani.fp.web.students;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Event;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvEvents;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvStudents;
import edu.iit.sat.itmd4515.abhimani.fp.web.AbstractControllerExt;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Named
@RequestScoped
public class ControllerFetchEvents
	extends AbstractControllerExt{
    private static final Logger LOG=Logger.getLogger(ControllerFetchEvents.class.getName());

    @EJB
    SrvStudents srvStudent;

    @EJB
    private SrvEvents srvEvents;

    private Student currStudent;

    private List<Event> lstUpcomingEvents=new ArrayList();
    private List<Event> lstPastEvents=new ArrayList();
    private List<Event> lstTopActiveEvents=new ArrayList();
    private List<Event> lstAttendingEvents=new ArrayList();

    //pre-inits
    /**
     * The first method for the FaceServlet
     */
    @PostConstruct
    @Override
    @Transactional(TxType.REQUIRES_NEW)
    protected void triggerControllerInit(){
	super.triggerControllerInit();
	currStudent=srvStudent.findByUsername(ctxFaces.getExternalContext().getSessionMap().get("auth_username").toString());
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF EVENTS THROUGH DIFFERENT FILTERS - {0} EVENTS IN TOTAL", srvEvents.retrieveAllActive().size());
	lstUpcomingEvents=srvEvents.retrieveAllUpcoming();
	lstPastEvents=srvEvents.retrieveAllPast();
	lstTopActiveEvents=srvEvents.retrieveAllTopActive();
	lstAttendingEvents=srvEvents.retrieveAllAttending(this.currStudent);
	LOG.log(Level.INFO, "END: RETREIVING LIST OF EVENTS THROUGH DIFFERENT FILTERS - {0} EVENTS IN TOTAL", srvEvents.retrieveAllActive().size());
    }

    public ControllerFetchEvents(){
    }

    //call refresh somewhere here
    //method-calls
    public List<Event> getLstUpcomingEvents(){
	return lstUpcomingEvents;
    }

    public List<Event> getLstPastEvents(){
	return lstPastEvents;
    }

    public List<Event> getLstTopActiveEvents(){
	return lstTopActiveEvents;
    }

    public List<Event> getLstAttendingEvents(){
	return lstAttendingEvents;
    }

    public Student getCurrStudent(){
	return currStudent;
    }

    public void setCurrStudent(Student currStudent){
	this.currStudent=currStudent;
    }

}
