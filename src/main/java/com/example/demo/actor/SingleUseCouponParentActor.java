package com.example.demo.actor;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.resume;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.dto.SingleUseCouponFailMessage;
import com.example.demo.dto.SingleUseCouponMessage;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

@Component("singleUseParent")
@Scope("prototype")
public class SingleUseCouponParentActor extends AbstractActor{
	
	private ActorRef childActorRef;
	
	public SingleUseCouponParentActor(ActorRef childActorRef) {
		super();
		this.childActorRef = childActorRef;
	}
	private SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder.
                    match(Exception.class, e -> resume()).
                    matchAny(o -> escalate()).build());
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(SingleUseCouponMessage.class, singleUseMsg -> {
			createChildWorker(singleUseMsg);
		}).match(String.class, childMessage -> {
			System.out.println(":::::::::::::::::::::Completed for child:::::::::::::::::::::" + childMessage);
		}).match(SingleUseCouponFailMessage.class, childFailCount -> {
			System.out.println(":::::::::::::::::::::Resuming Child:::::::::::::::::::::" + childFailCount.getBatchId());
			if(childFailCount.getRetryCount()<3){
				childActorRef.tell(new SingleUseCouponMessage("FailChild-" + childFailCount.getBatchId(),
					childFailCount.getFailNoOfCoupons(), childFailCount.getPromotionID(),
					childFailCount.getRetryCount()), self());
			}
		}).build();
	}

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
	private void createChildWorker(SingleUseCouponMessage singleUseMsg) {
		int noOfCouponsOfChild = singleUseMsg.getNoOfCoupons() / 8;
		int remainingCount = singleUseMsg.getNoOfCoupons() % 8;
		
		System.out.println("Remaining coupon for child::"+remainingCount);
		for (int j = 0; j < 8; j++) {
			childActorRef.tell(
					new SingleUseCouponMessage("Child-" + j, noOfCouponsOfChild, singleUseMsg.getPromotionID(),0),
					self());
		}
		if (remainingCount > 0) {
			childActorRef.tell(new SingleUseCouponMessage("Child-9", remainingCount, singleUseMsg.getPromotionID(),0),
					self());
		}
	}
	
}
