package ex1d;
// 12m 43s

import java.util.*;

interface Action {

}

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

  /*
   * コンストラクタ
   * 親がない場合
   */
  State(World initialWorld) {
    this.parent = null;
    this.world = initialWorld;
  }

  /* 親がある場合 */
  State(State parent, World child) {
    this.parent = parent;
    this.world = child;
  }

  /* 文字列化した場合の書式設定 */
  public String toString() {
    return this.world.toString();
  }
}

public class BFSSolver {
  World world;
  /* 訪れたノードの数 */
  long visited = 0;
  /* オープンリストの最大長 */
  long maxLen = 0;

  /* 解くメソッド */
  public void solve(World world) {
    this.world = world;
    var root = new State(this.world);

    long startTime = System.currentTimeMillis();
    var goal = search(root);
    long finishTime = System.currentTimeMillis();
    if (goal != null)
      printSolution(goal);
    System.out.printf("visited: %d, max length: %d\n", this.visited, this.maxLen);
    System.out.printf("Time passed: %d\n", finishTime - startTime);
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
      this.maxLen = Math.max(this.maxLen, openList.size());
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
    this.visited++;
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
    System.out.println("BFS Solver");
    while (goal != null) {
      System.out.print(goal + " <- ");
      goal = goal.parent;
    }
    System.out.println("start");

  }

}
