package ex3b;

import java.util.*;

public class State implements Cloneable {
  public final static int BLACK = 1;
  public final static int WHITE = -1;
  public final static int NONE = 0;

  // 石の数
  int stones;
  // 色
  public int color = BLACK;
  Move lastMove;

  public State(int stones) {
    this.stones = stones;
  }

  // その状態をコピーする
  public State clone() {
    State other = new State(this.stones);
    other.color = this.color;
    other.lastMove = this.lastMove;
    return other;
  }

  public String toString() {
    return String.format("%d stone(s)", this.stones);
  }

  // 色(を示す整数値)から対応する文字を取得する
  public static char colorToChar(int color) {
    return Map.of(1, 'o', -1, 'x').getOrDefault(color, ' ');
  }

  // 石の数が0個ならゴール
  public boolean isGoal() {
    return this.stones == 0;
  }

  // 行うことができる行動のリストを返す
  public List<Move> getMoves() {
    var moves = new ArrayList<Move>();
    // Math.min(3, this.stones)は取り除ける石の数を示す
    for (int i = 1; i <= Math.min(3, this.stones); i++) {
      moves.add(new Move(i, this.color));
    }
    return moves;
  }

  public State perform(Move move) {
    var next = this.clone();
    // 石を取り除く
    next.stones -= move.removal;
    // 色をチェンジ(黒なら白に、白なら黒に)
    next.color = -move.color;
    // 最後の行動を保持する
    next.lastMove = move;
    return next;
  }

  // ゲーム終了時にその状態の色を返す
  public int winner() {
    return isGoal() ? this.color : NONE;
  }
}