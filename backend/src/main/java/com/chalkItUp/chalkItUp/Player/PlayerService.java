package com.chalkItUp.chalkItUp.Player;

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
public class PlayerService {

    private Firestore firestore;

    public String createPlayer(Player player) {
        try {
            ApiFuture<DocumentReference> players = firestore.collection("Players").add(player);
            return "Document is saved: id is"+ players.get().getId();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error creating player: "+e.getMessage());
        }
    }

    public Player getPlayer(String id) {
        try {
            ApiFuture<DocumentSnapshot> player = firestore.collection("Players").document(id).get();
            return player.get().toObject(Player.class);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error finding player: "+e.getMessage());
        }
    }

    public List<Player> getAllPlayer() {
        try {
            ApiFuture<QuerySnapshot> future = firestore.collection("Players").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            return documents.stream()
                    .map(doc -> doc.toObject(Player.class))
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error finding player: "+e.getMessage());
        }
    }

    public String updatePlayer(Player player) {

        if(getPlayer(player.getId()) == null) {
            return createPlayer(player);
        }

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("username", player.getUsername());
            map.put("email", player.getEmail());

            ApiFuture<WriteResult> players = firestore.collection("Players").document(player.getId()).update(map);
            return "Document is updated: id is"+ player.getId();
        } catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error updating player: "+e.getMessage());
        }
    }

    public String deletePlayer(String id) {
        try {
            firestore.collection("Players").document(id).delete();
            return "Document is deleted: id is"+ id;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error deleting player: "+e.getMessage());
        }
    }
}
