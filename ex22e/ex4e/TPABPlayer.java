package ex4e;

import ex4a.*;

import static java.lang.Float.*;
import java.util.*;

public class TPABPlayer extends AlphaBetaPlayer {
  public Map<State, Float> transpositionTable = new HashMap<>();

  public TPABPlayer(Eval eval, int depthLimit) {
    super(eval, depthLimit);
    this.name = "TPABPlayer" + depthLimit;
  }

  protected Move search(State state) {
    super.maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
    return this.move;
  }

  @Override
  protected float maxSearch(State state, float alpha, float beta, int depth) {

    if (transpositionTable.containsKey(state)) {
      Game.count++;
      return transpositionTable.get(state);
    }
    float v = super.maxSearch(state, alpha, beta, depth);
    transpositionTable.put(state, v);

    return v;
  }

  @Override
  float minSearch(State state, float alpha, float beta, int depth) {

    if (transpositionTable.containsKey(state)) {
      Game.count++;
      return transpositionTable.get(state);
    }
    float v = super.minSearch(state, alpha, beta, depth);
    transpositionTable.put(state, v);

    return v;
  }

}