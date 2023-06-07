package com.project.socialMedia.service;

import com.project.socialMedia.model.post.BinaryContent;
import com.project.socialMedia.model.post.Post;
import com.project.socialMedia.repository.BinaryContentRepository;
import com.project.socialMedia.repository.PostRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final ResourceLoader resourceLoader;

    public PostService(PostRepository postRepository,
                       BinaryContentRepository binaryContentRepository,
                       ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.postRepository = postRepository;
        this.binaryContentRepository = binaryContentRepository;
    }

    @Transactional
    public void create(Post post, String pathToImage) throws IOException {
        BinaryContent binaryContent = getBinaryContent(pathToImage);
        post.setImgAsBytes(binaryContent);
        binaryContentRepository.save(binaryContent);
        postRepository.save(post);
    }

    @Transactional
    public void create(Post post){
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
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    private BinaryContent getBinaryContent(String path) throws IOException {

        Resource resource;

        if(path.startsWith("http")) {
            resource = new UrlResource(path);
        } else {
            resource = resourceLoader.getResource("file:" + path);
        }

        byte[] fileBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        BinaryContent binaryContent = new BinaryContent();
        binaryContent.setImgAsBytes(fileBytes);
        binaryContent.setType(path.substring(path.lastIndexOf(".") + 1));

        return binaryContent;
    }
}
