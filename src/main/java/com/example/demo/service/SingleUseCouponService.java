package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CouponsDAO;
import com.example.demo.dto.SingleUseCouponMessage;

@Service
public class SingleUseCouponService {

	@Autowired
	private CouponsDAO couponDao;
	public void createSingleUseCoupon(SingleUseCouponMessage message) throws Exception {
		/*if(message.getBatchId().equalsIgnoreCase("Child-7")){
			throw new Exception();
		}*/
		couponDao.saveSingleUseCoupons(message);
	}
}
