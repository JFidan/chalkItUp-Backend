package com.chalkItUp.chalkItUp.Player;

import com.chalkItUp.chalkItUp.Games.Game;
import com.chalkItUp.chalkItUp.Games.GameDTO;
import com.google.cloud.Timestamp;

import java.time.Instant;

public class PlayerMapper {

    public static PlayerDTO toDTO(Player player, int winsCount, int lossesCount) {
        if (player == null) {
            return null;
        }

        int totalGames = winsCount + lossesCount;
        double winRate = totalGames > 0 ? (double) winsCount / totalGames : 0.0;

        return PlayerDTO.builder()
                .id(player.getId())
                .userId(player.getUserId())
                .username(player.getUsername())
                .email(player.getEmail())
                .winsCount(winsCount)
                .lossesCount(lossesCount)
                .winRate(winRate)
                .build();
    }

    public static Player toEntity(PlayerDTO dto) {
        if (dto == null) {
            return null;
        }

        return Player.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .username(dto.getUsername())
                .email(dto.getEmail())
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
