package io.agrest.docs.framework;

import io.agrest.DataResponse;
import io.agrest.docs.framework.model.Author;
import io.agrest.jaxrs3.AgJaxrs;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

// tag::all[]
@Path("author")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorApi {

    @Context
    private Configuration config; // <1>

    @GET
    public DataResponse<Author> get(@Context UriInfo uri) {
        return AgJaxrs.select(Author.class, config) // <2>
                .clientParams(uri.getQueryParameters())
                .get();
    }
}
// end::all[]

