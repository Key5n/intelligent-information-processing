package ex3b;

import static ex3b.State.*;

import java.util.*;

public class Game {
  public static void main(String[] args) {
    var p1 = new MinMaxPlayer(new Eval(), 20);
    var p0 = new RandomPlayer();
    // p0が黒(先手)でp1が白(後手)
    Game g = new Game(p0, p1);
    // 対戦
    g.play();
    // 結果の表示
    g.printResult();
  }

  protected State state;
  protected Map<Integer, Player> players;

  public Game(Player black, Player white) {
    this.state = new State(5);
    black.color = BLACK;
    white.color = WHITE;
    this.players = Map.of(BLACK, black, WHITE, white, NONE, new Player("draw"));
  }

  public void play() {
    while (true) {
      System.out.println(this.state);
      // 色に対応したプレイヤーを取得(Stateの初期値は黒なので初手は自分)
      var player = this.players.get(this.state.color);
      // 行動する
      var move = player.think(this.state.clone());
      System.out.println(move);
      // 行動後に状態を更新する
      this.state = this.state.perform(move);
      System.out.println("--------------------");
      if (this.state.isGoal())
        break;
    }
  }

  // 結果の表示
  public void printResult() {
    System.out.println(this.state);
    System.out.println("winner: " + this.players.get(this.state.winner()));
  }
}
