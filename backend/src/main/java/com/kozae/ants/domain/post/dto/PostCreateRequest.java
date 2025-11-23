package com.kozae.ants.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 생성 요청 DTO
 */
@Getter
@Setter
public class PostCreateRequest {

    @NotNull(message = "종목 ID는 필수입니다")
    private Long stockId;

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;
}
