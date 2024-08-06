package anton.kaliadau.touristagency.weather;

import anton.kaliadau.touristagency.weather.model.WeatherData;

public interface AdviceService {
    String getAdvice(WeatherData weatherData);
}
