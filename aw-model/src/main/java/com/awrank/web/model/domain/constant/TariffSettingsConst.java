package com.awrank.web.model.domain.constant;

/**
 *
 */
public interface TariffSettingsConst extends AbstractObjectConst {

    public static final String TABLE_NAME = "tariff_settings";

    public static final String S_TARIFF = "tariff_id";
    public static final String H_TARIFF = "tariff";
    public static final String H_TARIFF__NAME_DIC_CODE = H_TARIFF + '.' + TariffConst.H_NAME_DIC_CODE;
    public static final String H_TARIFF__DESCRIPTION_DIC_CODE = H_TARIFF + '.' + TariffConst.H_DESCRIPTION_DIC_CODE;
    public static final String H_TARIFF__LENGTH_IN_DAYS = H_TARIFF + '.' + TariffConst.H_LENGTH_IN_DAYS;

    public static final String S_PRICE = "price";
    public static final String H_PRICE = "price";

    public static final String S_DISCOUNT = "discount";
    public static final String H_DISCOUNT = "discount";

    public static final String S_STARTED_DATE = "started_at";
    public static final String H_STARTED_DATE = "startedDate";
}
