/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package especies;

import framework.*;
import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import org.primefaces.component.chart.bar.BarChart;
import org.primefaces.component.chart.line.LineChart;
import org.primefaces.component.chart.pie.PieChart;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import sistema.Pantalla;

/**
 *
 * @author Byron
 */
public class pre_reportes_gerenciales extends Pantalla{

    //private Utilitario utilitario = new Utilitario();
 //   private Barra bar_botones = new Barra();
    private Division div_division = new Division();
  //  private Grupo gru_pantalla = new Grupo();
    private CartesianChartModel modelo_barra = new CartesianChartModel();
    private BarChart grafico_barra = new BarChart();
    private PieChartModel modelo_pastel = new PieChartModel();
    private PieChart grafico_pastel = new PieChart();
    private Combo cmb_opcion = new Combo();
    private Grupo grid1 = new Grupo();
    private Grupo grid2 = new Grupo();
    private SeleccionCalendario sel_cal = new SeleccionCalendario();
    private Boton btn_parametros = new Boton();

    public pre_reportes_gerenciales() {

        bar_botones.getBot_insertar().setRendered(false);
        bar_botones.getBot_guardar().setRendered(false);
        bar_botones.getBot_eliminar().setRendered(false);
        bar_botones.quitarBotonsNavegacion();
        btn_parametros.setId("btn_parametros");
        btn_parametros.setValue("Rango Fechas");
        btn_parametros.setMetodo("recoger_parametros_fecha");
        btn_parametros.setUpdate("sel_cal,grid1,grid2");

        cmb_opcion.setId("cmb_opcion");
        List lista = new ArrayList();
        Object fila1[] = {
            "AÑOS", "AÑOS"
        };
        Object fila2[] = {
            "MESES", "MESES"
        };
        lista.add(fila1);
        lista.add(fila2);


        cmb_opcion.setCombo(lista);
        cmb_opcion.eliminarVacio();
        cmb_opcion.setMetodo("graficar", "grid1,grid2");

        bar_botones.agregarComponente(cmb_opcion);
        bar_botones.agregarComponente(btn_parametros);

//        crear_grafico_anos();
        grafico_barra.setId("grafico_barra");

        grafico_barra.setTitle("GRAFICO RECAUDACION POR AÑOS");


        grid1.setId("grid1");

        grafico_pastel.setId("grafico_pastel");

        grafico_pastel.setTitle("GRAFICO RECAUDACION POR AÑOS");

        grid2.setId("grid2");

        div_division.setId("div_division");
        div_division.dividir2(grid1, grid2, "70%", "V");
        //gru_pantalla.getChildren().add(bar_botones);
       agregarComponente(div_division);

        sel_cal.setId("sel_cal");
        sel_cal.setMultiple(true);
        sel_cal.getBot_aceptar().setMetodo("graficar");
        sel_cal.getBot_aceptar().setUpdate("sel_cal,grid1,grid2");

      agregarComponente(sel_cal);

       
    }

    public void recoger_parametros_fecha() {
        sel_cal.dibujar();
    }

    public void graficar() {
        sel_cal.cerrar();
        grid1.getChildren().clear();
        grid2.getChildren().clear();

        if (sel_cal.getFecha1String() != null && sel_cal.getFecha2String() != null) {
            if (cmb_opcion.getValue().toString().equals("AÑOS")) {
                crear_grafico_anos();
                grafico_barra.setValue(modelo_barra);
                grafico_pastel.setValue(modelo_pastel);
                grid1.getChildren().add(grafico_barra);
                grid2.getChildren().add(grafico_pastel);
            } else {
                crear_grafico_meses();
                grafico_barra.setValue(modelo_barra);
                grid1.getChildren().add(grafico_barra);

            }
        } else {
            utilitario.agregarNotificacionInfo("Reportes Gerenciales", "Debe Ingresar un Rango de Fechas para generar el Reporte");
        }
    }

