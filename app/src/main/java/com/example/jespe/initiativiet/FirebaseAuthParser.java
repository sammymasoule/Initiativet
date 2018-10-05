package com.example.jespe.initiativiet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

/**
 * Created by HP on 16-01-2018.
 */

public class FirebaseAuthParser implements Parcelable{


    //Kilde http://sohailaziz05.blogspot.dk/2012/04/passing-custom-objects-between-android.html

    private FirebaseAuth auth;

    public FirebaseAuthParser(FirebaseAuth auth) {
        this.auth = auth;
    }

    protected FirebaseAuthParser(Parcel in) {
    }

    public static final Creator<FirebaseAuthParser> CREATOR = new Creator<FirebaseAuthParser>() {
        @Override
        public FirebaseAuthParser createFromParcel(Parcel in) {
            return new FirebaseAuthParser(in);
        }

        @Override
        public FirebaseAuthParser[] newArray(int size) {
            return new FirebaseAuthParser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
