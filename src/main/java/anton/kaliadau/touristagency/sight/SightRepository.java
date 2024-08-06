package anton.kaliadau.touristagency.sight;

import anton.kaliadau.touristagency.sight.model.Sight;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface SightRepository extends JpaRepository<Sight, Long> {
    @Query("select s from Sight s where s.place.placeName = :placeName")
    List<Sight> findByPlaceName(@Param("placeName") String placeName);

    @Modifying
    @Query("update Sight s set s.description = :description where s.sightName = :sightName")
    void updateSightDescription(@Param("sightName") String sightName, @Param("description") String description);

    @Modifying
    @Query("delete Sight s where s.sightName = :sightName")
    void deleteBySightName(@Param("sightName") String sightName);

    List<Sight> findAllBySightNameOrderBySightTypeAsc(String sightName);

    List<Sight> findAllByOrderBySightTypeAsc();

    List<Sight> findAllBySightName(String sightName);
}
