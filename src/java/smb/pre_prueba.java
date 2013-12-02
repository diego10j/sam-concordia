/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smb;

import sistema.*;
import framework.*;
import persistencia.Conexion;

/**
 *
 * @author user
 */
public class pre_prueba {

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private Conexion con_acces = new Conexion();

    public pre_prueba() {
        bar_botones.getBot_insertar().setUpdate("tab_tabla");
        bar_botones.getBot_guardar().setUpdate("tab_tabla");
        //  bar_botones.setConfirmarGuardar("tab_tabla");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla");

        con_acces.setUnidad_persistencia("access");
        tab_tabla.setConexion(con_acces);
        tab_tabla.setId("tab_tabla");
        tab_tabla.setCondicion("\"PAGADO\"=false");
        tab_tabla.setTabla("\"LIQUIDACIONES\"", "ide_sam", 1);
        for (int i = 0; i < tab_tabla.getTotalColumnas(); i++) {
            if (!tab_tabla.getColumnas()[i].getNombre().equalsIgnoreCase("ide_sam") && !tab_tabla.getColumnas()[i].getNombre().equalsIgnoreCase("cliente_sam")) {
                tab_tabla.getColumnas()[i].setNombre("\"" + tab_tabla.getColumnas()[i].getNombre() + "\"");
            }
        }
        tab_tabla.setRows(20);
        tab_tabla.setLectura(true);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);

        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);
       
    }

    public void insertar() {
        tab_tabla.insertar();
    }

    public void guardar() {
        tab_tabla.guardar();
        utilitario.getConexion().guardarPantalla();
    }

    public void eliminar() {
        tab_tabla.eliminar();
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }
}
