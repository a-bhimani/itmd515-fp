package edu.iit.sat.itmd4515.abhimani.fp.domain.entities;

import edu.iit.sat.itmd4515.abhimani.fp.domain.AbstractEntityUnit;
import edu.iit.sat.itmd4515.abhimani.fp.domain.relations.DepartmentOffice;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Entity
@Table(name="departments")
@NamedQueries({
    @NamedQuery(name="Departments.retrieveAll", query="SELECT d FROM Department AS d"),
    @NamedQuery(name="Departments.findByID", query="SELECT d FROM Department AS d WHERE d.PID=:PID"),
    @NamedQuery(name="Departments.findByName", query="SELECT d FROM Department AS d WHERE d.Name=:Name")
})
public class Department
	extends AbstractEntityUnit
	implements Comparable<Department>, Serializable{
    //COLUMNS
    @Column(name="Dept_Name", nullable=false, length=255, unique=true)
    @Size(min=4)
    private String Name;

    @Column(name="Description", length=2000)
    private String Description;

    @OneToMany(cascade=CascadeType.REMOVE, mappedBy="Dept")
    private List<DepartmentOffice> lstOffices;

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="Dept")
    private List<Event> lstEvents;

    //CONSTRUCTS
    public Department(){
	super();
    }

    public Department(String Name, String Description){
	this.Name=Name.trim();
	this.Description=Description.trim();
    }

    public Department(String Name){
	this.Name=Name.trim();
    }

    //PROPERTIES
    public String getName(){
	return this.Name;
    }

    public void setName(String Name){
	this.Name=Name.trim();
    }

    public String getDescription(){
	return this.Description;
    }

    public void setDescription(String Description){
	this.Description=Description.trim();
    }

    //RELATIONS
    /**
     * Holds a list of offices that belong to the Department.
     *
     * @return Returns a list of Offices under the Department
     */
    public List<DepartmentOffice> getDepartmentOfficesList(){
	return lstOffices;
    }

    public void addDepartmentOffice(DepartmentOffice office){
	if(!this.getDepartmentOfficesList().contains(office))
	    this.lstOffices.add(office);
    }

    /**
     * Holds a list of events held by the department or will be held.
     *
     * @return Returns a list of Events held by the Department
     */
    public List<Event> getDepartmentEvents(){
	return lstEvents;
    }

    //IMPLEMENTATION
    /**
     * CompareTo implemented on the Name of the Department which is unqiue.
     *
     * @param el Expects an element of type Department
     */
    @Override
    public int compareTo(Department el){
	return (this.getName().compareTo(el.getName()));
    }

    //OVERRIDES
    @Override
    public boolean equals(Object el){
	if(!(el instanceof Department))
	    return false;
	try{
	    Department tstDept=(Department)el;
	    if(!(Integer.compare(this.hashCode(), tstDept.hashCode())==0))
		return false;
	}catch(Exception ex){
	    return false;
	}
	return true;
    }

    @Override
    public String toString(){
	try{
	    return ("/Entities.Department{ID:"+this.getPID()+", Name:\""+this.getName()+"\", Description:{Length:"+((this.getDescription()==null) ? 0 : this.getDescription().length())+"}, Offices:"+this.getDepartmentOfficesList().size()+", Events:"+this.getDepartmentEvents().size()+"}");
	}catch(Exception ex){
	    return ex.toString();
	}
    }
}
