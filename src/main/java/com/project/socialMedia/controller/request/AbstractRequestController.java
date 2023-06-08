package com.project.socialMedia.controller.request;

import com.project.socialMedia.dto.RequestDTO;
import com.project.socialMedia.dto.userDTO.ResponseAppUserDTO;
import com.project.socialMedia.exception.AppUserNotFoundException;
import com.project.socialMedia.exception.ForbiddenActionException;
import com.project.socialMedia.exception.FriendRequestNotFoundException;
import com.project.socialMedia.model.request.FriendRequest;
import com.project.socialMedia.model.request.Status;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.FriendRequestService;
import com.project.socialMedia.util.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class AbstractRequestController {

    protected final FriendRequestService friendRequestService;
    protected final AppUserService appUserService;

    public AbstractRequestController(FriendRequestService friendRequestService,
                                     AppUserService appUserService) {
        this.friendRequestService = friendRequestService;
        this.appUserService = appUserService;
    }

    public ResponseEntity<?> create(Long targetId,
                                    Long initiatorId) {
        checkUserExistence(targetId);
        checkUserExistence(initiatorId);

        if (targetId.equals(initiatorId)) {
            throw new ForbiddenActionException();
        }

        friendRequestService.create(targetId, initiatorId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<List<ResponseAppUserDTO>> getFriends(Long userId) {
        checkUserExistence(userId);

        List<ResponseAppUserDTO> friends = friendRequestService.getFriends(userId).stream()
                .map(Converter::getAppUserDTO).toList();

        return ResponseEntity.ok(friends);
    }

    public ResponseEntity<List<ResponseAppUserDTO>> getSubscribers(Long userId) {
        checkUserExistence(userId);

        List<ResponseAppUserDTO> subscribers = friendRequestService.getSubscribers(userId).stream()
                .map(Converter::getAppUserDTO).toList();

        return ResponseEntity.ok(subscribers);
    }

    public ResponseEntity<List<RequestDTO>> getIncoming(Long userId) {
        checkUserExistence(userId);

        List<RequestDTO> incoming = friendRequestService.getIncoming(userId).stream()
                .map(Converter::getRequestDTO).toList();

        return ResponseEntity.ok(incoming);
    }

    public ResponseEntity<List<RequestDTO>> getOutgoing(Long userId) {
        checkUserExistence(userId);

        List<RequestDTO> outgoing = friendRequestService.getOutgoing(userId).stream()
                .map(Converter::getRequestDTO).toList();

        return ResponseEntity.ok(outgoing);
    }

    public ResponseEntity<HttpStatus> approve(Long requestId,
                                              Long userId) {
        checkRequestExistence(requestId);
        checkUserExistence(userId);

        FriendRequest request = friendRequestService.getById(requestId).get();

        if (request.getInitiator().getId().equals(userId)) {
            throw new ForbiddenActionException();
        }

        if (!request.getTarget().getId().equals(userId)) {
            throw new ForbiddenActionException();
        }

        friendRequestService.approve(request, userId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<HttpStatus> decline(Long requestId,
                                              Long userId) {
        checkRequestExistence(requestId);
        checkUserExistence(userId);

        FriendRequest request = friendRequestService.getById(requestId).get();

        if (!request.getInitiator().getId().equals(userId) && !request.getTarget().getId().equals(userId)) {
            throw new ForbiddenActionException();
        }

        if (request.getInitiator().getId().equals(userId) && request.getInitiatorStatus().equals(Status.DECLINED)) {
            throw new ForbiddenActionException();
        }

        if (request.getTarget().getId().equals(userId) && request.getTargetStatus().equals(Status.DECLINED)) {
            throw new ForbiddenActionException();
        }

        friendRequestService.decline(request, userId);
        return ResponseEntity.ok().build();
    }

    protected void checkUserExistence(Long id) {
        if (!appUserService.checkExistence(id)) {
            throw new AppUserNotFoundException(id);
        }
    }

    protected void checkRequestExistence(Long id) {
        if (!friendRequestService.checkExistence(id)) {
            throw new FriendRequestNotFoundException(id);
        }
    }
}
