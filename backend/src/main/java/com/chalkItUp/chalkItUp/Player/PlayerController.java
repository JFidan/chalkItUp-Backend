package com.chalkItUp.chalkItUp.Player;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/player")
@AllArgsConstructor
public class PlayerController {

    private PlayerService playerService;

    @PostMapping
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        return ResponseEntity.ok(playerService.createPlayer(player));
    }

    @GetMapping("all")
    public ResponseEntity<List<Player>> getPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayer());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable String id) {
        return ResponseEntity.ok(playerService.getPlayerWithUserId(id));
    }

    @PutMapping
    public ResponseEntity<String> updatePlayer(@RequestBody Player player) {
        return ResponseEntity.ok(playerService.updatePlayer(player));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable String id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
