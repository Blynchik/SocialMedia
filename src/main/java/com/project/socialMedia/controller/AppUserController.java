package com.project.socialMedia.controller;

import com.project.socialMedia.model.AppUser;
import com.project.socialMedia.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AppUserController extends AbstractUserController {

    @Autowired
    public AppUserController(AppUserService appUserService){
        super(appUserService);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<AppUser> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<AppUser> deleteById(@PathVariable Long id) {
        return super.deleteById(id);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody AppUser updatedAppUser) {
        return super.update(id, updatedAppUser);
    }
}
