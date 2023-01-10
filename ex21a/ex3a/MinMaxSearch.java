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
  // 深さ制限
  int depthLimit;

  MinMaxSearch(Eval eval, int deapthLimit) {
    this.eval = eval;
    this.depthLimit = deapthLimit;
  }

  float search(State state) {
    return maxSearch(state, 0);
  }

  /**
   * max-min search
   * 
   * @param state ルートノード
   * @param depth その状態の深さを表す
   * @return 子ノードで最大の評価値
   */
  float maxSearch(State state, int depth) {
    // その状態が終端ならばその状態の評価値を返す
    if (isTerminal(state, depth))
      return this.eval.value(state);

    // 子ノードの取得
    List<String> moves = state.getMoves();
    float v = NEGATIVE_INFINITY;

    for (String move : moves) {
      State next = state.perform(move);
      // 子ノード1つ1つに対して
      // 子ノードは1階層深くなるため
      float v0 = minSearch(next, depth + 1);
      // 子ノードのうち最大のものが見つかったら更新
      v = Math.max(v, v0);
    }

    return v;
  }

  /**
   * min-max search
   * 
   * @param state ルートノード
   * @param depth その状態の深さを表す
   * @return 子ノードで最大の評価値
   */
  float minSearch(State state, int depth) {
    // その状態が終端ならばその状態の評価値を返す
    if (isTerminal(state, depth))
      return this.eval.value(state);

    // 子ノードの取得
    List<String> moves = state.getMoves();
    float v = POSITIVE_INFINITY;

    for (String move : moves) {
      State next = state.perform(move);
      // 子ノード1つ1つに対して
      // 子ノードは1階層深くなるため
      float v0 = maxSearch(next, depth + 1);
      // 子ノードのうち最小のものが見つかったら更新
      v = Math.min(v, v0);
    }

    return v;
  }

  // その状態が葉、または深さ制限に達したならば真を返す
  boolean isTerminal(State state, int depth) {
    return state.isGoal() || depth >= this.depthLimit;
  }
}
