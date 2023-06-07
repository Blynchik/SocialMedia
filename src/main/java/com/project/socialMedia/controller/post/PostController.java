package com.project.socialMedia.controller.post;

import com.project.socialMedia.dto.postDTO.CreatePostDTO;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/user/post")
public class PostController extends AbstractPostController{

    @Autowired
    public PostController(PostService postService,
                          AppUserService appUserService){
        super(postService, appUserService);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody CreatePostDTO postDTO) throws IOException {

        return super.create(authUser.id(), postDTO);
    }

}
