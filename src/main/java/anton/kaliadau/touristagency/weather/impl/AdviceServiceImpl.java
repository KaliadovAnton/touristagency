package anton.kaliadau.touristagency.weather.impl;

import anton.kaliadau.touristagency.weather.AdviceService;
import anton.kaliadau.touristagency.weather.model.WeatherData;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Service;

@Service
public class AdviceServiceImpl implements AdviceService {
    @Override
    public String getAdvice(WeatherData weatherData) {
        var adviceMessage = "Advice.";
        if (isCold(weatherData)) {
            adviceMessage = adviceMessage.concat(" It is cold out there. Get something warm to wear.");
        }
        if (isHot(weatherData)) {
            adviceMessage = adviceMessage.concat(" It is hot out there. Maybe eat some ice cream?");
        }
        if (isWindy(weatherData)) {
            adviceMessage = adviceMessage.concat(" Windy, stay at the hotel.");
        }
        if (isRainy(weatherData)) {
            adviceMessage = adviceMessage.concat(" Make sure to get an umbrella.");
        }
        if (isSnowy(weatherData)) {
            adviceMessage = adviceMessage.concat(" Let it snow, let it snow, let it snow.");
        }
        return adviceMessage;
    }

    private boolean isCold(WeatherData weatherData) {
        return Range.of(Double.MIN_VALUE, 5D).contains(weatherData.getCurrent().getFeelslikeC());
    }

    private boolean isHot(WeatherData weatherData) {
        return Range.of(30D, Double.MAX_VALUE).contains(weatherData.getCurrent().getFeelslikeC());
    }

    private boolean isWindy(WeatherData weatherData) {
        return Range.of(39D, Double.MAX_VALUE).contains(weatherData.getCurrent().getWindKph());
    }

    private boolean isRainy(WeatherData weatherData) {
        return weatherData.getCurrent().getCondition().getText().toLowerCase().contains("rain");
    }

    private boolean isSnowy(WeatherData weatherData) {
        return weatherData.getCurrent().getCondition().getText().toLowerCase().contains("snow");
    }
}
