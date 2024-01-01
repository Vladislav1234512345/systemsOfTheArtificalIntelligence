import org.jfree.chart.ui.UIUtils;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static UIUtils RefineryUtilities;

    public static void main(String[] args) {

        final int SIZE = 100;
        Portfolio.setPrices(0.1, 0.2, 0.3);
        Population p = new Population(100);
        List<Double> costs = new ArrayList<>(); // список максимальных доходностей особей в поколении
        List<Double> averageCosts = new ArrayList<>(); // список средних доходностей поколений
        int repeatsQty = 0; // счётчик повторяющегося предела
        while (repeatsQty < 5) {
            costs.add(p.getBestPortfolio().getCost());
            averageCosts.add(p.getAveragePopulationCost());
            double sum = p.getBestPortfolio().getWeight(0) +
                    p.getBestPortfolio().getWeight(1) +
                    p.getBestPortfolio().getWeight(2);
            System.out.println("Популяция - " +
                    "\tДоходность лучшей особи = " + p.getBestPortfolio().getCost() +
                    "\tСредняя доходность популяции = " + p.getAveragePopulationCost() +
                    "\t" + p.getBestPortfolio().getWeight(0) +
                    "\t" + p.getBestPortfolio().getWeight(1) +
                    "\t" + p.getBestPortfolio().getWeight(2) +
                    "\t" + sum);
            double prevCost = p.getBestPortfolio().getCost();
            p = p.nextPopulation(2);
            double currentCost = p.getBestPortfolio().getCost();
            ++i;
            if (Math.abs(currentCost - prevCost) < 0.0000000000000001) {
                ++repeatsQty;
            } else {
                repeatsQty = 0;
            }
        }
        DualChart example = new DualChart("График доходностей", costs, averageCosts);
        example.pack();
        RefineryUtilities.centerFrameOnScreen(example);
        example.setVisible(true);
    }
}
