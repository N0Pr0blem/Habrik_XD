package com.example.article_service.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownUtils {
    private static final Parser parser = Parser.builder().build();
    private static final HtmlRenderer intoHtmlRenderer = HtmlRenderer.builder().build();

    public static String toHtml(String articleContent) {
        Node node = parser.parse(articleContent);
        return intoHtmlRenderer.render(node);
    }
}
