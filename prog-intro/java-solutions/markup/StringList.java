package markup;

import java.util.List;

public abstract class StringList implements ListItemValue {
    protected List<ListItem> list;
    protected String round;

    protected StringList(final List<ListItem> list, String round) {
        this.list = list;
        this.round = round;
    }

    public void toMarkdown(StringBuilder string) {
        for (ListItem textElem : list) {
            textElem.toMarkdown(string);
        }
    }

    public void toTex(StringBuilder string) {
        string.append("\\begin{");
        string.append(round);
        string.append("}");
        for (ListItem textElem : list) {
            textElem.toTex(string);
        }
        string.append("\\end{");
        string.append(round);
        string.append("}");
    }
}


