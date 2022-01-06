package com.company;

import com.company.AIS.ArtificialImmuneSystem;
import com.company.population.Population;

import java.util.Arrays;
import java.util.function.Function;

import static java.lang.Math.*;

public class Main {

    public static void main(String[] args) {
        Double[] fX = new Double[]{-10d};
        Function<Double[], Double> f = arr -> {
            double x = arr[0];
            return pow(x - 1, 2);
        };

        Double[] sphereX = new Double[]{-100d, -100d};
        Function<Double[], Double> sphere = arr -> {
            double x = arr[0], y = arr[1];
            return pow(x, 2) + pow(y, 2);
        };

        Double[] izomX = new Double[]{-100d, -100d};
        Function<Double[], Double> izom = arr -> {
            double x = arr[0], y = arr[1];
            return -cos(x) * cos(y) * exp(-(pow(x - PI, 2) + pow(y - PI, 2)));
        };

        ArtificialImmuneSystem artificialImmuneSystem = new ArtificialImmuneSystem(sphere, sphereX,
                1000, new double[][]{
                {-100, 100},
                {-100, 100}
        }, 2, 10, 15);
        artificialImmuneSystem.setBetta(5);

        System.out.println("xmin = " + Arrays.toString(artificialImmuneSystem.start()));
    }
}
