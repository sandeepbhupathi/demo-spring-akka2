package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.di.SpringExtension;
import com.example.demo.dto.SingleUseCouponMessage;
import com.example.demo.dto.SingleUseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

@RestController
@RequestMapping("/pcms")
public class SingleUseCouponController {

	@Autowired
	private ActorSystem system;
	
	@Autowired
	private SpringExtension extension;
	
	
	@RequestMapping("/invokeActor")
	public String invokeActorCommunication(@RequestParam("promotionId") String message){
		ActorRef parentActor = system.actorOf(extension.props("singleUseParent",system.actorOf(extension.props("singleUseChild"))));
		parentActor.tell(message, ActorRef.noSender());
		return "invoked";
	}
	
	@RequestMapping("/singleUseCoupon")
	public String createSingleUseCoupon(@RequestParam("promotionId") String promotionId,
			@RequestParam("noOfCoupons") int noOfCoupons) throws JsonProcessingException{
		String batchId = UUID.randomUUID().toString();
		SingleUseCouponMessage message = new SingleUseCouponMessage(batchId, noOfCoupons,promotionId,0);
		getSingleUseParentChildActor(message).tell(message, ActorRef.noSender());
		return singleUseResponse(batchId,message);
	}
	
	public ActorRef getSingleUseParentChildActor(SingleUseCouponMessage message) {
		return system.actorOf(extension.props("singleUseParent", system.actorOf(extension.props("singleUseChild"))));
	}
	
	private String singleUseResponse(String batchId, SingleUseCouponMessage message) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(
				new SingleUseResponse(batchId,message.getPromotionID(),"INPROCESS"));
	}
}
