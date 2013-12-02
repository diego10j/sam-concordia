/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

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
import sistema.*;
import framework.*;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author Desarrollosw
 */
public class reportes_titulos {

    private Utilitario utilitario = new Utilitario();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private MarcaAgua maa_busqueda = new MarcaAgua();
    private Reporte rep_reporte = new Reporte();
    private SeleccionFormatoReporte sel_rep = new SeleccionFormatoReporte();
    private SeleccionCalendario sel_cal = new SeleccionCalendario();
    private SeleccionTabla sel_tab = new SeleccionTabla();
    private SeleccionTabla sel_tab2 = new SeleccionTabla();
    private SeleccionTabla sel_tab3 = new SeleccionTabla();
     private SeleccionTabla sel_tab4 = new SeleccionTabla();
    //para colocar el cursor del mouse
    private Foco foco = new Foco();
    private Tabla tab_tabla = new Tabla();

    public reportes_titulos() {

        bar_botones.getBot_insertar().setRendered(false);
        bar_botones.getBot_guardar().setRendered(false);
        bar_botones.getBot_eliminar().setRendered(false);
        bar_botones.quitarBotonsNavegacion();

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

        sel_tab2.setId("sel_tab2");
        sel_tab2.setSeleccionTabla("select ide_tipo,des_tipo from rec_tipo order by des_tipo", "ide_tipo");
        sel_tab2.getTab_seleccion().setRows(8);
        sel_tab2.getBot_aceptar().setMetodo("aceptar_reporte");
        sel_tab2.getBot_aceptar().setUpdate("sel_tab2,sel_cal");
        sel_tab2.getBot_aceptar().setUpdate("sel_tab2,sel_tab3");
        gru_pantalla.getChildren().add(sel_tab2);

        sel_tab3.setId("sel_tab3");
        sel_tab3.setSeleccionTabla("select ide_concepto,des_concepto from rec_concepto order by des_concepto", "ide_concepto");
        sel_tab3.getTab_seleccion().setRows(8);
        sel_tab3.getBot_aceptar().setMetodo("aceptar_reporte");
        sel_tab3.getBot_aceptar().setUpdate("sel_tab3,sel_cal");
        gru_pantalla.getChildren().add(sel_tab3);
        
        sel_tab4.setId("sel_tab4");
        sel_tab4.setSeleccionTabla("select ide_estado,detalle from rec_estados order by detalle", "ide_estado");
        sel_tab4.getTab_seleccion().setRows(4);
        sel_tab4.getBot_aceptar().setMetodo("aceptar_reporte");
        sel_tab4.getBot_aceptar().setUpdate("sel_tab4,sel_cal");
        gru_pantalla.getChildren().add(sel_tab4);

        div_division.setId("div_division");
        PanelTabla pat_panel = new PanelTabla();
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

    public void abrir_reporte() {
        rep_reporte.dibujar();
    }
    Map parametros = new HashMap();

    public void aceptar_reporte() {
        String ide_empl = "-1";
        List list_sql1 = utilitario.getConexion().consultar("select nick_usua from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
            ide_empl = String.valueOf(list_sql1.get(0));
        }

        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat texto = new java.text.SimpleDateFormat("yyyy-MM-dd");
        //Se ejecuta cuando se selecciona un reporte de la lista

        if (rep_reporte.getReporteSelecionado().equals("Reporte Emision")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("especie", sel_tab.getSeleccionados());
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        }else if (rep_reporte.getReporteSelecionado().equals("Reporte Emision Activa por nombres")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("especie", sel_tab.getSeleccionados());
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        }
        else if (rep_reporte.getReporteSelecionado().equals("Reporte Emision General por Concepto")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_tab3.dibujar();
            } else if (sel_tab3.isVisible()) {
                sel_tab3.cerrar();
                parametros.put("concep", Integer.parseInt(sel_tab3.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab3.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                parametros.put("user", ide_empl);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        }
        else if (rep_reporte.getReporteSelecionado().equals("Reporte Emision Activa por Concepto y Nombre")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_tab3.dibujar();
            } else if (sel_tab3.isVisible()) {
                sel_tab3.cerrar();
                parametros.put("concep", Integer.parseInt(sel_tab3.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab3.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                parametros.put("user", ide_empl);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        }
        else if (rep_reporte.getReporteSelecionado().equals("Reporte Emision Activa")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            }  else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                parametros.put("user", ide_empl);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        }
            else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen por concepto y estado")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_tab4.dibujar();
            } else if (sel_tab4.isVisible()) {
                sel_tab4.cerrar();
                parametros.put("estado", Integer.parseInt(sel_tab4.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab4.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                             sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen de Emision")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("especie", sel_tab.getSeleccionados());
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        }
       else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen Emision por Concepto")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("especie", sel_tab.getSeleccionados());
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen Total de Emision")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("especie", sel_tab.getSeleccionados());
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen Titulos Activos por Concepto")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("especie", sel_tab.getSeleccionados());
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte Cobro por Contribuyente")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_tab.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("pide_mesuno", Integer.parseInt(sel_tab.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen de Titulos Cobrados por Partida")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_tab.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("pide_mesuno", Integer.parseInt(sel_tab.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen Total de Titulos por Partida")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("pide_mesuno", Integer.parseInt(sel_tab.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Recaudacion Total por Titulos y por Especie")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("especie", sel_tab.getSeleccionados());
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } ///////////////////////////////////////// este debe enviar el SPI de pago///////////////////////
        else if (rep_reporte.getReporteSelecionado().equals("Cobros por Ventanilla")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_tab.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("pide_caja", Integer.parseInt(sel_tab.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab.getSeleccionados()));
                sel_tab2.dibujar();
            } else if (sel_tab2.isVisible()) {
                sel_tab2.cerrar();
                parametros.put("pide_tipo", Integer.parseInt(sel_tab2.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab2.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("p_fecha", fecha1);
                //parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen de Cobro por Partida y Tipo de Recaudacion")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("pide_mesuno", Integer.parseInt(sel_tab.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen de Cobro de Titulos por Concepto")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("pide_mesuno", Integer.parseInt(sel_tab.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte de Cobro de Titulos por Concepto y Cajas")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_tab.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("pide_mesuno", Integer.parseInt(sel_tab.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Cobros por Concepto")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_tab.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("pide_caja", Integer.parseInt(sel_tab.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab.getSeleccionados()));
                sel_tab2.dibujar();
            } else if (sel_tab2.isVisible()) {
                sel_tab2.cerrar();
                parametros.put("pide_tipo", Integer.parseInt(sel_tab2.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab2.getSeleccionados()));
                sel_tab3.dibujar();
            } else if (sel_tab3.isVisible()) {
                sel_tab3.cerrar();
                parametros.put("concep", Integer.parseInt(sel_tab3.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab3.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("p_fecha", fecha1);
                parametros.put("p_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen de Titulos Activos por Concepto")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("pide_mesuno", Integer.parseInt(sel_tab.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();
            }
        } else if (rep_reporte.getReporteSelecionado().equals("Reporte Resumen de Titulos Anulados por Concepto")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("pide_mesuno", Integer.parseInt(sel_tab.getSeleccionados()));
                System.out.println(" " + Integer.parseInt(sel_tab.getSeleccionados()));
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                String fecha1 = texto.format(sel_cal.getFecha1());
                String fecha2 = texto.format(sel_cal.getFecha2());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                parametros.put("pide_fechai", fecha1);
                parametros.put("pide_fechaf", fecha2);
                // parametros.put("user", ide_empl);
                System.out.println(" " + fecha1);
                //parametros.put("fecha_fin", sel_cal.getFecha2().toString());
                //parametros.put("fecha_ini", sel_cal.getFecha1().toString());
                System.out.println(" " + fecha2);
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
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

    public SeleccionTabla getSel_tab2() {
        return sel_tab2;
    }

    public void setSel_tab2(SeleccionTabla sel_tab2) {
        this.sel_tab2 = sel_tab2;
    }

    public SeleccionTabla getSel_tab3() {
        return sel_tab3;
    }

    public void setSel_tab3(SeleccionTabla sel_tab3) {
        this.sel_tab3 = sel_tab3;
    }
    
    public SeleccionTabla getSel_tab4() {
        return sel_tab4;
    }

    public void setSel_tab4(SeleccionTabla sel_tab4) {
        this.sel_tab4 = sel_tab4;
    }
}
