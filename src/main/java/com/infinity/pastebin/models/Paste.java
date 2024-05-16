package com.infinity.pastebin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "paste")
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "key")
    private String key;

    @Column(name = "createdWho")
    private Long createdWho;

    @NotEmpty
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @NotEmpty
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public Paste() {}

    public Paste(Long id, String key, Long createdWho, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.key = key;
        this.createdWho = createdWho;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public Long getCreatedWho() {
        return createdWho;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCreatedWho(Long createdWho) {
        this.createdWho = createdWho;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
