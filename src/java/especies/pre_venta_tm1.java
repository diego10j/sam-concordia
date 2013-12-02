/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package especies;

import framework.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import sistema.Pantalla;

/**
 *
 * @author Byron
 */
public class pre_venta_tm1 extends Pantalla {

    //  private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    //   private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    //  private Grupo gru_pantalla = new Grupo();
    private Etiqueta eti_titulo = new Etiqueta();
    private Etiqueta eti_caja = new Etiqueta();
    private Etiqueta eti_recaudador = new Etiqueta();
    private Etiqueta eti_valor = new Etiqueta();
    private Etiqueta eti_cambio = new Etiqueta();
    private Texto text_valor = new Texto();
    private Confirmar con_guardar = new Confirmar();
    private AutoCompletar aut_filtro_descripcion = new AutoCompletar();
    private String descripcion_actual = "-1";
    private int documento = -1;
    private Espacio espacio = new Espacio();
    private Float valor;
    private Float valor_pagar;
    private MarcaAgua maa_busqueda = new MarcaAgua();
    private Boton bot_clean = new Boton();
    private VisualizarPDF vp = new VisualizarPDF();
    // valores de las especies parametrizadas
    private int ide_tm1 = 1;
    private int ide_tasa_adm = 21;
//    private int ide_tm1 = 58;
//    private int ide_tasa_adm = 107;
    //
    private Consulta sel_tab = new Consulta();
    // para reportes
    private Reporte rep_reporte = new Reporte();
    private SeleccionFormatoReporte sel_rep = new SeleccionFormatoReporte();
    private SeleccionCalendario sel_cal = new SeleccionCalendario();
    //
    //para recoger datos del cliente
    private Dialogo dia_datos_cliente = new Dialogo();
    private Boton bot_cliente_extra = new Boton();
    private Texto txt_nombre = new Texto();
    private Texto txt_cedula = new Texto();
    private int bandera_parametros = 0;
    //
    private String ide_empleado;
    private String valor_tas_adm;
    private Combo com_tip_doc = new Combo();

