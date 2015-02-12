package com.sxj.supervisor.machine.pay;

import com.sxj.statemachine.EventInfo;
import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.supervisor.enu.contract.PayModeEnum;

@StateMachine(stateType = PayModeEnum.class, startState = "MODE1", finalStates = {
		"MODE3", "MODE4" }, name = "payModeFsm")
public class PayModeMachineConfig {

	/**
	 * 待支付-融资受理中（点击融资事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE1", event = "MODE1_STAGE1_Stage2_0", target = "MODE2_0") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 待支付-融资受理中（点击融资事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE1", event = "MODE1_STAGE2_Stage2_0", target = "MODE2_0") })
	public void noop01(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 待支付-融资受理中（点击融资事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE1", event = "MODE1_STAGE3_Stage2_0", target = "MODE2_0") })
	public void noop02(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 待支付-现金支付（现金支付事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE1", event = "MODE1_STAGE3_", target = "MODE3") })
	public void noop1(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资受理中-融资已搁置（点击融资未通过事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_0", event = "MODE2_0_STAGE1_Stage2_3", target = "MODE2_1") })
	public void noop2(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资受理中-融资已搁置（点击融资未通过事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_0", event = "MODE2_0_STAGE2_Stage2_3", target = "MODE2_1") })
	public void noop21(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资受理中-融资已放款（点击融资通过事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_0", event = "MODE2_0_STAGE1_Stage2_2", target = "MODE2_2") })
	public void noop3(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资受理中-融资已放款（点击融资通过事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_0", event = "MODE2_0_STAGE2_Stage2_2", target = "MODE2_2") })
	public void noop31(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资已放款-融资支付（点击融资通过事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_2", event = "MODE2_2_STAGE3_", target = "MODE4") })
	public void noop4(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	// /**
	// * 融资已放款-融资支付（点击融资通过事件）
	// *
	// * @param event
	// */
	// @Transitions({ @Transition(source = "MODE2_0", event =
	// "MODE2_0_STAGE3_Stage2_2", target = "MODE4") })
	// public void noop43(TransitionInfo event) {
	// System.out.println("tx@:" + event.getEvent());
	// }

	/**
	 * 融资已放款-融资支付（点击融资通过事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE3", event = "MODE3_STAGE3_Stage2_2", target = "MODE4") })
	public void noop41(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资已放款-融资支付（点击融资通过事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE4", event = "MODE4_STAGE3_Stage2_2", target = "MODE4") })
	public void noop42(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资已搁置-现金支付（重新进行现金支付事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_1", event = "MODE2_1_STAGE3_", target = "MODE3") })
	public void noop5(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资已搁置-现金支付（重新进行现金支付事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE3", event = "MODE3_STAGE3_Stage2_3", target = "MODE3") })
	public void noop51(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资已搁置-现金支付（重新进行现金支付事件）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE4", event = "MODE4_STAGE3_Stage2_3", target = "MODE3") })
	public void noop52(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	/**
	 * 融资受理中-现金支付（点击融资后点击现金支付）
	 * 
	 * @param event
	 */
	@Transitions({ @Transition(source = "MODE2_0", event = "MODE2_0_STAGE3_", target = "MODE3") })
	public void noop53(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@OnEnter(value = "MODE2_0")
	public EventInfo enter1(TransitionInfo event) {
		PayRecordEntity re = (PayRecordEntity) event.getObject();
		re.setPayMode(PayModeEnum.MODE2_0);
		return null;
	}

	@OnEnter(value = "MODE3")
	public EventInfo enter2(TransitionInfo event) {
		PayRecordEntity re = (PayRecordEntity) event.getObject();
		re.setPayMode(PayModeEnum.MODE3);
		return null;
	}

	@OnEnter(value = "MODE2_1")
	public EventInfo enter3(TransitionInfo event) {
		PayRecordEntity re = (PayRecordEntity) event.getObject();
		re.setPayMode(PayModeEnum.MODE2_1);
		return null;
	}

	@OnEnter(value = "MODE2_2")
	public EventInfo enter4(TransitionInfo event) {
		PayRecordEntity re = (PayRecordEntity) event.getObject();
		re.setPayMode(PayModeEnum.MODE2_2);
		return null;
	}

	@OnEnter(value = "MODE4")
	public EventInfo enter5(TransitionInfo event) {
		PayRecordEntity re = (PayRecordEntity) event.getObject();
		re.setPayMode(PayModeEnum.MODE4);
		return null;
	}
}
