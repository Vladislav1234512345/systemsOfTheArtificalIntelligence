import java.util.Random;

public class Population {
    private static final double MUTATION_RATE = 0.1;
    private static final Random random = new Random();
    private Portfolio[] portfolios;

    public Population(Portfolio[] portfolios) {
        this.portfolios = portfolios;
    }

    public Population(int size) {
        randomPopulation(size);
    }

    //Создание случайного поколения
    public Portfolio[] randomPopulation(int size) {
        portfolios = new Portfolio[size];
        for (int i = 0; i < portfolios.length; ++i) {
            portfolios[i] = new Portfolio();
        }
        return portfolios;
    }

    //Мутация портфеля
    public void mutate(Portfolio p) {
        int weightIndex = random.nextInt(3);
        double weight = p.getWeight(weightIndex);
        char[] binaryWeight = Integer.toBinaryString((int) (weight * 100)).toCharArray();
        int binaryWeightIndex = random.nextInt(binaryWeight.length);
        binaryWeight[binaryWeightIndex] = binaryWeight[binaryWeightIndex] == '1' ? '0' : '1';
        String strBinaryWeight = new String(binaryWeight);
        double mutatedWeight = (double) Integer.parseInt(strBinaryWeight, 2) % 100 / 100;
        p.setWeight(mutatedWeight, weightIndex);
        double sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += p.getWeight(i);
        }
        for (int i = 0; i < 3; ++i) {
            p.setWeight(p.getWeight(i) / sum, i);
        }
    }

    //Скрещевание двух портфелей
    public Portfolio crossover(Portfolio p1, Portfolio p2) {
        Portfolio child = new Portfolio();
        double sum = 0;
        for(int i = 0; i < 3; ++i) {
            double childWeight=  krossingover(p1.getWeight(i), p2.getWeight(i));
            child.setWeight(childWeight, i);
            sum += child.getWeight(i);
        }
        for(int i = 0; i < 3; ++i) {
            child.setWeight(child.getWeight(i) / sum, i);
        }
        return child;
    }

    public double krossingover(double weight1, double weight2) {
        String weight1Binary = Integer.toBinaryString((int) (weight1 * 100));
        String weight2Binary = Integer.toBinaryString((int) (weight2 * 100));
//        char[] weight1Binary = new char[32];
//        char[] weight2Binary = new char[32];
//        int n = 0;
//        for (int i = 0; i < 32; ++i) {
//            if (i < 32 - weight1Binary.length) {
//                weight1Binary[i] = '0';
//            } else {
//                weight1Binary[i] = weight1BinaryStr.charAt(n++);
//            }
//        }
//        n = 0;
//        for (int i = 0; i < 32; ++i) {
//            if(i < 32 - weight2Binary.length) {
//                weight2Binary[i] = '0';
//            } else {
//                weight2Binary[i] = weight2BinaryStr.charAt(n++);
//            }
//        }
        String result = "";
        if (weight1Binary.length() < weight2Binary.length()) {
            int index = random.nextInt(weight1Binary.length());
            result += weight1Binary.substring(0, index + 1);
            result += weight2Binary.substring(index + 1);

        } else {
            int index = random.nextInt(weight2Binary.length());
            result += weight2Binary.substring(0, index + 1);
            result += weight1Binary.substring(index + 1);
        }
        double weight = (double) Integer.parseInt(result, 2) % 100 / 100;
        return weight;
    }

    //Отбор турнирным методом
    public Portfolio[] selection(int tournamentSize) {
        Portfolio[] tempPortfolios = new Portfolio[portfolios.length];
        for (int i = 0; i < tempPortfolios.length; ++i) {
            Portfolio[] tournament = new Portfolio[tournamentSize];
            for (int j = 0; j < tournament.length; ++j) {
                int index = random.nextInt(portfolios.length);
                tournament[j] = portfolios[index];
            }
            for (int j = 0; j < tournament.length; ++j) {
                boolean isChanged = false;
                for (int k = j + 1; k < tournament.length; ++k) {
                    if (tournament[k].getCost() > tournament[k - 1].getCost()) {
                        Portfolio tmp = tournament[k - 1];
                        tournament[k - 1] = tournament[k];
                        tournament[k] = tmp;
                        isChanged = true;
                    }
                }
                if (!isChanged) {
                    break;
                }
            }
            tempPortfolios[i] = tournament[0];
        }
        return tempPortfolios;
    }

    //Получение лучшего портфеля в поколении
    public Portfolio getBestPortfolio() {
        Portfolio bestPortfolio = portfolios[0];
        for (int i = 0; i < portfolios.length; ++i) {
            if (bestPortfolio.getCost() < portfolios[i].getCost()) {
                bestPortfolio = portfolios[i];
            }
        }
        return bestPortfolio;
    }

    //Получение средней доходности в поколении
    public double getAveragePopulationCost() {
        double averageCost = 0;
        for (int i = 0; i < portfolios.length; ++i) {
            averageCost += portfolios[i].getCost();
        }
        averageCost /= portfolios.length;
        return averageCost;
    }

    //Переход к следующему поколению
    public Population nextPopulation(int tournamentSize) {
        Portfolio[] tempPortfolios = selection(tournamentSize);
        for (int i = 0; i < tempPortfolios.length; ++i) {
            int parentIndex1 = random.nextInt(portfolios.length);
            int parentIndex2 = random.nextInt(portfolios.length);
            Portfolio parent1 = tempPortfolios[parentIndex1];
            Portfolio parent2 = tempPortfolios[parentIndex2];
            portfolios[i] = crossover(parent1, parent2);
            if (Math.random() <= MUTATION_RATE) {
                mutate(portfolios[i]);
            }
        }
        return this;
    }
}

