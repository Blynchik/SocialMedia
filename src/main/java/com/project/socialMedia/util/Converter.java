package com.project.socialMedia.util;

import com.project.socialMedia.dto.message.CreateMessageDTO;
import com.project.socialMedia.dto.message.ResponseMessageDTO;
import com.project.socialMedia.dto.request.RequestDTO;
import com.project.socialMedia.dto.post.CreatePostDTO;
import com.project.socialMedia.dto.post.ResponsePostDTO;
import com.project.socialMedia.dto.user.CreateAppUserDTO;
import com.project.socialMedia.dto.user.ResponseAppUserDTO;
import com.project.socialMedia.model.message.Message;
import com.project.socialMedia.model.post.BinaryContent;
import com.project.socialMedia.model.post.Post;
import com.project.socialMedia.model.request.FriendRequest;
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

    public static Message getMessage(CreateMessageDTO messageDTO, AppUser sender, AppUser recipient) {
        Message message = modelMapper.map(messageDTO, Message.class);
        message.setSender(sender);
        message.setRecipient(recipient);
        return message;
    }

    public static ResponseMessageDTO getMessageDTO(Message message) {
        ResponseMessageDTO messageDTO = modelMapper.map(message, ResponseMessageDTO.class);
        ResponseAppUserDTO sender = modelMapper.map(message.getSender(), ResponseAppUserDTO.class);
        ResponseAppUserDTO recipient = modelMapper.map(message.getRecipient(), ResponseAppUserDTO.class);
        messageDTO.setSender(sender);
        messageDTO.setRecipient(recipient);
        return messageDTO;
    }
}


