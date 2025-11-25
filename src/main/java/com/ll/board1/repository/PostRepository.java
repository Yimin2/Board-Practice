package com.ll.board1.repository;


import com.ll.board1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContaining(String keyword);
    List<Post> findByTitleStartingWith(String keyword);
    List<Post> findByIdGreaterThan(Long id);
    List<Post> findAllByOrderByIdDesc();
}
