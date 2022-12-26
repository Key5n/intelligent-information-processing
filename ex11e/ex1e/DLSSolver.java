package ex1e;
// 12m 43s

import java.util.*;

/*
 * コンパイルの都合上Stateクラスを他の探索方法のstateと区別する
 */
class DLSState {
  // 親ノード
  DLSState parent;
  // 問題の特性
  World world;
  // 深さ
  int depth;

  DLSState(World initialWorld) {
    this.parent = null;
    this.world = initialWorld;
    // 親ノードがない場合深さ0
    this.depth = 0;
  }

  DLSState(DLSState parent, World child) {
    this.parent = parent;
    this.world = child;
    // 親ノードの深さ + 1
    this.depth = parent.depth + 1;
  }

  public String toString() {
    return this.world.toString();
  }
}

public class DLSSolver {
  World world;
  int depth;
  /* 訪れたノードの数 */
  int numOfVisitedNodes = 0;
  /* オープンリストの最大長 */
  int maxLengthOfOpenList = 0;

  /* 解くメソッド */
  public void solve(World world, int depth) {
    this.world = world;
    this.depth = depth;
    var root = new DLSState(this.world);

    long startTime = System.currentTimeMillis();
    var goal = search(root);
    long finishTime = System.currentTimeMillis();

    System.out.printf("Time passed: %5d\n", finishTime - startTime);

    if (goal != null) {
      printSolution(goal);
    } else {
      System.out.println("Goal not found within depth: " + this.depth);
      System.out.println();
    }

  }

  /* 次探索すべきノードを探してゲットするメソッド */
  DLSState search(DLSState root) {
    List<DLSState> openList = new ArrayList<>();
    openList.add(root);

    while (openList.size() > 0) {
      /* 先頭ノードの選択 */
      var state = get(openList);
      if (isGoal(state)) {
        return state;
      }
      /* 深さ制限dを超えるノードを展開しない */
      if (state.depth < depth) {
        var children = children(state);
        /* 縦型探索 */
        openList = concat(children, openList);
      }
    }
    return null;
  }

  /*
   * 引数のstateがゴールか判定するメソッド
   * worldインタフェースのゴール判定を用いる
   */
  boolean isGoal(DLSState state) {
    return state.world.isGoal();
  }

  /*
   * listから先頭のノードを獲得するメソッド
   */
  DLSState get(List<DLSState> list) {
    numOfVisitedNodes++;

    return list.remove(0);
  }

  /**
   * 子ノードを生成する
   * 
   * @param state
   * @return children 子ノードのリスト
   */
  List<DLSState> children(DLSState state) {
    List<DLSState> children = new ArrayList<>();
    for (var action : state.world.actions()) {
      var next = state.world.perform(action);
      if (next != null) {
        var child = new DLSState(state, next);
        children.add(child);
      }
    }
    return children;
  }

  /*
   * リストを結合する
   * 第1引数が前, 第2引数が後ろに結合される
   */
  List<DLSState> concat(List<DLSState> frontList, List<DLSState> backList) {
    List<DLSState> list = new ArrayList<>();
    list.addAll(frontList);
    list.addAll(backList);
    this.maxLengthOfOpenList = maxLengthOfOpenList < list.size() ? list.size() : this.maxLengthOfOpenList;
    return list;
  }

  /*
   * 解を出力する
   * 右から左に状態が出力される
   */
  void printSolution(DLSState goal) {
    System.out.println("DLS Solver");
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
