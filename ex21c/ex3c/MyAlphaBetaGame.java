package ex3c;

import ex3b.*;

public class MyAlphaBetaGame extends Game {
  public int numOfCutting = 0;
  public int visited = 0;

  public static void main(String[] args) {
    long[] time = new long[17];

    for (int i = 4; i < 21; i++) {
      var p0 = new MyAlphaBetaPlayer(new Eval(), 20);
      // var p0 = new MinMaxPlayer(new Eval(), 20);
      var p1 = new RandomPlayer();
      // p0が黒(先手)でp1が白(後手)
      MyAlphaBetaGame g = new MyAlphaBetaGame(p0, p1, i);
      p0.game = g;
      long startTime = System.currentTimeMillis();
      // 対戦
      g.play();
      long finishTime = System.currentTimeMillis();
      // 結果の表示
      g.printResult();
      time[i - 4] = finishTime - startTime;
      // System.out.printf("Time passed: %.3f(s)\n", (float) (finishTime - startTime)
      // / 1_000);
    }
    for (int i = 4; i < 21; i++) {
      System.out.printf("The number of states: %2d, Processing Time %.3f(s)\n", i,
          (float) time[i - 4] / 1000);
    }
  }

  public MyAlphaBetaGame(Player black, Player white, int numOfStones) {
    super(black, white);
    this.state = new State(numOfStones);
  }

  // 結果の表示
  @Override
  public void printResult() {
    super.printResult();
    System.out.printf("Cuts happened: %d\n", this.numOfCutting);
    System.out.printf("Visited: %d\n", this.visited);
  }
}
