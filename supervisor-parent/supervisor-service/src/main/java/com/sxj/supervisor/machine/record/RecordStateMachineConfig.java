package com.sxj.supervisor.machine.record;

import com.sxj.statemachine.EventInfo;
import com.sxj.statemachine.TransitionInfo;
import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.record.RecordStateEnum;

@StateMachine(stateType = RecordStateEnum.class, startState = "NOBINDING", finalStates = {
        "BINDING" }, name = "recordStatefsm")
public class RecordStateMachineConfig
{
    
    @Transitions({
            @Transition(source = "NOBINDING", event = "NOBINDING", target = "BINDING"),
            @Transition(source = "BINDING", event = "BINDING", target = "BINDING") })
    public void noop(TransitionInfo event)
    {
        System.out.println("tx@:" + event.getEvent());
    }
    
    @OnEnter(value = "BINDING")
    public EventInfo enterB(TransitionInfo event)
    {
        RecordEntity record = (RecordEntity) event.getObject();
        record.setState(RecordStateEnum.BINDING);
        // recordDao.updateRecord(record);
        return null;
    }
    //
    // @OnExit("A")
    // public Boolean exitA(TransitionInfo event) {
    // System.out.println("Exited A state");
    // return true;
    // }
}
