package com.example.appcsvfilecreator.utils;

import com.example.appcsvfilecreator.entity.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class CSVHelper {


    /**
     * Check if Multipart file content type matches or not
     * @return boolean
     */

    public static boolean isFormatMatches(MultipartFile multipartFile) {
        String TYPE = "application/vnd.ms-excel";  // CSV file content type format
        String TYPE2 = "text/csv";
        return Objects.equals(multipartFile.getContentType(), TYPE) || TYPE2.equals(multipartFile.getContentType());
    }

    /**
     * Parses Multipart file to Records List
     * @return List<Record>
     */
    public static List<Record> toRecordList(MultipartFile multipartFile) {
        try {
            InputStream is = multipartFile.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8));
            List<Record> recordsList = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                Record record = new Record();
                record.setId(parts[0]);
                record.setName(parts[1]);
                record.setDocDate(parts[2]);
                recordsList.add(record);
            }
            bufferedReader.close();
            is.close();
            return recordsList;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse CSV multipartFile: " + e.getMessage());
        }
    }

    /**
     * Parse recordlist to InputStream
     * @param recordsList
     * @return ByteArrayInputStream
     */
    public static InputStream recordsToCSV(List<Record> recordsList) {

        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(byteArrayOutputStream), format)) {
             for (Record record : recordsList) {
                List<String> data = Arrays.asList(
                        record.getId(),
                        record.getName(),
                        record.getDocDate()
                );

                csvPrinter.printRecord(data);

            }
            csvPrinter.flush();

            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
