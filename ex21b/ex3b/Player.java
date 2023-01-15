package ex3b;

public class Player {
  String name;
  public int color;

  public Player(String name) {
    this.name = name;
  }

  public String toString() {
    // 黒手番か白手番を表示する
    return this.name + (this.color > 0 ? "(black)" : "(white)");
  }

  //
  public Move think(State state) {
    Move move = search(state);
    move.color = this.color;
    return move;
  }

  Move search(State state) {
    return null;
  }
}
