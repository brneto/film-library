package ie.ait.agile.filmlibrary;

import ie.ait.agile.filmlibrary.domain.Film;
import ie.ait.agile.filmlibrary.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class FilmLibraryApplicationIT extends MySqlTestContainerBase {

    @Test
    void shouldContextLoads(@Autowired FilmRepository filmRepository) {
        then(filmRepository).isNotNull();
    }

    @Test
    void shouldHaveNoFilmsWithGetAll(@Autowired WebTestClient webClient) {
        webClient
                .get()
                .uri("/api/films/")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Film.class)
                .hasSize(0);
    }

}
