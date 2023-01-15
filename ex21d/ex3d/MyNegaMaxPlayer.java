package ex3d;

import ex3b.*;
import ex3c.*;
import static java.lang.Float.*;
import java.util.*;

public class MyNegaMaxPlayer extends MyAlphaBetaPlayer {

  public MyNegaMaxPlayer(Eval eval, int deapthLimit) {
    super(eval, deapthLimit);
  }

  float negaMaxSearch(State state, float alpha, float beta, int depth) {
    if (isTerminal(state, depth)) {
      return this.eval.value(state);
    }
    List<Move> moves = state.getMoves();
    float v = NEGATIVE_INFINITY;

    for (Move move : moves) {
      State next = state.perform(move);
      // game.visited++;
      float v0 = -negaMaxSearch(next, -beta, -alpha, depth + 1);
      v = Math.max(v, v0);
      if (beta <= v0) {
        // game.numOfCutting++;
        // System.out.println("Cutting happened since evaluated value " + v0 + " larger
        // than " + beta + " is found.");
        break;
      }
      alpha = Math.max(alpha, v);
      if (depth == 0 && v == v0) {
        this.move = move;
      }
    }
    return v;
  }

  @Override
  protected Move search(State state) {
    negaMaxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
    return this.move;
  }
}
