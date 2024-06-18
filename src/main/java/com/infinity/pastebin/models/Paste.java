package com.infinity.pastebin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@Table(name = "paste")
public class Paste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty
    @Length(min = 4, max = 16)
    @Column(name = "title")
    private String title;

    @NotEmpty
    @Length(min = 8, max = 8)
    @Column(name = "key")
    private String key;

    @NotEmpty
    @Length(min = 4, max = 16)
    @Column(name = "author")
    private String author;

    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @NotNull
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @NotEmpty
    @Column(name = "url")
    private String url;

    @NotNull
    @Column(name = "is_private")
    private boolean isPrivate = false;

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "paste_visible_for", joinColumns = @JoinColumn(name = "paste_id"))
    private Set<String> visibleFor = new HashSet<>();

}
