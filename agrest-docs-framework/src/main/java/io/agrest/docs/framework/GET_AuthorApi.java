package io.agrest.docs.framework;

import io.agrest.AgRequest;
import io.agrest.DataResponse;
import io.agrest.docs.framework.model.Author;
import io.agrest.docs.framework.model.Book;
import io.agrest.jaxrs3.AgJaxrs;
import io.agrest.meta.AgEntity;
import io.agrest.meta.AgEntityOverlay;
import io.agrest.protocol.Exp;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import java.time.LocalDate;
import java.util.List;


@Path("author")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GET_AuthorApi {

    @Context
    private Configuration config;

    // tag::get_all_params[]
    @GET
    public DataResponse<Author> get(@Context UriInfo uri) {
        return AgJaxrs.select(Author.class, config) // <1>
                .clientParams(uri.getQueryParameters()) // <2>
                .get(); // <3>
    }
    // end::get_all_params[]

    // tag::get_by_id[]
    @GET
    @Path("{id}")
    public DataResponse<Author> getById(
            @PathParam("id") long id,  // <1>
            @Context UriInfo uri) {
        return AgJaxrs.select(Author.class, config)
                .byId(id) // <2>
                .clientParams(uri.getQueryParameters())
                .getOne(); // <3>
    }
    // end::get_by_id[]


    // tag::get_include_sort[]
    @GET
    public DataResponse<Author> getIncludeAndSort(
            @QueryParam("include") List<String> includes,  // <1>
            @QueryParam("sort") String sort,
            @QueryParam("direction") String direction) {

        AgRequest request = AgJaxrs.request(config) // <2>
                .addIncludes(includes)
                .addSort(sort, direction)
                .build();

        return AgJaxrs.select(Author.class, config)
                .request(request) // <3>
                .get();
    }
    // end::get_include_sort[]

    // tag::get_server_side_rules[]
    @GET
    public DataResponse<Author> getModernAuthors(
            @QueryParam("include") List<String> includes,
            @QueryParam("exp") String urlExp) {

        AgRequest request = AgJaxrs.request(config)
                .addIncludes(includes)
                .andExp(Exp.keyValue("dateOfBirth", ">=", LocalDate.of(1970, 1, 1))) // <1>
                .andExp(urlExp) // <2>
                .addSort("name", "asc") // <3>
                .build();

        return AgJaxrs.select(Author.class, config)
                .request(request)
                .get();
    }
    // end::get_server_side_rules[]


    // tag::get_for_author[]
    @GET
    @Path("{author_id}/books") // <1>
    public DataResponse<Book> booksForAuthor(
            @PathParam("author_id") long authorId, // <2>
            @Context UriInfo uri) {

        return AgJaxrs.select(Book.class, config)
                .parent(Author.class, authorId, "books") // <3>
                .clientParams(uri.getQueryParameters())
                .get();
    }
    // end::get_for_author[]

    // tag::get_with_overlay[]
    @GET
    public DataResponse<Author> getWithOverlay(@Context UriInfo uri) {
        AgEntityOverlay<Book> bookModelChanges = AgEntity.overlay(Book.class);

        if (!isAdminRole()) {  // <1>
            bookModelChanges.readablePropFilter(b -> b.property("copiesSold", false));
        }

        return AgJaxrs.select(Author.class, config)
                .clientParams(uri.getQueryParameters())
                .entityOverlay(bookModelChanges) // <2>
                .get();
    }
    // end::get_with_overlay[]

    private boolean isAdminRole() {
        return false;
    }
}


