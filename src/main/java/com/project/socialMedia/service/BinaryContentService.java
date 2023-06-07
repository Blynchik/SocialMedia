package com.project.socialMedia.service;


import com.project.socialMedia.model.post.BinaryContent;
import com.project.socialMedia.repository.BinaryContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
public class BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public BinaryContentService(BinaryContentRepository binaryContentRepository,
                                ResourceLoader resourceLoader) {
        this.binaryContentRepository = binaryContentRepository;
        this.resourceLoader = resourceLoader;
    }


    @Transactional
    public void create(BinaryContent binaryContent) {
        binaryContentRepository.save(binaryContent);
    }

    public BinaryContent getBinaryContent(String path) throws IOException {

        Resource resource;

        if (path.startsWith("http")) {
            resource = new UrlResource(path);
        } else {
            resource = resourceLoader.getResource("file:" + path);
        }

        byte[] fileBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        BinaryContent binaryContent = new BinaryContent();
        binaryContent.setImgAsBytes(fileBytes);
        binaryContent.setType(path.substring(path.lastIndexOf(".") + 1));

        return binaryContent;
    }
}
