package markup;

import java.util.List;

public class BeautifulText extends TextCollection implements TextPiece {
    protected String limit;
    protected String texLimit;

    protected BeautifulText(final List<TextPiece> text, String limit, String texLimit) {
        super(text);
        this.limit = limit;
        this.texLimit = texLimit;
    }

    protected String getlimites() {
        return limit;
    }

    protected String getTexLimites() {
        return texLimit;
    }

    @Override
    public void toMarkdown(StringBuilder string) {
        string.append(limit);
        super.toMarkdown(string);
        string.append(limit);
    }

    @Override
    public void toTex(StringBuilder string) {
        string.append(texLimit);
        string.append("{");
        super.toTex(string);
        string.append("}");
    }
}