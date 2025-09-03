package com.henrique.urlshortener.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "short_urls")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;
    private String shortCode;
    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    private OffsetDateTime expiresAt = OffsetDateTime.now(ZoneOffset.UTC).plusDays(7);

    @OneToMany(mappedBy = "shortUrl", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ShortUrlAccess> accesses;
}