    public pre_venta_tm1() {

        ide_empleado = obtener_ide_empleado();
        valor_tas_adm = obtener_valor_tas_adm();
        if (!ide_empleado.equals("-1")) {
            bar_botones.getBot_insertar().setUpdate("tab_tabla2,grup_titulo");
            bar_botones.getBot_guardar().setUpdate("con_guardar");
            bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2,grup_titulo");
            bar_botones.agregarReporte();

            bot_clean.setIcon("ui-icon-cancel");
            bot_clean.setTitle("Limpiar");
            bot_clean.setUpdate("aut_filtro_descripcion,tab_tabla1,tab_tabla2,grup_titulo");
            bot_clean.setMetodo("limpiar");

            bot_cliente_extra.setTitle("Cliente Externo");
            bot_cliente_extra.setValue("Cliente Externo");
            bot_cliente_extra.setUpdate("dia_datos_cliente");
            bot_cliente_extra.setMetodo("recoger_datos_cliente");

            agregarComponente(dia_datos_cliente);

            aut_filtro_descripcion.setId("aut_filtro_descripcion");
            aut_filtro_descripcion.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes ");
            aut_filtro_descripcion.setMetodoChange("filtrar_por_descripcion", "tab_tabla1,tab_tabla2,grup_titulo");
            bar_botones.agregarComponente(aut_filtro_descripcion);
            bar_botones.agregarBoton(bot_clean);
            bar_botones.agregarBoton(bot_cliente_extra);

            maa_busqueda.setValue("Nombre o Cedula del Cliente a Buscar");
            maa_busqueda.setFor("aut_filtro_descripcion");
            bar_botones.agregarComponente(maa_busqueda);

            espacio.setWidth("20");
            espacio.setHeight("17");
            eti_titulo.setStyle("font-size: 11px;font-weight: bold");
            eti_caja.setStyle("font-size: 11px;font-weight: bold");
            eti_recaudador.setStyle("font-size: 11px;font-weight: bold");
            eti_valor.setStyle("font-size: 17px;color: red;font-weight: bold");
            eti_valor.setId("eti_valor");
            eti_cambio.setId("eti_cambio");
            eti_cambio.setStyle("font-size: 15px;color: red;font-weight: bold");

            text_valor.setSoloNumeros();

            List asig = utilitario.getConexion().consultar("select * from rec_entrega_especies where mun_ide_empleado=" + ide_empleado + " and ide_detalle in (select d.ide_detalle from rec_detalle d where d.ide_documento=" + ide_tm1 + ") ");
            if (asig.isEmpty()) {
                aut_filtro_descripcion.setDisabled(true);
                bot_cliente_extra.setDisabled(true);
                text_valor.setDisabled(true);
                utilitario.agregarNotificacionInfo("Mensaje", "No tiene Asignado Talonario, Contactese con el Administrador");
            } else {
                aut_filtro_descripcion.setDisabled(false);
                bot_cliente_extra.setDisabled(false);
                text_valor.setDisabled(false);
            }
            tab_tabla1.setId("tab_tabla1");
            tab_tabla1.setSql("select esp.ide_entrega,rangof - c.rango_v as saldos,e.descripcion,rangoi,rangof,esp.cantidad,rd.valor,rd.valor*esp.cantidad as valor_total_esp "
                    + "from rec_entrega_especies esp,rec_especies e,rec_detalle rd, "
                    + "(select a.ide_entrega,(case when rango_venta is null then rangoi-1 else rango_venta end) as rango_v  from rec_entrega_especies a  "
                    + "left join (select max(rango_f1) as rango_venta,ide_entrega  FROM rec_entrega_detalle group by "
                    + "ide_entrega)b on a.ide_entrega = b.ide_entrega)c "
                    + "where esp.ide_entrega=c.ide_entrega and e.ide_documento in "
                    + "(select de.ide_documento from rec_detalle de where de.ide_detalle in ( "
                    + "(select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))) "
                    + "and rd.ide_detalle in ((select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))"
                    + "and e.ide_documento=" + documento + ""
                    + "and esp.estatus=TRUE and rangof - c.rango_v>0 and esp.estatus_reasig is not true  and esp.mun_ide_empleado= " + ide_empleado);
            tab_tabla1.setCampoPrimaria("ide_entrega");
            tab_tabla1.setNumeroTabla(1);
            tab_tabla1.onSelect("seleccionar_tabla1");
            tab_tabla1.agregarRelacion(tab_tabla2);
            tab_tabla1.getColumna("saldos").setEstilo("font-size: 13px;color: red;font-weight: bold");
            tab_tabla1.setLectura(true);
            tab_tabla1.setRows(4);
            tab_tabla1.dibujar();



            PanelTabla pat_panel1 = new PanelTabla();
            pat_panel1.setPanelTabla(tab_tabla1);

            pat_panel1.getMenuTabla().getItem_eliminar().setRendered(false);
            pat_panel1.getMenuTabla().getItem_guardar().setRendered(false);
            tab_tabla2.setId("tab_tabla2");
            tab_tabla2.setTabla("rec_entrega_detalle", "ide_entrega_detalle", 2);
            tab_tabla2.setCampoForanea("ide_entrega");
            if (tab_tabla1.getValorSeleccionado() != null) {
                tab_tabla2.setCondicion("ide_empleado=" + ide_empleado + " and cantidad > 0 ");
            }
            String de = "";
            List sql = utilitario.getConexion().consultar("select ide_caja from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
            if (!sql.isEmpty()) {
                de = String.valueOf(sql.get(0));
            }
            tab_tabla2.getColumna("ide_caja").setValorDefecto(de);
            tab_tabla2.getColumna("ide_caja").setVisible(false);
            String rec = "";
            List sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
            if (!sql1.isEmpty()) {
                rec = String.valueOf(sql1.get(0));
            }
            tab_tabla2.getColumna("ide_empleado").setValorDefecto(rec);
            tab_tabla2.getColumna("ide_empleado").setVisible(false);
            tab_tabla2.getColumna("ide_detalle").setVisible(false);
//        tab_tabla2.getColumna("ide_empleado").setCombo("munc_empleados", "ide_empleado", "apellido1,apellido2,nombre1,nombre2", "ide_empleado= (select e.ide_empleado from munc_empleados e where ide_usua="+utilitario.getVariable("ide_usua")+")");
//        tab_tabla2.getColumna("ide_empleado").setNombreVisual("Recaudador ");
            tab_tabla2.getColumna("rango_i1").setLectura(true);
            tab_tabla2.getColumna("rango_i1").setNombreVisual("Rango Inicial");
            tab_tabla2.getColumna("rango_f1").setLectura(true);
            tab_tabla2.getColumna("rango_f1").setNombreVisual("Rango Final");
            tab_tabla2.getColumna("ide_entrega_detalle").setNombreVisual("Codigo");
            tab_tabla2.getColumna("ide_entrega_detalle").setLongitud(10);
            tab_tabla2.getColumna("cantidad").setMetodoKeyPress("controlar_stock");
            tab_tabla2.getColumna("cantidad").setNombreVisual("Cantidad ");
            tab_tabla2.getColumna("cantidad").setValorDefecto("1");
            tab_tabla2.getColumna("cantidad").setLectura(true);
            tab_tabla2.getColumna("valor1").setLectura(true);
            tab_tabla2.getColumna("valor1").setNombreVisual("Valor ");
            tab_tabla2.getColumna("fecha").setLectura(true);
            tab_tabla2.getColumna("fecha").setValorDefecto(utilitario.getFechaActual());
            tab_tabla2.getColumna("fecha").setNombreVisual("Fecha");
            tab_tabla2.getColumna("ced_ruc").setLectura(true);
            tab_tabla2.getColumna("nom_cliente").setLectura(true);

            tab_tabla2.setTipoFormulario(true);
//        tab_tabla2.setRecuperarLectura(true);
//        tab_tabla2.setRows(3);
            tab_tabla2.getGrid().setColumns(6);
            tab_tabla2.setCondicion("ide_entrega_detalle=-1");
            tab_tabla2.dibujar();

            cargar_titulo();

            text_valor.setId("text_valor");
            text_valor.setMetodoKeyPress("obtener_cambio", "");

            Grid grup_titulo = new Grid();
            grup_titulo.setColumns(2);
            grup_titulo.setWidth("100%");
            grup_titulo.setId("grup_titulo");
            grup_titulo.getChildren().add(eti_titulo);
            grup_titulo.getChildren().add(eti_valor);
            grup_titulo.getChildren().add(eti_caja);
            grup_titulo.getChildren().add(text_valor);
            grup_titulo.getChildren().add(eti_recaudador);
            grup_titulo.getChildren().add(eti_cambio);

            Division div = new Division();
            div.setFooter(grup_titulo, pat_panel1, "40%");

            PanelTabla pat_panel2 = new PanelTabla();
            pat_panel2.setPanelTabla(tab_tabla2);

            pat_panel2.getMenuTabla().getItem_insertar().setRendered(false);

            div_division.setId("div_division");
            div_division.dividir2(div, pat_panel2, "50%", "H");

            //gru_pantalla.getChildren().add(bar_botones);
            agregarComponente(div_division);

            //para cliente externo

            dia_datos_cliente.setId("dia_datos_cliente");
            dia_datos_cliente.setTitle("Datos del Cliente");
            dia_datos_cliente.setWidth("35%");
            dia_datos_cliente.setHeight("30%");

            Grid grid_matriz = new Grid();
            grid_matriz.setColumns(2);
            Etiqueta eti_tip_doc = new Etiqueta();
            Etiqueta eti_cedula = new Etiqueta();
            Etiqueta eti_nombre = new Etiqueta();
            com_tip_doc.setCombo("select ide_tipo_documento,des_tipo_documento from sigc_tipo_documento order by ide_tipo_documento");
            com_tip_doc.eliminarVacio();
            com_tip_doc.setValue("1");
            eti_tip_doc.setValue("Tipo Documento");
            eti_cedula.setValue("Cedula");
            eti_nombre.setValue("Nombre");
            txt_nombre.setSize(40);
            grid_matriz.getChildren().add(eti_tip_doc);
            grid_matriz.getChildren().add(com_tip_doc);
            grid_matriz.getChildren().add(eti_cedula);
            grid_matriz.getChildren().add(txt_cedula);
            grid_matriz.getChildren().add(eti_nombre);
            grid_matriz.getChildren().add(txt_nombre);

            dia_datos_cliente.setDialogo(grid_matriz);
            dia_datos_cliente.getBot_aceptar().setMetodo("aceptar_datos_cliente");
//            dia_datos_cliente.getBot_aceptar().setUpdate("dia_datos_cliente,tab_tabla1,tab_tabla2,grup_titulo");

            //
            sel_tab.setId("sel_tab");
            sel_tab.setConsulta("select ide_ingreso,des_ingreso,observaciones,valor from tes_ingreso where ide_titulo in (select ide_titulo from rec_valores where ide_cliente=" + descripcion_actual + ") and ide_estado =1", "ide_ingreso");

            agregarComponente(sel_tab);

            vp.setId("vp");
            agregarComponente(vp);



            rep_reporte.setId("rep_reporte");
            rep_reporte.getBot_aceptar().setMetodo("aceptar_reporte");
            rep_reporte.getBot_aceptar().setUpdate("rep_reporte,sel_cal");

            vp.getBot_cancelar().setValue("Cerrar");

            sel_cal.setId("sel_cal");
            sel_cal.setMultiple(true);
            sel_cal.getBot_aceptar().setMetodo("aceptar_reporte");
            sel_cal.getBot_aceptar().setUpdate("sel_cal,sel_rep");
            agregarComponente(sel_cal);

            sel_rep.setId("sel_rep");
            agregarComponente(sel_rep);
            agregarComponente(rep_reporte);
            con_guardar.setId("con_guardar");
            con_guardar.setHeader("VENTA DE ESPECIE NO ADEUDA AL MUNICIPIO");
            con_guardar.getBot_aceptar().setMetodo("aceptar_venta_especie");
            con_guardar.getBot_cancelar().setMetodo("cancelar_venta_especie");
            con_guardar.getBot_aceptar().setUpdate("con_guardar,tab_tabla1,tab_tabla2,grup_titulo");
            agregarComponente(con_guardar);
        } else {
            utilitario.agregarNotificacionInfo("Mensaje", "No  tiene Asignado un Empleado para poder Realizar Ventas");
        }

    }

    public void cancelar_venta_especie() {
        con_guardar.cerrar();
        aut_filtro_descripcion.setValue(null);
        tab_tabla1.limpiar();
        tab_tabla2.limpiar();
        eti_valor.setValue("TOTAL A PAGAR: $");
        utilitario.addUpdate("con_guardar,tab_tabla1,eti_valor,tab_tabla2,aut_filtro_descripcion");
    }

    public void cerrar_visualizador() {
        vp.cerrar();
        eti_valor.setValue("TOTAL A PAGAR: $");
        valor_pagar = Float.parseFloat(tab_tabla1.getValor("valor"));
        text_valor.setValue("");
        utilitario.addUpdate("text_valor,eti_valor,vp");

    }

    public void aceptar_datos_cliente() {
        boolean val_ced = false;
        boolean val_ruc = false;
        boolean val_pas = false;
        boolean val_sin_identi = false;
        if (com_tip_doc.getValue().equals("1")) {
            val_ced = utilitario.validarCedula(txt_cedula.getValue().toString());
        }
        if (com_tip_doc.getValue().equals("2")) {
            val_ruc = utilitario.validarRUC(txt_cedula.getValue().toString());
        }
        if (com_tip_doc.getValue().equals("3")) {
            val_pas = true;
        }
        if (com_tip_doc.getValue().equals("4")) {
            txt_cedula.setValue(null);
            val_sin_identi = true;
        }


        if (val_ced == true || val_ruc == true || val_pas == true) {
            List sql = utilitario.getConexion().consultar("select ide_cliente from rec_clientes WHERE cedula='" + txt_cedula.getValue().toString() + "'");
            if (sql.isEmpty()) {
//                utilitario.agregarMensaje("ok", "ooooo");
                bandera_parametros = 1;
                dia_datos_cliente.cerrar();
                documento = ide_tm1;
                tab_tabla1.setSql("select esp.ide_entrega,rangof - c.rango_v as saldos,e.descripcion,rangoi,rangof,esp.cantidad,rd.valor,rd.valor*esp.cantidad as valor_total_esp "
                        + "from rec_entrega_especies esp,rec_especies e,rec_detalle rd, "
                        + "(select a.ide_entrega,(case when rango_venta is null then rangoi-1 else rango_venta end) as rango_v  from rec_entrega_especies a  "
                        + "left join (select max(rango_f1) as rango_venta,ide_entrega  FROM rec_entrega_detalle group by "
                        + "ide_entrega)b on a.ide_entrega = b.ide_entrega)c "
                        + "where esp.ide_entrega=c.ide_entrega and e.ide_documento in "
                        + "(select de.ide_documento from rec_detalle de where de.ide_detalle in ( "
                        + "(select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))) "
                        + "and rd.ide_detalle in ((select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))"
                        + "and e.ide_documento=" + documento + ""
                        + "and esp.estatus=TRUE and rangof - c.rango_v>0 and esp.estatus_reasig is not true and esp.mun_ide_empleado= " + ide_empleado);
                tab_tabla1.ejecutarSql();
                float valor_tm1 = Float.parseFloat(tab_tabla1.getValor("valor")) - Float.parseFloat(valor_tas_adm);
                tab_tabla2.getColumna("valor1").setValorDefecto(valor_tm1 + "");
                tab_tabla2.getColumna("ced_ruc").setValorDefecto(txt_cedula.getValue().toString());
                tab_tabla2.getColumna("nom_cliente").setValorDefecto(txt_nombre.getValue().toString());

                tab_tabla2.setCondicion("ide_empleado=" + ide_empleado + " and ide_entrega=" + tab_tabla1.getValor("ide_entrega") + " and cantidad > 0 ");
                tab_tabla2.dibujar();
                tab_tabla2.insertar();
                List sql3 = utilitario.getConexion().consultar("select ide_detalle from rec_entrega_especies where ide_entrega=" + tab_tabla1.getValor("ide_entrega"));
                if (!sql3.isEmpty()) {
                    String ide_det = String.valueOf(sql3.get(0));
                    tab_tabla2.setValor("ide_detalle", ide_det);
                }
                eti_valor.setValue("TOTAL A PAGAR: $" + tab_tabla1.getValor("valor"));
                utilitario.addUpdate("eti_valor");
                valor_pagar = Float.parseFloat(tab_tabla1.getValor("valor"));
                text_valor.setValue("");
                utilitario.addUpdate("text_valor,dia_datos_cliente,tab_tabla1,tab_tabla2,grup_titulo");

                obtener_rangos();


            } else {
                utilitario.agregarMensajeError("Validacion de Cliente", "El Cliente esta Actualmente Registrado en la Base, No es un Cliente Externo ");
                bandera_parametros = 0;
            }
        } else {
            utilitario.agregarMensajeError("Validacion de Cedula", "La Cedula o Ruc Ingresada No Es Valida favor Revisar");
//            aut_filtro_descripcion.setValue(null);
//            utilitario.addUpdate("aut_filtro_descripcion,dia_");
//            tab_tabla1.limpiar();
//            tab_tabla2.limpiar();
            bandera_parametros = 0;
        }

    }

    public void recoger_datos_cliente() {
        txt_cedula.setValue(null);
        txt_nombre.setValue(null);

        dia_datos_cliente.dibujar();
    }

    public void abrir_reporte() {
//Se ejecuta cuando da click en el boton de Reportes de la Barra  
        rep_reporte.dibujar();

    }

    public void aceptar_reporte() {
//Se ejecuta cuando se selecciona un reporte de la lista

        Map parametros = new HashMap();
        if (rep_reporte.getReporteSelecionado().equals("Total Ventas TM1")) {
            if (rep_reporte.isVisible()) {
                rep_reporte.cerrar();
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                parametros.put("especie", "" + ide_tm1 + "," + ide_tasa_adm);
                parametros.put("fecha_ini", sel_cal.getFecha1());
                parametros.put("fecha_fin", sel_cal.getFecha2());
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();

            }


        }


    }

    public void limpiar() {
        aut_filtro_descripcion.setValue(null);

        tab_tabla1.limpiar();
        cargar_titulo();
    }

    public void obtener_cambio() {

        if (!String.valueOf(text_valor.getValue()).isEmpty()) {
            try {
                float valor_entregado = Float.parseFloat(String.valueOf(text_valor.getValue()));
                if (valor_entregado >= valor_pagar) {
                    float cambio = valor_entregado - valor_pagar;
                    eti_cambio.setValue("CAMBIO : $" + utilitario.getFormatoNumero(cambio));
                    utilitario.addUpdate("eti_cambio");
                }
                //else{
//                utilitario.agregarMensajeInfo("Mensaje", "No existe cambio  ");
//                eti_cambio.setValue("CAMBIO : ");
//                utilitario.addUpdate("eti_cambio");
//                text_valor.setValue("");
//                utilitario.addUpdate("text_valor");
//            }
            } catch (Exception e) {
                utilitario.agregarMensajeInfo("Mensaje", "Solo se admite numeros reales ");
                eti_cambio.setValue("CAMBIO : ");
                utilitario.addUpdate("eti_cambio");
                text_valor.setValue("");
                utilitario.addUpdate("text_valor");
            }
        }
    }

    public void filtrar_por_descripcion(SelectEvent evt) {
        aut_filtro_descripcion.onSelect(evt);
        descripcion_actual = aut_filtro_descripcion.getValor();

        cargar_titulo();
        List sql = utilitario.getConexion().consultar("select ide_titulo from rec_valores WHERE ide_cliente=" + descripcion_actual + " and ide_estado=1");
        if (sql.isEmpty()) {

            documento = ide_tm1;
            tab_tabla1.setSql("select esp.ide_entrega,rangof - c.rango_v as saldos,e.descripcion,rangoi,rangof,esp.cantidad,rd.valor,rd.valor*esp.cantidad as valor_total_esp "
                    + "from rec_entrega_especies esp,rec_especies e,rec_detalle rd, "
                    + "(select a.ide_entrega,(case when rango_venta is null then rangoi-1 else rango_venta end) as rango_v  from rec_entrega_especies a  "
                    + "left join (select max(rango_f1) as rango_venta,ide_entrega  FROM rec_entrega_detalle group by "
                    + "ide_entrega)b on a.ide_entrega = b.ide_entrega)c "
                    + "where esp.ide_entrega=c.ide_entrega and e.ide_documento in "
                    + "(select de.ide_documento from rec_detalle de where de.ide_detalle in ( "
                    + "(select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))) "
                    + "and rd.ide_detalle in ((select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))"
                    + "and e.ide_documento=" + documento + ""
                    + "and esp.estatus=TRUE and rangof - c.rango_v>0 and esp.estatus_reasig is not true and esp.mun_ide_empleado= " + ide_empleado);
            tab_tabla1.ejecutarSql();
            float valor_tm1 = Float.parseFloat(tab_tabla1.getValor("valor")) - Float.parseFloat(valor_tas_adm);

            tab_tabla2.getColumna("valor1").setValorDefecto(valor_tm1 + "");
            String nombre = "";
            String cedula = "";
            List sql1 = utilitario.getConexion().consultar("select nombre from rec_clientes where ide_cliente=" + descripcion_actual + " ");
            if (!sql1.isEmpty()) {
                nombre = (String) sql1.get(0);
            }
            List sql2 = utilitario.getConexion().consultar("select cedula from rec_clientes where ide_cliente=" + descripcion_actual + " ");
            if (!sql2.isEmpty()) {
                cedula = (String) sql2.get(0);
            }
            tab_tabla2.getColumna("ced_ruc").setValorDefecto(cedula);
            tab_tabla2.getColumna("nom_cliente").setValorDefecto(nombre);
            eti_valor.setValue("TOTAL A PAGAR: $" + tab_tabla1.getValor("valor"));
            utilitario.addUpdate("eti_valor");
            valor_pagar = Float.parseFloat(tab_tabla1.getValor("valor"));
            text_valor.setValue("");
            utilitario.addUpdate("text_valor");

            tab_tabla2.setCondicion("ide_empleado=" + ide_empleado + " and ide_entrega=" + tab_tabla1.getValor("ide_entrega") + " and cantidad > 0 ");
            tab_tabla2.dibujar();
            tab_tabla2.insertar();
            List sql3 = utilitario.getConexion().consultar("select ide_detalle from rec_entrega_especies where ide_entrega=" + tab_tabla1.getValor("ide_entrega"));
            if (!sql3.isEmpty()) {
                String ide_det = String.valueOf(sql3.get(0));
                tab_tabla2.setValor("ide_detalle", ide_det);
            }

            obtener_rangos();

        } else {
            tab_tabla1.limpiar();
            tab_tabla2.limpiar();
            String nombre = "";
            List sql1 = utilitario.getConexion().consultar("select nombre from rec_clientes where ide_cliente=" + descripcion_actual);
            if (!sql1.isEmpty()) {
                nombre = (String) sql1.get(0);
            }
            utilitario.agregarMensajeInfo("Validacion de No Adeuda", "Usted tiene rubros pendientes...");
            sel_tab.setTitle("Rubros Adeudados ( " + nombre + ")");
            sel_tab.getTab_consulta_dialogo().setSql("select ide_ingreso,des_ingreso,observaciones,valor from tes_ingreso where ide_titulo in (select ide_titulo from rec_valores where ide_cliente=" + descripcion_actual + ") and ide_estado =1");
            sel_tab.dibujar();
            utilitario.addUpdate("sel_tab");
            descripcion_actual = "-1";
            documento = -1;
        }

    }

    public void aceptar_venta_especie() {

        if (tab_tabla1.getTotalFilas() > 0) {

            List valor_tasa_adm = utilitario.getConexion().consultar("select valor from rec_detalle where ide_documento=" + ide_tasa_adm);
            Float val_tasa_adm = (float) 0;
            if (valor_tasa_adm != null) {
                val_tasa_adm = Float.parseFloat(String.valueOf(valor_tasa_adm.get(0)));
            }
            List val_esp_no_adeuda = utilitario.getConexion().consultar("select valor from rec_detalle where ide_documento=" + ide_tm1);
            Float val_esp_no_adeu = (float) 0;
            if (val_esp_no_adeuda != null) {
                val_esp_no_adeu = Float.parseFloat(String.valueOf(val_esp_no_adeuda.get(0)));
            }

            String cod = tab_tabla1.getValorSeleccionado();//para coger el ide de la tabla 
            tab_tabla2.guardar();
            utilitario.getConexion().guardarPantalla();
            List sql3 = utilitario.getConexion().consultar("select ide_detalle from rec_detalle where ide_documento=" + ide_tasa_adm);
            String ide_det = "";
            if (!sql3.isEmpty()) {
                ide_det = String.valueOf(sql3.get(0));
            }
            List sql4 = utilitario.getConexion().consultar("select valor from rec_detalle where ide_detalle=" + ide_det);
            String valor = "";
            if (!sql4.isEmpty()) {
                valor = String.valueOf(sql4.get(0));
            }

            int ide_ent = utilitario.getConexion().getMaximo("rec_entrega_detalle", "ide_entrega_detalle");
            String mensaje = utilitario.getConexion().ejecutar("insert into rec_entrega_detalle (ide_entrega_detalle,ide_caja, "
                    + "ide_empleado,ide_entrega,rango_i1,rango_f1,cantidad,valor1,fecha,nom_cliente,ced_ruc,ide_detalle) "
                    + "values(" + ide_ent + "," + tab_tabla2.getValor("ide_caja") + "," + tab_tabla2.getValor("ide_empleado") + "," + tab_tabla2.getValor("ide_entrega") + ",1,1," + tab_tabla2.getValor("cantidad") + "," + valor + " "
                    + ",'" + tab_tabla2.getValor("fecha") + "','" + tab_tabla2.getValor("nom_cliente").toUpperCase() + "','" + tab_tabla2.getValor("ced_ruc") + "'," + ide_det + ") ");
            if (mensaje.isEmpty()) {
                utilitario.getConexion().commit();
                System.out.println("si vendio tasa administrativa");
            } else {
                utilitario.getConexion().rollback();

            }

            con_guardar.cerrar();
            Map parametros = new HashMap();
            if (bandera_parametros == 0) {
                parametros.put("ide_cliente", Integer.parseInt(descripcion_actual));
                parametros.put("rango", Integer.parseInt(tab_tabla2.getValor("rango_i1")));
                parametros.put("valor_tas_adm", val_tasa_adm);
                parametros.put("valor_esp", val_esp_no_adeu);
                vp.setVisualizarPDF("rep_especies/Certificado_tm1.jasper", parametros);
                vp.dibujar();
                utilitario.addUpdate("vp");
            } else {
                System.out.println("tasa adm " + val_tasa_adm + "    tm1 " + val_esp_no_adeu);
                parametros.put("cedula", txt_cedula.getValue().toString());
                parametros.put("rango", Integer.parseInt(tab_tabla2.getValor("rango_i1")));
                parametros.put("nombre", txt_nombre.getValue().toString().toUpperCase());
                parametros.put("valor_tas_adm", val_tasa_adm);
                parametros.put("valor_esp", val_esp_no_adeu);


                vp.setVisualizarPDF("rep_especies/Certificado_tm1_1.jasper", parametros);
                vp.dibujar();
                utilitario.addUpdate("vp");

            }
        } else {
            con_guardar.cerrar();
        }
        eti_valor.setValue("TOTAL A PAGAR: $");
        aut_filtro_descripcion.setValue(null);
        utilitario.addUpdate("aut_filtro_descripcion,eti_valor");
        tab_tabla1.limpiar();


    }

    public void controlar_stock(AjaxBehaviorEvent evt) {
        tab_tabla2.modificar(evt);

        if (!tab_tabla2.getValor("cantidad").isEmpty()) {
            try {
                int cant = Integer.parseInt(tab_tabla2.getValor("cantidad"));
                if (cant != 0) {
                    Long rf = Long.parseLong(tab_tabla2.getValor("rango_i1"));
                    rf = rf + cant - 1;
                    if (rf > Integer.parseInt(tab_tabla1.getValor("rangof"))) {
                        utilitario.agregarMensajeInfo("Mensaje", "La cantidad solicitada sobrepasa el Stock ");
                        int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                        tab_tabla2.setValor(int_fila_modificada, "valor1", "");
                        tab_tabla2.setValor(int_fila_modificada, "cantidad", "");
                        utilitario.addUpdateTabla(tab_tabla2, "valor1,cantidad", "");
                        text_valor.setValue("");
                        eti_cambio.setValue(" ");
                        utilitario.addUpdate("text_valor");
                    } else {
                        valor = Float.parseFloat(tab_tabla1.getValor("valor"));
                        valor = cant * valor;
                        int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                        tab_tabla2.setValor(int_fila_modificada, "rango_f1", rf + "");
                        tab_tabla2.setValor(int_fila_modificada, "valor1", valor + "");
                        utilitario.addUpdateTabla(tab_tabla2, "rango_f1,valor1", "");
                        eti_valor.setValue("TOTAL A PAGAR: " + valor);
                        utilitario.addUpdate("eti_valor");
                        valor_pagar = valor;
                        eti_cambio.setValue(" ");
                        text_valor.setValue("");
                        utilitario.addUpdate("text_valor");
                        //              int saldo=Integer.parseInt(tab_tabla1.getValor("saldos"));
                        //              int int_fila_modificada1 = tab_tabla1.getFilaActual();
                        //              tab_tabla1.setValor(int_fila_modificada1, "saldos", nuevo_saldo+"");
                        //              tab_tabla1.modificar(tab_tabla1.getFilaActual());
                        //              utilitario.addUpdateTabla(tab_tabla1, "saldo", "tab_tabla1");
                    }
                }
            } catch (Exception e) {
                utilitario.agregarMensajeInfo("Mensaje", "La cantidad es Invalida solo admite Numeros Enteros ");
                int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                tab_tabla2.setValor(int_fila_modificada, "valor1", "");
                tab_tabla2.setValor(int_fila_modificada, "cantidad", "");
                utilitario.addUpdateTabla(tab_tabla2, "valor1,cantidad", "");
            }

        }
    }

    public void obtener_rangos() {
        List list_sql1 = utilitario.getConexion().consultar("select MAX(rango_f1) from rec_entrega_detalle where ide_entrega=" + tab_tabla1.getValor("ide_entrega") + "and ide_empleado=" + ide_empleado);
        String rango_fi = "";
        int band = 0;
        if (list_sql1.get(0) == null) {

            List list_sql2 = utilitario.getConexion().consultar("select MAX(rangoi) from rec_entrega_especies where ide_entrega=" + tab_tabla1.getValor("ide_entrega"));
            if (!list_sql2.isEmpty()) {
                rango_fi = String.valueOf(list_sql2.get(0));
            } else {
                rango_fi = "0";
            }
            band = 1;
        } else {
            rango_fi = String.valueOf(list_sql1.get(0));

        }
        Long r = Long.parseLong(rango_fi);
        if (band != 1) {
            r = r + 1;
        }
        tab_tabla2.setValor("rango_i1", r + "");
        tab_tabla2.setValor("rango_f1", r + "");
    }

    public void obtener_rango(AjaxBehaviorEvent evt) {
        tab_tabla2.modificar(evt);
        List list_sql1 = utilitario.getConexion().consultar("select MAX(rango_f1) from rec_entrega_detalle where ide_detalle=" + tab_tabla1.getValor("ide_detalle"));
        String rango_fi;
        int band = 0;
        if (list_sql1.get(0) == null) {
            List list_sql2 = utilitario.getConexion().consultar("select MAX(rango_i) from rec_detalle where ide_detalle=" + tab_tabla1.getValor("ide_detalle"));
            rango_fi = String.valueOf(list_sql2.get(0));
            band = 1;
        } else {
            rango_fi = String.valueOf(list_sql1.get(0));
        }
        Long r = Long.parseLong(rango_fi);
        if (band != 1) {
            r = r + 1;
        }
        int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
        tab_tabla2.setValor(int_fila_modificada, "rango_i1", r + "");
        utilitario.addUpdateTabla(tab_tabla2, "rango_i1", "");
        tab_tabla2.setValor(int_fila_modificada, "rango_f1", r + "");
        utilitario.addUpdateTabla(tab_tabla2, "rango_f1", "");
    }

    public String obtener_ide_empleado() {
        String ide_empl = "-1";
        List list_sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
            ide_empl = String.valueOf(list_sql1.get(0));
        }
        return ide_empl;
    }

