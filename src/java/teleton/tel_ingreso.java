/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teleton;

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
public class tel_ingreso extends Pantalla{

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private boolean boo_documento_valido = true;
    private String ide_empleado;
    private Confirmar con_guardar = new Confirmar();
    private VisualizarPDF vp = new VisualizarPDF();
    private Reporte rep_reporte = new Reporte();
    private SeleccionFormatoReporte sel_rep = new SeleccionFormatoReporte();
    private SeleccionCalendario sel_cal = new SeleccionCalendario();

    public tel_ingreso() {
        bar_botones.getBot_insertar().setUpdate("tab_tabla");
        bar_botones.getBot_guardar().setUpdate("tab_tabla");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla");

        ide_empleado = obtener_ide_empleado();
        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("tel_aportes", "ide_aporte", 1);
        tab_tabla.setCondicion("ide_aporte = -1");
        tab_tabla.setCampoOrden("ide_aporte");
        tab_tabla.getColumna("ide_aporte").setVisible(false);
        tab_tabla.getColumna("vrecibido_aporte").setVisible(false);
        tab_tabla.getColumna("fecrec_aporte").setVisible(false);
        tab_tabla.getColumna("estado_aporte").setVisible(false);
        tab_tabla.getColumna("estado_aporte").setValorDefecto("false");
        tab_tabla.getColumna("user_aporte").setVisible(false);
        tab_tabla.getColumna("fecofre_aporte").setValorDefecto(utilitario.getFechaActual());
        tab_tabla.getColumna("user_aporte").setValorDefecto(ide_empleado);
        tab_tabla.getColumna("cedula_aporte").setMetodoChange("validar_documento");
        tab_tabla.setTipoFormulario(true);
        tab_tabla.getGrid().setColumns(4);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        /**
         * ************************************************************
         */
        vp.setId("vp");
        vp.setTitle("Aporte Teleton");
        gru_pantalla.getChildren().add(vp);
        /**
         * ******************************************************
         */
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);

        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);
      
    }

    public void insertar() {
        tab_tabla.insertar();
    }

    public void guardar() {
        if (boo_documento_valido) {
            tab_tabla.guardar();
            utilitario.getConexion().guardarPantalla();
            Map parametros = new HashMap();
            parametros.put("numero", Integer.parseInt(tab_tabla.getValor("ide_aporte").toString()));
            vp.setVisualizarPDF("rep_teleton/teleton2.jasper", parametros);
            vp.dibujar();
            utilitario.addUpdate("vp");
        } else {
            utilitario.agregarMensajeError("Validación", "El documento ingresado no es válido, "
                    + "por favor corrija antes de guardar.");
        }

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

    public void validar_documento(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        String cedula = tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "cedula_aporte");
        boo_documento_valido = utilitario.validarCedula(cedula);
        if (boo_documento_valido) {
        } else {
            utilitario.agregarMensajeError("Validación", "El documento ingresado no es válido, "
                    + "por favor corrija antes de guardar.");
        }


    }

    public String obtener_ide_empleado() {
        String ide_empl = "-1";
        List list_sql1 = utilitario.getConexion().consultar("select ide_usua from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
            ide_empl = String.valueOf(list_sql1.get(0));
        }
        return ide_empl;
    }

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

    public SeleccionFormatoReporte getSel_rep() {
        return sel_rep;
    }

    public void setSel_rep(SeleccionFormatoReporte sel_rep) {
        this.sel_rep = sel_rep;
    }

    public SeleccionCalendario getSel_cal() {
        return sel_cal;
    }

    public void setSel_cal(SeleccionCalendario sel_cal) {
        this.sel_cal = sel_cal;
    }
}
