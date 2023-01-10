package ex2d;

import java.util.*;

interface Action {
  float cost();
}

interface World extends Cloneable {
  boolean isGoal();

  List<Action> actions();

  World perform(Action action);

  /*
   * ゴールまでの評価を返す
   */
  float h();

  // boolean world
}

class State {
  State parent;
  World world;
  float cost;

  State(State parent, World child) {
    this.parent = parent;
    this.world = child;
  }

  public String toString() {
    return this.world.toString() + "@" + this.cost;
  }

  /* 経路コストを返す */
  float g() {
    return this.cost;
  }

  /* 評価値を返す */
  float h() {
    return this.world.h();
  }
}

interface Evaluator {
  /* 比較メソッド */
  float f(State s);
}

public class InformedSolver {
  Evaluator eval;
  World world;
  List<State> closedList = new ArrayList<>();

  /* 訪れたノード数 */
  long visited = 0;
  /* オープンリストの最大長 */
  long maxLen = 0;

  /* 比較方法の定義 */
  public InformedSolver(Evaluator eval) {
    this.eval = eval;
  }

  /* 解くメソッド */
  public void solve(World world) {
    this.world = world;
    State root = new State(null, this.world);
    long startTime = System.currentTimeMillis();
    State goal = search(root);
    long finishTime = System.currentTimeMillis();
    if (goal != null) {
      printSolution(goal);
      System.out.printf("Time passed: %d\n", finishTime - startTime);
    }
    System.out.printf("visited: %d, max length: %d\n", this.visited, this.maxLen);
  }

  /* 解を探すメソッド */
  State search(State root) {
    List<State> openList = new ArrayList<>();
    List<State> deleteList = new ArrayList<>();
    openList.add(root);

    while (openList.size() > 0) {
      var state = get(openList);
      /* 訪問ノード数 + 1 */
      this.visited += 1;
      closedList.add(state);
      if (isGoal(state))
        return state;
      var children = children(state);
      for (State s : children) {
        if (closedList.contains(s) || openList.contains(s)) {
          deleteList.add(s);
        }
      }
      for (State s : deleteList) {
        children.remove(s);
      }
      /* 横型探索 */
      openList = concat(openList, children);
      /* 評価関数を元にソート */
      sort(openList);
      /* リストの最大長の更新 */
      this.maxLen = Math.max(this.maxLen, openList.size());
    }

    return null;
  }

  /* ゴール判定 */
  boolean isGoal(State state) {
    return state.world.isGoal();
  }

  /* 先頭ノードの取得 */
  State get(List<State> list) {
    return list.remove(0);
  }

  /* 子ノードの展開 */
  List<State> children(State state) {
    List<State> children = new ArrayList<>();
    for (var action : state.world.actions()) {
      var next = state.world.perform(action);
      /*
       * そのノードが有効ならば追加する
       * 有効でないなら何もしない
       */
      if (next != null) {
        var child = new State(state, next);
        /* ノードのコスト = ノードのコスト + ノードからノードへの辺のコスト */
        child.cost = state.cost + action.cost();
        children.add(child);
      }
    }
    return children;
  }

  /* リストの結合 */
  List<State> concat(List<State> frontList, List<State> backList) {
    List<State> list = new ArrayList<>();
    list.addAll(frontList);
    list.addAll(backList);
    return list;
  }

  /*
   * Stateインスタンスのfメソッドの返り値をもとにした昇順順序付けソート
   * この場合メソッドfは状態のコストを返すためコストが小さい順にソートされる
   */
  void sort(List<State> list) {
    list.sort(Comparator.comparing(s -> this.eval.f(s)));
  }

  /*
   * 解の表示
   */
  void printSolution(State goal) {
    while (goal != null) {
      System.out.print(goal + " <-\n");
      goal = goal.parent;
    }
    System.out.println("start");
  }
}