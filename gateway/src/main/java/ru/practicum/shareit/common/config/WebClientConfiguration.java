package ru.practicum.shareit.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.BookingClient;
import ru.practicum.shareit.item.ItemClient;
import ru.practicum.shareit.request.RequestClient;
import ru.practicum.shareit.user.UserClient;

@Configuration
public class WebClientConfiguration {
    @Value("${shareit-server.url}")
    public String serverUrl;


    @Bean
    public UserClient userClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/users"))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();

        return new UserClient(restTemplate);
    }

    @Bean
    public RequestClient requestClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/requests"))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();

        return new RequestClient(restTemplate);
    }

    @Bean
    public ItemClient itemClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/items"))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();

        return new ItemClient(restTemplate);
    }

    @Bean
    public BookingClient bookingClient(RestTemplateBuilder builder) {
        var restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/bookings"))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();

        return new BookingClient(restTemplate);
    }
}
