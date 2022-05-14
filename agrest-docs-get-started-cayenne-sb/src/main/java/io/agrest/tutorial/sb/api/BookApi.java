package io.agrest.tutorial.sb.api;

import io.agrest.DataResponse;
import io.agrest.SimpleResponse;
import io.agrest.jaxrs2.AgJaxrs;
import io.agrest.tutorial.sb.persistence.Book;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

// tag::base[]
@Path("book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookApi {

    @Context
    private Configuration config;
// end::base[]

// tag::post[]
    @POST
    public SimpleResponse create(String data) {
        return AgJaxrs.create(Book.class, config).sync(data);
    }
// end::post[]
    @GET
    public DataResponse<Book> get(@Context UriInfo uriInfo) {
        return AgJaxrs.select(Book.class, config).clientParams(uriInfo.getQueryParameters()).get();
    }
// tag::base[]
}
// end::base[]
