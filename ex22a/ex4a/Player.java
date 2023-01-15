package ex4a;

import static ex4a.State.*;

public class Player {
  String name;
  int color;

  public Player(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }

  public Move think(State state) {
    if (this.color == WHITE)
      state = state.flipped();
    Move move = search(state);
    move.color = this.color;
    return move;
  }

  Move search(State state) {
    return null;
  }
}