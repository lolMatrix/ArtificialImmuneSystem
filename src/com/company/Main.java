package com.company;

import com.company.AIS.ArtificialImmuneSystem;
import com.company.population.Population;

import java.util.Arrays;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        Function<Double[], Double> f = arr -> {
            double x = arr[0];
            return Math.pow(x - 1, 2);
        };
        ArtificialImmuneSystem artificialImmuneSystem = new ArtificialImmuneSystem(f, new Double[]{1d},
                100, -2d, 5d, 1, 3, 15);
        artificialImmuneSystem.setBetta(100);

        System.out.println(Arrays.toString(artificialImmuneSystem.start()));
    }
}
