package com.armoomragames.denketa.IntroAuxilaries;

public class DModelDictionary {
    public DModelDictionary(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public DModelDictionary() {
    }
    String word;
    String meaning;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
