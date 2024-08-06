package anton.kaliadau.touristagency.weather.impl;

import anton.kaliadau.touristagency.feign.WeatherClient;
import anton.kaliadau.touristagency.weather.WeatherService;
import anton.kaliadau.touristagency.weather.model.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:feign.properties")
public class WeatherServiceImpl implements WeatherService {

    @Value("${feign.apiKey}")
    private String apiKey;
    @Value("${feign.aqi}")
    private String aqi;

    private final WeatherClient weatherClient;

    @Override
    @CacheEvict(value = "weather-requests")
    @Scheduled(fixedRateString = "${caching.spring.weather.requests.TTL}")
    public WeatherData getWeatherData(String latitude, String longitude) {
        return weatherClient.getWeather(apiKey, aqi, latitude, longitude);
    }
}
