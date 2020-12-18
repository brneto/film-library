package ie.ait.agile.filmlibrary.service;

import ie.ait.agile.filmlibrary.domain.Film;
import ie.ait.agile.filmlibrary.repository.FilmRepository;
import org.assertj.core.api.BDDAssertions;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig(FilmService.class)
public class FilmServiceTests {

    @MockBean
    private FilmRepository repository;

    @Autowired
    private FilmService service;

    @Test
    void shouldRegisterANonExistingFilm() {
        // given
        String titleUnderTest = "titleTest";
        Film filmUnderTest = new Film();
        filmUnderTest.setTitle(titleUnderTest);
        given(repository.findAllByTitle(anyString())).willReturn(List.of());
        given(repository.save(eq(filmUnderTest))).willReturn(filmUnderTest);

        // when
        Film expected = service.addFilm(filmUnderTest);

        // then
        then(expected.getTitle()).isEqualTo(titleUnderTest);
    }

    @Test
    void shouldFailToRegisterAnExistingFilm() {
        // given
        String titleUnderTest = "titleTest";
        Film filmUnderTest = new Film();
        filmUnderTest.setTitle(titleUnderTest);
        given(repository.findAllByTitle(anyString())).willReturn(List.of(filmUnderTest));

        // when
        ThrowingCallable expected = () -> service.addFilm(filmUnderTest);

        // then
        thenThrownBy(expected).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldAccessRegisteredFilms() {
        // given
        String titleUnderTest1 = "titleTest1";
        String titleUnderTest2 = "titleTest2";
        Film filmUnderTest1 = new Film();
        Film filmUnderTest2 = new Film();
        filmUnderTest1.setTitle(titleUnderTest1);
        filmUnderTest2.setTitle(titleUnderTest2);
        given(repository.findAll()).willReturn(List.of(filmUnderTest1, filmUnderTest2));

        // when
        List<Film> expectedFilms = service.getFilmList();

        // then
        then(expectedFilms.size()).isEqualTo(2);
        then(expectedFilms.get(0).getTitle()).isEqualTo(titleUnderTest1);
        then(expectedFilms.get(1).getTitle()).isEqualTo(titleUnderTest2);
    }

    @Test
    void shouldFindARegisteredFilmById() {
        // given
        String titleUnderTest = "titleTest";
        Film filmUnderTest = new Film();
        filmUnderTest.setTitle(titleUnderTest);
        given(repository.findById(anyLong())).willReturn(Optional.of(filmUnderTest));

        // when
        Film expected = service.findFilm(2L);

        // then
        then(expected.getTitle()).isEqualTo(titleUnderTest);
    }

    @Test
    void shouldNotFindANotRegisteredFilmById() {
        // given
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        // when
        ThrowingCallable expected = () -> service.findFilm(2L);

        // then
        thenThrownBy(expected).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    void shouldUpdateARegisteredFilm() {
        // given
        String titleUnderTest = "titleTest";
        Film filmUnderTest = mock(Film.class);
        given(filmUnderTest.getId()).willReturn(2L);
        given(filmUnderTest.getTitle()).willReturn(titleUnderTest);
        given(repository.findById(eq(2L))).willReturn(Optional.of(filmUnderTest));
        given(repository.save(any(Film.class))).willReturn(filmUnderTest);

        // when
        Film expected = service.updateFilm(filmUnderTest);

        // then
        BDDMockito.then(filmUnderTest).should().setTitle(anyString());
        BDDMockito.then(filmUnderTest).should().setSynopsis(isNull());
        then(expected.getTitle()).isEqualTo(titleUnderTest);
    }

    @Test
    void shouldDeleteAFilmById() {
        // given
        Film filmUnderTest = new Film();
        filmUnderTest.setId(2L);
        given(repository.findById(eq(2L))).willReturn(Optional.of(filmUnderTest));
        willDoNothing().given(repository).deleteById(anyLong());

        // when
        service.deleteFilm(2L);

        // then
        BDDMockito.then(repository).should().deleteById(eq(2L));
    }

}
