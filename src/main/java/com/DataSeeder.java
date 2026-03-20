package com.cafe;

import com.cafe.model.Order;
import com.cafe.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds some demo orders on startup so the UI has data immediately.
 * Remove or comment out in production.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final OrderRepository repo;

    public DataSeeder(OrderRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        seed("Alice",  Order.DrinkType.LATTE,       Order.DrinkTemp.HOT,  Order.MilkType.OAT,       true,  Order.Flavour.VANILLA,             Order.OrderStatus.IN_PROGRESS);
        seed("Ben",    Order.DrinkType.CAPPUCCINO,   Order.DrinkTemp.HOT,  Order.MilkType.WHOLE,     true,  null,                              Order.OrderStatus.AERATION);
        seed("Cara",   Order.DrinkType.FLAT_WHITE,   Order.DrinkTemp.ICED, Order.MilkType.ALMOND,    false, Order.Flavour.BROWN_SUGAR,         Order.OrderStatus.PENDING);
        seed("David",  Order.DrinkType.AMERICANO,    Order.DrinkTemp.HOT,  Order.MilkType.TWO_PERCENT,false,Order.Flavour.DARK_CARAMEL,        Order.OrderStatus.PENDING);
        seed("Elena",  Order.DrinkType.MACCHIATO,    Order.DrinkTemp.ICED, Order.MilkType.SOY,       false, Order.Flavour.PEPPERMINT_WHITE_MOCHA, Order.OrderStatus.READY);
    }

    private void seed(String name, Order.DrinkType drink, Order.DrinkTemp temp,
                      Order.MilkType milk, boolean aerated, Order.Flavour flavour,
                      Order.OrderStatus status) {
        Order o = new Order();
        o.setCustomerName(name);
        o.setDrinkType(drink);
        o.setTemp(temp);
        o.setMilk(milk);
        o.setMilkAerated(aerated);
        o.setFlavour(flavour);
        o.setStatus(status);
        repo.save(o);
    }
}
