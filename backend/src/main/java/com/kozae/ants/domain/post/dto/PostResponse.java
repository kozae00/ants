package com.kozae.ants.domain.post.dto;

import com.kozae.ants.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 게시글 응답 DTO
 */
@Getter
public class PostResponse {
    private Long id;
    private Long stockId;
    private String stockCode;
    private String stockName;
    private Long memberId;
    private String memberNickname;
    private String title;
    private String content;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.stockId = post.getStock().getId();
        this.stockCode = post.getStock().getCode();
        this.stockName = post.getStock().getName();
        this.memberId = post.getMember().getId();
        this.memberNickname = post.getMember().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.viewCount = post.getViewCount();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
