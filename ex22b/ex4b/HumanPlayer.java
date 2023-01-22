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
    // if (state.board[index] != 0) {
    // throw new Error("Invalid Input Error");
    // }
    int index;
    while (true) {
      index = stdIn.nextInt();
      if (state.board[index] == 0) {
        break;
      }
      System.out.println("There is already a piece, so enter another one: ");
    }
    Move move = new Move(index, this.color);

    return move;

  }

}