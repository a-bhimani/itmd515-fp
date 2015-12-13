package edu.iit.sat.itmd4515.abhimani.fp.service.units;

import edu.iit.sat.itmd4515.abhimani.fp.domain.EventState;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Department;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Event;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Venue;
import edu.iit.sat.itmd4515.abhimani.fp.service.AbstractService;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Stateless
public class SrvEvents
	extends AbstractService<Event>{
    public SrvEvents(){
	super(Event.class);
    }

    @Override
    public List<Event> retrieveAll(){
	List<Event> lstEvents=super.em.createNamedQuery("Events.retrieveAll", Event.class).getResultList();
	Collections.sort(lstEvents);
	Collections.reverse(lstEvents);
	return lstEvents;
    }

    @Override
    public Event findByID(long ID){
	return super.em.createNamedQuery("Events.findByID", Event.class).setParameter("PID", ID).getSingleResult();
    }

    public List<Event> retrieveByDepartment(Department dept){
	return super.em.createNamedQuery("Events.retrieveByDepartment", Event.class).setParameter("Dept", dept).getResultList();
    }

    public List<Event> retrieveByVenue(Venue ven){
	return super.em.createNamedQuery("Events.retrieveByVenue", Event.class).setParameter("Ven", ven).getResultList();
    }

    //retreive-by-status
    public List<Event> retrieveAll(EventState evs){
	List<Event> lstEvents=new ArrayList<>();
	try{
	    this.retrieveAll().stream().filter((evt)->(evt.getRawAState()==evs)).forEach((evt)->{
		lstEvents.add(evt);
	    });
	}catch(Exception ex){
	    out.println(ex);
	}
	return lstEvents;
    }

    public List<Event> retrieveAllDeclined(){
	return this.retrieveAll(EventState.DECLINED);
    }

    public List<Event> retrieveAllCancelled(){
	return this.retrieveAll(EventState.CANCELLED);
    }

    public List<Event> retrieveAllPending(){
	return this.retrieveAll(EventState.NEW);
    }

    public List<Event> retrieveAllActive(){
	return this.retrieveAll(EventState.APPROVED);
    }

    //retreive-by-criteria
    public List<Event> retrieveAllUpcoming(){
	List<Event> lstEvents=new ArrayList<>();
	try{
	    this.retrieveAllActive().stream().filter((evt)->(evt.getRawEBegin().after(new Date()))).forEach((evt)->{
		lstEvents.add(evt);
	    });
	}catch(Exception ex){
	    out.println(ex);
	}
	return lstEvents;
    }

    public List<Event> retrieveAllAttending(Student stud){
	List<Event> lstEvents=new ArrayList<>();
	try{
	    this.retrieveAllActive().stream().filter((evt)->(evt.getStudentList().contains(stud))).forEach((evt)->{
		lstEvents.add(evt);
	    });
	}catch(Exception ex){
	    out.println(ex);
	}
	return lstEvents;
    }

    public List<Event> retrieveAllPast(){
	List<Event> lstEvents=new ArrayList<>();
	try{
	    this.retrieveAllActive().stream().filter((evt)->(evt.getRawEBegin().before(new Date()))).forEach((evt)->{
		lstEvents.add(evt);
	    });
	}catch(Exception ex){
	    out.println(ex);
	}
	return lstEvents;
    }

    public List<Event> retrieveAllTopActive(){
	List<Event> lstEvents=new ArrayList<>();
	for(Event evt:this.retrieveAllActive())
	    if(!(evt.getStudentList().isEmpty()))
		lstEvents.add(evt);
	Collections.sort(lstEvents, (Event o1, Event o2)->o1.getStudentList().size()-o2.getStudentList().size());
	Collections.reverse(lstEvents);
	return lstEvents;
    }
}
