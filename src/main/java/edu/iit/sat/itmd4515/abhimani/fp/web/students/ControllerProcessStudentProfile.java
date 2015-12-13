package edu.iit.sat.itmd4515.abhimani.fp.web.students;

import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.StudentLogin;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvStudents;
import edu.iit.sat.itmd4515.abhimani.fp.web.AbstractControllerExt;
import edu.iit.sat.itmd4515.abhimani.fp.web.administration.users.ControllerProcessUsers;
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
public class ControllerProcessStudentProfile
	extends AbstractControllerExt{
    private static final Logger LOG=Logger.getLogger(ControllerProcessStudentProfile.class.getName());

    @EJB
    SrvStudents srvStudent;

    @Inject
    BeanEmailer emailer;

    private Student currStudent;

    private String strStudent_Number;

    public String getStrEmailID(){
	return strEmailID;
    }

    private String strEmailID;

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

    private boolean blnNotifyEvents;

    private boolean blnChangePwd=false;

    private String strNewPassword;

    //pre-inits
    @PostConstruct
    @Override
    protected void triggerControllerInit(){
	super.triggerControllerInit();
	this.currStudent=srvStudent.findByUsername(ctxFaces.getExternalContext().getSessionMap().get("auth_username").toString());
	this.strStudent_Number=currStudent.getStudent_Number();
	this.strEmailID=currStudent.getEmailID();
	this.strFName=currStudent.getFName();
	this.strLName=currStudent.getLName();
	this.chrGender=currStudent.getGender();
	this.intPhone=currStudent.getPhone();
	this.blnNotifyEvents=currStudent.isNotifyEvents();
    }

    public ControllerProcessStudentProfile(){
    }

    //method-calls
    @SuppressWarnings("empty-statement")
    public String updateProfile(){
	try{
	    if(Integer.parseInt(this.ctxFaces.getExternalContext().getRequestParameterMap().get("frmChk"))==1){
		LOG.log(Level.INFO, "BEGIN: UPDATING THE STUDENT PROFILE - {0}", currStudent.toString());
		this.currStudent.setFName(strFName);
		this.currStudent.setLName(strLName);
		this.currStudent.setGender(chrGender);
		this.currStudent.setPhone(intPhone);
		this.currStudent.setNotifyEvents(blnNotifyEvents);
		srvStudent.update(currStudent);
		LOG.log(Level.INFO, "END: UPDATING THE STUDENT PROFILE - {0}", currStudent.toString());
		if(blnChangePwd){
		    LOG.log(Level.INFO, "BEGIN: UDPATING THE STUDENT CREDENTIALS - {0}", currStudent.getAuth().toString());
		    StudentLogin auth=currStudent.getAuth();
		    auth.setPassword(strNewPassword);
		    srvStudent.updateCredentials(auth);
		    LOG.log(Level.INFO, "END: UDPATING THE STUDENT CREDENTIALS - {0}", currStudent.getAuth().toString());
		    try{
			emailer.sendMail(currStudent.getEmailID(), "Password changed successfully.", "Your password was changed successfully at the time you are receiving this email.");
		    }catch(Exception ex){
			;
		    }
		}
		return ("Profile.xhtml"+ControllerProcessStudentProfile.FACES_REDIRECT+"&msg=$success_Your_profile_has_been_updated_successfully.");
	    }
	}catch(Exception ex){
	    ;
	}
	return ("Profile.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=Insufficient_data_to_update_the_Profile.");
    }
    //getters-setters

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

    public boolean isBlnNotifyEvents(){
	return blnNotifyEvents;
    }

    public void setBlnNotifyEvents(boolean blnNotifyEvents){
	this.blnNotifyEvents=blnNotifyEvents;
    }

    public String getStrStudent_Number(){
	return strStudent_Number;
    }

    public boolean isBlnChangePwd(){
	return blnChangePwd;
    }

    public void setBlnChangePwd(boolean blnChangePwd){
	this.blnChangePwd=blnChangePwd;
    }

    public void setStrNewPassword(String strNewPassword){
	this.strNewPassword=strNewPassword;
    }

    public String getStrNewPassword(){
	return strNewPassword;
    }
}
