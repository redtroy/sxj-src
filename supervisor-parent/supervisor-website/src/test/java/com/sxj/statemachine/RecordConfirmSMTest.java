package com.sxj.statemachine;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
public class RecordConfirmSMTest {
	@Autowired
	@Qualifier("recordSM")
	private StateMachineImpl<RecordConfirmStateEnum> recordSM;

	@Test
	public void test() {
		// service.modifyCheckState("A", ContractStateEnum.approval);
		fail("Not yet implemented");
	}

}
