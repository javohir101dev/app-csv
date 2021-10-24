package com.example.appcsvfilecreator.controller;

import com.example.appcsvfilecreator.dto.ResponseMessage;
import com.example.appcsvfilecreator.entity.Record;
import com.example.appcsvfilecreator.service.CSVService;
import com.example.appcsvfilecreator.utils.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/csv")
public class CSVFileController {


    private final CSVService service;

    public CSVFileController(CSVService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        return service.save(file);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource>downloadFile(@PathVariable String fileName){

        InputStreamResource file =new InputStreamResource(service.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
