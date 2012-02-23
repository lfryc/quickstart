package org.jboss.as.quickstarts.kitchensink.web;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.qualifiers.NewMember;

@SessionScoped
@Named
public class PasswordBean implements Serializable, Cloneable {

     @NotNull
     @NotEmpty
     @Size(min = 6)
    private String password;

     @NotNull
     @NotEmpty
     @Size(min = 6)
    private String passwordConfirmation;

    // @Inject
    // @NewMember
    // private Member newMember;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    @AssertTrue(message = "Password and confirmation needs to be same", groups = { EqualityValidationGroup.class })
    public boolean isPasswordEquals() {
        return password.equals(passwordConfirmation);
    }

    public Class[] getEqualityValidationGroups() {
        return new Class[] { EqualityValidationGroup.class };
    }

    public void savePasswordHash() {
        // once a password pass validation, we can save the hash
        // newMember.setPasswordHash("...");
    }

    public interface EqualityValidationGroup {
    }
}
