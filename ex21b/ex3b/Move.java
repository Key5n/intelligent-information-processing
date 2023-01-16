package ex3b;

public class Move {
  // 取り除く石の数
  int removal;
  public int color;

  public Move(int removal, int color) {
    this.removal = removal;
    this.color = color;
  }

  public String toString() {
    char player = State.colorToChar(this.color);
    return String.format("%c takes %d stone(s).", player, this.removal);
  }
}