package io.agrest.docs.framework;

import io.agrest.DataResponse;
import io.agrest.EntityUpdate;
import io.agrest.SimpleResponse;
import io.agrest.docs.framework.model.Author;
import io.agrest.jaxrs3.AgJaxrs;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.util.List;


@Path("author")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class POST_AuthorApi {

    @Context
    private Configuration config;

    // tag::post_create_and_forget[]
    @POST
    public SimpleResponse createAndForget(String data) { // <1>
        return AgJaxrs.create(Author.class, config) // <2>
                .sync(data); // <3>
    }
    // end::post_create_and_forget[]

    // tag::post_create_and_select[]
    @POST
    public DataResponse<Author> createAndSelect(String data) {
        return AgJaxrs.create(Author.class, config)
                .syncAndSelect(data); // <1>
    }
    // end::post_create_and_select[]

    // tag::post_create_entity_update[]
    @POST
    public DataResponse<Author> createWithEntityUpdate(
            EntityUpdate<Author> authorUpdate) { // <1>

        return AgJaxrs.create(Author.class, config)
                .syncAndSelect(authorUpdate);
    }
    // end::post_create_entity_update[]

    // tag::post_create_entity_updates[]
    @POST
    public DataResponse<Author> createWithEntityUpdates(
            List<EntityUpdate<Author>> authorUpdates) { // <1>

        return AgJaxrs.create(Author.class, config)
                .syncAndSelect(authorUpdates);
    }
    // end::post_create_entity_updates[]

}


