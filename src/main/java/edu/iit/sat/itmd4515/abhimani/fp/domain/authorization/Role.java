package edu.iit.sat.itmd4515.abhimani.fp.domain.authorization;

import edu.iit.sat.itmd4515.abhimani.fp.domain.AbstractEntityUnit;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Entity
@Table(name="auth_roles")
@NamedQueries({
    @NamedQuery(name="Roles.retrieveAll", query="SELECT r FROM Role AS r"),
    @NamedQuery(name="Roles.findByID", query="SELECT r FROM Role AS r WHERE r.PID=:PID"),
    @NamedQuery(name="Roles.findByName", query="SELECT r FROM Role AS r WHERE r.Name=:Name")
})
public class Role
	extends AbstractEntityUnit
	implements Comparable<Role>, Serializable{
    //COLUMNS
    @Column(name="Role_Name", nullable=false, length=255, unique=true)
    @Size(min=4)
    private String Name;

    @Column(name="Description", length=2000)
    private String Description;

    @ManyToMany(mappedBy="lstRoles")
    private List<Authentication> lstUsers;

    //CONSTRUCTS
    public Role(){
	super();
    }

    public Role(String Name, String Description){
	this.Name=Name.trim();
	this.Description=Description.trim();
    }

    public Role(String Name){
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
    public List<Authentication> getUsers(){
	return lstUsers;
    }

    public void addUser(Authentication user){
	if(!this.getUsers().contains(user))
	    this.lstUsers.add(user);
	if(!user.getRoles().contains(this))
	    user.grant(this);
    }

    public void removeUser(Authentication user){
	if(this.getUsers().contains(user))
	    this.lstUsers.remove(user);
	if(user.getRoles().contains(this))
	    user.revoke(this);
    }

    //IMPLEMENTATION
    /**
     * CompareTo implemented on the Name of the Role which is unqiue.
     *
     * @param el Expects an element of type Role
     */
    @Override
    public int compareTo(Role el){
	return (this.getName().compareTo(el.getName()));
    }

    //OVERRIDES
    @Override
    public boolean equals(Object el){
	if(!(el instanceof Role))
	    return false;
	try{
	    Role tstRole=(Role)el;
	    if(!(Integer.compare(this.hashCode(), tstRole.hashCode())==0))
		return false;
	}catch(Exception ex){
	    return false;
	}
	return true;
    }

    @Override
    public String toString(){
	try{
	    return ("/Authorization.Role{ID:"+this.getPID()+", Name:\""+this.getName()+"\", Description:{Length:"+((this.getDescription()==null) ? 0 : this.getDescription().length())+"}, Users:"+1+"}");
	}catch(Exception ex){
	    return ex.toString();
	}
    }
}
