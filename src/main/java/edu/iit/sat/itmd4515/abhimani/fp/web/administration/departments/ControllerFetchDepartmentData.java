package edu.iit.sat.itmd4515.abhimani.fp.web.administration.departments;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Department;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Event;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvDepartments;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvEvents;
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

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Named
@RequestScoped
public class ControllerFetchDepartmentData
	extends AbstractControllerExt{
    private static final Logger LOG=Logger.getLogger(ControllerFetchDepartmentData.class.getName());

    @EJB
    SrvEvents srVEvents;

    @EJB
    SrvDepartments srvDepartments;

    private List<Department> lstDepartments=new ArrayList<>();
    private List<Event> lstPendingEvents=new ArrayList<>();
    private List<Event> lstActiveEvents=new ArrayList<>();
    private List<Event> lstCancelledEvents=new ArrayList<>();
    private List<Event> lstDeclinedEvents=new ArrayList<>();

    //pre-inits
    /**
     * The first method for the FaceServlet
     */
    @PostConstruct
    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected void triggerControllerInit(){
	super.triggerControllerInit();
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF DEPARTMENTS - {0} VENUES IN TOTAL", srvDepartments.retrieveAll().size());
	lstDepartments=srvDepartments.retrieveAll();
	LOG.log(Level.INFO, "END: RETREIVING LIST OF DEPARTMENTS - {0} VENUES IN TOTAL", srvDepartments.retrieveAll().size());
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF ACTIVE EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllActive().size());
	lstActiveEvents=srVEvents.retrieveAllActive();
	LOG.log(Level.INFO, "END: RETREIVING LIST OF ACTIVE EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllActive().size());
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF NEW EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllPending().size());
	lstPendingEvents=srVEvents.retrieveAllPending();
	LOG.log(Level.INFO, "END: RETREIVING LIST OF NEW EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllPending().size());
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF CANCELLED EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllCancelled().size());
	lstCancelledEvents=srVEvents.retrieveAllCancelled();
	LOG.log(Level.INFO, "END: RETREIVING LIST OF CANCELLED EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllCancelled().size());
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF DECLINED EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllDeclined().size());
	lstDeclinedEvents=srVEvents.retrieveAllDeclined();
	LOG.log(Level.INFO, "END: RETREIVING LIST OF DECLINED EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllDeclined().size());
    }

    public ControllerFetchDepartmentData(){
    }

    //getter-setters
    public List<Department> getLstDepartments(){
	return lstDepartments;
    }

    public void setLstDepartments(List<Department> lstDepartments){
	this.lstDepartments=lstDepartments;
    }

    public List<Event> getLstCancelledEvents(){
	return lstCancelledEvents;
    }

    public void setLstCancelledEvents(List<Event> lstCancelledEvents){
	this.lstCancelledEvents=lstCancelledEvents;
    }

    public List<Event> getLstDeclinedEvents(){
	return lstDeclinedEvents;
    }

    public void setLstDeclinedEvents(List<Event> lstDeclinedEvents){
	this.lstDeclinedEvents=lstDeclinedEvents;
    }

    public List<Event> getLstPendingEvents(){
	return lstPendingEvents;
    }

    public void setLstPendingEvents(List<Event> lstPendingEvents){
	this.lstPendingEvents=lstPendingEvents;
    }

    public List<Event> getLstActiveEvents(){
	return lstActiveEvents;
    }

    public void setLstActiveEvents(List<Event> lstActiveEvents){
	this.lstActiveEvents=lstActiveEvents;
    }
}
