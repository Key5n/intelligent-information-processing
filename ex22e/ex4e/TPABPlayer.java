package ex4e;

import ex4a.*;

import static java.lang.Float.*;
import java.util.*;

public class TPABPlayer extends AlphaBetaPlayer {
  public Map<State, Float> transpositionTable = new HashMap<>();
  public int count;

  public TPABPlayer(Eval eval, int depthLimit) {
    super(eval, depthLimit);
    this.name = "TPABPlayer" + depthLimit;
  }

  protected float maxSearch(State state, float alpha, float beta, int depth) {
    if (isTerminal(state, depth))
      return this.eval.value(state);

    if (transpositionTable.containsKey(state)) {
      count++;
      return transpositionTable.get(state);
    }
    List<Move> moves = state.getMoves();
    float v = NEGATIVE_INFINITY;

    for (Move move : moves) {
      State next = (State) state.perform(move);
      float v0 = minSearch(next, alpha, beta, depth + 1);
      // 一度訪れた状態の値を記録
      transpositionTable.put(state, v0);

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

    if (transpositionTable.containsKey(state)) {
      count++;
      return transpositionTable.get(state);
    }

    List<Move> moves = state.getMoves();
    float v = POSITIVE_INFINITY;

    for (Move move : moves) {
      State next = (State) state.perform(move);
      float v0 = maxSearch(next, alpha, beta, depth + 1);
      // 一度訪れた状態の値を記録
      transpositionTable.put(state, v0);
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