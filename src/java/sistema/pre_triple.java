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
public class pre_triple extends Pantalla {

    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    private Tabla tab_tabla3 = new Tabla();
    private Division div_division = new Division();

    public pre_triple() {

        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setNumeroTabla(1);
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.dibujar();
        PanelTabla pat_panel1 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);

        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setNumeroTabla(2);
        tab_tabla2.dibujar();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla2);

        tab_tabla3.setId("tab_tabla3");
        tab_tabla3.setNumeroTabla(3);
        tab_tabla3.dibujar();
        PanelTabla pat_panel3 = new PanelTabla();
        pat_panel3.setPanelTabla(tab_tabla3);

        div_division.setId("div_division");
        div_division.dividir3(pat_panel1, pat_panel2, pat_panel3, "30%", "30%", "H");
        agregarComponente(div_division);
    }

    @Override
    public void insertar() {
        utilitario.getTablaisFocus().insertar();
    }

    @Override
    public void guardar() {
        tab_tabla1.guardar();
        tab_tabla2.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
    }

    public Tabla getTab_tabla1() {
        return tab_tabla1;
    }

    public void setTab_tabla1(Tabla tab_tabla1) {
        this.tab_tabla1 = tab_tabla1;
    }

    public Tabla getTab_tabla2() {
        return tab_tabla2;
    }

    public void setTab_tabla2(Tabla tab_tabla2) {
        this.tab_tabla2 = tab_tabla2;
    }

    public Tabla getTab_tabla3() {
        return tab_tabla3;
    }

    public void setTab_tabla3(Tabla tab_tabla3) {
        this.tab_tabla3 = tab_tabla3;
    }
}