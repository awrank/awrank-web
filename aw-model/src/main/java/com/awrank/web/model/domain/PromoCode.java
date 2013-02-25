package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.DatedAbstractAuditable;

import javax.persistence.*;

/**
 * The class contains information about {@code PromoCode} instance.
 *
 * @author Alex Polyakov
 * @author Eugene Solomka
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@Entity
@Table(name = "promo_codes")
public class PromoCode extends DatedAbstractAuditable<Long> {

    /**
     * Promotion code.
     */
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /**
     * TODO: description ?
     */
    @Column(name = "reference", nullable = true)
    private String reference;

    // todo: need user?

    /**
     * Product.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // todo: need product profile ?

    /**
     * Order.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
