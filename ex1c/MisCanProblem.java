package ex1c;

import java.util.*;

// 30m
public class MisCanProblem {
  public static void main(String[] args) {
    var solver = new Solver();
    solver.solve(new MisCanWorld());
  }
}

class MisCanAction implements Action {
  int missionary;
  int cannival;
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

  public MisCanWorld clone() {
    var other = new MisCanWorld();
    other.missionary = this.missionary;
    other.cannival = this.cannival;
    other.boat = this.boat;
    return other;
  }

  public boolean isGoal() {
    return this.missionary == 0 && this.cannival == 0;
  }

  public List<Action> actions() {
    return MisCanAction.allActions;
  }

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
}