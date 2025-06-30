public class StrategyLoader {
    public static StrategyConfig loadConfig(String filename) {
        StrategyConfig config = new StrategyConfig();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] pair = line.split(",");
                if (pair.length == 2) {
                    String key = pair[0].trim();
                    double value = Double.parseDouble(pair[1].trim());
                    config.set(key, value);
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not load config file. Using defaults.");
        }

        return config;
    }
}