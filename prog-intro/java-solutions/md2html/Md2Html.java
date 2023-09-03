package md2html;
import java.util.*;
import java.io.*;

public class Md2Html {

    private static final int BUFFER_SIZE = 4096;

    private static int rangOfTopic(String line) {
        StringBuilder topicStart = new StringBuilder("###### ");
        for (int rang = 6; rang >= 1; rang--) {
            if (line.startsWith(topicStart.toString())) {
                return rang;
            }
            topicStart.deleteCharAt(topicStart.length() - 2);
        }
        return 0;
    }
    
    private static String makeTag(String tag) {
        return "<" + tag + ">";
    }

    private static String makeLink(String link, String comment) {
        return makeTag("a href='" + link + "'") + comment + makeTag("/a");
    }

    private static String killSpecialSign(String line) {
        line = line.replaceAll("&", "&amp;");
        line = line.replaceAll("<", "&lt;");
        line = line.replaceAll(">", "&gt;");
        return line;
    }

    private static int skipLink(int position, StringBuilder line) {
        if (position + 1 < line.length() && line.charAt(position) == '<' && line.charAt(position + 1) == 'a') {
            while (position < line.length() && line.charAt(position) != '>') {
                position++;
            }
        }
        return position;
    }

    private static String toHtml(String tag) {
        if (tag.equals("**") || tag.equals("__")) {
            return "strong";
        }
        if (tag.equals("*") || tag.equals("_")) {
            return "em";
        }
        if (tag.equals("`")) {
            return "code";
        }
        if (tag.equals("--")) {
            return "s";
        }
        return null;
    }

    private static String highlighting(int position, StringBuilder line) {
        if (line.charAt(position) == '*' && position + 1 < line.length() && line.charAt(position + 1) == '*') {
            return "**";
        }
        if (line.charAt(position) == '_' && position + 1 < line.length() && line.charAt(position + 1) == '_') {
            return "__";
        }
        if (line.charAt(position) == '*') {
            return "*";
        }
        if (line.charAt(position) == '_') {
            return "_";
        }
        if (line.charAt(position) == '`') {
            return "`";
        }
        if (line.charAt(position) == '-' && position + 1 < line.length() && line.charAt(position + 1) == '-') {
            return "--";
        }
        return null;
    }

    private static int lenTag(String tag) {
        return (tag != null ? tag.length() : 0);
    }

    public static void main(final String[] args) {
        String inputName = args[0];
        String outputName = args[1];
        List<StringBuilder> output = new ArrayList<>(1);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputName), "UTF-8"),
                BUFFER_SIZE)
        ) {
            boolean isNewLineStarted = true;
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.isEmpty()) {
                    isNewLineStarted = true;
                } else {
                    if (isNewLineStarted) {
                        output.add(new StringBuilder());
                    }
                    StringBuilder now = output.get(output.size() - 1);
                    now.append(killSpecialSign(line));
                    now.append(System.lineSeparator());
                    isNewLineStarted = false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("    Error: Input file not found: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("    Error: Unsupported encoding: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("    Error: IOException: " + e.getMessage());
        }

        for (StringBuilder nowLine : output) {
            int rang = rangOfTopic(nowLine.toString());
            String start;
            String end;
            if (rang > 0) {
                start = makeTag("h" + String.valueOf(rang));
                end = makeTag("/h" + String.valueOf(rang));
                nowLine = nowLine.delete(0, rang + 1);
            } else {
                start = makeTag("p");
                end = makeTag("/p");
            }
            nowLine.insert(0, start);
            nowLine.replace(nowLine.length() - System.lineSeparator().length(), nowLine.length(), end);
        }

        for (StringBuilder nowLine : output) {
            for (int i = 0; i < nowLine.length(); i++) {
                char now = nowLine.charAt(i);
                if (now == '[') {
                    int commentStartPos = i;
                    int commentEndPos = i + 1;
                    while (commentEndPos < nowLine.length() && nowLine.charAt(commentEndPos) != ']') {
                        commentEndPos++;
                    }
                    if (commentEndPos + 1 < nowLine.length()) {
                        now = nowLine.charAt(commentEndPos + 1);
                        if (now == '(') {
                            int linkStartPos = commentEndPos + 1;
                            int linkEndPos = linkStartPos + 1;
                            while (linkEndPos < nowLine.length() && nowLine.charAt(linkEndPos) != ')') {
                                linkEndPos++;
                            }
                            if (linkEndPos < nowLine.length()) {
                                String link = nowLine.substring(linkStartPos + 1, linkEndPos);
                                String comment = nowLine.substring(commentStartPos + 1, commentEndPos);
                                nowLine.replace(commentStartPos, linkEndPos + 1, makeLink(link, comment));
                            }
                            i = linkEndPos;
                        }
                    }
                }
            }
        }

        List<Integer> tagsPositions = new ArrayList<>();
        List<String> tagsNames = new ArrayList<>();
        for (StringBuilder nowLine : output) {
            for (int i = 0; i < nowLine.length(); i++) {
                char now = nowLine.charAt(i);
                if (now == '\\') {
                    nowLine.deleteCharAt(i);
                    continue;
                }
                i = skipLink(i, nowLine);
                String tag = highlighting(i, nowLine);
                if (tag != null) {
                    if (!tagsNames.isEmpty() && tagsNames.get(tagsNames.size() - 1).equals(tag)) {
                        String htmlTag = toHtml(tag);
                        int start = tagsPositions.get(tagsPositions.size() - 1);
                        nowLine.replace(i, i + lenTag(tag), makeTag("/" + htmlTag));
                        nowLine.replace(start, start + lenTag(tag), makeTag(htmlTag));
                        tagsNames.remove(tagsNames.size() - 1);
                        tagsPositions.remove(tagsPositions.size() - 1);
                    } else {
                        tagsPositions.add(i);
                        tagsNames.add(tag);
                    }
                    i += lenTag(tag) - 1;
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8"), 
                BUFFER_SIZE)
        ) {
            for (int i = 0; i < output.size(); i++) {
                writer.write(output.get(i).toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("    Error: IOException: " + e.getMessage());
        }
    }
}