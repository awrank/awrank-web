package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.domain.constant.EPaymentType;
import com.awrank.web.model.domain.constant.PaymentConst;
import com.awrank.web.model.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * оплата
 */
@Entity
@Table(name = PaymentConst.TABLE_NAME)
public class Payment extends AbstractObject implements PaymentConst {

    /**
     * заказ
     */
    private UserOrder userOrder;
    /**
     * система оплаты
     */
    private EPaymentType paymentType;
    /**
     * код операции
     */
    private String transactionId;
    /**
     * TODO
     * ?
     */
    private String reference;
    /**
     * TODO
     * ?
     */
    private String authorizationCode;
    /**
     * сумма
     */
    private BigDecimal amount;

    {
        objectType = EObjectType.PAYMENT;
    }

    public Payment() {
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = S_USER_ORDER, nullable = false)
    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }

    @Column(name = S_PAYMENT_TYPE, nullable = false)
    @Enumerated(EnumType.STRING)
    public EPaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(EPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = (paymentType != null) ? EPaymentType.valueOf(paymentType) : null;
    }

    @Column(name = S_TRANSACTION_ID, nullable = false)
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Column(name = S_REFERENCE, nullable = false)
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Column(name = S_AUTHORIZATION_CODE, nullable = false)
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    @Column(name = S_AMOUNT, columnDefinition = SQL_PRICE_COLUMN_DEFINITION, nullable = false)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // ------------------------------- JSON ---------------------------------------

    public Payment(final JsonObject jsonObject) {
        // userOrder
        setPaymentType(JsonUtils.getString(jsonObject, S_PAYMENT_TYPE));
        this.transactionId = JsonUtils.getString(jsonObject, S_TRANSACTION_ID);
        this.reference = JsonUtils.getString(jsonObject, S_REFERENCE);
        this.authorizationCode = JsonUtils.getString(jsonObject, S_AUTHORIZATION_CODE);
        this.amount = JsonUtils.getBigDecimal(jsonObject, S_AMOUNT);
    }

    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_USER_ORDER, userOrder);
        JsonUtils.set(jsonObject, S_PAYMENT_TYPE, paymentType);
        JsonUtils.set(jsonObject, S_TRANSACTION_ID, transactionId);
        JsonUtils.set(jsonObject, S_REFERENCE, reference);
        JsonUtils.set(jsonObject, S_AUTHORIZATION_CODE, authorizationCode);
        JsonUtils.set(jsonObject, S_AMOUNT, amount);
        return jsonObject;
    }
}
