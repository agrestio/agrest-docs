package io.agrest.docs.framework;

import io.agrest.converter.jsonvalue.JsonValueConverter;
import io.agrest.docs.framework.value.MoneyConverter;
import io.agrest.runtime.AgRuntime;
import org.glassfish.jersey.server.ResourceConfig;


public class AgRuntimeExample extends ResourceConfig {

    public void createRuntime() {

        // tag::create[]
        AgRuntime agRuntime = AgRuntime.builder().build();
        // end::create[]
    }

    public void createRuntime_Custom() {

        // tag::create-with-converter[]
        AgRuntime agRuntime = AgRuntime
                .builder()
                .module(b -> b
                        .bindMap(JsonValueConverter.class)
                        .put(MoneyConverter.class.getName(), MoneyConverter.class)) // <1>
                .build();
        // end::create-with-converter[]
    }

}


