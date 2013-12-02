/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.event.SelectEvent;
import sistema.*;

/**
 *
 * @author HP
 */
public class pre_cajas extends Pantalla {

    // private Utilitario utilitario = new Utilitario();
    private Tabla tab_seleccion = new Tabla();
    //private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    //private Grupo gru_pantalla = new Grupo();
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
    
    public pre_cajas() {
        
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
            bot_pagar.setValue("Pagar");
            bot_pagar.setMetodo("pagar");
            bot_pagar.setUpdate("tab_seleccion_panel");
            Espacio esp = new Espacio();
            esp.setHeight("0");
            esp.setWidth("25");
            bar_botones.agregarComponente(esp);
            
            bar_botones.agregarBoton(bot_pagar);

            //configuracion de la tabla de valores o titulos (cabecera)
            tab_seleccion.setId("tab_seleccion");
            tab_seleccion.setSql("select ide_ingreso as aux,des_ingreso,valor,parroquia,calles,observaciones from tes_ingreso where ide_titulo=-1 order by aux");
            tab_seleccion.setNumeroTabla(1);
            tab_seleccion.setCampoPrimaria("aux");
            
            tab_seleccion.getColumna("observaciones").setFiltro(true);
            tab_seleccion.getColumna("calles").setFiltro(true);
            tab_seleccion.getColumna("des_ingreso").setFiltro(true);
            
            tab_seleccion.setCondicion("ide_titulo = -1 and ide_estado = " + ide_estado_activo);
            tab_seleccion.getColumna("observaciones").setEstilo("width: 200px;");
            tab_seleccion.getColumna("des_ingreso").setEstilo("width: 150px;");
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
            tab_seleccion_panel.getTab_seleccion().setScrollable(true);
            tab_seleccion_panel.getTab_seleccion().setScrollHeight(100);
            tab_seleccion_panel.getTab_seleccion().setRows(0);
            
            tab_seleccion_panel.setSeleccionTabla("select ide_ingreso,des_ingreso,valor,ide_titulo from tes_ingreso where ide_ingreso in (-1)", "ide_ingreso");
            tab_seleccion_panel.getBot_aceptar().setMetodo("confirmar_pago");
            
            
            bot_eli_pago.setValue("Eliminar Valores Seleccionados");
            bot_eli_pago.setMetodo("eliminarSeleccionados");
            bot_eli_pago.setUpdate(tab_seleccion_panel.getTab_seleccion().getId() + ",lgri_resumen");
            
            
            tab_ingreso.setCondicion("ide_ingreso=-1");
            tab_ingreso.getGrid().setColumns(4);
            tab_ingreso.getColumna("DES_INGRESO").setVisible(false);
            tab_ingreso.getColumna("IDE_TIPO").setPermitirNullCombo(false);
            tab_ingreso.getColumna("IDE_TIPO").setCombo("rec_tipo", "IDE_TIPO", "DES_TIPO", "");
            tab_ingreso.getColumna("IDE_TIPO").setNombreVisual("TIPO PAGO");
            tab_ingreso.getColumna("num_titulo").setNombreVisual("ESPECIE");
            tab_ingreso.getColumna("ide_caja").setCombo("tes_caja", "ide_caja", "des_caja", "ide_caja =(SELECT IDE_CAJA FROM SIS_USUARIO  WHERE IDE_USUA=" + utilitario.getVariable("ide_usua") + ")");
            tab_ingreso.getColumna("ide_caja").setNombreVisual("CAJA");
            tab_ingreso.getColumna("ide_caja").setLectura(true);
            tab_ingreso.getColumna("ide_empleado").setCombo("SELECT IDE_EMPLEADO,NOMBRES FROM MUNC_EMPLEADOS WHERE IDE_EMPLEADO= (SELECT IDE_EMPLEADO FROM SIS_USUARIO WHERE IDE_USUA=" + utilitario.getVariable("ide_usua") + ")");
            tab_ingreso.getColumna("ide_empleado").setNombreVisual("RECAUDADOR ");
            tab_ingreso.getColumna("ide_empleado").setLectura(true);
            tab_ingreso.getColumna("ide_titulo").setVisible(false);
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
            tab_ingreso.getColumna("fecha_modificacion").setVisible(false);
            tab_ingreso.getColumna("responsable_modificacion").setVisible(false);
            tab_ingreso.getColumna("responsable_cobro").setVisible(false);
            tab_ingreso.getColumna("ip_modifica").setVisible(false);
            tab_ingreso.setTipoFormulario(true);
            tab_ingreso.setMostrarNumeroRegistros(false);
            tab_ingreso.dibujar();
            
            total_valores.setId("total_valores");
            total_valores.setValue(utilitario.getFormatoNumero("0"));
            total_valores.setStyle("color: red; font-size: 15px; align: right;");
            
            Etiqueta total_valores_titulo = new Etiqueta();
            total_valores_titulo.setId("total_valores_titulo");
            total_valores_titulo.setValue("TOTAL VALORES: ");
            total_valores_titulo.setStyle("color: red; font-size: 15px; align: right;");
            
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
            
            tab_seleccion_panel.getGri_cuerpo().getChildren().add(tab_ingreso);
            tab_seleccion_panel.getGri_cuerpo().getChildren().add(lgri_resumen);


            //gru_pantalla.getChildren().add(bar_botones);
            //gru_pantalla.getChildren().add(div_division);
            //gru_pantalla.getChildren().add(tab_seleccion_panel);
            agregarComponente(div_division);
            agregarComponente(tab_seleccion_panel);
            
            maa_marca.setFor("aut_filtro_cliente");
            maa_marca.setValue("Buscar Contribuyente");
            agregarComponente(maa_marca);
            
            
            vpdf_pago.setId("vpdf_pago");
            vpdf_pago.setTitle("Detalle de la Recaudación");
            agregarComponente(vpdf_pago);
            
            
            
        } else {
            utilitario.agregarNotificacionInfo("Mensaje", "No  tiene Asignado un Empleado para poder Realizar Ventas");
        }
    }
    
    public void pagar() {

        // String lstr_seleccionados = tab_seleccion.getStringColumnaSeleccionados("ide_titulo");
        String lstr_seleccionados = tab_seleccion.getFilasSeleccionadas();
        if (!lstr_seleccionados.isEmpty()) {
            tab_seleccion_panel.getTab_seleccion().limpiar();
            tab_seleccion_panel.getTab_seleccion().setSql("select ide_ingreso,des_ingreso,valor,ide_titulo from tes_ingreso where ide_ingreso in (" + lstr_seleccionados + ")");
            tab_seleccion_panel.getTab_seleccion().getColumna("ide_titulo").setVisible(false);
            tab_seleccion_panel.getTab_seleccion().ejecutarSql();
            lstr_seleccionados = tab_seleccion_panel.getTab_seleccion().getStringColumna("ide_titulo");
            if (validarAnteriores(lstr_seleccionados)) {
                
                tab_seleccion_panel.dibujar();
                double ldou_subtotal = 0;
                for (int aint = 0; aint < tab_seleccion_panel.getTab_seleccion().getFilas().size(); aint++) {
                    
                    List lis_des = utilitario.getConexion().consultar("select interes,multa,descuento from descuentos_multas where ide_ingreso=" + tab_seleccion_panel.getTab_seleccion().getValor(aint, "ide_ingreso"));
                    if (lis_des != null && !lis_des.isEmpty()) {
                        try {
                            double valor = Double.parseDouble(tab_seleccion_panel.getTab_seleccion().getValor(aint, "valor"));
                            
                            Object obj_fila[] = (Object[]) lis_des.get(0);
                            if (obj_fila[0] == null) {
                                obj_fila[0] = "0";
                            }
                            if (obj_fila[1] == null) {
                                obj_fila[1] = "0";
                            }
                            if (obj_fila[2] == null) {
                                obj_fila[2] = "0";
                            }
                            
                            double interes = Double.parseDouble(obj_fila[0] + "");
                            double multa = Double.parseDouble(obj_fila[1] + "");
                            double descuento = Double.parseDouble(obj_fila[2] + "");
                            valor = (valor + multa + interes) - descuento;
                            tab_seleccion_panel.getTab_seleccion().setValor(aint, "valor", utilitario.getFormatoNumero(valor));
                            
                        } catch (Exception e) {
                        }
                    }


                    ///aqui sumar los valores de descuentos_multas
                    ldou_subtotal += Double.parseDouble(tab_seleccion_panel.getTab_seleccion().getValor(aint, "valor"));
                }
                total_valores.setValue(utilitario.getFormatoNumero(ldou_subtotal));
                tab_ingreso.limpiar();
                tab_ingreso.insertar();
                tab_ingreso.setValor("num_titulo", buscaMaximoNumero() + "");
            }
            
            
        } else {
            utilitario.agregarMensajeInfo("Atencion", "Debe seleccionar por lo menos un registro a pagar.");
        }
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
        for (int aint = 0; aint < tab_seleccion_panel.getTab_seleccion().getSeleccionados().length; aint++) {
            tab_seleccion_panel.getTab_seleccion().getFilas().remove(tab_seleccion_panel.getTab_seleccion().getSeleccionados()[aint]);
        }
        total_valores.setValue(utilitario.getFormatoNumero(ldou_subtotal));
    }
    
    public void limpiar() {
        tab_seleccion.limpiar();
        aut_filtro_cliente.limpiar();
    }
    
    public boolean validarAnteriores(String str_seleccionados) {
        // recibe los pagos que 
        List lis = utilitario.getConexion().consultar("select ide_titulo,ide_concepto,fecha_emision  from rec_valores where ide_titulo in (" + str_seleccionados + ") and ide_cliente=" + cliente_actual + " order by fecha_emision");
        
        for (int i = 0; i < lis.size(); i++) {
            Object[] fila = (Object[]) lis.get(i);
            String ide_concepto = fila[1] + "";
            String fecha = utilitario.getFormatoFecha(fila[2]);
            /*if (!validaConcepto(ide_concepto, fecha, str_seleccionados)) {
             return false;
             }*/
        }
        return true;
    }
    
    private boolean validaConcepto(String ide_concepto, String fecha, String conceptos) {
        //cojo todos los conceptos 
        String sql = "";
        if (!conceptos.isEmpty()) {
            String aux = conceptos.replace("," + ide_concepto, "");
            sql = "select ide_titulo,fecha_emision from rec_valores where ide_estado=1 and ide_concepto=" + ide_concepto + " and fecha_emision <'" + fecha + "' and ide_cliente=" + cliente_actual + " and ide_TITULO not in(" + aux + ") order by fecha_emision";
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
    
    public void confirmar_pago() {
        
        tab_seleccion_panel.cerrar();
        
        if (tab_seleccion_panel.getTab_seleccion().getTotalFilasVisibles() > 0) {
            
            String ip_responsable = utilitario.getIp();
            String ide_empleado = tab_ingreso.getValor(0, "ide_empleado");
            String ide_tipo = tab_ingreso.getValor(0, "ide_tipo");
            String num_titulo = tab_ingreso.getValor(0, "num_titulo");
            if (ide_tipo != null && !ide_tipo.isEmpty()) {
                if (num_titulo != null && !num_titulo.isEmpty()) {
                    String update_res = "";
                    int int_titulo = Integer.parseInt(num_titulo);
                    String str_titulos = "";
                    for (int i = 0; i < tab_seleccion_panel.getTab_seleccion().getTotalFilas(); i++) {

                        ///ejecuto el metodo valorar
                        String str_sql = "Select fecha_emision from rec_valores where ide_titulo=" + tab_seleccion_panel.getTab_seleccion().getValor(i, "ide_titulo");
                        String str_fecha = utilitario.getConexion().consultar(str_sql).get(0) + "";
                        cls_valorar valorar = new cls_valorar();
                        valorar.valorar_Interes_Multa_Descuento(str_fecha);
                        
                        if (!str_titulos.isEmpty()) {
                            str_titulos += ",";
                        }
                        str_titulos += tab_seleccion_panel.getTab_seleccion().getValor(i, "ide_ingreso");
                        String fecha_pago = utilitario.getFormatoFechaHora(utilitario.getFechaHoraActual());
                        String update = "update tes_ingreso set"
                                + " ip_responsable = '" + ip_responsable + "',"
                                + " ide_empleado= " + ide_empleado + ","
                                + " ide_caja= " + ide_caja + ","
                                + " ide_estado= " + ide_estado_pagado + ","
                                + " ide_tipo= " + ide_tipo + ","
                                + " num_titulo= '" + int_titulo + "',"
                                + " responsable_cobro= '" + recaudador + "',"
                                + " fecha_pago= '" + fecha_pago + "'"
                                + " where ide_titulo =" + tab_seleccion_panel.getTab_seleccion().getValor(i, "ide_titulo");
                        update_res += utilitario.getConexion().ejecutar(update);
                        
                        
                        List lis_des = utilitario.getConexion().consultar("select interes,multa,descuento from descuentos_multas where ide_ingreso=" + tab_seleccion_panel.getTab_seleccion().getValor(i, "ide_ingreso"));
                        double interes = 0;
                        double multa = 0;
                        double descuento = 0;
                        if (lis_des != null && !lis_des.isEmpty()) {
                            try {
                                
                                Object obj_fila[] = (Object[]) lis_des.get(0);
                                if (obj_fila[0] == null) {
                                    obj_fila[0] = "0";
                                }
                                if (obj_fila[1] == null) {
                                    obj_fila[1] = "0";
                                }
                                if (obj_fila[2] == null) {
                                    obj_fila[2] = "0";
                                }
                                
                                interes = Double.parseDouble(obj_fila[0] + "");
                                multa = Double.parseDouble(obj_fila[1] + "");
                                descuento = Double.parseDouble(obj_fila[2] + "");
                            } catch (Exception e) {
                            }
                        }
                        
                        String update2 = "update aux_valores set"
                                + " ide_estado  = '" + ide_estado_pagado + "',"
                                + " numero= " + int_titulo + ","
                                + " mul= " + multa + ","
                                + " des= " + descuento + ","
                                + " inte= " + interes + ","
                                + " empleado= '" + recaudador + "',"
                                + " caja= '" + caja + "',"
                                + " fecha_pago= '" + fecha_pago + "'"
                                + " where ide_titulo =" + tab_seleccion_panel.getTab_seleccion().getValor(i, "ide_titulo");
                        update_res += utilitario.getConexion().ejecutar(update2);

                        ///rec valores ide_estado y num_titulo
                        String update3 = "update rec_valores set"
                                + " ide_estado  = " + ide_estado_pagado + ","
                                + " num_titulo= " + int_titulo + " "
                                + " where ide_titulo =" + tab_seleccion_panel.getTab_seleccion().getValor(i, "ide_titulo");
                        update_res += utilitario.getConexion().ejecutar(update3);
                        
                        int_titulo++;
                        
                    }
                    if (update_res.isEmpty()) {
                        utilitario.getConexion().commit();
                        utilitario.agregarMensaje("Se guardo correctamente", "El pago se registro satisfactoriamente.");

                        ///////////AQUI ABRE EL REPORTE
                        Map parametros = new HashMap();
                        parametros.put("ide_ingresos", str_titulos);
                        System.out.println(" " + str_titulos);
                        vpdf_pago.setVisualizarPDF("rep_rentas/rep_carta_predial.jasper", parametros);
                        vpdf_pago.dibujar();
                        utilitario.addUpdate("vpdf_pago");
                    }
                    tab_seleccion.ejecutarSql();
                    utilitario.addUpdate("tab_seleccion_panel,tab_seleccion");
                } else {
                    utilitario.agregarMensajeInfo("Atención", "Falta el número del título");
                }
            } else {
                utilitario.agregarMensajeInfo("Atención", "Falta el Tipo de pago");
            }
        } else {
            utilitario.agregarMensajeInfo("Atención", "No existen detalles para recaudar.");
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
  /*  
    public Barra getBar_botones() {
        return bar_botones;
    }
    
    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }*/
    
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
