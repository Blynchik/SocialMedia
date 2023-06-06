package com.project.socialMedia.repository;

import com.project.socialMedia.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value =  "SELECT r FROM Post r WHERE r.owner.id=?1")
    List<Post> findUserPosts(Long id);
}
