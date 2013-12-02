/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import persistencia.Conexion;
import sistema.Pantalla;


/**
 *
 * @author HP
 */
public class pre_cajas_consulta extends Pantalla {

    private Tabla tab_seleccion = new Tabla();
    private Division div_division = new Division();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private Boton bot_limpiar = new Boton();
    private Boton bot_pagar = new Boton();
    private String cliente_actual = "-1";
    private String ide_estado_activo = "1";  //estado Activo de la tabla rec_estados
    private String ide_estado_pagado = "2"; //estado PAGADO de la tabla rec_estados
    private SeleccionTabla tab_seleccion_panel = new SeleccionTabla();
    private Tabla tab_ingreso = new Tabla();
    private Etiqueta total_valores = new Etiqueta();
    private Boton bot_eli_pago = new Boton();
    private MarcaAgua maa_marca = new MarcaAgua();
    private String ide_caja = "-1";
    private VisualizarPDF vpdf_pago = new VisualizarPDF();
    private boolean pagoBanco = false;
    private boolean pagoNotaCredito = false;
    private Dialogo dia_banco = new Dialogo();
    private Dialogo dialNotaCredito = new Dialogo();
    private Texto txt_num_docu = new Texto();
    private AreaTexto detalleNotaCredito = new AreaTexto();
    private  Texto valorNotaCredito = new Texto();
     
    private Texto documentoNotaCRedito = new Texto();
    private Combo com_banco = new Combo();
    private Calendario cal_fecha_depo = new Calendario();
    private Conexion con_acces = new Conexion();
    private Conexion con_sql = new Conexion();

