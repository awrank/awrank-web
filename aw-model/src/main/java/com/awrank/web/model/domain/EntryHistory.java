package com.awrank.web.model.domain;

import com.awrank.web.common.constants.AppConstants;
import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code EntryHistory} class describes one record in history table related to specified user.
 *
 * @author Alex Polyakov
 * @author Eugene Solomka
 * @author Andrew Stoyaltsev
 */
@Entity
@Table(name = "entry_history")
public class EntryHistory extends AbstractUserRelatedEntityAuditable {

	/**
	 * Entry point that entry history belongs to.
	 */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "entry_point_id", nullable = false)
	private EntryPoint entryPoint;

	/**
	 * Remote IP address where user was signed in.
	 */
	@Column(name = "ip_address", nullable = false, length = 64)
	private String ipAddress;
	/**
	 * Country code iso3166 defined on the basis ip address
	 */
	@Column(name = "country_code", nullable = false, length = 2)
	private String countryCode;
	/**
	 * browse or user agent
	 */
	@Column(name = "browse_name", nullable = false)
	private String browseName;

	/**
	 * Entered successfully?
	 */
	@Column(name = "success", nullable = false)
	private boolean success;

	/**
	 * Session identifier.
	 */
	@Column(name = "session_id", nullable = false)
	private String sessionId;

	/**
	 * Request spent for session.
	 */
	@Column(name = "spent_requests")
	private Integer spentRequests;

	/**
	 * Date when user has signed in.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "signed_in_at", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime signinDate;

	/**
	 * Date when user has signed out.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "signed_out_at", nullable = true)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime signoutDate;

	public EntryHistory() {
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getBrowseName() {
		return browseName;
	}

	public void setBrowseName(String browseName) {
		this.browseName = browseName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getSpentRequests() {
		return spentRequests;
	}

	public void setSpentRequests(Integer spentRequests) {
		this.spentRequests = spentRequests;
	}

	public LocalDateTime getSigninDate() {
		return (null == signinDate) ? null : new LocalDateTime(signinDate);
	}

	public void setSigninDate(LocalDateTime signinDate) {
		this.signinDate = signinDate;
	}

	public LocalDateTime getSignoutDate() {
		return (null == signoutDate) ? null : new LocalDateTime(signoutDate);
	}

	public void setSignoutDate(LocalDateTime signoutDate) {
		this.signoutDate = signoutDate;
	}

	public EntryPoint getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(EntryPoint entryPoint) {
		this.entryPoint = entryPoint;
	}

	public Object[] toArray() {
		List<Object> values = new ArrayList<Object>();
		values.add(getUser().getFullName());
		values.add(getEntryPoint().getEntryTypeUID());
		values.add(getSessionId());
		values.add(getIpAddress());
		values.add(isSuccess());
		values.add(getSpentRequests());
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
