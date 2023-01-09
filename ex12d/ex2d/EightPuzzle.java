package ex2d;

import java.util.*;

class EightPuzzleAction implements Action {
  /* タイルの並び順 */
  String actionKind;

  static List<Action> allActions = List.of(
      new EightPuzzleAction("UP"),
      new EightPuzzleAction("RIGHT"),
      new EightPuzzleAction("DOWN"),
      new EightPuzzleAction("LEFT"));

  public void swap(int[] order, int positionOfZero) {
    int t;
    switch (actionKind) {
      case "UP":
        t = order[positionOfZero];
        order[positionOfZero] = order[positionOfZero - 3];
        order[positionOfZero - 3] = t;
        break;
      case "RIGHT":
        t = order[positionOfZero];
        order[positionOfZero] = order[positionOfZero + 1];
        order[positionOfZero + 1] = t;
        break;
      case "DOWN":
        t = order[positionOfZero];
        order[positionOfZero] = order[positionOfZero + 3];
        order[positionOfZero + 3] = t;
        break;
      case "LEFT":
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

  public float cost() {
    return 1;
  }
}

class EightPuzzleWorld implements World {
  int order[] = new int[9];

  public EightPuzzleWorld(int order[]) {
    this.order = order.clone();
  }

  public String toString() {
    return String.format(
        "%d, %d, %d\n%d, %d, %d\n%d, %d, %d",
        order[0], order[1], order[2], order[3], order[4],
        order[5], order[6], order[7], order[8]);
  };

  public float h() {
    int count = 0;
    for (int i = 0; i < 8; i++) {
      if (order[i] == i + 1) {
        count++;
      }
    }
    if (order[8] == 0) {
      count++;
    }
    return 9 - count;
  }

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

  public World perform(Action action) {
    if (!(action instanceof EightPuzzleAction)) {
      return null;
    }
    var a = (EightPuzzleAction) action;
    var newWorld = new EightPuzzleWorld(order.clone());

    int indexOfZero = whereIsZero();
    if (!isValid(a.actionKind, indexOfZero)) {
      return null;
    }
    a.swap(newWorld.order, indexOfZero);
    return newWorld;
  }

  public boolean isValid(String kind, int indexOfZero) {
    switch (kind) {
      case "UP":
        if (indexOfZero >= 0 && indexOfZero <= 2) {
          return false;
        }
        break;
      case "RIGHT":
        if (indexOfZero % 3 == 2) {
          return false;
        }
        break;
      case "DOWN":
        if (indexOfZero >= 6 && indexOfZero <= 8) {
          return false;
        }
        break;
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

}