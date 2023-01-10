package ex1e;
// 12m 43s

public class IDSSolver extends DLSSolver {

  /* 次探索すべきノードを探してゲットするメソッド */
  @Override
  State search(State root) {
    depth = 0;
    while (true) {
      /* 深さの制限に依存した探索 */
      var state = search(root, depth);
      if (state != null && isGoal(state)) {
        return state;
      }
      /* 深さの制限を1つ緩く */
      depth++;
    }
  }
}
