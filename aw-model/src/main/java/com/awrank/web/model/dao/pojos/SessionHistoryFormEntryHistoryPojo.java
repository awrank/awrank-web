package com.awrank.web.model.dao.pojos;

import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.constant.EntryHistoryConst;
import com.awrank.web.model.utils.json.MyXmlLocalDateTimeSerializer;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDateTime;

/**
 * @author Alex Polyakov
 */
public class SessionHistoryFormEntryHistoryPojo {
	private final Long id;
	private final String browseName;
	private final String ipAddress;
	private final String countryCode;
	private final boolean success;
	@JsonSerialize(using = MyXmlLocalDateTimeSerializer.class)
	private final LocalDateTime signinDate;
	@JsonSerialize(using = MyXmlLocalDateTimeSerializer.class)
	private final LocalDateTime signoutDate;

	@SelectFrom(EntryHistory.class)
	public SessionHistoryFormEntryHistoryPojo(
			@SelectField(EntryHistoryConst.H_ID) Long id,
			@SelectField(EntryHistoryConst.H_BROWSE_NAME) String browseName,
			@SelectField(EntryHistoryConst.H_IP_ADDRESS) String ipAddress,
			@SelectField(EntryHistoryConst.H_COUNTRY_CODE) String countryCode,
			@SelectField(EntryHistoryConst.H_SUCCESS) boolean success,
			@SelectField(EntryHistoryConst.H_SIGNIN_DATE) LocalDateTime signinDate,
			@SelectField(EntryHistoryConst.H_SIGNOUT_DATE) LocalDateTime signoutDate
	) {
		this.id = id;
		this.browseName = browseName;
		this.ipAddress = ipAddress;
		this.countryCode = countryCode;
		this.success = success;
		this.signinDate = signinDate;
		this.signoutDate = signoutDate;
	}

	public Long getId() {
		return id;
	}

	public String getBrowseName() {
		return browseName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public LocalDateTime getSigninDate() {
		return signinDate;
	}

	public LocalDateTime getSignoutDate() {
		return signoutDate;
	}
}
