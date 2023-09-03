package markup;

import java.util.List;

public class OrderedList extends StringList {
    public OrderedList(final List<ListItem> list) {
        super(list, "enumerate");
    }
}
