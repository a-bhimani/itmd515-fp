package edu.iit.sat.itmd4515.abhimani.fp.service.units;

import edu.iit.sat.itmd4515.abhimani.fp.domain.authorization.Authentication;
import edu.iit.sat.itmd4515.abhimani.fp.service.AbstractService;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Stateless
public class SrvAuthUsers
	extends AbstractService<Authentication>{
    public SrvAuthUsers(){
	super(Authentication.class);
    }

    @Override
    public List<Authentication> retrieveAll(){
	return super.em.createNamedQuery("Users.retrieveAll", Authentication.class).getResultList();
    }

    @Override
    public Authentication findByID(long ID){
	return super.em.createNamedQuery("Users.findByID", Authentication.class).setParameter("PID", ID).getSingleResult();
    }

    public Authentication findByUsername(String Username){
	return super.em.createNamedQuery("Users.findByUsername", Authentication.class).setParameter("Username", Username).getSingleResult();
    }
}
