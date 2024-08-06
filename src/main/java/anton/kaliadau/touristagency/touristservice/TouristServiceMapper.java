package anton.kaliadau.touristagency.touristservice;

import anton.kaliadau.touristagency.touristservice.model.TouristService;
import anton.kaliadau.touristagency.touristservice.model.TouristServiceDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TouristServiceMapper {

    TouristService toTouristService(TouristServiceDTO touristServiceDTO);

    TouristServiceDTO toTouristServiceDTO(TouristService touristServiceDTO);

    List<TouristService> toTouristServices(List<TouristServiceDTO> touristServiceDTOs);

    List<TouristServiceDTO> toTouristServiceDTOs(List<TouristService> touristServiceDTOs);
}
