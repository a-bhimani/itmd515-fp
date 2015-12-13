package edu.iit.sat.itmd4515.abhimani.fp.service;

import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.Authentication;
import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.Role;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvAuthRoles;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvAuthUsers;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Startup
@Singleton
public class DataInitializer{
    @EJB
    SrvAuthRoles srvAuthRoles;

    @EJB
    SrvAuthUsers srvAuthUsers;

    /**
     * ONLY GENERATING SOME SECURITY USERS AND ROLES HERE.
     *
     */
    @PostConstruct
    public void triggerInit(){
	System.out.println("WEBDEV: TRIGGERED PRELOAD SQL SCRIPT TO POPULATE THE DB FOR THIS DOMAIN.");
	Role r1=new Role("VENUE_ADMIN", "These set of users can manage Venues."),
		r2=new Role("DEPT_ADMIN", "These set of users can manage Departments, Department Offices, host and manage an event at a venue."),
		r3=new Role("SUPER_ADMIN", "These users can create Students and manage other entities in the system.");
	Authentication v1=new Authentication("vadmin1"),
		v2=new Authentication("vadmin2"),
		d1=new Authentication("dadmin1"),
		d2=new Authentication("dadmin2"),
		sa=new Authentication("sa");
	v1.setPassword("password1");
	v2.setPassword("password2");
	d1.setPassword("password1");
	d2.setPassword("password2");
	sa.setPassword("password3");
	try{
	    System.out.println("WEBDEV: BEGIN: TRIGGERING ALL ROLES");
	    srvAuthRoles.create(r1);
	    srvAuthRoles.create(r2);
	    srvAuthRoles.create(r3);
	    System.out.println("WEBDEV: END: TRIGGERING ALL ROLES");
	}catch(DatabaseException dex){
	    System.out.println("WEBDEV: THE ROLES ARE PROBABLY ALREADY IN THE DATABASE.");
	}
	v1.grant(r1);
	v2.grant(r1);
	d1.grant(r2);
	d2.grant(r2);
	sa.grant(r3);
	try{
	    System.out.println("WEBDEV: BEGIN: TRIGGERING ALL USERS");
	    srvAuthUsers.create(v1);
	    srvAuthUsers.create(v2);
	    srvAuthUsers.create(d1);
	    srvAuthUsers.create(d2);
	    srvAuthUsers.create(sa);
	    System.out.println("WEBDEV: END: TRIGGERING ALL USERS");
	}catch(DatabaseException dex){
	    System.out.println("WEBDEV: THE ADMIN USERS ARE PROBABLY ALREADY IN THE DATABASE.");
	}
    }
}
