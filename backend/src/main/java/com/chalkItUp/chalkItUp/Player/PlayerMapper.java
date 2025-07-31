package com.chalkItUp.chalkItUp.Player;

import com.chalkItUp.chalkItUp.Games.Game;
import com.chalkItUp.chalkItUp.Games.GameDTO;
import com.google.cloud.Timestamp;

import java.time.Instant;

public class PlayerMapper {

    public static PlayerDTO toDTO(Player firestoreGame) {
        return PlayerDTO.builder()
                .id(firestoreGame.getId())
                .email(firestoreGame.getEmail())
                .userId(firestoreGame.getUserId())
                .username(firestoreGame.getUsername())
                .build();
    }

    public static Player toFirestore(PlayerDTO dto) {
        return Player.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .userId(dto.getUserId())
                .build();
    }

    private static Instant toInstant(Timestamp firestoreTs) {
        return firestoreTs == null ? null : firestoreTs.toSqlTimestamp().toInstant();
    }

    private static Timestamp fromInstant(Instant instant) {
        return instant == null ? null :
                Timestamp.ofTimeSecondsAndNanos(instant.getEpochSecond(), instant.getNano());
    }

}
