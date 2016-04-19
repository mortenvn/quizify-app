package net.quizifyapp.quizifyapp;

public interface APIObjectResponseListener<T> {
    public void getResult(T error, T result);
}
