package com.kozae.ants.domain.comment.repository;

import com.kozae.ants.domain.comment.entity.Comment;
import com.kozae.ants.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 댓글 Repository
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostOrderByCreatedAtAsc(Post post, Pageable pageable);
    List<Comment> findByPostOrderByCreatedAtAsc(Post post);
}
