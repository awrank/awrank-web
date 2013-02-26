package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.DatedAbstractAuditable;

import javax.persistence.*;

/**
 * The <b>Product</b> class represents a product.
 *
 * @author Eugene Solomka
 */
@Entity
@Table(name = "products")
public class Product extends DatedAbstractAuditable {
	/**
	 * Name of the product.
	 */
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	/**
	 * Description of the product.
	 */
	@Column(name = "description", nullable = false, length = 255)
	private String description;

	/**
	 * Type.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private ProductType productType;

	/**
	 * Visibility.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "visibility", nullable = false)
	private ProductVisibility productVisibility;

	/**
	 * Expiration period.
	 */
	@Column(name = "period")
	private Integer periodInDays;

	/**
	 * Count of available daily requests.
	 */
	@Column(name = "daily_requests")
	private Integer countDailyRequest;

	/**
	 * Count of available monthly requests.
	 */
	@Column(name = "monthly_requests")
	private Integer countMonthlyRequest;

	/**
	 * Is active?
	 */
	@Column(name = "is_active", nullable = false)
	private boolean isActive = true;


	public Product() {
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public ProductVisibility getProductVisibility() {
		return productVisibility;
	}

	public void setProductVisibility(ProductVisibility productVisibility) {
		this.productVisibility = productVisibility;
	}

	public Integer getPeriodInDays() {
		return periodInDays;
	}

	public void setPeriodInDays(Integer periodInDays) {
		this.periodInDays = periodInDays;
	}

	public Integer getCountDailyRequest() {
		return countDailyRequest;
	}

	public void setCountDailyRequest(Integer countDailyRequest) {
		this.countDailyRequest = countDailyRequest;
	}

	public Integer getCountMonthlyRequest() {
		return countMonthlyRequest;
	}

	public void setCountMonthlyRequest(Integer countMonthlyRequest) {
		this.countMonthlyRequest = countMonthlyRequest;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}
}


