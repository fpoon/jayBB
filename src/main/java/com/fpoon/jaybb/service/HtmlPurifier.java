package com.fpoon.jaybb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HtmlPurifier {

    public static Whitelist whitelist;
    static {
        whitelist = Whitelist.relaxed();
        whitelist.preserveRelativeLinks(true);
        whitelist.addAttributes(":all", "style");
        whitelist.addAttributes("img", "data-filename");
        whitelist.addProtocols("img", "src", "http", "https", "data", "cid");
    }

    public String purify(String dirtyHtml) {
        return Jsoup.clean(dirtyHtml, whitelist);
    }
}
