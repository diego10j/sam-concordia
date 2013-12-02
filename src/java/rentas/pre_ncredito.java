package rentas;

import framework.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import sistema.*;

/**
 */
public class pre_ncredito extends Pantalla {

    // private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
    //private Barra bar_botones = new Barra();
    private Tabulador tab_tabulador = new Tabulador();
    private Division div_division = new Division();
    //private Grupo gru_pantalla = new Grupo();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private AutoCompletar auc_cliente = new AutoCompletar();
    private Boton bot_limpiar = new Boton();
    private MarcaAgua maa_marca = new MarcaAgua();
    private VisualizarPDF vp = new VisualizarPDF();
    private Reporte rep_reporte = new Reporte();
    private Boton bot_impresion = new Boton();

    public pre_ncredito() {

        bar_botones.getBot_insertar().setUpdate("tab_tabla");
        bar_botones.getBot_guardar().setUpdate("tab_tabla");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla");

        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
        aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_tabla");
        bar_botones.agregarComponente(aut_filtro_cliente);

        Espacio esp = new Espacio();
        esp.setWidth("25");
        esp.setHeight("1");
        bar_botones.agregarComponente(esp);

        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("aut_filtro_cliente,tab_tabla");
        bar_botones.agregarBoton(bot_limpiar);

        /**
         *************************************************************************************
         */
        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("rec_ncredito", "ide_ncredito", 1);
        tab_tabla.setCampoOrden("ide_ncredito");
        tab_tabla.setCondicion("ide_cliente = -1");
        tab_tabla.getColumna("ide_cliente").setVisible(false);
        tab_tabla.getColumna("nc_fecemi").setValorDefecto(utilitario.getFechaActual());
        tab_tabla.getColumna("nc_expedi").setCombo("munc_ubicacion", "ide_ubicacion", "des_ubicacion", "");
        tab_tabla.getColumna("nc_expedi").setAutoCompletar();
        tab_tabla.setTipoFormulario(true);
        tab_tabla.getGrid().setColumns(4);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        /**
         * ************************************************************
         */
        vp.setId("vp");
        vp.setTitle("Nota de Credito");
        agregarComponente(vp);

        bot_impresion.setValue("Impresion");
        bot_impresion.setMetodo("impresion");
        bar_botones.agregarBoton(bot_impresion);


        /**
         * ******************************************************
         */
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);
        //gru_pantalla.getChildren().add(bar_botones);
        agregarComponente(div_division);

    }

    public void limpiar() {
        tab_tabla.limpiar();
        aut_filtro_cliente.limpiar();
    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro_cliente.onSelect(evt);
        tab_tabla.setCondicion("ide_cliente = " + aut_filtro_cliente.getValor());
        tab_tabla.ejecutarSql();
        utilitario.addUpdate("tab_tabla");
    }

    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
    }
@Override
    public void insertar() {

        if (aut_filtro_cliente.getValor() != null) {
            if (!tab_tabla.isFilaInsertada()) {
                tab_tabla.getColumna("ide_cliente").setValorDefecto(aut_filtro_cliente.getValor());
                tab_tabla.insertar();
            } else {
                utilitario.agregarMensaje("No se puede insertar", "Debe grabar el registro actual");
            }
        }
    }
@Override
    public void guardar() {
        tab_tabla.guardar();
        utilitario.getConexion().guardarPantalla();
    }
@Override
    public void eliminar() {
        tab_tabla.eliminar();
    }

    public void seleccionar_tabla(SelectEvent evt) {
        tab_tabla.seleccionarFila(evt);
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
    }*/

    public Boton getBot_limpiar() {
        return bot_limpiar;
    }

    public void setBot_limpiar(Boton bot_limpiar) {
        this.bot_limpiar = bot_limpiar;
    }

    public AutoCompletar getAuc_cliente() {
        return auc_cliente;
    }

    public void setAuc_cliente(AutoCompletar auc_cliente) {
        this.auc_cliente = auc_cliente;
    }

    /**
     * ***********************************************************
     */
    public void impresion() {
        Map parametros = new HashMap();
        parametros.put("ide", Integer.parseInt(tab_tabla.getValor("ide_ncredito").toString()));
        vp.setVisualizarPDF("rep_rentas/ncredito_rep.jasper", parametros);
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