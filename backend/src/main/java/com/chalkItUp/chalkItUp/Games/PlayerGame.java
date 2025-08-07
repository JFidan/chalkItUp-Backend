package com.chalkItUp.chalkItUp.Games;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerGame {
    public String userId;
    public int team;
    public boolean winner;
    public String endState;
}
