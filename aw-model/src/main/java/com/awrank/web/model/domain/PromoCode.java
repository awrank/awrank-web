package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.domain.constant.PromoCodeConst;
import com.awrank.web.model.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;

/**
 * промо код
 */
@Entity
@Table(name = PromoCodeConst.TABLE_NAME)
public class PromoCode extends AbstractObject implements PromoCodeConst {

    /**
     * TODO
     * ?
     */
    private String reference;
    /**
     * промо код
     */
    private String code;
    /**
     * свойства тарифа
     */
    private TariffSettings tariffSettings;
    /**
     * заказ
     */
    private UserOrder userOrder;
    /**
     * пользователь кто создал
     */
    private User creator;

    {
        objectType = EObjectType.PROMO_CODE;
    }

    public PromoCode() {
    }

    @Column(name = S_REFERENCE, nullable = true)
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Column(name = S_CODE, nullable = false, unique = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = S_TARIFF_SETTINGS, nullable = false)
    public TariffSettings getTariffSettings() {
        return tariffSettings;
    }

    public void setTariffSettings(TariffSettings tariffSettings) {
        this.tariffSettings = tariffSettings;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = S_USER_ORDER, nullable = true)
    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = S_CREATOR, nullable = false)
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    // --------------------------- JSON ------------------------------------------

    public PromoCode(final JsonObject jsonObject) {
        super(jsonObject);
        this.reference = JsonUtils.getString(jsonObject, S_REFERENCE);
        this.code = JsonUtils.getString(jsonObject, S_CODE);
        // tariffSettings
        // userOrder
        // creator
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_REFERENCE, reference);
        JsonUtils.set(jsonObject, S_CODE, code);
        JsonUtils.set(jsonObject, S_TARIFF_SETTINGS, tariffSettings);
        JsonUtils.set(jsonObject, S_USER_ORDER, userOrder);
        JsonUtils.set(jsonObject, S_CREATOR, creator);
        return jsonObject;
    }

}
