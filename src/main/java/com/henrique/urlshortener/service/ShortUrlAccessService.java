package com.henrique.urlshortener.service;

import com.henrique.urlshortener.model.ShortUrlAccess;
import com.henrique.urlshortener.repository.ShortUrlAccessRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ShortUrlAccessService {

    private final ShortUrlAccessRepository shortUrlAccessRepository;

    public ShortUrlAccessService(ShortUrlAccessRepository shortUrlAccessRepository) {
        this.shortUrlAccessRepository = shortUrlAccessRepository;
    }

    public ShortUrlAccess registerAccess(ShortUrlAccess shortUrlAccess) {

        return shortUrlAccessRepository.save(shortUrlAccess);

    }

    public List<ShortUrlAccess> getAccessesByShortUrlId(Long shortUrlId) {

        return shortUrlAccessRepository.findByShortUrlId(shortUrlId);

    }


    public Optional<ShortUrlAccess> getAccessByDateAndShortUrlId(LocalDate date, Long shortUrlId) {

        return shortUrlAccessRepository.findByAccessDateAndShortUrlId(date, shortUrlId);

    }


}
