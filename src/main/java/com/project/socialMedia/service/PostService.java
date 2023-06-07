package com.project.socialMedia.service;

import com.project.socialMedia.model.post.BinaryContent;
import com.project.socialMedia.model.post.Post;
import com.project.socialMedia.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final BinaryContentService binaryContentService;

    public PostService(PostRepository postRepository,
                       BinaryContentService binaryContentService) {
        this.postRepository = postRepository;
        this.binaryContentService = binaryContentService;
    }

    public Optional<Post> getById(Long id){
        return postRepository.findById(id);
    }

    @Transactional
    public void create(Post post, String pathToImage) throws IOException {
        BinaryContent binaryContent = binaryContentService.getBinaryContent(pathToImage);
        post.setBinaryContent(binaryContent);
        binaryContentService.create(binaryContent);
        postRepository.save(post);
    }

    @Transactional
    public void create(Post post) {
        postRepository.save(post);
    }

    public List<Post> getUserPosts(Long id) {
        List<Post> posts = postRepository.findUserPosts(id);
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        return posts;
    }

    @Transactional
    public void edit(Long id, Post updatedPost) {
        Post post = postRepository.getReferenceById(id);
        updatedPost.setId(post.getId());
        updatedPost.setCreatedAt(post.getCreatedAt());
        updatedPost.setOwner(post.getOwner());
        postRepository.save(updatedPost);
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public boolean checkExistence(Long id) {
       return postRepository.existsById(id);
    }
}

