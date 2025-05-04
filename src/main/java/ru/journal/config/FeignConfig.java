package ru.journal.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import java.nio.charset.StandardCharsets;

@Configuration
public class FeignConfig {

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }

    private ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        return () -> {
            XmlMapper xmlMapper = new XmlMapper();

            MappingJackson2XmlHttpMessageConverter converter =
                    new MappingJackson2XmlHttpMessageConverter(xmlMapper);
            converter.setDefaultCharset(StandardCharsets.UTF_8);

            return new HttpMessageConverters(converter);
        };
    }
}