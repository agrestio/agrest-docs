package io.agrest.docs.framework;

import io.agrest.DataResponse;
import io.agrest.SimpleResponse;
import io.agrest.docs.framework.model.Author;
import io.agrest.jaxrs3.AgJaxrs;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

public class RequestHandlersExample {

    private Configuration config;

    // tag::select[]
    @GET
    public DataResponse<Author> get(@Context UriInfo uri) {
        return AgJaxrs
                .select(Author.class, config)
                .clientParams(uri.getQueryParameters())
                .get();
    }
    // end::select[]

    // tag::create[]
    @POST
    public SimpleResponse create(String entityData) {
        return AgJaxrs
                .create(Author.class, config)
                .sync(entityData);
    }
    // end::create[]

    // tag::create_with_data[]
    @POST
    public DataResponse<Author> createWithData(@Context UriInfo uri, String entityData) {
        return AgJaxrs
                .create(Author.class, config)
                .clientParams(uri.getQueryParameters())
                .syncAndSelect(entityData);
    }
    // end::create_with_data[]

    // tag::delete[]
    @DELETE
    @Path("{id}")
    public SimpleResponse delete(@PathParam("id") long id) {
        return AgJaxrs
                .delete(Author.class, config)
                .byId(id)
                .sync();
    }
    // end::delete[]


    // tag::unrelate[]
    @PUT
    @Path("{id}")
    public SimpleResponse unrelate(@PathParam("id") long id) {
        return AgJaxrs
                .unrelate(Author.class, config)
                .sourceId(id)
                .allRelated("books")
                .sync();
    }
    // end::unrelate[]

}

