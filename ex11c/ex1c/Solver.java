package ex1c;
// 12m 43s

import java.util.*;

interface Action {

}

/*
 * worldインターフェースには
 * 1. isGoal() ゴール判定をするメソッド
 * 2. actions() 動作の種類のリストを返すメソッド
 * 3. perform 動作を実際に行うメソッド
 * がされている
 */
interface World extends Cloneable {
  boolean isGoal();

  List<Action> actions();

  World perform(Action action);
}

class State {
  /* 親ノード */
  State parent;
  /* worldの定義 */
  World world;

  /* 親がある場合 */
  State(World initialWorld) {
    this.parent = null;
    this.world = initialWorld;
  }

  State(State parent, World child) {
    this.parent = parent;
    this.world = child;
  }

  /* 文字列化した場合の書式設定 */
  public String toString() {
    return this.world.toString();
  }
}

public class Solver {
  World world;

  /* 解くメソッド */
  public void solve(World world) {
    this.world = world;
    var root = new State(this.world);
    var goal = search(root);
    if (goal != null)
      printSolution(goal);
  }

  /* 次探索すべきノードを探してゲットするメソッド */
  State search(State root) {
    List<State> openList = new ArrayList<>();
    openList.add(root);

    while (openList.size() > 0) {
      var state = get(openList);
      if (isGoal(state)) {
        return state;
      }
      var children = children(state);
      openList = concat(openList, children);
    }
    return null;
  }

  /*
   * 引数のstateがゴールか判定するメソッド
   * worldインタフェースのゴール判定を用いる
   */
  boolean isGoal(State state) {
    return state.world.isGoal();
  }

  /*
   * listから先頭のノードを獲得するメソッド
   */
  State get(List<State> list) {
    return list.remove(0);
  }

  /**
   * 子ノードを生成する
   * 
   * @param state
   * @return children 子ノードのリスト
   */
  List<State> children(State state) {
    List<State> children = new ArrayList<>();
    for (var action : state.world.actions()) {
      var next = state.world.perform(action);
      if (next != null) {
        var child = new State(state, next);
        children.add(child);
      }
    }
    return children;
  }

  /*
   * リストを結合する
   * 第1引数が前, 第2引数が後ろに結合される
   */
  List<State> concat(List<State> frontList, List<State> backList) {
    List<State> list = new ArrayList<>();
    list.addAll(frontList);
    list.addAll(backList);
    return list;
  }

  /*
   * 解を出力する
   * 右から左に状態が出力される
   */
  void printSolution(State goal) {
    while (goal != null) {
      System.out.print(goal + " <- ");
      goal = goal.parent;
    }
    System.out.println("start");
  }

}
