package ex1b;

// 9m19s
import java.util.*;

public class MazeProblem {
  public static void main(String[] args) {
    var solver = new Solver();
    solver.solve(new MazeWorld("A"));
  }

}

class MazeAction implements Action {
  String next;

  MazeAction(String next) {
    this.next = next;
  }
}

class MazeWorld implements World {
  /* 親ノードから子ノードへのマップ */
  Map<String, List<String>> map = Map.of(
      "A", List.of("B", "C", "D"),
      "B", List.of("E", "F"),
      "C", List.of("G", "H"),
      "D", List.of("I", "J"));
  String current;

  MazeWorld(String current) {
    this.current = current;
  }

  /* ルートノードの定義 */
  String root() {
    return "A";
  }

  /* ゴールノードの定義 */
  String goal() {
    return "E";
  }

  /* 書式設定 */
  public String toString() {
    return this.current;
  }

  /*
   * 引数のstateがゴールか判定するメソッド
   * Stringクラスの一致判定にはequalsメソッドを用いる
   * ==演算子では参照先の一致判定になってしまうため
   */
  public boolean isGoal() {
    return this.current.equals(goal());
  }

  public boolean isValid() {
    return true;
  }

  /*
   * worldインタフェースの実装
   * mapで定義した親ノードに対する子ノードをリスト化して返している
   */
  public List<Action> actions() {
    List<Action> actions = new ArrayList<>();
    var nexts = this.map.getOrDefault(current, Collections.emptyList());
    for (var next : nexts) {
      actions.add(new MazeAction(next));
    }
    return actions;
  }

  /* actionを行った後の次のノードを返す */
  public World perform(Action action) {
    if (action instanceof MazeAction) {
      var a = (MazeAction) action;
      return new MazeWorld(a.next);
    }
    return null;
  }
}