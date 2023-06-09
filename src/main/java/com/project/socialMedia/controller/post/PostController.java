package com.project.socialMedia.controller.post;

import com.project.socialMedia.dto.post.CreatePostDTO;
import com.project.socialMedia.dto.post.ResponsePostDTO;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.PostService;
import com.project.socialMedia.validator.PostValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class PostController extends AbstractPostController {

    @Autowired
    public PostController(PostService postService,
                          AppUserService appUserService,
                          PostValidator postValidator) {
        super(postService, appUserService, postValidator);
    }

    @PostMapping("/post/create")
    public ResponseEntity<?> create(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody CreatePostDTO postDTO,
                                    BindingResult bindingResult) throws IOException {

        return super.create(authUser.id(), postDTO, bindingResult);
    }

    @GetMapping("/{userId}/post")
    public ResponseEntity<List<ResponsePostDTO>> getUserPosts(@PathVariable Long userId) throws IOException {
        return super.getUserPosts(userId);
    }

    @PutMapping("/post/{id}/edit")
    public ResponseEntity<?> edit(@PathVariable Long id,
                                  @Valid @RequestBody CreatePostDTO createPostDTO,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal AuthUser authUser){
        return super.edit(id, authUser.id(), createPostDTO, bindingResult);
    }

    @DeleteMapping("/post/{id}/delete")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id,
                                             @AuthenticationPrincipal AuthUser authUser){
        return super.delete(id, authUser.id());
    }
}

