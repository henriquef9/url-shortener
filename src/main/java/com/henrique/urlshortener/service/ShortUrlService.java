package com.henrique.urlshortener.service;

import com.henrique.urlshortener.dto.ShortUrlStatisticsDTO;
import com.henrique.urlshortener.exception.NotFoundEntityException;
import com.henrique.urlshortener.model.ShortUrl;
import com.henrique.urlshortener.model.ShortUrlAccess;
import com.henrique.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

@Service
public class ShortUrlService {

    private final int MAX_LENGTH_CODE = 8;

    private final ShortUrlRepository shortUrlRepository;

    private final ShortUrlAccessService shortUrlAccessService;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, ShortUrlAccessService shortUrlAccessService) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortUrlAccessService = shortUrlAccessService;
    }

    public ShortUrl create(String originalUrl) {

        String shortCode = generateCode(originalUrl);

        ShortUrl entity = new ShortUrl();

        entity.setShortCode(shortCode);
        entity.setOriginalUrl(originalUrl);

        return shortUrlRepository.save(entity);

    }

    public ShortUrl findByShortCode(String shortCode) throws NotFoundEntityException {

        ShortUrl shortUrlEntity = shortUrlRepository.findByShortCode(shortCode).orElseThrow(() -> new NotFoundEntityException("URL short not found"));

        Optional<ShortUrlAccess> shortUrlAccess = shortUrlAccessService.getAccessByDateAndShortUrlId(LocalDate.now(), shortUrlEntity.getId());

        ShortUrlAccess accessEntity;
        if(shortUrlAccess.isPresent()) {

            accessEntity = shortUrlAccess.get();

            accessEntity.incrementAccessCount();

        }else{

            accessEntity = new ShortUrlAccess();

            accessEntity.setShortUrl(shortUrlEntity);
            accessEntity.setAccessDate(LocalDate.now());
            accessEntity.setAccessCount(1);

        }

        shortUrlAccessService.registerAccess(accessEntity);

        return shortUrlEntity;

    }

    public ShortUrlStatisticsDTO getStatisticsByShortCode(String shortCode) {

        ShortUrl shortUrlEntity = shortUrlRepository.findByShortCode(shortCode).orElseThrow(() -> new NotFoundEntityException("URL short not found"));


        List<ShortUrlAccess> shortUrlAccesses = shortUrlAccessService.getAccessesByShortUrlId(shortUrlEntity.getId());

        Long totalAccessCount = null;

        LocalDate lastDate = null;

        if (!shortUrlAccesses.isEmpty()) {

            totalAccessCount = shortUrlAccesses.stream().mapToLong(ShortUrlAccess::getAccessCount).sum();

            lastDate = shortUrlAccesses.stream().max(Comparator.comparing(ShortUrlAccess::getAccessDate)).get().getAccessDate();

        }

        return new ShortUrlStatisticsDTO(
                shortUrlEntity.getId(),
                shortUrlEntity.getOriginalUrl(),
                shortUrlEntity.getShortCode(),
                shortUrlEntity.getCreatedAt(),
                shortUrlEntity.getExpiresAt(),
                shortUrlAccesses,
                totalAccessCount,
                lastDate
        );


    }

    private String generateCode(String originalUrl) {

        String hash = generateCodeHash(originalUrl);

        String code = hash.substring(0, MAX_LENGTH_CODE);

        if(!shortUrlRepository.existsByShortCode(code)) {
            return code;
        }

        do {

            String salt = UUID.randomUUID().toString();

            String input = originalUrl + salt;

            hash = generateCodeHash(input);

            code = hash.substring(0, MAX_LENGTH_CODE);

        } while (shortUrlRepository.existsByShortCode(code));

        return code;

    }

    private static String generateCodeHash(String originalUrl) {

        try {

            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = algorithm.digest(originalUrl.getBytes());

            StringBuilder hexString = new StringBuilder();

            return HexFormat.of().formatHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating URL code", e);
        }

    }

}
