package edu.iit.sat.itmd4515.abhimani.fp.web.students;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Event;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.domain.relations.Comment;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvComments;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvEvents;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvStudents;
import edu.iit.sat.itmd4515.abhimani.fp.web.AbstractControllerExt;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Named
@RequestScoped
public class ControllerEventData
	extends AbstractControllerExt{
    private static final Logger LOG=Logger.getLogger(ControllerEventData.class.getName());

    @EJB
    SrvStudents srvStudent;

    @EJB
    private SrvEvents srvEvents;

    @EJB
    private SrvComments srvComments;

    private Student currStudent;
    private Event currEvent;

    private boolean isAttendingEvent;

    @NotNull
    @Size(min = 2)
    private String NewComment;

    boolean isNotPast;
    private long event_id;

    //pre-inits
    /**
     * The first method for the FaceServlet
     */
    @PostConstruct
    @Override
    @Transactional(TxType.REQUIRES_NEW)
    @SuppressWarnings("empty-statement")
    protected void triggerControllerInit(){
	super.triggerControllerInit();
	this.isAttendingEvent=false;
	this.currStudent=srvStudent.findByUsername(ctxFaces.getExternalContext().getSessionMap().get("auth_username").toString());
	try{
	    this.event_id=Integer.parseInt(ctxFaces.getExternalContext().getRequestParameterMap().get("event_id"));
	    for(Event evt:this.srvEvents.retrieveAllActive())
		if(evt.getPID()==this.event_id)
		    this.currEvent=srvEvents.findByID(evt.getPID());
	    if(this.currEvent.getPID()>0){
		if(this.currEvent.getStudentList().contains(this.currStudent))
		    isAttendingEvent=true;
		isNotPast=this.currEvent.getRawEBegin().after(new Date());
	    }
	    this.NewComment="";
	    try{
		if(this.currEvent==null)
		    ctxFaces.getExternalContext().redirect("Events.xhtml?msg=The_system_could_not_find_the_event.");
	    }catch(Exception fex){
		try{
		    ctxFaces.getExternalContext().redirect("Events.xhtml?msg=The_system_could_not_find_the_event.");;
		}catch(Exception iex){
		    ;
		}
	    }
	}catch(Exception ex){
	    ;
	}
    }

    @SuppressWarnings("empty-statement")
    public ControllerEventData(){
	;
    }

    //method-calls
    @SuppressWarnings("empty-statement")
    public void pre_load(){
	this.srvEvents.retrieveAllActive().stream().filter((evt)->(evt.getPID()==this.event_id)).forEach((evt)->{
	    this.currEvent=srvEvents.findByID(evt.getPID());
	});
	if(this.currEvent.getPID()>0){
	    if(this.currEvent.getStudentList().contains(this.currStudent))
		isAttendingEvent=true;
	    isNotPast=this.currEvent.getRawEBegin().after(new Date());
	}
	this.NewComment="";
	try{
	    if(this.currEvent==null)
		ctxFaces.getExternalContext().redirect("Events.xhtml?msg=The_system_could_not_find_the_event.");
	}catch(Exception ex){
	    try{
		ctxFaces.getExternalContext().redirect("Events.xhtml?msg=The_system_could_not_find_the_event.");;
	    }catch(Exception iex){
		;
	    }
	}
    }

    public String addComment(){
	this.currEvent=srvEvents.findByID(event_id);
	if(this.currEvent.getPID()>0){
	    LOG.log(Level.INFO, "BEGIN: ADDING EVENT COMMENT - {0} COMMENTS IN TOTAL", currEvent.getComments().size());
	    Comment addComment=new Comment();
	    addComment.setComment(NewComment);
	    addComment.setStudent(currStudent);
	    addComment.setEvent(currEvent);
	    this.srvComments.create(addComment);
	    LOG.log(Level.INFO, "END: ADDING EVENT COMMENT - {0} COMMENTS IN TOTAL", currEvent.getComments().size());
	    return ("Event.xhtml"+ControllerEventData.FACES_REDIRECT+"&includeViewParams=true&msg=$success_You_successfully_commented_on_the_event.");
	}
	return ("Events.xhtml"+ControllerEventData.FACES_REDIRECT+"&msg=The_system_could_not_find_the_event.");
    }

    //getters-setters
    public boolean isIsNotPast(){
	return isNotPast;
    }

    public void setIsNotPast(boolean isNotPast){
	this.isNotPast=isNotPast;
    }

    public long getEvent_id(){
	return event_id;
    }

    public void setEvent_id(long event_id){
	this.event_id=event_id;
    }

    public Event getCurrentEvent(){
	return currEvent;
    }

    public boolean isIsAttendingEvent(){
	return isAttendingEvent;
    }

    public void setIsAttendingEvent(boolean isAttendingEvent){
	this.isAttendingEvent=isAttendingEvent;
    }

    public String getNewComment(){
	return NewComment;
    }

    public void setNewComment(String NewComment){
	this.NewComment=NewComment;
    }
}
