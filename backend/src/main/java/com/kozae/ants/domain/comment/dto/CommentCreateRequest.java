package com.kozae.ants.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 생성 요청 DTO
 */
@Getter
@Setter
public class CommentCreateRequest {

    @NotNull(message = "게시글 ID는 필수입니다")
    private Long postId;

    @NotBlank(message = "내용은 필수입니다")
    private String content;
}
