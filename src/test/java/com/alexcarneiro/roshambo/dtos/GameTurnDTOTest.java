package com.alexcarneiro.roshambo.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.alexcarneiro.roshambo.enums.Outcome;
import com.alexcarneiro.roshambo.enums.Option;

public class GameTurnDTOTest {

  public class ConstructorTests {
    @Test
    public void constructor() {
      Option option = Option.ROCK;
      GameTurnDTO gameTurn = new GameTurnDTO(option.toString());
  
      assertEquals(option.getValue(), gameTurn.getPlayerValue());
    }
    
    @Test
    public void constructorInvalid() {
      GameTurnDTO gameTurn = new GameTurnDTO("INVALID");
  
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
        gameTurn.setPlayerValue(Option.ROCK.getValue());
        gameTurn.setComputerValue(Option.SCISSOR.getValue());
  
        gameTurn.processOutcome();
  
        assertEquals(Outcome.WIN, gameTurn.getOutcome());
    }
    
    @Test
    public void processOutcomeLose() {
        gameTurn.setPlayerValue(Option.ROCK.getValue());
        gameTurn.setComputerValue(Option.PAPER.getValue());
  
        gameTurn.processOutcome();
  
        assertEquals(Outcome.LOSE, gameTurn.getOutcome());
    }
    
    @Test
    public void processOutcomeDraw() {
        gameTurn.setPlayerValue(Option.ROCK.getValue());
        gameTurn.setComputerValue(Option.ROCK.getValue());
  
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
