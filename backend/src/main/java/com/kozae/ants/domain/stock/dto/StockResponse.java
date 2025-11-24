package com.kozae.ants.domain.stock.dto;

import com.kozae.ants.domain.stock.entity.Stock;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 종목 응답 DTO
 */
@Getter
public class StockResponse {
    private Long id;
    private String code;
    private String name;
    private LocalDateTime createdAt;

    public StockResponse(Stock stock) {
        this.id = stock.getId();
        this.code = stock.getCode();
        this.name = stock.getName();
        this.createdAt = stock.getCreatedAt();
    }
}
