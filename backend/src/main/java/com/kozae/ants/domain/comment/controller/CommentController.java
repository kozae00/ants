package com.kozae.ants.domain.comment.controller;

import com.kozae.ants.domain.comment.dto.CommentCreateRequest;
import com.kozae.ants.domain.comment.dto.CommentResponse;
import com.kozae.ants.domain.comment.dto.CommentUpdateRequest;
import com.kozae.ants.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 Controller
 */
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 생성
     */
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CommentCreateRequest request,
                                                         Authentication authentication) {
        String email = authentication.getName();
        CommentResponse response = commentService.createComment(request, email);
        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long id) {
        CommentResponse response = commentService.getComment(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글별 댓글 조회
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<CommentResponse>> getCommentsByPost(@PathVariable Long postId,
                                                                   @PageableDefault(size = 20) Pageable pageable) {
        Page<CommentResponse> response = commentService.getCommentsByPost(postId, pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id,
                                                         @Valid @RequestBody CommentUpdateRequest request,
                                                         Authentication authentication) {
        String email = authentication.getName();
        CommentResponse response = commentService.updateComment(id, request, email);
        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        commentService.deleteComment(id, email);
        return ResponseEntity.ok().build();
    }
}
