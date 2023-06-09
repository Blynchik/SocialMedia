package com.project.socialMedia.controller.message;

import com.project.socialMedia.dto.message.CreateMessageDTO;
import com.project.socialMedia.dto.message.ResponseMessageDTO;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.ChatPermissionService;
import com.project.socialMedia.service.FriendRequestService;
import com.project.socialMedia.service.MessageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/message")
public class MessageController extends AbstractMessageController{


    @Autowired
    public MessageController(MessageService messageService,
                             AppUserService appUserService,
                             FriendRequestService friendRequestService,
                             ChatPermissionService chatPermissionService) {
        super(messageService, appUserService, friendRequestService, chatPermissionService);
    }

    @PostMapping("/send")
    public ResponseEntity<HttpStatus> send(@AuthenticationPrincipal AuthUser authUser,
                                           @RequestBody CreateMessageDTO messageDTO,
                                           @RequestParam Long recipientId){
        return super.send(messageDTO, authUser.id(), recipientId);
    }

    @GetMapping("/incoming")
    public ResponseEntity<List<ResponseMessageDTO>> getIncoming(@AuthenticationPrincipal AuthUser authUser){
        return super.getIncoming(authUser.id());
    }

    @GetMapping("/outgoing")
    public ResponseEntity<List<ResponseMessageDTO>> getOutgoing(@AuthenticationPrincipal AuthUser authUser){
        return super.getOutgoing(authUser.id());
    }
}
