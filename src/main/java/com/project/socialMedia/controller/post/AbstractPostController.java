package com.project.socialMedia.controller.post;

import com.project.socialMedia.dto.postDTO.CreatePostDTO;
import com.project.socialMedia.dto.postDTO.ResponsePostDTO;
import com.project.socialMedia.exception.AppUserNotFoundException;
import com.project.socialMedia.exception.ForbiddenActionException;
import com.project.socialMedia.exception.PostNotFoundException;
import com.project.socialMedia.model.post.Post;
import com.project.socialMedia.model.user.AppUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.PostService;
import com.project.socialMedia.util.Converter;
import com.project.socialMedia.validator.PostValidator;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.List;

public class AbstractPostController {

    protected final PostService postService;
    protected final AppUserService appUserService;
    protected final PostValidator postValidator;

    public AbstractPostController(PostService postService,
                                  AppUserService appUserService,
                                  PostValidator postValidator) {
        this.postService = postService;
        this.appUserService = appUserService;
        this.postValidator = postValidator;
    }

    public ResponseEntity<?> create(Long userId,
                                    CreatePostDTO postDTO,
                                    BindingResult bindingResult) throws IOException {

        checkUserExistence(userId);

        postValidator.validate(postDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            );
        }

        AppUser owner = appUserService.getById(userId).get();
        Post post = Converter.getPost(postDTO, owner);
        String path = postDTO.getPathToImage();

        if (path.isBlank()) {
            postService.create(post);
        } else {
            postService.create(post, path);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<List<ResponsePostDTO>> getUserPosts(Long userId) throws IOException {
        checkUserExistence(userId);

        List<ResponsePostDTO> postsDTO = postService.getUserPosts(userId).stream()
                .map(Converter::getPostDTO).toList();

        return ResponseEntity.ok().body(postsDTO);
    }

    public ResponseEntity<?> edit(Long postId,
                                  Long userId,
                                  CreatePostDTO updatedPostDTO,
                                  BindingResult bindingResult) {

        checkUserExistence(userId);
        checkPostExistence(postId);

        Post postToEdit = postService.getById(postId).get();

        if(!postToEdit.getOwner().getId().equals(userId)){
            throw new ForbiddenActionException();
        }

        postValidator.validate(updatedPostDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            );
        }

        AppUser owner = appUserService.getById(userId).get();

        Post post = Converter.getPost(updatedPostDTO, owner);

        postService.edit(postId,post);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    protected void checkUserExistence(Long id) {
        if (!appUserService.checkExistence(id)) {
            throw new AppUserNotFoundException(id);
        }
    }

    protected void checkPostExistence(Long id) {
        if (!postService.checkExistence(id)) {
            throw new PostNotFoundException(id);
        }
    }
}
