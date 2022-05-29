package io.agrest.docs.framework;

import io.agrest.converter.jsonvalue.JsonValueConverter;
import io.agrest.docs.framework.value.MoneyConverter;
import org.apache.cayenne.di.Binder;
import org.apache.cayenne.di.Module;

// tag::all[]
public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder
                .bindMap(JsonValueConverter.class)
                .put(MoneyConverter.class.getName(), MoneyConverter.class);
    }
}
// tag::all[]
