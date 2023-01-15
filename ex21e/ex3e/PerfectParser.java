package ex3e;

import ex3b.*;
import ex3c.*;
import ex3d.*;

public class PerfectParser extends MyNegaMaxGame {
  public PerfectParser(Player black, Player white, int numOfPebbles) {
    super(black, white);
    this.state = new State(numOfPebbles);
  }

  public static void main(String[] args) {
    for (int i = 1; i <= 10; i++) {
      var p0 = new MyNegaMaxPlayer(new Eval(), 20);
      var p1 = new MyNegaMaxPlayer(new Eval(), 20);
      // p0が黒(先手)でp1が白(後手)
      MyNegaMaxGame g = new PerfectParser(p0, p1, i);
      p0.game = g;
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
}
