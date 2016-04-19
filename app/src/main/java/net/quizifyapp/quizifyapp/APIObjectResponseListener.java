package net.quizifyapp.quizifyapp;

public interface APIObjectResponseListener<T, Z> {
    public void getResult(T error, Z result);
}
