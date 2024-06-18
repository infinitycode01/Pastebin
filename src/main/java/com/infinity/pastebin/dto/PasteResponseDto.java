package com.infinity.pastebin.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@ToString
@Builder
public class PasteResponseDto {

    @NotEmpty
    @Length(min = 4, max = 16)
    private String title;

    @NotEmpty
    @Length(min = 1, max = 10 * 1024 * 1024)
    private String content;

    @NotEmpty
    @Length(min = 4, max = 16)
    private String author;

    @NotNull
    private LocalDateTime creationDate;

    @NotNull
    private LocalDateTime expirationDate;

    @NotEmpty
    private String url;

    @NotNull
    private boolean isPrivate;

    @NotEmpty
    @ElementCollection
    private Set<String> visibleFor;

}
