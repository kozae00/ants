package com.kozae.ants.global.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * URL 메타데이터 파서
 */
@Component
public class MetadataParser {

    /**
     * URL에서 메타데이터 추출
     */
    public Map<String, String> parseMetadata(String url) {
        Map<String, String> metadata = new HashMap<>();
        
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            // 제목 추출 (og:title 우선, 없으면 title 태그)
            String title = doc.select("meta[property=og:title]").attr("content");
            if (title.isEmpty()) {
                title = doc.select("title").text();
            }
            metadata.put("title", title);

            // 설명 추출 (og:description 우선, 없으면 description 메타 태그)
            String description = doc.select("meta[property=og:description]").attr("content");
            if (description.isEmpty()) {
                description = doc.select("meta[name=description]").attr("content");
            }
            metadata.put("description", description);

            // 이미지 추출 (og:image)
            String image = doc.select("meta[property=og:image]").attr("content");
            metadata.put("image", image);

        } catch (IOException e) {
            metadata.put("title", "");
            metadata.put("description", "");
            metadata.put("image", "");
        }

        return metadata;
    }
}