    public pre_cajas_consulta() {

        if (tieneCaja()) {
            bar_botones.getBot_insertar().setRendered(false);
            bar_botones.getBot_guardar().setRendered(false);
            bar_botones.getBot_eliminar().setRendered(false);
            bot_limpiar.setIcon("ui-icon-cancel");
            bot_limpiar.setTitle("Limpiar");
            bot_limpiar.setMetodo("limpiar");
            bot_limpiar.setUpdate("aut_filtro_cliente,tab_seleccion");


            aut_filtro_cliente.setId("aut_filtro_cliente");
            aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
            aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_seleccion");
            bar_botones.agregarComponente(aut_filtro_cliente);
            bar_botones.agregarComponente(bot_limpiar);
            bot_pagar.setId("bot_pagar");
            bot_pagar.setValue("Consultar Deuda");
            bot_pagar.setMetodo("pagar");
            bot_pagar.setUpdate("tab_seleccion_panel");
            Espacio esp = new Espacio();
            esp.setHeight("0");
            esp.setWidth("25");
            bar_botones.agregarComponente(esp);

            bar_botones.agregarComponente(bot_pagar);

            //configuracion de la tabla de valores o titulos (cabecera)
            tab_seleccion.setId("tab_seleccion");
            tab_seleccion.setSql("select ide_ingreso as aux,des_ingreso,valor,parroquia,calles,observaciones from tes_ingreso where ide_titulo=-1 order by aux");
            tab_seleccion.setNumeroTabla(1);
            tab_seleccion.setCampoPrimaria("aux");

            tab_seleccion.getColumna("observaciones").setFiltro(true);
            tab_seleccion.getColumna("calles").setFiltro(true);
            tab_seleccion.getColumna("des_ingreso").setFiltro(true);
            tab_seleccion.getColumna("des_ingreso").setNombreVisual("DETALLE DEL CONCEPTO DE COBRO");
            tab_seleccion.getColumna("parroquia").setNombreVisual("DIRECCION");
            tab_seleccion.getColumna("calles").setNombreVisual("UBICACION");
            tab_seleccion.getColumna("observaciones").setNombreVisual("CLAVE CATASTRAL");
            tab_seleccion.getColumna("des_ingreso").setLongitud(50);
        tab_seleccion.getColumna("parroquia").setLongitud(30);
        tab_seleccion.getColumna("calles").setLongitud(30);
        tab_seleccion.getColumna("observaciones").setLongitud(30);
            tab_seleccion.setCondicion("ide_titulo = -1 and ide_estado = " + ide_estado_activo);
           
            tab_seleccion.setCampoOrden("ide_titulo");
            tab_seleccion.setRows(20);
            tab_seleccion.setTipoSeleccion(true);
            tab_seleccion.dibujar();

            PanelTabla pat_panel1 = new PanelTabla();
            pat_panel1.setPanelTabla(tab_seleccion);

            div_division.setId("div_division");
            div_division.dividir1(pat_panel1);
            

            tab_seleccion_panel.setId("tab_seleccion_panel");
            tab_seleccion_panel.setHeight("80%");
            tab_seleccion_panel.setWidth("70%");
           // tab_seleccion_panel.getTab_seleccion().setScrollable(true);
           // tab_seleccion_panel.getTab_seleccion().setScrollHeight(130);
           // tab_seleccion_panel.getTab_seleccion().setScrollWidth(tab_seleccion_panel.getAnchoPanel()-30);
            tab_seleccion_panel.getTab_seleccion().setRows(0);
            
            tab_seleccion_panel.setSeleccionTabla("select ide_ingreso,des_ingreso,valor,ide_titulo,parroquia,calles,observaciones from tes_ingreso where ide_ingreso in (-1)", "ide_ingreso");
     /*     tab_seleccion_panel.getTab_seleccion().getColumna("modulo").setVisible(true);
            tab_seleccion_panel.getTab_seleccion().getColumna("codigo").setVisible(true);*/
            tab_seleccion_panel.getTab_seleccion().getColumna("des_ingreso").setNombreVisual("DETALLE DEL CONCEPTO DE COBRO");
            tab_seleccion_panel.getTab_seleccion().getColumna("parroquia").setNombreVisual("DIRECCION");
            tab_seleccion_panel.getTab_seleccion().getColumna("calles").setNombreVisual("UBICACION");
            tab_seleccion_panel.getTab_seleccion().getColumna("observaciones").setNombreVisual("CLAVE CATASTRAL");

            tab_seleccion_panel.getTab_seleccion().getColumna("des_ingreso").setLongitud(50);
            tab_seleccion_panel.getTab_seleccion().getColumna("parroquia").setLongitud(30);
            tab_seleccion_panel.getTab_seleccion().getColumna("calles").setLongitud(30);
            tab_seleccion_panel.getTab_seleccion().getColumna("observaciones").setLongitud(30);
            tab_seleccion_panel.getBot_aceptar().setMetodo("cancelarDialogo");
            

            bot_eli_pago.setValue("Eliminar Valores Seleccionados");
            bot_eli_pago.setMetodo("eliminarSeleccionados");
            bot_eli_pago.setUpdate(tab_seleccion_panel.getTab_seleccion().getId() + ",lgri_resumen");


            tab_ingreso.setCondicion("ide_ingreso=-1");
            tab_ingreso.getGrid().setColumns(4);
            tab_ingreso.getColumna("DES_INGRESO").setVisible(false);
            tab_ingreso.getColumna("ide_empleado").setLectura(false);
            tab_ingreso.getColumna("ide_titulo").setVisible(false);
            tab_ingreso.getColumna("ide_tipo").setVisible(false);
            tab_ingreso.getColumna("ide_empleado").setVisible(false);
            tab_ingreso.getColumna("ide_caja").setVisible(false);
            tab_ingreso.getColumna("num_titulo").setVisible(false);
            tab_ingreso.getColumna("ide_estado").setVisible(false);
            tab_ingreso.getColumna("des_ingreso").setVisible(false);
            tab_ingreso.getColumna("nombres").setVisible(false);
            tab_ingreso.getColumna("parroquia").setVisible(false);
            tab_ingreso.getColumna("cedula").setVisible(false);
            tab_ingreso.getColumna("calles").setVisible(false);
            tab_ingreso.getColumna("observaciones").setVisible(false);
            tab_ingreso.getColumna("ip_responsable").setVisible(false);
            tab_ingreso.getColumna("fecha_pago").setVisible(false);
            tab_ingreso.getColumna("valor").setVisible(false);
       //     tab_ingreso.getColumna("codigo").setVisible(false);
            tab_ingreso.getColumna("RESPONSABLE_MODIFICACION").setVisible(false);
            tab_ingreso.getColumna("FECHA_MODIFICACION").setVisible(false);
        //    tab_ingreso.getColumna("modulo").setVisible(false);
            tab_ingreso.getColumna("ide_ingreso").setVisible(false);
            tab_ingreso.getColumna("VALOR_CONSTRUCCION").setVisible(false);
            tab_ingreso.getColumna("BASE_IMPONIBLE").setVisible(false);
            tab_ingreso.getColumna("VALOR_OTRAS_INVERSIONES").setVisible(false);
            tab_ingreso.getColumna("VALOR_TERRENO").setVisible(false);
            tab_ingreso.getColumna("REBAJA_HIPOTECARIA").setVisible(false);
        //    tab_ingreso.getColumna("NUM_TITULO_SQL").setVisible(false);

            tab_ingreso.getColumna("responsable_cobro").setVisible(false);
            tab_ingreso.getColumna("ip_modifica").setVisible(false);
            tab_ingreso.getColumna("ide_banco").setVisible(false);
            tab_ingreso.getColumna("fecha_deposito").setVisible(false);
            tab_ingreso.getColumna("nro_documento").setVisible(false);
            tab_ingreso.getColumna("detalle_not_credito").setVisible(false);
            tab_ingreso.getColumna("valor_not_credito").setVisible(false);
            tab_ingreso.setTipoFormulario(true);
            tab_ingreso.setMostrarNumeroRegistros(false);
            tab_ingreso.dibujar();

            total_valores.setId("total_valores");
            total_valores.setValue(utilitario.getFormatoNumero("0"));
            total_valores.setStyle("color: red; font-size: 18px; align: right;font-weight:bold");

            Etiqueta total_valores_titulo = new Etiqueta();
            total_valores_titulo.setId("total_valores_titulo");
            total_valores_titulo.setValue("TOTAL VALORES A CANCELAR: ");
            total_valores_titulo.setStyle("color: red; font-size: 18px; align: right;font-weight:bold");

            Grid lgri_resumen = new Grid();
            lgri_resumen.setId("lgri_resumen");
            lgri_resumen.setColumns(4);
            lgri_resumen.getChildren().add(total_valores_titulo);
            lgri_resumen.getChildren().add(total_valores);

            Espacio espacio = new Espacio();
            espacio.setWidth("50");
            espacio.setHeight("0");
            lgri_resumen.getChildren().add(espacio);
            lgri_resumen.getChildren().add(bot_eli_pago);

            tab_seleccion_panel.getGri_cuerpo().setHeader(tab_ingreso);
            tab_seleccion_panel.getGri_cuerpo().getChildren().add(lgri_resumen);

            gru_pantalla.getChildren().add(div_division);
            gru_pantalla.getChildren().add(tab_seleccion_panel);

            maa_marca.setFor("aut_filtro_cliente");
            maa_marca.setValue("Buscar Contribuyente");
            gru_pantalla.getChildren().add(maa_marca);

            vpdf_pago.setId("vpdf_pago");
            vpdf_pago.setTitle("Detalle de la Recaudación");
            gru_pantalla.getChildren().add(vpdf_pago);

           
           
            
            
            
        } else {
            utilitario.agregarNotificacionInfo("Mensaje", "No  tiene Asignado un Empleado para poder Realizar Ventas");
        }
    }

