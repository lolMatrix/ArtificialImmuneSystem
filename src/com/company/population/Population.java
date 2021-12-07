package com.company.population;

import com.company.cell.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Population {

    private List<Cell> population;
    private ArrayList<List<Cell>> clones;

    private int dCount;

    private double a;
    private double b;
    private double r;

    private int cellsCount;
    private double h = 1;

    public Population(Function<Double[], Double> fx, Double[] x0, int dCount, double a, double b, double r, int cellsCount) {
        this.dCount = dCount;
        this.a = a;
        this.b = b;
        this.r = r;
        this.cellsCount = cellsCount;

        System.out.println("Создаю начальную колонию");
        population = createFirstPopulation(fx, x0);
        System.out.println("Вычисляю их значения");
        calcPopulation();
    }

    private List<Cell> createFirstPopulation(Function<Double[], Double> fx, Double[] x0) {
        ArrayList<Cell> cells = new ArrayList<>();
        double end = a;
        h = (b - a) / cellsCount;
        for (int i = 0; i < cellsCount; i++) {
            double[] interval = new double[2];
            interval[0] = end;
            interval[1] = interval[0] + h;
            end = interval[1];
            Cell cell = new Cell(fx, x0.clone(), r, interval);
            cells.add(cell);
        }
        return cells;
    }

    public void clone(int betta) {
        System.out.println("Клонирую");
        ArrayList<List<Cell>> clones = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            int count = betta * population.size() / (i+1);
            ArrayList<Cell> cells = new ArrayList<>();
            for (int j = 0; j < count; j++) {
                cells.add(population.get(i).clone());
            }
            clones.add(cells);
        }
        this.clones = clones;
    }

    public Double[] getResult(){
        System.out.println("Получаю результат");
        Cell cell = findMin(population);
        System.out.println("F(xmin) = " + cell.getResult());
        return cell.getState().clone();
    }

    public Population getNewPopulation() {
        System.out.println("Создаю новую мутацию");
        mutate();
        System.out.println("Вычисляю мутантов");
        calcMutants();

        Cell[] population = this.population.toArray(new Cell[this.population.size()]);

        System.out.println("Нахожу лучших и заменяю");
        for (int i = 0; i < population.length; i++) {
            Cell parent = population[i];
            Cell best = findMin(clones.get(i));
            if (parent.getResult() > best.getResult()) {
                population[i] = best;
            }
        }

        population = Arrays.stream(population).sorted(Comparator.comparingDouble(Cell::getResult)).toArray(Cell[]::new);

        System.out.println("Убираю лишних");
        for (int i = population.length - 1; i >= dCount; i--) {
            population[i].mutate();
        }

        this.population = Arrays.asList(population);
        return this;
    }

    private Cell findMin(List<Cell> cells) {
        Cell best = cells.get(0);
        for (Cell cell : cells) {
            if (cell.getResult() < best.getResult()){
                best = cell;
            }
        }
        return best;
    }

    private void calcMutants() {
        for (List<Cell> cells : clones) {
            for (Cell cell : cells)
                cell.calc();
        }
    }

    private void calcPopulation() {
        for (Cell cell : population)
            cell.calc();
    }

    private void mutate(){
        for (List<Cell> cells : clones) {
            for (Cell cell : cells) {
                cell.mutate();
            }
        }
    }
}
