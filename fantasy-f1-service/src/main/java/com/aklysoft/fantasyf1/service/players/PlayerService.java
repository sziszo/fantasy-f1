package com.aklysoft.fantasyf1.service.players;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PlayerService {

  private final PlayerRepository playerRepository;

  public PlayerService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }


  public List<Player> getAllPlayers() {
    return playerRepository.findAll();
  }

  @Transactional
  public Player create(NewPlayer newPlayer) {
    Player player = playerRepository.findById(newPlayer.getUserName());
    if (player != null) {
      throw new PlayerException("Player '" + newPlayer.getUserName() + "' already exists.");
    }

    this.playerRepository.persist(
            Player.builder()
                    .userName(newPlayer.getUserName())
                    .firstName(newPlayer.getFirstName())
                    .lastName(newPlayer.getLastName())
                    .created(LocalDateTime.now())
                    .build()
    );
    return this.playerRepository.findById(newPlayer.getUserName());
  }

  @Transactional
  public void delete(String username) {
    final Player player = this.playerRepository.findById(username);
    if (player == null) {
      throw new PlayerException("Player '" + username + "' does not exist!");
    }
    this.playerRepository.delete(player);
  }

  public Player getPlayer(String username) {
    final Player player = this.playerRepository.findById(username);
    if (player == null) {
      throw new PlayerException("Player '" + username + "' does not exist!");
    }
    return player;
  }
}
