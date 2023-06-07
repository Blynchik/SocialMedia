package com.project.socialMedia.controller.post;

import com.project.socialMedia.dto.postDTO.CreatePostDTO;
import com.project.socialMedia.exception.AppUserNotFoundException;
import com.project.socialMedia.model.post.Post;
import com.project.socialMedia.model.user.AppUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.PostService;
import com.project.socialMedia.util.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public class AbstractPostController {

    protected final PostService postService;
    protected final AppUserService appUserService;

    public AbstractPostController(PostService postService,
                                  AppUserService appUserService) {
        this.postService = postService;
        this.appUserService = appUserService;
    }

    public ResponseEntity<?> create(Long userId,
                                    CreatePostDTO postDTO) throws IOException {

        checkUserExistence(userId);
        AppUser owner = appUserService.getById(userId).get();
        Post post = Converter.getPost(postDTO, owner);
        String path = postDTO.getPathToImage();

        if(path.isBlank()) {
            postService.create(post);
        } else {
            postService.create(post, path);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    protected void checkUserExistence(Long id) {
        if (!appUserService.checkExistence(id)) {
            throw new AppUserNotFoundException(id);
        }
    }
}
