package com.kozae.ants.domain.feed.controller;

import com.kozae.ants.domain.feed.dto.FeedItemResponse;
import com.kozae.ants.domain.feed.service.FeedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 피드 Controller (뉴스 + 게시글 통합)
 */
@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    /**
     * 종목별 피드 조회 (뉴스 + 게시글 통합, 최신순)
     */
    @GetMapping("/stock/{stockId}")
    public ResponseEntity<Page<FeedItemResponse>> getStockFeed(@PathVariable Long stockId,
                                                               @PageableDefault(size = 20) Pageable pageable) {
        Page<FeedItemResponse> response = feedService.getStockFeed(stockId, pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * 전체 피드 조회 (뉴스 + 게시글 통합, 최신순)
     */
    @GetMapping
    public ResponseEntity<Page<FeedItemResponse>> getAllFeed(@PageableDefault(size = 20) Pageable pageable) {
        Page<FeedItemResponse> response = feedService.getAllFeed(pageable);
        return ResponseEntity.ok(response);
    }
}
