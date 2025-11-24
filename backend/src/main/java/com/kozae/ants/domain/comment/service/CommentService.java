package com.kozae.ants.domain.comment.service;

import com.kozae.ants.domain.comment.dto.CommentCreateRequest;
import com.kozae.ants.domain.comment.dto.CommentResponse;
import com.kozae.ants.domain.comment.dto.CommentUpdateRequest;
import com.kozae.ants.domain.comment.entity.Comment;
import com.kozae.ants.domain.comment.repository.CommentRepository;
import com.kozae.ants.domain.member.entity.Member;
import com.kozae.ants.domain.member.repository.MemberRepository;
import com.kozae.ants.domain.post.entity.Post;
import com.kozae.ants.domain.post.repository.PostRepository;
import com.kozae.ants.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 Service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    /**
     * 댓글 생성
     */
    public CommentResponse createComment(CommentCreateRequest request, String email) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new BusinessException("POST_NOT_FOUND", "게시글을 찾을 수 없습니다"));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("MEMBER_NOT_FOUND", "회원을 찾을 수 없습니다"));

        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .content(request.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);
        return new CommentResponse(savedComment);
    }

    /**
     * 댓글 조회
     */
    @Transactional(readOnly = true)
    public CommentResponse getComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("COMMENT_NOT_FOUND", "댓글을 찾을 수 없습니다"));
        return new CommentResponse(comment);
    }

    /**
     * 게시글별 댓글 조회
     */
    @Transactional(readOnly = true)
    public Page<CommentResponse> getCommentsByPost(Long postId, Pageable pageable) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("POST_NOT_FOUND", "게시글을 찾을 수 없습니다"));

        Page<Comment> commentPage = commentRepository.findByPostOrderByCreatedAtAsc(post, pageable);
        return commentPage.map(CommentResponse::new);
    }

    /**
     * 댓글 수정
     */
    public CommentResponse updateComment(Long id, CommentUpdateRequest request, String email) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("COMMENT_NOT_FOUND", "댓글을 찾을 수 없습니다"));

        if (!comment.getMember().getEmail().equals(email)) {
            throw new BusinessException("UNAUTHORIZED", "수정 권한이 없습니다");
        }

        comment.update(request.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return new CommentResponse(updatedComment);
    }

    /**
     * 댓글 삭제
     */
    public void deleteComment(Long id, String email) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("COMMENT_NOT_FOUND", "댓글을 찾을 수 없습니다"));

        if (!comment.getMember().getEmail().equals(email)) {
            throw new BusinessException("UNAUTHORIZED", "삭제 권한이 없습니다");
        }

        commentRepository.delete(comment);
    }
}
