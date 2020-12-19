package ie.ait.agile.filmlibrary;

import ie.ait.agile.filmlibrary.domain.Film;
import ie.ait.agile.filmlibrary.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.INFERRED;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class FilmLibraryApplicationTests extends MySqlTestContainerBase {

    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldContextLoads(@Autowired FilmRepository filmRepository) {
        then(filmRepository).isNotNull();
    }

    @Test
    void shouldHaveNoFilmsWithGetAll() {
        webClient
                .get()
                .uri("/api/films/")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Film.class)
                .hasSize(0);
    }

    @Test
    @Sql(scripts = "/db/add-one-film.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/delete-all-films.sql", executionPhase = AFTER_TEST_METHOD)
    void shouldReturnOneFilm() {
        Film film = webClient
                .get()
                .uri("/api/films/1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Film.class)
                .returnResult()
                .getResponseBody();

        then(film.getTitle()).isEqualTo("Call of the wild");
    }
}
