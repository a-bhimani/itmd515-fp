package edu.iit.sat.itmd4515.abhimani.fp.web.administration.departments;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Department;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvDepartments;
import edu.iit.sat.itmd4515.abhimani.fp.web.AbstractControllerExt;
import edu.iit.sat.itmd4515.abhimani.fp.web.administration.users.ControllerProcessUsers;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Named
@RequestScoped
public class ControllerProcessDepartment
	extends AbstractControllerExt{
    private static final Logger LOG=Logger.getLogger(ControllerProcessDepartment.class.getName());

    @EJB
    SrvDepartments srvDepartments;

    private int department_id;

    private Department updDept;

    @NotNull
    @Size(min = 2)
    private String strName;

    @NotNull
    private String strDescription;

    //pre-inits
    @PostConstruct
    @Override
    @SuppressWarnings("empty-statement")
    protected void triggerControllerInit(){
	super.triggerControllerInit();
    }

    //method-calls
    @SuppressWarnings("empty-statement")
    public ControllerProcessDepartment(){
	;
    }

    @SuppressWarnings("empty-statement")
    public void pre_load(){
	try{
	    this.updDept=srvDepartments.findByID(department_id);
	    this.strName=this.updDept.getName();
	    this.strDescription=this.updDept.getDescription();
	    try{
		if(updDept.equals(null))
		    ctxFaces.getExternalContext().redirect("Welcome.xhtml?msg=The_system_could_not_find_the_Department.");
	    }catch(Exception ex){
		try{
		    ctxFaces.getExternalContext().redirect("Welcome.xhtml?msg=The_system_could_not_find_the_Department.");;
		}catch(Exception iex){
		    ;
		}
	    }
	}catch(Exception ex){
	    ;
	}
    }

    @SuppressWarnings("empty-statement")
    public String manipulateDepartment(){
	try{
	    if(Integer.parseInt(this.ctxFaces.getExternalContext().getRequestParameterMap().get("frmChk"))==1)
		if(department_id>0){
		    updDept=srvDepartments.findByID(department_id);
		    LOG.log(Level.INFO, "BEGIN: UPDATING DEPARTMENT - {0}", updDept.toString());
		    updDept.setName(strName);
		    updDept.setDescription(strDescription);
		    try{
			srvDepartments.update(updDept);
		    }catch(Exception ex){
			return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=A_Department_with_the_same_Name_already_exists.");
		    }
		    LOG.log(Level.INFO, "END: UPDATING DEPARTMENT - {0}", updDept.toString());
		    return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=$success_The_Department_has_been_updated_successfully.");
		}else{
		    LOG.log(Level.INFO, "BEGIN: CREATING NEW DEPARTMENT - {0}", strName);
		    Department newDept=new Department(strName, strDescription);
		    try{
			srvDepartments.create(newDept);
		    }catch(Exception ex){
			return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=A_Department_with_the_same_Name_already_exists.");
		    }
		    LOG.log(Level.INFO, "END: CREATING NEW DEPARTMENT - {0}", strName);
		    //email that new credentials have been created
		    return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=$success_The_new_Department_has_been_added_successfully.");
		}
	}catch(Exception ex){
	    LOG.log(Level.SEVERE, "ERROR: MANIPULATING VENUES - {0}", strName);;
	}
	return ("Manage_Department.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=Insufficient_data_to_add_a_new_Department.");
    }

    //getters-setters
    public int getDepartment_id(){
	return department_id;
    }

    public void setDepartment_id(int department_id){
	this.department_id=department_id;
    }

    public String getStrName(){
	return strName;
    }

    public void setStrName(String strName){
	this.strName=strName;
    }

    public String getStrDescription(){
	return strDescription;
    }

    public void setStrDescription(String strDescription){
	this.strDescription=strDescription;
    }
}
