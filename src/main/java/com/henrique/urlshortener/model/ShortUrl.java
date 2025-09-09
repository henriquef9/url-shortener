package com.henrique.urlshortener.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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

    public List<ShortUrlAccess> getAccessLast7Days(){

        final OffsetDateTime startDate = OffsetDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIN, ZoneOffset.UTC);
        final OffsetDateTime endDate = OffsetDateTime.of(LocalDate.now(), LocalTime.MAX, ZoneOffset.UTC);

        if(accesses == null || accesses.isEmpty()){
            return new ArrayList<>();
        }

        return this.accesses.stream().filter(
                shortUrlAccess -> {

                    OffsetDateTime accessDate = OffsetDateTime.of(shortUrlAccess.getAccessDate(), LocalTime.of(23, 59, 59), ZoneOffset.UTC);

                    return (accessDate.isAfter(startDate) || accessDate.isEqual(startDate)) &&
                            (accessDate.isBefore(endDate) || accessDate.isEqual(endDate));

                }).toList();

    }

}
