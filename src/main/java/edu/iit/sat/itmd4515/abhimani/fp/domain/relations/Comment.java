package edu.iit.sat.itmd4515.abhimani.fp.domain.relations;

import edu.iit.sat.itmd4515.abhimani.fp.domain.AbstractEntityUnit;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Event;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Entity
@Table(name = "comments")
@NamedQueries({
    @NamedQuery(name = "Comments.retrieveAll", query = "SELECT c FROM Comment AS c"),
    @NamedQuery(name = "Comments.findByID", query = "SELECT c FROM Comment AS c WHERE c.PID=:PID"),
    @NamedQuery(name = "Comments.retrieveByEvent", query = "SELECT c FROM Comment AS c WHERE c.Evt=:Evt"),
    @NamedQuery(name = "Comments.retrieveByStudent", query = "SELECT c FROM Comment AS c WHERE c.Stud=:Stud")

})
public class Comment
	extends AbstractEntityUnit
	implements Comparable<Comment>, Serializable{
    //COLUMNS
    @JoinColumn(name = "Student_ID", referencedColumnName = "PID", nullable = false)
    @ManyToOne(optional = false)
    private Student Stud;

    @JoinColumn(name = "Event_ID", referencedColumnName = "PID", nullable = false)
    @ManyToOne(optional = false)
    private Event Evt;

    @Column(name = "Comment", nullable = false, length = 2000)
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CDate", nullable = false, insertable = true, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private final Date CDate=new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MDate", insertable = false, updatable = true, columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date MDate;

    //CONSTRUCTS
    public Comment(){
	super();
    }

    public Comment(String comment){
	this.setComment(comment);
    }

    //PROPERTIES
    public Student getStudent(){
	return Stud;
    }

    public void setStudent(Student Stud){
	this.Stud=Stud;
    }

    public Event getEvent(){
	return Evt;
    }

    public void setEvent(Event Evt){
	this.Evt=Evt;
    }

    public String getComment(){
	return StringEscapeUtils.unescapeHtml4(comment);
    }

    public void setComment(String comment){
	this.comment=StringEscapeUtils.escapeHtml4(comment);
    }

    /**
     * This field is inserted only once automatically upon row creation.
     *
     * @return Returns Creation Date
     */
    public String getCDate(){
	return CDate.toString();
    }

    /**
     * This field is updated automatically receives last update date.
     *
     * @return Returns Last Modified Date
     */
    public String getMDate(){
	return ((MDate==null) ? "-" : MDate.toString());
    }

    //IMPLEMENTATION
    @Override
    public int compareTo(Comment el){
	return (this.getMDate().compareTo(el.getMDate()));
    }

    //OVERRIDES
    @Override
    public boolean equals(Object el){
	if(!(el instanceof Comment))
	    return false;
	try{
	    Comment tstComment=(Comment)el;
	    if(!(Integer.compare(this.hashCode(), tstComment.hashCode())==0))
		return false;
	}catch(Exception ex){
	    return false;
	}
	return true;
    }

    @Override
    public String toString(){
	try{
	    return ("/Relations.Comment{ID:"+this.getPID()+", Student_Email_ID:"+this.getStudent().getEmailID()+", Event:\""+this.getEvent().getTitle()+"\", Create_Date:\""+this.getCDate()+"\", Last_Modified:\""+this.getMDate()+"\"}");
	}catch(Exception ex){
	    return ex.toString();
	}
    }
}
