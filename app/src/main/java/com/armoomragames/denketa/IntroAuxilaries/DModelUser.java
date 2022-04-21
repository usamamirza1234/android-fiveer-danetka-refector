package com.armoomragames.denketa.IntroAuxilaries;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DModelUser {

    public int User_Id;
    public String Name;
    public String Nationality;
    public String Phone;
    public String Email;
    public String Gender;
    public String DOB;
    public String Iqama_Id;
    public String Iqama_Expiry;
    public String DanetkaTotal;
    public String DanetkaPlayed;
    public String Image;
    public String Role;
    public int Status;
    public int Is_Under_Review;
    public String GameCredits;
    public String DanetkaPurchased;
    public int Active;
    public boolean isPushOn;
    private boolean isLoggedIn;
    public boolean isAdmin;
    public String Authorization;
    public String Password_Token;

    public DModelUser(int user_Id, String name, String nationality, String phone, String email, String gender, String DOB, String iqama_Id, String iqama_Expiry, String danetkaTotal, String danetkaPlayed, String image, String role, int status, int is_Under_Review, String gameCredits, String DanetkaPurchased, int active, boolean isPushOn, boolean isLoggedIn, boolean isAdmin, String authorization, String password_Token) {
        User_Id = user_Id;
        Name = name;
        Nationality = nationality;
        Phone = phone;
        Email = email;
        Gender = gender;
        this.DOB = DOB;
        Iqama_Id = iqama_Id;
        Iqama_Expiry = iqama_Expiry;
        DanetkaTotal = danetkaTotal;
        DanetkaPlayed = danetkaPlayed;
        Image = image;
        Role = role;
        Status = status;
        Is_Under_Review = is_Under_Review;
        GameCredits = gameCredits;
        this.DanetkaPurchased = DanetkaPurchased;
        Active = active;
        this.isPushOn = isPushOn;
        this.isLoggedIn = isLoggedIn;
        this.isAdmin = isAdmin;
        Authorization = authorization;
        Password_Token = password_Token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public DModelUser() {
        this.User_Id = 0;
        this.Name = "";
        this.Phone = "";
        this.Email = "";
        this.Gender = "";
        this.DOB = "";
        this.Iqama_Id = "";
        this.Iqama_Expiry = "";
        this.DanetkaTotal = "";
        this.DanetkaPlayed = "";
        this.Image = "";
        this.Role = "";
        this.Status = 0;
        this.Is_Under_Review = 0;
        this.GameCredits = "";
        this.Active = 0;
        this.isPushOn = true;
        this.isLoggedIn = false;
        this.isAdmin = false;
        this.Authorization = "";
        this.Password_Token = "";
        this.DanetkaPurchased = "";
    }

    public String getDanetkaPurchased() {
        return DanetkaPurchased;
    }

    public void setDanetkaPurchased(String danetkaPurchased) {
        this.DanetkaPurchased = danetkaPurchased;
    }

    public int getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(int user_Id) {
        User_Id = user_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getIqama_Id() {
        return Iqama_Id;
    }

    public void setIqama_Id(String iqama_Id) {
        Iqama_Id = iqama_Id;
    }

    public String getIqama_Expiry() {
        return Iqama_Expiry;
    }

    public void setIqama_Expiry(String iqama_Expiry) {
        Iqama_Expiry = iqama_Expiry;
    }

    public String getDanetkaTotal() {
        return DanetkaTotal;
    }

    public void setDanetkaTotal(String danetkaTotal) {
        DanetkaTotal = danetkaTotal;
    }

    public String getDanetkaPlayed() {
        return DanetkaPlayed;
    }

    public void setDanetkaPlayed(String danetkaPlayed) {
        DanetkaPlayed = danetkaPlayed;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getIs_Under_Review() {
        return Is_Under_Review;
    }

    public void setIs_Under_Review(int is_Under_Review) {
        Is_Under_Review = is_Under_Review;
    }

    public String getGameCredits() {
        return GameCredits;
    }

    public void setGameCredits(String gameCredits) {
        GameCredits = gameCredits;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public boolean isPushOn() {
        return isPushOn;
    }

    public void setPushOn(boolean pushOn) {
        isPushOn = pushOn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    public String getPassword_Token() {
        return Password_Token;
    }

    public void setPassword_Token(String password_Token) {
        Password_Token = password_Token;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }
}