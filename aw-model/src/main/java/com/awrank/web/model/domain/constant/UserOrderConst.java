package com.awrank.web.model.domain.constant;

/**
 *
 */
public interface UserOrderConst extends AbstractUserItemConst {

    public static final String TABLE_NAME = "user_order";

    public static final String S_TARIFF_SETTINGS = "tariff_settings_id";
    public static final String H_TARIFF_SETTINGS = "tariffSettings";
    public static final String H_TARIFF_SETTINGS__PRICE = H_TARIFF_SETTINGS + '.' + TariffSettingsConst.H_PRICE;
    public static final String H_TARIFF_SETTINGS__TARIFF__NAME_DIC_CODE = H_TARIFF_SETTINGS + '.' + TariffSettingsConst.H_TARIFF__NAME_DIC_CODE;
    public static final String H_TARIFF_SETTINGS__TARIFF__DESCRIPTION_DIC_CODE = H_TARIFF_SETTINGS + '.' + TariffSettingsConst.H_TARIFF__DESCRIPTION_DIC_CODE;
    public static final String H_TARIFF_SETTINGS__TARIFF__LENGTH_IN_DAYS = H_TARIFF_SETTINGS + '.' + TariffSettingsConst.H_TARIFF__LENGTH_IN_DAYS;

    public static final String S_COMPLETE = "complete";
    public static final String H_COMPLETE = "complete";

    public static final String S_REF_USER = "ref_user_id";
    public static final String H_REF_USER = "refUser";

    public static final String S_GRACE_PERIOD = "grace_period";
    public static final String H_GRACE_PERIOD = "gracePeriod";

    public static final String S_PAYED_DATE = "payed_at";
    public static final String H_PAYED_DATE = "payedDate";

}
