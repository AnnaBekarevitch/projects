package markup;

import java.util.List;

public abstract class TextCollection {
    protected List<TextPiece> text;

    protected TextCollection(final List<TextPiece> text) {
        this.text = text;
    }

    public void toMarkdown(StringBuilder string) {
        for (TextPiece textElem : text) {
            textElem.toMarkdown(string);
        }
    }

    public void toTex(StringBuilder string) {
        for (TextPiece textElem : text) {
            textElem.toTex(string);
        }
    }
}
