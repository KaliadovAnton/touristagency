package anton.kaliadau.touristagency.weather;

import anton.kaliadau.touristagency.weather.model.WeatherData;

public interface WeatherService {
    WeatherData getWeatherData(String latitude, String longitude);
}
