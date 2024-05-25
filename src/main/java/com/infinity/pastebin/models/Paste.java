package com.infinity.pastebin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "paste")
public class Paste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty
    @Length(min = 3, max = 16)
    private String title;

    @NotEmpty
    @Length(min = 8, max = 8)
    @Column(name = "key")
    private String key;

    @Column(name = "createdWho")
    private long createdWho;



    protected Paste() { }



}
