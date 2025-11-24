package com.kozae.ants.domain.feed.service;

import com.kozae.ants.domain.feed.dto.FeedItemResponse;
import com.kozae.ants.domain.news.dto.NewsResponse;
import com.kozae.ants.domain.news.service.NewsService;
import com.kozae.ants.domain.post.dto.PostResponse;
import com.kozae.ants.domain.post.service.PostService;
import com.kozae.ants.domain.stock.repository.StockRepository;
import com.kozae.ants.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 피드 Service (뉴스 + 게시글 통합)
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {

    private final NewsService newsService;
    private final PostService postService;
    private final StockRepository stockRepository;


    /**
     * 종목별 피드 조회 (뉴스 + 게시글 통합, 최신순)
     */
    public Page<FeedItemResponse> getStockFeed(Long stockId, Pageable pageable) {
        // 종목 존재 여부 확인
        if (!stockRepository.existsById(stockId)) {
            throw new BusinessException("STOCK_NOT_FOUND", "종목을 찾을 수 없습니다");
        }

        // 뉴스 조회 (더 큰 페이지 사이즈로 조회하여 통합 정렬)
        Page<NewsResponse> newsPage = newsService.getNewsByStock(stockId, pageable);
        List<FeedItemResponse> newsFeedItems = newsPage.getContent().stream()
                .map(FeedItemResponse::new)
                .collect(Collectors.toList());

        // 게시글 조회
        Page<PostResponse> postPage = postService.getPostsByStock(stockId, pageable);
        List<FeedItemResponse> postFeedItems = postPage.getContent().stream()
                .map(FeedItemResponse::new)
                .collect(Collectors.toList());

        // 통합 및 정렬 (최신순)
        List<FeedItemResponse> allFeedItems = new ArrayList<>();
        allFeedItems.addAll(newsFeedItems);
        allFeedItems.addAll(postFeedItems);

        allFeedItems.sort(Comparator.comparing(FeedItemResponse::getCreatedAt).reversed());

        // 페이지네이션 적용
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allFeedItems.size());
        List<FeedItemResponse> pagedItems = allFeedItems.subList(start, end);

        return new PageImpl<>(pagedItems, pageable, allFeedItems.size());
    }

    /**
     * 전체 피드 조회 (뉴스 + 게시글 통합, 최신순)
     */
    public Page<FeedItemResponse> getAllFeed(Pageable pageable) {
        // 뉴스 조회
        Page<NewsResponse> newsPage = newsService.getAllNews(pageable);
        List<FeedItemResponse> newsFeedItems = newsPage.getContent().stream()
                .map(FeedItemResponse::new)
                .collect(Collectors.toList());

        // 게시글 조회
        Page<PostResponse> postPage = postService.getAllPosts(pageable);
        List<FeedItemResponse> postFeedItems = postPage.getContent().stream()
                .map(FeedItemResponse::new)
                .collect(Collectors.toList());

        // 통합 및 정렬 (최신순)
        List<FeedItemResponse> allFeedItems = new ArrayList<>();
        allFeedItems.addAll(newsFeedItems);
        allFeedItems.addAll(postFeedItems);

        allFeedItems.sort(Comparator.comparing(FeedItemResponse::getCreatedAt).reversed());

        // 페이지네이션 적용
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allFeedItems.size());
        List<FeedItemResponse> pagedItems = allFeedItems.subList(start, end);

        return new PageImpl<>(pagedItems, pageable, allFeedItems.size());
    }
}
