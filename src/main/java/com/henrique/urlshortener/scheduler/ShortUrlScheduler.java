package com.henrique.urlshortener.scheduler;

import com.henrique.urlshortener.model.ShortUrl;
import com.henrique.urlshortener.model.ShortUrlAccess;
import com.henrique.urlshortener.service.ShortUrlService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlScheduler {

    private final ShortUrlService shortUrlService;
    private final Double MIN_DAILY_ACCESS = 2.0;

    private Logger logger = LoggerFactory.getLogger(ShortUrlScheduler.class);

    public ShortUrlScheduler(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @Transactional
    @Scheduled(cron = "59 59 23 * * sun")
    //@Scheduled(cron = "0 */1 * * * *")
    public void clearShortUrlsExpires(){

        logger.info("Executando limpeza de expirados");

        this.shortUrlService.deleteByExpiredToday();

        logger.info("Limpeza expirados com sucesso");
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    //@Scheduled(cron = "0 */1 * * * *") Para teste
    public void renovationShorUrlDateExpired(){

        logger.info("Iniciando verificação de URLs expiradas");

        List<ShortUrl> shortUrlsExpired = shortUrlService.findByExpiredToday();

        shortUrlsExpired
                .forEach(shortUrl -> {

                    double dailyAverage = shortUrl.getAccessLast7Days().stream().mapToInt(ShortUrlAccess::getAccessCount).sum() / 7.0;

                    if (dailyAverage >= MIN_DAILY_ACCESS) {
                        shortUrl.setExpiresAt(shortUrl.getExpiresAt().plusDays(7));
                        shortUrlService.update(shortUrl);
                    }
        });

        logger.info("{} shortUrls expired", shortUrlsExpired.size());

    }

}