    public String obtener_valor_tas_adm() {
        String valor = "0";
        List list_sql1 = utilitario.getConexion().consultar("select valor from rec_detalle where ide_documento=" + ide_tasa_adm);
        if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
            valor = String.valueOf(list_sql1.get(0));
        }
        return valor;
    }
@Override
    public void eliminar() {
        tab_tabla2.eliminar();
        descripcion_actual = aut_filtro_descripcion.getValor();
        tab_tabla1.setSql("select esp.ide_entrega,rangof - c.rango_v as saldos,e.descripcion,rangoi,rangof,esp.cantidad,rd.valor,rd.valor*esp.cantidad as valor_total_esp "
                + "from rec_entrega_especies esp,rec_especies e,rec_detalle rd, "
                + "(select a.ide_entrega,(case when rango_venta is null then rangoi-1 else rango_venta end) as rango_v  from rec_entrega_especies a  "
                + "left join (select max(rango_f1) as rango_venta,ide_entrega  FROM rec_entrega_detalle group by "
                + "ide_entrega)b on a.ide_entrega = b.ide_entrega)c "
                + "where esp.ide_entrega=c.ide_entrega and e.ide_documento in "
                + "(select de.ide_documento from rec_detalle de where de.ide_detalle in ( "
                + "(select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))) "
                + "and rd.ide_detalle in ((select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))"
                + "and e.ide_documento=" + documento + ""
                + "and esp.estatus=TRUE and rangof - c.rango_v>0 and esp.estatus_reasig is not true and esp.mun_ide_empleado= " + ide_empleado);
        tab_tabla1.ejecutarSql();
        tab_tabla2.setCondicion("ide_empleado=" + ide_empleado + " and ide_detalle=" + tab_tabla1.getValor("ide_detalle") + " and cantidad > 0 ");
        tab_tabla2.ejecutarSql();
    }
