package com.sxj.supervisor.machine.pay;

import com.sxj.statemachine.EventInfo;
import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.supervisor.enu.contract.PayModeEnum;

@StateMachine(stateType = PayModeEnum.class, startState = "MDDE1", finalStates = {
		"MDDE3", "MDDE4" }, name = "payModeFsm")
public class PayModeMachineConfig {

	/**
	 * 待支付-融资受理中（点击融资事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MDDE1", event = "MDDE1_COST", target = "MODE2_0") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 待支付-现金支付（现金支付事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MDDE1", event = "MDDE1_PAY", target = "MDDE3") })
	public void noop1(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资受理中-融资已搁置（点击融资未通过事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_0", event = "MODE2_0_CANCEL", target = "MODE2_1") })
	public void noop2(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资受理中-融资已放款（点击融资通过事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_0", event = "MODE2_0_CONFIRM", target = "MODE2_2") })
	public void noop3(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资已放款-融资支付（点击融资通过事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_2", event = "MODE2_2", target = "MODE4") })
	public void noop4(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资已搁置-现金支付（重新进行现金支付事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_1", event = "MODE2_1", target = "MDDE3") })
	public void noop5(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@OnEnter(value = "MODE2_0")
	public EventInfo enter1(TransitionInfo event) {
		// 业务处理
		return null;
	}

	@OnEnter(value = "MDDE3")
	public EventInfo enter2(TransitionInfo event) {
		// 业务处理
		return null;
	}

	@OnEnter(value = "MODE2_1")
	public EventInfo enter3(TransitionInfo event) {
		// 业务处理
		return null;
	}

	@OnEnter(value = "MODE2_2")
	public EventInfo enter4(TransitionInfo event) {
		// 业务处理
		return null;
	}

	@OnEnter(value = "MODE4")
	public EventInfo enter5(TransitionInfo event) {
		// 业务处理
		return null;
	}
}
