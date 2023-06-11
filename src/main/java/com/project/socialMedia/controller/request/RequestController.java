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

    /**
     * Метод создает зявку в друзья с другим пользователем.
     * Отправитель с этого момента считается подписчиком второго пользователя.
     * Если пользователя не существует возвращает NOT_FOUND,
     * Если запрос уже создан/такой же запрос отправлен адресатом, то
     * FORBIDDEN
     * @param authUser текущий аутентифицированный пользователь
     * @param id id пользвателя, с кем запрашивается дружба
     */
    @PostMapping("/addFriend")
    public ResponseEntity<?> createFriendRequest(@RequestParam Long id,
                                       @AuthenticationPrincipal AuthUser authUser) {
        return super.create(id, authUser.id());
    }

    /**
     * Метод возвращает входящие
     * заявки в друзья для данного пользователя или пустой список.
     * Заявки сортированы по времени.
     * @param authUser текущий аутентифицированный пользователь
     */
    @GetMapping("/request/incoming")
    public ResponseEntity<List<RequestDTO>> getIncoming(@AuthenticationPrincipal AuthUser authUser){
        return super.getIncoming(authUser.id());
    }

    /**
     * Метод возвращает исходящие
     * заявки в друзья для данного пользователя или пустой список.
     * Заявки сортированы по времени.
     * @param authUser текущий аутентифицированный пользователь
     */
    @GetMapping("/request/outgoing")
    public ResponseEntity<List<RequestDTO>> getOutgoing(@AuthenticationPrincipal AuthUser authUser){
        return super.getOutgoing(authUser.id());
    }

    /**
     * Метод позволяет подтвердить запрос в друзья по id запроса.
     * Если запрос уже подтвержден или не относится к текущему пользователю,
     * то метод возвращает FORBIDDEN.
     * Если запрос не существует - NOT_FOUND.
     * Ответ OK, пользователей можно считать друзьями,
     * пользователи смогут отправлять сообщения без запроса.
     * Друзья считаются подписчиками друг друга.
     * @param id id заявки в друзья
     * @param authUser текущий аутентифицированный пользователь
     */
    @PatchMapping("/request/{id}/approve")
    public ResponseEntity<HttpStatus> approve(@PathVariable Long id,
                                              @AuthenticationPrincipal AuthUser authUser){
        return super.approve(id, authUser.id());
    }

    /**
     * Отменяет дружбу между двумя пользователями по id заявки.
     * Отменивший заявку не считается подписчиком другого пользователя,
     * другой пользователь считается подписчиком до тех пор пока
     * не отменит заявку с этим же id.
     * Если запрос уже отменен или не относится к текущему пользователю,
     * то метод возвращает FORBIDDEN.
     * Если запрос не существует - NOT_FOUND.
     * Ответ OK, если пользоватлеи были друзьями,
     * они больше не смогут отправлять друг другу сообщения
     * до тех пор, пока снова не станут друзьями или
     * подтвердят разрешние на переписку.
     * @param id id заявки в друзья
     * @param authUser текущий аутентифицированный пользователь
     */
    @PatchMapping("/request/{id}/decline")
    public ResponseEntity<HttpStatus> decline(@PathVariable Long id,
                                              @AuthenticationPrincipal AuthUser authUser){
        return super.decline(id, authUser.id());
    }
}
