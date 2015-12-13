package edu.iit.sat.itmd4515.abhimani.fp.web.administration.venues;

import edu.iit.sat.itmd4515.abhimani.fp.US_States;
import edu.iit.sat.itmd4515.abhimani.fp.domain.Venue_Type;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Venue;
import edu.iit.sat.itmd4515.abhimani.fp.service.units.SrvVenues;
import edu.iit.sat.itmd4515.abhimani.fp.web.AbstractControllerExt;
import edu.iit.sat.itmd4515.abhimani.fp.web.administration.users.ControllerProcessUsers;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Named
@RequestScoped
public class ControllerProcessVenue
	extends AbstractControllerExt{
    private static final Logger LOG=Logger.getLogger(ControllerProcessVenue.class.getName());

    @EJB
    SrvVenues srvVenue;

    private Venue updVenue;

    private long venue_id;

    @NotNull
    @Size(min = 2)
    private String strTitle;

    @NotNull
    @Size(min = 2)
    private String strAddr1;

    private String strAddr2;

    @NotNull
    @Size(min = 3)
    private String strCity;

    @NotNull
    @Min(1000)
    @Max(99999)
    private int strZip;

    private int strZipExt;

    //pre-inits
    @PostConstruct
    @Override
    @SuppressWarnings("empty-statement")
    protected void triggerControllerInit(){
	super.triggerControllerInit();
    }

    //method-calls
    @SuppressWarnings("empty-statement")
    public ControllerProcessVenue(){
	;
    }

    @SuppressWarnings("empty-statement")
    public void pre_load(){
	try{
	    this.updVenue=srvVenue.findByID(venue_id);
	    this.strTitle=updVenue.getTitle();
	    this.strAddr1=updVenue.getAddr1();
	    this.strAddr2=updVenue.getAddr2();
	    this.strCity=updVenue.getCity();
	    this.strZip=updVenue.getRawZip();
	    this.strZipExt=updVenue.getZip_Ext();
	    try{
		if(updVenue.equals(null))
		    ctxFaces.getExternalContext().redirect("Welcome.xhtml?msg=The_system_could_not_find_the_Venue.");
	    }catch(Exception ex){
		try{
		    ctxFaces.getExternalContext().redirect("Welcome.xhtml?msg=The_system_could_not_find_the_Venue.");;
		}catch(Exception iex){
		    ;
		}
	    }
	}catch(Exception ex){
	    ;
	}
    }

    @SuppressWarnings("empty-statement")
    public String manipulateVenue(){
	try{
	    if(Integer.parseInt(this.ctxFaces.getExternalContext().getRequestParameterMap().get("frmChk"))==1)
		if(venue_id>0){
		    updVenue=srvVenue.findByID(venue_id);
		    LOG.log(Level.INFO, "BEGIN: UPDATING VENUE - {0}", updVenue.toString());
		    updVenue.setTitle(strTitle);
		    updVenue.setAddr1(strAddr1);
		    updVenue.setAddr2(strAddr2);
		    updVenue.setCity(strCity);
		    updVenue.setZip(strZip);
		    updVenue.setZip_Ext(strZipExt);
		    try{
			srvVenue.update(updVenue);
		    }catch(Exception ex){
			return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=A_Venue_with_the_same_Title_already_exists.");
		    }
		    LOG.log(Level.INFO, "END: UPDATING VENUE - {0}", updVenue.toString());
		    return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=$success_The_Venue_has_been_updated_successfully.");
		}else{
		    LOG.log(Level.INFO, "BEGIN: CREATING NEW VENUE - {0}", strTitle);
		    Venue newVenue=new Venue(strTitle, Venue_Type.Open, strAddr1, strAddr2, strCity, US_States.IL, strZip, strZipExt);
		    try{
			srvVenue.create(newVenue);
		    }catch(Exception ex){
			return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=A_Venue_with_the_same_Title_already_exists.");
		    }
		    LOG.log(Level.INFO, "END: CREATING NEW VENUE - {0}", strTitle);
		    //email that new credentials have been created
		    return ("Welcome.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=$success_The_new_Venue_has_been_added_successfully.");
		}
	}catch(Exception ex){
	    LOG.log(Level.SEVERE, "ERROR: MANIPULATING VENUES - {0}", strTitle);;
	}
	return ("Manage_Venue.xhtml"+ControllerProcessUsers.FACES_REDIRECT+"&msg=Insufficient_data_to_add_a_new_Venue.");
    }

    //getters-setters
    /**
     *
     * @return Returns Venue Id
     */
    public long getVenue_id(){
	return venue_id;
    }

    public void setVenue_id(long venue_id){
	this.venue_id=venue_id;
    }

    public String getStrTitle(){
	return strTitle;
    }

    public void setStrTitle(String strTitle){
	this.strTitle=strTitle;
    }

    public String getStrAddr1(){
	return strAddr1;
    }

    public void setStrAddr1(String strAddr1){
	this.strAddr1=strAddr1;
    }

    public String getStrAddr2(){
	return strAddr2;
    }

    public void setStrAddr2(String strAddr2){
	this.strAddr2=strAddr2;
    }

    public String getStrCity(){
	return strCity;
    }

    public void setStrCity(String strCity){
	this.strCity=strCity;
    }

    public int getStrZip(){
	return strZip;
    }

    public void setStrZip(int strZip){
	this.strZip=strZip;
    }

    public int getStrZipExt(){
	return strZipExt;
    }

    public void setStrZipExt(int strZipExt){
	this.strZipExt=strZipExt;
    }
}
