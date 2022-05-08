package io.agrest.tutorial.sb.api;

import io.agrest.DataResponse;
import io.agrest.jaxrs2.AgJaxrs;
import io.agrest.tutorial.sb.persistence.Author;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("author")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorApi {

    @Context
    private Configuration config;

    @POST
    public DataResponse<Author> create(String data) {
        return AgJaxrs.create(Author.class, config).syncAndSelect(data);
    }
}
