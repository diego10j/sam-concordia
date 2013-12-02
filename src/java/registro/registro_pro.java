/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package registro;

import framework.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import sistema.Utilitario;
import framework.*;
import java.awt.Color;
import java.awt.GradientPaint;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.labels.*;
import org.primefaces.component.graphicimage.GraphicImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.jfree.data.category.DefaultCategoryDataset;
import sistema.Utilitario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author HP
 */
public class registro_pro {

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private boolean boo_documento_valido = true;
    private List lis_genero = new ArrayList();
    private MarcaAgua maa_busqueda = new MarcaAgua();
    private Reporte rep_reporte = new Reporte();
    private SeleccionFormatoReporte sel_rep = new SeleccionFormatoReporte();
    private SeleccionCalendario sel_cal = new SeleccionCalendario();
    private SeleccionTabla sel_tab = new SeleccionTabla();
    private SeleccionTabla sel_tab2 = new SeleccionTabla();
    private SeleccionTabla sel_tab3 = new SeleccionTabla();
    //para colocar el cursor del mouse
    private Foco foco = new Foco();
    private Tabla tab_tabla = new Tabla();

    public registro_pro() {
        bar_botones.getBot_insertar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.getBot_guardar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.agregarReporte();

        rep_reporte.setId("rep_reporte");

        rep_reporte.getBot_aceptar().setMetodo("aceptar_reporte");
        rep_reporte.getBot_aceptar().setUpdate("rep_reporte,sel_tab,sel_cal");

        sel_tab.getBot_aceptar().setMetodo("aceptar_reporte");
        sel_tab.getBot_aceptar().setUpdate("sel_tab,sel_cal");

        gru_pantalla.getChildren().add(rep_reporte);

        sel_rep.setId("sel_rep");
        gru_pantalla.getChildren().add(sel_rep);

        sel_cal.setId("sel_cal");
        sel_cal.setMultiple(true);
        sel_cal.getBot_aceptar().setMetodo("aceptar_reporte");
        sel_cal.getBot_aceptar().setUpdate("sel_cal,sel_rep");
        gru_pantalla.getChildren().add(sel_cal);

        sel_tab.setId("sel_tab");
        sel_tab.setSeleccionTabla("select ide_caja,des_caja from tes_caja order by des_caja", "ide_caja");
        sel_tab.getTab_seleccion().setRows(8);
        sel_tab.getBot_aceptar().setMetodo("aceptar_reporte");
        sel_tab.getBot_aceptar().setUpdate("sel_tab,sel_tab2");
        gru_pantalla.getChildren().add(sel_tab);


        //configuracion de la tabla de clientes (cabecera)
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("reg_ficha", "ide_rp_ficha", 1);
        tab_tabla1.setCampoOrden("ide_rp_ficha");
        tab_tabla1.getColumna("fec_inscri").setValorDefecto(utilitario.getFechaActual());
        tab_tabla1.getColumna("pos_actual").setCombo("select ide_cliente,cedula,nombre from rec_clientes_reg;");
        tab_tabla1.getColumna("pos_actual").setAutoCompletar();
        tab_tabla1.getColumna("pos_anterior").setCombo("select ide_cliente,cedula,nombre from rec_clientes_reg");
        tab_tabla1.getColumna("pos_anterior").setAutoCompletar();
        tab_tabla1.getColumna("adquisicion").setCombo("select ide_reg_adqu,des_reg_adqu from reg_adquision");
        tab_tabla1.getColumna("adquisicion").setAutoCompletar();
        tab_tabla1.getColumna("documento").setCombo("select ide_reg_document,des_reg_document from reg_documento");
        tab_tabla1.getColumna("documento").setAutoCompletar();
        tab_tabla1.getColumna("entidad").setCombo("select ide_reg_entid,des_reg_entid from reg_entid_leg");
        tab_tabla1.getColumna("entidad").setAutoCompletar();


        tab_tabla1.setTipoFormulario(true);
        tab_tabla1.getGrid().setColumns(4);
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.dibujar();

        //configuracion de la tabla de operadoras moviles (detalle)
        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("reg_prohibicion", "cod_rp_prohibicion", 2);
        tab_tabla2.setCampoOrden("cod_rp_prohibicion");
        tab_tabla2.setCampoForanea("id_ficha");
        tab_tabla2.dibujar();

        PanelTabla pat_panel1 = new PanelTabla();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);
        pat_panel2.setPanelTabla(tab_tabla2);

        div_division.setId("div_division");
        div_division.dividir2(pat_panel1, pat_panel2, "50%", "H");

        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);
  
    }

    public void insertar() {
        utilitario.getTablaisFocus().insertar();
    }

    public void guardar() {
        tab_tabla1.guardar();
        tab_tabla2.guardar();
        utilitario.getConexion().guardarPantalla();
    }

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

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }

    public void abrir_reporte() {
//Se ejecuta cuando da click en el/-//- boton de Reportes de la Barra  
        Integer cct;
        rep_reporte.dibujar();
        System.out.println("si se encontro el M");
        cct = Integer.parseInt(tab_tabla1.getValor("ide_rp_ficha").toString());
        System.out.println("si se encontro el M" + cct);
    }
    Map parametros = new HashMap();

    public void aceptar_reporte() {
        String ide_empl = "-1";
        Integer cct;
        List list_sql1 = utilitario.getConexion().consultar("select nick_usua from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
            ide_empl = String.valueOf(list_sql1.get(0));
        }


        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat texto = new java.text.SimpleDateFormat("yyyy-MM-dd");
        //Se ejecuta cuando se selecciona un reporte de la lista

        if (rep_reporte.getReporteSelecionado().equals("formato inscripcion")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                parametros.put("ide", Integer.parseInt(tab_tabla1.getValor("ide_rp_ficha").toString()));
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
                rep_reporte.cerrar();
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("formato posesion efectiva")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                parametros.put("ide", Integer.parseInt(tab_tabla1.getValor("ide_rp_ficha").toString()));
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
                rep_reporte.cerrar();
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("formato prohibicion")) {
            if (rep_reporte.isVisible()) {
                try {
                    cct = Integer.parseInt(tab_tabla2.getValor("cod_rp_prohibicion").toString());
                } catch (NullPointerException e) { //codigo que ejecuta al capturar la excepcion
                    cct = 0;
                }
                parametros = new HashMap();
                parametros.put("ide", cct);
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
                rep_reporte.cerrar();
                sel_rep.dibujar();
            }
        }
    }

    public Grupo getGru_pantalla() {
        return gru_pantalla;
    }

    public void setGru_pantalla(Grupo gru_pantalla) {
        this.gru_pantalla = gru_pantalla;
    }

    public Reporte getRep_reporte() {
        return rep_reporte;
    }

    public void setRep_reporte(Reporte rep_reporte) {
        this.rep_reporte = rep_reporte;
    }

    public SeleccionCalendario getSel_cal() {
        return sel_cal;
    }

    public void setSel_cal(SeleccionCalendario sel_cal) {
        this.sel_cal = sel_cal;
    }

    public SeleccionFormatoReporte getSel_rep() {
        return sel_rep;
    }

    public void setSel_rep(SeleccionFormatoReporte sel_rep) {
        this.sel_rep = sel_rep;
    }

    public SeleccionTabla getSel_tab() {
        return sel_tab;
    }

    public void setSel_tab(SeleccionTabla sel_tab) {
        this.sel_tab = sel_tab;
    }
}