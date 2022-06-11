package io.agrest.tutorial.sb;

import io.agrest.cayenne.AgCayenneModule;
import io.agrest.jaxrs2.AgJaxrsFeature;
import io.agrest.jaxrs2.openapi.AgSwaggerModule;
import io.agrest.runtime.AgRuntime;
import io.agrest.tutorial.sb.api.AuthorApi;
import io.agrest.tutorial.sb.api.BookApi;
import io.agrest.tutorial.sb.persistence.Book;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.ApplicationPath;

// tag::app[]
@ApplicationPath("/api")
@SpringBootApplication
public class AgrestApp extends ResourceConfig { // <1>

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AgrestApp.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    public AgrestApp() {

        // <2>
        ServerRuntime cayenneRuntime = ServerRuntime
                .builder()
                .addConfig("cayenne-project.xml")
                .build();

        // <3>
        // tag::openapi-bootstrap[]
        AgRuntime agRuntime = AgRuntime
                .builder()
                .module(AgCayenneModule.build(cayenneRuntime))
                // end::app[]
                .module(AgSwaggerModule.builder().entityPackage(Book.class.getPackage()).build()) // <1>
                // tag::app[]
                .build();
        // end::openapi-bootstrap[]

        // <4>
        register(AgJaxrsFeature.build(agRuntime));

        // <5>
        register(AuthorApi.class);
        register(BookApi.class);
        // end::app[]

        // tag::openapi-bootstrap[]
        register(OpenApiResource.class); // <2>
        // end::openapi-bootstrap[]

        // tag::app[]
    }
}
// end::app[]
