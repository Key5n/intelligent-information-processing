package ex4e;

import java.util.*;
import ex4a.*;

public class State implements Cloneable {
  public final static int BLACK = 1;
  public final static int WHITE = -1;
  public final static int NONE = 0;
  public final static int SIZE = 3;
  public final static int LENGTH = SIZE * SIZE;

  // 盤面のコマの情報を記憶する配列
  public int board[] = new int[LENGTH];
  public int color = BLACK;
  Move lastMove;

  // a1: 自分の1個のコマの並び数
  // b1: 相手の一個のコマの並び数
  // a2: 自分の2個のコマの並び数
  // b2: 相手の2個のコマの並び数
  // a3: 自分の3個のコマの並び数
  // b3: 相手の3個のコマの並び数
  public int a1, a2, a3, b1, b2, b3;

  // インスタンスのコピー
  public State clone() {
    State other = new State();
    other.board = Arrays.copyOf(this.board, this.board.length);
    other.color = this.color;
    other.lastMove = this.lastMove;
    // a1, a2, ... ,b3までを登録する
    other.count();
    return other;
  }

  public String toString() {
    StringBuilder buf = new StringBuilder();

    for (int col = 0; col < SIZE; col++) {
      // 1列目
      char a = colorToChar(this.board[SIZE * col]);
      // 2列目
      char b = colorToChar(this.board[SIZE * col + 1]);
      // 3列目
      char c = colorToChar(this.board[SIZE * col + 2]);
      if (col > 0)
        buf.append(String.format("-+-+-\n", a, b, c));
      buf.append(String.format("%c|%c|%c\n", a, b, c));
    }

    buf.append(String.format("o: a3=%d,a2=%d,a1=%d\n", this.a3, this.a2, this.a1));
    buf.append(String.format("x: b3=%d,b2=%d,b1=%d", this.b3, this.b2, this.b1));

    return buf.toString();
  }

  // 色の値から文字を抜き出す
  public static char colorToChar(int color) {
    return Map.of(BLACK, 'o', WHITE, 'x').getOrDefault(color, ' ');
  }

  //
  public boolean isGoal() {
    if (winner() != NONE)
      return true;
    for (int i = 0; i < LENGTH; i++)
      if (this.board[i] == NONE)
        return false;
    // すべてのマスが埋まっている場合
    return true;
  }

  // 空きスペースに自分のコマを置いたすべての場合を返す
  public List<Move> getMoves() {
    var moves = new ArrayList<Move>();
    for (int i = 0; i < LENGTH; i++) {
      if (this.board[i] == NONE)
        moves.add(new Move(i, this.color));
    }

    return moves;
  }

  // move通りにコマを配置する
  public State perform(Move move) {
    var next = this.clone();
    next.place(move);
    return next;
  }

  // 盤面(board)にコマをおく
  public void place(Move move) {
    this.board[move.index] = move.color;
    this.color = -move.color;
    this.lastMove = move;
    count();
  }

  public int winner() {
    // 自分の駒が3つ並んでいたら自分の勝利
    if (this.a3 > 0)
      return BLACK;
    // 相手の駒が3つ並んでいたら相手の勝利
    if (this.b3 > 0)
      return WHITE;
    return NONE;
  }

  public void count() {
    this.a1 = this.a2 = this.a3 = 0;
    this.b1 = this.b2 = this.b3 = 0;
    // 上段
    count(0, 1, 2);
    // 中断
    count(3, 4, 5);
    // 下段
    count(6, 7, 8);
    // 左列
    count(0, 3, 6);
    // 中央列
    count(1, 4, 7);
    // 右列
    count(2, 5, 8);
    // 右下斜め
    count(0, 4, 8);
    // 左下斜め
    count(2, 4, 6);
  }

