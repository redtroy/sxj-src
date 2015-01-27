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

@StateMachine(stateType = ContractSureStateEnum.class, startState = "NOAFFIRM", finalStates = { "FILINGS" })
public class ContractSureStateMachineConfig {

	@Autowired
	private IContractDao contractDao;

	@Transitions({ @Transition(source = "NOAFFIRM", event = "NOAFFIRMDAWPGLASS", target = "AAFFIRM") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "NOAFFIRM", event = "NOAFFIRMDAWPEXTRUSIONS", target = "AAFFIRM") })
	public void noop1(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "NOAFFIRM", event = "NOAFFIRMGLASSFACTORYGLASS", target = "BAFFIRM") })
	public void noop2(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "NOAFFIRM", event = "NOAFFIRMGENRESFACTORYEXTRUSIONS", target = "BAFFIRM") })
	public void noop3(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "NOAFFIRM", event = "NOAFFIRMDAWPBIDDING", target = "FILINGS") })
	public void noop4(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "AAFFIRM", event = "AAFFIRMGLASSFACTORYGLASS", target = "FILINGS") })
	public void noop5(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "AAFFIRM", event = "AAFFIRMGENRESFACTORYEXTRUSIONS", target = "FILINGS") })
	public void noop6(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "BAFFIRM", event = "BAFFIRMDAWPGLASS", target = "FILINGS") })
	public void noop7(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@Transitions({ @Transition(source = "BAFFIRM", event = "BAFFIRMDAWPEXTRUSIONS", target = "FILINGS") })
	public void noop8(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@OnEnter(value = "AAFFIRM")
	public EventInfo enterA(TransitionInfo event) {
		ContractEntity contract = (ContractEntity) event.getObject();
		contract.setConfirmState(ContractSureStateEnum.AAFFIRM);
		// contractDao.updateContract(contract);
		return null;
	}

	@OnEnter(value = "BAFFIRM")
	public EventInfo enterB(TransitionInfo event) {
		ContractEntity contract = (ContractEntity) event.getObject();
		contract.setConfirmState(ContractSureStateEnum.BAFFIRM);
		// contractDao.updateContract(contract);
		return null;
	}

	@OnEnter(value = "FILINGS")
	public EventInfo enterC(TransitionInfo event) {
		ContractEntity contract = (ContractEntity) event.getObject();
		contract.setRecordDate(new Date());
		contract.setConfirmState(ContractSureStateEnum.FILINGS);
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
