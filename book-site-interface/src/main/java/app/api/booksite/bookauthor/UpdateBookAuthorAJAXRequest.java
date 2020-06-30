package app.api.booksite.bookauthor;

import core.framework.api.json.Property;

/**
 * @author zoo
 */
public class UpdateBookAuthorAJAXRequest {
    @Property(name = "name")
    public String name;
}
