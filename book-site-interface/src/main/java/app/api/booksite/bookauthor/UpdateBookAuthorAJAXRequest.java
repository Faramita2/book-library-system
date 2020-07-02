package app.api.booksite.bookauthor;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;

/**
 * @author zoo
 */
public class UpdateBookAuthorAJAXRequest {
    @Length(max = 50)
    @Property(name = "name")
    public String name;
}
