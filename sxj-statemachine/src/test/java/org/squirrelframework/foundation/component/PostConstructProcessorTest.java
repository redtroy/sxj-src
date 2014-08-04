package org.squirrelframework.foundation.component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.sxj.component.SquirrelPostProcessorProvider;
import com.sxj.component.SquirrelProvider;
import com.sxj.component.impl.PostConstructPostProcessorImpl;

public class PostConstructProcessorTest {
    
    @Test
    public void testPostConstruct() {
        SquirrelPostProcessorProvider.getInstance().register(Programmer.class, PostConstructPostProcessorImpl.class);
        Programmer p = SquirrelProvider.getInstance().newInstance(Programmer.class);
        assertThat(p.getName(), equalTo("Henry"));
        assertThat(p.getLanguage(), equalTo("Java"));
        SquirrelPostProcessorProvider.getInstance().unregister(Programmer.class);
    }
}
