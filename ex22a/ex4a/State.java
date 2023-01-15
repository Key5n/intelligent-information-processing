package ex4a;

import java.util.*;

public class State implements Cloneable {
  public final static int BLACK = 1;
  public final static int WHITE = -1;
  public final static int NONE = 0;
  public final static int SIZE = 3;
  public final static int LENGTH = SIZE * SIZE;

  int board[] = new int[LENGTH];
  int color = BLACK;
  Move lastMove;

  int a1, a2, a3, b1, b2, b3;

  public State clone() {
    State other = new State();
    other.board = Arrays.copyOf(this.board, this.board.length);
    other.color = this.color;
    other.lastMove = this.lastMove;
    other.count();
    return other;
  }

  public String toString() {
    StringBuilder buf = new StringBuilder();

    for (int col = 0; col < SIZE; col++) {
      char a = colorToChar(this.board[SIZE * col]);
      char b = colorToChar(this.board[SIZE * col + 1]);
      char c = colorToChar(this.board[SIZE * col + 2]);
      if (col > 0)
        buf.append(String.format("-+-+-\n", a, b, c));
      buf.append(String.format("%c|%c|%c\n", a, b, c));
    }

    buf.append(String.format("o: a3=%d,a2=%d,a1=%d\n", this.a3, this.a2, this.a1));
    buf.append(String.format("x: b3=%d,b2=%d,b1=%d", this.b3, this.b2, this.b1));

    return buf.toString();
  }

  public static char colorToChar(int color) {
    return Map.of(BLACK, 'o', WHITE, 'x').getOrDefault(color, ' ');
  }

  public boolean isGoal() {
    if (winner() != NONE)
      return true;
    for (int i = 0; i < LENGTH; i++)
      if (this.board[i] == NONE)
        return false;
    return true;
  }

  public List<Move> getMoves() {
    var moves = new ArrayList<Move>();
    for (int i = 0; i < LENGTH; i++)
      if (this.board[i] == NONE)
        moves.add(new Move(i, this.color));
    return moves;
  }

  public State perform(Move move) {
    var next = this.clone();
    next.place(move);
    return next;

  }

  public void place(Move move) {
    this.board[move.index] = move.color;
    this.color = -move.color;
    this.lastMove = move;
    count();
  }

  public int winner() {
    if (this.a3 > 0)
      return BLACK;
    if (this.b3 > 0)
      return WHITE;
    return NONE;
  }

  public void count() {
    this.a1 = this.a2 = this.a3 = 0;
    this.b1 = this.b2 = this.b3 = 0;
    count(0, 1, 2);
    count(3, 4, 5);
    count(6, 7, 8);
    count(0, 3, 6);
    count(1, 4, 7);
    count(2, 5, 8);
    count(0, 4, 8);
    count(2, 4, 6);
  }

  public void count(int p, int q, int r) {
    int x = this.board[p], y = this.board[q], z = this.board[r];
    int sum = x + y + z;
    if (sum == 3)
      this.a3 += 1;
    if (sum == -3)
      this.b3 += 1;
    if (x * y * z == 0) {
      if (sum == 2)
        this.a2 += 1;
      if (sum == 1)
        this.a1 += 1;
      if (sum == -2)
        this.b2 += 1;
      if (sum == -1)
        this.b1 += 1;
    }
  }

  public State flipped() {
    var flipped = this.clone();
    for (int i = 0; i < LENGTH; i++)
      flipped.board[i] *= -1;
    flipped.color *= -1;
    flipped.lastMove = this.lastMove.flipped();
    flipped.count();
    return flipped;
  }
}