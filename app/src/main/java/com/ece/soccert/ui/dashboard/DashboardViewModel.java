package com.ece.soccert.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mScores;
    private MutableLiveData<String> mT1;
    private MutableLiveData<String> mT2;
    private MutableLiveData<String> mStrike;
    private MutableLiveData<String> mFault;
    private MutableLiveData<String> mYellow;
    private MutableLiveData<String> mRed;


    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
        mScores = new MutableLiveData<>();
        mScores.setValue("0 - 0");
        mT1 = new MutableLiveData<>();
        mT2 = new MutableLiveData<>();
        mYellow = new MutableLiveData<>();
        mYellow.setValue("0 - 0");
        mRed = new MutableLiveData<>();
        mRed.setValue("0 - 0");
        mStrike = new MutableLiveData<>();
        mStrike.setValue("0 - 0");
        mFault = new MutableLiveData<>();
        mFault.setValue("0 - 0");
    }

    public void setTeams(String[] team) {
        this.mT1.setValue(team[0]);
        this.mT2.setValue(team[1]);
    }

    public MutableLiveData<String> getStrike() {
        return mStrike;
    }

    public void setStrike(int[] mStrike) {
        this.mStrike.setValue(String.valueOf(mStrike[0]).concat(" - " + mStrike[1]));
    }

    public MutableLiveData<String> getFault() {
        return mFault;
    }

    public void setFault(int[] mFault) {
        this.mFault.setValue(String.valueOf(mFault[0]).concat(" - " + mFault[1]));
    }

    public MutableLiveData<String> getYellow() {
        return mYellow;
    }

    public void setYellow(int[] mYellow) {
        this.mYellow.setValue(String.valueOf(mYellow[0]).concat(" - " + mYellow[1]));
    }

    public MutableLiveData<String> getRed() {
        return mRed;
    }

    public void setRed(int[] mRed) {
        this.mRed.setValue(String.valueOf(mRed[0]).concat(" - " + mRed[1]));
    }

    public MutableLiveData<String> getmT1() {
        return mT1;
    }

    public MutableLiveData<String> getmT2() {
        return mT2;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getScores(){return mScores;}

    public void setScores(int[] s) {
        mScores.setValue(String.valueOf(s[0]).concat(" - "+ s[1]));
    }
}