package anton.kaliadau.touristagency.place;

import anton.kaliadau.touristagency.place.model.Place;
import anton.kaliadau.touristagency.place.model.PlaceDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaceMapperTest {
    private final PlaceMapper placeMapper = Mappers.getMapper(PlaceMapper.class);

    @Test
    void toPlace() {
        //given: valid place dto
        var placeDTO = new PlaceDTO(1L, "Madrid", 123123L, true);
        var expected = new Place(placeDTO.id(), placeDTO.placeName(), placeDTO.population(), List.of(), placeDTO.metroAvailable(), null, null);
        //when:
        var result = placeMapper.toPlace(placeDTO);
        //then:
        assertEquals(expected, result);
    }

    @Test
    void toPlaceDTO() {
        var expected = new PlaceDTO(1L, "Madrid", 123123L, true);
        var place = new Place(expected.id(), expected.placeName(), expected.population(), List.of(), expected.metroAvailable(), null, null);
        //when:
        var result = placeMapper.toPlaceDTO(place);
        //then:
        assertEquals(expected, result);
    }
}