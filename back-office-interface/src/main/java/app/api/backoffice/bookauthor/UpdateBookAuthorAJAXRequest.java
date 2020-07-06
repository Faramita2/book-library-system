package app.api.backoffice.bookauthor;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;

/**
 * @author zoo
 */
public class UpdateBookAuthorAJAXRequest {
    // todo
    @Length(max = 50)
    @Property(name = "name")
    public String name;
}
