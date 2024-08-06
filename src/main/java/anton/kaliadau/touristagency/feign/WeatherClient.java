package anton.kaliadau.touristagency.feign;


import anton.kaliadau.touristagency.weather.model.WeatherData;
import feign.Param;
import feign.RequestLine;

public interface WeatherClient {

    @RequestLine("GET /current.json?key={key}&aqi={aqi}&q={latitude},{longitude}")
    WeatherData getWeather(@Param("key") String key,
                           @Param("aqi") String aqi,
                           @Param("latitude") String latitude,
                           @Param("longitude") String longitude
    );
}
