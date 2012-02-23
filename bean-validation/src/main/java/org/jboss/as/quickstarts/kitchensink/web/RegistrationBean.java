package org.jboss.as.quickstarts.kitchensink.web;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.qualifiers.NewMember;

@SessionScoped
@Named("registration")
public class RegistrationBean implements Serializable {

    private static final long serialVersionUID = -1L;

    private Member member = new Member();

    @Produces
    @NewMember
    @Named
    public Member getNewMember() {
        return member;
    }

    @Inject
    Logger logger;

    @Inject
    transient Flash flash;

    public String proceed() {
        // save the member to the database
        // e.g.: entityManager.persist(member);

        logger.info("registered member '" + member.getEmail() + "'");

        // add message
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Hello " + member.getName() + ", you have been successfully registered"));

        // setup JSF to keep message to next request (using flash-scope)
        flash.setKeepMessages(true);

        // redirect to index page
        return "index?faces-redirect=true";
    }
}
