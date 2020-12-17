package ie.ait.ria.springrestbootstrap.repository;

import ie.ait.ria.springrestbootstrap.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findAllByTitle(String title);

}
