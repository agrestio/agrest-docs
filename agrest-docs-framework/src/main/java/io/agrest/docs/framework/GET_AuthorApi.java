package io.agrest.docs.framework;

import io.agrest.AgRequest;
import io.agrest.DataResponse;
import io.agrest.SelectBuilder;
import io.agrest.docs.framework.model.Author;
import io.agrest.docs.framework.model.Book;
import io.agrest.jaxrs3.AgJaxrs;
import io.agrest.meta.AgEntity;
import io.agrest.meta.AgEntityOverlay;
import io.agrest.protocol.ControlParams;
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
            @QueryParam(ControlParams.INCLUDE) List<String> includes,  // <1>
            @QueryParam(ControlParams.SORT) String sort,
            @QueryParam(ControlParams.DIRECTION) String direction) {

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
            @QueryParam(ControlParams.INCLUDE) List<String> includes,
            @QueryParam(ControlParams.EXP) String urlExp) {

        AgRequest request = AgJaxrs.request(config)
                .addIncludes(includes)
                .andExp(Exp.greaterOrEqual("dateOfBirth", LocalDate.of(1970, 1, 1))) // <1>
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

        SelectBuilder<Author> builder = AgJaxrs
                .select(Author.class, config)
                .clientParams(uri.getQueryParameters());

        if (!isAdminRole()) {  // <1>

            AgEntityOverlay<Book> bookModelChanges = AgEntity
                    .overlay(Book.class)
                    .readablePropFilter(b -> b.property("copiesSold", false));

            builder.entityOverlay(bookModelChanges); // <2>
        }

        return builder.get();
    }
    // end::get_with_overlay[]

    // tag::get_with_prop_filter[]
    @GET
    public DataResponse<Author> getWithPropFilter(@Context UriInfo uri) {

        SelectBuilder<Author> builder = AgJaxrs
                .select(Author.class, config)
                .clientParams(uri.getQueryParameters());

        if (!isAdminRole()) {
            builder.propFilter(Book.class, pfb -> pfb.property("copiesSold", false)); // <1>
        }

        return builder.get();
    }
    // end::get_with_prop_filter[]

    // tag::get_with_read_filter[]
    @GET
    public DataResponse<Author> getWithReadFilter(@Context UriInfo uri) {

        SelectBuilder<Author> builder = AgJaxrs
                .select(Author.class, config)
                .clientParams(uri.getQueryParameters());

        if (isModernAuthorsOnly()) {
            LocalDate threshold = LocalDate.of(1970, 1, 1);
            builder.filter(Author.class, a -> a.getDateOfBirth().isAfter(threshold) ); // <1>
        }

        return builder.get();
    }
    // end::get_with_read_filter[]


    private boolean isAdminRole() {
        return false;
    }

    private boolean isModernAuthorsOnly() {
        return false;
    }
}


