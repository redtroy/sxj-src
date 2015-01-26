package com.sxj.supervisor.machine.contract;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.statemachine.EventInfo;
import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;

@StateMachine(stateType = ContractSureStateEnum.class, startState = "noaffirm", finalStates = { "filings" })
public class ContractSureStateMachineConfig {

	@Autowired
	private IContractDao contractDao;

	@Transitions({ @Transition(source = "noaffirm", event = "noaffirmDAWPglass", target = "aaffirm") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "noaffirm", event = "noaffirmDAWPextrusions", target = "aaffirm") })
	public void noop1(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "noaffirm", event = "noaffirmglassFactoryglass", target = "baffirm") })
	public void noop2(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "noaffirm", event = "noaffirmglassFactoryextrusions", target = "baffirm") })
	public void noop3(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "noaffirm", event = "noaffirmDAWPbidding", target = "filings") })
	public void noop4(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "aaffirm", event = "aaffirm", target = "filings") })
	public void noop5(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "baffirm", event = "baffirm", target = "filings") })
	public void noop6(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@OnEnter(value = "filings")
	public EventInfo enterB(TransitionInfo event) {
		ContractEntity contract = (ContractEntity) event.getObject();
		contract.setRecordDate(new Date());
		// contract.setConfirmState(ContractSureStateEnum.filings);
		// contractDao.updateContract(contract);
		return null;
	}

	// @OnExit(value = "aaffirm")
	// public Boolean exitAaffirm(TransitionInfo event) {
	// ContractEntity contract = (ContractEntity) event.getObject();
	// contract.setConfirmState(ContractSureStateEnum.filings);
	// contract.setRecordDate(new Date());
	// contractDao.updateContract(contract);
	// return true;
	// }
	//
	// @OnExit(value = "baffirm")
	// public Boolean exitBaffirm(TransitionInfo event) {
	// RecordEntity record = (RecordEntity) event.getObject();
	// record.setState(RecordStateEnum.Binding);
	// recordDao.updateRecord(record);
	// return null;
	// }

}
