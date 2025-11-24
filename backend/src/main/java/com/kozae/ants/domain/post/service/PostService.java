package com.kozae.ants.domain.post.service;

import com.kozae.ants.domain.member.entity.Member;
import com.kozae.ants.domain.member.repository.MemberRepository;
import com.kozae.ants.domain.post.dto.PostCreateRequest;
import com.kozae.ants.domain.post.dto.PostResponse;
import com.kozae.ants.domain.post.dto.PostUpdateRequest;
import com.kozae.ants.domain.post.entity.Post;
import com.kozae.ants.domain.post.repository.PostRepository;
import com.kozae.ants.domain.stock.entity.Stock;
import com.kozae.ants.domain.stock.repository.StockRepository;
import com.kozae.ants.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 Service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final StockRepository stockRepository;
    private final MemberRepository memberRepository;


    /**
     * 게시글 생성
     */
    public PostResponse createPost(PostCreateRequest request, String email) {
        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new BusinessException("STOCK_NOT_FOUND", "종목을 찾을 수 없습니다"));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("MEMBER_NOT_FOUND", "회원을 찾을 수 없습니다"));

        Post post = Post.builder()
                .stock(stock)
                .member(member)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }

    /**
     * 게시글 조회 (조회수 증가)
     */
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException("POST_NOT_FOUND", "게시글을 찾을 수 없습니다"));
        
        post.increaseViewCount();
        postRepository.save(post);
        
        return new PostResponse(post);
    }

    /**
     * 게시글 수정
     */
    public PostResponse updatePost(Long id, PostUpdateRequest request, String email) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException("POST_NOT_FOUND", "게시글을 찾을 수 없습니다"));

        if (!post.getMember().getEmail().equals(email)) {
            throw new BusinessException("UNAUTHORIZED", "수정 권한이 없습니다");
        }

        post.update(request.getTitle(), request.getContent());
        Post updatedPost = postRepository.save(post);
        return new PostResponse(updatedPost);
    }

    /**
     * 게시글 삭제
     */
    public void deletePost(Long id, String email) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException("POST_NOT_FOUND", "게시글을 찾을 수 없습니다"));

        if (!post.getMember().getEmail().equals(email)) {
            throw new BusinessException("UNAUTHORIZED", "삭제 권한이 없습니다");
        }

        postRepository.delete(post);
    }

    /**
     * 종목별 게시글 조회
     */
    @Transactional(readOnly = true)
    public Page<PostResponse> getPostsByStock(Long stockId, Pageable pageable) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new BusinessException("STOCK_NOT_FOUND", "종목을 찾을 수 없습니다"));

        Page<Post> postPage = postRepository.findByStock(stock, pageable);
        return postPage.map(PostResponse::new);
    }

    /**
     * 모든 게시글 조회 (최신순)
     */
    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return postPage.map(PostResponse::new);
    }
}