  public void cancelarDialogo() {
        if (tab_seleccion_panel.isVisible()) {
            tab_seleccion_panel.cerrar();
        }
        utilitario.getConexion().rollback();
        utilitario.getConexion().getSqlPantalla().clear();
    }  
     

  
    
    String recaudador = "";
    String caja = "";

    private boolean tieneCaja() {
        List sql = utilitario.getConexion().consultar("select nombres, des_caja,tes_caja.ide_caja,munc_empleados.ide_empleado from munc_empleados, tes_caja where ide_empleado = (select ide_empleado from sis_usuario where ide_usua = " + utilitario.getVariable("ide_usua") + ") and ide_caja = (select ide_caja from sis_usuario where ide_usua = " + utilitario.getVariable("ide_usua") + ")");

        if (!sql.isEmpty()) {
            Object[] fila = (Object[]) sql.get(0);
            recaudador = fila[0].toString();
            caja = fila[1].toString();
            tab_ingreso.setId("tab_ingreso");
            tab_ingreso.setTabla("tes_ingreso", "ide_ingreso", 3);
            ide_caja = fila[2].toString();
            tab_ingreso.getColumna("ide_caja").setValorDefecto(ide_caja);
            tab_ingreso.getColumna("ide_empleado").setValorDefecto(fila[3].toString());
            tab_seleccion_panel.setTitle("RECAUDADOR: " + recaudador + "      (" + caja + ")");
            return true;
        } else {
            return false;
        }
    }

    public void eliminarSeleccionados() {

        double ldou_subtotal = 0;

        try {
            ldou_subtotal = Double.parseDouble(total_valores.getValue() + "");
        } catch (Exception e) {
        }
        for (int aint = 0; aint < tab_seleccion_panel.getTab_seleccion().getSeleccionados().length; aint++) {
            tab_seleccion_panel.getTab_seleccion().getFilas().remove(tab_seleccion_panel.getTab_seleccion().getSeleccionados()[aint]);
            try {
                ldou_subtotal = ldou_subtotal - Double.parseDouble(tab_seleccion_panel.getTab_seleccion().getSeleccionados()[aint].getCampos()[tab_seleccion_panel.getTab_seleccion().getNumeroColumna("valor")] + "");
            } catch (Exception e) {
            }
        }


        total_valores.setValue(utilitario.getFormatoNumero(ldou_subtotal));
    }

