package ex1e;
// 12m 43s

import java.util.*;

class State {
  /* 親ノード */
  State parent;
  /* worldの定義 */
  World world;

  /*
   * コンストラクタ
   * 親がない場合
   */
  State(World initialWorld) {
    this.parent = null;
    this.world = initialWorld;
  }

  /* 親がある場合 */
  State(State parent, World child) {
    this.parent = parent;
    this.world = child;
  }

  /* 文字列化した場合の書式設定 */
  public String toString() {
    return this.world.toString();
  }

  /* hash codeが同じ場合その状態は同値 */
  public boolean equals(State s) {
    return this.world.hashCode() == s.world.hashCode();
  }
}

public class DLSSolver {
  World world;
  int depth;
  /* 訪れたノードの数 */
  long visited = 0;
  /* オープンリストの最大長 */
  long maxLen = 0;

  /*
   * 問題を解くメソッド
   * 探索する深さを50とした深さ優先探索
   */
  public void solve(World world) {
    this.world = world;
    var root = new State(this.world);

    long startTime = System.currentTimeMillis();
    var goal = search(root);
    long finishTime = System.currentTimeMillis();

    if (goal != null) {
      printSolution(goal);
      System.out.printf("Time passed: %d\n", finishTime - startTime);
    } else {
      System.out.println("Goal not found within depth: " + this.depth);
    }
  }

  /*
   * 問題を解くメソッド
   * 探索する深さを指定する深さ優先探索
   */

  public void solve(World world, int depth) {
    this.world = world;
    this.depth = depth;
    var root = new State(this.world);

    long startTime = System.currentTimeMillis();
    var goal = search(root);
    long finishTime = System.currentTimeMillis();

    if (goal != null) {
      printSolution(goal);
      System.out.printf("Time passed: %d\n", finishTime - startTime);
    } else {
      System.out.println("Goal not found within depth: " + this.depth);
    }
  }

  /*
   * 次探索すべきノードを探してゲットするメソッド
   * 探索する深さを50とした
   */
  State search(State root) {
    // 深さ制限を50とする
    this.depth = 50;
    List<State> openList = new ArrayList<>();
    openList.add(root);
    if (this.world instanceof MisCanWorld) {
      MisCanWorld misCanWorld = (MisCanWorld) this.world;

      while (openList.size() > 0) {
        /* 先頭ノードの選択 */
        var state = get(openList);
        if (isGoal(state)) {
          return state;
        }
        /* 深さ制限dを超えるノードを展開しない */
        if (misCanWorld.depth(state) < depth) {
          var children = children(state);
          /* 縦型探索 */
          openList = concat(children, openList);
          this.maxLen = Math.max(this.maxLen, openList.size());
        }
      }
    }
    return null;
  }

  /* 深さ制限探索 */
  State search(State root, int depth) {
    List<State> openList = new ArrayList<>();
    openList.add(root);
    if (this.world instanceof MisCanWorld) {
      MisCanWorld misCanWorld = (MisCanWorld) this.world;
      while (openList.size() > 0) {
        var state = get(openList);
        if (isGoal(state)) {
          return state;
        }
        // そのノードの深さが制限以内なら
        if (misCanWorld.depth(state) < depth) {
          var children = children(state);
          openList = concat(children, openList);
          // リストの最大ノード数の更新
          this.maxLen = Math.max(this.maxLen, openList.size());
        }
      }
    }
    return null;
  }

  /*
   * 引数のstateがゴールか判定するメソッド
   * worldインタフェースのゴール判定を用いる
   */
  boolean isGoal(State state) {
    return state.world.isGoal();
  }

  /*
   * listから先頭のノードを獲得するメソッド
   */
  State get(List<State> list) {
    visited++;
    return list.remove(0);
  }

  /**
   * 子ノードを生成する
   * 
   * @param state
   * @return children 子ノードのリスト
   */
  List<State> children(State state) {
    List<State> children = new ArrayList<>();
    for (var action : state.world.actions()) {
      var next = state.world.perform(action);
      if (next != null) {
        var child = new State(state, next);
        children.add(child);
      }
    }
    return children;
  }

  /*
   * リストを結合する
   * 第1引数が前, 第2引数が後ろに結合される
   */
  List<State> concat(List<State> frontList, List<State> backList) {
    List<State> list = new ArrayList<>();
    list.addAll(frontList);
    list.addAll(backList);
    return list;
  }

  /*
   * 解を出力する
   * 右から左に状態が出力される
   */
  void printSolution(State goal) {
    while (goal != null) {
      System.out.print(goal + " <- ");
      goal = goal.parent;
    }
    System.out.println("start");
    System.out.printf("visited: %d, max length: %d\n", this.visited, this.maxLen);
  }

}
