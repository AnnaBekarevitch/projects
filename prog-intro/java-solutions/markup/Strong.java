package markup;
import java.util.*;

class Strong extends BeautifulText {
    public Strong(final List<TextPiece> text) {
        super(text, "__", "\\textbf");
    }
}