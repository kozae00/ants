package com.kozae.ants.domain.news.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 뉴스 생성 요청 DTO
 */
@Getter
@Setter
public class NewsCreateRequest {

    @NotNull(message = "종목 ID는 필수입니다")
    private Long stockId;

    @NotBlank(message = "URL은 필수입니다")
    private String url;
}
