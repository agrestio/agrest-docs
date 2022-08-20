package io.agrest.docs.framework;

import io.agrest.compiler.AgEntityCompiler;
import io.agrest.compiler.AnnotationsAgEntityCompiler;
import io.agrest.docs.framework.model.Author;
import io.agrest.docs.framework.model.Book;
import io.agrest.jaxrs3.AgJaxrs;
import io.agrest.meta.AgAttribute;
import io.agrest.meta.AgEntity;
import io.agrest.meta.AgEntityOverlay;
import io.agrest.meta.AgSchema;
import io.agrest.runtime.AgRuntime;
import jakarta.ws.rs.core.Configuration;

import java.time.LocalDate;
import java.time.Period;

public class Schema {

    public void schemaAccess() {
        AgRuntime runtime = AgRuntime.build();

        // tag::schema[]
        AgSchema schema = runtime.service(AgSchema.class); // <1>
        AgEntity<Book> bookEntity = schema.getEntity(Book.class); // <2>
        AgAttribute titleAttribute = bookEntity.getAttribute("title"); // <3>
        // end::schema[]
    }

    public void customSchemaCompiler() {
        // tag::schema-compiler-module[]
        AgRuntime runtime = AgRuntime
                .builder()
                .module(b -> b
                        .bindList(AgEntityCompiler.class)
                        .insertBefore(MyEntityCompiler.class, AnnotationsAgEntityCompiler.class))
                .build();
        // end::schema-compiler-module[]
    }

    public void runtimeOverlay() {
        AgEntityOverlay<Author> overlay = createAuthorOverlay();

        // tag::runtime-overlay[]
        AgRuntime runtime = AgRuntime
                .builder()
                .entityOverlay(overlay)
                .build();
        // end::runtime-overlay[]
    }

    public void requestOverlay() {
        AgEntityOverlay<Author> overlay = createAuthorOverlay();
        Configuration config = null;

        // tag::request-overlay[]
        AgJaxrs.select(Book.class, config).entityOverlay(overlay).get();
        // end::request-overlay[]
    }

    AgEntityOverlay<Author> createAuthorOverlay() {
        // tag::overlay[]
        AgEntityOverlay<Author> overlay = AgEntity
                .overlay(Author.class)
                .attribute( // <1>
                        "age",
                        Integer.TYPE,
                        a -> Period.between(a.getDateOfBirth(), LocalDate.now()).getYears())
                .writablePropFilter(p -> p.property("dateOfBirth", false)); // <2>
        // end::overlay[]

        return overlay;
    }


    // tag::schema-compiler[]
    class MyEntityCompiler implements AgEntityCompiler {

        @Override
        public <T> AgEntity<T> compile(Class<T> aClass, AgSchema agSchema) {

            return isHandledByMyBackend(aClass)
                    ? doCompile(aClass, agSchema) // <1>
                    : null; // <2>
        }
        // end::schema-compiler[]

        private boolean isHandledByMyBackend(Class<?> aClass) {
            return false;
        }

        private <T> AgEntity<T> doCompile(Class<T> aClass, AgSchema agSchema) {
            throw new UnsupportedOperationException("do something here");
        }
        // tag::schema-compiler[]
    }
    // end::schema-compiler[]
}
