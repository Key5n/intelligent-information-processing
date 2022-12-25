package ex1a;

/* 10m 38s */
import java.util.*;

public class Maze {
  Map<String, List<String>> map = Map.of("A", List.of("B", "C", "D"), "B", List.of("E", "F"), "C", List.of("G", "H"),
      "D", List.of("I", "J"));

  String root() {
    return "A";
  }

  String goal() {
    return "E";
  }

  public static void main(String[] args) {
    var maze = new Maze();
    maze.solve();
  }

  public void solve() {
    var root = root();
    var goal = search(root);
    if (goal != null) {
      System.out.println("found");
    }
  }

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

  boolean isGoal(String state) {
    return goal().equals(state);
  }

  String get(List<String> list) {
    return list.remove(0);
  }

  List<String> children(String current) {
    return this.map.getOrDefault(current, Collections.emptyList());
  }

  List<String> concat(List<String> frontList, List<String> backList) {
    List<String> list = new ArrayList<>();
    list.addAll(frontList);
    list.addAll(backList);
    return list;
  }

}
