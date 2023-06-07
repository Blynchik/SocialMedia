package com.project.socialMedia.util;

import com.project.socialMedia.dto.postDTO.CreatePostDTO;
import com.project.socialMedia.dto.userDTO.CreateAppUserDTO;
import com.project.socialMedia.dto.userDTO.ResponseAppUserDTO;
import com.project.socialMedia.model.post.Post;
import com.project.socialMedia.model.user.AppUser;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class Converter {

    private final static ModelMapper modelMapper = new ModelMapper();

    public static AppUser getAppUser(CreateAppUserDTO appUserDTO) {
        return modelMapper.map(appUserDTO, AppUser.class);
    }

    public static ResponseAppUserDTO getAppUserDTO(AppUser appUser) {
        return modelMapper.map(appUser, ResponseAppUserDTO.class);
    }

    public static Post getPost(CreatePostDTO postDTO, AppUser owner) {
        Post post = modelMapper.map(postDTO, Post.class);
        post.setOwner(owner);
        return post;
    }
}
