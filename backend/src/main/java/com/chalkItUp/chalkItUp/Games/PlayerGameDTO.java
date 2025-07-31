package com.chalkItUp.chalkItUp.Games;

import com.chalkItUp.chalkItUp.Player.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerGameDTO {
    public Player player;
    public int team;
    public boolean winner;
}
