package com.fira.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class JobDetail {

    @jakarta.persistence.Id
    private String Id;

    private String description;

    private String note;
    private String target;
    private Date timeStart;
    private Date timeEnd;

    private String additionInfo;

    public JobDetail() {
        this.Id = UUID.randomUUID().toString();
    }

}
