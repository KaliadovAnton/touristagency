package anton.kaliadau;

import anton.kaliadau.touristagency.place.model.Place;
import anton.kaliadau.touristagency.place.model.PlaceDTO;
import anton.kaliadau.touristagency.sight.model.Sight;
import anton.kaliadau.touristagency.sight.model.SightDTO;
import anton.kaliadau.touristagency.sight.model.SightType;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.google.gson.Gson;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractCommonIntegrationTest {

    protected final Sight sight = Sight.builder()
            .id(1L)
            .sightName("Château de Boulogne")
            .sightType(SightType.CASTLE)
            .description("Here we have a description")
            .creationDate(LocalDateTime.of(1525, 1, 1, 0, 0))
            .build();
    protected final Sight newSight = Sight.builder()
            .id(5L)
            .sightName("Eiffel Tower")
            .sightType(SightType.PARK)
            .description("Эйфеллева башня.")
            .creationDate(LocalDateTime.of(1889, 3, 31, 0, 0))
            .build();

    protected final Place place = Place.builder()
            .id(1L)
            .placeName("Madrid")
            .population(123123123L)
            .metroAvailable(true)
            .longitude("-3.70379")
            .latitude("40.416775")
            .sights(List.of(sight))
            .build();

    protected final Gson gson = new Gson();
    protected final PlaceDTO placeDTO = new PlaceDTO(
            place.getId(), place.getPlaceName(), place.getPopulation(), place.getMetroAvailable());
    protected final PlaceDTO newPlaceDTO = new PlaceDTO(
            place.getId(), place.getPlaceName(), place.getPopulation() + 100, place.getMetroAvailable());
    protected final PlaceDTO newPlaceDTO2 = new PlaceDTO(
            1L, "Minsk", place.getPopulation() + 123, place.getMetroAvailable());
    protected final SightDTO newSightDTO = new SightDTO(sight.getId(), sight.getSightName(), SightType.PARK.name(), "1889-03-31T00:00:00", sight.getDescription(), placeDTO, "", null, null);

    @Container
    public static PostgreSQLContainer<?> postgreDBContainer;

    static {
        int containerPort = 5432;
        int localPort = 5435;
        DockerImageName postgres = DockerImageName.parse("postgres:13.15");
        postgreDBContainer = new PostgreSQLContainer<>(postgres)
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("123")
                .withReuse(true)
                .withExposedPorts(containerPort)
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
                ));
        postgreDBContainer.start();
    }
}
