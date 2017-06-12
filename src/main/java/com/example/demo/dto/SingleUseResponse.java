package com.example.demo.dto;

public class SingleUseResponse {
	public SingleUseResponse(String batchId, String promotionId, String batchStatus) {
		super();
		this.batchId = batchId;
		this.promotionId = promotionId;
		this.batchStatus = batchStatus;
	}

	String batchId;
	String promotionId;
	String batchStatus;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

}
