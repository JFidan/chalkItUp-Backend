package com.chalkItUp.chalkItUp.Games;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<Game> getAllGames() {
        try {
            ApiFuture<QuerySnapshot> future = firestore.collection("Games").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            return documents.stream()
                    .map(doc -> doc.toObject(Game.class))
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error finding games: "+e.getMessage());
        }
    }

    public String updateGame(Game game) {

        if(getGame(game.getId()) == null) {
            return createGame(game);
        }

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("winner", game.getWinner());
            map.put("loser", game.getLoser());

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
