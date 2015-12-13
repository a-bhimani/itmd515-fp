package edu.iit.sat.itmd4515.abhimani.fp.web.administration.users;

import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.Authentication;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvAuthUsers;
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
public class ControllerFetchUsers
	extends AbstractControllerExt{
    private static final Logger LOG=Logger.getLogger(ControllerFetchUsers.class.getName());

    @EJB
    SrvStudents srvStudent;

    @EJB
    private SrvAuthUsers srvAuthUsers;

    private List<Authentication> lstAdministrators=new ArrayList<>();
    private List<Student> lstStudents=new ArrayList<>();

    //pre-inits
    /**
     * The first method for the FaceServlet
     */
    @PostConstruct
    @Override
    @Transactional(TxType.REQUIRES_NEW)
    protected void triggerControllerInit(){
	super.triggerControllerInit();
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF ADMINISTRATORS - {0} ADMINISTRATORS IN TOTAL", srvAuthUsers.retrieveAll().size());
	lstAdministrators=srvAuthUsers.retrieveAll();
	LOG.log(Level.INFO, "END: RETREIVING LIST OF ADMINISTRATORS - {0} ADMINISTRATORS IN TOTAL", srvAuthUsers.retrieveAll().size());
	LOG.log(Level.INFO, "BEGIN: RETREIVING LIST OF STUDENTS - {0} STUDENTS IN TOTAL", srvStudent.retrieveAll().size());
	lstStudents=srvStudent.retrieveAll();
	LOG.log(Level.INFO, "END: RETREIVING LIST OF STUDENTS - {0} STUDENTS IN TOTAL", srvStudent.retrieveAll().size());
    }

    public ControllerFetchUsers(){
    }

    //call refresh somewhere here
    //method-calls
    //getter-setters
    public List<Authentication> getLstAdministrators(){
	return lstAdministrators;
    }

    public void setLstAdministrators(List<Authentication> lstAdministrators){
	this.lstAdministrators=lstAdministrators;
    }

    public List<Student> getLstStudents(){
	return lstStudents;
    }

    public void setLstStudents(List<Student> lstStudents){
	this.lstStudents=lstStudents;
    }
}
