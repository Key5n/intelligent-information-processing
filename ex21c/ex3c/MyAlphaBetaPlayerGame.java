package ex3c;

import ex3b.*;

public class MyAlphaBetaPlayerGame extends Game {
  int numOfCutting = 0;
  int visited = 0;

  public static void main(String[] args) {
    var p0 = new MyAlphaBetaPlayer(new Eval(), 20);
    var p1 = new RandomPlayer();
    // p0が黒(先手)でp1が白(後手)
    MyAlphaBetaPlayerGame g = new MyAlphaBetaPlayerGame(p0, p1);
    p0.game = g;
    long startTime = System.currentTimeMillis();
    // 対戦
    g.play();
    long finishTime = System.currentTimeMillis();
    // 結果の表示
    g.printResult();
    System.out.printf("Time passed: %.3f(s)\n", (float) (finishTime - startTime) / 1_000);
  }

  public MyAlphaBetaPlayerGame(Player black, Player white) {
    super(black, white);
  }

  // 結果の表示
  @Override
  public void printResult() {
    System.out.println("winner: " + this.players.get(this.state.winner()));
    System.out.printf("Cuts happened: %d\n", numOfCutting);
    System.out.printf("Visited: %d\n", visited);
  }
}
