package com.chalkItUp.chalkItUp.Games;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @DocumentId
    private String id;
    private String winner;
    private String loser;

    @ServerTimestamp
    private Timestamp createdAt;
}