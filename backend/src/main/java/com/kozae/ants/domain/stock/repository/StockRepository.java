package com.kozae.ants.domain.stock.repository;

import com.kozae.ants.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 종목 Repository
 */
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByCode(String code);
    List<Stock> findByNameContainingIgnoreCase(String name);
}
