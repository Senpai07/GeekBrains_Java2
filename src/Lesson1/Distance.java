package Lesson1;

/** The type Distance. */
public class Distance implements Obstacle {
  public static final int LENGTH = 200;
  private int length;

  /**
   * Instantiates a new Distance.
   *
   * @param length the length
   */
  public Distance(int length) {
    this.length = length;
  }

  public Distance() {
    this(LENGTH);
  }

  @Override
  public void passing(Competitor competitor) {
    if (competitor.run() < length) {
      competitor.setCompetition(false);
      System.out.println(competitor.getName() + " НЕ пробежал!");
    } else {
      System.out.println(competitor.getName() + " пробежал!");
    }
  }
}
