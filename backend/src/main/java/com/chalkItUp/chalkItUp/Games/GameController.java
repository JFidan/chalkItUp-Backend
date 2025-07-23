package com.chalkItUp.chalkItUp.Games;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Games")
@AllArgsConstructor
public class GameController {

    private GameService gameService;

    @PostMapping
    public ResponseEntity<String> addPlayer(@RequestBody Game player) {
        return ResponseEntity.ok(gameService.createGame(player));
    }

    @GetMapping("all")
    public ResponseEntity<List<Game>> getPlayers() {
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getPlayer(@PathVariable String id) {
        return ResponseEntity.ok(gameService.getGame(id));
    }

    @PutMapping
    public ResponseEntity<String> updatePlayer(@RequestBody Game player) {
        return ResponseEntity.ok(gameService.updateGame(player));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
