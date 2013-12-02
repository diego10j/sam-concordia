/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transporte;

import framework.*;
import java.util.ArrayList;
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
public class trans_socios  extends Pantalla{

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private Boton bot_limpiar = new Boton();

    public trans_socios() {

        bar_botones.getBot_insertar().setUpdate("tab_tabla1");
        bar_botones.getBot_guardar().setUpdate("tab_tabla1");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla1");

        bar_botones.getBot_inicio().setMetodo("inicio");
        bar_botones.getBot_siguiente().setMetodo("siguiente");
        bar_botones.getBot_atras().setMetodo("atras");
        bar_botones.getBot_fin().setMetodo("fin");

        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
        aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_tabla1");
        bar_botones.agregarComponente(aut_filtro_cliente);

        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("aut_filtro_cliente,tab_tabla1");
        bar_botones.agregarComponente(bot_limpiar);

        //configuracion de la tabla de clientes (cabecera)
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("trans_socios", "ide_socios", 1);
        tab_tabla1.setCampoOrden("ide_socios");
        tab_tabla1.setCondicion("ide_cliente = -1");
        tab_tabla1.getColumna("ide_cliente").setVisible(false);
        tab_tabla1.getColumna("ide_socios").setVisible(false);
        tab_tabla1.getColumna("fecha_ingreso").setValorDefecto(utilitario.getFechaActual());
        tab_tabla1.getColumna("nom_responsable").setLectura(true);
        tab_tabla1.getColumna("ip_responsable").setLectura(true);
        tab_tabla1.getColumna("fecha_responsable").setLectura(true);
        tab_tabla1.getColumna("ide_empresa").setCombo("select ide_empresa,nombre from trans_empresa order by nombre;");
        tab_tabla1.getColumna("ide_empresa").setAutoCompletar();
        tab_tabla1.getColumna("ide_tipo_licencia").setCombo("select ide_tipo_licencia,des_tipo_licencia,detalle_de_vehiculos from trans_tipo_licencia order by des_tipo_licencia;");
        tab_tabla1.getColumna("ide_tipo_licencia").setAutoCompletar();
        tab_tabla1.setTipoFormulario(true);
        tab_tabla1.getGrid().setColumns(4);
        tab_tabla1.dibujar();


        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla1);
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);

        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);
   
    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro_cliente.onSelect(evt);
        tab_tabla1.setCondicion("ide_cliente = " + aut_filtro_cliente.getValor());
        tab_tabla1.ejecutarSql();
    }

    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
    }

    public Boton getBot_limpiar() {
        return bot_limpiar;
    }

    public void setBot_limpiar(Boton bot_limpiar) {
        this.bot_limpiar = bot_limpiar;
    }

    public void limpiar() {
        tab_tabla1.limpiar();
        aut_filtro_cliente.limpiar();
    }

    public void insertar() {
        if (aut_filtro_cliente.getValor() != null) {
            if (!tab_tabla1.isFilaInsertada()) {
                tab_tabla1.getColumna("ide_cliente").setValorDefecto(aut_filtro_cliente.getValor());
                tab_tabla1.insertar();
            } else {
                utilitario.agregarMensaje("No se puede insertar", "Debe grabar el registro actual");
            }
        } else {
            utilitario.agregarMensajeInfo("Atención", "Debe seleccionar un contribuyente");
        }
    }

    public void guardar() {
        actualiza();
        tab_tabla1.guardar();
        utilitario.getConexion().guardarPantalla();
    }

    public void actualiza() {
        String empleado = "";
        List sql1 = utilitario.getConexion().consultar("select nombres from munc_empleados where ide_empleado=(select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql1.isEmpty()) {
            empleado = sql1.get(0) + "";
        }
        System.out.println("este es el empleado" + empleado + utilitario.getIp());
        tab_tabla1.setValor("nom_responsable", empleado);
        tab_tabla1.setValor("ip_responsable", utilitario.getIp());
        tab_tabla1.setValor("fecha_responsable", utilitario.getFechaActual());
        utilitario.addUpdate("tab_tabla1");
    }

    public void eliminar() {
        tab_tabla1.eliminar();
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

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }
}