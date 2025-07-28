package com.chalkItUp.chalkItUp.Games;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDTO {

    private String id;
    private List<PlayerGame> players;
    private Instant endTime;
}

