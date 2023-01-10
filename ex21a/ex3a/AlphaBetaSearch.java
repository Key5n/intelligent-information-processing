package ex3a;

import static java.lang.Float.*;
import java.util.*;

class AlphaBetaSearch {
  public static void main(String[] args) {
    AlphaBetaSearch player = new AlphaBetaSearch(new Eval(), 2);
    // ルートノードの定義
    State state = new State("A");
    // Aをルートノードにして探索開始
    float value = player.search(state);
    System.out.println(value);
  }

  Eval eval;
  int depthLimit;

  // 評価方法の初期化
  // 深さ制限の初期化
  AlphaBetaSearch(Eval eval, int deapthLimit) {
    this.eval = eval;
    this.depthLimit = deapthLimit;
  }

  float search(State state) {
    return maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
  }

  /**
   * 子ノードから最大の評価値を返す
   * 
   * @param state ルートノード
   * @param alpha
   * @param beta
   * @param depth
   * @return
   */
  float maxSearch(State state, float alpha, float beta, int depth) {
    if (isTerminal(state, depth))
      return this.eval.value(state);

    List<String> moves = state.getMoves();
    float v = NEGATIVE_INFINITY;

    for (String move : moves) {
      State next = state.perform(move);
      float v0 = minSearch(next, alpha, beta, depth + 1);
      v = Math.max(v, v0);
      // alphaより小さいものを見つけるとminSearchメソッドが返す値はalpha以下になる
      // そのためalphaより小さいノードを見つけたらそれ以降のノードの探索を打ち切る
      if (beta <= v0)
        break;
      // alphaの更新
      alpha = Math.max(alpha, v0);
    }

    return v;
  }

  /**
   * 子ノードから最小の評価値を返す
   * 
   * @param state ルートノード
   * @param alpha
   * @param beta
   * @param depth
   * @return 子ノードから最小の評価値
   */
  float minSearch(State state, float alpha, float beta, int depth) {
    // その状態が葉、または深さ制限に達したならば真を返す
    if (isTerminal(state, depth))
      return this.eval.value(state);

    // 子ノードのリストの取得
    List<String> moves = state.getMoves();
    float v = POSITIVE_INFINITY;

    for (String move : moves) {
      State next = state.perform(move);
      float v0 = maxSearch(next, alpha, beta, depth + 1);
      v = Math.min(v, v0);
      // alphaより小さいものを見つけるとminSearchメソッドが返す値はalpha以下になる
      // そのためalphaより小さいノードを見つけたらそれ以降のノードの探索を打ち切る
      if (alpha >= v0)
        break;
      // betaの更新
      beta = Math.min(beta, v0);
    }

    return v;
  }

  // その状態が葉、または深さ制限に達したならば真を返す
  boolean isTerminal(State state, int depth) {
    return state.isGoal() || depth >= this.depthLimit;
  }
}