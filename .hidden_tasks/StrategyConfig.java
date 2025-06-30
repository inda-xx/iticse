import java.util.Map;
import java.util.HashMap;

public class StrategyConfig {
    private Map<String, Double> configValues;

    public StrategyConfig() {
        configValues = new HashMap<>();
        loadDefaults();
    }

    public void set(String key, double value) {
        configValues.put(key, value);
    }

    public double get(String key) {
        return configValues.getOrDefault(key, 0.0);
    }

    private void loadDefaults() {
        configValues.put("softTyreWearRate", 1.25);
        configValues.put("hardTyreWearRate", 0.85);
        configValues.put("basePitLossSeconds", 22.5);
        configValues.put("blowoutRiskThreshold", 0.4);
    }
}