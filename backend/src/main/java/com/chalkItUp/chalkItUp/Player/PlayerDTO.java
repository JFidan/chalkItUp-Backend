package com.chalkItUp.chalkItUp.Player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDTO {
    private String id;
    private String userId;
    private String username;
    private String email;
    private int winsCount;
    private int lossesCount;
    private double winRate;
}
