package ie.ait.agile.filmlibrary;

import ie.ait.agile.filmlibrary.domain.Film;
import ie.ait.agile.filmlibrary.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class FilmLibraryApplicationTests extends MySqlTestContainer {

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
        long id = webClient
                .get()
                .uri("/api/films")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Film.class)
                .returnResult()
                .getResponseBody()
                .stream()
                .findAny()
                .get()
                .getId();

        Film film = webClient
                .get()
                .uri("/api/films/" + id)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Film.class)
                .returnResult()
                .getResponseBody();

        then(film.getTitle()).isEqualTo("Call of the wild");
    }

    @Test
    @Sql(scripts = "/db/delete-all-films.sql", executionPhase = AFTER_TEST_METHOD)
    void shouldAddANewFilm() {
        Film filmToAdd = new Film();
        filmToAdd.setTitle("Title Under Test");
        filmToAdd.setSynopsis("Film synopsis");
        Film film = webClient
                .post()
                .uri("/api/films")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(filmToAdd)
                .exchange()
                .expectBody(Film.class)
                .returnResult()
                .getResponseBody();

        then(filmToAdd.getTitle()).isEqualTo(film.getTitle());
        then(filmToAdd.getSynopsis()).isEqualTo(film.getSynopsis());
        then(film.getId()).isPositive();
    }

    @Test
    @Sql(scripts = "/db/add-one-film.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/delete-all-films.sql", executionPhase = AFTER_TEST_METHOD)
    void shouldUpdateAFilm() {
        long id = webClient
                .get()
                .uri("/api/films")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Film.class)
                .returnResult()
                .getResponseBody()
                .stream()
                .findAny()
                .get()
                .getId();

        Film filmToUpdate = new Film();
        filmToUpdate.setTitle("Title Under Test");
        filmToUpdate.setSynopsis("Film synopsis");

        Film film = webClient
                .patch()
                .uri("/api/films/" + id)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(filmToUpdate)
                .exchange()
                .expectBody(Film.class)
                .returnResult()
                .getResponseBody();

        then(film.getTitle()).isEqualTo(filmToUpdate.getTitle());
        then(film.getSynopsis()).isEqualTo(filmToUpdate.getSynopsis());
        then(film.getId()).isEqualTo(id);
    }

    @Test
    @Sql("/db/add-one-film.sql")
    void shouldDeleteAFilm() {
        long id = webClient
                .get()
                .uri("/api/films")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Film.class)
                .returnResult()
                .getResponseBody()
                .stream()
                .findAny()
                .get()
                .getId();

        webClient
                .delete()
                .uri("/api/films/" + id)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
