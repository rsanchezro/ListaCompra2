package com.roberto.listacompra2;

public class DetalleProducto {
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getNum_unidades() {
        return num_unidades;
    }

    public void setNum_unidades(int num_unidades) {
        this.num_unidades = num_unidades;
    }

    private Producto producto;
    private int num_unidades;

}
