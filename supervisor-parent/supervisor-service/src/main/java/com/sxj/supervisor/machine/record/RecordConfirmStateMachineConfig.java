package com.sxj.supervisor.machine.record;

import java.util.Date;

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
import com.sxj.supervisor.enu.record.RecordFlagEnum;

@StateMachine(stateType = RecordConfirmStateEnum.class, startState = "ACCEPTED", finalStates = { "HASRECORD" })
public class RecordConfirmStateMachineConfig {

	@Autowired
	private IRecordDao recordDao;

	@Transitions({ @Transition(source = "ACCEPTED", event = "ACCEPTED", target = "UNCONFIRMED") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "UNCONFIRMED", event = "UNCONFIRMEDDAWPEXTRUSIONS", target = "CONFIRMEDA") })
	public void noop1(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "UNCONFIRMED", event = "UNCONFIRMEDDAWPGLASS", target = "CONFIRMEDA") })
	public void noop2(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "UNCONFIRMED", event = "UNCONFIRMEDGLASSFACTORYGLASS", target = "CONFIRMEDB") })
	public void noop3(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "UNCONFIRMED", event = "UNCONFIRMEDGENRESFACTORYEXTRUSIONS", target = "CONFIRMEDB") })
	public void noop4(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "UNCONFIRMED", event = "UNCONFIRMEDDAWPBIDDING", target = "HASRECORD") })
	public void noop5(TransitionInfo event) {

		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "CONFIRMEDA", event = "CONFIRMEDAGLASSFACTORYGLASS", target = "HASRECORD") })
	public void noop6(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "CONFIRMEDA", event = "CONFIRMEDAGENRESFACTORYEXTRUSIONS", target = "HASRECORD") })
	public void noop7(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "CONFIRMEDB", event = "CONFIRMEDBDAWPGLASS", target = "HASRECORD") })
	public void noop8(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "CONFIRMEDB", event = "CONFIRMEDBDAWPEXTRUSIONS", target = "HASRECORD") })
	public void noop9(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@OnEnter(value = "CONFIRMEDA")
	public EventInfo enterA(TransitionInfo event) {
		RecordEntity record = (RecordEntity) event.getObject();
		record.setConfirmState(RecordConfirmStateEnum.CONFIRMEDA);
		// recordDao.updateRecord(record);
		return null;
	}

	@OnEnter(value = "CONFIRMEDB")
	public EventInfo enterB(TransitionInfo event) {
		RecordEntity record = (RecordEntity) event.getObject();
		record.setConfirmState(RecordConfirmStateEnum.CONFIRMEDB);
		// recordDao.updateRecord(record);
		return null;
	}

	@OnEnter(value = "HASRECORD")
	public EventInfo enterHasRecord(TransitionInfo event) {
		RecordEntity record = (RecordEntity) event.getObject();
		record.setConfirmState(RecordConfirmStateEnum.HASRECORD);
		if (record.getRecordState() != null) {
			if (record.getRecordState().intValue() != 1) {
				record.setRecordDate(new Date());
				if (record.getFlag().equals(RecordFlagEnum.A)) {
					record.setRecordState(1);
				}

			}
		}
		// recordDao.batchUpdateConfirmState(record.getContractNo());
		return null;
	}
	//
	// @OnExit("A")
	// public Boolean exitA(TransitionInfo event) {
	// System.out.println("Exited A state");
	// return true;
	// }
}
