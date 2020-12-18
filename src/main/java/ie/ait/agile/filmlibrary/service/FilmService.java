package ie.ait.agile.filmlibrary.service;

import ie.ait.agile.filmlibrary.domain.Film;
import ie.ait.agile.filmlibrary.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository repository;

    public List<Film> getFilmList() {
        return repository.findAll();
    }

    public Film findFilm(Long id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Film addFilm(Film film) {
        List<Film> films = repository.findAllByTitle(film.getTitle());
        if (films.isEmpty()) {
            return repository.save(film);
        }
        else {
            throw new IllegalStateException(
                    format("Film title in catalog already with id <%d>", films.get(0).getId()));
        }
    }

    public Film updateFilm(Film film) {
        Film filmToUpdate = findFilm(film.getId());
        filmToUpdate.setTitle(film.getTitle());
        filmToUpdate.setSynopsis(film.getSynopsis());
        return repository.save(filmToUpdate);
    }

    public Film deleteFilm(Long id) {
        Film film = findFilm(id);
        repository.deleteById(id);
        return film;
    }

}
