package com.pra.config;

import able.guice.configuration.ConfigurationModule;
import java.util.TimeZone;

public class ableConfiguration extends ConfigurationModule {
    protected void bindConfiguration() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kuala_Lumpur"));
    }
}
