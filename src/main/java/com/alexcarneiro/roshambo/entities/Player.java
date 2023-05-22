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

  public void takeDamage(int damage) {
    if (this.health <= 0) {
      return;
    }

    int currentHealth = this.health - damage;
    setHealth(currentHealth <= 0 ? 0 : currentHealth);
  }
}
