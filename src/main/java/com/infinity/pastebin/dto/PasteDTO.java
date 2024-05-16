package com.infinity.pastebin.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class PasteDTO {
    @NotEmpty
    private String content;
    private Long createdWho;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatedWho() {
        return createdWho;
    }

    public void setCreatedWho(Long createdWho) {
        this.createdWho = createdWho;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
