package org.rantue.instagram.reporter;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import picocli.CommandLine;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@CommandLine.Command(name = "reporter", description = "Run a report on number of tags for a given list of tags")
public class Main implements Callable<Void> {

    private static final String POST_COUNT = "//*[@id=\"react-root\"]/section/main/article/header/span/span";

    @CommandLine.Parameters(index = "0..*")
    private final List<String> tags = new ArrayList<>();

    public static void main(String[] args) {
        CommandLine.call(new Main(), System.err, args);
    }

    public Void call() throws Exception {
        HtmlCleaner cleaner = new HtmlCleaner();

        List<String> items = tags
                                .stream()
                                .map(tag -> retrievePostCount(cleaner, tag) )
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
        items.forEach(System.out::println);
        return null;
    }

    private String retrievePostCount(HtmlCleaner cleaner, String tag) {
        try {
            URLConnection conn = new URL("https://www.instagram.com/explore/tags/"+tag+"/").openConnection();
            TagNode node = cleaner.clean(conn.getInputStream());
            String postCount = cleaner
                                .getInnerHtml(((TagNode)node.evaluateXPath(POST_COUNT)[0]))
                                .replace(",", "");
            return String.format("%-100s:%-20s", tag, postCount);
        } catch(Exception e) {
            return null;
        }
    }
}
