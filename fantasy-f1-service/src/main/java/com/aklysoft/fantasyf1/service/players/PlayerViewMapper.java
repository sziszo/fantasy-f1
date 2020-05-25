package com.aklysoft.fantasyf1.service.players;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerViewMapper {

  static final Player UNKNOWN_PLAYER = Player.builder().userName("unknown").points(-1L).build();
  static final PlayerView UNKNOWN_PLAYER_VIEW = new PlayerView("unknown", "Unknown player", -1L);

  public static PlayerView toPlayerView(Player player) {
    return toPlayerViews(Stream.of(player))
            .findFirst()
            .orElse(UNKNOWN_PLAYER_VIEW);
  }

  public static List<PlayerView> toPlayerViews(List<Player> players) {
    return toPlayerViews(players.stream())
            .collect(Collectors.toList());
  }

  public static List<PlayerView> toPlayerViews(Player... players) {
    return toPlayerViews(Stream.of(players))
            .collect(Collectors.toList());
  }

  public static Stream<PlayerView> toPlayerViews(Stream<Player> players) {
    return players
            .map(player -> Optional.ofNullable(player).orElse(UNKNOWN_PLAYER))
            .map(player ->
                    PlayerView
                            .builder()
                            .username(player.getUserName())
                            .fullName(player.getFullName())
                            .points(player.getPoints())
                            .build()
            );
  }

}
