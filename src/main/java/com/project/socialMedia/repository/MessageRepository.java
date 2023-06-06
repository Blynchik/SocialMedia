package com.project.socialMedia.repository;

import com.project.socialMedia.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT r FROM Message r WHERE r.recipient.id=?1")
    List<Message> findIncoming(Long id);

    @Query(value = "SELECT r FROM Message r WHERE r.sender.id=?1")
    List<Message> findOutgoing(Long id);
}
