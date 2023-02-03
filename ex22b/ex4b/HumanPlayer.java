package ex4b;

import java.util.Scanner;

import ex4a.*;

public class HumanPlayer extends Player {
  Eval eval;
  Move move;

  public HumanPlayer(Eval eval) {
    super("HumanPlayer");
    this.eval = eval;
  }

  @Override
  public Move think(State state) {
    /*
     * 現在の状態を表示
     * 入力(0 ~ 8)
     * moveインスタンスの作成
     */
    System.out.printf("Enter a number from 0 to 8: ");
    Scanner stdIn = new Scanner(System.in);
    int index;
    while (true) {
      index = stdIn.nextInt();
      if (index < 0 || index > 8) {
        System.out.println("Enter a number from 0 to 8: ");
        continue;
      }
      if (state.board[index] != 0) {
        System.out.println("There is already a piece, so enter another one: ");
        continue;
      }
      break;
    }
    Move move = new Move(index, this.color);
    return move;
  }

}