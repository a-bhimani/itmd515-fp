package edu.iit.sat.itmd4515.abhimani.fp.service.units;

import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.Role;
import edu.iit.sat.itmd4515.abhimani.fp.service.AbstractService;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Stateless
@LocalBean
public class SrvAuthRoles
	extends AbstractService<Role>{
    public SrvAuthRoles(){
	super(Role.class);
    }

    @Override
    public List<Role> retrieveAll(){
	return super.em.createNamedQuery("Roles.retrieveAll", Role.class).getResultList();
    }

    @Override
    public Role findByID(long ID){
	return super.em.createNamedQuery("Roles.findByID", Role.class).setParameter("PID", ID).getSingleResult();
    }

    public Role findByName(String name){
	return super.em.createNamedQuery("Roles.findByName", Role.class).setParameter("Name", name).getSingleResult();
    }
}
