package edu.iit.sat.itmd4515.abhimani.fp.service.units;

import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.StudentLogin;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.service.AbstractService;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Stateless
public class SrvStudents
	extends AbstractService<Student>{
    public SrvStudents(){
	super(Student.class);
    }

    @Override
    public List<Student> retrieveAll(){
	return super.em.createNamedQuery("Students.retrieveAll", Student.class).getResultList();
    }

    @Override
    public Student findByID(long ID){
	return super.em.createNamedQuery("Students.findByID", Student.class).setParameter("PID", ID).getSingleResult();
    }

    public Student findByNumber(String uid){
	return super.em.createNamedQuery("Students.findByNumber", Student.class).setParameter("Number", uid).getSingleResult();
    }

    public Student findByUsername(String username){
	try{
	    return this.findByID(super.em.createNamedQuery("StudentLogin.authenticate", StudentLogin.class).setParameter("uname", username).getSingleResult().getLoginID());
	}catch(Exception ex){
	    return null;
	}
    }

    public Student findByEmailID(String emailID){
	try{
	    return super.em.createNamedQuery("Students.findByEmailID", Student.class).setParameter("EmailID", emailID).getSingleResult();
	}catch(Exception ex){
	    return null;
	}
    }

    public void updateCredentials(StudentLogin auth){
	Student thisObj=this.findByUsername(auth.getUsername());
	thisObj.setAuth(auth);
	this.em.merge(auth);
	this.update(thisObj);
    }
}
