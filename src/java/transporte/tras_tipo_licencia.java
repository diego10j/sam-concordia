/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transporte;

import framework.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import sistema.*;

/**
 *
 * @author HP
 */
public class tras_tipo_licencia  extends Pantalla {

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    ///// BUSQUEDA DE CLAVES
    private VisualizarPDF vp = new VisualizarPDF();
    private Reporte rep_reporte = new Reporte();
    private Boton bot_impresion = new Boton();

    public tras_tipo_licencia() {

        bar_botones.getBot_insertar().setUpdate("tab_tabla1");
        bar_botones.getBot_guardar().setUpdate("tab_tabla1");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla1");

        //configuracion de la tabla de clientes (cabecera)
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("trans_tipo_licencia", "ide_tipo_licencia", 1);
        tab_tabla1.setNumeroTabla(1);
        tab_tabla1.dibujar();



        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla1);
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);



        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);
       
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

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla1.seleccionarFila(evt);
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