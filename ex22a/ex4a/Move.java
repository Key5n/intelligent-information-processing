package ex4a;

import static ex4a.State.*;

public class Move {
  int index;
  int color;

  public Move(int index, int color) {
    this.index = index;
    this.color = color;
  }

  public String toString() {
    return indexToString(this.index) + State.colorToChar(this.color);
  }

  public Move flipped() {
    return new Move(this.index, -this.color);
  }

  public static String indexToString(int index) {
    return colToString(index % SIZE) + rowToString(index / SIZE);
  }

  public static String colToString(int col) {
    return Character.toString('a' + col);
  }

  public static String rowToString(int row) {
    return Character.toString('1' + row);
  }
}