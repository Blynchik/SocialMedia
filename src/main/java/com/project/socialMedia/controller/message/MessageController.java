package com.project.socialMedia.controller.message;

import com.project.socialMedia.dto.message.CreateMessageDTO;
import com.project.socialMedia.dto.message.ResponseMessageDTO;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.ChatPermissionService;
import com.project.socialMedia.service.FriendRequestService;
import com.project.socialMedia.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/message")
public class MessageController extends AbstractMessageController{


    @Autowired
    public MessageController(MessageService messageService,
                             AppUserService appUserService,
                             FriendRequestService friendRequestService,
                             ChatPermissionService chatPermissionService) {
        super(messageService, appUserService, friendRequestService, chatPermissionService);
    }

    /**
     * Метод позволяет отправить сообщение другому пользователю.
     * Если получателя не существует, то возвращает NOT_FOUND.
     * Возможность отправить сообщение имеют только те, кто является
     * друг другу друзьями или при наличии разрешения на отправку
     * сообщений, иначе возвращает FORBIDDEN.
     * При успешной отправке CREATED.
     * @param authUser текущий аутентифицированный пользователь
     * @param messageDTO тело сообщения
     *                   Поле text не может быть пустым или состоять из пустых значений.
     *                   Длина сообщения не должна быть меньше 1 и больше 300 символов.
     *                   Иначе BAD_REQUEST.
     * @param recipientId id получателя
     */
    @PostMapping("/send")
    public ResponseEntity<HttpStatus> send(@AuthenticationPrincipal AuthUser authUser,
                                           @RequestBody CreateMessageDTO messageDTO,
                                           @RequestParam Long recipientId){
        return super.send(messageDTO, authUser.id(), recipientId);
    }

    /**
     * Метод возвращает входящие сообщения пользователя
     * в порядке от последнего к самым дальним по времени
     * или пустой список, если их нет.
     * Ответ OK.
     * @param authUser текущий аутентифицированный пользователь
     */
    @GetMapping("/incoming")
    public ResponseEntity<List<ResponseMessageDTO>> getIncoming(@AuthenticationPrincipal AuthUser authUser){
        return super.getIncoming(authUser.id());
    }

    /**
     * Метод возвращает исходящие сообщения пользователя
     * в порядке от последнего к самым дальним по времени
     * или пустой список, если их нет.
     * Ответ OK.
     * @param authUser текущий аутентифицированный пользователь
     */
    @GetMapping("/outgoing")
    public ResponseEntity<List<ResponseMessageDTO>> getOutgoing(@AuthenticationPrincipal AuthUser authUser){
        return super.getOutgoing(authUser.id());
    }
}
