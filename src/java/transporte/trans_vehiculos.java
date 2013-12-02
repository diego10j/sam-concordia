/*
 * /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transporte;

import framework.*;
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
public class trans_vehiculos  extends Pantalla {

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
    private Barra bar_botones = new Barra();
    private Tabulador tab_tabulador = new Tabulador();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private Tabla tab_seguro = new Tabla();
    private Tabla tab_revision = new Tabla();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private Boton bot_limpiar = new Boton();
    private VisualizarPDF vp = new VisualizarPDF();
    private Reporte rep_reporte = new Reporte();
    private Boton bot_impresion = new Boton();

    public trans_vehiculos() {

        bar_botones.getBot_insertar().setUpdate("tab_tabla,tab_tabulador:tab_seguro,tab_tabulador:tab_revision");
        bar_botones.getBot_guardar().setUpdate("tab_tabla,tab_tabulador:tab_seguro,tab_tabulador:tab_revision");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla,tab_tabulador:tab_seguro,tab_tabulador:tab_revision,");

        bar_botones.getBot_inicio().setMetodo("inicio");
        bar_botones.getBot_siguiente().setMetodo("siguiente");
        bar_botones.getBot_atras().setMetodo("atras");
        bar_botones.getBot_fin().setMetodo("fin");

        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select b.ide_socios,a.cedula,a.nombre,c.nombre from rec_clientes as a,trans_socios as b,trans_empresa as c where b.ide_cliente=a.ide_cliente and b.ide_empresa=c.ide_empresa order by a.nombre");
        aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_tabla,tab_tabulador:tab_seguro,tab_tabulador:tab_revision");
        bar_botones.agregarComponente(aut_filtro_cliente);

        Espacio esp = new Espacio();
        esp.setWidth("25");
        esp.setHeight("1");
        bar_botones.agregarComponente(esp);

        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("aut_filtro_cliente,tab_tabla,tab_tabulador");
        bar_botones.agregarComponente(bot_limpiar);

        /**
         * ************************************************************************************************************************
         */
        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("trans_vehiculos", "ide_vehiculo", 1);
        tab_tabla.setCampoOrden("ide_vehiculo");
        tab_tabla.getColumna("ide_vehiculo").setVisible(false);
        tab_tabla.setCondicion("ide_vehiculo = -1");
        tab_tabla.getColumna("ide_modelo").setCombo("select ide_modelo,des_modelo from trans_modelos order by des_modelo;");
        tab_tabla.getColumna("ide_modelo").setAutoCompletar();
        tab_tabla.setTipoFormulario(true);
        tab_tabla.getGrid().setColumns(8);
        tab_tabla.dibujar();
        tab_tabla.agregarRelacion(tab_seguro);
        tab_tabla.agregarRelacion(tab_revision);
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);
        /**
         * ***************************************************************************
         */
        tab_seguro.setId("tab_seguro");
        tab_seguro.setIdCompleto("tab_tabulador:tab_seguro");
        tab_seguro.setTabla("trans_seguro", "ide_seguro", 2);
        tab_seguro.getColumna("ide_seguro").setVisible(false);
        tab_seguro.setTipoFormulario(true);
        tab_seguro.getGrid().setColumns(8);
        tab_seguro.setCampoForanea("ide_vehiculo");
        tab_seguro.dibujar();
        PanelTabla pat_panel3 = new PanelTabla();
        pat_panel3.setPanelTabla(tab_seguro);

        /*////////////////////////////////////////////////////////////////////////////*/
        tab_revision.setId("tab_revision");
        tab_revision.setIdCompleto("tab_tabulador:tab_revision");
        tab_revision.setTabla("trans_revision_vehicular", "ide_revision", 3);
        tab_revision.setTipoFormulario(true);
        tab_revision.getGrid().setColumns(8);
        tab_revision.setCampoForanea("ide_vehiculo");
        tab_revision.dibujar();
        PanelTabla pat_panel4 = new PanelTabla();
        pat_panel4.setPanelTabla(tab_revision);



        /* *************************************************************/
        vp.setId("vp");
        vp.setTitle("Ficha Patentes");
        gru_pantalla.getChildren().add(vp);

        bot_impresion.setValue("Impresion");
        bot_impresion.setMetodo("impresion");
        bar_botones.agregarComponente(bot_impresion);


        /* *******************************************************/
        tab_tabulador.setId("tab_tabulador");
        tab_tabulador.agregarTab("SEGURO", pat_panel3);
        tab_tabulador.agregarTab("REVISION VEHICULAR", pat_panel4);
        div_division.setId("div_division");
        div_division.dividir2(pat_panel, tab_tabulador, "65%", "H");
        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);
     
    }

    public void limpiar() {
        tab_tabla.limpiar();
        aut_filtro_cliente.limpiar();
    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro_cliente.onSelect(evt);
        tab_tabla.setCondicion("ide_socios = " + aut_filtro_cliente.getValor());
        tab_tabla.ejecutarSql();
        tab_seguro.ejecutarValorForanea(tab_tabla.getValorSeleccionado());
        tab_revision.ejecutarValorForanea(tab_tabla.getValorSeleccionado());
        utilitario.addUpdate("tab_tabulador");
    }

    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
    }

    public void insertar() {
        if (aut_filtro_cliente.getValor() != null) {
            if (tab_tabla.isFocus()) {
                if (!tab_tabla.isFilaInsertada()) {
                    tab_tabla.getColumna("ide_socios").setValorDefecto(aut_filtro_cliente.getValor());
                    tab_tabla.insertar();
                } else {
                    utilitario.agregarMensaje("No se puede insertar", "Debe grabar el registro actual");
                }
            } else if (tab_seguro.isFocus()) {
                tab_seguro.insertar();
            } else if (tab_revision.isFocus()) {
                tab_revision.insertar();
            }
        } else {
            utilitario.agregarMensajeInfo("Atención", "Debe seleccionar un contribuyente");
        }
    }

    public void guardar() {
        tab_tabla.guardar();
        tab_seguro.guardar();
        tab_revision.guardar();
        utilitario.getConexion().guardarPantalla();
    }

    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
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

    public Tabla gettab_seguro() {
        return tab_seguro;
    }

    public void settab_seguro(Tabla tab_seguro) {
        this.tab_seguro = tab_seguro;
    }

    public Tabla gettab_revision() {
        return tab_revision;
    }

    public void settab_revision(Tabla tab_revision) {
        this.tab_revision = tab_revision;
    }

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }

    public Boton getBot_limpiar() {
        return bot_limpiar;
    }

    public void setBot_limpiar(Boton bot_limpiar) {
        this.bot_limpiar = bot_limpiar;
    }

    public void impresion() {
        Map parametros = new HashMap();
        parametros.put("IDE", Integer.parseInt(tab_tabla.getValor("ide_pat_ficha").toString()));
        System.out.println(" " + Integer.parseInt(tab_tabla.getValor("ide_pat_ficha").toString()));
        vp.setVisualizarPDF("rep_patente/rep_patentes.jasper", parametros);
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