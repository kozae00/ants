package com.kozae.ants.domain.news.repository;

import com.kozae.ants.domain.news.entity.News;
import com.kozae.ants.domain.stock.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 뉴스 Repository
 */
public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findByStock(Stock stock, Pageable pageable);
    Page<News> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
