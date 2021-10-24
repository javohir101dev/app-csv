package com.example.appcsvfilecreator.service;

import com.example.appcsvfilecreator.dto.ResponseMessage;
import com.example.appcsvfilecreator.entity.Record;
import com.example.appcsvfilecreator.repository.RecordRepository;
import com.example.appcsvfilecreator.utils.CSVHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
public class CSVServiceImpl implements CSVService{

    private final RecordRepository recordRepository;

    public CSVServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    /**
     * Saves CSV file to database
     * @param multipartFile
     * @return ResponseEntity<ResponseMessage>
     */
    @Override
    public ResponseEntity<ResponseMessage> save(MultipartFile multipartFile) {

        String message = "";

        if (CSVHelper.isFormatMatches(multipartFile)) {
            try {
                List<Record> recordsList = CSVHelper.toRecordList(multipartFile);
                recordRepository.saveAll(recordsList);

                message = "Uploaded the file successfully: " + multipartFile.getOriginalFilename();

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/csv/download/")
                        .path(Objects.requireNonNull(multipartFile.getOriginalFilename()))
                        .toUriString();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, fileDownloadUri));

            }catch (Exception e){
                throw new RuntimeException("Fail to store CSV data: " + e.getMessage());
            }
        }
        message = "Please upload CSV file!";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseMessage(message));
    }


    /**
     * For uploading CSV file
     * @return InputStream
     */
    @Override
    public InputStream load() {
        List<Record> recordsList = recordRepository.findAll();

        return CSVHelper.recordsToCSV(recordsList);
    }


}
