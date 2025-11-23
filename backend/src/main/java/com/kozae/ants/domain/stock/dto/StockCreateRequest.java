package com.kozae.ants.domain.stock.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 종목 생성 요청 DTO
 */
@Getter
@Setter
public class StockCreateRequest {

    @NotBlank(message = "종목 코드는 필수입니다")
    private String code;

    @NotBlank(message = "종목명은 필수입니다")
    private String name;
}
