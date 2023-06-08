package com.project.socialMedia.util;

import com.project.socialMedia.dto.RequestDTO;
import com.project.socialMedia.dto.postDTO.CreatePostDTO;
import com.project.socialMedia.dto.postDTO.ResponsePostDTO;
import com.project.socialMedia.dto.userDTO.CreateAppUserDTO;
import com.project.socialMedia.dto.userDTO.ResponseAppUserDTO;
import com.project.socialMedia.model.post.BinaryContent;
import com.project.socialMedia.model.post.Post;
import com.project.socialMedia.model.request.FriendRequest;
import com.project.socialMedia.model.user.AppUser;
import lombok.experimental.UtilityClass;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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

    public static ResponsePostDTO getPostDTO(Post post) {
        ResponsePostDTO postDTO = modelMapper.map(post, ResponsePostDTO.class);
        ResponseAppUserDTO appUserDTO = Converter.getAppUserDTO(post.getOwner());
        postDTO.setOwner(appUserDTO);

        BinaryContent binaryContent = post.getBinaryContent();

        if (binaryContent != null) {
            postDTO.setImgAsBytes(binaryContent.getImgAsBytes());

            String type = binaryContent.getType();
            postDTO.setType(type);
        }
        return postDTO;
    }

    public static RequestDTO getRequestDTO(FriendRequest request){
        RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
        requestDTO.setInitiator(Converter.getAppUserDTO(request.getInitiator()));
        requestDTO.setTarget(Converter.getAppUserDTO(request.getTarget()));
        return requestDTO;
    }
}

