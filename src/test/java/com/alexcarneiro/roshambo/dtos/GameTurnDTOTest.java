package com.alexcarneiro.roshambo.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alexcarneiro.roshambo.enums.Option;
import com.alexcarneiro.roshambo.enums.Outcome;

public class GameTurnDTOTest {

  public class ConstructorTests {
    @Test
    public void constructor() {
      Option option = Option.ROCK;
      GameTurnDTO gameTurn = new GameTurnDTO(option.toString());
  
      assertEquals(option, gameTurn.getPlayerChoice());
    }
    
    @Test
    public void constructorInvalid() {
      assertThrows(IllegalArgumentException.class, () -> {
            new GameTurnDTO("INVALID");
        });
    }
  }

  public class ProcessOutcomeTests {

    GameTurnDTO gameTurn;

    @BeforeEach
    public void beforeEach() {
      gameTurn = new GameTurnDTO();
    }
    
    @Test
    public void processOutcomeWin() {
        gameTurn.setPlayerChoice(Option.ROCK);
        gameTurn.setComputerChoice(Option.SCISSOR);
  
        gameTurn.processOutcome();
  
        assertEquals(Outcome.WIN, gameTurn.getOutcome());
    }
    
    @Test
    public void processOutcomeLose() {
        gameTurn.setPlayerChoice(Option.ROCK);
        gameTurn.setComputerChoice(Option.PAPER);
  
        gameTurn.processOutcome();
  
        assertEquals(Outcome.LOSE, gameTurn.getOutcome());
    }
    
    @Test
    public void processOutcomeDraw() {
        gameTurn.setPlayerChoice(Option.ROCK);
        gameTurn.setComputerChoice(Option.ROCK);
  
        gameTurn.processOutcome();
  
        assertEquals(Outcome.DRAW, gameTurn.getOutcome());
    }
  }

  public class GenerateDamageTests {
    GameTurnDTO gameTurn;

    @BeforeEach
    public void beforeEach() {
      gameTurn = new GameTurnDTO();
    }

    @Test
    public void generateDamageOnWin() {
        gameTurn.setOutcome(Outcome.WIN);
  
        int damage = gameTurn.generateDamage();
  
        assertTrue(damage != 0);
    }
    
    @Test
    public void generateDamageOnLose() {
        gameTurn.setOutcome(Outcome.LOSE);
  
        int damage = gameTurn.generateDamage();
  
        assertTrue(damage != 0);
    }

    @Test
    public void generateDamageOnDraw() {
        gameTurn.setOutcome(Outcome.DRAW);
  
        int damage = gameTurn.generateDamage();
  
        assertTrue(damage == 0);
    }
  }
}
