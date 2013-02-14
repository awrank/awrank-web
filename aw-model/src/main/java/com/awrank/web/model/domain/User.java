package com.awrank.web.model.domain;


import com.awrank.web.model.domain.constant.ESecretQuestion;
import com.awrank.web.model.domain.support.ExtendedAbstractAuditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import javax.persistence.*;
import java.util.Date;

/**
 * User, refactored by Olga following Dictionary way 
 */
@Entity
@Table(name = "users")
//@JsonIgnoreProperties({"createdDate", "lastModifiedDate", "createdBy", "lastModifiedBy"})
public class User extends ExtendedAbstractAuditable<Long> {
	/*
	@Value("#{appProps[user_default_language_code]}")
	private String user_default_language_code;
	*/
	/*
	@Column(name = "id", nullable = false, unique = true)
    private Long id;
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
*/
	/**
     * уникальный код, который будет использоваться для запросов к API
     */
	@Column(name = "api_key", nullable = false, unique = true)
    private String apiKey;
    /**
     * пользователь, пригласивший в систему
     */
	//@ManyToOne(fetch = FetchType.LAZY, optional = true)
	//@JoinColumn(name = "ref_user_id", nullable = true)
	@Column(name = "ref_user_id", nullable = true)
    private User refUser;
    /**
     * email пользователя
     */
	@Column(name = "email", nullable = false, unique = true)
    private String email;
    /**
     * скайп
     */
	@Column(name = "skype", nullable = true)
    private String skype;
    /**
     * имя
     */
	@Column(name = "first_name", nullable = true)
    private String firstName;
    /**
     * фамилия
     */
	@Column(name = "last_name", nullable = true)
    private String lastName;
    /**
     * день рождения
     */
	@Column(name = "birthday", nullable = true)
	@Temporal(TemporalType.DATE)
    private Date birthday;
    /**
     * code in dictionary for secret question
     * код текст из словаря для секретного вопроса
     */
	@Column(name = "secret_question_dic_code", nullable = true)
	@Enumerated(EnumType.STRING)
    private ESecretQuestion secretQuestionDicCode;
    /**
     * ответ на секретный вопрос
     */
	@Column(name = "secret_answer", nullable = true)
    private String secretAnswer;
    
    /**
     * количество неудачных аутентификации
     */
	@Column(name = "autorization_fails_count", nullable = false)
    private Integer authorizationFailsCount;
    /**
     * последняя неудачная попытка
     */
	@Column(name = "autorization_fails_last", nullable = true)
    private Date authorizationFailsLastDate;
    /**
     * дата блокирокви
     */
	@Column(name = "ban_started_at", nullable = true)
    private Date banStartedDate;
	
	/**
	 *  language code in "EN", "RU" format
	 */
	@Column(name = "language_code", nullable = false)
	@Enumerated(EnumType.STRING)
	private Language language;
   
    public User() {
    	
    	this.authorizationFailsCount = 0;
    	this.language = Language.valueOf("EN");
    	//this.language = Language.valueOf(user_default_language_code);//doesn't work for some reason
    	
    }
  
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
  
    public User getRefUser() {
        return refUser;
    }

    public void setRefUser(User refUser) {
        this.refUser = refUser;
    }
   
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }
   
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
   
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
   
    public ESecretQuestion getSecretQuestionDicCode() {
        return secretQuestionDicCode;
    }

    public void setSecretQuestionDicCode(ESecretQuestion secretQuestionDicCode) {
        this.secretQuestionDicCode = secretQuestionDicCode;
    }
   
    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }
   
//    public Language getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(Language language) {
//        this.language = language;
//    }
//   
    public Integer getAuthorizationFailsCount() {
        return authorizationFailsCount;
    }

    public void setAuthorizationFailsCount(Integer authorizationFailsCount) {
        this.authorizationFailsCount = authorizationFailsCount;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    public Date getAuthorizationFailsLastDate() {
        return authorizationFailsLastDate;
    }

    public void setAuthorizationFailsLastDate(Date authorizationFailsLastDate) {
        this.authorizationFailsLastDate = authorizationFailsLastDate;
    }
   
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBanStartedDate() {
        return banStartedDate;
    }

    public void setBanStartedDate(Date banStartedDate) {
        this.banStartedDate = banStartedDate;
    }
}
