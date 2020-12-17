package ie.ait.ria.springrestbootstrap.service;

import ie.ait.ria.springrestbootstrap.domain.Film;
import ie.ait.ria.springrestbootstrap.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        if (repository.findAllByTitle(film.getTitle()).isEmpty())
            return repository.save(film);
        else
            throw new IllegalStateException("This film title already exists");
    }

}
