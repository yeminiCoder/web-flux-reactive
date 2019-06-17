package com.info.org.sid.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    @Id
    private String id;
    private double price;
    private Instant dateTrans;

    @DBRef
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  Societe societe;
}
