package edu.iit.sat.itmd4515.abhimani.fp.service.units;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Department;
import edu.iit.sat.itmd4515.abhimani.fp.service.AbstractService;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Stateless
public class SrvDepartments
	extends AbstractService<Department>{
    public SrvDepartments(){
	super(Department.class);
    }

    @Override
    public List<Department> retrieveAll(){
	return super.em.createNamedQuery("Departments.retrieveAll", Department.class).getResultList();
    }

    @Override
    public Department findByID(long ID){
	return super.em.createNamedQuery("Departments.findByID", Department.class).setParameter("PID", ID).getSingleResult();
    }

    public Department findByName(String name){
	return super.em.createNamedQuery("Departments.findByName", Department.class).setParameter("Name", name).getSingleResult();
    }
}
