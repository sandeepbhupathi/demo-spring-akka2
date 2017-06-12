package com.example.demo.dao;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.example.demo.dto.SingleUseCouponMessage;

@Repository
public class CouponsDAO {
	
	@Autowired
	private Session session;

	public boolean saveSingleUseCoupons(SingleUseCouponMessage message) throws Exception {
		BatchStatement batchStatement = new BatchStatement();
		int insertCounter = 0;
		try{
			for (int i = 0; i < message.getNoOfCoupons(); i++) {
				batchStatement.add(getSimpleStatement(message));
				insertCounter++;
				if((message.getNoOfCoupons()<=500 && insertCounter==message.getNoOfCoupons())){
					session.execute(batchStatement);
					message.setNoOfCouponsCreated(message.getNoOfCouponsCreated()+(insertCounter));
					batchStatement.clear();
					System.out.println("::::::No of Coupons Create:::::"+message.getNoOfCouponsCreated());
				}else if (message.getNoOfCoupons()>500 && insertCounter == 500) {
					session.execute(batchStatement);
					message.setNoOfCouponsCreated(message.getNoOfCouponsCreated()+insertCounter);
					insertCounter = 0;
					batchStatement.clear();
					System.out.println("::::::No of Coupons Create:::::"+message.getNoOfCouponsCreated());
				}
			}
			if (message.getNoOfCoupons() > 500 && message.getNoOfCoupons() % 500 > 0
					&& (message.getNoOfCoupons() != message.getNoOfCouponsCreated())) {
				session.execute(batchStatement);
				message.setNoOfCouponsCreated(message.getNoOfCouponsCreated() + insertCounter);
			}
		}catch(Exception ex){
			System.out.println("Exception Message:: "+ex.getMessage());
			throw new Exception();
		}finally{
			//session.close();
		}
		return true;
	}

	private Statement getSimpleStatement(SingleUseCouponMessage message) {
		StringBuilder query = new StringBuilder(
				"insert into claimable (claimable_id, parent_folder,promotion_id, status) values (");
		query.append(UUID.randomUUID().toString() + ",").append("'Test',").append("'" + message.getPromotionID() + "',")
				.append("0").append(")");
		return new SimpleStatement(query.toString());
	}

}
