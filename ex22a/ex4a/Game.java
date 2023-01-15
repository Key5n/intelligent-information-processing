package ex4a;

import static ex4a.State.*;
import java.util.*;

public class Game {
  public static void main(String[] args) {
    var p0 = new RandomPlayer();
    var p1 = new MinMaxPlayer(new Eval(), 2);
    var g = new Game(p0, p1);
    g.play();
    g.printResult();
  }

  State state;
  Map<Integer, Player> players = new HashMap<>();

  public Game(Player black, Player white) {
    this.state = new State();
    black.color = BLACK;
    white.color = WHITE;
    this.players = Map.of(BLACK, black, WHITE, white, NONE, new Player("draw"));
  }

  void play() {
    while (true) {
      System.out.println(this.state);
      var player = this.players.get(this.state.color);
      var move = player.think(this.state.clone());
      System.out.println(move);
      this.state = this.state.perform(move);
      System.out.println("--------------------");
      if (this.state.isGoal())
        break;
    }
  }

  void printResult() {
    System.out.println(this.state);
    System.out.println("winner: " + this.players.get(this.state.winner()));
  }
}