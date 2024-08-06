package anton.kaliadau.touristagency.sight;

import anton.kaliadau.touristagency.sight.model.SightDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sight")
public class SightController {

    private final SightService sightService;

    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    SightDTO save(@RequestBody SightDTO sightDTO) {
        return sightService.save(sightDTO);
    }

    @GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    List<SightDTO> getAll(@RequestParam(name = "sightName", required = false) String sightName,
                          @RequestParam(name = "sorted", required = false) Boolean sorted) {
        return sightService.getWithOptions(sightName, sorted);
    }

    @GetMapping(path = "/get/place/{placeName}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<SightDTO> getSightsFromPlace(@PathVariable(name = "placeName") @NonNull String placeName) {
        return sightService.getAllByPlaceName(placeName);
    }

    @PutMapping(path = "/update/description")
    void updateDescription(@RequestBody SightDTO sightDTO) {
        sightService.updateSightDescription(sightDTO);
    }

    @DeleteMapping(path = "/remove/{sightName}")
    void deleteSight(@PathVariable(name = "sightName") String sightName) {
        sightService.deleteBySightName(sightName);
    }

}
