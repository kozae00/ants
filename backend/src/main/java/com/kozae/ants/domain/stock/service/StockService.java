package com.kozae.ants.domain.stock.service;

import com.kozae.ants.domain.stock.dto.StockCreateRequest;
import com.kozae.ants.domain.stock.dto.StockResponse;
import com.kozae.ants.domain.stock.entity.Stock;
import com.kozae.ants.domain.stock.repository.StockRepository;
import com.kozae.ants.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 종목 Service
 */
@Service
@Transactional
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * 종목 생성
     */
    public StockResponse createStock(StockCreateRequest request) {
        if (stockRepository.findByCode(request.getCode()).isPresent()) {
            throw new BusinessException("STOCK_ALREADY_EXISTS", "이미 존재하는 종목입니다");
        }

        Stock stock = Stock.builder()
                .code(request.getCode())
                .name(request.getName())
                .build();

        Stock savedStock = stockRepository.save(stock);
        return new StockResponse(savedStock);
    }

    /**
     * 종목 조회 (코드로)
     */
    @Transactional(readOnly = true)
    public StockResponse getStockByCode(String code) {
        Stock stock = stockRepository.findByCode(code)
                .orElseThrow(() -> new BusinessException("STOCK_NOT_FOUND", "종목을 찾을 수 없습니다"));
        return new StockResponse(stock);
    }

    /**
     * 종목 검색 (이름으로)
     */
    @Transactional(readOnly = true)
    public List<StockResponse> searchStocks(String keyword) {
        List<Stock> stocks = stockRepository.findByNameContainingIgnoreCase(keyword);
        return stocks.stream()
                .map(StockResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 모든 종목 조회
     */
    @Transactional(readOnly = true)
    public List<StockResponse> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .map(StockResponse::new)
                .collect(Collectors.toList());
    }
}
