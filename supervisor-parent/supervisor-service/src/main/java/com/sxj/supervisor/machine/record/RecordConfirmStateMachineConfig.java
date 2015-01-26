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
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;

@StateMachine(stateType = RecordConfirmStateEnum.class, startState = "accepted", finalStates = { "hasRecord" })
public class RecordConfirmStateMachineConfig {

	@Autowired
	private IRecordDao recordDao;

	@Transitions({ @Transition(source = "accepted", event = "accepted", target = "unconfirmed") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "unconfirmed", event = "unconfirmedDAWPextrusions", target = "confirmedA") })
	public void noop1(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "unconfirmed", event = "unconfirmedDAWPglass", target = "confirmedA") })
	public void noop2(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "unconfirmed", event = "unconfirmedglassFactoryglass", target = "confirmedB") })
	public void noop3(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "unconfirmed", event = "unconfirmedglassFactoryextrusions", target = "confirmedB") })
	public void noop4(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "unconfirmed", event = "unconfirmedDAWPbidding", target = "hasRecord") })
	public void noop5(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "confirmedA", event = "confirmedA", target = "hasRecord") })
	public void noop6(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "confirmedB", event = "confirmedB", target = "hasRecord") })
	public void noop7(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@OnEnter(value = "confirmedA")
	public EventInfo enterB(TransitionInfo event) {
		// RecordEntity record = (RecordEntity) event.getObject();
		// record.setState(RecordStateEnum.Binding);
		// recordDao.updateRecord(record);
		return null;
	}

	@OnEnter(value = "hasRecord")
	public EventInfo enterHasRecord(TransitionInfo event) {
		RecordEntity record = (RecordEntity) event.getObject();
		recordDao.batchUpdateConfirmState(record.getContractNo());
		return null;
	}
	//
	// @OnExit("A")
	// public Boolean exitA(TransitionInfo event) {
	// System.out.println("Exited A state");
	// return true;
	// }
}
