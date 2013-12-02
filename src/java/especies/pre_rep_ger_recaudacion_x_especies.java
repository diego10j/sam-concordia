/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package especies;

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
import sistema.Pantalla;

/**
 *
 * @author Byron
 */
public class pre_rep_ger_recaudacion_x_especies extends Pantalla {

   // private Utilitario utilitario = new Utilitario();
   // private Barra bar_botones = new Barra();
    private Division div_division = new Division();
//    private Grupo gru_pantalla = new Grupo();
    private GraphicImage ima_imagen = new GraphicImage();
    private Boton btn_parametros = new Boton();
    private SeleccionCalendario sel_cal = new SeleccionCalendario();
    private SeleccionTabla sel_tab = new SeleccionTabla();
    private int ancho = 1200;
    private int alto = 700;

    public pre_rep_ger_recaudacion_x_especies() {

        bar_botones.getBot_insertar().setRendered(false);
        bar_botones.getBot_guardar().setRendered(false);
        bar_botones.getBot_eliminar().setRendered(false);
        bar_botones.quitarBotonsNavegacion();

        ima_imagen.setId("ima_imagen");
        btn_parametros.setId("btn_parametros");
        btn_parametros.setValue("Rango Fechas");
        btn_parametros.setMetodo("recoger_parametros_fecha");
        btn_parametros.setUpdate("sel_cal");

        sel_cal.setId("sel_cal");
        sel_cal.setMultiple(true);
        sel_cal.getBot_aceptar().setMetodo("recoger_especies");
        sel_cal.getBot_aceptar().setUpdate("sel_cal,sel_tab");

        agregarComponente(sel_cal);
      //  gru_pantalla.getChildren().add(sel_cal);

        div_division.setId("div_division");

        div_division.dividir1(ima_imagen);
        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);
        bar_botones.agregarComponente(btn_parametros);

