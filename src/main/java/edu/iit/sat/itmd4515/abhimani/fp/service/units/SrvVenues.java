package edu.iit.sat.itmd4515.abhimani.fp.service.units;

import edu.iit.sat.itmd4515.abhimani.fp.domain.entities.Venue;
import edu.iit.sat.itmd4515.abhimani.fp.service.AbstractService;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Stateless
public class SrvVenues
	extends AbstractService<Venue>{
    public SrvVenues(){
	super(Venue.class);
    }

    @Override
    public List<Venue> retrieveAll(){
	return super.em.createNamedQuery("Venues.retrieveAll", Venue.class).getResultList();
    }

    @Override
    public Venue findByID(long ID){
	return super.em.createNamedQuery("Venues.findByID", Venue.class).setParameter("PID", ID).getSingleResult();
    }

    public Venue findByName(String name){
	return super.em.createNamedQuery("Venues.findByName", Venue.class).setParameter("Name", name).getSingleResult();
    }
}
