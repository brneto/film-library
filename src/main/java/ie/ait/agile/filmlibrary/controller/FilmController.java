package ie.ait.agile.filmlibrary.controller;

import ie.ait.agile.filmlibrary.domain.Film;
import ie.ait.agile.filmlibrary.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "FilmController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;

    @Operation(tags = "Film", summary = "Find Films", description = "Rest call to return a list of Films")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = APPLICATION_JSON_VALUE)))
    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return service.getFilmList();
    }

    @Operation(tags = "films", summary = "Find an existing film by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element founded"),
            @ApiResponse(responseCode = "404", description = "Provided not existing Film Id")})
    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable Long id) {
        try {
            return service.findFilm(id);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(NOT_FOUND, "Existing film id not provided", ex);
        }
    }

    @Operation(tags = "films", summary = "Add a new film")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entry created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Film already exists")})
    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        return service.addFilm(film);
    }

    @Operation(tags = "films", summary = "Update existing film")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entry updated"),
            @ApiResponse(responseCode = "404", description = "Provided not existing Film Id")})
    @PatchMapping("/films/{id}")
    public Film updateFilm(@Valid @RequestBody Film film) {
        return service.updateFilm(film);
    }

    @Operation(tags = "films", summary = "Delete existing film")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entry deleted"),
            @ApiResponse(responseCode = "404", description = "Provided not existing Film Id")})
    @DeleteMapping("/films/{id}")
    public void deleteFilm(@PathVariable Long id) {
        service.deleteFilm(id);
    }

    @GetMapping("/films/form")
    public String getFilmForm(@Valid Film film) {
        return film.toString();
    }

    @PostMapping("/films/form")
    public String postFilmForm(@Valid Film film) {
        return film.toString();
    }

}
