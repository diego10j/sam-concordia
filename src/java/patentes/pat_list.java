/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package patentes;

import framework.*;
import sistema.Pantalla;

/**
 *
 * @author user
 */
public class pat_list extends Pantalla{

  //  private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
    //private Barra bar_botones = new Barra();
    private Division div_division = new Division();
  //  private Grupo gru_pantalla = new Grupo();

    public pat_list() {
        bar_botones.getBot_insertar().setUpdate("tab_tabla");
        bar_botones.getBot_guardar().setUpdate("tab_tabla");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla");

        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("pat_ficha", "ide_pat_ficha", 1);

        tab_tabla.getColumna("ide_cliente").setCombo("select ide_cliente,nombre from rec_clientes order by nombre;");
        tab_tabla.getColumna("ide_cliente").setAutoCompletar();
        tab_tabla.setNumeroTabla(1);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);

       // gru_pantalla.getChildren().add(bar_botones);
        agregarComponente(div_division);
    
    }
@Override
    public void insertar() {
        tab_tabla.insertar();
    }


@Override
    public void guardar() {
        tab_tabla.guardar();
        utilitario.getConexion().guardarPantalla();
    }
@Override
    public void eliminar() {
        tab_tabla.eliminar();
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }

 /*   public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }*/
}
