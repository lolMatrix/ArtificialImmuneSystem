package com.company.AIS;

import com.company.population.Population;

import java.util.function.Function;

public class ArtificialImmuneSystem {

    private Population p;
    private Integer maxIterations;

    private int betta = 10;

    public ArtificialImmuneSystem(Function<Double[], Double> f, Double[] x0, int maxPopulation, double[][] intervals, double r, int d, int maxIterations) {
        this.maxIterations = maxIterations;
        p = new Population(f, x0, d, intervals, r, maxPopulation);
    }

    public Double[] start(){
        startWithMaxIterations();
        return p.getResult();
    }

    private void startWithMaxIterations() {
        for (int i = 0; i < maxIterations; i++) {
            p.clone(betta);
            p = p.getNewPopulation();
        }
    }

    public void setBetta(int betta) {
        this.betta = betta;
    }
}
