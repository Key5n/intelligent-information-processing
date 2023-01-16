package ex3e;

import ex3b.*;

public class MyAlphaBetaGameParser extends Game {
  // public int numOfCutting = 0;
  // public int visited = 0;

  public static void main(String[] args) {
    for (int i = 6; i <= 6; i++) {

      var p0 = new MyAlphaBetaPlayer(new Eval(), 20);
      var p1 = new MyAlphaBetaPlayer(new Eval(), 20);
      // p0が黒(先手)でp1が白(後手)
      MyAlphaBetaGameParser g = new MyAlphaBetaGameParser(p0, p1, i);
      // p0.game = g;
      // long startTime = System.currentTimeMillis();
      // 対戦
      g.play();
      // long finishTime = System.currentTimeMillis();
      // 結果の表示
      g.printResult();
      // System.out.printf("Time passed: %.3f(s)\n", (float) (finishTime - startTime)
      // / 1_000);
    }
  }

  public MyAlphaBetaGameParser(Player black, Player white, int i) {
    super(black, white);
    this.state = new State(i);
  }

  // 結果の表示
  @Override
  public void printResult() {
    System.out.println("winner: " + this.players.get(this.state.winner()));
    // System.out.printf("Cuts happened: %d\n", numOfCutting);
    // System.out.printf("Visited: %d\n", visited);
  }
}
