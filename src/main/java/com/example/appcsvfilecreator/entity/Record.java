package com.example.appcsvfilecreator.entity;


import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    /**
     * Record id (String)
     */
    private String id;

    /**
     * Record name
     */
    private String name;

    /**
     * Record doc date
     * doc_date: dd.mm.yyyyThh:mm:ss+zz:zz
     */
    private String docDate;

}
