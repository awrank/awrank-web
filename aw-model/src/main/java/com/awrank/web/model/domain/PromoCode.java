package com.awrank.web.model.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * promo codes
 * refactored by Olga
 */
@Entity
@Table(name = "promo_codes")
public class PromoCode extends AbstractPersistable<Long> {

    /**
     * TODO
     * ?
     */
	@Column(name = "reference", nullable = true)	
    private String reference;
    /**
     * code
     */
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    
    //TODO: replace with "product"
    /**
     * product settings
     */
//  @ManyToOne(fetch = FetchType.LAZY, optional = false)
//  @JoinColumn(name = S_TARIFF_SETTINGS, nullable = false)
    //private TariffSettings tariffSettings;
    
//    /**
//     * order
//     */
//    @ManyToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "user_order_id", nullable = true)
//    private UserOrder userOrder;

    public PromoCode() {
    }
    
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

//    public TariffSettings getTariffSettings() {
//        return tariffSettings;
//    }
//
//    public void setTariffSettings(TariffSettings tariffSettings) {
//        this.tariffSettings = tariffSettings;
//    }
//
//    public UserOrder getUserOrder() {
//        return userOrder;
//    }
//
//    public void setUserOrder(UserOrder userOrder) {
//        this.userOrder = userOrder;
//    }
}
