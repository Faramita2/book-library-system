package app.api.backoffice.booktag;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;

/**
 * @author zoo
 */
public class UpdateBookTagAJAXRequest {
    @Length(max = 50)
    @Property(name = "name")
    public String name;
}
