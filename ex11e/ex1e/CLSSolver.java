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

public class CLSSolver extends DLSSolver {
  public void solve(World world) {
    this.world = world;
    var root = new State(this.world);

    long startTime = System.currentTimeMillis();
    var goal = search(root);
    long finishTime = System.currentTimeMillis();
    /* 探索にかかった時間 */

    if (goal != null) {
      printSolution(goal);
      System.out.printf("Time passed: %d\n", finishTime - startTime);
    }
  }

  State search(State root) {
    /* オープンリスト */
    List<State> openList = new ArrayList<>();
    /* クローズドリスト */
    List<State> closedList = new ArrayList<>();
    /* 消去するノードを格納するリスト */
    List<State> deleteNodeList = new ArrayList<>();
    openList.add(root);

    while (openList.size() > 0) {
      var state = get(openList);
      if (isGoal(state)) {
        return state;
      }
      closedList.add(state);
      var children = children(state);
      for (State childState : children) {
        for (State closedListState : closedList) {
          /*
           * その状態がクローズドリストの中にある場合
           * 消去するノードの中にそのノードをpushする
           */
          if (childState.equals(closedListState)) {
            deleteNodeList.add(childState);
          }
        }

        for (State openListState : openList) {
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
      for (State deleteNode : deleteNodeList) {
        children.remove(deleteNode);
      }
      openList = concat(openList, children);
      maxLen = Math.max(this.maxLen, openList.size());
    }
    return null;
  }

}
