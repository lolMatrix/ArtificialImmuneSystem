package com.company.cell;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class Cell {

    private Double[] state;

    private Double result;

    private Random rnd = new Random();
    /**
     * f(x), where x = {x0, ...., xn}
     */
    private Function<Double[], Double> lambda;
    private double r;
    private final double[][] interval;

    public Cell(Function<Double[], Double> lambda, Double[] x0, double r, double[][] interval) {
        this.lambda = lambda;
        state = x0;
        this.r = r;
        this.interval = interval;
    }

    public void calc() {
        result = lambda.apply(state);
    }

    public void mutate(){
        double nextState = 0;
        for (int i = 0; i < state.length; i++) {
            while (!(nextState >= interval[i][0] && nextState <= interval[i][1])) {
                if (rnd.nextFloat() > 0.5f){
                    nextState = state[i] + r * getRndNumberInInterval(0, interval[i][1] - state[i]);
                } else {
                    nextState = state[i] - r * getRndNumberInInterval(0, state[i] - interval[i][0]);
                }
            }
            state[i] = nextState;
        }
    }

    public Double getResult() {
        return result;
    }

    public Double[] getState() {
        return state;
    }

    public Cell clone() {
        Cell clone = new Cell(lambda, state.clone(), r, interval.clone());
        clone.rnd = rnd;
        return clone;
    }

    private double getRndNumberInInterval(double a, double b){
        double result;
        if (a < b) {
            result = rndInInterval(a, b);
        } else if (b < a) {
            result = rndInInterval(b, a);
        } else {
            result = rndInInterval(a, b + 1);
        }

        return result;
    }

    private double rndInInterval(double a, double b) {
        double val = rnd.nextDouble();
        return a + (val * (b - a));
    }
}
