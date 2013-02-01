package com.awrank.web.tools.init.user;

import com.awrank.web.model.domain.UserOrder;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
public class OrderWrapper {

    private final Date payedDate;
    private final BigDecimal tariffSettingsPrice;
    private final String tariffNameDicCode;
    private final String tariffDescriptionDicCode;
    private final Integer tariffLengthInDays;

    @SelectFrom(UserOrder.class)
    public OrderWrapper(
            @SelectField(UserOrder.H_PAYED_DATE) Date payedDate,
            @SelectField(UserOrder.H_TARIFF_SETTINGS__PRICE) BigDecimal tariffSettingsPrice,
            @SelectField(UserOrder.H_TARIFF_SETTINGS__TARIFF__NAME_DIC_CODE) String tariffNameDicCode,
            @SelectField(UserOrder.H_TARIFF_SETTINGS__TARIFF__DESCRIPTION_DIC_CODE) String tariffDescriptionDicCode,
            @SelectField(UserOrder.H_TARIFF_SETTINGS__TARIFF__LENGTH_IN_DAYS) Integer tariffLengthInDays) {
        this.payedDate = payedDate;
        this.tariffSettingsPrice = tariffSettingsPrice;
        this.tariffNameDicCode = tariffNameDicCode;
        this.tariffDescriptionDicCode = tariffDescriptionDicCode;
        this.tariffLengthInDays = tariffLengthInDays;
    }

    public Date getPayedDate() {
        return payedDate;
    }

    public BigDecimal getTariffSettingsPrice() {
        return tariffSettingsPrice;
    }

    public String getTariffNameDicCode() {
        return tariffNameDicCode;
    }

    public String getTariffDescriptionDicCode() {
        return tariffDescriptionDicCode;
    }

    public Integer getTariffLengthInDays() {
        return tariffLengthInDays;
    }

    @Override
    public String toString() {
        return "OrderWrapper{" +
                "payedDate=" + payedDate +
                ", tariffSettingsPrice=" + tariffSettingsPrice +
                ", tariffNameDicCode='" + tariffNameDicCode + '\'' +
                ", tariffDescriptionDicCode='" + tariffDescriptionDicCode + '\'' +
                ", tariffLengthInDays=" + tariffLengthInDays +
                '}';
    }
}
