package com.pra.config;
import able.guice.AbleModule;
import com.google.inject.Binder;
import com.pra.stripes.ableStripesModule;


public class ableModule extends AbleModule {
    public ableModule() {
        super(new ableConfiguration(), new ableStripesModule());
    }

    @Override
    public void configure(Binder binder) {
        super.configure(binder);       
    }
}
