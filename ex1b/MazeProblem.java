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
  Map<String, List<String>> map = Map.of(
      "A", List.of("B", "C", "D"),
      "B", List.of("E", "F"),
      "C", List.of("G", "H"),
      "D", List.of("I", "J"));
  String current;

  MazeWorld(String current) {
    this.current = current;
  }

  String root() {
    return "A";
  }

  String goal() {
    return "E";
  }

  public String toString() {
    return this.current;
  }

  public boolean isGoal() {
    return this.current.equals(goal());
  }

  public boolean isValid() {
    return true;
  }

  public List<Action> actions() {
    List<Action> actions = new ArrayList<>();
    var nexts = this.map.getOrDefault(current, Collections.emptyList());
    for (var next : nexts) {
      actions.add(new MazeAction(next));
    }
    return actions;
  }

  public World perform(Action action) {
    if (action instanceof MazeAction) {
      var a = (MazeAction) action;
      return new MazeWorld(a.next);
    }
    return null;
  }
}