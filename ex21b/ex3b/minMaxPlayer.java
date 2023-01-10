package ex3a;

import static java.lang.Float.*;
import java.util.*;

class MinMaxSearch {
  public static void main(String[] args) {
    MinMaxSearch player = new MinMaxSearch(new Eval(), 2);
    State state = new State("A");
    float value = player.search(state);
    System.out.println(value);
  }

  Eval eval;
  int depthLimit;

  MinMaxSearch(Eval eval, int deapthLimit) {
    this.eval = eval;
    this.depthLimit = deapthLimit;
  }

  float search(State state) {
    return maxSearch(state, 0);
  }

  float maxSearch(State state, int depth) {
    if (isTerminal(state, depth))
      return this.eval.value(state);

    List<String> moves = state.getMoves();
    float v = NEGATIVE_INFINITY;

    for (String move : moves) {
      State next = state.perform(move);
      float v0 = minSearch(next, depth + 1);
      v = Math.max(v, v0);
    }

    return v;
  }

  float minSearch(State state, int depth) {
    if (isTerminal(state, depth))
      return this.eval.value(state);

    List<String> moves = state.getMoves();
    float v = POSITIVE_INFINITY;

    for (String move : moves) {
      State next = state.perform(move);
      float v0 = maxSearch(next, depth + 1);
      v = Math.min(v, v0);
    }

    return v;
  }

  boolean isTerminal(State state, int depth) {
    return state.isGoal() || depth >= this.depthLimit;
  }
}
