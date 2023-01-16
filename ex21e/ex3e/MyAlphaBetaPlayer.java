package ex3e;

import java.util.*;
import static java.lang.Float.*;
import ex3b.*;

public class MyAlphaBetaPlayer extends MinMaxPlayer {
  // public MyAlphaBetaGame game;

  public MyAlphaBetaPlayer(Eval eval, int deapthLimit) {
    super(eval, deapthLimit);
  }

  @Override
  protected Move search(State state) {
    if (this.color == State.WHITE) {
      minSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
    } else {
      maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
    }
    return this.move;
  }

  float maxSearch(State state, float alpha, float beta, int depth) {
    if (isTerminal(state, depth))
      return this.eval.value(state);

    List<Move> moves = state.getMoves();
    float v = NEGATIVE_INFINITY;

    for (Move move : moves) {
      State next = state.perform(move);
      // game.visited++;
      float v0 = minSearch(next, alpha, beta, depth + 1);
      v = Math.max(v, v0);
      if (beta <= v0) {
        // game.numOfCutting++;
        // System.out.println("Beta cut happened since evaluated value " + v0 + " larger
        // than " + beta + " is found.");
        break;
      }
      alpha = Math.max(alpha, v0);
      if (depth == 0 && v == v0)
        this.move = move;
    }

    return v;
  }

  float minSearch(State state, float alpha, float beta, int depth) {
    if (isTerminal(state, depth))
      return this.eval.value(state);

    List<Move> moves = state.getMoves();
    float v = POSITIVE_INFINITY;

    for (Move move : moves) {
      State next = state.perform(move);
      // game.visited++;
      float v0 = maxSearch(next, alpha, beta, depth + 1);
      v = Math.min(v, v0);
      if (depth == 0 && v == v0)
        this.move = move;
      if (alpha >= v0) {
        // game.numOfCutting++;
        // System.out.println("Alpha cut happened since evaluated value " + v0 + " less
        // than " + beta + " is found.");
        break;
      }
      beta = Math.min(beta, v0);
    }
    return v;
  }
}