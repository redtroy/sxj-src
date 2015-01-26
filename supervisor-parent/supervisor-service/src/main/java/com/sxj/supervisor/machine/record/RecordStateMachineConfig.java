package com.sxj.supervisor.machine.record;

import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.statemachine.EventInfo;
import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.record.RecordStateEnum;

@StateMachine(stateType = RecordStateEnum.class, startState = "noBinding", finalStates = { "Binding" })
public class RecordStateMachineConfig {

	@Autowired
	private IRecordDao recordDao;

	@Transitions({ @Transition(source = "noBinding", event = "noBinding", target = "Binding") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@OnEnter(value = "Binding")
	public EventInfo enterB(TransitionInfo event) {
		RecordEntity record = (RecordEntity) event.getObject();
		record.setState(RecordStateEnum.Binding);
		recordDao.updateRecord(record);
		return null;
	}
	//
	// @OnExit("A")
	// public Boolean exitA(TransitionInfo event) {
	// System.out.println("Exited A state");
	// return true;
	// }
}
