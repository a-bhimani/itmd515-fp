package edu.iit.sat.itmd4515.abhimani.fp.web.administration.users;

import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.Authentication;
import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.StudentLogin;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvAuthRoles;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvAuthUsers;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvStudents;
import edu.iit.sat.itmd4515.abhimani.fp.web.AbstractControllerExt;
import edu.iit.sat.itmd4515.abhimani.fp.web.cdi.BeanEmailer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
public class ControllerProcessUsers
	extends AbstractControllerExt{
    private static final Logger LOG=Logger.getLogger(ControllerProcessUsers.class.getName());

    @Inject
    BeanEmailer emailer;

    @EJB
    SrvStudents srvStudent;

    @EJB
    SrvAuthUsers srvAuthUsers;

    @EJB
    SrvAuthRoles srvAuthRoles;

    private int intRole;

    @NotNull
    @Size(min = 2)
    private String strUsername;

    private String strEmailID;

    private String strEmail;

    @NotNull
    @Size(min = 2)
    private String strFName;

    @NotNull
    @Size(min = 2)
    private String strLName;

    private char chrGender='M';

    @NotNull
    @Min(1000000000)
    private long intPhone=0;

    private String strNewPassword;

    private String strSpecial;

    private Student updStud;
    private long student_id;
    private String strEditSpecial;

    //pre-inits
    @PostConstruct
    @Override
    @SuppressWarnings("empty-statement")
    protected void triggerControllerInit(){
	super.triggerControllerInit();
    }

    @SuppressWarnings("empty-statement")
    public ControllerProcessUsers(){
	;
    }

    //method-calls
    @SuppressWarnings("empty-statement")
    public void pre_load(){
	try{
	    this.updStud=srvStudent.findByID(student_id);
	    //this.student_id=updStud.getPID();
	    this.strUsername=updStud.getAuth().getUsername();
	    this.strFName=updStud.getFName();
	    this.strLName=updStud.getLName();
	    this.strEmail=updStud.getEmailID();
	    this.chrGender=updStud.getGender();
	    this.intPhone=updStud.getPhone();
	    this.strEditSpecial=updStud.getSpecial();
	    try{
		if(updStud.equals(null))
		    ctxFaces.getExternalContext().redirect("Welcome.xhtml?msg=The_system_could_not_find_the_Student.");
	    }catch(Exception ex){
		try{
		    ctxFaces.getExternalContext().redirect("Welcome.xhtml?msg=The_system_could_not_find_the_Student.");;
		}catch(Exception iex){
		    ;
		}
	    }
	}catch(Exception ex){
	    ;
	}
    }

    @SuppressWarnings("empty-statement")
    public String createStudent(){
	try{
	    if(Integer.parseInt(this.ctxFaces.getExternalContext().getRequestParameterMap().get("frmChk"))==1){
		setStrEmailID(strUsername);
		LOG.log(Level.INFO, "BEGIN: CREATING NEW STUDENT - {0}", strEmailID);
		LOG.log(Level.INFO, "BEGIN: UDPATING THE STUDENT CREDENTIALS - {0}", strUsername);
		Student newStudent=new Student(strFName, strLName, chrGender, intPhone, strEmailID, true, strSpecial);
		StudentLogin auth=new StudentLogin(strUsername);
		auth.setPassword(strNewPassword);
		newStudent.setAuth(auth);
		try{
		    srvStudent.create(newStudent);
		    try{
			emailer.sendMail(newStudent.getEmailID(), "Student Account created successfully.", "Dear "+newStudent.getFName()+" "+newStudent.getLName()+", Your account has successfully been created on the Events Portal. Please use your Email ID/Username and password to log in to the portal. Your password is - "+strNewPassword+". Please change your password upon successful login for the first time.");
		    }catch(Exception ex){
			;
		    }
		}catch(Exception ex){
		    return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=A_Student_with_the_Username_already_exists.");
		}
		LOG.log(Level.INFO, "END: CREATING NEW STUDENT - {0}", strEmailID);
		LOG.log(Level.INFO, "END: UDPATING THE STUDENT CREDENTIALS - {0}", strUsername);
		//email that new credentials have been created
		return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=$success_The_new_Student_has_been_created_successfully_and_the_credentials_have_been_emailed_to_the_Student.");
	    }
	}catch(Exception ex){
	    ;
	}
	return ("Student_Profile.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=Insufficient_data_to_add_a_new_Student.");
    }

    @SuppressWarnings("empty-statement")
    public String createAdministrator(){
	try{
	    if(Integer.parseInt(this.ctxFaces.getExternalContext().getRequestParameterMap().get("frmChk"))==1){
		LOG.log(Level.INFO, "BEGIN: CREATING NEW ADMINISTRATOR - {0}", strUsername);
		Authentication adminUser=new Authentication(strUsername);
		adminUser.setPassword(strNewPassword);
		switch(intRole){
		    case 0:
			adminUser.grant(srvAuthRoles.findByName("SUPER_ADMIN"));
			break;
		    case 1:
			adminUser.grant(srvAuthRoles.findByName("DEPT_ADMIN"));
			break;
		    case 2:
			adminUser.grant(srvAuthRoles.findByName("VENUE_ADMIN"));
			break;
		}
		try{
		    srvAuthUsers.create(adminUser);
		}catch(Exception ex){
		    return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=An_Administrator_with_the_Username_already_exists.");
		}
		LOG.log(Level.INFO, "END: CREATING NEW ADMINISTRATOR - {0}", strUsername);
		return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=$success_The_new_Administrator_has_been_created_successfully.");
	    }
	}catch(Exception ex){
	    ;
	}
	return ("Add_Administrator.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=Insufficient_data_to_add_a_new_Administrator.");
    }

    public String updateRemark(){
	if(student_id>0){
	    this.updStud=srvStudent.findByID(student_id);
	    LOG.log(Level.INFO, "BEGIN: UPDATING STUDENT REMARKS", updStud.toString());
	    updStud.setSpecial(strEditSpecial);
	    srvStudent.update(updStud);
	    LOG.log(Level.INFO, "END: UPDATING STUDENT REMARKS", updStud.toString());
	    return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&includeViewParams=true&msg=$success_You_successfully_updated_the_Student_Remark.");
	}
	return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=The_system_could_not_find_the_Student.");
    }

    //getters-setters
    public int getIntRole(){
	return intRole;
    }

    public void setIntRole(int intRole){
	this.intRole=intRole;
    }

    public String getStrUsername(){
	return strUsername;
    }

    public void setStrUsername(String strUsername){
	this.strUsername=strUsername;
    }

    public String getStrSpecial(){
	return strSpecial;
    }

    public void setStrSpecial(String strSpecial){
	this.strSpecial=strSpecial;
    }

    public void setStrEmailID(String Username){
	this.strEmailID=(Username+"@hawk.iit.edu");
    }

    public String getStrFName(){
	return strFName;
    }

    public void setStrFName(String strFName){
	this.strFName=strFName;
    }

    public String getStrLName(){
	return strLName;
    }

    public void setStrLName(String strLName){
	this.strLName=strLName;
    }

    public char getChrGender(){
	return chrGender;
    }

    public void setChrGender(char chrGender){
	this.chrGender=chrGender;
    }

    public long getIntPhone(){
	return intPhone;
    }

    public void setIntPhone(long intPhone){
	this.intPhone=intPhone;
    }

    public void setStrNewPassword(String strNewPassword){
	this.strNewPassword=strNewPassword;
    }

    public String getStrNewPassword(){
	return strNewPassword;
    }

    public String getStrEditSpecial(){
	return strEditSpecial;
    }

    public void setStrEditSpecial(String strEditSpecial){
	this.strEditSpecial=strEditSpecial;
    }

    public long getStudent_id(){
	return student_id;
    }

    public void setStudent_id(long student_id){
	this.student_id=student_id;
    }

    public String getStrEmail(){
	return strEmail;
    }

    public void setStrEmail(String strEmail){
	this.strEmail=strEmail;
    }
}
