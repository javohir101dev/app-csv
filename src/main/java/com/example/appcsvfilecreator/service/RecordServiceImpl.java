package com.example.appcsvfilecreator.service;

import com.example.appcsvfilecreator.entity.Record;
import com.example.appcsvfilecreator.repository.RecordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository repository;

    public RecordServiceImpl(RecordRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns one Record by id
     * @param id
     * @return ResponseEntity<Record>
     */
    @Override
    public ResponseEntity<Record> get(String id) {
        Optional<Record> optionalRecord = repository.findById(id);
        return optionalRecord.map(
                        record -> new ResponseEntity<>(record, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }

    /**
     * Returns all Records in database
     * @return ResponseEntity<List<Record>>
     */
    @Override
    public ResponseEntity<List<Record>> getAll() {
        List<Record> recordList = repository.findAll();
        if (recordList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(recordList);
    }

    /**
     * Returns list of all parent records
     * @return ResponseEntity<List<Record>>
     */
    @Override
    public ResponseEntity<List<Record>> getParents() {
        try {
            List<Record> recordListAll = repository.findAll();
            List<Record> parentRecordList = new ArrayList<>();
            for (Record record : recordListAll) {
                String id = record.getId();

                if (id.contains(".")) {
//                    taking parent id
                    String parentId = id.substring(0, id.lastIndexOf("."));

                    Optional<Record> optionalRecord = repository.findById(parentId);
                    if (optionalRecord.isPresent()) {
//                        Getting parent record
                        Record recordParent = optionalRecord.get();
                        if (!parentRecordList.contains(recordParent)) {
//                            Checking if exists or not and adding Record to the parentList
                            parentRecordList.add(recordParent);
                        }

                    }
                }
            }
            return ResponseEntity.ok().body(parentRecordList);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     *  Returns list all parent records by childId
     * @param childId
     * @returnResponseEntity<List<Record>>
     */
    @Override
    public ResponseEntity<List<Record>> getParentsByChildId(String childId) {
        try {
            List<Record> parentRecordList = new ArrayList<>();

            while (!childId.equals("")){
                childId = getParentId(childId);
                Optional<Record> optionalRecord = repository.findById(childId);
                optionalRecord.ifPresent(parentRecordList::add);
            }

            return ResponseEntity.ok().body(parentRecordList);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Helper method that returns parent id of child if not returns empty String
     * @param childId
     * @return parentId(String)
     */
    String getParentId(String childId){
        if (childId.contains(".")){
            return childId.substring(0, childId.lastIndexOf("."));
        }
         return "";
    }


    /**
     * Returns list of all child records
     * @return ResponseEntity<List<Record>>
     */
    @Override
    public ResponseEntity<List<Record>> getAllChilds() {
        try {
            List<Record> recordListAll = repository.findAll();
            List<Record> childRecordList = new ArrayList<>();
            List<String> recordIdList = new ArrayList<>();
            for (Record record : recordListAll) {

                    recordIdList.add(record.getId());
            }
            for (Record record : recordListAll) {
                for (String recordId : recordIdList) {
                    if (record.getId().startsWith(recordId + ".")){
                        if (!childRecordList.contains(record)){
                            childRecordList.add(record);
                        }
                    }

                }
            }

            return ResponseEntity.ok().body(childRecordList);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns all child Records by parent id
     * @param parentId
     * @return ResponseEntity<List<Record>>
     */
    @Override
    public ResponseEntity<List<Record>> getChildsByParentId(String parentId) {
        try {
            List<Record> recordListAll = repository.findAll();
            List<Record> childRecordList = new ArrayList<>();
            for (Record record : recordListAll) {
                String id = record.getId();

//                Checking does have child or not
                if (id.startsWith(parentId + ".")) {

                    Optional<Record> optionalRecord = repository.findById(id);

                    if (optionalRecord.isPresent()) {
//                        Getting child record
                        Record recordChild = optionalRecord.get();
                        if (!childRecordList.contains(recordChild)) {
//                            Checking if exists or not and adding Record to the childList
                            childRecordList.add(recordChild);
                        }

                    }

                }
            }
            return ResponseEntity.ok().body(childRecordList);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
