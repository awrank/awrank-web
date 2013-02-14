package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * verification of email entered by user during registration
 * 
 * refactored by Olga
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user_email_activation")
//@JsonIgnoreProperties({"createdDate", "lastModifiedDate", "createdBy", "lastModifiedBy"})
public class UserEmailActivation extends AbstractUserRelatedEntityAuditable<Long> {
   
	@Column(name = "code", nullable = false)
    private String code;
   
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "email_verified_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date emailVerifiedDate;
    
    @Column(name = "ended_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endedDate; 
   
    public UserEmailActivation() {
    }
   
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

   
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  
      public Date getEmailVerifiedDate() {
        return emailVerifiedDate;
    }

    public void setEmailVerifiedDate(Date emailVerifiedDate) {
        this.emailVerifiedDate = emailVerifiedDate;
    }

    public Date getEndedDate() {
      return endedDate;
    }

	  public void setEndedDate(Date endedDate) {
	      this.endedDate = endedDate;
	  }
   
}
