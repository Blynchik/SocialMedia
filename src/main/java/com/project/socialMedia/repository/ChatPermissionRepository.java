package com.project.socialMedia.repository;

import com.project.socialMedia.model.permission.ChatPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatPermissionRepository extends JpaRepository<ChatPermission, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ChatPermission r WHERE " +
            "(r.u1 = :u1 AND r.u2 = :u2) OR (r.u1 = :u2 AND r.u2 = :u1)")
    Boolean permissionExistence(Long u1, Long u2);

    @Query("SELECT r FROM ChatPermission r WHERE r.u1 = :userId OR r.u2 =: userId")
    List<ChatPermission> findUserPermissions(Long userId);

    @Query("SELECT r FROM ChatPermission r WHERE r.status = 'REQUESTED' AND (r.u1 = :userId OR r.u2 =:userId)")
    List<ChatPermission> findRequests(Long userId);

    @Query("SELECT r FROM ChatPermission r WHERE (r.u1 = :u1 OR r.u2 = :u1) AND " +
            "(r.u1 = :u2 OR r.u2 = :u2)")
    Optional<ChatPermission> findByUsers(Long u1, Long u2);
}
