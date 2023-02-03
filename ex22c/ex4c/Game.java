package ex4c;

import ex4a.*;

import static ex4a.State.*;
import java.util.*;

public class Game {

  // winRate[0]: プレイヤーA(RandomPlayer)
  // winRate[1]: プレイヤーB(AlphaBetaPlayer) 深さ制限2
  // winRate[2]: プレイヤーC(AlphaBetaPlayer) 深さ制限4
  // winRate[3]: プレイヤーD(AlphaBetaPlayer) 深さ制限6
  // winRate[4]: Draw
  static int[] winRate = { 0, 0, 0, 0, 0 };

  public static void main(String[] args) {

    var pa = new RandomPlayer();
    var pb = new AlphaBetaPlayer(new Eval(), 2);
    var pc = new AlphaBetaPlayer(new Eval(), 4);
    var pd = new AlphaBetaPlayer(new Eval(), 6);

    calc(pa, pb, 0, 1);
    calc(pa, pc, 0, 2);
    calc(pa, pd, 0, 3);
    calc(pb, pc, 1, 2);
    calc(pb, pd, 1, 3);
    calc(pc, pd, 2, 3);

    System.out.printf("Win Rate of Random Player: %d\n", winRate[0]);
    System.out.printf("Win Rate of AlphaBetaPlayer(depth limit: 2): %d\n", winRate[1]);
    System.out.printf("Win Rate of AlphaBetaPlayer(depth limit: 4): %d\n", winRate[2]);
    System.out.printf("Win Rate of AlphaBetaPlayer(depth limit: 6): %d\n", winRate[3]);
    System.out.printf("Draw Rate: %d\n", winRate[4]);

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
      // System.out.println(this.state);
      // 状態の色を元にプレイヤーの取得
      var player = this.players.get(this.state.color);
      // 次に打つ手の取得
      var move = player.think(this.state.clone());
      // System.out.println(move);
      // 状態を元に手を打つ
      this.state = this.state.perform(move);
      // System.out.println("--------------------");
      if (this.state.isGoal()) {
        break;
      }
    }
  }

  // 結果の表示
  void printResult() {
    System.out.println(this.state);
    System.out.println("winner: " + this.players.get(this.state.winner()));
  }

  static void calc(Player black, Player white, int blackID, int whiteID) {
    // pa(黒)が先手、pb(白)が後手
    for (int i = 0; i < 100; i++) {
      var g = new Game(black, white);
      // 対戦
      g.play();
      int winner = g.state.winner();
      // 結果の表示
      if (g.state.winner() == 1) {
        winRate[blackID]++;
      } else if (winner == -1) {
        winRate[whiteID]++;
      } else if (winner == 0) {
        winRate[4]++;
      }
    }
    for (int i = 0; i < 100; i++) {
      var g = new Game(white, black);
      // 対戦
      g.play();
      int winner = g.state.winner();
      // 結果の表示
      if (g.state.winner() == 1) {
        winRate[whiteID]++;
      } else if (winner == -1) {
        winRate[blackID]++;
      } else if (winner == 0) {
        winRate[4]++;
      }
    }
  }
}