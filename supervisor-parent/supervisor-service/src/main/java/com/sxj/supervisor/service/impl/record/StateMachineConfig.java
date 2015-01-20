package com.sxj.supervisor.service.impl.record;

import com.sxj.statemachine.EventInfo;
import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.annotations.OnExit;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;

@StateMachine(stateType = RecordConfirmStateEnum.class, startState = "unconfirmed", finalStates = {
		"confirmedA", "confirmedB", "hasRecord", "accepted" })
@Transitions({ @Transition(source = "A", event = "AtoB", target = "B", callee = "noop2") })
public class StateMachineConfig {

	@Transitions({ @Transition(source = "B", event = "BtoC", target = "C") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	public void noop2(TransitionInfo event) {
		System.out.println("tx22222@:" + event.getEvent());
	}

	@OnEnter(value = "B")
	public EventInfo enterB(TransitionInfo event) {
		System.out.println("Entered B state");
		return null;
	}

	@OnExit("A")
	public Boolean exitA(TransitionInfo event) {
		System.out.println("Exited A state");
		return true;
	}
}
