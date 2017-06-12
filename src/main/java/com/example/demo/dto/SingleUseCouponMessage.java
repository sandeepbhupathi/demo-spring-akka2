package com.example.demo.dto;

import java.io.Serializable;

public class SingleUseCouponMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String batchId;
	private String promotionID;
	private int noOfCoupons;
	private int noOfCouponsCreated;

	private int retryCount;
	
	public SingleUseCouponMessage(String batchId, int noOfCoupons,String promotionId,int retryCount) {
		super();
		this.batchId = batchId;
		this.noOfCoupons = noOfCoupons;
		this.promotionID = promotionId;
		this.retryCount=retryCount;
	}
	
	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}


	public String getPromotionID() {
		return promotionID;
	}
	
	public void setPromotionID(String promotionID) {
		this.promotionID = promotionID;
	}
	
	public int getNoOfCouponsCreated() {
		return noOfCouponsCreated;
	}

	public void setNoOfCouponsCreated(int noOfCouponsCreated) {
		this.noOfCouponsCreated = noOfCouponsCreated;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public int getNoOfCoupons() {
		return noOfCoupons;
	}

	public void setNoOfCoupons(int noOfCoupons) {
		this.noOfCoupons = noOfCoupons;
	}
}
