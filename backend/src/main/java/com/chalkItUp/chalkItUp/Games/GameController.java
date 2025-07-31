package com.chalkItUp.chalkItUp.Games;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/games")
@AllArgsConstructor
public class GameController {

    private GameService gameService;

    @PostMapping
    public ResponseEntity<String> addGame(@RequestBody Game player) {
        return ResponseEntity.ok(gameService.createGame(player));
    }

    @GetMapping("all")
    public ResponseEntity<List<GameDTO>> getGames() {
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable String id) {
        return ResponseEntity.ok(gameService.getGame(id));
    }

    @PutMapping
    public ResponseEntity<String> updateGame(@RequestBody GameDTO dto) {
        return ResponseEntity.ok(gameService.updateGame(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
