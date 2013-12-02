/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coactivas;

import framework.*;
import sistema.Pantalla;

/**
 *
 * @author HP
 */
public class pre_tipo_gestor extends Pantalla
{
    //private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
   // private Grupo gru_pantalla = new Grupo();

    public pre_tipo_gestor()
    {
        /*bar_botones/*.getBot_insertar().setUpdate("tab_tabla");
        bar_botones.getBot_guardar().setUpdate("tab_tabla");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla");*/

        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("rec_tipo_gestor", "ide_tipo_gestor", 1);
        tab_tabla.setCampoOrden("ide_tipo_gestor");
        tab_tabla.setRows(20);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        div_division.setId("div_division");
        div_division.dividir1(pat_panel);
        agregarComponente(div_division);

       /* gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);*/
      
    }
@Override
    public void insertar()
    {
        tab_tabla.insertar();
    }
@Override
    public void guardar()
    {
        tab_tabla.guardar();
        utilitario.getConexion().guardarPantalla();
    }
@Override
    public void eliminar()
    {
        tab_tabla.eliminar();
    }

    public Tabla getTab_tabla()
    {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla)
    {
        this.tab_tabla = tab_tabla;
    }

 /*   public Barra getBar_botones()
    {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones)
    {
        this.bar_botones = bar_botones;
    }*/
}
