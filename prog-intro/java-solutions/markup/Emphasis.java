package markup;
import java.util.*;

class Emphasis extends BeautifulText {
    public Emphasis(final List<TextPiece> text) {
        super(text, "*", "\\emph");
    }
}