package edu.iit.sat.itmd4515.abhimani.fp.domain.authorization;

import edu.iit.sat.itmd4515.abhimani.fp.EncryptText;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="auth_students_login")
@NamedQueries({
    @NamedQuery(name="StudentLogin.authenticate", query="SELECT l FROM StudentLogin AS l WHERE l.Username=:uname AND l.Password IS NOT NULL")
})
public class StudentLogin
	implements Comparable<StudentLogin>, Serializable{
    //COLUMNS
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable=false)
    protected long LoginID;

    @Column(length=255, unique=true, updatable=false)
    @Size(min=4)
    private String Username;

    @Column(name="enc_pwd", length=64)
    @Size(min=8, max=40)
    private String Password;

    //CONSTRUCT
    private StudentLogin(){
	super();
    }

    /**
     * You must not initialize and use an instance of this class with the
     * default constructor, instead use the Student.getAuth() and
     * Student.setAuth(StudentLogin auth) methods to access or update the
     * credentials; Use this constructor only to use the matchLogin(String
     * Password) and authenticate the user.
     *
     * @param Username Expects a Username to initialize the class
     */
    public StudentLogin(String Username){
	this.Username=Username.trim();
    }

    //PROPERTIES
    public long getLoginID(){
	return this.LoginID;
    }

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
     * @param Password Expects Student password for matching
     *
     * @return A Boolean True or False
     */
    public boolean matchLogin(String Password){
	return EncryptText.hashEquals(this.getPasswordKey(), Password);
    }

    //IMPLEMENTATION
    /**
     * CompareTo implemented on the Username of the Student which is unqiue.
     *
     * @param el Expects an element of type StudentLogin
     */
    @Override
    public int compareTo(StudentLogin el){
	return (this.getUsername().compareTo(el.getUsername()));
    }

    //OVERRIDES
    @Override
    public int hashCode(){
	return ((this.getLoginID()>0) ? Long.hashCode(this.getLoginID()) : 0);
    }

    @Override
    public boolean equals(Object el){
	if(!(el instanceof StudentLogin))
	    return false;
	try{
	    StudentLogin tstLogin=(StudentLogin)el;
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
