package markup;

import java.util.List;

class Strikeout extends BeautifulText {
    public Strikeout(final List<TextPiece> text) {
        super(text, "~", "\\textst");
    }
}