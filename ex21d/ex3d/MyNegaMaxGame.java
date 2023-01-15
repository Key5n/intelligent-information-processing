package ex3d;

import ex3b.*;
import ex3c.*;

public class MyNegaMaxGame extends MyAlphaBetaGame {

  public static void main(String[] args) {
    var p0 = new MyNegaMaxPlayer(new Eval(), 20);
    var p1 = new RandomPlayer();
    // p0が黒(先手)でp1が白(後手)
    MyNegaMaxGame g = new MyNegaMaxGame(p0, p1);
    p0.game = g;
    long startTime = System.currentTimeMillis();
    // 対戦
    g.play();
    long finishTime = System.currentTimeMillis();
    // 結果の表示
    g.printResult();
    System.out.printf("Time passed: %.3f(s)\n", (float) (finishTime - startTime) / 1_000);
  }

  public MyNegaMaxGame(Player black, Player white) {
    super(black, white);
  }

}