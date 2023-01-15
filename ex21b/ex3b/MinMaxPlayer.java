package ex3b;

import static java.lang.Float.*;
import java.util.*;

public class MinMaxPlayer extends Player {
  // 評価関数
  protected Eval eval;
  // 深さ制限
  int depthLimit;
  // プレイヤーが採った行動
  protected Move move;

  public MinMaxPlayer(Eval eval, int depthLimit) {
    super("MinMax" + depthLimit);
    this.eval = eval;
    this.depthLimit = depthLimit;
  }

  // stateをルートにしたmin-max search
  protected Move search(State state) {
    maxSearch(state, 0);
    return this.move;
  }

  // 自分の評価値を最大化する探索
  // 評価値の最大値を返す
  float maxSearch(State state, int depth) {
    // 葉ノードもしくは深さ制限に引っかかるなら
    if (isTerminal(state, depth))
      // その状態の評価値を返す
      return this.eval.value(state);

    // 行うことができる行動のリスト
    List<Move> moves = state.getMoves();
    float v = NEGATIVE_INFINITY;

    for (Move move : moves) {
      // 行動を行う
      State next = state.perform(move);
      // 相手の手番なのでこちらの評価値を最小化する手を打つ
      float v0 = minSearch(next, depth + 1);
      v = Math.max(v, v0);
      // 最大となる評価値を出した行動を記録する
      if (depth == 0 && v == v0)
        this.move = move;
    }

    return v;
  }

  // 自分の評価値を最小化する探索
  // 評価値の最小値を返す
  float minSearch(State state, int depth) {
    // 葉ノードもしくは深さ制限に引っかかるなら
    if (isTerminal(state, depth))
      // その状態の評価値を返す
      return this.eval.value(state);

    // 行うことができる行動のリスト
    List<Move> moves = state.getMoves();
    float v = POSITIVE_INFINITY;

    for (Move move : moves) {
      // 行動を行う
      State next = state.perform(move);
      // 自分の手番なのでの評価値を最大化する手を打つ
      float v0 = maxSearch(next, depth + 1);
      v = Math.min(v, v0);
      // 最小となる評価値を出した行動を記録する
      if (depth == 0 && v == v0)
        this.move = move;
    }

    return v;
  }

  // 石の数が0個か深さ制限を超えているなら終端とする
  protected boolean isTerminal(State state, int depth) {
    return state.isGoal() || depth >= this.depthLimit;
  }
}