        sel_tab.setId("sel_tab");
        sel_tab.setSeleccionTabla("select ide_documento,descripcion from rec_especies order by descripcion", "ide_documento");
//            sel_tab.getTab_seleccion().getColumna("descripcion").setFiltro(true);
        sel_tab.getTab_seleccion().setRows(8);
       // gru_pantalla.getChildren().add(sel_tab);
        agregarComponente(sel_tab);
        sel_tab.getBot_aceptar().setMetodo("graficar");
        sel_tab.getBot_aceptar().setUpdate("sel_tab,ima_imagen");



    }

    public void recoger_especies() {
        sel_cal.cerrar();
        sel_tab.getTab_seleccion().limpiar();
        sel_tab.getTab_seleccion().setSql("select ide_documento,descripcion from rec_especies order by descripcion");
        sel_tab.getTab_seleccion().ejecutarSql();

        sel_tab.dibujar();
    }

    public void recoger_parametros_fecha() {

        sel_cal.dibujar();
    }

    public void graficar() {
        sel_tab.cerrar();
        crear_grafico_meses();
//        if (sel_cal.getFecha1String() != null && sel_cal.getFecha2String() != null) {
//            if (cmb_opcion.getValue().toString().equals("AÑOS")) {
//                crear_grafico_anos();
//                grafico_barra.setValue(modelo_barra);
//                grafico_pastel.setValue(modelo_pastel);
//                grid1.getChildren().add(grafico_barra);
//                grid2.getChildren().add(grafico_pastel);
//            } else {
//                crear_grafico_meses();
//                grafico_barra.setValue(modelo_barra);
//                grid1.getChildren().add(grafico_barra);
//
//            }
//        } else {
//            utilitario.agregarNotificacionInfo("Reportes Gerenciales", "Debe Ingresar un Rango de Fechas para generar el Reporte");
//        }
    }

    public void crear_grafico_meses() {


        List lis = utilitario.getConexion().consultar("select sum(tot),descripcion,fecha,fecha1 from "
                + "(select SUM(valore) as tot ,descripcion,cast (extract (year from fecha) as integer) as fecha, "
                + "cast (extract (MONTH from fecha) as integer) as fecha1 from ( "
                + "select sum (b.valor1) as valore,ide_documento,descripcion,b.fecha from rec_especies, "
                + "(select ide_detalle,fecha,valor1 from rec_entrega_detalle where fecha BETWEEN '" + sel_cal.getFecha1String() + "' and '" + sel_cal.getFecha2String() + "' "
                + "and ide_detalle in (select ide_detalle from rec_detalle where ide_documento in (" + sel_tab.getSeleccionados() + ")))b "
                + "where ide_documento in (select ide_documento from rec_detalle where ide_detalle=b.ide_detalle) "
                + "GROUP BY ide_documento,b.fecha "
                + "union "
                + "select sum(b.val*(-1)),e.ide_documento,descripcion,b.fecha_d from rec_especies e, "
                + "(select cantidad_d,d.ide_documento,dv.cantidad_d*d.valor as val,dv.fecha_d from rec_devolucion dv,rec_detalle d "
                + "where fecha_d BETWEEN '" + sel_cal.getFecha1String() + "' and '" + sel_cal.getFecha2String() + "' and dv.ide_detalle =d.ide_detalle "
                + "and d.ide_documento in (" + sel_tab.getSeleccionados() + "))b where b.ide_documento=e.ide_documento "
                + "GROUP BY e.ide_documento,b.fecha_d "
                + "union "
                + "select sum (b.valor_vd),ide_documento,descripcion,b.fecha_vd from rec_especies, "
                + "(select ide_detalle,fecha_vd,valor_vd from rec_venta_devolucion "
                + "where fecha_vd BETWEEN '" + sel_cal.getFecha1String() + "' and '" + sel_cal.getFecha2String() + "' "
                + "and ide_detalle in (select ide_detalle from rec_detalle where ide_documento in (" + sel_tab.getSeleccionados() + ")))b "
                + "where ide_documento in (select ide_documento from rec_detalle where ide_detalle=b.ide_detalle) "
                + "group by ide_documento,b.fecha_vd )a "
                + "group by descripcion,fecha,fecha1)b group by descripcion,fecha,fecha1 order by fecha,fecha1 asc");

        if (lis != null && !lis.isEmpty()) {

            Object[] fila = (Object[]) lis.get(0);
            List lista_series = new ArrayList();
            List lista_categorias = new ArrayList();
            List series = new ArrayList();
            List categorias = new ArrayList();

            String especie = "";
            String categoria = "";
            String año = "";
            String mes = "";
            for (int i = 0; i < lis.size(); i++) {
                fila = (Object[]) lis.get(i);
                especie = fila[1].toString();
                lista_series.add(i, especie);
                categoria = fila[2].toString().concat(" ").concat(retornar_mes(Integer.parseInt(fila[3].toString())));
                lista_categorias.add(i, categoria);
            }


            String rep = "repetido";
            for (int i = 0; i < lista_series.size(); i++) {
                especie = lista_series.get(i).toString();
                for (int j = i + 1; j < lista_series.size(); j++) {
                    if (especie.equals(lista_series.get(j))) {
                        lista_series.set(j, rep);
                    }
                }
            }
            int indice = 0;
            for (int i = 0; i < lista_series.size(); i++) {
                if (rep.equals(lista_series.get(i))) {
                } else {
                    series.add(indice, lista_series.get(i).toString());
                    indice++;
                }
            }

            for (int i = 0; i < lista_categorias.size(); i++) {
                especie = lista_categorias.get(i).toString();
                for (int j = i + 1; j < lista_categorias.size(); j++) {
                    if (especie.equals(lista_categorias.get(j))) {
                        lista_categorias.set(j, rep);
                    }
                }
            }
            indice = 0;
            for (int i = 0; i < lista_categorias.size(); i++) {
                if (rep.equals(lista_categorias.get(i))) {
                } else {
                    categorias.add(indice, lista_categorias.get(i).toString());
                    indice++;
                }
            }

//        for (int i = 0; i < series.size(); i++) {
//            System.out.println("serie " + series.get(i));
//        }
//        for (int i = 0; i < categorias.size(); i++) {
//            System.out.println("categoria " + categorias.get(i));
//        }
            // create the dataset...

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i < series.size(); i++) {
                for (int j = 0; j < categorias.size(); j++) {
                    año = categorias.get(j).toString().substring(0, 4);
                    mes = categorias.get(j).toString().substring(5, categorias.get(j).toString().length());
                    mes = String.valueOf(retornar_mes_num(mes));
                    for (int k = 0; k < lis.size(); k++) {
                        fila = (Object[]) lis.get(k);
                        if (fila[1].toString().equals(series.get(i))
                                && fila[2].toString().equals(año)
                                && fila[3].toString().equals(mes)) {
                            dataset.addValue(Double.parseDouble(fila[0].toString()), series.get(i).toString(), categorias.get(j).toString());

                        }

                    }

                }

            }

            JFreeChart jfreechart = ChartFactory.createBarChart(
                    "GRAFICO RECAUDACION POR MESES", // chart title
                    "Meses", // domain axis label
                    "$", // range axis label
                    dataset, // data
                    PlotOrientation.HORIZONTAL, // orientation
                    true, // include legend
                    true, // tooltips?
                    true // URLs?
                    );

            jfreechart.setBackgroundPaint(Color.white);
            jfreechart.setBorderVisible(true);

            // get a reference to the plot for further customisation...
            CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();

            plot.setBackgroundPaint(Color.white);

            // set the range axis to display integers only...
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            // disable bar outlines...
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setDrawBarOutline(false);
            renderer.setItemLabelsVisible(true);
            renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());


            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(
                    CategoryLabelPositions.createUpRotationLabelPositions(
                    Math.PI / 6.0));

            File chartFile = new File("dynamichart");

            if ((categorias.size() >= 1 && categorias.size() <= 2) && (series.size() >= 1 && series.size() <= 4)) {
                try {
                    ChartUtilities.saveChartAsPNG(chartFile, jfreechart, ancho, alto - 450);
                    chart = new DefaultStreamedContent(
                            new FileInputStream(chartFile), "image/png");
                } catch (Exception e) {
                }
            } else {


                if ((categorias.size() >= 2 && categorias.size() <= 4) && (series.size() > 1 && series.size() <= 4)) {
                    try {
                        ChartUtilities.saveChartAsPNG(chartFile, jfreechart, ancho, alto - 200);
                        chart = new DefaultStreamedContent(
                                new FileInputStream(chartFile), "image/png");
                    } catch (Exception e) {
                    }

                } else {

                    if ((categorias.size() > 10 && categorias.size() <= 18) && (series.size() > 4 && series.size() <= 8)) {
                        try {
                            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, ancho, alto + 900);
                            chart = new DefaultStreamedContent(
                                    new FileInputStream(chartFile), "image/png");
                        } catch (Exception e) {
                        }
                    } else {
                        if ((categorias.size() > 18 && categorias.size() <= 24) && (series.size() >= 8 && series.size() <= 12)) {
                            try {
                                ChartUtilities.saveChartAsPNG(chartFile, jfreechart, ancho, alto + 1500);
                                chart = new DefaultStreamedContent(
                                        new FileInputStream(chartFile), "image/png");
                            } catch (Exception e) {
                            }
                        } else {
                            try {
                                ChartUtilities.saveChartAsPNG(chartFile, jfreechart, ancho, alto);
                                chart = new DefaultStreamedContent(
                                        new FileInputStream(chartFile), "image/png");
                            } catch (Exception e) {
                            }

                        }
                    }
                    }
                }

                ima_imagen.setValueExpression("value", creav("chart"));

            }  else {
            utilitario.agregarNotificacionInfo("Reportes Gerenciales", "No existen Registros ");
        }

    }

    public String retornar_mes(int mes) {
        String mes_letras = "";
        if (mes == 1) {
            mes_letras = "Enero";
        }
        if (mes == 2) {
            mes_letras = "Febrero";
        }
        if (mes == 3) {
            mes_letras = "Marzo";
        }
        if (mes == 4) {
            mes_letras = "Abril";
        }
        if (mes == 5) {
            mes_letras = "Mayo";
        }
        if (mes == 6) {
            mes_letras = "Junio";
        }
        if (mes == 7) {
            mes_letras = "Julio";
        }
        if (mes == 8) {
            mes_letras = "Agosto";
        }
        if (mes == 9) {
            mes_letras = "Septiembre";
        }
        if (mes == 10) {
            mes_letras = "Octubre";
        }
        if (mes == 11) {
            mes_letras = "Noviembre";
        }
        if (mes == 12) {
            mes_letras = "Diciembre";
        }
        return mes_letras;
    }

    public int retornar_mes_num(String mes) {
        int mes_num = 0;
        if (mes.equals("Enero")) {
            mes_num = 1;
        }
        if (mes.equals("Febrero")) {
            mes_num = 2;
        }
        if (mes.equals("Marzo")) {
            mes_num = 3;
        }
        if (mes.equals("Abril")) {
            mes_num = 4;
        }
        if (mes.equals("Mayo")) {
            mes_num = 5;
        }
        if (mes.equals("Junio")) {
            mes_num = 6;
        }
        if (mes.equals("Julio")) {
            mes_num = 7;
        }
        if (mes.equals("Agosto")) {
            mes_num = 8;
        }
        if (mes.equals("Septiembre")) {
            mes_num = 9;
        }
        if (mes.equals("Octubre")) {
            mes_num = 10;
        }
        if (mes.equals("Noviembre")) {
            mes_num = 11;
        }
        if (mes.equals("Diciembre")) {
            mes_num = 12;
        }
        return mes_num;
    }
    StreamedContent chart;

    public StreamedContent getChart() {
        return chart;
    }

    public void setChart(StreamedContent chart) {
        this.chart = chart;
    }

    private ValueExpression creav(String str) {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getApplication().getExpressionFactory().createValueExpression(fc.getELContext(), "#{pre_index.clase." + str + "}", Object.class);
    }

 /*   public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }*/

    public SeleccionCalendario getSel_cal() {
        return sel_cal;
    }

    public void setSel_cal(SeleccionCalendario sel_cal) {
        this.sel_cal = sel_cal;
    }

    public SeleccionTabla getSel_tab() {
        return sel_tab;
    }

    public void setSel_tab(SeleccionTabla sel_tab) {
        this.sel_tab = sel_tab;
    }

    @Override
    public void insertar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
