package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import enums.Option;

@Entity
@Table (name = "players")
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

  public Player() {
    super();
  }

  public Player(String name, String type, String image ) {
    this.name = name;
    this.type = type;
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
