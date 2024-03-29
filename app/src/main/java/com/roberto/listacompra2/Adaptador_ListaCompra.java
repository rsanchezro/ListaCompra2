package com.roberto.listacompra2;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class Adaptador_ListaCompra extends RecyclerView.Adapter<Adaptador_ListaCompra.Miholder> {
    private ArrayList<ListaCompra> datos;
    private Context ctx;
    private ListaCompraFragment fragmento;
    private int recursoLayout;
    private View.OnClickListener onClick;
    private View.OnLongClickListener onLongClick;



    public void añadir_ListaCompra(ListaCompra l)
    {
        this.datos.add(l);
        notifyItemChanged((this.datos.size()-1));
    }

    public Adaptador_ListaCompra(ListaCompraFragment c, int rlayout, ArrayList<ListaCompra> d)
    {
        this.fragmento=c;

        this.ctx=c.getActivity();
        this.datos=d;
        this.recursoLayout=rlayout;

    }



    public void setOnClickListener(View.OnClickListener click)
    {
        this.onClick=click;
    }

    public void setOnLongClickListener(View.OnLongClickListener longclick)
    {
        this.onLongClick=longclick;
    }

    @NonNull
    @Override
    public Miholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Este metodo se invocará tantas veces como el número de elementos que se visualicen de forma simultanea
        //Inflo la vista
        View v=null;
        try {
          v = ((AppCompatActivity)ctx).getLayoutInflater().inflate(this.recursoLayout, parent, false);

        }
        catch (Exception e)
        {
            Log.i("Informacion",e.getMessage().toString());
        }
        //Establezco los escuchadores a la vista
        v.setOnClickListener(this.onClick);
        v.setOnLongClickListener(this.onLongClick);



        //Retorno una instancia de miholder
        return new Miholder(v);
    }

    public void eliminar_elementos(ArrayList<ListaCompra> a)
    {

        for (ListaCompra elemento:a
             ) {
            datos.remove(elemento);
        }
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull final Miholder holder, int position) {
        Log.i("Informacion","Metodo bind"+position);
        //Este metodo se invocara tantas veces como elementos se vayan visualizando
        //Vinculo los datos del alumno que voy a tratar
        holder.listacompra=this.datos.get(position);
        //Asocio los datos de los elementos visuales
        holder.nombre.setText(holder.listacompra.getNombre_lista()+"");
        holder.num_articulos.setText(holder.listacompra.getNum_productos()+"");
        holder.importe_total.setText(holder.listacompra.getImporte_total()+"");
        holder.vista.setBackgroundResource(R.color.colorFondoElemento);
        //Cambio el color de fondo en funcion de si el elemento esta seleccionado o

        if(this.fragmento.isActionModeactivado()) {
            if (fragmento.getListadocomprasdatos().contains(holder.listacompra)) {
                holder.vista.setBackgroundResource(R.color.colorFondoElementoSeleccionado);
            }
        }

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }


    public class Miholder extends ViewHolder{
        public View vista;
        public TextView nombre;
        public TextView num_articulos;
        public TextView importe_total;
        public ListaCompra listacompra;
        public Miholder(View v){
            super(v);
            this.vista=v;
            this.nombre=this.vista.findViewById(R.id.nombre_listacompra_txtview);
            this.num_articulos=this.vista.findViewById(R.id.num_pdtos_listcompra_txtview);
            this.importe_total=this.vista.findViewById(R.id.importe_listacompra_txtview);


        }

    }
}
