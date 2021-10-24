package com.example.appcsvfilecreator.service;

import com.example.appcsvfilecreator.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface CSVService {


    ResponseEntity<ResponseMessage> save(MultipartFile file);

    InputStream load();

}
