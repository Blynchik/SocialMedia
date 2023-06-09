package com.project.socialMedia.controller.request;

import com.project.socialMedia.dto.request.RequestDTO;
import com.project.socialMedia.dto.user.ResponseAppUserDTO;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.FriendRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class RequestController extends AbstractRequestController {


    public RequestController(FriendRequestService friendRequestService,
                             AppUserService appUserService) {
        super(friendRequestService, appUserService);
    }

    @PostMapping("/addFriend")
    public ResponseEntity<?> createFriendRequest(@RequestParam Long id,
                                       @AuthenticationPrincipal AuthUser authUser) {
        return super.create(id, authUser.id());
    }

    @GetMapping("/friends")
    public ResponseEntity<List<ResponseAppUserDTO>> getFriends(@AuthenticationPrincipal AuthUser authUser) {
        return super.getFriends(authUser.id());
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<ResponseAppUserDTO>> getSubscribers(@AuthenticationPrincipal AuthUser authUser){
        return super.getSubscribers(authUser.id());
    }

    @GetMapping("/request/incoming")
    public ResponseEntity<List<RequestDTO>> getIncoming(@AuthenticationPrincipal AuthUser authUser){
        return super.getIncoming(authUser.id());
    }

    @GetMapping("/request/outgoing")
    public ResponseEntity<List<RequestDTO>> getOutgoing(@AuthenticationPrincipal AuthUser authUser){
        return super.getOutgoing(authUser.id());
    }

    @PatchMapping("/request/{id}/approve")
    public ResponseEntity<HttpStatus> approve(@PathVariable Long id,
                                              @AuthenticationPrincipal AuthUser authUser){
        return super.approve(id, authUser.id());
    }

    @PatchMapping("/request/{id}/decline")
    public ResponseEntity<HttpStatus> decline(@PathVariable Long id,
                                              @AuthenticationPrincipal AuthUser authUser){
        return super.decline(id, authUser.id());
    }
}
