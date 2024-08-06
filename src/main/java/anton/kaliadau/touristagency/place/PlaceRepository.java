package anton.kaliadau.touristagency.place;

import anton.kaliadau.touristagency.place.model.Place;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Modifying
    @Query("update Place p set p.population = :population, p.metroAvailable = :metroAvailability where p.placeName = :placeName")
    void updatePlacePopulationAndMetroByPlaceName(@Param("population") Long population, @Param("metroAvailability") Boolean metroAvailability, @Param("placeName") String placeName);

    @Modifying
    @Query("update Place p set p.population = :population where p.placeName = :placeName")
    void updatePlacePopulationByPlaceName(@Param("population") Long population, @Param("placeName") String placeName);

    @Modifying
    @Query("update Place p set p.metroAvailable = :metroAvailability where p.placeName = :placeName")
    void updatePlaceMetroAvailabilityByPlaceName(@Param("metroAvailability") Boolean metroAvailability, @Param("placeName") String placeName);
}
