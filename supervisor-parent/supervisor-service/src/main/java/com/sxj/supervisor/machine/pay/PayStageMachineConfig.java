package com.sxj.supervisor.machine.pay;

import com.sxj.statemachine.EventInfo;
import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.supervisor.enu.contract.PayStageEnum;

@StateMachine(stateType = PayStageEnum.class, startState = "STAGE1", finalStates = { "STAGE3" }, name = "payStagefsm")
public class PayStageMachineConfig {

	@Transitions({ @Transition(source = "STAGE1", event = "STAGE1", target = "STAGE2") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "STAGE2", event = "STAGE2", target = "STAGE3") })
	public void noop1(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@OnEnter(value = "STAGE2")
	public EventInfo enter2(TransitionInfo event) {
		// 业务处理
		return null;
	}

	@OnEnter(value = "STAGE3")
	public EventInfo enter3(TransitionInfo event) {
		// 业务处理
		return null;
	}

}
