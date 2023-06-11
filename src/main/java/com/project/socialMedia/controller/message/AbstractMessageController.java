package com.project.socialMedia.controller.message;

import com.project.socialMedia.dto.message.CreateMessageDTO;
import com.project.socialMedia.dto.message.ResponseMessageDTO;
import com.project.socialMedia.exception.AppUserNotFoundException;
import com.project.socialMedia.exception.ForbiddenActionException;
import com.project.socialMedia.model.permission.ChatPermission;
import com.project.socialMedia.model.permission.PermissionStatus;
import com.project.socialMedia.model.request.FriendRequest;
import com.project.socialMedia.model.request.Status;
import com.project.socialMedia.model.user.AppUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.ChatPermissionService;
import com.project.socialMedia.service.FriendRequestService;
import com.project.socialMedia.service.MessageService;
import com.project.socialMedia.util.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public abstract class AbstractMessageController {

    protected MessageService messageService;
    protected AppUserService appUserService;
    protected ChatPermissionService chatPermissionService;
    protected FriendRequestService friendRequestService;

    public AbstractMessageController(MessageService messageService,
                                     AppUserService appUserService,
                                     FriendRequestService friendRequestService,
                                     ChatPermissionService chatPermissionService) {
        this.messageService = messageService;
        this.appUserService = appUserService;
        this.friendRequestService = friendRequestService;
        this.chatPermissionService = chatPermissionService;
    }

    public ResponseEntity<HttpStatus> send(CreateMessageDTO messageDTO,
                                           Long senderId,
                                           Long recipientId) {
        checkUserExistence(senderId);
        checkUserExistence(recipientId);
        checkUserRelations(senderId, recipientId);

        AppUser sender = appUserService.getById(senderId).get();
        AppUser recipient = appUserService.getById(recipientId).get();

        messageService.create(Converter.getMessage(messageDTO, sender, recipient));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<List<ResponseMessageDTO>> getIncoming(Long userId) {
        checkUserExistence(userId);

        List<ResponseMessageDTO> messages = messageService.getIncoming(userId).stream()
                .map(Converter::getMessageDTO).toList();

        return ResponseEntity.ok(messages);
    }

    protected ResponseEntity<List<ResponseMessageDTO>> getOutgoing(Long userId) {
        checkUserExistence(userId);

        List<ResponseMessageDTO> messages = messageService.getOutgoing(userId).stream()
                .map(Converter::getMessageDTO).toList();

        return ResponseEntity.ok(messages);
    }

    protected void checkUserExistence(Long id) {
        if (!appUserService.checkExistence(id)) {
            throw new AppUserNotFoundException(id);
        }
    }

    protected void checkUserRelations(Long u1, Long u2) {

        if(!enabledPermissionExistence(u1, u2) && !approvedRelationExistence(u1,u2)){
            throw new ForbiddenActionException("Users should be friends or get permission");
        }

        if(!enabledPermissionExistence(u1, u2) && approvedRelationExistence(u1,u2)){
            throw new ForbiddenActionException("Users should be friends or get permission");
        }
    }

    protected Boolean approvedRelationExistence(Long u1, Long u2) {
        Optional<FriendRequest> relation = friendRequestService.getByUsers(u1, u2);

        if (relation.isPresent()) {
            Status u1Status = relation.get().getInitiatorStatus();
            Status u2Status = relation.get().getTargetStatus();

            return u1Status.equals(Status.APPROVED) && u2Status.equals(Status.APPROVED);
        }
        return false;
    }

    protected Boolean enabledPermissionExistence(Long u1, Long u2) {
        Optional<ChatPermission> chatPermission = chatPermissionService.getByUsers(u1, u2);

        return chatPermission.map(permission -> permission.getStatus().equals(PermissionStatus.ENABLE)).orElse(false);
    }
}
