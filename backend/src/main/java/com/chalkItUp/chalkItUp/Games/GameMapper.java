package com.chalkItUp.chalkItUp.Games;

import com.chalkItUp.chalkItUp.Player.Player;
import com.google.cloud.Timestamp;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameMapper {
    public static GameDTO toDTO(Game game, Map<String, Player> playerMap) {
        if (game == null) {
            return null;
        }

        List<PlayerGameDTO> playerGameDTOs = game.getPlayers().stream()
                .map(pg -> toDTO(pg, playerMap.get(pg.getUserId())))
                .collect(Collectors.toList());

        return GameDTO.builder()
                .id(game.getId())
                .endTime(game.getEndTime() != null ? toInstant(game.getEndTime()) : null)
                .players(playerGameDTOs)
                .build();
    }

    public static Game toEntity(GameDTO dto) {
        if (dto == null) {
            return null;
        }

        List<PlayerGame> playerGames = dto.getPlayers().stream()
                .map(pg -> toEntity(pg))
                .collect(Collectors.toList());

        return Game.builder()
                .id(dto.getId())
                .endTime(dto.getEndTime() != null ? Timestamp.ofTimeSecondsAndNanos(
                        dto.getEndTime().getEpochSecond(),
                        dto.getEndTime().getNano()
                ) : null)
                .players(playerGames)
                .build();
    }

    private static Instant toInstant(Timestamp firestoreTs) {
        return firestoreTs == null ? null : firestoreTs.toSqlTimestamp().toInstant();
    }

    private static Timestamp fromInstant(Instant instant) {
        return instant == null ? null :
                Timestamp.ofTimeSecondsAndNanos(instant.getEpochSecond(), instant.getNano());
    }

    public static PlayerGameDTO toDTO(PlayerGame playerGame, Player player) {
        if (playerGame == null || player == null) {
            return null;
        }

        return PlayerGameDTO.builder()
                .player(player)
                .team(playerGame.getTeam())
                .winner(playerGame.isWinner())
                .build();
    }

    public static PlayerGame toEntity(PlayerGameDTO dto) {
        if (dto == null || dto.getPlayer() == null) {
            return null;
        }

        return PlayerGame.builder()
                .userId(dto.getPlayer().getId()) // assuming Player has an `id` field
                .team(dto.getTeam())
                .winner(dto.isWinner())
                .build();
    }

}
