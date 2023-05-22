package com.alexcarneiro.roshambo.controllers;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



import com.alexcarneiro.roshambo.RoshamboApplication;
import com.alexcarneiro.roshambo.dtos.GameTurnDTO;
import com.alexcarneiro.roshambo.services.GameService;
import com.alexcarneiro.roshambo.enums.Option;
import com.alexcarneiro.roshambo.entities.Player;
import com.alexcarneiro.roshambo.factories.PlayerFactory;

@SpringBootTest(classes = RoshamboApplication.class)
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    private Player player;
    private Player computer;

    @BeforeEach
    public void setup() {
        player = PlayerFactory.createPlayer();
        computer = PlayerFactory.createComputerPlayer();
    }

    @Test
    public void getOptions() throws Exception {
      Mockito.when(gameService.getOptions()).thenReturn(Option.values());

      mockMvc.perform(MockMvcRequestBuilders.get("/api/game/options"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.options", contains("ROCK", "PAPER", "SCISSOR")));
    }

    @Test
    public void getPlayers() throws Exception {
      Mockito.when(gameService.getPlayers()).thenReturn(Arrays.asList(player, computer));

      mockMvc.perform(MockMvcRequestBuilders.get("/api/game/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.players").exists());
    }
    
    @Test
    public void resolveGameTurn() throws Exception {
      String payload = "{\"playerChoice\":\"ROCK\"}";
      ArgumentCaptor<GameTurnDTO> gameTurnCaptor = ArgumentCaptor.forClass(GameTurnDTO.class);
      
      Map<String, Object> mockResult = new HashMap<>();
      mockResult.put("message", "message");
      mockResult.put("computerChoice", "SCISSOR");
      mockResult.put("players", Arrays.asList(player, computer));
      mockResult.put("gameOver", false);

      Mockito.when(gameService.process(gameTurnCaptor.capture())).thenReturn(mockResult);

      mockMvc.perform(MockMvcRequestBuilders.post("/api/game/resolve")
              .contentType(MediaType.APPLICATION_JSON)
              .content(payload))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.data.message").exists())
              .andExpect(jsonPath("$.data.computerChoice").exists())
              .andExpect(jsonPath("$.data.players").exists())
              .andExpect(jsonPath("$.data.gameOver").exists());
    }
    
    @Test
    public void resetGame() throws Exception {
      Mockito.when(gameService.getPlayers()).thenReturn(Arrays.asList(player, computer));
      mockMvc.perform(MockMvcRequestBuilders.post("/api/game/reset"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.data.players").exists());
    }
}
