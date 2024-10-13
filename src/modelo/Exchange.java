package modelo;

import java.util.Map;

public record Exchange(String base_code, Map<String, Double> conversion_rates) {
}