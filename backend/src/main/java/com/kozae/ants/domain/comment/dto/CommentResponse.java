package com.kozae.ants.domain.comment.dto;

import com.kozae.ants.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 댓글 응답 DTO
 */
@Getter
public class CommentResponse {
    private Long id;
    private Long postId;
    private Long memberId;
    private String memberNickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.memberId = comment.getMember().getId();
        this.memberNickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
