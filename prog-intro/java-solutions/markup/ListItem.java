package markup;

import java.util.List;

public class ListItem {
    String teg = "\\item ";
    List<ListItemValue> text;
    public ListItem(final List<ListItemValue> text) {
        this.text = text;
    }
    public void toTex(StringBuilder string) {
        string.append(teg);
        for (ListItemValue textElem : text) {
            textElem.toTex(string);
        }
    }
    public void toMarkdown(StringBuilder string) {
        for (ListItemValue textElem : text) {
            textElem.toMarkdown(string);
        }
    }
}
