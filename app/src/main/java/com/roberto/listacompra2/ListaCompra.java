package com.roberto.listacompra2;

import java.util.ArrayList;

public class ListaCompra {

    private String nombre_lista;
    private int num_productos;
    private float importe_total;
    private ArrayList<DetalleProducto> detalleproductos;

    public void añadir_detalleproducto(DetalleProducto d)
    {
        detalleproductos.add(d);
        setNum_productos(detalleproductos.size());
    }

    public void eliminar_detalleproducto(DetalleProducto d)
    {
        detalleproductos.remove(d);
        setNum_productos(detalleproductos.size());
    }

    public int getNum_productos() {
        return num_productos;
    }

    public void setNum_productos(int num_productos) {
        this.num_productos = num_productos;
    }

    public float getImporte_total() {
        return importe_total;
    }

    public void setImporte_total(float importe_total) {
        this.importe_total = importe_total;
    }


    public ListaCompra(String nl) {
        this.nombre_lista=nl;
        this.num_productos=0;
        this.importe_total=0;
        this.detalleproductos=new ArrayList<DetalleProducto>();

    }






}
