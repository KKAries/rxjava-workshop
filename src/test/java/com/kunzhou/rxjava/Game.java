package com.kunzhou.rxjava;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kzhou on 6/8/2018.
 */
public class Game {
    private String id;
    private String title;
    private double score;
    private String publisher;
    private BigDecimal price;

    public List<String> getDisks() {
        return disks;
    }

    private List<String> disks;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getScore() {
        return score;
    }

    public String getPublisher() {
        return publisher;
    }

    public BigDecimal getPrice() {
        return price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static List<Game> MyGameCollection = initialize();

    public Game(String id, String title, double score, String publisher, BigDecimal price, List<String> disks){
        this.title = title;
        this.id = id;
        this.score = score;
        this.publisher = publisher;
        this.price = price;
        this.disks = disks;
    }

    private static List<Game> initialize () {
        List<Game> gamelist = new ArrayList<>();
        Game game = new Game("1", "StarCraft2", 8.5, "Blizzard", new BigDecimal(13.99), Arrays.asList("disk1", "disk2"));
        gamelist.add(game);
        game = new Game("2", "WarCraft3", 9.0, "Blizzard", new BigDecimal(0.99), Arrays.asList("disk1"));
        gamelist.add(game);
        game = new Game("3", "FIFA18", 8.0, "Electronic Arts", new BigDecimal(49.99), Arrays.asList("disk1", "disk2", "disk3"));
        gamelist.add(game);
        game = new Game("4", "BattleField1", 9.0, "Electronic Arts", new BigDecimal(29.99), Arrays.asList("disk1", "disk2", "disk3", "disk4"));
        gamelist.add(game);
        game = new Game("5", "POPPIT", 10.0, "Electronic Arts", new BigDecimal(59.99), Arrays.asList("disk1", "disk2"));
        gamelist.add(game);
        game = new Game("6", "BattleField4", 9.0, "Electronic Arts", new BigDecimal(19.99), Arrays.asList("disk1", "disk2", "disk3"));
        gamelist.add(game);
        return gamelist;
    }

    @Override
    public String toString() {
        return String.format("['%s', '%s', '%s', '%s', '%s']",
                getId(), getTitle(), getScore(), getPublisher(), "$" + getPrice());
    }
}
