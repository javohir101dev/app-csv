package com.example.appcsvfilecreator.service;

import com.example.appcsvfilecreator.entity.Record;
import org.springframework.http.ResponseEntity;


import java.util.List;


public interface RecordService {
    ResponseEntity<Record> get(String id);

    ResponseEntity<List<Record>> getAll();

    ResponseEntity<List<Record>> getParents();

    ResponseEntity<List<Record>> getParentsByChildId(String childId);

    ResponseEntity<List<Record>> getAllChilds();

    ResponseEntity<List<Record>> getChildsByParentId(String parentId);

}
