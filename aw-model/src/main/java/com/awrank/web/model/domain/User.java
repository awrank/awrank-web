package com.awrank.web.model.domain;

import com.awrank.web.common.constants.AppConstants;
import com.awrank.web.model.domain.support.DatedAbstractAuditable;
import com.awrank.web.model.enums.SecretQuestion;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@code User} class represents a user entry.
 *
 * @author Alex Polyakov
 * @author Olga Korokhina
 */
@Entity
@Table(name = "users")
public class User extends DatedAbstractAuditable {

    /*
	@Value("#{appProps[user_default_language_code]}")
	private String user_default_language_code;
	*/

	/**
	 * A unique key which is used in requests to API system service.
	 */
	@Column(name = "api_key", nullable = false, unique = true, length = 64)
	private String apiKey;

	/**
	 * A user who invited the current user to the system.
	 */
	//@ManyToOne(fetch = FetchType.LAZY, optional = true)
	//@JoinColumn(name = "ref_user_id", nullable = true)
	@Column(name = "ref_user_id", nullable = true)
	private User refUser;

	/**
	 * User email.
	 */
	@Column(name = "email", nullable = false, unique = true, length = 128)
	private String email;

	/**
	 * User Skype id.
	 */
	@Column(name = "skype", nullable = true)
	private String skype;

	/**
	 * User name.
	 */
	@Column(name = "first_name", nullable = true)
	private String firstName;

	/**
	 * User last name.
	 */
	@Column(name = "last_name", nullable = true)
	private String lastName;

	/**
	 * User date of birth.
	 */
	@Column(name = "birthday", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime birthday;

	/**
	 * A code in dictionary for secret question
	 */
	@Column(name = "secret_question_dic_code", nullable = true)
	@Enumerated(EnumType.STRING)
	private SecretQuestion secretQuestionDicCode;

	/**
	 * An answer for the secret question.
	 */
	@Column(name = "secret_answer", nullable = true)
	private String secretAnswer;

	/**
	 * Language code in two-chars format, e.g. "EN", "RU", etc.
	 */
	@Column(name = "language_code", nullable = false, length = 2)
	@Enumerated(EnumType.STRING)
	private Language language;

	/**
	 * A quantity of failed authorization attempts.
	 */
	@Column(name = "authorization_fails_count", nullable = true)
	private Integer authorizationFailsCount;

	/**
	 * A date of last failed attempt of authorization.
	 */
	@Column(name = "authorization_fails_last", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime authorizationFailsLastDate;

	/**
	 * A date when a ban was started.
	 */
	@Column(name = "ban_started_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime banStartedDate;

	@OneToMany(mappedBy = "user", cascade = {})
	private Set<EntryPoint> entryPoints = new HashSet<EntryPoint>();

	@OneToMany(mappedBy = "user", cascade = {})
	private Set<UserRole> userRoles = new HashSet<UserRole>();

	public User() {
	}

	public User(Long id) {
		setId(id);
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

	public LocalDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDateTime birthday) {
		this.birthday = birthday;
	}

	public SecretQuestion getSecretQuestionDicCode() {
		return secretQuestionDicCode;
	}

	public void setSecretQuestionDicCode(SecretQuestion secretQuestionDicCode) {
		this.secretQuestionDicCode = secretQuestionDicCode;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Integer getAuthorizationFailsCount() {
		return authorizationFailsCount;
	}

	public void setAuthorizationFailsCount(Integer authorizationFailsCount) {
		this.authorizationFailsCount = authorizationFailsCount;
	}

	public LocalDateTime getAuthorizationFailsLastDate() {
		return authorizationFailsLastDate;
	}

	public void setAuthorizationFailsLastDate(LocalDateTime authorizationFailsLastDate) {
		this.authorizationFailsLastDate = authorizationFailsLastDate;
	}


	public LocalDateTime getBanStartedDate() {
		return banStartedDate;
	}

	public void setBanStartedDate(LocalDateTime banStartedDate) {
		this.banStartedDate = banStartedDate;
	}

	public Set<EntryPoint> getEntryPoints() {
		return entryPoints;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public String getFullName() {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isNotEmpty(getFirstName())) {
			builder.append(getFirstName());
		}
		if (StringUtils.isNotEmpty(getLastName())) {
			builder.append(" ");
			builder.append(getLastName());
		}
		return builder.toString().trim();
	}

	public Object[] toArray() {
		List<Object> values = new ArrayList<Object>();
		values.add(getFirstName());
		values.add(getLastName());
		values.add(getEmail());
		values.add(getApiKey());
		values.add(getSkype());
		DateTimeFormatter formatter = DateTimeFormat.forPattern(AppConstants.DateFormat.DF_ddMMyyyy_slash);
		if (getCreatedDate() != null) {
			values.add(getCreatedDate().toString(formatter));
		} else {
			values.add("-");
		}
		if (getEndedDate() != null) {
			values.add(getEndedDate().toString(formatter));
		} else {
			values.add("-");
		}
		return values.toArray();
	}

}
