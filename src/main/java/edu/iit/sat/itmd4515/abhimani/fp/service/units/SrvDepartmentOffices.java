package edu.iit.sat.itmd4515.abhimani.fp.service.units;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Department;
import edu.iit.sat.itmd4515.abhimani.fp.domain.relations.DepartmentOffice;
import edu.iit.sat.itmd4515.abhimani.fp.service.AbstractService;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Stateless
public class SrvDepartmentOffices
	extends AbstractService<DepartmentOffice>{
    public SrvDepartmentOffices(){
	super(DepartmentOffice.class);
    }

    @Override
    public List<DepartmentOffice> retrieveAll(){
	return super.em.createNamedQuery("DepartmentOffices.retrieveAll", DepartmentOffice.class).getResultList();
    }

    @Override
    public DepartmentOffice findByID(long ID){
	return super.em.createNamedQuery("DepartmentOffices.findByID", DepartmentOffice.class).setParameter("PID", ID).getSingleResult();
    }

    public List<DepartmentOffice> retrieveByDepartment(Department dept){
	return super.em.createNamedQuery("DepartmentOffices.retrieveByDepartment", DepartmentOffice.class).setParameter("Dept", dept).getResultList();
    }

    public DepartmentOffice findByTitle(String name){
	return super.em.createNamedQuery("DepartmentOffices.findByTitle", DepartmentOffice.class).setParameter("Name", name).getSingleResult();
    }
}
