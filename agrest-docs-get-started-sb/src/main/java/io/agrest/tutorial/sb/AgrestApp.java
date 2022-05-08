package io.agrest.tutorial.sb;

import io.agrest.cayenne.AgCayenneBuilder;
import io.agrest.cayenne.AgCayenneModule;
import io.agrest.jaxrs2.AgJaxrsFeature;
import io.agrest.runtime.AgRuntime;
import io.agrest.tutorial.sb.api.BookApi;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class AgrestApp extends ResourceConfig {

    public AgrestApp() {

        // Setup Agrest Cayenne ORM backend
        ServerRuntime cayenneRuntime = ServerRuntime.builder().addConfig("cayenne-project.xml").build();
        AgCayenneModule agCayenneBackend = AgCayenneBuilder.build(cayenneRuntime);

        // Setup Agrest runtime
        AgRuntime agRuntime = AgRuntime.builder().module(agCayenneBackend).build();

        // Register Agrest runtime with Jersey / JAX-RS environment
        AgJaxrsFeature agFeature = AgJaxrsFeature.builder().runtime(agRuntime).build();
        register(agFeature);

        // Register API endpoints
        register(BookApi.class);
    }
}
