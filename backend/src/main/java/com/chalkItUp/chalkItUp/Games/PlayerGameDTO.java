package com.chalkItUp.chalkItUp.Games;

import com.chalkItUp.chalkItUp.Player.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerGameDTO {
    public PlayerDTO player;
    public int team;
    public boolean winner;
}
