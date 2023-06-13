package com.project.socialMedia.controller.post;

import com.project.socialMedia.dto.post.CreatePostDTO;
import com.project.socialMedia.dto.post.ResponsePostDTO;
import com.project.socialMedia.model.post.Post;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.PostService;
import com.project.socialMedia.validator.PostValidator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class PostController extends AbstractPostController {

    @Autowired
    public PostController(PostService postService,
                          AppUserService appUserService,
                          PostValidator postValidator) {
        super(postService, appUserService, postValidator);
    }

    @Operation(summary = "Метод позваоляет создать новый пост. " +
            "Картинка сохраняется в виде массива байт в БД. " +
            "Успешный ответ CREATED. " +
            "Поле header не может быть длиной больше 100 символов. " +
            "Поле text не может состоять из пустых символов и " +
            "быть больше 5000 символов. " +
            "Поле pathToImage принимает полное имя файла. " +
            "Файл должен быть в формате png, jpeg, jpg. " +
            "Принимаются ссылки из интернета и из файловой системы, " +
            "например, C:\\\\Desktop\\\\image.jpg или https://images.ru/image.png. " +
            "Иначе BAD_REQUEST.")
    @PostMapping("/post/create")
    public ResponseEntity<?> create(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody CreatePostDTO postDTO,
                                    BindingResult bindingResult) throws IOException {

        return super.create(authUser.id(), postDTO, bindingResult);
    }

    @Operation(summary = "Возвращает все посты пользователя по его id пустой список. " +
            "Если такого пользователя нет, то FORBIDDEN. " +
            "Посты сортированы по времени от самого ближайшего до самого дальнего по времени. " +
            "Картинка возвращается в виде массива байт, если она есть. " +
            "Успешный ответ OK.")
    @GetMapping("/{userId}/post")
    public ResponseEntity<List<ResponsePostDTO>> getUserPosts(@PathVariable Long userId) throws IOException {
        return super.getUserPosts(userId);
    }

    @Operation(summary = "Метод возвращает посты пользоватлей, на которых текущий " +
            "аутентифицированный пользователь подписался и не отменил подписку, " +
            "или пустой список. " +
            "Посты сортированы по времени от самого ближайшего до самого дальнего по времени. " +
            "Картинка возвращается в виде массива байт, если она есть. " +
            "Успешный ответ OK.")
    @GetMapping("/news")
    public ResponseEntity<Page<ResponsePostDTO>> getSubscriptionPosts(@AuthenticationPrincipal AuthUser authUser,
                                                                      @RequestParam
                                                                      @Positive(message = "Should be positive")
                                                                      int pageNumber,
                                                                      @RequestParam
                                                                      @Positive(message = "Should be positive")
                                                                      int pageSize) {
        return super.getSubscriptionPosts(authUser.id(), pageNumber, pageSize);
    }

    @Operation(summary = "Метод позволяет редактировать уже созданный пост по его id. " +
            "Если поста не существет, вернет NOT_FOUND. " +
            "Попытка изменить пост, не принадлежащий текущему пользователю, " +
            "вернет FORBIDDEN. " +
            "Ответ OK. " +
            "Поле header не может быть длиной больше 100 символов. " +
            "Поле text не может состоять из пустых символов и " +
            "быть больше 5000 символов. " +
            "Поле pathToImage принимает полное имя файла. " +
            "Файл должен быть в формате png, jpeg, jpg. " +
            "Принимаются ссылки из интернета и из файловой системы, " +
            "например, C:/Desktop/image.jpg или https://images.ru/image.png. " +
            "Иначе BAD_REQUEST.")
    @PutMapping("/post/{id}/edit")
    public ResponseEntity<?> edit(@PathVariable Long id,
                                  @Valid @RequestBody CreatePostDTO createPostDTO,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal AuthUser authUser) {
        return super.edit(id, authUser.id(), createPostDTO, bindingResult);
    }

    @Operation(summary = "Метод позволяет удалить пост по его id. " +
            "Если поста не существет, вернет NOT_FOUND. " +
            "Попытка изменить пост, не принадлежащий текущему пользователю, " +
            "вернет FORBIDDEN. " +
            "Ответ NO_CONTENT.")
    @DeleteMapping("/post/{id}/delete")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id,
                                             @AuthenticationPrincipal AuthUser authUser) {
        return super.delete(id, authUser.id());
    }
}

