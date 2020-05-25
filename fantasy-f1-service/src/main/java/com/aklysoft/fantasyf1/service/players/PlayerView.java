package com.aklysoft.fantasyf1.service.players;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class PlayerView {
  private String username;
  private String fullName;
  private Long points;
}
