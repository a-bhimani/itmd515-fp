package edu.iit.sat.itmd4515.abhimani.fp.service.units;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Event;
import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Student;
import edu.iit.sat.itmd4515.abhimani.fp.domain.relations.Comment;
import edu.iit.sat.itmd4515.abhimani.fp.service.AbstractService;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Stateless
public class SrvComments
	extends AbstractService<Comment>{
    public SrvComments(){
	super(Comment.class);
    }

    @Override
    public List<Comment> retrieveAll(){
	return super.em.createNamedQuery("Comments.retrieveAll", Comment.class).getResultList();
    }

    @Override
    public Comment findByID(long ID){
	return super.em.createNamedQuery("Comments.findByID", Comment.class).setParameter("PID", ID).getSingleResult();
    }

    public List<Comment> retrieveByEvent(Event evt){
	return super.em.createNamedQuery("Comments.retrieveByEvent", Comment.class).setParameter("Evt", evt).getResultList();
    }

    public List<Comment> retrieveByStudent(Student stud){
	return super.em.createNamedQuery("Comments.retrieveByStudent", Comment.class).setParameter("Stud", stud).getResultList();
    }
}
