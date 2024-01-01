public class Portfolio {
    private final double[] weights = new double[3];
    private static final double[] prices = new double[3];

    public Portfolio() {
        double weightEdge = 1;
        for (int i = 0; i < weights.length - 1; ++i) {
            weights[i] = Math.random() % weightEdge;
            weightEdge -= weights[i];
        }
        weights[weights.length - 1] = weightEdge;
    }

    //Установление доходности актива
    public static void setPrices(double price1, double price2, double price3) {
        prices[0] = price1;
        prices[1] = price2;
        prices[2] = price3;
    }

    //Получение коэффициента актива по индексу
    public double getWeight(int index) {
        double weightSum = 0;
        for(int i = 0; i < weights.length; ++i) {
            weightSum += weights[i];
        }
        if(weights[index] == 0) {
            weights[index] = Math.random() / weightSum;
        }
        return weights[index];
    }

    //Задание коэффициента актива по индексу
    public void setWeight(double weight, int index) {
        weights[index] = weight;
    }

    //Получение доходности портфеля
    public double getCost() {
        double cost = 0;
        for (int i = 0; i < weights.length; ++i) {
            cost += weights[i] * prices[i];
        }
        return cost;
    }
}