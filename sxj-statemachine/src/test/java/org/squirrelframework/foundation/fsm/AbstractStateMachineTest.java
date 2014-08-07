package org.squirrelframework.foundation.fsm;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.sxj.fsm.ConverterProvider;

public class AbstractStateMachineTest {
    
    @BeforeClass
    public static void beforeTest() {
    }
    
    @AfterClass
    public static void afterTest() {
        ConverterProvider.INSTANCE.clearRegistry();
    }
}