    public void limpiar() {
        tab_seleccion.limpiar();
        aut_filtro_cliente.limpiar();
    }

    public boolean validarAnteriores(String str_seleccionados) {
        // recibe los pagos que 
        List lis = utilitario.getConexion().consultar("select ide_titulo,ide_concepto,fecha_emision,clave_catastral  from rec_valores where ide_titulo in (" + str_seleccionados + ") and ide_cliente=" + cliente_actual + " order by fecha_emision");

        for (int i = 0; i < lis.size(); i++) {
            Object[] fila = (Object[]) lis.get(i);
            String ide_concepto = fila[1] + "";
            String fecha = utilitario.getFormatoFecha(fila[2]);
            String clave_catastral=fila[3]+"";
            if (!validaConcepto(ide_concepto, fecha,clave_catastral, str_seleccionados)) {
                return false;
            }
        }
        return true;
    }

    private boolean validaConcepto(String ide_concepto, String fecha,String clave_catastral, String conceptos) {
        //cojo todos los conceptos 
        String sql = "";
        if (!conceptos.isEmpty()) {
            String aux = conceptos.replace("," + ide_concepto, "");
            sql = "select ide_titulo,fecha_emision from rec_valores where ide_estado=1 and ide_concepto=" + ide_concepto + " and fecha_emision <'" + fecha + "' and ide_cliente=" + cliente_actual + " and ide_TITULO not in(" + aux + ") and clave_catastral = '"+clave_catastral+"'  order by fecha_emision";
        } else {
            sql = "select ide_titulo,fecha_emision from rec_valores where ide_estado=1 and ide_concepto=" + ide_concepto + " and fecha_emision <'" + fecha + "' and ide_cliente=" + cliente_actual + " order by fecha_emision";
        }
        List lis = utilitario.getConexion().consultar(sql);
        //    System.out.println("select ide_titulo,fecha_emision from rec_valores where ide_estado=1 and ide_concepto=" + ide_concepto + " and fecha_emision <'" + fecha + "' and ide_cliente=" + cliente_actual + " order by fecha_emision");
        if (lis.isEmpty()) {
            return true;
        } else {
            List li = utilitario.getConexion().consultar("select des_concepto from rec_concepto where ide_concepto=" + ide_concepto);
            utilitario.agregarMensajeError("No se puede recaudar", "Existen valores anteriores para el concepto: " + li.get(0));
            return false;
        }
    }

    

    public int buscaMaximoNumero() {
        //busca el maximo del cajero
        List lis_sql = utilitario.getConexion().consultar("select max(num_titulo) from tes_ingreso  where ide_caja=" + ide_caja);
        try {
            if (lis_sql.get(0) != null) {
                return (Integer.parseInt(lis_sql.get(0) + "") + 1);
            }
        } catch (Exception e) {
        }

        return -1;
    }

    @Override
    public void guardar() {
        tab_seleccion.guardar();
        utilitario.getConexion().guardarPantalla();
    }

    @Override
    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
    }

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_seleccion.seleccionarFila(evt);
    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro_cliente.onSelect(evt);
        cliente_actual = aut_filtro_cliente.getValor();
        tab_seleccion.setSql("select ide_ingreso as aux,des_ingreso,valor,parroquia,calles,observaciones from tes_ingreso  where ide_titulo in (select ide_titulo from rec_valores where ide_cliente = " + cliente_actual + "  order by fecha_emision) and ide_estado = " + ide_estado_activo + " order by aux");

        tab_seleccion.setCondicionBuscar("");
        tab_seleccion.ejecutarSql();
    }

    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
    }

    public Tabla getTab_seleccion() {
        return tab_seleccion;
    }

    public void setTab_seleccion(Tabla tab_seleccion) {
        this.tab_seleccion = tab_seleccion;
    }

    public SeleccionTabla getTab_seleccion_panel() {
        return tab_seleccion_panel;
    }

    public void setTab_seleccion_panel(SeleccionTabla tab_seleccion_panel) {
        this.tab_seleccion_panel = tab_seleccion_panel;
    }

    public Tabla getTab_ingreso() {
        return tab_ingreso;
    }

    public void setTab_ingreso(Tabla tab_ingreso) {
        this.tab_ingreso = tab_ingreso;
    }

    @Override
    public void insertar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
