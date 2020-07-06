package app.api.backoffice.bookcategory;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;

/**
 * @author zoo
 */
public class UpdateBookCategoryAJAXRequest {
    @Length(max = 50)
    @Property(name = "name")
    public String name;
}
