package com.sxj.supervisor.machine.contract;

import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.statemachine.EventInfo;
import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.service.contract.IContractProcessService;

@StateMachine(stateType = ContractStateEnum.class, startState = "NOAPPROVAL", finalStates = { "APPROVAL" })
public class ContractStateMachineConfig {

	/**
	 * 合同DAO
	 */
	@Autowired
	private IContractProcessService contractService;

	@Transitions({ @Transition(source = "NOAPPROVAL", event = "NOAPPROVAL", target = "APPROVAL") })
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
	}

	@OnEnter(value = "APPROVAL")
	public EventInfo enterB(TransitionInfo event) {
		ContractEntity contract = (ContractEntity) event.getObject();
		contract.setState(ContractStateEnum.APPROVAL);
		contractService.modifyCheckState(contract);
		return null;
	}
	//
	// @OnExit("A")
	// public Boolean exitA(TransitionInfo event) {
	// System.out.println("Exited A state");
	// return true;
	// }
}
