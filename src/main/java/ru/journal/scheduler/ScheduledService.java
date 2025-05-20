package ru.journal.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.journal.service.CBRService;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledService {
    private final CBRService cbrService;

    @Scheduled(fixedRateString = "${scheduled-rate}")
    private void updateRates() {
        log.info("Scheduled update rates");
        cbrService.updateRates();
    }
}