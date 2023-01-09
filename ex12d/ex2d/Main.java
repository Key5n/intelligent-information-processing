package ex2d;

public class Main {
  public static void main(String[] args) {
    // int order[] = { 2, 3, 5, 7, 1, 6, 4, 8, 0 };
    int order[] = { 8, 6, 7, 5, 0, 4, 2, 3, 1 };
    /*
     * 評価関数 = 経路コスト
     * 辺の重みが最小のものから探索する
     * 最小コスト優先探索
     */
    // InformedSolver minCostSearch = new InformedSolver((state) -> state.g());
    // minCostSearch.solve(new EightPuzzleWorld(order));
    /*
     * eval.f(評価関数) = h(ヒューリスティック)
     * 今回の場合左岸の宣教師の数が最小のものから探索する
     * 最良優先探索
     */
    // InformedSolver bestFirstSearch = new InformedSolver((state) -> state.h());
    // bestFirstSearch.solve(new EightPuzzleWorld(order));

    /*
     * 評価関数 = 状態のコスト + ヒューリスティック
     * 最小コスト優先探索を行い、さらに見込みも利用する
     * 見込みhを利用しているため探索効率がよく、最適解を出す保証がある
     * A*アルゴリズム
     */
    InformedSolver aStarSearch = new InformedSolver((state) -> state.g() +
        state.h());
    aStarSearch.solve(new EightPuzzleWorld(order));
  }
}