package models;

public class Player {
  private String name;
  private String type;
  private String image;
  private Integer health;

  public static enum Options {
    ROCK,
    PAPER,
    SCISSOR
  }

  public Player(String name, String type, String image ) {
    this.name = name;
    this.type = type;
    this.image = String.format("../../assets/images/%s", image);
    this.health = 100;
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

  public Integer getHealth() {
    return health;
  }

  public void setHealth(Integer health) {
    this.health = health;
  }  
}
