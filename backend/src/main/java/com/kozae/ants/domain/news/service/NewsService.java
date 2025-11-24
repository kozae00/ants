package com.kozae.ants.domain.news.service;

import com.kozae.ants.domain.member.entity.Member;
import com.kozae.ants.domain.member.repository.MemberRepository;
import com.kozae.ants.domain.news.dto.NewsCreateRequest;
import com.kozae.ants.domain.news.dto.NewsResponse;
import com.kozae.ants.domain.news.entity.News;
import com.kozae.ants.domain.news.repository.NewsRepository;
import com.kozae.ants.domain.stock.entity.Stock;
import com.kozae.ants.domain.stock.repository.StockRepository;
import com.kozae.ants.global.exception.BusinessException;
import com.kozae.ants.global.util.MetadataParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 뉴스 Service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;
    private final StockRepository stockRepository;
    private final MemberRepository memberRepository;
    private final MetadataParser metadataParser;


    /**
     * 뉴스 생성 (URL 메타데이터 파싱 포함)
     */
    public NewsResponse createNews(NewsCreateRequest request, String email) {
        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new BusinessException("STOCK_NOT_FOUND", "종목을 찾을 수 없습니다"));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("MEMBER_NOT_FOUND", "회원을 찾을 수 없습니다"));

        // URL 메타데이터 파싱
        Map<String, String> metadata = metadataParser.parseMetadata(request.getUrl());
        String title = metadata.get("title");
        String description = metadata.get("description");
        String imageUrl = metadata.get("image");

        News news = News.builder()
                .stock(stock)
                .member(member)
                .url(request.getUrl())
                .title(title != null && !title.isEmpty() ? title : "제목 없음")
                .description(description)
                .imageUrl(imageUrl)
                .build();

        News savedNews = newsRepository.save(news);
        return new NewsResponse(savedNews);
    }

    /**
     * 뉴스 조회
     */
    @Transactional(readOnly = true)
    public NewsResponse getNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new BusinessException("NEWS_NOT_FOUND", "뉴스를 찾을 수 없습니다"));
        return new NewsResponse(news);
    }

    /**
     * 종목별 뉴스 조회
     */
    @Transactional(readOnly = true)
    public Page<NewsResponse> getNewsByStock(Long stockId, Pageable pageable) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new BusinessException("STOCK_NOT_FOUND", "종목을 찾을 수 없습니다"));

        Page<News> newsPage = newsRepository.findByStock(stock, pageable);
        return newsPage.map(NewsResponse::new);
    }

    /**
     * 모든 뉴스 조회 (최신순)
     */
    @Transactional(readOnly = true)
    public Page<NewsResponse> getAllNews(Pageable pageable) {
        Page<News> newsPage = newsRepository.findAllByOrderByCreatedAtDesc(pageable);
        return newsPage.map(NewsResponse::new);
    }

    /**
     * 뉴스 삭제
     */
    public void deleteNews(Long id, String email) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new BusinessException("NEWS_NOT_FOUND", "뉴스를 찾을 수 없습니다"));

        if (!news.getMember().getEmail().equals(email)) {
            throw new BusinessException("UNAUTHORIZED", "삭제 권한이 없습니다");
        }

        newsRepository.delete(news);
    }
}
