package ex4a;

import static java.lang.Float.*;
import java.util.*;

public class AlphaBetaPlayer extends Player {
  Eval eval;
  int depthLimit;
  Move move;

  public AlphaBetaPlayer(Eval eval, int depthLimit) {
    super("AlphaBeta" + depthLimit);
    this.eval = eval;
    this.depthLimit = depthLimit;
  }

  Move search(State state) {
    maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
    return this.move;
  }

  float maxSearch(State state, float alpha, float beta, int depth) {
    if (isTerminal(state, depth))
      return this.eval.value(state);

    List<Move> moves = state.getMoves();
    float v = NEGATIVE_INFINITY;

    for (Move move : moves) {
      State next = state.perform(move);
      float v0 = minSearch(next, alpha, beta, depth + 1);
      v = Math.max(v, v0);
      if (beta <= v0)
        break;
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
      float v0 = maxSearch(next, alpha, beta, depth + 1);
      v = Math.min(v, v0);
      if (depth == 0 && v == v0)
        this.move = move;
      if (alpha >= v0)
        break;
      beta = Math.min(beta, v0);
    }

    return v;
  }

  boolean isTerminal(State state, int depth) {
    return state.isGoal() || depth >= this.depthLimit;
  }
}