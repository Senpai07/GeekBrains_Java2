package Lesson1;

/** The type Wall. */
public class Wall implements Obstacle {
  public static final int HEIGHT = 5;
  private int height;

  /**
   * Instantiates a new Wall.
   *
   * @param height the height
   */
  public Wall(int height) {
    this.height = height;
  }

  public Wall() {
    this(HEIGHT);
  }

  @Override
  public void Passing(Competitor competitor) {
    if (competitor.jump() < height) {
      competitor.setCompetition(false);
      System.out.println(competitor.getName() + " НЕ перепрыгнул!");
    } else {
      System.out.println(competitor.getName() + " перепрыгнул!");
    }
  }
}
