package com.project.socialMedia.service;

import com.project.socialMedia.model.Message;
import com.project.socialMedia.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public void create(Message message) {
        messageRepository.save(message);
    }

    @Transactional
    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getIncoming(Long id) {
        List<Message> incoming = messageRepository.findIncoming(id);
        incoming.sort(Comparator.comparing(Message::getCreatedAt).reversed());
        return incoming;
    }

    public List<Message> getOutgoing(Long id) {
        List<Message> outgoing = messageRepository.findOutgoing(id);
        outgoing.sort(Comparator.comparing(Message::getCreatedAt).reversed());
        return outgoing;
    }
}
