package com.project.socialMedia.util;

import com.project.socialMedia.dto.CreateAppUserDTO;
import com.project.socialMedia.dto.ResponseAppUserDTO;
import com.project.socialMedia.model.user.AppUser;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class Converter {

    private final static ModelMapper modelMapper = new ModelMapper();

    public static AppUser getAppUser(CreateAppUserDTO appUserDTO){
        return modelMapper.map(appUserDTO, AppUser.class);
    }

    public static ResponseAppUserDTO getAppUserDTO(AppUser appUser){
        return modelMapper.map(appUser, ResponseAppUserDTO.class);
    }
}
