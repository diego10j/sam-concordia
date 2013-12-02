package inventarios;

import framework.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.event.SelectEvent;
import sistema.Pantalla;

/**
 *
 * @author Jhoan torres
 */
public class ord_entrega extends Pantalla {

    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    //private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    //private Grupo gru_pantalla = new Grupo();
    ///// BUSQUEDA DE CLAVES
    private VisualizarPDF vp = new VisualizarPDF();
    private Reporte rep_reporte = new Reporte();
    private Boton bot_impresion = new Boton();

    public ord_entrega() {

        //configuracion de la tabla de clientes (cabecera)
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("inv_orden", "ide_orden", 1);
        tab_tabla1.setNumeroTabla(1);
        tab_tabla1.getColumna("fec_ingr_orden").setValorDefecto(utilitario.getFechaActual());
        tab_tabla1.setTipoFormulario(true);
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.getGrid().setColumns(4);
        tab_tabla1.dibujar();


        //configuracion de la tabla de operadoras moviles (detalle)
        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("inv_det_ord", "ide_det_ord", 2);
        tab_tabla2.getColumna("ide_item").setCombo("select inv_ide,inv_nombre,inv_marca from inv_articulos;");
        tab_tabla2.getColumna("ide_item").setAutoCompletar();
        tab_tabla2.setNumeroTabla(2);
        tab_tabla2.setCampoForanea("ide_orden");
        tab_tabla2.dibujar();

        /**
         * ************************************************************
         */
        vp.setId("vp");
        vp.setTitle("Nota de Credito");
        agregarComponente(vp);


        bot_impresion.setValue("Impresion");
        bot_impresion.setMetodo("impresion");
        bar_botones.agregarComponente(bot_impresion);


        /**
         * ******************************************************
         */
        PanelTabla pat_panel1 = new PanelTabla();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);
        pat_panel2.setPanelTabla(tab_tabla2);

        div_division.setId("div_division");
        div_division.dividir2(pat_panel1, pat_panel2, "50%", "H");

        agregarComponente(div_division);

        //  gru_pantalla.getChildren().add(bar_botones);
        //  gru_pantalla.getChildren().add(div_division);

    }

    @Override
    public void insertar() {
        String sql, empleado = "";
        List sql1 = utilitario.getConexion().consultar("select nombres from munc_empleados where ide_empleado=(select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql1.isEmpty()) {
            empleado = sql1.get(0) + "";
        }
        if (tab_tabla1.isFocus()) {
            if (!tab_tabla1.isFilaInsertada()) {
                tab_tabla1.insertar();
                tab_tabla1.setValor("emisor", empleado);
            } else {
                utilitario.agregarMensaje("No se puede insertar", "Debe grabar el registro actual");
            }

        } else if (tab_tabla2.isFocus()) {
            tab_tabla2.insertar();
        }
    }

    @Override
    public void guardar() {
        tab_tabla1.guardar();
        tab_tabla2.guardar();
        utilitario.getConexion().guardarPantalla();
    }

    @Override
    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
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

    public Tabla getTab_tabla2() {
        return tab_tabla2;
    }

    public void setTab_tabla2(Tabla tab_tabla2) {
        this.tab_tabla2 = tab_tabla2;
    }

    /*  public Barra getBar_botones() {
     return bar_botones;
     }

     public void setBar_botones(Barra bar_botones) {
     this.bar_botones = bar_botones;
     }
     /**
     * ***********************************************************
     */
    public void impresion() {
        Map parametros = new HashMap();
        parametros.put("ide", Integer.parseInt(tab_tabla1.getValor("ide_orden").toString()));
        vp.setVisualizarPDF("bodega/orden.jasper", parametros);
        vp.dibujar();
        utilitario.addUpdate("vp");

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
}