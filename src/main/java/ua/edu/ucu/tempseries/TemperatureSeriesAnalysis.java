package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private final int minTemperature = -273;
    private double[] temperatureSeries;
    private int seriesSize = 0;  // represents the filled size of the array, not its physical capacity

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[]{};
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        checkTemperatures(temperatureSeries);
        this.seriesSize = temperatureSeries.length;
        this.temperatureSeries = Arrays.copyOf(temperatureSeries, this.seriesSize);
    }


    public double average() {
        if (this.seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        double sum = 0;
        for (double temperature : temperatureSeries) {
            sum += temperature;
        }
        return sum / this.seriesSize;
    }

    public double deviation() {
        if (this.seriesSize == 0) {
            throw new IllegalArgumentException();
        }

        double tempAvg = this.average();
        double tempDev = 0;
        for (int i = 0; i < this.seriesSize; i++) {
            tempDev += (temperatureSeries[i] - tempAvg) * (temperatureSeries[i] - tempAvg);
        }

        return tempDev / this.seriesSize;
    }

    public double min() {
        if (this.seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        double minTemp = this.minTemperature;
        for (double temp : this.temperatureSeries) {
            if (temp < minTemp) {
                minTemp = temp;
            }
        }
        return minTemp;
    }

    public double max() {
        if (this.seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        double maxTemp = this.minTemperature;
        for (double temp : this.temperatureSeries) {
            if (temp > maxTemp) {
                maxTemp = temp;
            }
        }
        return maxTemp;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (this.seriesSize == 0) {
            throw new IllegalArgumentException();
        }

        double closestTempToValue = 0;
        double smallestDifference = Double.MAX_VALUE;

        for (double currTemp : this.temperatureSeries) {
            double currDifference = Math.abs(currTemp - tempValue);
            if (currDifference < smallestDifference) {
                smallestDifference = currDifference;
                closestTempToValue = currTemp;
            } else if (currDifference == smallestDifference) {
                if (currTemp > closestTempToValue) {
                    // if there is a choice between positive and negative temp, we will pick positive
                    closestTempToValue = currTemp;
                }
            }
        }

        return closestTempToValue;
    }

    public double[] findTempsLessThen(double tempValue) {
        double[] lesserTemps = new double[this.seriesSize];
        int newArrayIndex = 0;
        for (double currTemp : this.temperatureSeries) {
            if (currTemp < tempValue) {
                lesserTemps[newArrayIndex] = currTemp;
                newArrayIndex++;
            }
        }
        return Arrays.copyOf(lesserTemps, newArrayIndex);
    }

    public double[] findTempsGreaterThen(double tempValue) {
        double[] lesserTemps = new double[this.seriesSize];
        int newArrayIndex = 0;
        for (double currTemp : this.temperatureSeries) {
            if (currTemp >= tempValue) {
                lesserTemps[newArrayIndex] = currTemp;
                newArrayIndex++;
            }
        }
        return Arrays.copyOf(lesserTemps, newArrayIndex);
    }

    public TempSummaryStatistics summaryStatistics() {
        if (this.seriesSize == 0) {
            throw new IllegalArgumentException();
        }
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public double addTemps(double... temps) {
        checkTemperatures(temps);

        int sum = 0;

        while (temps.length > this.temperatureSeries.length) {
            this.temperatureSeries = Arrays.copyOf(this.temperatureSeries,
                    this.temperatureSeries.length * 2);
        }

        for (int i = 0; i < temps.length; i++) {
            this.temperatureSeries[this.seriesSize] = temps[i];
            this.seriesSize++;
        }

        for (double temp : this.temperatureSeries) {
            sum += temp;
        }

        return sum;
    }

    public void checkTemperatures(double[] temperatureSeries) {
        for (double temperature : temperatureSeries) {
            if (temperature < minTemperature) {
                throw new InputMismatchException();
            }
        }
    }
}
