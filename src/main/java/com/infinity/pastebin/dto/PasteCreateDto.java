package com.infinity.pastebin.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;

@Getter
@ToString
public class PasteCreateDto {

    @NotEmpty
    @Length(min = 4, max = 16)
    private String title;

    @NotEmpty
    @Length(min = 1, max = 10 * 1024 * 1024)
    private String content;

    @NotEmpty
    @Length(min = 4, max = 16)
    private String author;

    @Min(0)
    @Max(999)
    private long expirationTimeDays;

    private HashSet<String> visibleFor;
}
