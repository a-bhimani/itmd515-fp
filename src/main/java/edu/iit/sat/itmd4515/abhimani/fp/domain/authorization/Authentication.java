package edu.iit.sat.itmd4515.abhimani.fp.domain.authorization;

import edu.iit.sat.itmd4515.abhimani.fp.EncryptText;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Entity
@Table(name = "auth_administrators")
@NamedQueries({
    @NamedQuery(name = "Users.retrieveAll", query = "SELECT u FROM Authentication AS u"),
    @NamedQuery(name = "Users.findByID", query = "SELECT u FROM Authentication AS u WHERE u.LoginID=:PID"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Authentication AS u WHERE u.Username=:Username")
})
public class Authentication
	implements Serializable{
    //COLUMNS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected int LoginID;

    @Column(length = 255, unique = true, updatable = false)
    @Size(min = 2)
    private String Username;

    @Column(name = "enc_pwd", length = 64)
    @Size(min = 8, max = 40)
    private String Password;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(
	    name = "auth_designations",
	    joinColumns = {
		@JoinColumn(name = "Login_ID", referencedColumnName = "LoginID")},
	    inverseJoinColumns = {
		@JoinColumn(name = "Role_ID", referencedColumnName = "PID")})
    private List<Role> lstRoles=new ArrayList<>();

    //CONSTRUCT
    private Authentication(){
    }

    public Authentication(String Username){
	this.Username=Username;
    }

    //PROPERTIES
    public String getUsername(){
	return this.Username;
    }

    public String getPasswordKey(){
	return this.Password;
    }

    /**
     * Password is sha256ed and saved.
     *
     */
    @PrePersist
    @PreUpdate
    private void cryptPassword(){
	this.Password=EncryptText.crypt(this.Password);
    }

    public void setPassword(String Password){
	this.Password=Password.trim();
    }

    /**
     * Can be used to authenticate the user.
     *
     *
     * @param Password Expects a Administrator password
     *
     * @return Returns a Boolean True or False
     */
    public boolean matchLogin(String Password){
	return EncryptText.hashEquals(this.getPasswordKey(), Password);
    }

    //RELATIONS
    /**
     * Holds a list of privileges held by the User.
     *
     * @return Returns a list of Roles held by the authenticated Administrator
     * User
     */
    public long getPID(){
	return this.LoginID;
    }

    public long getID(){
	return this.LoginID;
    }

    public List<Role> getRoles(){
	return lstRoles;
    }

    public void grant(Role role){
	if(!this.getRoles().contains(role))
	    this.lstRoles.add(role);
	if(!role.getUsers().contains(this))
	    role.addUser(this);
    }

    public void revoke(Role role){
	if(this.getRoles().contains(role))
	    this.lstRoles.remove(role);
	if(role.getUsers().contains(this))
	    role.removeUser(this);
    }

    //OVERRIDES
    @Override
    public int hashCode(){
	return 0;
    }

    @Override
    public boolean equals(Object el){
	if(!(el instanceof Authentication))
	    return false;
	try{
	    Authentication tstLogin=(Authentication)el;
	    if(!(Integer.compare(this.hashCode(), tstLogin.hashCode())==0))
		return false;
	}catch(Exception ex){
	    return false;
	}
	return true;
    }

    @Override
    public String toString(){
	try{
	    return ("NOT_AUTHORIZED");
	}catch(Exception ex){
	    return ex.toString();
	}
    }
}
