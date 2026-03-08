package com.demo.movieticketbooking;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal getPrice();
}
