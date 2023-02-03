package ex4a;

import static ex4a.State.*;

public class Move {
  public int index;
  public int color;

  public Move(int index, int color) {
    this.index = index;
    this.color = color;
  }

  public String toString() {
    return indexToString(this.index) + State.colorToChar(this.color);
  }

  // 色を反転させたものを表示
  public Move flipped() {
    return new Move(this.index, -this.color);
  }

  // インデックスを文字化
  public static String indexToString(int index) {
    return colToString(index % SIZE) + rowToString(index / SIZE);
  }

  // 列を文字列に
  public static String colToString(int col) {
    return Character.toString('a' + col);
  }

  // 行を文字列に
  public static String rowToString(int row) {
    return Character.toString('1' + row);
  }
}