package ex1e;
// 12m 43s

import java.util.*;

/*
 * コンパイルの都合上Stateクラスを他の探索方法のstateと区別する
 */
class IDSState {
  IDSState parent;
  World world;
  int depth;

  /*
   * コンストラクタ
   * 親がない場合
   */
  IDSState(World initialWorld) {
    this.parent = null;
    this.world = initialWorld;
    this.depth = 0;
  }

  /* 親がある場合 */
  IDSState(IDSState parent, World child) {
    this.parent = parent;
    this.world = child;
    this.depth = parent.depth + 1;
  }

  /* 文字列化した場合の書式設定 */
  public String toString() {
    return this.world.toString();
  }
}

public class IDSSolver {
  World world;
  /* 訪れたノードの数 */
  int numOfVisitedNodes = 0;
  /* オープンリストの最大長 */
  int maxLengthOfOpenList = 0;
  /* 深さ */
  int depth;

  /* 解くメソッド */
  public void solve(World world) {
    this.world = world;
    var root = new IDSState(this.world);
    long startTime = System.currentTimeMillis();
    var goal = search(root);
    long finishTime = System.currentTimeMillis();
    System.out.printf("Time passed: %5d\n", finishTime - startTime);
    if (goal != null) {
      printSolution(goal);
    }
  }

  /* 次探索すべきノードを探してゲットするメソッド */
  IDSState search(IDSState root) {
    depth = 0;
    while (true) {
      List<IDSState> openList = new ArrayList<>();
      openList.add(root);
      /* 深さの制限に依存した探索 */
      var state = depthLimitedSearch(depth, openList);
      if (isGoal(state)) {
        return state;
      }
      /* 深さの制限を1つ緩く */
      depth++;
    }

  }

  /* 深さ制限探索 */
  IDSState depthLimitedSearch(int limitedDepth, List<IDSState> openList) {
    while (openList.size() > 0) {
      var state = get(openList);
      if (isGoal(state)) {
        return state;
      }
      if (state.depth < limitedDepth) {
        var children = children(state);
        openList = concat(children, openList);
      }
    }
    return null;
  }

  /*
   * 引数のstateがゴールか判定するメソッド
   * worldインタフェースのゴール判定を用いる
   */
  boolean isGoal(IDSState state) {
    if (state == null) {
      return false;
    }
    return state.world.isGoal();
  }

  /*
   * listから先頭のノードを獲得するメソッド
   */
  IDSState get(List<IDSState> list) {
    numOfVisitedNodes++;
    return list.remove(0);
  }

  /**
   * 子ノードを生成する
   * 
   * @param state
   * @return children 子ノードのリスト
   */
  List<IDSState> children(IDSState state) {
    List<IDSState> children = new ArrayList<>();
    for (var action : state.world.actions()) {
      var next = state.world.perform(action);
      if (next != null) {
        var child = new IDSState(state, next);
        children.add(child);
      }
    }
    return children;
  }

  /*
   * リストを結合する
   * 第1引数が前, 第2引数が後ろに結合される
   */
  List<IDSState> concat(List<IDSState> frontList, List<IDSState> backList) {
    List<IDSState> list = new ArrayList<>();
    list.addAll(frontList);
    list.addAll(backList);
    /* リストの最大長の更新 */
    this.maxLengthOfOpenList = maxLengthOfOpenList < list.size() ? list.size() : this.maxLengthOfOpenList;
    return list;
  }

  /*
   * 解を出力する
   * 右から左に状態が出力される
   */
  void printSolution(IDSState goal) {
    System.out.println("IDS Solver");
    while (goal != null) {
      System.out.print(goal + " <- ");
      goal = goal.parent;
    }
    System.out.println("start");
    System.out.printf("Visited Nodes: %d\n", numOfVisitedNodes);
    System.out.printf("The maximum length of the open list: %d\n", maxLengthOfOpenList);
    System.out.println();
  }

}
