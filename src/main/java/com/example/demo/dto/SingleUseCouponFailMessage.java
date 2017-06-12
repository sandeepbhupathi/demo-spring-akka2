package com.example.demo.dto;

import java.io.Serializable;

public class SingleUseCouponFailMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String batchId;
	private String promotionID;
	private int failNoOfCoupons;
	private int retryCount;
	


	public SingleUseCouponFailMessage(String batchId, String promotionId,int failNoOfCoupons,int retryCount) {
		this.batchId = batchId;
		this.promotionID = promotionId;
		this.failNoOfCoupons = failNoOfCoupons;
		this.retryCount = retryCount;
	}

	public int getRetryCount() {
		return retryCount;
	}
	
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	public int getFailNoOfCoupons() {
		return failNoOfCoupons;
	}

	public void setFailNoOfCoupons(int failNoOfCoupons) {
		this.failNoOfCoupons = failNoOfCoupons;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getPromotionID() {
		return promotionID;
	}

	public void setPromotionID(String promotionID) {
		this.promotionID = promotionID;
	}

}
