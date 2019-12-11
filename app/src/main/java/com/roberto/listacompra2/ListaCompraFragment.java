package com.roberto.listacompra2;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaCompraFragment extends Fragment {

    private RecyclerView listado_de_compras_recycler;
    private ArrayList<ListaCompra> listadocomprasdatos;
    private Adaptador_ListaCompra adaptadorlistacompra;
    private boolean actionModeactivado;


    public ListaCompraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.actionModeactivado=false;
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_lista_compra, container, false);
        //Preparo el RecyclerView
        prepararRecyclerView(v);


    return v;
    }

    private void prepararRecyclerView(View v)
    {
        listado_de_compras_recycler=v.findViewById(R.id.recycler_listado_compras);
        //Defino el Layout
        listado_de_compras_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Defino el Separador
        listado_de_compras_recycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        adaptadorlistacompra= new Adaptador_ListaCompra(this,R.layout.compra_layout,listadocomprasdatos);

        adaptadorlistacompra.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //En el click largo no voy a permitir seleccionar elementos salvo la primera vez
                int posicion = listado_de_compras_recycler.getChildAdapterPosition(v);
                if(!actionModeactivado)
                {//Solo inflo el menu si no esta el actionmodeactivado
                    actionModeactivado = true;

                    //Invoco a un metodo de mainActivity para inflar la barra, le paso la barra
                    barra.inflateMenu(R.menu.menu_action_mode);
                    //Cambio el estilo de la barra
                    barra.setBackgroundColor(Color.BLACK);
                    barra.setTitleTextAppearance(Listado_alumnos.this,R.style.estiloActionpersonalizado);

                    //Seleccionar el elemento sobre el que se ha pulsado

                    seleccionar_Elemento(posicion);
                    //Actualizco el numero de elementos seleccionados
                    //Repinto el elemento
                    adaptadorAlumnos.notifyItemChanged(posicion);
                    //Incluir icono de flecha hacia atras
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                    actualizar_barra();
                }
                return true;
            }
        });
        adaptadorlistacompra.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Para conocer en que elemento se ha hecho click
                //El objeto View representa un elemento de la lista
                int posicion=listado_de_compras_recycler.getChildAdapterPosition(v);
                if(actionModeactivado)
                {
                    //Si tenemos el modo de accion activado permite seleccionar los elementos
                    seleccionar_Elemento(posicion);
                    adaptadorAlumnos.notifyItemChanged(posicion);
                    actualizar_barra();
                    if(alumnosseleccionados.size()==0)
                    {
                        //Me salgo del action mode
                        salir_actionmode();
                    }
                }
                else{
                    //Le indica a la actividad que cargue otro fragmento con el detalle
                    //para ello le pasamos a la activity información del elemento seleccionado

                }
            }
        });
        vista_listaalumnos.setAdapter(adaptadorAlumnos);




    }
    private void actualizar_barra()
    {
        barra.getMenu().getItem(0).setVisible(alumnosseleccionados.size()>1?false:true);
        barra.setTitle(alumnosseleccionados.size()+" Alumnos seleccionados...");
    }
    private void seleccionar_Elemento(int posicion)
    {
        if(alumnosseleccionados.contains(alumnos.getLista_alumnos().get(posicion)))
        {
            alumnosseleccionados.remove(alumnos.getLista_alumnos().get(posicion));
        }
        else
        {
            alumnosseleccionados.add(alumnos.getLista_alumnos().get(posicion));
        }
    }


}
