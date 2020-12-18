package ie.ait.agile.filmlibrary.contracts;

import ie.ait.agile.filmlibrary.controller.FilmController;
import ie.ait.agile.filmlibrary.domain.Film;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@SpringJUnitConfig
public abstract class FilmsBase extends ContractsSetup {

    @MockBean
    private FilmController filmController;

    @BeforeEach
    void setUp() {
        Film filmUnderTest = new Film();
        filmUnderTest.setId(1L);
        filmUnderTest.setTitle("Call of the wild");
        filmUnderTest.setSynopsis("A vibrant story of Buck, a big and kindhearted dog.");

        given(filmController.getAllFilms()).willReturn(List.of(filmUnderTest));
        given(filmController.getFilm(anyLong())).willReturn(filmUnderTest);
        given(filmController.addFilm(any(Film.class))).willReturn(filmUnderTest);
        given(filmController.updateFilm(anyLong(), any(Film.class))).willReturn(filmUnderTest);
        willDoNothing().given(filmController).deleteFilm(anyLong());

        standaloneSetup(filmController);
    }
}
