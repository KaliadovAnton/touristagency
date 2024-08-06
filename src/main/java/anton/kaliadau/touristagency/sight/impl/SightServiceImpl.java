package anton.kaliadau.touristagency.sight.impl;

import anton.kaliadau.touristagency.sight.SightMapper;
import anton.kaliadau.touristagency.sight.SightRepository;
import anton.kaliadau.touristagency.sight.SightService;
import anton.kaliadau.touristagency.sight.model.Sight;
import anton.kaliadau.touristagency.sight.model.SightDTO;
import anton.kaliadau.touristagency.weather.AdviceService;
import anton.kaliadau.touristagency.weather.WeatherService;
import anton.kaliadau.touristagency.weather.model.WeatherData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SightServiceImpl implements SightService {

    private final SightRepository sightRepository;
    private final WeatherService weatherService;
    private final AdviceService adviceService;
    private final SightMapper sightMapper = Mappers.getMapper(SightMapper.class);

    @Override
    public SightDTO save(SightDTO sightDTO) {
        var sight = sightMapper.toSight(sightDTO);
        return sightMapper.toSightDTO(sightRepository.save(sight));
    }

    @Override
    public List<SightDTO> getAll() {
        return sightRepository.findAll().stream()
                .map(sightMapper::toSightDTO)
                .toList();
    }

    @Override
    public List<SightDTO> getAllByPlaceName(String placeName) {
        var sights = sightRepository.findByPlaceName(placeName);
        if (!sights.isEmpty()) {
            final var weatherData = addWeatherData(sights);
            final var advice = adviceService.getAdvice(weatherData);
            sights.forEach(sight -> sight.setWeatherData(weatherData));
            return sights.stream()
                    .map(sightMapper::toSightDTO)
                    .map(sightDTO -> sightDTO.withAdvice(advice))
                    .toList();
        }
        return List.of();
    }

    private WeatherData addWeatherData(List<Sight> sights) {
        var place = sights.get(0).getPlace();
        var latitude = place.getLatitude();
        var longitude = place.getLongitude();
        return weatherService.getWeatherData(latitude, longitude);
    }

    @Override
    public void updateSightDescription(SightDTO sightDTO) {
        sightRepository.updateSightDescription(sightDTO.sightName(), sightDTO.description());
    }

    @Override
    public void deleteBySightName(String sightName) {
        sightRepository.deleteBySightName(sightName);
    }

    @Override
    public List<SightDTO> getWithOptions(String sightName, Boolean sorted) {
        var sightNameIsValid = sightName != null && !sightName.isEmpty() && !sightName.isBlank();
        List<Sight> sights;
        if (sorted != null && sorted) {
            if (sightNameIsValid) {
                sights = sightRepository.findAllBySightNameOrderBySightTypeAsc(sightName);
            } else {
                sights = sightRepository.findAllByOrderBySightTypeAsc();
            }
        } else {
            if (sightNameIsValid) {
                sights = sightRepository.findAllBySightName(sightName);
            } else {
                sights = sightRepository.findAll();
            }
        }
        return sights.stream()
                .map(sightMapper::toSightDTO)
                .toList();
    }
}
