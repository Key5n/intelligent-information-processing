package ex3b;

public class Eval {
  // その状態の評価値を返す
  // 今回は自分の勝利なら最大値
  // 相手の勝利なら最小値
  // その他なら石の数を返す
  public float value(State state) {
    return state.isGoal() ? Integer.MAX_VALUE * state.winner() : state.stones;
  }
}
