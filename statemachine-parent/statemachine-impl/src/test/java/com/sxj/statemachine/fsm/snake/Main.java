package com.sxj.statemachine.fsm.snake;

import com.sxj.statemachine.fsm.StateMachine;
import com.sxj.statemachine.fsm.StateMachineBuilderFactory;
import com.sxj.statemachine.fsm.StateMachineConfiguration;
import com.sxj.statemachine.fsm.UntypedStateMachine;
import com.sxj.statemachine.fsm.UntypedStateMachineBuilder;
import com.sxj.statemachine.fsm.StateMachine.TransitionCompleteEvent;
import com.sxj.statemachine.fsm.snake.SnakeController.SnakeEvent;
import com.sxj.statemachine.fsm.snake.SnakeController.SnakeState;

public class Main {
    
    public static void main(String[] args) {
        final SnakeModel gameModel = new SnakeModel();
        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(SnakeController.class);
        builder.setStateMachineConfiguration(StateMachineConfiguration.create().enableRemoteMonitor(true));
        
        // define timed state
        builder.defineTimedState(SnakeState.UP, 0, GameConfigure.FRAME_TIME, SnakeEvent.MOVE_AHEAD, gameModel);
        builder.defineTimedState(SnakeState.DOWN, 0, GameConfigure.FRAME_TIME, SnakeEvent.MOVE_AHEAD, gameModel);
        builder.defineTimedState(SnakeState.RIGHT, 0, GameConfigure.FRAME_TIME, SnakeEvent.MOVE_AHEAD, gameModel);
        builder.defineTimedState(SnakeState.LEFT, 0, GameConfigure.FRAME_TIME, SnakeEvent.MOVE_AHEAD, gameModel);
        
        SnakeController controller = builder.newUntypedStateMachine(SnakeState.NEW);
        final SnakeGame game = new SnakeGame(controller, gameModel);
        controller.addTransitionCompleteListener(new StateMachine.TransitionCompleteListener<UntypedStateMachine, Object, Object, Object>() {
            @Override
            public void transitionComplete(TransitionCompleteEvent<UntypedStateMachine, Object, Object, Object> event) {
                game.repaint();
                game.setTitle("Greedy Snake("+gameModel.length()+"): "+
                        event.getSourceState()+"--["+event.getCause()+"]--"+event.getTargetState());
            }
        });
        controller.export();
        game.startGame();
    }
}
