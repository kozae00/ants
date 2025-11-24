package com.kozae.ants.domain.post.repository;

import com.kozae.ants.domain.post.entity.Post;
import com.kozae.ants.domain.stock.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 게시글 Repository
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByStock(Stock stock, Pageable pageable);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
