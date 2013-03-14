package com.awrank.web.model.service.impl.pojos;

public class PaymentWMFormPojo {
	/**
	 * Индикатор предварительного запроса (1)
	 */
	private String LMI_PREREQUEST;
	/**
	 * Внутренний номер покупки продавца
	 */
	private String LMI_PAYMENT_NO;
	/**
	 * Внутренний номер счета в системе WebMoney Transfer
	 */
	private String LMI_SYS_INVS_NO;
	/**
	 * Внутренний номер платежа в системе WebMoney Transfer
	 */
	private String LMI_SYS_TRANS_NO;
	/**
	 * Дата и время выполнения платежа
	 */
	private String LMI_SYS_TRANS_DATE;
	/**
	 * Кошелек продавца
	 */
	private String LMI_PAYEE_PURSE;
	/**
	 * Сумма платежа
	 */
	private String LMI_PAYMENT_AMOUNT;
	/**
	 * Флаг тестового режима
	 */
	private String LMI_MODE;
	/**
	 * Кошелек покупателя
	 */
	private String LMI_PAYER_PURSE;
	/**
	 * WMId покупателя
	 */
	private String LMI_PAYER_WM;
	/**
	 * Контрольная подпись
	 */
	private String LMI_HASH;

	private String LMI_LANG;
	/**
	 * Назначение платежа
	 */
	private String LMI_PAYMENT_DESC;
	/**
	 * Способ подтверждения
	 */
	private String LMI_DBLCHK;

	public String getLMI_PREREQUEST() {
		return LMI_PREREQUEST;
	}

	public void setLMI_PREREQUEST(String LMI_PREREQUEST) {
		this.LMI_PREREQUEST = LMI_PREREQUEST;
	}

	public String getLMI_PAYMENT_NO() {
		return LMI_PAYMENT_NO;
	}

	public void setLMI_PAYMENT_NO(String LMI_PAYMENT_NO) {
		this.LMI_PAYMENT_NO = LMI_PAYMENT_NO;
	}

	public String getLMI_SYS_INVS_NO() {
		return LMI_SYS_INVS_NO;
	}

	public void setLMI_SYS_INVS_NO(String LMI_SYS_INVS_NO) {
		this.LMI_SYS_INVS_NO = LMI_SYS_INVS_NO;
	}

	public String getLMI_SYS_TRANS_NO() {
		return LMI_SYS_TRANS_NO;
	}

	public void setLMI_SYS_TRANS_NO(String LMI_SYS_TRANS_NO) {
		this.LMI_SYS_TRANS_NO = LMI_SYS_TRANS_NO;
	}

	public String getLMI_SYS_TRANS_DATE() {
		return LMI_SYS_TRANS_DATE;
	}

	public void setLMI_SYS_TRANS_DATE(String LMI_SYS_TRANS_DATE) {
		this.LMI_SYS_TRANS_DATE = LMI_SYS_TRANS_DATE;
	}

	public String getLMI_PAYEE_PURSE() {
		return LMI_PAYEE_PURSE;
	}

	public void setLMI_PAYEE_PURSE(String LMI_PAYEE_PURSE) {
		this.LMI_PAYEE_PURSE = LMI_PAYEE_PURSE;
	}

	public String getLMI_PAYMENT_AMOUNT() {
		return LMI_PAYMENT_AMOUNT;
	}

	public void setLMI_PAYMENT_AMOUNT(String LMI_PAYMENT_AMOUNT) {
		this.LMI_PAYMENT_AMOUNT = LMI_PAYMENT_AMOUNT;
	}

	public String getLMI_MODE() {
		return LMI_MODE;
	}

	public void setLMI_MODE(String LMI_MODE) {
		this.LMI_MODE = LMI_MODE;
	}

	public String getLMI_PAYER_PURSE() {
		return LMI_PAYER_PURSE;
	}

	public void setLMI_PAYER_PURSE(String LMI_PAYER_PURSE) {
		this.LMI_PAYER_PURSE = LMI_PAYER_PURSE;
	}

	public String getLMI_PAYER_WM() {
		return LMI_PAYER_WM;
	}

	public void setLMI_PAYER_WM(String LMI_PAYER_WM) {
		this.LMI_PAYER_WM = LMI_PAYER_WM;
	}

	public String getLMI_HASH() {
		return LMI_HASH;
	}

	public void setLMI_HASH(String LMI_HASH) {
		this.LMI_HASH = LMI_HASH;
	}

	public String getLMI_LANG() {
		return LMI_LANG;
	}

	public void setLMI_LANG(String LMI_LANG) {
		this.LMI_LANG = LMI_LANG;
	}

	public String getLMI_PAYMENT_DESC() {
		return LMI_PAYMENT_DESC;
	}

	public void setLMI_PAYMENT_DESC(String LMI_PAYMENT_DESC) {
		this.LMI_PAYMENT_DESC = LMI_PAYMENT_DESC;
	}

	public String getLMI_DBLCHK() {
		return LMI_DBLCHK;
	}

	public void setLMI_DBLCHK(String LMI_DBLCHK) {
		this.LMI_DBLCHK = LMI_DBLCHK;
	}


	private static String fieldToString(String fieldName, String fieldValue) {
		String res = "";
		if (fieldValue != null) {
			res = fieldName + '=' + fieldValue + ',';
		}
		return res;
	}


	@Override
	public String toString() {
		return "WMPaymentConfirmation{" +
				fieldToString("LMI_PREREQUEST", LMI_PREREQUEST) +
				fieldToString("LMI_PAYMENT_NO", LMI_PAYMENT_NO) +
				fieldToString("LMI_SYS_INVS_NO", LMI_SYS_INVS_NO) +
				fieldToString("LMI_SYS_TRANS_NO", LMI_SYS_TRANS_NO) +
				fieldToString("LMI_SYS_TRANS_DATE", LMI_SYS_TRANS_DATE) +
				fieldToString("LMI_PAYEE_PURSE", LMI_PAYEE_PURSE) +
				fieldToString("LMI_PAYMENT_AMOUNT", LMI_PAYMENT_AMOUNT) +
				fieldToString("LMI_MODE", LMI_MODE) +
				fieldToString("LMI_PAYER_PURSE", LMI_PAYER_PURSE) +
				fieldToString("LMI_PAYER_WM", LMI_PAYER_WM) +
				fieldToString("LMI_HASH", LMI_HASH) +
				fieldToString("LMI_LANG", LMI_LANG) +
				fieldToString("LMI_PAYMENT_DESC", LMI_PAYMENT_DESC) +
				fieldToString("LMI_DBLCHK", LMI_DBLCHK) +
				'}';
	}
}
