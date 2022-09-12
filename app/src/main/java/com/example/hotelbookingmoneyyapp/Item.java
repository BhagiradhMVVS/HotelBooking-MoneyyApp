package com.example.hotelbookingmoneyyapp;

public class Item {
    private final String Name;
    private final String Image;
    private final String City;
    private final String Country;
    private final int Price;
    private int Rating = 0;
    private final String Id;
    private final String Des;

    public Item(String name, String image, String city, String country, int price, int rating, String id, String des) {
        Name = name;
        Image = image;
        City = city;
        Country = country;
        Price = price;
        Rating = rating;
        Id = id;
        Des = des;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public int getPrice() {
        return Price;
    }

    public int getRating() {
        return Rating;
    }

    public String getDes() {
        return Des;
    }

    public String getId() {
        return Id;
    }

}
