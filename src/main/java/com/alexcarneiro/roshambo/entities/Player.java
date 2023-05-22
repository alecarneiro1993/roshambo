package com.alexcarneiro.roshambo.entities;

import com.alexcarneiro.roshambo.enums.PlayerType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity that represents a Player in the game
 */
@Entity
@Table (name = "players")
@NoArgsConstructor
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  @Getter @Setter private String name;

  @Column(name = "type")
  @Getter @Setter private String type;

  @Column(name = "image")
  @Getter @Setter private String image;

  @Column(name = "health")
  @Getter @Setter private int health;

  public Player(String name, PlayerType type, String image ) {
    this.name = name;
    this.type = type.getValue();
    this.image = String.format("../../assets/images/%s", image);
    this.health = 100;
  }

  /**
   * Function that reduces the player's health
   * by the given amount.
   * 
   * If the amount is greater than the player's health,
   * then it sets it to 0 instead of a negative number
   * 
   * @param damage - integer that represents the amount of damage
   */
  public void takeDamage(int damage) {
    if (this.health <= 0) {
      return;
    }

    int currentHealth = this.health - damage;
    setHealth(currentHealth <= 0 ? 0 : currentHealth);
  }

  /**
   * Function that determines whether or not
   * a player is still able to play the game.
   * 
   * If the player has no more health, then
   * the last one standing is the winner.
   *
   * @return Boolean
   */
  public Boolean isKnockedOut() {
    return this.health == 0;
  }
}
