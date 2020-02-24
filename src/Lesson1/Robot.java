package Lesson1;

public class Robot implements Competitor {
  public static final int RUNNING = 400;
  public static final int JUMPING = 80;
  public static final String UNKNOWN_ROBOT = "Unknown robot";
  private final String name;
  private final int running;
  private final int jumping;
  private boolean competitor = true;

  public Robot(String name, int running, int jumping) {
    this.name = name;
    this.running = running;
    this.jumping = jumping;
  }

  public Robot() {
    this(UNKNOWN_ROBOT, RUNNING, JUMPING);
  }

  @Override
  public int run() {
    System.out.print("Робот " + name + " побежал. ");
    return running;
  }

  @Override
  public int jump() {
    System.out.print("Робот " + name + " прыгнул. ");
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
