package edu.iit.sat.itmd4515.abhimani.fp.domain.relations;

import edu.iit.sat.itmd4515.abhimani.fp.domain.AbstractEntityUnit;
import edu.iit.sat.itmd4515.abhimani.fp.US_States;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Department;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Entity
@Table(name="department_offices")
@NamedQueries({
    @NamedQuery(name="DepartmentOffices.retrieveAll", query="SELECT do FROM DepartmentOffice AS do"),
    @NamedQuery(name="DepartmentOffices.findByID", query="SELECT do FROM DepartmentOffice AS do WHERE do.PID=:PID"),
    @NamedQuery(name="DepartmentOffices.findByTitle", query="SELECT do FROM DepartmentOffice AS do WHERE do.Title=:Name"),
    @NamedQuery(name="DepartmentOffices.retrieveByDepartment", query="SELECT do FROM DepartmentOffice AS do WHERE do.Dept=:Dept")

})
public class DepartmentOffice
	extends AbstractEntityUnit
	implements Comparable<DepartmentOffice>, Serializable{
    //COLUMNS
    @JoinColumn(name="Office_Dept_ID", referencedColumnName="PID", nullable=false)
    @ManyToOne(optional=false)
    private Department Dept;

    @Column(name="Title", nullable=false, length=255, unique=true)
    @Size(min=4)
    private String Title;

    @Column(name="Addr1", nullable=false, length=255)
    @Size(min=6)
    private String Addr1;

    @Column(name="Addr2", length=255)
    private String Addr2;

    @Column(name="City", nullable=false, length=255)
    @Size(min=6)
    private String City;

    @Column(name="CState", nullable=false, length=255)
    private US_States CState;

    @Column(name="Zip", nullable=false, length=5)
    @Min(1000)
    @Max(99999)
    private int Zip;

    @Column(name="Zip_Ext", length=4)
    @Min(100)
    @Max(9999)
    private int Zip_Ext=0;

    @Column(name="Phone", nullable=false, length=10)
    @Min(1000000000)
    private long Phone=0;

    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."+"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"+"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid Email ID.")
    @Column(name="EmailID", nullable=false, length=255, unique=true)
    private String EmailID;

    //CONSTRUCTS
    public DepartmentOffice(){
	super();
    }

    public DepartmentOffice(String Title, String Addr1, String Addr2, String City, US_States CState, int Zip, int Zip_Ext, long Phone, String EmailID){
	this.Title=Title.trim();
	this.Addr1=Addr1.trim();
	this.Addr2=Addr2.trim();
	this.City=City.trim();
	this.CState=CState;
	this.Zip=Zip;
	this.Zip_Ext=Zip_Ext;
	this.Phone=Phone;
	this.EmailID=EmailID.trim();
    }

    public DepartmentOffice(String Title, String Addr1, String City, US_States CState, int Zip, long Phone, String EmailID){
	this.Title=Title.trim();
	this.Addr1=Addr1.trim();
	this.City=City.trim();
	this.CState=CState;
	this.Zip=Zip;
	this.Phone=Phone;
	this.EmailID=EmailID.trim();
    }

    public DepartmentOffice(Department Dept, String Title, String Addr1, String Addr2, String City, US_States CState, int Zip, int Zip_Ext, long Phone, String EmailID){
	this.Dept=Dept;
	this.Title=Title.trim();
	this.Addr1=Addr1.trim();
	this.Addr2=Addr2.trim();
	this.City=City.trim();
	this.CState=CState;
	this.Zip=Zip;
	this.Zip_Ext=Zip_Ext;
	this.Phone=Phone;
	this.EmailID=EmailID.trim();
    }

    public DepartmentOffice(Department Dept, String Title, String Addr1, String City, US_States CState, int Zip, long Phone, String EmailID){
	this.Dept=Dept;
	this.Title=Title.trim();
	this.Addr1=Addr1.trim();
	this.City=City.trim();
	this.CState=CState;
	this.Zip=Zip;
	this.Phone=Phone;
	this.EmailID=EmailID.trim();
    }

    //PROPERTIES
    public Department getDepartment(){
	return Dept;
    }

    public void setDepartment(Department Dept){
	this.Dept=Dept;
    }

    public String getTitle(){
	return Title;
    }

    public void setTitle(String Title){
	this.Title=Title.trim();
    }

    public String getAddr1(){
	return Addr1;
    }

    public void setAddr1(String Addr1){
	this.Addr1=Addr1.trim();
    }

    public String getAddr2(){
	return Addr2;
    }

    public void setAddr2(String Addr2){
	this.Addr2=Addr2.trim();
    }

    public String getCity(){
	return City;
    }

    public void setCity(String City){
	this.City=City.trim();
    }

    public String getCState(){
	return CState.toString();
    }

    public void setCState(US_States CState){
	this.CState=CState;
    }

    public String getZip(){
	return ((this.Zip_Ext>0) ? (Zip+"-"+Zip_Ext) : Integer.toString(Zip));
    }

    public void setZip(int Zip){
	this.Zip=Zip;
    }

    public int getZip_Ext(){
	return Zip_Ext;
    }

    public void setZip_Ext(int Ext){
	this.Zip_Ext=Ext;
    }

    public long getPhone(){
	return Phone;
    }

    public void setPhone(long Phone){
	this.Phone=Phone;
    }

    public String getEmailID(){
	return EmailID;
    }

    public void setEmailId(String EmailID){
	this.EmailID=EmailID.trim();
    }

    //IMPLEMENTATION
    @Override
    public int compareTo(DepartmentOffice el){
	return (this.getEmailID().compareTo(el.getEmailID()));
    }

    //OVERRIDES
    @Override
    public boolean equals(Object el){
	if(!(el instanceof DepartmentOffice))
	    return false;
	try{
	    DepartmentOffice tstDeptO=(DepartmentOffice)el;
	    if(!(Integer.compare(this.hashCode(), tstDeptO.hashCode())==0))
		return false;
	}catch(Exception ex){
	    return false;
	}
	return true;
    }

    @Override
    public String toString(){
	try{
	    return ("/Relations.DepartmentOffice{ID:"+this.getPID()+", Dept_ID:"+this.getDepartment().getPID()+", Department:\""+this.getDepartment().getName()+"\", Title:\""+this.getTitle()+"\", Address:{Addr1:\""+this.getAddr1()+"\", Addr2:\""+((this.getAddr2()==null) ? "" : this.getAddr2())+"\", City:\""+this.getCity()+"\", State:"+this.getCState()+", Zip:"+this.getZip()+"}, Contact:{Phone:"+this.getPhone()+", EmailID:"+this.getEmailID()+"}}");
	}catch(Exception ex){
	    return ex.toString();
	}
    }
}
