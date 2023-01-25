package ex4a;

public class Eval {
  public float value(State s1) {
    return -1025 * s1.b3 + 511 * s1.a3 - 63 * s1.b2 + 31 * s1.a2 - 15 * s1.b1 + 7 * s1.a1;
  }
}