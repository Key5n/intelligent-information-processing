package ex1e;

import java.util.*;

// 30m
public class MisCanProblem {
  public static void main(String[] args) {
    // System.out.println("DLS solver depth: 10");
    // var dlsSolver1 = new DLSSolver();
    // dlsSolver1.solve(new MisCanWorld(), 10);

    // System.out.println("DLS solver depth: 30");
    // var dlsSolver2 = new DLSSolver();
    // dlsSolver2.solve(new MisCanWorld(), 30);

    // System.out.println("IDS solver");
    // var idsSolver = new IDSSolver();
    // idsSolver.solve(new MisCanWorld());

    System.out.println("CLS solver");
    var clsSolver = new CLSSolver();
    clsSolver.solve(new MisCanWorld());
  }
}

class MisCanAction implements Action {
  /* 左岸の宣教師の数 */
  int missionary;
  /* 左岸の人食い人の数 */
  int cannival;
  /* 左岸のボートの数 */
  int boat;

  static List<Action> allActions = List.of(
      new MisCanAction(-2, 0, -1),
      new MisCanAction(-1, -1, -1),
      new MisCanAction(0, -2, -1),
      new MisCanAction(-1, 0, -1),
      new MisCanAction(0, -1, -1),
      new MisCanAction(2, 0, 1),
      new MisCanAction(1, 1, 1),
      new MisCanAction(0, 2, 1),
      new MisCanAction(1, 0, 1),
      new MisCanAction(0, 1, 1));

  MisCanAction(int missionary, int cannival, int boat) {
    this.missionary = missionary;
    this.cannival = cannival;
    this.boat = boat;
  }
}

class MisCanWorld implements World {
  int missionary = 3;
  int cannival = 3;
  int boat = 1;

  public String toString() {
    return String.format("(%s, %s, %s)", this.missionary, this.cannival, this.boat);
  }

  /* MisCanWorldの複製 */
  public MisCanWorld clone() {
    var other = new MisCanWorld();
    other.missionary = this.missionary;
    other.cannival = this.cannival;
    other.boat = this.boat;
    return other;
  }

  /* ゴールの定義 */
  public boolean isGoal() {
    return this.missionary == 0 && this.cannival == 0;
  }

  /* アクションの定義 */
  public List<Action> actions() {
    return MisCanAction.allActions;
  }

  // actionを行う
  public World perform(Action action) {
    if (action instanceof MisCanAction) {
      var a = (MisCanAction) action;
      var newWorld = clone();
      newWorld.missionary += a.missionary;
      newWorld.cannival += a.cannival;
      newWorld.boat += a.boat;
      return newWorld.isValid() ? newWorld : null;
    }
    return null;
  }

  /* その状態が有効かどうか */
  boolean isValid() {
    if (this.missionary < 0 || this.missionary > 3)
      return false;
    if (this.cannival < 0 || this.cannival > 3)
      return false;
    if (this.boat < 0 || this.boat > 1)
      return false;
    if (this.missionary > 0 && this.missionary < this.cannival)
      return false;
    if ((3 - this.missionary) > 0 && (3 - this.missionary) < (3 - this.cannival))
      return false;
    return true;
  }

  // 状態にユニークな値を与える
  public int hashCode() {
    return missionary * 100 + cannival * 10 + boat;
  }
}