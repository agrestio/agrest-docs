package io.agrest.docs.framework;

import io.agrest.jaxrs3.AgJaxrsFeature;
import io.agrest.runtime.AgRuntime;
import org.glassfish.jersey.server.ResourceConfig;

// tag::all[]
public class AgrestJerseyApp extends ResourceConfig { // <1>

    public AgrestJerseyApp() {

        // <2>
        AgRuntime runtime = AgRuntime
                .builder()
                // add backend configurations and runtime extensions
                // .module(...)
                .build();

        // <3>
        AgJaxrsFeature agJaxRs = AgJaxrsFeature.build(runtime);
        super.register(agJaxRs);

        // <4>
        super.register(AuthorApi.class);
        super.register(BookApi.class);
    }
}
// end::all[]

