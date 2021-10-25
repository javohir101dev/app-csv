package com.example.appcsvfilecreator.repository;

import com.example.appcsvfilecreator.entity.Record;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecordRepositoryImpl implements RecordRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecordRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //    QUERYS
    private static final String INSERT_RECORD_QUERY = "INSERT INTO RECORD(id, name, docDate) values(?, ?, ?)";
    private static final String UPDATE_RECORD_BY_ID_QUERY = "UPDATE RECORD SET name=?, docDate=? WHERE ID=?";
    private static final String GET_RECORD_BY_ID_QUERY = "SELECT * FROM RECORD WHERE ID=?";
    private static final String GET_RECORDS_QUERY = "SELECT * FROM RECORD";


    @Override
    public Record findById(String id) {
        try {
            return jdbcTemplate.queryForObject(GET_RECORD_BY_ID_QUERY, (rs, rowNum) -> {
                return new Record(rs.getString("id"), rs.getString("name"), rs.getString("docDate"));
            },id);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Record> findAll() {
        return jdbcTemplate.query(GET_RECORDS_QUERY, (rs, rowNum) -> {
            return new Record(rs.getString("id"), rs.getString("name"), rs.getString("docDate"));
        });
    }

    @Override
    public Record save(Record record) {
        Record savingRecord = findById(record.getId());
        if (savingRecord==null){
            jdbcTemplate.update(INSERT_RECORD_QUERY, record.getId(), record.getName(), record.getDocDate());
            return record;
        }
        jdbcTemplate.update(UPDATE_RECORD_BY_ID_QUERY, record.getName(), record.getDocDate(),record.getId() );
        return record;
    }
}
