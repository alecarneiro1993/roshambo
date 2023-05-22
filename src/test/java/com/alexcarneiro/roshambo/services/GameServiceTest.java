package com.alexcarneiro.roshambo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentMatchers;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.*;
import org.mockito.*;

import com.alexcarneiro.roshambo.dtos.*;
import com.alexcarneiro.roshambo.entities.*;
import com.alexcarneiro.roshambo.enums.*;
import com.alexcarneiro.roshambo.repositories.*;
import com.alexcarneiro.roshambo.factories.PlayerFactory;

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
      when(playerRepository.findByType(PlayerType.COMPUTER.getValue())).thenReturn(computer);
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
      when(playerRepository.findByType(PlayerType.PLAYER.getValue())).thenReturn(player);
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
      when(playerRepository.findByType(PlayerType.COMPUTER.getValue())).thenReturn(computer);
      computer.setHealth(0);
      when(playerRepository.findAll()).thenReturn(List.of(player, computer));
      
      result = gameService.process(gameTurn);
      System.out.println(result);
      assertNotNull(result);
      assertTrue(result.containsKey("computerChoice"));
      assertTrue(result.get("message").toString().contains("WIN"));
      assertEquals(List.of(player, computer), result.get("players"));
      assertEquals(true, result.get("gameOver"));
    }
  }
}
