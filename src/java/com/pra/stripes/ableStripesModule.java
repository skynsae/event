package com.pra.stripes;

import able.stripes.StripesModule;

import com.google.inject.Binder;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.pra.stripes.config.AuthRequired;
import java.lang.reflect.Method;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;

public class ableStripesModule extends StripesModule {
    public void configure(Binder binder) {
        super.configure(binder);

        Matcher<Method> stripesActionMatcher = Matchers.returns(Matchers.subclassesOf(Resolution.class))
                .or(Matchers.annotatedWith(DefaultHandler.class).or(Matchers.annotatedWith(HandlesEvent.class)));

       

    }
}
