import java.util.*;

public class DNA
{
    private final double dailyEnergyMultiplier;
    private final double lifeSpanMultiplier;

    public DNA()
    {
        Random random = new Random();
        dailyEnergyMultiplier = ( 70 + random.nextInt(60) ) / 100.0;
        lifeSpanMultiplier = ( 70 + random.nextInt(60) ) / 100.0;
    }

    public DNA(DNA parent1, DNA parent2)
    {
        dailyEnergyMultiplier = (parent1.getDailyEnergyMultiplier() + parent2.getDailyEnergyMultiplier()) / 2;
        lifeSpanMultiplier = (parent1.getLifeSpanMultiplier() + parent2.getLifeSpanMultiplier()) / 2;
    }

    public double getDailyEnergyMultiplier()
    {
        return dailyEnergyMultiplier;
    }

    public double getLifeSpanMultiplier()
    {
        return lifeSpanMultiplier;
    }

    public double getDNAStrength()
    {
        return (dailyEnergyMultiplier + lifeSpanMultiplier) / 2;
    }
}
