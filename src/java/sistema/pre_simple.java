/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import framework.Division;
import framework.PanelTabla;
import framework.Tabla;
/**
 *
 * @author Diego
 */
public class pre_simple extends Pantalla {

    private Division div_division = new Division();
    private Tabla tab_tabla = new Tabla();

    public pre_simple() {
        bar_botones.setConfirmarGuardar();
        tab_tabla.setId("tab_tabla");
        tab_tabla.setNumeroTabla(1);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);
        agregarComponente(div_division);
    }

    @Override
    public void insertar() {
        tab_tabla.insertar();
    }

    @Override
    public void guardar() {
        tab_tabla.guardar();
        guardarPantalla();
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
}
