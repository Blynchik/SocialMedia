package com.project.socialMedia.controller.permission;

import com.project.socialMedia.exception.AppUserNotFoundException;
import com.project.socialMedia.exception.ChatPermissionNotFoundException;
import com.project.socialMedia.exception.ForbiddenActionException;
import com.project.socialMedia.model.permission.ChatPermission;
import com.project.socialMedia.model.permission.PermissionStatus;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.ChatPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/permission")
public class PermissionController {

    private final ChatPermissionService chatPermissionService;
    private final AppUserService appUserService;

    @Autowired
    public PermissionController(ChatPermissionService chatPermissionService,
                                AppUserService appUserService) {
        this.chatPermissionService = chatPermissionService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatPermission>> getUserPermissions(@AuthenticationPrincipal AuthUser authUser) {
        List<ChatPermission> permissions = chatPermissionService.getUserPermissions(authUser.id());
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<ChatPermission>> getRequests(@AuthenticationPrincipal AuthUser authUser){
        List<ChatPermission> requests = chatPermissionService.getRequests(authUser.id());
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@AuthenticationPrincipal AuthUser authUser,
                                             @RequestParam Long userId) {
        checkUserExistence(userId);

        Boolean permissionExists = chatPermissionService.permissionExistenceByUsers(authUser.id(), userId);

        if(permissionExists){
            throw new ForbiddenActionException("Permission already exists. Check permissions or requests");
        }

        chatPermissionService.create(authUser.id(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/permit")
    public ResponseEntity<HttpStatus> permit(@PathVariable Long id,
                                             @RequestParam Boolean permission,
                                             @AuthenticationPrincipal AuthUser authUser){

        checkPermissionExistence(id);
        ChatPermission chatPermission = chatPermissionService.getById(id).get();

        if(!(chatPermission.getU1().equals(authUser.id()) ||
                chatPermission.getU2().equals(authUser.id()))){
            throw new ForbiddenActionException("Not your property");
        }

        if(chatPermission.getU1().equals(authUser.id()) && chatPermission.getStatus().equals(PermissionStatus.REQUESTED)){
            throw new ForbiddenActionException("Wait permission from second user");
        }

        chatPermissionService.permit(permission, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/deny")
    public ResponseEntity<HttpStatus> deny(@PathVariable Long id,
                                           @AuthenticationPrincipal AuthUser authUser){
        checkPermissionExistence(id);
        ChatPermission chatPermission = chatPermissionService.getById(id).get();

        if(!(chatPermission.getU1().equals(authUser.id()) ||
                chatPermission.getU2().equals(authUser.id()))){
            throw new ForbiddenActionException("Not your property");
        }

        chatPermissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void checkUserExistence(Long id) {
        if (!appUserService.checkExistence(id)) {
            throw new AppUserNotFoundException(id);
        }
    }

    private void checkPermissionExistence(Long id){
        if(!chatPermissionService.permissionExistence(id)){
            throw new ChatPermissionNotFoundException(id);
        }
    }
}
