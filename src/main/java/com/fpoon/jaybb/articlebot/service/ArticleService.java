package com.fpoon.jaybb.articlebot.service;

import com.chimbori.crux.articles.Article;
import com.chimbori.crux.articles.ArticleExtractor;
import com.fpoon.jaybb.domain.User;
import com.fpoon.jaybb.service.UserService;
import com.google.common.io.CharStreams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final UserService userService;

    @Value("${jaybb.default.article.username:ReaderBot}")
    private String defaultUsername;

    @Value("${jaybb.default.article.email:readerbot@jaybb}")
    private String defaultEmail;

    public Article getArticle(String address) throws Exception {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, "gzip");

        InputStream is;
        if ("gzip".equals(con.getContentEncoding())) {
            is = new GZIPInputStream(con.getInputStream());
        } else {
            is = con.getInputStream();
        }
        Article article = ArticleExtractor.with(address, CharStreams.toString(new InputStreamReader(is)))
                .extractMetadata()
                .extractContent()
                .article();
        return article;
    }

    @PostConstruct
    private User articleBot() {
        return userService.fakeUser(defaultUsername, defaultEmail);
    }

}
