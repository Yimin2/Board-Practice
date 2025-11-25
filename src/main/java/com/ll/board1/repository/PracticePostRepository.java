package com.ll.board1.repository;

import com.ll.board1.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PracticePostRepository {

    @PersistenceContext
    private EntityManager em;

    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        String jpql = "SELECT p FROM Post p";
        return em.createQuery(jpql, Post.class).getResultList();
    }

    public Post update(Post post) {
        return em.merge(post);
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public List<Post> findByTitleContaining(String keyword) {
        String jpql = "SELECT p FROM Post p WHERE p.title LIKE :keyword ";
        return em.createQuery(jpql, Post.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }
}

/*
@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<PostDto> rowMapper = (rs, rowNum) -> {
        return new PostDto(rs.getLong("id"), rs.getString("title"), rs.getString("content"), rs.getTimestamp("created_at")
                .toLocalDateTime());
    };

    public List<PostDto> findAll() {
        String sql = "SELECT * FROM post";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public PostDto findById(Long id) {
        String sql = "SELECT * FROM post WHERE id = ?";

        //queryForObject 단일행 조회
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void save(PostDto postDto) {
        String sql = "INSERT INTO post (title,content) VALUES (?, ?);";
        jdbcTemplate.update(sql, postDto.getTitle(), postDto.getContent());
    }

    public void update(Long id, PostDto postDto) {
        String sql = "UPDATE post SET title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, postDto.getTitle(), postDto.getContent(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM post  WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
*/
