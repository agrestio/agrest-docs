package io.agrest.docs.framework;

import io.agrest.AgModuleProvider;
import org.apache.cayenne.di.Module;

// tag::all[]
public class MyModuleProvider implements AgModuleProvider {

    @Override
    public Module module() {
        return new MyModule();
    }

    @Override
    public Class<? extends Module> moduleType() {
        return MyModule.class;
    }
}
// tag::all[]
