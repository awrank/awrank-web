package com.awrank.web.data;

import com.awrank.web.data.constant.ETariffType;
import com.awrank.web.data.constant.TariffConst;
import com.awrank.web.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;

/**
 * тариф
 */
@Entity
@Table(name = TariffConst.TABLE_NAME)
public class Tariff extends AbstractObject implements TariffConst {

    /**
     * название
     */
    private String nameDicCode;
    /**
     * описание
     */
    private String descriptionDicCode;
    /**
     * продолжительность дней
     */
    private Integer lengthInDays;
    /**
     * количество запросов в день
     */
    private Integer countDailyRequest;
    /**
     * количество запросов в месяц
     */
    private Integer countMonthlyRequest;
    /**
     * тип тарифа
     * F - free2play
     * M - monthly
     * Y - yearly
     */
    private ETariffType tariffType;
    /**
     * тариф является служебным (не отображать пользователю)
     */
    private Boolean service;

    public Tariff() {
    }

    @Column(name = S_NAME_DIC_CODE, nullable = false)
    public String getNameDicCode() {
        return nameDicCode;
    }

    public void setNameDicCode(String nameDicCode) {
        this.nameDicCode = nameDicCode;
    }

    @Column(name = S_DESCRIPTION_DIC_CODE, nullable = false)
    public String getDescriptionDicCode() {
        return descriptionDicCode;
    }

    public void setDescriptionDicCode(String descriptionDicCode) {
        this.descriptionDicCode = descriptionDicCode;
    }

    @Column(name = S_LENGTH_IN_DAYS, nullable = false)
    public Integer getLengthInDays() {
        return lengthInDays;
    }

    public void setLengthInDays(Integer lengthInDays) {
        this.lengthInDays = lengthInDays;
    }

    @Column(name = S_COUNT_DAILY_REQUEST, nullable = false)
    public Integer getCountDailyRequest() {
        return countDailyRequest;
    }

    public void setCountDailyRequest(Integer countDailyRequest) {
        this.countDailyRequest = countDailyRequest;
    }

    @Column(name = S_COUNT_MONTHLY_REQUEST, nullable = false)
    public Integer getCountMonthlyRequest() {
        return countMonthlyRequest;
    }

    public void setCountMonthlyRequest(Integer countMonthlyRequest) {
        this.countMonthlyRequest = countMonthlyRequest;
    }

    @Column(name = S_TARIFF_TYPE, nullable = false)
    @Enumerated(EnumType.STRING)
    public ETariffType getTariffType() {
        return tariffType;
    }

    public void setTariffType(ETariffType tariffType) {
        this.tariffType = tariffType;
    }

    public void setTariffType(String tariffType) {
        this.tariffType = (tariffType != null) ? ETariffType.valueOf(tariffType) : null;
    }

    @Column(name = S_SERVICE, nullable = false)
    public Boolean getService() {
        return service;
    }

    public void setService(Boolean service) {
        this.service = service;
    }

    // --------------------------- JSON ------------------------------------------

    public Tariff(final JsonObject jsonObject) {
        super(jsonObject);
        this.nameDicCode = JsonUtils.getString(jsonObject, S_NAME_DIC_CODE);
        this.descriptionDicCode = JsonUtils.getString(jsonObject, S_DESCRIPTION_DIC_CODE);
        this.lengthInDays = JsonUtils.getInteger(jsonObject, S_LENGTH_IN_DAYS);
        this.countDailyRequest = JsonUtils.getInteger(jsonObject, S_COUNT_DAILY_REQUEST);
        this.countMonthlyRequest = JsonUtils.getInteger(jsonObject, S_COUNT_MONTHLY_REQUEST);
        setTariffType(JsonUtils.getString(jsonObject, S_TARIFF_TYPE));
        this.service = JsonUtils.getBoolean(jsonObject, S_SERVICE);
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_NAME_DIC_CODE, nameDicCode);
        JsonUtils.set(jsonObject, S_DESCRIPTION_DIC_CODE, descriptionDicCode);
        JsonUtils.set(jsonObject, S_LENGTH_IN_DAYS, lengthInDays);
        JsonUtils.set(jsonObject, S_COUNT_DAILY_REQUEST, countDailyRequest);
        JsonUtils.set(jsonObject, S_COUNT_MONTHLY_REQUEST, countMonthlyRequest);
        JsonUtils.set(jsonObject, S_TARIFF_TYPE, tariffType);
        JsonUtils.set(jsonObject, S_SERVICE, service);
        return jsonObject;
    }
}
