package app.book.api.tag;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author meow
 */
public class ListTagResponse {
    @Property(name = "total")
    @NotNull
    public Long total;

    @Property(name = "tags")
    public List<Tag> tags;

    public static class Tag {
        @Property(name = "id")
        @NotNull
        public Long id;

        @Property(name = "name")
        @NotNull
        @NotBlank
        public String name;
    }
}
