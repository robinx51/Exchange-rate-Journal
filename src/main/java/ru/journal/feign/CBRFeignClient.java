package ru.journal.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "CBR",
        url = "${api-url.cbr-daily}"
)
public interface CBRFeignClient {
    @GetMapping(
            produces = "application/xml;charset=UTF-8"
    )
    String getRates();
}
