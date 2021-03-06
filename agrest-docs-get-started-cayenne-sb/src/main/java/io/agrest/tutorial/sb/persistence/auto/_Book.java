package io.agrest.tutorial.sb.persistence.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.property.EntityProperty;
import org.apache.cayenne.exp.property.PropertyFactory;
import org.apache.cayenne.exp.property.StringProperty;

import io.agrest.tutorial.sb.persistence.Author;

/**
 * Class _Book was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Book extends BaseDataObject {

    private static final long serialVersionUID = 1L;

    public static final String ID_PK_COLUMN = "id";

    public static final StringProperty<String> TITLE = PropertyFactory.createString("title", String.class);
    public static final EntityProperty<Author> AUTHOR = PropertyFactory.createEntity("author", Author.class);

    protected String title;

    protected Object author;

    public void setTitle(String title) {
        beforePropertyWrite("title", this.title, title);
        this.title = title;
    }

    public String getTitle() {
        beforePropertyRead("title");
        return this.title;
    }

    public void setAuthor(Author author) {
        setToOneTarget("author", author, true);
    }

    public Author getAuthor() {
        return (Author)readProperty("author");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "title":
                return this.title;
            case "author":
                return this.author;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "title":
                this.title = (String)val;
                break;
            case "author":
                this.author = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.title);
        out.writeObject(this.author);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.title = (String)in.readObject();
        this.author = in.readObject();
    }

}
