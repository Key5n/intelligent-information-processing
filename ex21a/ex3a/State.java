package ex3a;

import java.util.*;

class State {
  // 親ノードから子ノードへの対応付け
  static Map<String, List<String>> childrenLists = Map.of(
      "A", List.of("B", "C"),
      "B", List.of("D", "E"),
      "C", List.of("F", "G"));
  // 子ノードの評価
  static Map<String, Float> values = Map.of(
      "D", 3.0f,
      "E", 2.0f,
      "F", 1.0f,
      "G", 4.0f);

  String current;

  State(String current) {
    this.current = current;
  }

  // 書式設定
  public String toString() {
    return this.current.toString();
  }

  // 現在の状態が葉ならば真
  // 葉でないならば偽を返す
  // 葉ならばゴール扱い
  boolean isGoal() {
    return getMoves().isEmpty();
  }

  // 現在の状態に対する子ノードのリストを返す
  List<String> getMoves() {
    return State.childrenLists.getOrDefault(this.current, new ArrayList<>());
  }

  // 新しい状態を作成する
  State perform(String move) {
    return new State(move);
  }
}

// 引数のstateに対応する評価を返す
class Eval {
  float value(State state) {
    return State.values.getOrDefault(state.current, Float.NaN);
  }
}