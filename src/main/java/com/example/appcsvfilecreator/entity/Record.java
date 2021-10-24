package com.example.appcsvfilecreator.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity()
public class Record {

    /**
     * Record id (String)
     */
    @Id
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
