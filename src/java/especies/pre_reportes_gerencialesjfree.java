/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package especies;

import framework.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.primefaces.component.chart.bar.BarChart;
import org.primefaces.component.chart.line.LineChart;
import org.primefaces.component.chart.pie.PieChart;
import org.primefaces.component.graphicimage.GraphicImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import sistema.*;

/**
 *
 * @author Byron
 */
public class pre_reportes_gerencialesjfree {

    private Utilitario utilitario = new Utilitario();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private GraphicImage ima_imagen = new GraphicImage();
    private PieChartModel modelo_pastel = new PieChartModel();
    private PieChart grafico_pastel = new PieChart();

    public pre_reportes_gerencialesjfree() {

        bar_botones.getBot_insertar().setRendered(false);
        bar_botones.getBot_guardar().setRendered(false);
        bar_botones.getBot_eliminar().setRendered(false);
        bar_botones.quitarBotonsNavegacion();

        crear_grafico_barra();

//        grafico_barra.setOrientation("horizontal");

        crear_grafico_pastel();
        grafico_pastel.setValue(modelo_pastel);
        grafico_pastel.setTitle("GRAFICO RECAUDACION POR AÑOS");
        grafico_pastel.setSeriesColors("66cc66, 93b75f, E7E658, cc6666");


        div_division.dividir2(ima_imagen, grafico_pastel, "50%", "V");
        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);

    }

    public void crear_grafico_pastel() {
        List lis = utilitario.getConexion().consultar("select sum(valor),fecha from "
                + "(select valor,cast (extract (year from fecha)as integer) as fecha from ( "
                + "select SUM(valor1) as valor,fecha, '1' as tipo "
                + " from rec_entrega_detalle where fecha between '2010-01-01' and '2012-12-31' "
                + "GROUP BY fecha union "
                + "select sum(d.valor*dv.cantidad_d)*(-1),dv.fecha_d, '2' as ti "
                + "from rec_devolucion dv,rec_entrega_detalle ed,rec_entrega_especies ee,rec_detalle d "
                + "where dv.ide_entrega_detalle=ed.ide_entrega_detalle "
                + "and ee.ide_entrega=ed.ide_entrega and d.ide_detalle=ee.ide_detalle "
                + "and dv.fecha_d between '2010-01-01' and '2012-12-31' "
                + "GROUP BY dv.fecha_d union "
                + "select SUM(vd.cantidad*vd.valor_vd),vd.fecha_vd,'3' FROM "
                + "rec_venta_devolucion vd,rec_devolucion dv,rec_entrega_detalle ed "
                + "where dv.ide_entrega_detalle=ed.ide_entrega_detalle "
                + "and vd.ide_devolucion=dv.ide_devolucion "
                + "and vd.fecha_vd between '2010-01-01' and '2012-12-31' "
                + "GROUP BY vd.fecha_vd order by tipo)a)b group by fecha "
                + "order by fecha asc");
        for (int i = 0; i < lis.size(); i++) {
            Object[] fila = (Object[]) lis.get(i);
            modelo_pastel.set(fila[1] + "", Float.parseFloat(fila[0] + ""));
        }


    }

    public void crear_grafico_barra() {

        List lis = utilitario.getConexion().consultar("select sum(valor),fecha from "
                + "(select valor,cast (extract (year from fecha)as integer) as fecha from ( "
                + "select SUM(valor1) as valor,fecha, '1' as tipo "
                + " from rec_entrega_detalle where fecha between '2010-01-01' and '2012-12-31' "
                + "GROUP BY fecha union "
                + "select sum(d.valor*dv.cantidad_d)*(-1),dv.fecha_d, '2' as ti "
                + "from rec_devolucion dv,rec_entrega_detalle ed,rec_entrega_especies ee,rec_detalle d "
                + "where dv.ide_entrega_detalle=ed.ide_entrega_detalle "
                + "and ee.ide_entrega=ed.ide_entrega and d.ide_detalle=ee.ide_detalle "
                + "and dv.fecha_d between '2010-01-01' and '2012-12-31' "
                + "GROUP BY dv.fecha_d union "
                + "select SUM(vd.cantidad*vd.valor_vd),vd.fecha_vd,'3' FROM "
                + "rec_venta_devolucion vd,rec_devolucion dv,rec_entrega_detalle ed "
                + "where dv.ide_entrega_detalle=ed.ide_entrega_detalle "
                + "and vd.ide_devolucion=dv.ide_devolucion "
                + "and vd.fecha_vd between '2010-01-01' and '2012-12-31' "
                + "GROUP BY vd.fecha_vd order by tipo)a)b group by fecha "
                + "order by fecha asc");
//        ChartSeries serie = new ChartSeries();
//        serie.setLabel("Total Recaudado");
        DefaultPieDataset dataset = new DefaultPieDataset();
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();

        for (int i = 0; i < lis.size(); i++) {

            Object[] fila = (Object[]) lis.get(i);
            dataset.setValue(fila[1] + "", Double.parseDouble(fila[0] + ""));

            dataset1.setValue(Double.parseDouble(fila[0] + ""), fila[1] + "", fila[1] + "");
//
        }




//        modelo_barra.addSeries(serie);

        try {
//            JFreeChart jfreechart = ChartFactory.createPieChart(
//                    "GRAFICO RECAUDACION POR AÑOS", dataset, true, true, false);
//            File chartFile = new File("dynamichart");
//          
//            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 375, 300);
//            chart = new DefaultStreamedContent(
//                    new FileInputStream(chartFile), "image/png");

            JFreeChart jfreechart = ChartFactory.createBarChart(
                    "GRAFICO RECAUDACION POR AÑOS", // chart title
                    "Años", // domain axis label
                    "$", // range axis label
                    dataset1, // data
                    PlotOrientation.VERTICAL, // orientation
                    true, // include legend
                    true, // tooltips?
                    false // URLs?
                    );
            
            

            File chartFile = new File("dynamichart");

            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 375, 300);
            chart = new DefaultStreamedContent(
                    new FileInputStream(chartFile), "image/png");


            ima_imagen.setValueExpression("value", creav("chart"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    StreamedContent chart;

    private ValueExpression creav(String str) {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getApplication().getExpressionFactory().createValueExpression(fc.getELContext(), "#{pre_index.clase." + str + "}", Object.class);
    }

    public StreamedContent getChart() {
        return chart;
    }

    public void setChart(StreamedContent chart) {
        this.chart = chart;
    }

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }
}
