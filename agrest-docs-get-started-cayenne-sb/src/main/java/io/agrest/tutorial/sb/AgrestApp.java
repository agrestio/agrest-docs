package io.agrest.tutorial.sb;

import io.agrest.cayenne.AgCayenneBuilder;
import io.agrest.cayenne.AgCayenneModule;
import io.agrest.jaxrs2.AgJaxrsFeature;
import io.agrest.runtime.AgRuntime;
import io.agrest.tutorial.sb.api.AuthorApi;
import io.agrest.tutorial.sb.api.BookApi;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// tag::app[]
@SpringBootApplication
public class AgrestApp extends ResourceConfig {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AgrestApp.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    public AgrestApp() {

        // app configuration goes here
        // end::app[]

        // tag::basebootstrap[]
        // <1>
        ServerRuntime cayenneRuntime = ServerRuntime
                .builder()
                .addConfig("cayenne-project.xml")
                .build();

        // <2>
        AgCayenneModule agCayenneBackend = AgCayenneBuilder.build(cayenneRuntime);
        AgRuntime agRuntime = AgRuntime.builder().module(agCayenneBackend).build();

        // <3>
        AgJaxrsFeature agFeature = AgJaxrsFeature.build(agRuntime);
        register(agFeature);

        // <4>
        register(AuthorApi.class);
        register(BookApi.class);
        // end::basebootstrap[]

        // tag::app[]
    }
}
// end::app[]
