package markup;

public interface TextPiece {
    void toMarkdown(StringBuilder string);
    void toTex(StringBuilder string);
}