@Override
    public void guardar() {

        con_guardar.setMessage("Cliente: " + tab_tabla2.getValor("nom_cliente") + "       <br/> Ced / Ruc: " + tab_tabla2.getValor("ced_ruc") + "<br/> <br/> Esta Seguro de Vender la Especie con estos Datos");
        con_guardar.dibujar();
    }

    public void cargar_titulo() {
        eti_titulo.setValue("Descripcion: Certificado No Adeuda al GAD Mucipal del Canton La Concordia");
        String de = "";
        String rec = "";
        List sql = utilitario.getConexion().consultar("SELECT  des_caja FROM tes_caja WHERE ide_caja =(SELECT ide_caja from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql.isEmpty()) {
            de = (String) sql.get(0);
        }
        List sql1 = utilitario.getConexion().consultar("SELECT  nombres FROM munc_empleados WHERE ide_empleado =(SELECT ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql.isEmpty()) {
            rec = (String) sql1.get(0);
        }
        eti_caja.setValue("Numero Caja: " + de);
        eti_recaudador.setValue("Recaudador: " + rec);
        eti_valor.setValue("TOTAL A PAGAR: $");
        eti_cambio.setValue("CAMBIO: $");
    }

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla1.seleccionarFila(evt);
        cargar_titulo();
        tab_tabla2.setCondicion("ide_empleado=" + ide_empleado + " and cantidad > 0 ");
        tab_tabla2.ejecutarSql();

    }

    public Grupo getGru_pantalla() {
        return gru_pantalla;
    }

    public void setGru_pantalla(Grupo gru_pantalla) {
        this.gru_pantalla = gru_pantalla;
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
/*
    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }
*/
    public Confirmar getCon_guardar() {
        return con_guardar;
    }

    public void setCon_guardar(Confirmar con_guardar) {
        this.con_guardar = con_guardar;
    }

    public AutoCompletar getAut_filtro_descripcion() {
        return aut_filtro_descripcion;
    }

    public void setAut_filtro_descripcion(AutoCompletar aut_filtro_descripcion) {
        this.aut_filtro_descripcion = aut_filtro_descripcion;
    }

    public Consulta getSel_tab() {
        return sel_tab;
    }

    public void setSel_tab(Consulta sel_tab) {
        this.sel_tab = sel_tab;
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

    public SeleccionFormatoReporte getSel_rep() {
        return sel_rep;
    }

    public void setSel_rep(SeleccionFormatoReporte sel_rep) {
        this.sel_rep = sel_rep;
    }

    public SeleccionCalendario getSel_cal() {
        return sel_cal;
    }

    public void setSel_cal(SeleccionCalendario sel_cal) {
        this.sel_cal = sel_cal;
    }

    public Dialogo getDia_datos_cliente() {
        return dia_datos_cliente;
    }

    public void setDia_datos_cliente(Dialogo dia_datos_cliente) {
        this.dia_datos_cliente = dia_datos_cliente;
    }

   @Override
   public void insertar() {
       
    }
}
