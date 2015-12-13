package edu.iit.sat.itmd4515.abhimani.fp.web.administration.departments;

import edu.iit.sat.itmd4515.abhimani.fp.domain.EventState;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Department;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Event;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Venue;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvDepartments;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvEvents;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvVenues;
import edu.iit.sat.itmd4515.abhimani.fp.web.AbstractControllerExt;
import edu.iit.sat.itmd4515.abhimani.fp.web.administration.users.ControllerProcessUsers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Named
@RequestScoped
public class ControllerProcessEvent
	extends AbstractControllerExt{
    private static final String DATE_FORMAT="MM-dd-yyyy";
    private static final Logger LOG=Logger.getLogger(ControllerProcessEvent.class.getName());

    @EJB
    SrvEvents srvEvents;

    @EJB
    SrvDepartments srvDepartments;

    @EJB
    SrvVenues srvVenues;

    private int event_id;

    private Event updEvent;

    List<Department> lstDepartments=new ArrayList<>();
    List<Venue> lstVenues=new ArrayList<>();

    @NotNull
    @Size(min = 2)
    private String strTitle;

    @NotNull
    private String strDescription;

    @NotNull
    @Min(0)
    private long intDepartment_Id;

    @NotNull
    @Min(0)
    private long intVenue_Id;

    private String strBeginDate;
    private String strBeginDateHrs;
    private String strBeginDateMins;

    private String strEndDate;
    private String strEndDateHrs;
    private String strEndDateMins;

    //pre-inits
    @PostConstruct
    @Override
    @SuppressWarnings("empty-statement")
    protected void triggerControllerInit(){
	super.triggerControllerInit();
	lstDepartments=srvDepartments.retrieveAll();
	lstVenues=srvVenues.retrieveAll();
    }

    //method-calls
    @SuppressWarnings("empty-statement")
    public ControllerProcessEvent(){
	;
    }

    @SuppressWarnings("empty-statement")
    public void pre_load(){
	try{
	    this.updEvent=srvEvents.findByID(event_id);
	    this.intDepartment_Id=this.updEvent.getDepartment().getPID();
	    this.intVenue_Id=this.updEvent.getVenue().getPID();
	    this.strTitle=this.updEvent.getTitle();
	    this.strBeginDate=((new SimpleDateFormat(DATE_FORMAT)).format(this.updEvent.getRawEBegin()));
	    this.strBeginDateHrs=((new SimpleDateFormat("HH")).format(this.updEvent.getRawEBegin()));
	    this.strBeginDateMins=((new SimpleDateFormat("mm")).format(this.updEvent.getRawEBegin()));
	    this.strEndDate=((new SimpleDateFormat(DATE_FORMAT)).format(this.updEvent.getRawEEnd()));
	    this.strEndDateHrs=((new SimpleDateFormat("HH")).format(this.updEvent.getRawEEnd()));
	    this.strEndDateMins=((new SimpleDateFormat("mm")).format(this.updEvent.getRawEEnd()));
	    this.strDescription=this.updEvent.getDescription();
	    try{
		if(updEvent.equals(null))
		    ctxFaces.getExternalContext().redirect("Welcome.xhtml?msg=The_system_could_not_find_the_Event.");
	    }catch(Exception ex){
		try{
		    ctxFaces.getExternalContext().redirect("Welcome.xhtml?msg=The_system_could_not_find_the_Event.");;
		}catch(Exception iex){
		    ;
		}
	    }
	}catch(Exception ex){
	    ;
	}
    }

    @SuppressWarnings("empty-statement")
    public String manipulateEvent(){
	try{
	    if(Integer.parseInt(this.ctxFaces.getExternalContext().getRequestParameterMap().get("frmChk"))==1)
		if(event_id>0){
		    this.updEvent=srvEvents.findByID(event_id);
		    LOG.log(Level.INFO, "BEGIN: UPDATING EVENT- {0}", updEvent.toString());
		    this.updEvent.setTitle(strTitle);
		    this.updEvent.setDepartment(srvDepartments.findByID(intDepartment_Id));
		    this.updEvent.setVenue(srvVenues.findByID(intVenue_Id));
		    this.updEvent.setDescription(strDescription);
		    this.updEvent.setEBegin(new SimpleDateFormat(DATE_FORMAT+" HH:mm").parse(strBeginDate+" "+strBeginDateHrs+":"+strBeginDateMins));
		    this.updEvent.setEEnd(new SimpleDateFormat(DATE_FORMAT+" HH:mm").parse(strEndDate+" "+strEndDateHrs+":"+strEndDateMins));
		    //this.updEvent.setAState();
		    try{
			srvEvents.update(updEvent);
		    }catch(Exception ex){
			return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=A_conflict_occured_while_updating_the_Event.");
		    }
		    LOG.log(Level.INFO, "END: UPDATING EVENT - {0}", updEvent.toString());
		    return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=$success_The_Event_has_been_updated_successfully.");
		}else{
		    LOG.log(Level.INFO, "BEGIN: CREATING NEW EVENT - {0}", strTitle);
		    Event evt=new Event(srvDepartments.findByID(intDepartment_Id), srvVenues.findByID(intVenue_Id), strTitle, strDescription, new SimpleDateFormat(DATE_FORMAT+" HH:mm").parse(strBeginDate+" "+strBeginDateHrs+":"+strBeginDateMins), new SimpleDateFormat(DATE_FORMAT+" HH:mm").parse(strEndDate+" "+strEndDateHrs+":"+strEndDateMins));
		    try{
			srvEvents.create(evt);
		    }catch(Exception ex){
			return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=An_Event_with_the_same_parameters_already_exists.");
		    }
		    LOG.log(Level.INFO, "END: CREATING NEW EVENT - {0}", strTitle);
		    //email that new credentials have been created
		    return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=$success_The_new_Event_was_created_successfully.");
		}
	}catch(NumberFormatException|ParseException ex){
	    LOG.log(Level.SEVERE, "ERROR: MANIPULATING EVENT - {0}", strTitle);;
	}
	return ("Manage_Event.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=Insufficient_data_to_add_a_new_Event.");
    }

    //getters-setters
    public int getEvent_id(){
	return event_id;
    }

    public void setEvent_id(int event_id){
	this.event_id=event_id;
    }

    public String getStrTitle(){
	return strTitle;
    }

    public void setStrTitle(String strTitle){
	this.strTitle=strTitle;
    }

    public String getStrDescription(){
	return strDescription;
    }

    public void setStrDescription(String strDescription){
	this.strDescription=strDescription;
    }

    public long getIntDepartment_Id(){
	return intDepartment_Id;
    }

    public void setIntDepartment_Id(long intDepartment_Id){
	this.intDepartment_Id=intDepartment_Id;
    }

    public long getIntVenue_Id(){
	return intVenue_Id;
    }

    public void setIntVenue_Id(long intVenue_Id){
	this.intVenue_Id=intVenue_Id;
    }

    public String getStrBeginDate(){
	return strBeginDate;
    }

    public void setStrBeginDate(String strBeginDate){
	this.strBeginDate=strBeginDate;
    }

    public String getStrEndDate(){
	return strEndDate;
    }

    public void setStrEndDate(String strEndDate){
	this.strEndDate=strEndDate;
    }

    public List<Department> getLstDepartments(){
	return lstDepartments;
    }

    public void setLstDepartments(List<Department> lstDepartments){
	this.lstDepartments=lstDepartments;
    }

    public List<Venue> getLstVenues(){
	return lstVenues;
    }

    public void setLstVenues(List<Venue> lstVenues){
	this.lstVenues=lstVenues;
    }

    public String getStrBeginDateHrs(){
	return strBeginDateHrs;
    }

    public void setStrBeginDateHrs(String strBeginDateHrs){
	this.strBeginDateHrs=strBeginDateHrs;
    }

    public String getStrBeginDateMins(){
	return strBeginDateMins;
    }

    public void setStrBeginDateMins(String strBeginDateMins){
	this.strBeginDateMins=strBeginDateMins;
    }

    public String getStrEndDateHrs(){
	return strEndDateHrs;
    }

    public void setStrEndDateHrs(String strEndDateHrs){
	this.strEndDateHrs=strEndDateHrs;
    }

    public String getStrEndDateMins(){
	return strEndDateMins;
    }

    public void setStrEndDateMins(String strEndDateMins){
	this.strEndDateMins=strEndDateMins;
    }
}
