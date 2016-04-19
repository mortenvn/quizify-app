package net.quizifyapp.quizifyapp;

public interface APIAuthenticationResponseListener<T> {
    public void getResult(T object);
}