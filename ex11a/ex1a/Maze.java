package ex1a;

/* 10m 38s */
import java.util.*;

public class Maze {
  /* 親ノードから子ノードへのマップ */
  Map<String, List<String>> map = Map.of(
      "A", List.of("B", "C", "D"),
      "B", List.of("E", "F"),
      "C", List.of("G", "H"),
      "D", List.of("I", "J"));

  /* ルートノードの定義 */
  String root() {
    return "A";
  }

  /* ゴールノードの定義 */
  String goal() {
    return "E";
  }

  /* 迷路を定義してsolveメソッドで解く */
  public static void main(String[] args) {
    var maze = new Maze();
    maze.solve();
  }

  /* ゴールノードを探す */
  public void solve() {
    var root = root();
    var goal = search(root);
    if (goal != null) {
      System.out.println("found");
    }
  }
  /* 次探索すべきノードを探してゲットするメソッド */

  String search(String root) {
    List<String> openList = new ArrayList<>();
    openList.add(root);
    while (openList.size() > 0) {
      var state = get(openList);
      if (isGoal(state)) {
        return state;
      }
      var children = children(state);
      openList = concat(openList, children);
    }
    return null;
  }

  /*
   * 引数のstateがゴールか判定するメソッド
   * Stringクラスの一致判定にはequalsメソッドを用いる
   * ==演算子では参照先の一致判定になってしまうため
   */
  boolean isGoal(String state) {
    return goal().equals(state);
  }

  /*
   * listから先頭のノードを獲得するメソッド
   */
  String get(List<String> list) {
    return list.remove(0);
  }

  /**
   * 子ノードを生成する
   * mapからkeyがcurrentのもののvalueを抜き出す
   * valueがnullの場合には第2引数が返される(この場合はCollections.emptyList())
   * 
   * @param state
   * @return children 子ノードのリスト
   */
  List<String> children(String current) {
    return this.map.getOrDefault(current, Collections.emptyList());
  }

  /* リストを結合する */
  List<String> concat(List<String> frontList, List<String> backList) {
    List<String> list = new ArrayList<>();
    list.addAll(frontList);
    list.addAll(backList);
    return list;
  }

}