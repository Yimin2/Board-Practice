package com.ll.board1.repository;


import com.ll.board1.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContaining(String keyword);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
    List<Post> searchByTitle(@Param("keyword") String keyword);

    List<Post> findByTitleStartingWith(String keyword);
    List<Post> findByIdGreaterThan(Long id);
    List<Post> findAllByOrderByIdDesc();
    List<Post> findByTitleContainingOrContentContaining(String title, String content);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword% ORDER BY p.createdAt DESC")
    List<Post> searchByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM post WHERE title LIKE %keyword% ORDER BY id DESC", nativeQuery = true)
    List<Post> searchByTitleNative();

    List<Post> findTop3ByOrderByCreatedAtDesc();

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    Slice<Post> findAllBy(Pageable pageable);

}
