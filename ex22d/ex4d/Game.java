package ex4d;

import ex4a.*;
import static ex4a.State.*;
import java.util.*;

public class Game {
  public static void main(String[] args) {
    var p0 = new RandomPlayer();
    var p1 = new HumanPlayer(new Eval());
    // p0(黒)が先手、p1(白)が後手
    var g = new Game(p0, p1);
    // 対戦
    g.play();
    // 結果の表示
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
      // 状態の色を元にプレイヤーの取得
      var player = this.players.get(this.state.color);
      // 次に打つ手の取得
      var move = player.think(this.state.clone());
      System.out.println(move);
      // 状態を元に手を打つ
      this.state = this.state.perform(move);
      System.out.println("--------------------");
      if (this.state.isGoal())
        break;
    }
  }

  // 結果の表示
  void printResult() {
    System.out.println(this.state);
    System.out.println("winner: " + this.players.get(this.state.winner()));
  }
}