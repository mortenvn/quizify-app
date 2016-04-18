package net.quizifyapp.quizifyapp;

public interface APIListener<T> {
    public void getResult(T object);
}