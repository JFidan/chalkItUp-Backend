package com.chalkItUp.chalkItUp.Player;

import com.chalkItUp.chalkItUp.Games.Game;
import com.chalkItUp.chalkItUp.Games.GameDTO;
import com.google.cloud.Timestamp;

import java.time.Instant;
import java.util.List;

public class PlayerMapper {

    public static PlayerDTO toDTO(Player player, int winsCount, int lossesCount, List<Boolean> lastWins, int wonBy8Ball, int lostBy8Ball) {
        if (player == null) {
            return null;
        }

        int totalGames = winsCount + lossesCount;
        double winRate =  totalGames > 0 ? Math.round(((double) winsCount / totalGames) * 100.0) / 100.0  : 0.0;

        return PlayerDTO.builder()
                .id(player.getId())
                .userId(player.getUserId())
                .username(player.getUsername())
                .email(player.getEmail())
                .winsCount(winsCount)
                .lossesCount(lossesCount)
                .winRate(winRate)
                .lastWins(lastWins)
                .wonBy8Ball(wonBy8Ball)
                .lostBy8Ball(lostBy8Ball)
                .build();
    }

    public static PlayerDTO toDTO(Player player) {
        if (player == null) {
            return null;
        }
        return PlayerDTO.builder()
                .id(player.getId())
                .userId(player.getUserId())
                .username(player.getUsername())
                .email(player.getEmail())
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
