package net.quizifyapp.quizifyapp;

import java.util.HashMap;

public interface APIListener<T> {
    public void getResult(T object);
//    public void getGamesResult(String error, HashMap games);
}