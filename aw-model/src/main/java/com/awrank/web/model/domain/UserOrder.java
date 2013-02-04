package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.domain.constant.UserOrderConst;
import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.ObjectNode;

import javax.persistence.*;
import java.util.Date;

/**
 * заказ
 */
@Entity
@Table(name = UserOrderConst.TABLE_NAME)
public class UserOrder extends AbstractUserItem implements UserOrderConst {

    /**
     * свойства тарифа
     */
    private TariffSettings tariffSettings;
    /**
     * заказ был оплачен и действует
     */
    private Boolean complete;
    /**
     * TODO
     * ?
     */
    private User refUser;
    /**
     * в днях, спустя какой период времени будет производиться оплата
     */
    private Integer gracePeriod;
    /**
     * дата оплата
     */
    private Date payedDate;

    {
        objectType = EObjectType.USER_ORDER;
    }

    public UserOrder() {
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = S_TARIFF_SETTINGS, nullable = false)
    public TariffSettings getTariffSettings() {
        return tariffSettings;
    }

    public void setTariffSettings(TariffSettings tariffSettings) {
        this.tariffSettings = tariffSettings;
    }

    @Column(name = S_COMPLETE, nullable = false)
    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = S_REF_USER, nullable = true)
    public User getRefUser() {
        return refUser;
    }

    public void setRefUser(User refUser) {
        this.refUser = refUser;
    }

    @Column(name = S_GRACE_PERIOD, nullable = false)
    public Integer getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    @Column(name = S_PAYED_DATE, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    //@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
//@Type(type="org.jadira.usertype.dateandtime.jsr310.PersistentLocalDateTime")
    public Date getPayedDate() {
        return payedDate;
    }

    public void setPayedDate(Date payedDate) {
        this.payedDate = payedDate;
    }

    // ------------------------------- JSON ---------------------------------------

    public UserOrder(final ObjectNode jsonObject) {
        // tariffSettings
        this.complete = JsonUtils.getBoolean(jsonObject, S_COMPLETE);
        // refUser
        this.gracePeriod = JsonUtils.getInteger(jsonObject, S_GRACE_PERIOD);
        this.payedDate = JsonUtils.getDate(jsonObject, S_PAYED_DATE);
    }

    public ObjectNode toJsonObject() {
        final ObjectNode jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_TARIFF_SETTINGS, tariffSettings);
        JsonUtils.set(jsonObject, S_COMPLETE, complete);
        JsonUtils.set(jsonObject, S_GRACE_PERIOD, gracePeriod);
        JsonUtils.set(jsonObject, S_PAYED_DATE, payedDate);
        return jsonObject;
    }
}
