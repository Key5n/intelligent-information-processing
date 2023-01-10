package ex2d;

import java.util.*;

class EightPuzzleAction implements Action {
  /* タイルの並び順 */
  String actionKind;

  // ０を上、右、下、と入れ替える
  static List<Action> allActions = List.of(
      new EightPuzzleAction("UP"),
      new EightPuzzleAction("RIGHT"),
      new EightPuzzleAction("DOWN"),
      new EightPuzzleAction("LEFT"));

  public void swap(int[] order, int positionOfZero) {
    int t;
    switch (actionKind) {
      // 上と入れ替える
      // 上はのインデックスは-3
      case "UP":
        t = order[positionOfZero];
        order[positionOfZero] = order[positionOfZero - 3];
        order[positionOfZero - 3] = t;
        break;
      case "RIGHT":
        // 右と入れ替える
        // 右のインデックスは+1
        t = order[positionOfZero];
        order[positionOfZero] = order[positionOfZero + 1];
        order[positionOfZero + 1] = t;
        break;
      case "DOWN":
        // 下と入れ替える
        // 下のインデックスは+3
        t = order[positionOfZero];
        order[positionOfZero] = order[positionOfZero + 3];
        order[positionOfZero + 3] = t;
        break;
      case "LEFT":
        // 左と入れ替える
        // 左のインデックスは-1
        t = order[positionOfZero];
        order[positionOfZero] = order[positionOfZero - 1];
        order[positionOfZero - 1] = t;
        break;
      default:
        throw new Error("Invalid action");
    }
  }

  public EightPuzzleAction(String actionKind) {
    this.actionKind = actionKind;
  }

  // 入れ替えコストは入れ替えた数の1
  public float cost() {
    return 1;
  }

}

class EightPuzzleWorld implements World {
  int order[] = new int[9];
  List<State> closedList = new ArrayList<>();

  public EightPuzzleWorld(int order[]) {
    this.order = order.clone();
  }

  public String toString() {
    return String.format(
        "%d, %d, %d\n%d, %d, %d\n%d, %d, %d",
        order[0], order[1], order[2], order[3], order[4],
        order[5], order[6], order[7], order[8]);
  };

  // 評価関数h1
  // パネルと目標状態と異なるパネルの枚数
  // public float h() {
  // int count = 0;
  // for (int i = 0; i < 8; i++) {
  // if (order[i] == i + 1) {
  // count++;
  // }
  // }
  // if (order[8] == 0) {
  // count++;
  // }
  // return 9 - count;
  // }

  // 評価関数h2
  // マンハッタン距離
  public float h() {
    int res = 0;
    // 現在のx座標, y座標, 目標の位置のx座標, y座標
    int x_current, y_current, x_target, y_target;
    int manhattan = 0;
    for (int i = 0; i < 9; i++) {
      x_current = i % 3;
      y_current = i / 3;
      if (order[i] == 0) {
        x_target = 2;
        y_target = 2;
      } else {
        x_target = (order[i] - 1) % 3;
        y_target = (order[i] - 1) / 3;
      }
      manhattan = Math.abs((x_target - x_current)) + Math.abs((y_target -
          y_current));
      res += manhattan;
    }
    return res;
  }

  // 評価関数h3
  // マンハッタン距離の二乗和
  // public float h() {
  // int res = 0;
  // 現在のx座標, y座標, 目標の位置のx座標, y座標
  // int x_current, y_current, x_target, y_target;
  // int manhattan = 0;
  // for (int i = 0; i < 9; i++) {
  // x_current = i % 3;
  // y_current = i / 3;
  // if (order[i] == 0) {
  // x_target = 2;
  // y_target = 2;
  // } else {
  // x_target = (order[i] - 1) % 3;
  // y_target = (order[i] - 1) / 3;
  // }
  // manhattan = Math.abs((x_target - x_current)) + Math.abs((y_target -
  // y_current));
  // res += manhattan * manhattan;
  // }
  // return res;
  // }

  // パネルがすべて同じかどうか
  public boolean isGoal() {
    for (int i = 0; i < 8; i++) {
      if (order[i] != i + 1) {
        return false;
      }
    }
    if (order[8] != 0) {
      return false;
    }

    return true;
  }

  public List<Action> actions() {
    return EightPuzzleAction.allActions;
  }

  // actionをもとに処理する
  public World perform(Action action) {
    if (!(action instanceof EightPuzzleAction)) {
      return null;
    }
    var a = (EightPuzzleAction) action;
    var newWorld = new EightPuzzleWorld(order.clone());

    // 0の位置のインデックスを取得
    int indexOfZero = whereIsZero();
    // 0の位置に大してそのアクションは行えるかどうか
    // 例えば0, 1, 2の位置にある0に対して上と入れ替えはできない
    if (!isValid(a.actionKind, indexOfZero)) {
      return null;
    }
    // aのインスタンスフィールドactionKindを元にindexOfZeroの位置と入れ替える
    a.swap(newWorld.order, indexOfZero);
    return newWorld;
  }

  public boolean isValid(String kind, int indexOfZero) {
    switch (kind) {
      // 0, 1, 2の場合上と入れ替えはできない
      case "UP":
        if (indexOfZero >= 0 && indexOfZero <= 2) {
          return false;
        }
        break;
      // 2, 5, 8の場合右と入れ替えはできない
      case "RIGHT":
        if (indexOfZero % 3 == 2) {
          return false;
        }
        break;
      // 6, 7, 8の場合下と入れ替えはできない
      case "DOWN":
        if (indexOfZero >= 6 && indexOfZero <= 8) {
          return false;
        }
        break;
      // 0, 3, 6の場合左と入れ替えはできない
      case "LEFT":
        if (indexOfZero % 3 == 0) {
          return false;
        }
        break;
      default:
        throw new Error("Invalid action");
    }
    return true;
  }

  // 0のインデックスを調べる
  private int whereIsZero() {
    int indexOfZero = 0;
    for (int i = 0; i < 9; i++) {
      if (order[i] == 0) {
        indexOfZero = i;
        break;
      }
    }
    return indexOfZero;
  }

  public boolean equals(World world) {
    if (world instanceof EightPuzzleWorld) {

      return Arrays.equals(this.order, ((EightPuzzleWorld) world).order);
    }
    return false;
  }

}