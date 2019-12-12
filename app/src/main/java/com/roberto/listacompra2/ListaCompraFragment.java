package com.roberto.listacompra2;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaCompraFragment extends Fragment {

    private RecyclerView listado_de_compras_recycler;

    public ArrayList<ListaCompra> getListadocomprasdatos() {
        return listadocomprasdatos;
    }

    public void setListadocomprasdatos(ArrayList<ListaCompra> listadocomprasdatos) {
        this.listadocomprasdatos = listadocomprasdatos;
    }

    private ArrayList<ListaCompra> listadocomprasdatos;
    private ArrayList<ListaCompra> comprasseleccionadas;
    private Adaptador_ListaCompra adaptadorlistacompra;

    public boolean isActionModeactivado() {
        return actionModeactivado;
    }

    public void setActionModeactivado(boolean actionModeactivado) {
        this.actionModeactivado = actionModeactivado;
    }

    private boolean actionModeactivado;
    private MainActivity actividadprincipal;

    public Toolbar getBarra() {
        return barra;
    }

    public void setBarra(Toolbar barra) {
        this.barra = barra;
    }

    private Toolbar barra;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comprasseleccionadas=new ArrayList<ListaCompra>();
        barra=getActivity().findViewById(R.id.toolbar);
        actividadprincipal=(MainActivity)getActivity();

    }

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

    private void salir_actionmode()
    {
        comprasseleccionadas.clear();
        actionModeactivado=false;
        //Limpio el menu y restablezco los estilos
        barra.setBackgroundResource(R.color.colorPrimary);
        barra.setTitleTextAppearance(actividadprincipal,R.style.barra);
        barra.setTitle(R.string.titulobarraprincipal);
        actividadprincipal.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        barra.getMenu().clear();


        //notificar de los cambios en el adapatador
        adaptadorlistacompra.notifyDataSetChanged();
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

                    //Limpio la barra

                    //Inflo el menu de acción contextual
                    barra.inflateMenu(R.menu.menu_action_mode);
                    //Cambio el estilo de la barra
                    barra.setBackgroundColor(Color.BLACK);
                    barra.setTitleTextAppearance(getActivity(),R.style.estiloActionpersonalizado);

                    //Seleccionar el elemento sobre el que se ha pulsado

                    seleccionar_Elemento(posicion);
                    //Actualizco el numero de elementos seleccionados
                    //Repinto el elemento
                    adaptadorlistacompra.notifyItemChanged(posicion);
                    //Incluir icono de flecha hacia atras
                    if ((actividadprincipal.getSupportActionBar() != null)) {
                        actividadprincipal.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
                        actividadprincipal.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                    adaptadorlistacompra.notifyItemChanged(posicion);
                    actualizar_barra();
                    if(comprasseleccionadas.size()==0)
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
        listado_de_compras_recycler.setAdapter(adaptadorlistacompra);




    }
    private void actualizar_barra()
    {
        //Para mostrar el icono de editar o solo el de eliminar
        barra.getMenu().getItem(0).setVisible(comprasseleccionadas.size()>1?false:true);
        barra.setTitle(comprasseleccionadas.size()+" Compras seleccionadas...");
    }
    private void seleccionar_Elemento(int posicion)
    {
        if(comprasseleccionadas.contains(listadocomprasdatos.get(posicion)))
        {
            comprasseleccionadas.remove(listadocomprasdatos.get(posicion));
        }
        else
        {
            comprasseleccionadas.add(listadocomprasdatos.get(posicion));
        }
    }


}
