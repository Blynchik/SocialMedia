package com.project.socialMedia.service;

import com.project.socialMedia.model.AppUser;
import com.project.socialMedia.model.Role;
import com.project.socialMedia.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService (AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    public Optional<AppUser> getById(Long id){
        return appUserRepository.findById(id);
    }

    public List<AppUser> getAll(){
        return appUserRepository.findAll();
    }

    public Optional<AppUser> getByEmail(String email){
        return appUserRepository.findByEmail(email);
    }

    @Transactional
    public void create(AppUser user){
        user.getRoles().add(Role.USER);
        appUserRepository.save(user);
    }

    @Transactional
    public void update(Long id, AppUser updatedUser){
        AppUser user = appUserRepository.getReferenceById(id);
        updatedUser.setId(user.getId());
        updatedUser.setRoles(user.getRoles());
        appUserRepository.save(updatedUser);
    }

    @Transactional
    public void delete(Long id){
        appUserRepository.deleteById(id);
    }
}
