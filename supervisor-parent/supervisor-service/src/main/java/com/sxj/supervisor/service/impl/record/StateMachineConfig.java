package com.sxj.supervisor.service.impl.record;

import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;

@StateMachine(stateType = RecordConfirmStateEnum.class, startState = "accepted", finalStates = { "hasRecord" })
public class StateMachineConfig {

	@Transitions({ @Transition(source = "accepted", event = "ac_un", target = "unconfirmed") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "unconfirmed", event = "un_conA", target = "confirmedA") })
	public void noop1(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "unconfirmed", event = "un_conB", target = "confirmedB") })
	public void noop2(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "confirmedA", event = "conB_has", target = "hasRecord") })
	public void noop3(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "confirmedB", event = "conA_has", target = "hasRecord") })
	public void noop4(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	// @OnEnter(value = "B")
	// public EventInfo enterB(TransitionInfo event) {
	// System.out.println("Entered B state");
	// return null;
	// }
	//
	// @OnExit("A")
	// public Boolean exitA(TransitionInfo event) {
	// System.out.println("Exited A state");
	// return true;
	// }
}
