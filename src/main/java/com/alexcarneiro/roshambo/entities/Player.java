package entities;

import jakarta.persistence.*;

import enums.Option;

@Entity
@Table (name = "players")
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "type")
  private String type;

  @Column(name = "image")
  private String image;

  @Column(name = "health")
  private int health;

  public Player() {
    super();
  }

  public Player(String name, String type, String image ) {
    this.name = name;
    this.type = type;
    this.image = String.format("../../assets/images/%s", image);
    this.health = 100;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void takeDamage(int damage) {
    if (this.health <= 0) {
      return;
    }

    int currentHealth = this.health - damage;
    this.health = currentHealth <= 0 ? 0 : currentHealth;
  }
}
