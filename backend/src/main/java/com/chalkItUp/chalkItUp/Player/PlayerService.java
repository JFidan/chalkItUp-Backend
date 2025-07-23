package com.chalkItUp.chalkItUp.Player;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerService {

    private Firestore firestore;

    public String createPlayer(Player player) {
        try {
            ApiFuture<DocumentReference> players = firestore.collection("Players").add(player);
            return "Document is saved: id is"+ player.getId();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error creating player: "+e.getMessage());
        }
    }




}
