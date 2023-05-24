package com.alexcarneiro.roshambo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.alexcarneiro.roshambo.dtos.GameTurnDTO;
import com.alexcarneiro.roshambo.entities.Player;
import com.alexcarneiro.roshambo.enums.Option;
import com.alexcarneiro.roshambo.enums.Outcome;
import com.alexcarneiro.roshambo.enums.PlayerType;
import com.alexcarneiro.roshambo.factories.PlayerFactory;
import com.alexcarneiro.roshambo.repositories.PlayerRepository;

public class GameServiceTest {
  
  @Mock
  private PlayerRepository playerRepository;
  
  @InjectMocks
  private GameService gameService;
  
  @BeforeEach
  public void beforeEach() {
    MockitoAnnotations.openMocks(this);
  }
  
  @Test
  public void getOptions() {
    Option[] options = gameService.getOptions();
    assertEquals(Option.values().length, options.length);
  }

  @Test
  public void resetGame() {
    gameService.resetGame();

    verify(playerRepository, times(1)).resetPlayersHealth();
  }

  @Nested
  public class GetPlayersTests {
    List<Player> players;

    @BeforeEach
    public void beforeEach() {
      Player player = PlayerFactory.createPlayer();
      Player computer = PlayerFactory.createComputerPlayer();
      players = List.of(player, computer);
    }

    @Test
    public void getPlayers() {      
      when(playerRepository.existsByHealthEquals(0)).thenReturn(false);
      when(playerRepository.count()).thenReturn(2L);
      when(playerRepository.findAll()).thenReturn(players);
      
      Iterable<Player> result = gameService.getPlayers();

      assertEquals(players, result);
      
      verify(playerRepository, times(1)).existsByHealthEquals(0);
      verify(playerRepository, times(1)).count();
      verify(playerRepository, times(1)).findAll();
      verify(playerRepository, never()).resetPlayersHealth();
    }

    @Test
    public void getPlayersWithHealthReset() {    
      when(playerRepository.existsByHealthEquals(0)).thenReturn(true);
      when(playerRepository.count()).thenReturn(2L);
      when(playerRepository.findAll()).thenReturn(players);
      
      Iterable<Player> result = gameService.getPlayers();

      assertEquals(players, result);
      
      verify(playerRepository, times(1)).existsByHealthEquals(0);
      verify(playerRepository, times(1)).count();
      verify(playerRepository, times(1)).findAll();
      verify(playerRepository, times(1)).resetPlayersHealth();
    }

    @Test
    public void getPlayersWithCreatePlayers() {    
      when(playerRepository.existsByHealthEquals(0)).thenReturn(false);
      when(playerRepository.count()).thenReturn(0L);
      when(playerRepository.saveAll(ArgumentMatchers.anyIterable())).thenReturn(players);
      
      Iterable<Player> result = gameService.getPlayers();

      assertEquals(players, result);
      
      verify(playerRepository, times(1)).existsByHealthEquals(0);
      verify(playerRepository, times(1)).count();
      verify(playerRepository, times(1)).saveAll(ArgumentMatchers.anyIterable());
      verify(playerRepository, times(1)).deleteAll();
      verify(playerRepository, never()).findAll();
      verify(playerRepository, never()).resetPlayersHealth();
    }
  }

  @Nested
  public class ProcessTests {
    Player player;
    Player computer;
    GameTurnDTO gameTurn;
    Map<String, Object> result;

    @BeforeEach
    public void beforeEach() {
      player = PlayerFactory.createPlayer();
      computer = PlayerFactory.createComputerPlayer();
    }

    @Test
    public void processOutcomeAsWin() {
      gameTurn = Mockito.spy(new GameTurnDTO(Option.ROCK.toString()));

      doReturn(Outcome.WIN).when(gameTurn).getOutcome();
      doReturn(25).when(gameTurn).generateDamage();
      when(playerRepository.findByType(PlayerType.COMPUTER)).thenReturn(computer);
      when(playerRepository.findAll()).thenReturn(List.of(player, computer));
      
      result = gameService.process(gameTurn);
      assertNotNull(result);
      assertTrue(result.containsKey("computerChoice"));
      assertTrue(result.get("message").toString().contains("WIN"));
      assertEquals(List.of(player, computer), result.get("players"));
      assertEquals(false, result.get("gameOver"));
    }
    
    @Test
    public void processOutcomeAsLose() {
      gameTurn = Mockito.spy(new GameTurnDTO(Option.ROCK.toString()));

      doReturn(Outcome.LOSE).when(gameTurn).getOutcome();
      doReturn(25).when(gameTurn).generateDamage();
      when(playerRepository.findByType(PlayerType.PLAYER)).thenReturn(player);
      when(playerRepository.findAll()).thenReturn(List.of(player, computer));
      
      result = gameService.process(gameTurn);
      assertNotNull(result);
      assertTrue(result.containsKey("computerChoice"));
      assertTrue(result.get("message").toString().contains("LOST"));
      assertEquals(List.of(player, computer), result.get("players"));
      assertEquals(false, result.get("gameOver"));
    }

    @Test
    public void processOutcomeAsDraw() {
      gameTurn = Mockito.spy(new GameTurnDTO(Option.ROCK.toString()));

      doReturn(Outcome.DRAW).when(gameTurn).getOutcome();
      doReturn(0).when(gameTurn).generateDamage();
      when(playerRepository.findAll()).thenReturn(List.of(player, computer));
      
      result = gameService.process(gameTurn);
      assertNotNull(result);
      assertTrue(result.containsKey("computerChoice"));
      assertTrue(result.get("message").toString().contains("DRAW"));
      assertEquals(List.of(player, computer), result.get("players"));
      assertEquals(false, result.get("gameOver"));
    }

    @Test
    public void processGameIsOver() {
      gameTurn = Mockito.spy(new GameTurnDTO(Option.ROCK.toString()));

      doReturn(Outcome.WIN).when(gameTurn).getOutcome();
      doReturn(100).when(gameTurn).generateDamage();
      when(playerRepository.findByType(PlayerType.COMPUTER)).thenReturn(computer);
      computer.setHealth(0);
      when(playerRepository.findAll()).thenReturn(List.of(player, computer));
      
      result = gameService.process(gameTurn);
      assertNotNull(result);
      assertTrue(result.containsKey("computerChoice"));
      assertTrue(result.get("message").toString().contains("WIN"));
      assertEquals(List.of(player, computer), result.get("players"));
      assertEquals(true, result.get("gameOver"));
    }
  }
}
