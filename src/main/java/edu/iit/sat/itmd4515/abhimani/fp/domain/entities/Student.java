package edu.iit.sat.itmd4515.abhimani.fp.domain.entities;

import edu.iit.sat.itmd4515.abhimani.fp.domain.AbstractEntityUnit;
import edu.iit.sat.itmd4515.abhimani.fp.domain.relations.Comment;
import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.StudentLogin;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Entity
@Table(name="students")
@NamedQueries({
    @NamedQuery(name="Students.retrieveAll", query="SELECT s FROM Student AS s"),
    @NamedQuery(name="Students.findByID", query="SELECT s FROM Student AS s WHERE s.PID=:PID"),
    @NamedQuery(name="Students.findByNumber", query="SELECT s FROM Student AS s WHERE s.Student_Number=:Number"),
    @NamedQuery(name="Students.findByEmailID", query="SELECT s FROM Student AS s WHERE s.EmailID=:EmailID")
})
public class Student
	extends AbstractEntityUnit
	implements Comparable<Student>, Serializable{
    //COLUMNS
    @Column(name="Student_Number", nullable=false, length=36, unique=true, updatable=false)
    private final String Student_Number;

    @Column(name="FName", nullable=false, length=255)
    @Size(min=2)
    private String FName;

    @Column(name="LName", nullable=false, length=255)
    @Size(min=2)
    private String LName;

    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."+"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"+"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid Email ID.")
    @Column(name="EmailID", nullable=false, length=255, unique=true)
    private String EmailID;

    @Column(name="Gender", nullable=false)
    private char Gender='M'; //M, F

    @Column(name="Phone", nullable=false, length=10)
    @Min(1000000000)
    private long Phone=0;

    @Column(name="NotifyEvents", nullable=false)
    private boolean NotifyEvents;

    @Column(name="Special", length=2000)
    private String Special;

    @OneToOne(cascade=CascadeType.PERSIST, optional=false, fetch=FetchType.LAZY, orphanRemoval=false)
    @JoinColumn(name="LoginID", unique=true, nullable=false, updatable=false)
    private StudentLogin auth;

    @OneToMany(cascade=CascadeType.REMOVE, mappedBy="Stud")
    private List<Comment> lstComments;

    @ManyToMany(cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JoinTable(
	    name="event_student_attends",
	    joinColumns={
		@JoinColumn(name="Student_ID", referencedColumnName="PID")},
	    inverseJoinColumns={
		@JoinColumn(name="Event_ID", referencedColumnName="PID")})
    private List<Event> lstEvents=new ArrayList<>();

    //CONSTRUCTS
    public Student(){
	super();
	this.Student_Number=UUID.randomUUID().toString();
    }

    public Student(String FName, String LName, char Gender, long Phone, String EmailID, boolean NotifyEvents, String Special){
	this.Student_Number=UUID.randomUUID().toString();
	this.FName=FName.trim();
	this.LName=LName.trim();
	this.Gender=Gender;
	this.Phone=Phone;
	this.EmailID=EmailID.trim();
	this.NotifyEvents=NotifyEvents;
	this.Special=Special.trim();
    }

    public Student(String FName, String LName, char Gender, long Phone, String EmailID, boolean NotifyEvents){
	this.Student_Number=UUID.randomUUID().toString();
	this.FName=FName.trim();
	this.LName=LName.trim();
	this.Gender=Gender;
	this.Phone=Phone;
	this.EmailID=EmailID.trim();
	this.NotifyEvents=NotifyEvents;
    }

    //PROPERTIES
    /**
     * Has 1-1 mapping with the StudentLogin and hold the login credentials.
     *
     * @return Returns the StudentLogin for authentication of a Student
     */
    public StudentLogin getAuth(){
	return this.auth;
    }

    /**
     * Can be used to update the Student credentials.
     *
     * @param auth Expects an element of type StudentLogin for the authenticated
     * Student
     */
    public void setAuth(StudentLogin auth){
	this.auth=auth;
    }

    /**
     * Every Student is assigned a permanent 36-char Unique ID upon creation,
     * the field not being updatable.
     *
     * @return Returns A Unique Number assigned to the Student
     */
    public String getStudent_Number(){
	return Student_Number;
    }

    public String getFName(){
	return FName;
    }

    public void setFName(String FName){
	this.FName=FName.trim();
    }

    public String getLName(){
	return LName;
    }

    public void setLName(String LName){
	this.LName=LName.trim();
    }

    public char getGender(){
	return Gender;
    }

    public void setGender(char Gender){
	this.Gender=Gender;
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

    /**
     * A flag to hold whether a student can be notified of any event.
     *
     * @return Returns True or False if the Student can be notified for Events
     */
    public boolean isNotifyEvents(){
	return NotifyEvents;
    }

    public void setNotifyEvents(boolean NotifyEvents){
	this.NotifyEvents=NotifyEvents;
    }

    public String getSpecial(){
	return Special;
    }

    /**
     * To hold any special note of the student.
     *
     * @param Special Expects an Special Administrative Note of the Student if any
     */
    public void setSpecial(String Special){
	this.Special=Special.trim();
    }

    //RELATIONS
    /**
     * Holds a list of events the Student has attended or will attend.
     *
     * @return Returns a list of Events the Student is attending
     */
    public List<Event> getEventList(){
	return lstEvents;
    }

    public void attendEvent(Event event){
	if(!this.getEventList().contains(event))
	    this.lstEvents.add(event);
	if(!event.getStudentList().contains(this))
	    event.addStudent(this);
    }

    public void unAttendEvent(Event event){
	if(this.getEventList().contains(event))
	    this.lstEvents.remove(event);
	if(event.getStudentList().contains(this))
	    event.removeStudent(this);
    }

    public List<Comment> getComments(){
	return lstComments;
    }

    //IMPLEMENTATION
    /**
     * CompareTo implemented on the Email ID field which is always unique and
     * therefore a business rule.
     *
     * @param el Expects an element of type Student
     */
    @Override
    public int compareTo(Student el){
	return (this.getEmailID().compareTo(el.getEmailID()));
    }

    //OVERRIDES
    @Override
    public boolean equals(Object el){
	if(!(el instanceof Student))
	    return false;
	try{
	    Student tstStudent=(Student)el;
	    if(!(Integer.compare(this.hashCode(), tstStudent.hashCode())==0))
		return false;
	}catch(Exception ex){
	    ex.printStackTrace();
	    return false;
	}
	return true;
    }

    @Override
    public String toString(){
	try{
	    return ("/Entities.Student{ID:"+this.getPID()+", Number:"+this.getStudent_Number()+", Name:{First:\""+this.getFName()+"\", Last:\""+this.getLName()+"\"}, Contact:{Phone:"+this.getPhone()+", EmailID:"+this.getEmailID()+"}, NotifyEvents:"+Boolean.toString(this.isNotifyEvents())+", Special:{Length:"+((this.getSpecial()==null) ? 0 : this.getSpecial().length())+"}, Comments:"+this.getComments().size()+"}");
	}catch(Exception ex){
	    return ex.toString();
	}
    }
}
