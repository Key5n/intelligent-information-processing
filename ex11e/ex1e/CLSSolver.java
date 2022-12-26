package ex1e;
// 12m 43s

import java.util.*;

interface Action {

}

interface World extends Cloneable {
  boolean isGoal();

  List<Action> actions();

  World perform(Action action);

  /* worldインスタンスに特有な値 */
  int hashCode();
}

/*
 * コンパイルの都合上Stateクラスを他の探索方法のstateと区別する
 */
class CLSState {
  /* 親ノード */
  CLSState parent;
  /* 問題に関する状態の特性 */
  World world;

  /*
   * コンストラクタ
   * 親がない場合
   */
  CLSState(World initialWorld) {
    this.parent = null;
    this.world = initialWorld;

  }

  /* 親がある場合 */
  CLSState(CLSState parent, World child) {
    this.parent = parent;
    this.world = child;
  }

  /* 文字列化した場合の書式設定 */
  public String toString() {
    return this.world.toString();
  }

  /* hash codeが同じ場合その状態は同値 */
  boolean equals(CLSState s) {
    return this.world.hashCode() == s.world.hashCode();
  }
}

public class CLSSolver {
  World world;
  /* 訪れたノードの数 */
  int numOfVisitedNodes = 0;
  /* オープンリストの最大長 */
  int maxLengthOfOpenList = 0;

  public void solve(World world) {
    this.world = world;
    var root = new CLSState(this.world);

    long startTime = System.currentTimeMillis();
    var goal = search(root);
    long finishTime = System.currentTimeMillis();
    /* 探索にかかった時間 */
    System.out.printf("Time passed: %5d\n", finishTime - startTime);

    if (goal != null)
      printSolution(goal);
  }

  CLSState search(CLSState root) {
    /* オープンリスト */
    List<CLSState> openList = new ArrayList<>();
    /* クローズドリスト */
    List<CLSState> closedList = new ArrayList<>();
    /* 消去するノードを格納するリスト */
    List<CLSState> deleteNodeList = new ArrayList<>();
    openList.add(root);

    while (openList.size() > 0) {
      var state = get(openList);
      if (isGoal(state)) {
        return state;
      }
      closedList.add(state);
      var children = children(state);
      for (CLSState childState : children) {
        for (CLSState closedListState : closedList) {
          /*
           * その状態がクローズドリストの中にある場合
           * 消去するノードの中にそのノードをpushする
           */
          if (childState.equals(closedListState)) {
            deleteNodeList.add(childState);
          }
        }

        for (CLSState openListState : openList) {
          /*
           * その状態がオープンリストの中にある場合
           * 消去するノードの中にそのノードをpushする
           */
          if (childState.equals(openListState)) {
            deleteNodeList.add(childState);
          }
        }
      }
      // childrenからopenListとclosedListに存在するものを消去
      for (CLSState deleteNode : deleteNodeList) {
        children.remove(deleteNode);
      }
      openList = concat(openList, children);
    }
    return null;
  }

  /*
   * 引数のstateがゴールか判定するメソッド
   * worldインタフェースのゴール判定を用いる
   */
  boolean isGoal(CLSState state) {
    return state.world.isGoal();
  }

  /*
   * listから先頭のノードを獲得するメソッド
   * 訪れたノードの数を増やす
   */
  CLSState get(List<CLSState> list) {
    numOfVisitedNodes++;
    return list.remove(0);
  }

  /**
   * 子ノードを生成する
   * 
   * @param state
   * @return children 子ノードのリスト
   */
  List<CLSState> children(CLSState state) {
    List<CLSState> children = new ArrayList<>();
    for (var action : state.world.actions()) {
      var next = state.world.perform(action);
      if (next != null) {
        var child = new CLSState(state, next);
        children.add(child);
      }
    }
    return children;
  }

  /*
   * リストを結合する
   * 第1引数が前, 第2引数が後ろに結合される
   */
  List<CLSState> concat(List<CLSState> frontList, List<CLSState> backList) {
    List<CLSState> list = new ArrayList<>();
    list.addAll(frontList);
    list.addAll(backList);
    this.maxLengthOfOpenList = maxLengthOfOpenList < list.size() ? list.size() : this.maxLengthOfOpenList;
    return list;
  }

  /*
   * 解を出力する
   * 右から左に状態が出力される
   */
  void printSolution(CLSState goal) {
    System.out.println("CLS Solver");
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