    public void crear_grafico_meses() {
        grafico_barra.setTitle("GRAFICO TOTAL RECAUDADO POR CADA MES");
        List lis = utilitario.getConexion().consultar("select sum(valor),fecha,fecha1 from "
                + "(select valor,cast (extract (year from fecha) as integer) as fecha,cast (extract (month from fecha) as integer) as fecha1 from ( "
                + "select SUM(valor1) as valor,fecha, '1' as tipo from rec_entrega_detalle where fecha between '" + sel_cal.getFecha1String() + "' and '" + sel_cal.getFecha2String() + "' "
                + "GROUP BY fecha union select sum(d.valor*dv.cantidad_d)*(-1),dv.fecha_d, '2' as ti "
                + "from rec_devolucion dv,rec_entrega_detalle ed,rec_entrega_especies ee,rec_detalle d "
                + "where dv.ide_entrega_detalle=ed.ide_entrega_detalle and ee.ide_entrega=ed.ide_entrega "
                + "and d.ide_detalle=ee.ide_detalle and dv.fecha_d between '" + sel_cal.getFecha1String() + "' and '" + sel_cal.getFecha2String() + "' "
                + "GROUP BY dv.fecha_d union select SUM(vd.cantidad*vd.valor_vd),vd.fecha_vd,'3' FROM "
                + "rec_venta_devolucion vd,rec_devolucion dv,rec_entrega_detalle ed where dv.ide_entrega_detalle=ed.ide_entrega_detalle "
                + "and vd.ide_devolucion=dv.ide_devolucion and vd.fecha_vd between '" + sel_cal.getFecha2String() + "' and '" + sel_cal.getFecha2String() + "' "
                + "GROUP BY vd.fecha_vd order by tipo)a)b group by fecha,fecha1 order by fecha,fecha1 asc ");


        if (!lis.isEmpty()) {
            modelo_barra = new CartesianChartModel();
            modelo_pastel = new PieChartModel();

            Object[] fila = (Object[]) lis.get(0);
            String año = "";
            ChartSeries serie = new ChartSeries();
            fila = (Object[]) lis.get(0);
            String mes = "1";
            año = fila[1].toString();
            int anio;
            int bandera_año = -1;
            for (int j = 1; j <= 12; j++) {
                for (int i = 0; i < lis.size(); i++) {

                    fila = (Object[]) lis.get(i);
                    if (mes.equals(fila[2].toString())) {
                        String mes_l = retornar_mes(Integer.parseInt(fila[2] + ""));
                        serie.setLabel(mes_l);
                        serie.set(fila[1] + "", Float.parseFloat(fila[0] + ""));
                        bandera_año = 1;
                    } else {
                        if (año.equals(fila[1].toString())) {
                            if (bandera_año == -1) {
                                String mes_l = retornar_mes(j);
                                serie.setLabel(mes_l);
                                serie.set(fila[1] + "", Float.parseFloat("0"));
                                bandera_año = 1;
                            }
                        } else {
                            año = fila[1].toString();
                        }
                    }
                }
                bandera_año = -1;
                modelo_barra.addSeries(serie);
                serie = new ChartSeries();
                mes = j + 1 + "";

                fila = (Object[]) lis.get(0);
                año = fila[1].toString();

            }
        } else {
            utilitario.agregarNotificacionInfo("Reporte Gerencial", "Error La Fecha Inicial no puede ser mayor que la Fecha Final");
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

    public void crear_grafico_anos() {

        grafico_barra.setTitle("GRAFICO TOTAL RECAUDADO POR CADA AÑO");
        List lis = utilitario.getConexion().consultar("select sum(valor),fecha from "
                + "(select valor,cast (extract (year from fecha)as integer) as fecha from ( "
                + "select SUM(valor1) as valor,fecha, '1' as tipo "
                + " from rec_entrega_detalle where fecha between '" + sel_cal.getFecha1String() + "' and '" + sel_cal.getFecha2String() + "' "
                + "GROUP BY fecha union "
                + "select sum(d.valor*dv.cantidad_d)*(-1),dv.fecha_d, '2' as ti "
                + "from rec_devolucion dv,rec_entrega_detalle ed,rec_entrega_especies ee,rec_detalle d "
                + "where dv.ide_entrega_detalle=ed.ide_entrega_detalle "
                + "and ee.ide_entrega=ed.ide_entrega and d.ide_detalle=ee.ide_detalle "
                + "and dv.fecha_d between '" + sel_cal.getFecha1String() + "' and '" + sel_cal.getFecha2String() + "' "
                + "GROUP BY dv.fecha_d union "
                + "select SUM(vd.cantidad*vd.valor_vd),vd.fecha_vd,'3' FROM "
                + "rec_venta_devolucion vd,rec_devolucion dv,rec_entrega_detalle ed "
                + "where dv.ide_entrega_detalle=ed.ide_entrega_detalle "
                + "and vd.ide_devolucion=dv.ide_devolucion "
                + "and vd.fecha_vd between '" + sel_cal.getFecha1String() + "' and '" + sel_cal.getFecha2String() + "' "
                + "GROUP BY vd.fecha_vd order by tipo)a)b group by fecha "
                + "order by fecha asc");
        modelo_barra = new CartesianChartModel();
        modelo_pastel = new PieChartModel();
        ChartSeries serie = new ChartSeries();
        serie.setLabel("Total Recaudado");
        for (int i = 0; i < lis.size(); i++) {
            Object[] fila = (Object[]) lis.get(i);
            serie.set(fila[1] + "", Float.parseFloat(fila[0] + ""));
            modelo_pastel.set(fila[1] + "", Float.parseFloat(fila[0] + ""));

        }
        modelo_barra.addSeries(serie);

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
    }
*/
    public CartesianChartModel getModelo_barra() {
        return modelo_barra;
    }

    public void setModelo_barra(CartesianChartModel modelo_barra) {
        this.modelo_barra = modelo_barra;
    }

    public SeleccionCalendario getSel_cal() {
        return sel_cal;
    }

    public void setSel_cal(SeleccionCalendario sel_cal) {
        this.sel_cal = sel_cal;
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
