package ex2a;

import java.util.*;

class MisCanAction implements Action {
  int missionary;
  int cannibal;
  int boat;
  static List<Action> allActions = List.of(
      new MisCanAction(-2, 0, -1),
      new MisCanAction(-1, -1, -1),
      new MisCanAction(0, -2, -1),
      new MisCanAction(-1, 0, -1),
      new MisCanAction(0, -1, -1),
      new MisCanAction(+2, 0, +1),
      new MisCanAction(+1, +1, +1),
      new MisCanAction(0, +2, +1),
      new MisCanAction(+1, 0, +1),
      new MisCanAction(0, +1, +1));

  MisCanAction(int missionary, int cannibal, int boat) {
    this.missionary = missionary;
    this.cannibal = cannibal;
    this.boat = boat;
  }

  public float cost() {
    return 1;
  }
}

class MisCanWorld implements World {
  int missionary = 3;
  int cannibal = 3;
  int boat = 1;

  public String toString() {
    return String.format("(%d, %d, %d)", this.missionary, this.cannibal, this.boat);
  }

  public MisCanWorld clone() {
    var other = new MisCanWorld();
    other.missionary = this.missionary;
    other.cannibal = this.cannibal;
    other.boat = this.boat;
    return other;
  }

  public float h() {
    return this.missionary;
  }

  public boolean isGoal() {
    return this.missionary == 0 && this.cannibal == 0;
  }

  public List<Action> actions() {
    return MisCanAction.allActions;
  }

  public World perform(Action action) {
    if (action instanceof MisCanAction) {
      var a = (MisCanAction) action;
      var newWorld = clone();
      newWorld.missionary += a.missionary;
      newWorld.cannibal += a.cannibal;
      newWorld.boat += a.boat;

      return newWorld.isValid() ? newWorld : null;
    }
    return null;
  }

  boolean isValid() {
    if (this.missionary < 0 || this.missionary > 3)
      return false;
    if (this.cannibal < 0 || this.cannibal > 3)
      return false;
    if (this.boat < 0 || this.boat > 1)
      return false;
    if (this.missionary > 0 && this.missionary < this.cannibal)
      return false;
    if (3 - this.missionary > 0 && 3 - this.missionary < 3 - this.cannibal)
      return false;
    return true;
  }
}