package com.sxj.supervisor.service.rfid;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.redis.advance.RedisConcurrent;
import com.sxj.redis.advance.core.RAtomicLong;
import com.sxj.util.common.DateTimeUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TestRedisConcurrent {

	@Autowired
	RedisConcurrent redisConcurrent;

	@Test
	public void test() {
		RAtomicLong lon = redisConcurrent.getAtomicLong("test222",
				DateTimeUtils.getSecondOffset(new Date(), 5));
		System.out.println(lon.get());
		long compareAndSet = lon.incrementAndGet();
		System.out.println(compareAndSet);
		System.out.println(DateTimeUtils.getSecondOffset(new Date(), 5)
				.getTime());
	}

}
