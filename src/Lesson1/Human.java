package Lesson1;

public class Human implements Competitor {
  public static final int JUMPING = 20;
  public static final int RUNNING = 100;
  public static final String UNKNOWN_HUMAN = "Unknown human";
  private final String name;
  private final int running;
  private final int jumping;
  private boolean competitor = true;

  public Human(String name, int running, int jumping) {
    this.name = name;
    this.running = running;
    this.jumping = jumping;
  }

  public Human() {
    this(UNKNOWN_HUMAN, RUNNING, JUMPING);
  }

  @Override
  public int run() {
    System.out.print("Человек " + name + " побежал. ");
    return running;
  }

  @Override
  public int jump() {
    System.out.print("Человек " + name + " прыгнул. ");
    return jumping;
  }

  @Override
  public void setCompetition(boolean competition) {
    this.competitor = competition;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean getCompetition() {
    return this.competitor;
  }
}
