/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flisol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sistema.*;
import framework.*;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author user
 */
public class flisol extends Pantalla {

    private Tabla tab_tabla = new Tabla();
    private Division div_division = new Division();
    private boolean boo_documento_valido = true;
    private String ide_empleado;
    private Confirmar con_guardar = new Confirmar();
    private VisualizarPDF vp = new VisualizarPDF();
    private Reporte rep_reporte = new Reporte();
    private Boton bot_busqueda = new Boton();

    public flisol() {

        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("registro", "ide", 1);
        tab_tabla.setCampoOrden("ide");
        tab_tabla.getColumna("ide").setVisible(false);
        tab_tabla.setTipoFormulario(true);
        tab_tabla.getGrid().setColumns(4);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        /**
         * ************************************************************
         */
        vp.setId("vp");
        vp.setTitle("certificado");
        agregarComponente(vp);

        bot_busqueda.setValue("Impresion");
        bot_busqueda.setMetodo("impresion");
        bar_botones.agregarComponente(bot_busqueda);


        /**
         * ******************************************************
         */
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);

     //   gru_pantalla.getChildren().add(bar_botones);
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

    public void impresion() {
        Map parametros = new HashMap();
        parametros.put("ide", Integer.parseInt(tab_tabla.getValor("ide").toString()));
        vp.setVisualizarPDF("flisol/registro.jasper", parametros);
        vp.dibujar();
        utilitario.addUpdate("vp");

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
/*
    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }
*/
    public VisualizarPDF getVp() {
        return vp;
    }

    public void setVp(VisualizarPDF vp) {
        this.vp = vp;
    }

    public Reporte getRep_reporte() {
        return rep_reporte;
    }

    public void setRep_reporte(Reporte rep_reporte) {
        this.rep_reporte = rep_reporte;
    }
}
