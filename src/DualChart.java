import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class DualChart extends ApplicationFrame {
    List<Double> costs;
    List<Double> averageCosts;
    private static UIUtils RefineryUtilities;

    public DualChart(String title, List<Double> costs, List<Double> averageCosts) {
        super(title);
        this.costs = costs;
        this.averageCosts = averageCosts;
        JFreeChart chart = createChart(createDataset(), "График лучших и средних доходностей популяций");

        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new Dimension(960, 540));

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(chartPanel);

        setContentPane(panel);
    }
    //Создание графика
    private JFreeChart createChart(XYDataset dataset, String title) {
        return ChartFactory.createXYLineChart(
                title,
                "Поколение",
                "Доходность",
                dataset
        );
    }
    //Создание набора данных для графика
    private XYDataset createDataset() {
        XYSeries series1 = new XYSeries("Лучшая доходность");
        for(int i = 0; i < costs.size(); ++i) {
            series1.add(i + 1, costs.get(i));
        }
        XYSeries series2 = new XYSeries("Средняя доходность");
        for(int i = 0; i < averageCosts.size(); ++i) {
            series2.add(i + 1, averageCosts.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }
}
