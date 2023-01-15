package ex3d;

import ex3b.*;
import ex3c.*;

public class MyNegaMaxPlayer extends MyAlphaBetaPlayer {
  public MyNegaMaxPlayer(Eval eval, int deapthLimit) {
    super(eval, deapthLimit);
  }
}
