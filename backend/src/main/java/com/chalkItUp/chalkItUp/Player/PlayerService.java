package com.chalkItUp.chalkItUp.Player;

import com.chalkItUp.chalkItUp.Games.Game;
import com.chalkItUp.chalkItUp.Games.PlayerGame;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerService {

    private Firestore firestore;

    public String createPlayer(Player player) {
        try {
            ApiFuture<DocumentReference> players = firestore.collection("Players").add(player);
            return "Document is saved: id is" + players.get().getId();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error creating player: " + e.getMessage());
        }
    }

    public Player getPlayer(String id) {
        try {
            ApiFuture<DocumentSnapshot> player = firestore.collection("Players").document(id).get();
            return player.get().toObject(Player.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error finding player: " + e.getMessage());
        }
    }

    public List<PlayerDTO> getAllPlayer() {
        try {
            ApiFuture<QuerySnapshot> future = firestore.collection("Players").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            List<Player> player = documents.stream()
                    .map(doc -> doc.toObject(Player.class))
                    .collect(Collectors.toList());

            Map<String, Player> playerMap = player.stream()
                    .collect(Collectors.toMap(Player::getUserId, Function.identity()));

            ApiFuture<QuerySnapshot> futureGames = firestore.collection("Games").get();
            List<QueryDocumentSnapshot> gameDocs = futureGames.get().getDocuments();

            List<Game> games = gameDocs.stream()
                    .map(doc -> doc.toObject(Game.class))
                    .collect(Collectors.toList());

            Map<String, Integer> wins = new HashMap<>();
            Map<String, Integer> losses = new HashMap<>();

            for (Game game : games) {
                for (PlayerGame pg : game.getPlayers()) {
                    String userId = pg.getUserId();
                    if (pg.isWinner()) {
                        wins.put(userId, wins.getOrDefault(userId, 0) + 1);
                    } else {
                        losses.put(userId, losses.getOrDefault(userId, 0) + 1);
                    }
                }
            }

            return player.stream()
                    .map(p -> {
                        int winCount = wins.getOrDefault(p.getUserId(), 0);
                        int lossCount = losses.getOrDefault(p.getUserId(), 0);
                        return PlayerMapper.toDTO(p, winCount, lossCount);
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error finding player: " + e.getMessage());
        }
    }

    public Player getPlayerWithUserId(String userId) {
        try {
            ApiFuture<QuerySnapshot> future = firestore.collection("Players").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            List<Player> players = documents.stream()
                    .map(doc -> doc.toObject(Player.class))
                    .collect(Collectors.toList());

            return players.stream().filter(player -> player.getUserId().equals(userId)).findFirst().orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error finding player: " + e.getMessage());
        }
    }

    public String updatePlayer(PlayerDTO playerDTO) {
        Player player = PlayerMapper.toEntity(playerDTO);
        if (getPlayer(player.getId()) == null) {
            return createPlayer(player);
        }

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("username", player.getUsername());
            map.put("email", player.getEmail());

            ApiFuture<WriteResult> players = firestore.collection("Players").document(player.getId()).update(map);
            return "Document is updated: id is" + player.getId();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error updating player: " + e.getMessage());
        }
    }

    public String deletePlayer(String id) {
        try {
            firestore.collection("Players").document(id).delete();
            return "Document is deleted: id is" + id;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error deleting player: " + e.getMessage());
        }
    }
}
