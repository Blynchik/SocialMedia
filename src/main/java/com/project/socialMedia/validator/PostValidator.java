package com.project.socialMedia.validator;

import com.project.socialMedia.dto.postDTO.CreatePostDTO;
import com.project.socialMedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostValidator implements Validator {

    private final PostService postService;

    @Autowired
    public PostValidator(PostService postService) {
        this.postService = postService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CreatePostDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreatePostDTO postDTO = (CreatePostDTO) target;
        String path = postDTO.getPathToImage();
        String type = path.substring(path.lastIndexOf(".") + 1);

        if (!path.isEmpty()) {
            if (!type.equals("jpg") && !type.equals("png") && !type.equals("jpeg")){
                errors.rejectValue("pathToImage", "", "Image type should be jpg, jpeg or png");
            }
        }
    }
}
