import java.util.*;

public class DNA
{
    private final double dailyEnergyMultiplier;
    private final double lifeSpanMultiplier;

    // Generates random DNA.
    public DNA()
    {
        Random random = new Random();
        dailyEnergyMultiplier = ( 70 + random.nextInt(60) ) / 100.0;
        lifeSpanMultiplier = ( 70 + random.nextInt(60) ) / 100.0;
    }

    // Generates DNA based on the DNA of two parents.
    public DNA(DNA parent1, DNA parent2)
    {
        dailyEnergyMultiplier = (parent1.getDailyEnergyMultiplier() + parent2.getDailyEnergyMultiplier()) / 2;
        lifeSpanMultiplier = (parent1.getLifeSpanMultiplier() + parent2.getLifeSpanMultiplier()) / 2;
    }

    // Returns the daily energyToAct multiplier.
    public double getDailyEnergyMultiplier()
    {
        return dailyEnergyMultiplier;
    }

    // Returns the life span multiplier.
    public double getLifeSpanMultiplier()
    {
        return lifeSpanMultiplier;
    }

    // Computes the strength of this DNA.
    public double getDNAStrength()
    {
        return (dailyEnergyMultiplier + lifeSpanMultiplier) / 2;
    }
}
