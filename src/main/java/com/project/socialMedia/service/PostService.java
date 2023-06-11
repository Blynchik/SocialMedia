package com.project.socialMedia.service;

import com.project.socialMedia.model.post.BinaryContent;
import com.project.socialMedia.model.post.Post;
import com.project.socialMedia.model.user.AppUser;
import com.project.socialMedia.repository.PostRepository;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final FriendRequestService friendRequestService;
    private final BinaryContentService binaryContentService;

    public PostService(PostRepository postRepository,
                       FriendRequestService friendRequestService,
                       BinaryContentService binaryContentService) {
        this.postRepository = postRepository;
        this.binaryContentService = binaryContentService;
        this.friendRequestService = friendRequestService;
    }

    public Optional<Post> getById(Long id) {
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
        posts.sort(Comparator.comparing(Post::getCreatedAt));
        return posts;
    }

    public Page<Post> getSubscriptionPosts(Long userId, int pageNumber, int pageSize) {
        List<AppUser> subscriptions = friendRequestService.getSubscriptions(userId);
        List<Post> subscriptionPosts = new ArrayList<>();
        for(AppUser u : subscriptions){
            subscriptionPosts.addAll(u.getPosts());
        }
        subscriptionPosts.sort(Comparator.comparing(Post::getCreatedAt));

        int totalPages = (int)Math.ceil((double) subscriptionPosts.size()/pageSize);
        int maxPageNumber = totalPages == 0?1:totalPages;
        int validPageNumber = Math.min(Math.max(pageNumber, 1), maxPageNumber);

        int validPageSize = Math.max(pageSize, 1);

        int fromIndex = (validPageNumber - 1) * pageSize;
        int toIndex = Math.min(fromIndex + validPageSize, subscriptionPosts.size());
        List<Post> pagePosts = subscriptionPosts.subList(fromIndex, toIndex);

        return new PageImpl<>(pagePosts, PageRequest.of(pageNumber - 1, pageSize), subscriptionPosts.size());
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

