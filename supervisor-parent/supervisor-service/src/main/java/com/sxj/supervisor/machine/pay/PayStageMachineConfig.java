package com.sxj.supervisor.machine.pay;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sxj.statemachine.EventInfo;
import com.sxj.statemachine.StateMachineImpl;
import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.statemachine.exceptions.StateMachineException;
import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.supervisor.enu.contract.PayModeEnum;
import com.sxj.supervisor.enu.contract.PayStageEnum;

@StateMachine(stateType = PayStageEnum.class, startState = "STAGE1", finalStates = { "STAGE3" }, name = "payStagefsm")
public class PayStageMachineConfig {

	@Autowired
	@Qualifier("payModeFsm")
	private StateMachineImpl<PayModeEnum> payModeFsm;

	@Transitions({ @Transition(source = "STAGE1", event = "STAGE1_A", target = "STAGE2") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "STAGE2", event = "STAGE2_B", target = "STAGE3") })
	public void noop1(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@OnEnter(value = "STAGE2")
	public EventInfo enter2(TransitionInfo event) {
		PayRecordEntity re = (PayRecordEntity) event.getObject();
		re.setState(PayStageEnum.STAGE2);
		re.setPayDate(new Date());
		return null;
	}

	@OnEnter(value = "STAGE3")
	public EventInfo enter3(TransitionInfo event) {
		PayRecordEntity re = (PayRecordEntity) event.getObject();
		re.setState(PayStageEnum.STAGE3);
		payModeFsm.setCurrentState(re.getPayMode());
		try {
			payModeFsm.fire(re.getPayMode().toString() + "_"
					+ re.getState().toString() + "_", re);
		} catch (StateMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
