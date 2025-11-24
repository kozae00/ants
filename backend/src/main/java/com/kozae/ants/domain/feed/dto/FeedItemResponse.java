package com.kozae.ants.domain.feed.dto;

import com.kozae.ants.domain.news.dto.NewsResponse;
import com.kozae.ants.domain.post.dto.PostResponse;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 피드 아이템 응답 DTO (뉴스/게시글 통합)
 */
@Getter
public class FeedItemResponse {
    private String type; // "NEWS" or "POST"
    private Long id;
    private Long stockId;
    private String stockCode;
    private String stockName;
    private Long memberId;
    private String memberNickname;
    private String title;
    private String content;
    private String url;
    private String imageUrl;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // News용 생성자
    public FeedItemResponse(NewsResponse news) {
        this.type = "NEWS";
        this.id = news.getId();
        this.stockId = news.getStockId();
        this.stockCode = news.getStockCode();
        this.stockName = news.getStockName();
        this.memberId = news.getMemberId();
        this.memberNickname = news.getMemberNickname();
        this.title = news.getTitle();
        this.content = news.getDescription();
        this.url = news.getUrl();
        this.imageUrl = news.getImageUrl();
        this.viewCount = null;
        this.createdAt = news.getCreatedAt();
        this.updatedAt = news.getUpdatedAt();
    }

    // Post용 생성자
    public FeedItemResponse(PostResponse post) {
        this.type = "POST";
        this.id = post.getId();
        this.stockId = post.getStockId();
        this.stockCode = post.getStockCode();
        this.stockName = post.getStockName();
        this.memberId = post.getMemberId();
        this.memberNickname = post.getMemberNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.url = null;
        this.imageUrl = null;
        this.viewCount = post.getViewCount();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
