package com.kozae.ants.domain.news.dto;

import com.kozae.ants.domain.news.entity.News;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 뉴스 응답 DTO
 */
@Getter
public class NewsResponse {
    private Long id;
    private Long stockId;
    private String stockCode;
    private String stockName;
    private Long memberId;
    private String memberNickname;
    private String url;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NewsResponse(News news) {
        this.id = news.getId();
        this.stockId = news.getStock().getId();
        this.stockCode = news.getStock().getCode();
        this.stockName = news.getStock().getName();
        this.memberId = news.getMember().getId();
        this.memberNickname = news.getMember().getNickname();
        this.url = news.getUrl();
        this.title = news.getTitle();
        this.description = news.getDescription();
        this.imageUrl = news.getImageUrl();
        this.createdAt = news.getCreatedAt();
        this.updatedAt = news.getUpdatedAt();
    }
}
