package com.kozae.ants.domain.post.controller;

import com.kozae.ants.domain.post.dto.PostCreateRequest;
import com.kozae.ants.domain.post.dto.PostResponse;
import com.kozae.ants.domain.post.dto.PostUpdateRequest;
import com.kozae.ants.domain.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 Controller
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest request,
                                                    Authentication authentication) {
        String email = authentication.getName();
        PostResponse response = postService.createPost(request, email);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse response = postService.getPost(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id,
                                                    @Valid @RequestBody PostUpdateRequest request,
                                                    Authentication authentication) {
        String email = authentication.getName();
        PostResponse response = postService.updatePost(id, request, email);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        postService.deletePost(id, email);
        return ResponseEntity.ok().build();
    }

    /**
     * 종목별 게시글 조회
     */
    @GetMapping("/stock/{stockId}")
    public ResponseEntity<Page<PostResponse>> getPostsByStock(@PathVariable Long stockId,
                                                              @PageableDefault(size = 20) Pageable pageable) {
        Page<PostResponse> response = postService.getPostsByStock(stockId, pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * 모든 게시글 조회 (최신순)
     */
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(@PageableDefault(size = 20) Pageable pageable) {
        Page<PostResponse> response = postService.getAllPosts(pageable);
        return ResponseEntity.ok(response);
    }
}
