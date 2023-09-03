package markup;

import java.util.List;

public class Paragraph extends TextCollection implements ListItemValue {
    public Paragraph(final List<TextPiece> paragraph) {
        super(paragraph);
    }
}
