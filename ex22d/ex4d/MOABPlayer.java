package ex4d;

import ex4a.*;

import static java.lang.Float.*;
import java.util.*;

public class MOABPlayer extends Player {
  protected Eval eval;
  protected int depthLimit;
  protected Move move;

  public MOABPlayer(Eval eval, int depthLimit) {
    super("MOAB" + depthLimit);
    this.eval = eval;
    this.depthLimit = depthLimit;
  }

  // 最適解の探索
  protected Move search(State state) {
    maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
    return this.move;
  }

  // 最適解の探索
  protected float maxSearch(State state, float alpha, float beta, int depth) {
    if (isTerminal(state, depth))
      return this.eval.value(state);

    List<Move> moves = state.getMoves();

    Collections.sort(moves, (move1, move2) -> {
      State s1 = state.perform(move1);
      State s2 = state.perform(move2);
      Eval eval = new Eval();
      float value1 = eval.value(s1);
      float value2 = eval.value(s2);
      return (int) (value1 - value2);
    });

    float v = NEGATIVE_INFINITY;

    for (Move move : moves) {
      State next = state.perform(move);
      Game.visited++;
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
    Collections.sort(moves, (move1, move2) -> {
      State s1 = state.perform(move1);
      State s2 = state.perform(move2);
      Eval eval = new Eval();
      float value1 = eval.value(s1);
      float value2 = eval.value(s2);
      return (int) (value1 - value2);
    });

    for (Move move : moves) {
      State next = state.perform(move);
      Game.visited++;
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

  // 葉ノードもしくは深さ制限にひっかかったなら
  protected boolean isTerminal(State state, int depth) {
    return state.isGoal() || depth >= this.depthLimit;
  }
}