package com.example.appcsvfilecreator.repository;

import com.example.appcsvfilecreator.entity.Record;

import java.util.List;

public interface RecordRepository {

    Record findById(String id);

    List<Record> findAll();

    Record save(Record record);

}
