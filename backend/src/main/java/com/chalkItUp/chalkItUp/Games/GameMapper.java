package com.chalkItUp.chalkItUp.Games;

import com.google.cloud.Timestamp;

import java.time.Instant;

public class GameMapper {
    public static GameDTO toDTO(Game firestoreGame) {
        return GameDTO.builder()
                .id(firestoreGame.getId())
                .players(firestoreGame.getPlayers())
                .endTime(toInstant(firestoreGame.getEndTime()))
                .build();
    }

    public static Game toFirestore(GameDTO dto) {
        return Game.builder()
                .id(dto.getId())
                .players(dto.getPlayers())
                .endTime(fromInstant(dto.getEndTime()))
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
