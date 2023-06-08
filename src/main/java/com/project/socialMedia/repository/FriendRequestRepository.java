package com.project.socialMedia.repository;

import com.project.socialMedia.model.request.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query(value = "SELECT r FROM FriendRequest r WHERE (r.target.id=:targetId AND r.targetStatus ='REQUESTED')")
    List<FriendRequest> findIncoming(Long targetId);

    @Query(value = "SELECT r FROM FriendRequest  r WHERE (r.initiator.id=:initiatorId AND r.targetStatus ='REQUESTED')")
    List<FriendRequest> findOutgoing(Long initiatorId);

    @Query(value = "SELECT r FROM FriendRequest r WHERE ((r.initiator.id=:userId OR r.target.id=:userId) AND (r.targetStatus ='APPROVED' AND r.initiatorStatus = 'APPROVED'))")
    List<FriendRequest> findFriends(Long userId);

    @Query(value = "SELECT r FROM FriendRequest r WHERE (" +
            "(r.target.id=:userId AND r.initiatorStatus='APPROVED') OR " +
            "(r.initiator.id=:userId OR r.target.id=:userId) AND (r.targetStatus ='APPROVED' AND r.initiatorStatus = 'APPROVED'))")
    List<FriendRequest> findSubscribers(Long userId);
}
