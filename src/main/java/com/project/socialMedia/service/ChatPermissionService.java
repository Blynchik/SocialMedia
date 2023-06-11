package com.project.socialMedia.service;

import com.project.socialMedia.model.permission.ChatPermission;
import com.project.socialMedia.model.permission.PermissionStatus;
import com.project.socialMedia.repository.ChatPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ChatPermissionService {

    private final ChatPermissionRepository chatPermissionRepository;

    @Autowired
    public ChatPermissionService(ChatPermissionRepository chatPermissionRepository) {
        this.chatPermissionRepository = chatPermissionRepository;
    }

    @Transactional
    public void create(Long u1, Long u2) {
        if (u1 != null && u2 != null) {
            ChatPermission chatPermission = new ChatPermission();
            chatPermission.setU1(u1);
            chatPermission.setU2(u2);
            chatPermission.setStatus(PermissionStatus.REQUESTED);
            chatPermissionRepository.save(chatPermission);
        }
    }

    public Optional<ChatPermission> getById(Long id) {
        return chatPermissionRepository.findById(id);
    }

    public Optional<ChatPermission> getByUsers(Long u1, Long u2){
        return chatPermissionRepository.findByUsers(u1, u2);
    }

    @Transactional
    public void permit(Boolean permission, Long permissionId){
        if(permission){
            chatPermissionRepository.getReferenceById(permissionId).setStatus(PermissionStatus.ENABLE);
        } else {
            delete(permissionId);
        }
    }

    @Transactional
    public void delete(Long permissionId) {
        chatPermissionRepository.deleteById(permissionId);
    }

    public List<ChatPermission> getUserPermissions(Long userId){
        return chatPermissionRepository.findUserPermissions(userId);
    }

    public List<ChatPermission> getRequests(Long userId){
        return chatPermissionRepository.findRequests(userId);
    }

    public Boolean permissionExistenceByUsers(Long u1, Long u2){
        return chatPermissionRepository.permissionExistence(u1, u2);
    }

    public Boolean permissionExistence(Long id){
        return chatPermissionRepository.existsById(id);
    }

    @Transactional
    public void deleteAllByUserId(Long id){
        chatPermissionRepository.deleteByU1OrU2(id);
    }
}
