package com.project.socialMedia.service;

import com.project.socialMedia.model.request.FriendRequest;
import com.project.socialMedia.model.request.Status;
import com.project.socialMedia.model.user.AppUser;
import com.project.socialMedia.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final AppUserService appUserService;

    @Autowired
    public FriendRequestService(FriendRequestRepository friendRequestRepository,
                                AppUserService appUserService) {
        this.friendRequestRepository = friendRequestRepository;
        this.appUserService = appUserService;
    }


    public Optional<FriendRequest> getById(Long requestId) {
        return friendRequestRepository.findById(requestId);
    }

    public List<AppUser> getSubscribers(Long userId) {
        List<FriendRequest> requests = friendRequestRepository.findSubscribers(userId);
        List<AppUser> subscribers = new ArrayList<>();

        for (FriendRequest r : requests) {
            AppUser initiator = r.getInitiator();

            if (!initiator.getId().equals(userId)) {
                subscribers.add(initiator);
            }
        }
        return subscribers;
    }

    public List<AppUser> getFriends(Long userId) {
        List<FriendRequest> requests = friendRequestRepository.findFriends(userId);
        List<AppUser> friends = new ArrayList<>();

        for (FriendRequest r : requests) {
            AppUser initiator = r.getInitiator();
            AppUser target = r.getTarget();

            if (!initiator.getId().equals(userId)) {
                friends.add(initiator);
            } else {
                friends.add(target);
            }
        }
        return friends;
    }

    public List<AppUser> getSubscriptions(Long userId){
        List<FriendRequest> requests = friendRequestRepository.findSubscriptions(userId);
        List<AppUser> subscriptions = new ArrayList<>();

        for(FriendRequest r : requests){
            AppUser target = r.getTarget();

            if(!target.getId().equals(userId)){
                subscriptions .add(target);
            }
        }
        return subscriptions;
    }


    @Transactional
    public void create(Long targetId, Long initiatorId) {
        FriendRequest friendRequest = new FriendRequest();
        AppUser initiator = appUserService.getById(initiatorId).get();
        AppUser target = appUserService.getById(targetId).get();
        friendRequest.setInitiator(initiator);
        friendRequest.setTarget(target);
        friendRequest.setInitiatorStatus(Status.APPROVED);
        friendRequest.setTargetStatus(Status.REQUESTED);
        friendRequestRepository.save(friendRequest);
    }

    public List<FriendRequest> getIncoming(Long userId) {
        List<FriendRequest> incoming = friendRequestRepository.findIncoming(userId);
        incoming.sort(Comparator.comparing(FriendRequest::getCreatedAt).reversed());
        return incoming;
    }

    public List<FriendRequest> getOutgoing(Long userId) {
        List<FriendRequest> outgoing = friendRequestRepository.findOutgoing(userId);
        outgoing.sort(Comparator.comparing(FriendRequest::getCreatedAt).reversed());
        return outgoing;
    }

    @Transactional
    public void approve(FriendRequest request, Long userId) {

        if (request.getInitiator().getId().equals(userId)) {
            request.setInitiatorStatus(Status.APPROVED);
        } else {
            request.setTargetStatus(Status.APPROVED);
        }
        friendRequestRepository.save(request);
    }

    @Transactional
    public void decline(FriendRequest request, Long userId) {

        if (request.getInitiator().getId().equals(userId)) {
            request.setInitiatorStatus(Status.DECLINED);
        } else {
            request.setTargetStatus(Status.DECLINED);
        }


        if ((request.getInitiatorStatus().equals(Status.DECLINED) && request.getTargetStatus().equals(Status.DECLINED)) ||
                (request.getInitiatorStatus().equals(Status.DECLINED) && request.getTargetStatus().equals(Status.REQUESTED))) {
            friendRequestRepository.delete(request);
        } else {
            friendRequestRepository.save(request);
        }
    }

    public List<FriendRequest> getRequestFriends(Long userId) {
        return friendRequestRepository.findFriends(userId);
    }

    public List<FriendRequest> getRequestSubscribers(Long userId) {
        return friendRequestRepository.findSubscribers(userId);
    }

    public List<FriendRequest> getRequestSubscriptions(Long userId){
        return friendRequestRepository.findSubscriptions(userId);
    }

    public Boolean checkExistence(Long requestId) {
        return friendRequestRepository.existsById(requestId);
    }

    public Optional<FriendRequest> getByUsers(Long firstUserId,
                                    Long secondUserId){
        return friendRequestRepository.findByUsers(firstUserId, secondUserId);
    }
}
