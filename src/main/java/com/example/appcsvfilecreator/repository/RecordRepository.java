package com.example.appcsvfilecreator.repository;

import com.example.appcsvfilecreator.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, String> {
}
