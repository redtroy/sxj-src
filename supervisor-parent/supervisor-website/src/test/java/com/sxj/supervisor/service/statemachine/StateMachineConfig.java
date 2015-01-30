package com.sxj.supervisor.service.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.OnExit;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.supervisor.dao.member.IMemberDao;

@Transitions({ @Transition(source = "A", event = "AtoB", target = "B") })
@Service
public class StateMachineConfig {

	@Autowired
	IMemberDao memberDAO;

	@Transitions({ @Transition(source = "B", event = "BtoC", target = "C") })
	@Transactional
	public void noop(TransitionInfo event) {
		System.out.println("tx@:" + event.getEvent());
		// throw new RuntimeException("Testing rollback!!!!!!!!");
	}

	public void noop2(TransitionInfo event) {
		System.out.println("tx22222@:" + event.getEvent());
	}

	// @OnEnter(value = "B")
	// public EventInfo enterB(TransitionInfo event) {
	// System.out.println("Entered B state");
	// return null;
	// }
	//
	@OnExit("B")
	@Transactional
	public Boolean exitB(TransitionInfo event) {
		if (event == null)
			memberDAO.deleteMember("8RUlcJhqdAH2c60o1qNG3kCbJvGP40AT");
		else
			memberDAO.deleteMember((String) event.getObject());
		System.out.println("Exited A state");
		return true;
	}

	public void setMemberDAO(IMemberDao memberDAO) {
		this.memberDAO = memberDAO;
	}
}
