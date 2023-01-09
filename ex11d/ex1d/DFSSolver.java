package ex1d;
// 12m 43s

import java.util.*;

public class DFSSolver extends BFSSolver {
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
      // 縦型探索
      // 展開した子ノードの優先度を高くする(スタックの役割)
      openList = concat(children, openList);
    }
    return null;
  }

}