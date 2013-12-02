/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import sistema.Utilitario;

/**
 *
 * @author HP
 */
public class pre_tipo
{
    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();

    public pre_tipo()
    {
        bar_botones.getBot_insertar().setUpdate("tab_tabla");
        bar_botones.getBot_guardar().setUpdate("tab_tabla");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla");

        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("rec_tipo", "ide_tipo", 1);
        tab_tabla.setCampoOrden("ide_tipo");
        tab_tabla.setRows(20);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        div_division.setId("div_division");
        div_division.dividir1(pat_panel);

        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);
  
    }

    public void insertar()
    {
        tab_tabla.insertar();
    }

    public void guardar()
    {
        tab_tabla.guardar();
        utilitario.getConexion().guardarPantalla();
    }

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

    public Barra getBar_botones()
    {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones)
    {
        this.bar_botones = bar_botones;
    }
}
