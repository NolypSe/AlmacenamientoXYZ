package com.example.xyz;

public class MainModelo {

    String Nombre, Categoria, ImgUrl, Precio, Stock;

    public MainModelo() {
    }

    public MainModelo(String nombre, String categoria, String imgUrl , String precio, String stock) {
        Nombre = nombre;
        Categoria = categoria;
        ImgUrl = imgUrl;
        Precio = precio;
        Stock = stock;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }
}
