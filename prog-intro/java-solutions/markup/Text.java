package markup;

public class Text implements TextPiece {
    private final String text;
    public Text(String text) {
        this.text = text;
    }
    public void toMarkdown(StringBuilder string) {
        string.append(text);
    }
    public void toTex(StringBuilder string) {
        string.append(text);
    }
}