package com.example.appcsvfilecreator.controller;


import com.example.appcsvfilecreator.entity.Record;
import com.example.appcsvfilecreator.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/record")
public class RecordController {

    private final RecordService service;

    public RecordController(RecordService service) {
        this.service = service;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Record> get(@PathVariable(value = "id") String id) {
        return service.get(id);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Record>> getAll() {
        return service.getAll();
    }

    @GetMapping("/get/all/parents")
    public ResponseEntity<List<Record>> getAllParents() {
        return service.getParents();
    }

    @GetMapping("/get/all/parentsByChildId/{childId}")
    public ResponseEntity<List<Record>> getParents(@PathVariable String childId) {
        return service.getParentsByChildId(childId);
    }

    @GetMapping("/get/all/childs")
    public ResponseEntity<List<Record>> getAllChilds() {
        return service.getAllChilds();
    }

    @GetMapping("/get/all/childsByParentId/{parentId}")
    public ResponseEntity<List<Record>> getItems(@PathVariable(value = "parentId") String parentId) {
        return service.getChildsByParentId(parentId);
    }

}
