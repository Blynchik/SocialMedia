package com.project.socialMedia.service;

import com.project.socialMedia.model.user.AppUser;
import com.project.socialMedia.model.user.Role;
import com.project.socialMedia.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.socialMedia.config.SecurityConfig.PASSWORD_ENCODER;

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
        user.setPassword(PASSWORD_ENCODER.encode((user.getPassword())));
        user.setEmail(user.getEmail().toLowerCase());
        user.getRoles().add(Role.USER);
        appUserRepository.save(user);
    }

    @Transactional
    public void edit(Long id, AppUser updatedUser){
        AppUser user = appUserRepository.getReferenceById(id);
        updatedUser.setId(user.getId());
        updatedUser.setRoles(user.getRoles());
        updatedUser.setEmail(updatedUser.getEmail().toLowerCase());
        updatedUser.setPassword(PASSWORD_ENCODER.encode(updatedUser.getPassword()));
        appUserRepository.save(updatedUser);
    }

    @Transactional
    public void delete(Long id){
        appUserRepository.deleteById(id);
    }

    public Boolean checkExistence(Long id) {
        return appUserRepository.existsById(id);
    }
}
