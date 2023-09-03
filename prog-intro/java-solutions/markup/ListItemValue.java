package markup;

public interface ListItemValue {

    void toTex(StringBuilder string);

    void toMarkdown(StringBuilder string);
}
