package com.chalkItUp.chalkItUp.Games;

import com.chalkItUp.chalkItUp.Player.Player;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class GameService {


    private Firestore firestore;

    public String createGame(Game game) {
        try {
            ApiFuture<DocumentReference> games = firestore.collection("Games").add(game);
            return "Document is saved: id is"+ games.get().getId();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error creating game: "+e.getMessage());
        }
    }

    public Game getGame(String id) {
        try {
            ApiFuture<DocumentSnapshot> games = firestore.collection("Games").document(id).get();
            return games.get().toObject(Game.class);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error finding game: "+e.getMessage());
        }
    }

    public List<GameDTO> getAllGames() {
        try {
            ApiFuture<QuerySnapshot> future = firestore.collection("Games").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            List<Game> games = documents.stream()
                                .map(doc -> doc.toObject(Game.class))
                                .sorted(Comparator.comparing(Game::getCreatedAt).reversed())
                                .toList();

            Set<String> userIds = games.stream()
                                .flatMap(game -> game.getPlayers().stream())
                                .map(PlayerGame::getUserId)
                                .collect(Collectors.toSet());

            Map<String, Player> playerMap = new HashMap<>();
            for (String userId : userIds) {
                DocumentSnapshot playerDoc = firestore.collection("Players").document(userId).get().get();
                if (playerDoc.exists()) {
                    Player player = playerDoc.toObject(Player.class);
                    playerMap.put(userId, player);
                }
            }

            return games.stream()
                                .map(game -> GameMapper.toDTO(game, playerMap))
                                .toList();
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error finding games: "+e.getMessage());
        }
    }

    public String updateGame(GameDTO gameDTO) {
        Game game = GameMapper.toEntity(gameDTO);

        if(getGame(game.getId()) == null) {
            return createGame(game);
        }

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("players", game.getPlayers());
            map.put("endTime", game.getEndTime());

            ApiFuture<WriteResult> games = firestore.collection("Games").document(game.getId()).update(map);
            return "Document is updated: id is"+ game.getId();
        } catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error updating game: "+e.getMessage());
        }
    }

    public String deleteGame(String id) {
        try {
            ApiFuture<WriteResult> games = firestore.collection("Games").document(id).delete();
            return "Document is deleted: id is"+ id;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error deleting player: "+e.getMessage());
        }
    }

}
