package com.example.appcsvfilecreator.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

    /**
     * Response Message text
     */
    private String message;

    /**
     *Url for download file
     */
    private String fileDownloadUrl;


    public ResponseMessage(String message) {
        this.message = message;
        this.fileDownloadUrl = "";
    }


}
