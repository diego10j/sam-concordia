/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avaluos;

import sistema.*;
import framework.Division;
import framework.Tabla;
import framework.PanelTabla;
import framework.Barra;
import framework.Grupo;
import framework.Arbol;
import org.primefaces.event.NodeSelectEvent;

/**
 *
 * @author user
 */
public class pre_distribucion {

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private Arbol arb_arbol = new Arbol();

    public pre_distribucion() {
        bar_botones.getBot_insertar().setUpdate("tab_tabla1");
        bar_botones.getBot_guardar().setUpdate("tab_tabla1,arb_arbol");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla1");

        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("inst_distribucion_politica", "ide_distribucion", 1);
        tab_tabla1.setCampoPadre("ins_ide_distribucion");
        tab_tabla1.setCampoNombre("des_distribucion");
        tab_tabla1.getColumna("ide_tipo_distribucion").setCombo("inst_tipo_distribucion", "ide_tipo_distribucion", "des_tipo_distribucion", "");
        tab_tabla1.agregarArbol(arb_arbol);
        tab_tabla1.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla1);

        arb_arbol.setId("arb_arbol");                  
        arb_arbol.onSelect("seleccionar_arbol");        
        arb_arbol.dibujar();        
        
        div_division.setId("div_division");
        div_division.dividir2(arb_arbol, pat_panel, "21%", "V");
        
        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);

       
    }

    public void seleccionar_arbol(NodeSelectEvent evt) {
        arb_arbol.seleccionarNodo(evt);
        tab_tabla1.ejecutarValorPadre(arb_arbol.getValorSeleccionado());        
    }

    public void insertar() {
        tab_tabla1.insertar();
    }

    public void guardar() {
        tab_tabla1.guardar();
        utilitario.getConexion().guardarPantalla();
    }

    public void eliminar() {
        tab_tabla1.eliminar();
    }

    public Arbol getArb_arbol() {
        return arb_arbol;
    }

    public void setArb_arbol(Arbol arb_arbol) {
        this.arb_arbol = arb_arbol;
    }

    public Tabla getTab_tabla1() {
        return tab_tabla1;
    }

    public void setTab_tabla1(Tabla tab_tabla1) {
        this.tab_tabla1 = tab_tabla1;
    }

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }
}
