package Lesson1;

import java.util.ArrayList;
import java.util.List;

public class Competition {
  public static void main(String[] args) {
    List<Obstacle> obstacleList = new ArrayList<>();
    List<Competitor> competitorList = new ArrayList<>();

    obstacleList.add(new Wall());
    obstacleList.add(new Distance());
    obstacleList.add(new Wall(10));
    obstacleList.add(new Distance(300));
    obstacleList.add(new Wall(30));
    obstacleList.add(new Distance(500));
    obstacleList.add(new Wall(70));
    obstacleList.add(new Distance(1000));

    competitorList.add(new Cat());
    competitorList.add(new Human());
    competitorList.add(new Robot());
    competitorList.add(new Cat("Мурзик",600,50));
    competitorList.add(new Human("Петр",300,10));
    competitorList.add(new Robot("Р2Д2",1000,100));
    competitorList.add(new Cat("Барсик",500,60));
    competitorList.add(new Human("Иван",400,30));
    competitorList.add(new Robot("Вертер",700,200));

    for (Obstacle obstacle : obstacleList) {
      for (Competitor competitor : competitorList) {
        if (competitor.getCompetition()) {
          obstacle.passing(competitor);
        }
      }
    }
  }
}
