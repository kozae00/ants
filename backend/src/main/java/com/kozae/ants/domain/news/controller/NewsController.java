package com.kozae.ants.domain.news.controller;

import com.kozae.ants.domain.news.dto.NewsCreateRequest;
import com.kozae.ants.domain.news.dto.NewsResponse;
import com.kozae.ants.domain.news.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 뉴스 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    /**
     * 뉴스 생성
     */
    @PostMapping
    public ResponseEntity<NewsResponse> createNews(@Valid @RequestBody NewsCreateRequest request,
                                                    Authentication authentication) {
        String email = authentication.getName();
        NewsResponse response = newsService.createNews(request, email);
        return ResponseEntity.ok(response);
    }

    /**
     * 뉴스 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> getNews(@PathVariable Long id) {
        NewsResponse response = newsService.getNews(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 종목별 뉴스 조회
     */
    @GetMapping("/stock/{stockId}")
    public ResponseEntity<Page<NewsResponse>> getNewsByStock(@PathVariable Long stockId,
                                                              @PageableDefault(size = 20) Pageable pageable) {
        Page<NewsResponse> response = newsService.getNewsByStock(stockId, pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * 모든 뉴스 조회 (최신순)
     */
    @GetMapping
    public ResponseEntity<Page<NewsResponse>> getAllNews(@PageableDefault(size = 20) Pageable pageable) {
        Page<NewsResponse> response = newsService.getAllNews(pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * 뉴스 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        newsService.deleteNews(id, email);
        return ResponseEntity.ok().build();
    }
}
