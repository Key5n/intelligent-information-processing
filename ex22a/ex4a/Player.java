package ex4a;

import static ex4a.State.*;

public class Player {
  String name;
  public int color;

  public Player(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }

  public Move think(State state) {
    // 後手なら盤面を後手目線にする
    // これをしないと後手が先手が有利になるような手をうってしまう
    if (this.color == WHITE)
      state = state.flipped();
    // 探索
    Move move = search(state);
    move.color = this.color;
    return move;
  }

  protected Move search(State state) {
    return null;
  }
}