package edu.usf.cutr.open311client;

import java.util.Random;

public class TestUtils {

  public static LatLong getRandomLocation() {
    Random rnd = new Random(System.currentTimeMillis());
    int latRandom = rnd.nextInt(900) + 100;
    int longRandom = rnd.nextInt(900) + 100;

    String lat = "27.8" + latRandom;
    String lon = "-82.7" + longRandom;

    return new LatLong(Double.parseDouble(lat), Double.parseDouble(lon));
  }

}
