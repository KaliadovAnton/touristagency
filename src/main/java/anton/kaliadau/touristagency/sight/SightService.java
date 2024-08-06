package anton.kaliadau.touristagency.sight;

import anton.kaliadau.touristagency.sight.model.SightDTO;

import java.util.List;

public interface SightService {
    SightDTO save(SightDTO sightDTO);

    List<SightDTO> getAll();

    List<SightDTO> getAllByPlaceName(String placeName);

    void updateSightDescription(SightDTO sightDTO);

    void deleteBySightName(String sightName);

    List<SightDTO> getWithOptions(String type, Boolean sorted);
}