  // p, q, rは縦、横、斜めのいずれかの1列のインデックス
  public void count(int p, int q, int r) {
    int x = this.board[p], y = this.board[q], z = this.board[r];
    int sum = x + y + z;
    // 自分の駒で1列埋まっている場合
    if (sum == 3)
      this.a3 += 1;
    // 相手の駒で1列埋まっている場合
    if (sum == -3)
      this.b3 += 1;
    // xまたはyまたはzが0の場合(すなわち空きスペースがある場合)
    if (x * y * z == 0) {
      // その列に自分の駒が2つ、スペースが1つ以上ある場合
      if (sum == 2)
        this.a2 += 1;
      // その列に自分の駒が1つ、スペースが1つ以上ある場合
      if (sum == 1)
        this.a1 += 1;
      // その列に相手の駒が2つ、スペースが1つ以上ある場合
      if (sum == -2)
        this.b2 += 1;
      // その列に相手の駒が1つ、スペースが1つ以上ある場合
      if (sum == -1)
        this.b1 += 1;
    }
  }

  // 盤面のコマを反転させる
  // つまり相手のコマを自分の駒、自分の駒を相手の駒とする
  public State flipped() {
    var flipped = this.clone();
    for (int i = 0; i < LENGTH; i++)
      flipped.board[i] *= -1;
    flipped.color *= -1;
    flipped.lastMove = this.lastMove.flipped();
    flipped.count();
    return flipped;
  }

  public boolean equals(Object obj) {
    return this.hashCode() == obj.hashCode();
  }

  public int hashCode() {
    List<Integer> a = new LinkedList<>();
    int min = Arrays.hashCode(this.board);
    CoodinateTransformer[] TransformerArrays = {
        // x軸対称移動
        (v) -> new int[] { v % 3 - 1, v / 3 - 1 },
        // 90度回転
        /*
         * |0 1 2|
         * |3 4 5|
         * |6 7 8|
         * を90度回転させ
         * |2 5 8|
         * |1 4 7|
         * |0 3 6|
         * とする。そのため4を原点とした2次元座標と見る。
         * (e.g. 0 -> (-1, 1), 8 -> (1, -1))
         * そのインデックスvから90度回転したものが
         * (x, y) = (v / 3 - 1, v % 3 - 1)
         */
        (v) -> new int[] { v / 3 - 1, v % 3 - 1 },
        // 90度回転させたものをx軸対称移動
        (v) -> new int[] { v / 3 - 1, 1 - v % 3 },
        // 180度回転
        (v) -> new int[] { 1 - v % 3, v / 3 - 1 },
        // 180度回転させたものをx軸対称移動
        (v) -> new int[] { 1 - v % 3, 1 - v / 3 },
        // 270度回転
        (v) -> new int[] { 1 - v / 3, 1 - v % 3 },
        // 270度回転させたものをx軸対称移動
        (v) -> new int[] { 1 - v / 3, v % 3 - 1 },

    };
    for (CoodinateTransformer t : TransformerArrays) {
      a.add(hashCode(this.board, t));
      min = Math.min(min, hashCode(this.board, t));
    }
    // 最小値を返す
    return min;
  }

  /**
   * @param original 要素を移動する前の配列
   * @param t        変換関数
   * @return originalをtによって変換し、それを１次元配列に戻し、その1次元配列のhash値
   */
  public int hashCode(int[] original, CoodinateTransformer t) {
    int[] res = new int[9];
    for (int i = 0; i < res.length; i++) {
      // coodinate: 配列のインデックスiをtにしたがって変換した座標
      int[] coodinate = t.transform(i);
      // iを変換した後のx座標
      int x = coodinate[0];
      // iを変換した後のy座標
      int y = coodinate[1];
      // 変換した座標にもとの値を代入する
      res[4 - 3 * y + x] = original[i];
    }
    return Arrays.hashCode(res);
  }
}

/**
 * {@summary} 配列の値から２次元座標へ変換する関数型インタフェース
 */
@FunctionalInterface
interface CoodinateTransformer {
  /**
   * @param index 配列のインデックス
   * @return 変換した２次元座標 (x, y)
   */
  public int[] transform(int index);
}