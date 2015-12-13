package edu.iit.sat.itmd4515.abhimani.fp.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 * @param <T> Must pass the type of the Entity the Service belongs to
 */
public abstract class AbstractService<T>{
    public static final String STR_PERS_UNIT="abhimaniPU";

    @PersistenceContext(name = STR_PERS_UNIT)
    protected EntityManager em;

    private Class<T> mappedEntityClass;

    private AbstractService(){
    }

    protected AbstractService(Class<T> entityClass){
	this.mappedEntityClass=entityClass;
    }

    public void create(T entityObj){
	this.em.merge(entityObj);
    }

    public abstract List<T> retrieveAll();
//  return this.em.createNamedQuery((mappedEntityClass.getName()+"s.retrieveAll"), mappedEntityClass).getResultList();

    public abstract T findByID(long ID);
//  return this.em.createNamedQuery((mappedEntityClass.getName()+"s.findByID"), mappedEntityClass).setParameter("PID", ID).getSingleResult();

    public T update(T entityObj){
	return this.em.merge(entityObj);
    }

    public void delete(T entityObj){
	this.em.remove(entityObj);
    }
}
