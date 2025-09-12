package com.henrique.urlshortener.controller;


import com.henrique.urlshortener.dto.ShortUrlCreateDTO;
import com.henrique.urlshortener.dto.ShortUrlStatisticsDTO;
import com.henrique.urlshortener.model.ShortUrl;
import com.henrique.urlshortener.service.ShortUrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> getShortUrl(@PathVariable String code) {

        ShortUrl entity = shortUrlService.findByShortCode(code);

        return ResponseEntity.status(HttpStatus.FOUND).header("Location", entity.getOriginalUrl()).build();
    }

    @PostMapping()
    public ResponseEntity<ShortUrlCreateDTO> shortUrl(@RequestParam(value = "originalUrl") String originalUrl) {
        ShortUrl entity = shortUrlService.create(originalUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ShortUrlCreateDTO(
                entity.getId(),
                entity.getOriginalUrl(),
                entity.getShortCode(),
                entity.getCreatedAt(),
                entity.getExpiresAt()
        ));
    }

    @GetMapping("/stats/{code}")
    public ResponseEntity<ShortUrlStatisticsDTO> shortUrlStats(@PathVariable String code) {
        return ResponseEntity.ok(shortUrlService.getStatisticsByShortCode(code));
    }

}
