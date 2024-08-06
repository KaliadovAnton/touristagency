package anton.kaliadau.touristagency.sight.model;

import anton.kaliadau.touristagency.place.model.PlaceDTO;
import anton.kaliadau.touristagency.touristservice.model.TouristServiceDTO;
import anton.kaliadau.touristagency.weather.model.WeatherData;

import java.util.List;

public record SightDTO(Long id, String sightName, String type, String creationDate, String description, PlaceDTO place,
                       String advice, List<TouristServiceDTO> touristServiceDTOs, WeatherData weatherData) {
    public SightDTO withAdvice(String advice) {
        return new SightDTO(id(), sightName(), type(), creationDate(), description(), place(),
                advice, touristServiceDTOs(), weatherData());
    }
}
