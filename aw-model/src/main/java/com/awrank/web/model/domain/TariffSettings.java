package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.TariffSettingsConst;
import com.awrank.web.model.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * свойства тарифа
 */
@Entity
@Table(name = TariffSettingsConst.TABLE_NAME)
public class TariffSettings extends AbstractObject implements TariffSettingsConst {

    /**
     * тариф
     */
    private Tariff tariff;
    /**
     * цена
     */
    private BigDecimal price;
    /**
     * процент скидки
     */
    private BigDecimal discount;
    /**
     * дата начала действия тарифа
     */
    private Date started;

    public TariffSettings() {
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = S_TARIFF, nullable = false)
    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    @Column(name = S_PRICE, columnDefinition = SQL_PRICE_COLUMN_DEFINITION, nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = S_DISCOUNT, columnDefinition = SQL_PRICE_COLUMN_DEFINITION, nullable = false)
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Column(name = S_STARTED, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    // --------------------------- JSON ------------------------------------------

    public TariffSettings(final JsonObject jsonObject) {
        super(jsonObject);
        // tariff
        this.price = JsonUtils.getBigDecimal(jsonObject, S_PRICE);
        this.discount = JsonUtils.getBigDecimal(jsonObject, S_DISCOUNT);
        this.started = JsonUtils.getDate(jsonObject, S_STARTED);
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_TARIFF, tariff);
        JsonUtils.set(jsonObject, S_PRICE, price);
        JsonUtils.set(jsonObject, S_DISCOUNT, discount);
        JsonUtils.set(jsonObject, S_STARTED, started);
        return jsonObject;
    }
}
