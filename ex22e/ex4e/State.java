package ex4e;

import java.util.*;

public class State extends ex4a.State {
  @Override
  public boolean equals(Object obj) {
    return this.hashCode() == obj.hashCode();
  }

  @Override
  public int hashCode() {
    int min = Arrays.hashCode(this.board);
    CoodinateTransformer[] TransformerArrays = {
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
         * そのインデックスから90度回転したものが
         * (x, y) = (v / 3 - 1, v % 3 - 1)
         */
        (v) -> new int[] { v / 3 - 1, v % 3 - 1 },
        // 180度回転
        (v) -> new int[] { 1 - v % 3, v / 3 - 1 },
        // 270度回転
        (v) -> new int[] { 1 - v / 3, 1 - v % 3 },
        // x軸対称移動
        (v) -> new int[] { 1 - v % 3, 1 - v / 3 },
        // y軸対称移動
        (v) -> new int[] { v % 3 - 1, v / 3 - 1 },
    };
    for (CoodinateTransformer t : TransformerArrays) {
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
    int[] res = original.clone();
    Arrays.setAll(res, (v) -> {
      int[] coodinate = t.transform(v);
      int x = coodinate[0];
      int y = coodinate[1];
      // 座標から配列のインデックスに逆変換
      // x = v % 3 - 1
      // y = -(v / 3 - 1)
      // よりv = 3(1 - y) + x + 1 = 4 - 3y + x
      return 4 - 3 * y + x;
    });
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