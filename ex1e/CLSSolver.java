package ex1e;
// 12m 43s

import java.util.*;

interface Action {

}

interface World extends Cloneable {
  boolean isGoal();

  List<Action> actions();

  World perform(Action action);

  int hashCode();
}

class State {
  State parent;
  World world;

  State(World initialWorld) {
    this.parent = null;
    this.world = initialWorld;

  }

  State(State parent, World child) {
    this.parent = parent;
    this.world = child;
  }

  public String toString() {
    return this.world.toString();
  }

  boolean equals(State s) {
    return this.world.hashCode() == s.world.hashCode();
  }
}

public class CLSSolver {
  World world;
  int numOfVisitedNodes = 0;
  int maxLengthOfOpenList = 0;

  public void solve(World world) {
    this.world = world;
    var root = new State(this.world);

    long startTime = System.currentTimeMillis();
    var goal = search(root);
    long finishTime = System.currentTimeMillis();
    System.out.printf("Time passed: %5d\n", finishTime - startTime);

    if (goal != null)
      printSolution(goal);
  }

  State search(State root) {
    List<State> openList = new ArrayList<>();
    List<State> closedList = new ArrayList<>();
    openList.add(root);

    while (openList.size() > 0) {
      var state = get(openList);
      if (isGoal(state)) {
        return state;
      }
      closedList.add(state);
      var children = children(state);
      for (State childState : children) {
        for (State closedListState : closedList) {
          if (childState.equals(closedListState)) {
            children.remove(childState);
          }
        }

        for (State openListState : openList) {
          if (childState.equals(openListState)) {
            children.remove(childState);
          }
        }
      }
      openList = concat(openList, children);
    }
    return null;
  }

  boolean isGoal(State state) {
    return state.world.isGoal();
  }

  State get(List<State> list) {
    numOfVisitedNodes++;
    return list.remove(0);
  }

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

  List<State> concat(List<State> frontList, List<State> backList) {
    List<State> list = new ArrayList<>();
    list.addAll(frontList);
    list.addAll(backList);
    this.maxLengthOfOpenList = maxLengthOfOpenList < list.size() ? list.size() : this.maxLengthOfOpenList;

    return list;
  }

  void printSolution(State goal) {
    System.out.println("CLS Solver");
    while (goal != null) {
      System.out.print(goal + " <- ");
      goal = goal.parent;
    }
    System.out.println("start");
    System.out.printf("Visited Nodes: %d\n", numOfVisitedNodes);
    System.out.printf("The maximum length of the open list: %d\n", maxLengthOfOpenList);
  }

}
