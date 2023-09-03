package markup;

import java.util.List;

public class UnorderedList extends StringList {
    public UnorderedList(final List<ListItem> list) {
        super(list, "itemize");
    }
}

