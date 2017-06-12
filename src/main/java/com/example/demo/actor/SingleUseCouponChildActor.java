package com.example.demo.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.dto.SingleUseCouponFailMessage;
import com.example.demo.dto.SingleUseCouponMessage;
import com.example.demo.service.SingleUseCouponService;

import akka.actor.AbstractActor;

@Component("singleUseChild")
@Scope("prototype")
public class SingleUseCouponChildActor extends AbstractActor{

	@Autowired
	private SingleUseCouponService singleUseCouponService;
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(SingleUseCouponMessage.class, singleUseMsg -> {
			try {
				singleUseCouponService.createSingleUseCoupon(singleUseMsg);
			} catch (Exception e) {
				System.out.println("Exception With Child :" + singleUseMsg.getBatchId() + " Coupons Left: "
						+ (singleUseMsg.getNoOfCoupons() - singleUseMsg.getNoOfCouponsCreated()));
				sender().tell(
						new SingleUseCouponFailMessage(singleUseMsg.getBatchId(), 
								singleUseMsg.getPromotionID(),
								(singleUseMsg.getNoOfCoupons() - singleUseMsg.getNoOfCouponsCreated()),
								singleUseMsg.getRetryCount()+1),
						self());
			}
			sender().tell(singleUseMsg.getBatchId(), self());
		}).build();
	}
	

}
