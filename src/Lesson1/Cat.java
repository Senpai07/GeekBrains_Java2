package Lesson1;

public class Cat implements Competitor {
  public static final int JUMPING = 40;
  public static final int RUNNING = 200;
  public static final String UNKNOWN_CAT = "Unknown cat";
  private final String name;
  private final int running;
  private final int jumping;
  private boolean competitor = true;

  public Cat(String name, int running, int jumping) {
    this.name = name;
    this.running = running;
    this.jumping = jumping;
  }

  public Cat() {
    this(UNKNOWN_CAT, RUNNING, JUMPING);
  }

  @Override
  public int run() {
    System.out.print("Кот " + name + " побежал. ");
    return running;
  }

  @Override
  public int jump() {
    System.out.print ("Кот " + name + " прыгнул. ");
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
