package anton.kaliadau.touristagency.place;

import anton.kaliadau.touristagency.place.model.Place;
import anton.kaliadau.touristagency.place.model.PlaceDTO;

public interface PlaceService {
    PlaceDTO save(PlaceDTO place);

    void updatePlace(PlaceDTO placeDTO);

    Place findById(Long id);
}
