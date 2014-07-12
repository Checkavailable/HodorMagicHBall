package bz.bosco.hodormagichball;

import java.util.Random;

/**
 * Created by bzubiaga on 7/3/14.
 */
public class CrystalBall {

    public String[] mAnswers = {
            "hodor1",
            "hodor2",
            "hodor3",
            "hodor4",
            "hodor5",
            "hodor6",
            "hodor7",
            "hodor8",
            "hodor9",
            "hodor10",
            "hodor11",
            "hodor12"};

    public String getAnAnswer(){

        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mAnswers.length);

        return mAnswers[randomNumber];
    }

}
