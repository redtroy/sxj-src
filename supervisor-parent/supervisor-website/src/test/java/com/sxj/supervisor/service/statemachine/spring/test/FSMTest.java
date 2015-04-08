package com.sxj.supervisor.service.statemachine.spring.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/statemachine.xml" })
@TransactionConfiguration(defaultRollback = false)
public class FSMTest
{
    //	@Autowired
    //	private StateMachineImpl<State1> fsm1;
    //
    //	@Test
    //	public void test() throws StateMachineException {
    //		fsm1.setCurrentState(State1.A1);
    //		fsm1.fire("A1TOB1", null);
    //	}
    
}
