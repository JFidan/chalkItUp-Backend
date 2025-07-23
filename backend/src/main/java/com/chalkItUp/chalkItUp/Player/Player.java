package com.chalkItUp.chalkItUp.Player;

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
public class Player {

    @DocumentId
    private String id;
    private String firstName;
    private String lastName;

    @ServerTimestamp
    private Timestamp createdAt;
}
