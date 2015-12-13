package edu.iit.sat.itmd4515.abhimani.fp.web;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
public abstract class AbstractControllerExt{
    protected FacesContext ctxFaces;
    protected static final String FACES_REDIRECT="?faces-redirect=true";

    @PostConstruct
    protected void triggerControllerInit(){
	ctxFaces=FacesContext.getCurrentInstance();
    }
}
