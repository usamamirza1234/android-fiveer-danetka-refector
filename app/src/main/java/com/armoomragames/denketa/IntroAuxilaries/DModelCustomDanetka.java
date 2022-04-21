package com.armoomragames.denketa.IntroAuxilaries;

import java.io.File;

public class DModelCustomDanetka {
    String title;
    String answer;
    File image;
    File answerImage;
    String hint;
    String question;
    String learnMore;
    String masterId;

    public DModelCustomDanetka(String title, String answer, File image, File answerImage, String hint, String question, String learnMore, String masterId) {
        this.title = title;
        this.answer = answer;
        this.image = image;
        this.answerImage = answerImage;
        this.hint = hint;
        this.question = question;
        this.learnMore = learnMore;
        this.masterId = masterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public File getAnswerImage() {
        return answerImage;
    }

    public void setAnswerImage(File answerImage) {
        this.answerImage = answerImage;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLearnMore() {
        return learnMore;
    }

    public void setLearnMore(String learnMore) {
        this.learnMore = learnMore;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }
}
