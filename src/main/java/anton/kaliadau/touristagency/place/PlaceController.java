package anton.kaliadau.touristagency.place;

import anton.kaliadau.touristagency.place.model.PlaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    PlaceDTO save(@RequestBody PlaceDTO placeDTO) {
        return placeService.save(placeDTO);
    }

    @PutMapping(path = "/update")
    void update(@RequestBody PlaceDTO placeDTO) {
        if (placeDTO.placeName() == null) {
            throw new NoSuchElementException();
        }
        placeService.updatePlace(placeDTO);
    }
}
