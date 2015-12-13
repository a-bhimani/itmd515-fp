package edu.iit.sat.itmd4515.abhimani.fp.web.administration.venues;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Event;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Venue;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvEvents;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvVenues;
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
public class ControllerFetchVenues
	extends AbstractControllerExt{
    private static final Logger LOG=Logger.getLogger(ControllerFetchVenues.class.getName());

    @EJB
    SrvEvents srVEvents;

    @EJB
    SrvVenues srvVenues;

    private List<Venue> lstVenues=new ArrayList<>();
    private List<Event> lstPendingEvents=new ArrayList<>();
    private List<Event> lstActiveEvents=new ArrayList<>();

    //pre-inits
    /**
     * The first method for the FaceServlet
     */
    @PostConstruct
    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected void triggerControllerInit(){
	super.triggerControllerInit();
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF VENUES - {0} VENUES IN TOTAL", srvVenues.retrieveAll().size());
	lstVenues=srvVenues.retrieveAll();
	LOG.log(Level.INFO, "END: RETREIVING LIST OF VENUES - {0} VENUES IN TOTAL", srvVenues.retrieveAll().size());
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF ACTIVE EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllActive().size());
	lstActiveEvents=srVEvents.retrieveAllActive();
	LOG.log(Level.INFO, "END: RETREIVING LIST OF ACTIVE EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllActive().size());
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF NEW EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllPending().size());
	lstPendingEvents=srVEvents.retrieveAllPending();
	LOG.log(Level.INFO, "END: RETREIVING LIST OF NEW EVENTS - {0} EVENTS IN TOTAL", srVEvents.retrieveAllPending().size());
    }

    public ControllerFetchVenues(){
    }

    //call refresh somewhere here
    //method-calls
    //getter-setters
    public List<Venue> getLstVenues(){
	return lstVenues;
    }

    public void setLstVenues(List<Venue> lstVenues){
	this.lstVenues=lstVenues;
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
