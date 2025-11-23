package com.kozae.ants.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 수정 요청 DTO
 */
@Getter
@Setter
public class CommentUpdateRequest {

    @NotBlank(message = "내용은 필수입니다")
    private String content;
}
