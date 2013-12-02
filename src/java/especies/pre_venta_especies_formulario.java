/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package especies;

import framework.*;
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
public class pre_venta_especies_formulario extends Pantalla {

    //private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    // private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    //private Grupo gru_pantalla = new Grupo();
    private Etiqueta eti_titulo = new Etiqueta();
    private Etiqueta eti_caja = new Etiqueta();
    private Etiqueta eti_recaudador = new Etiqueta();
    private Etiqueta eti_valor = new Etiqueta();
    private Etiqueta eti_cambio = new Etiqueta();
    private Texto text_valor = new Texto();
    private Confirmar con_guardar = new Confirmar();
    private AutoCompletar aut_filtro_descripcion = new AutoCompletar();
    private String descripcion_actual = "-1";
    private Espacio espacio = new Espacio();
    private Float valor;
    private Float valor_pagar;
    private MarcaAgua maa_busqueda = new MarcaAgua();
    private Boton bot_clean = new Boton();
    private Reporte rep_reporte = new Reporte();
    private SeleccionFormatoReporte sel_rep = new SeleccionFormatoReporte();
    private SeleccionCalendario sel_cal = new SeleccionCalendario();
    private SeleccionTabla sel_tab = new SeleccionTabla();
    private int ide_tasa_deposte = 2;
    //para colocar el cursor del mouse
    private Foco foco = new Foco();
    // para la devoluci
    private Boton btn_devolucion = new Boton();
    private Dialogo dia_devolucion = new Dialogo();
    private Texto txt_empl = new Texto();
    private Texto txt_rani_ven = new Texto();
    private Texto txt_ranf_ven = new Texto();
    private Texto txt_cant_ven = new Texto();
    private Texto txt_rani = new Texto();
    private Texto txt_ranf = new Texto();
    private Texto txt_cant = new Texto();
    private AreaTexto txt_razon = new AreaTexto();
    private int ide_tm1 = 1;
    private int ide_tasa_adm = 21;
////    private int ide_tm1 = 58;
////    private int ide_tasa_adm = 107;
    private String ide_empleado;

    public pre_venta_especies_formulario() {

        ide_empleado = obtener_ide_empleado();
        if (!ide_empleado.equals("-1")) {
            bar_botones.getBot_insertar().setUpdate("tab_tabla2,grup_titulo");
            bar_botones.getBot_guardar().setUpdate("con_guardar");
            bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2,grup_titulo");
            bar_botones.agregarReporte();
            bar_botones.agregarBoton(btn_devolucion);
            btn_devolucion.setId("btn_devolucion");
            btn_devolucion.setValue("Devolucion");
            btn_devolucion.setValueExpression("disabled", "pre_index.clase.tab_tabla2.totalFilas==0");
            btn_devolucion.setMetodo("devolucion");
            btn_devolucion.setUpdate("dia_devolucion");

            agregarComponente(dia_devolucion);

            bot_clean.setIcon("ui-icon-cancel");
            bot_clean.setTitle("Limpiar");
            bot_clean.setUpdate("aut_filtro_descripcion,tab_tabla1,tab_tabla2,grup_titulo");
            bot_clean.setMetodo("limpiar");

            aut_filtro_descripcion.setId("aut_filtro_descripcion");
            aut_filtro_descripcion.setAutoCompletar("select ide_documento,descripcion from rec_especies where ide_documento!=" + ide_tm1 + " and ide_documento != " + ide_tasa_adm);
            aut_filtro_descripcion.setMetodoChange("filtrar_por_descripcion", "tab_tabla1,tab_tabla2,grup_titulo,btn_devolucion");

            bar_botones.agregarComponente(aut_filtro_descripcion);
            bar_botones.agregarBoton(bot_clean);

            foco.setFor("aut_filtro_descripcion");
            agregarComponente(foco);

            maa_busqueda.setValue("Ingrese Nombre de Especie");
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
            tab_tabla1.setId("tab_tabla1");
            tab_tabla1.setSql("select esp.ide_entrega,rangof - c.rango_v as saldos,e.descripcion,rangoi,rangof,esp.cantidad,rd.valor,rd.valor*esp.cantidad as valor_total_esp "
                    + "from rec_entrega_especies esp,rec_especies e,rec_detalle rd, "
                    + "(select a.ide_entrega,(case when rango_venta is null then rangoi-1 else rango_venta end) as rango_v  from rec_entrega_especies a  "
                    + "left join (select max(rango_f1) as rango_venta,ide_entrega  FROM rec_entrega_detalle group by "
                    + "ide_entrega)b on a.ide_entrega = b.ide_entrega)c "
                    + "where esp.ide_entrega=c.ide_entrega and e.ide_documento in "
                    + "(select de.ide_documento from rec_detalle de where de.ide_detalle in ( "
                    + "(select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))) "
                    + "and rd.ide_detalle in ((select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega)) and e.ide_documento!=" + ide_tm1 + " "
                    + "and e.ide_documento= " + descripcion_actual + ""
                    + "and esp.estatus=TRUE and esp.estatus_reasig is not true and rangof - c.rango_v>0 and esp.mun_ide_empleado= " + ide_empleado);
            tab_tabla1.setCampoPrimaria("ide_entrega");
            tab_tabla1.setNumeroTabla(1);

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
            tab_tabla2.setCondicion("ide_entrega_detalle=-1");
            tab_tabla2.getColumna("nom_cliente").setEstilo("height:15px");
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
            tab_tabla2.getColumna("cantidad").setMetodoChange("calcularTotal");
            tab_tabla2.getColumna("cantidad").setNombreVisual("Cantidad ");
            tab_tabla2.getColumna("valor1").setLectura(true);
            tab_tabla2.getColumna("valor1").setNombreVisual("Valor ");
            tab_tabla2.getColumna("fecha").setLectura(true);
            tab_tabla2.getColumna("fecha").setValorDefecto(utilitario.getFechaActual());
            tab_tabla2.getColumna("fecha").setNombreVisual("Fecha");
            tab_tabla2.setTipoFormulario(true);
            tab_tabla2.setRecuperarLectura(true);
            tab_tabla2.getGrid().setColumns(6);
            tab_tabla2.setCampoOrden("ide_entrega_detalle desc");

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
            div.setFooter(grup_titulo, pat_panel1, "35%");

            PanelTabla pat_panel2 = new PanelTabla();
            pat_panel2.setPanelTabla(tab_tabla2);

            div_division.setId("div_division");
            div_division.dividir2(div, pat_panel2, "50%", "H");

            //gru_pantalla.getChildren().add(bar_botones);
            agregarComponente(div_division);

            //para el reporte escoger las especies
            sel_tab.setId("sel_tab");
            sel_tab.setSeleccionTabla("select ide_documento,descripcion from rec_especies order by descripcion", "ide_documento");
//            sel_tab.getTab_seleccion().getColumna("descripcion").setFiltro(true);
            sel_tab.getTab_seleccion().setRows(8);

            agregarComponente(sel_tab);

            // para la devolucion ---------------------------------


            dia_devolucion.setId("dia_devolucion");
            dia_devolucion.setWidth("40%");
            dia_devolucion.setHeight("55%");

            txt_empl.setDisabled(true);
            txt_rani_ven.setDisabled(true);
            txt_ranf_ven.setDisabled(true);
            txt_cant_ven.setDisabled(true);
            txt_cant.setDisabled(true);

            txt_razon.setStyle("width:160px;height:40px");

            txt_cant.setId("txt_cant");
            txt_rani.setMetodoChange("obtener_cantidad_devuelta", "txt_cant");
            txt_ranf.setMetodoChange("obtener_cantidad_devuelta", "txt_cant");

            txt_rani.setId("txt_rani");
            txt_ranf.setId("txt_ranf");
            txt_rani.setSoloEnteros();
            txt_ranf.setSoloEnteros();

            Grid grid_matriz = new Grid();
            grid_matriz.setColumns(2);
            Etiqueta eti_empl = new Etiqueta();
            Etiqueta eti_rani_ven = new Etiqueta();
            Etiqueta eti_ranf_ven = new Etiqueta();
            Etiqueta eti_cant_ven = new Etiqueta();
            Etiqueta eti_rani = new Etiqueta();
            Etiqueta eti_ranf = new Etiqueta();
            Etiqueta eti_cant = new Etiqueta();
            Etiqueta eti_razon = new Etiqueta();
            eti_empl.setValue("Responsable");
            eti_rani_ven.setValue("Rango Ini. Vend.");
            eti_ranf_ven.setValue("Rango Fin. Vend.");
            eti_cant_ven.setValue("Cant. Vend.");
            eti_rani.setValue("Rango Ini");
            eti_ranf.setValue("Rango Fin");
            eti_cant.setValue("Cant");
            eti_razon.setValue("Razon *");
            grid_matriz.getChildren().add(eti_empl);
            grid_matriz.getChildren().add(txt_empl);
            grid_matriz.getChildren().add(eti_rani_ven);
            grid_matriz.getChildren().add(txt_rani_ven);
            grid_matriz.getChildren().add(eti_ranf_ven);
            grid_matriz.getChildren().add(txt_ranf_ven);
            grid_matriz.getChildren().add(eti_cant_ven);
            grid_matriz.getChildren().add(txt_cant_ven);
            grid_matriz.getChildren().add(eti_rani);
            grid_matriz.getChildren().add(txt_rani);
            grid_matriz.getChildren().add(eti_ranf);
            grid_matriz.getChildren().add(txt_ranf);
            grid_matriz.getChildren().add(eti_cant);
            grid_matriz.getChildren().add(txt_cant);
            grid_matriz.getChildren().add(eti_razon);
            grid_matriz.getChildren().add(txt_razon);


            dia_devolucion.setDialogo(grid_matriz);
            dia_devolucion.getBot_aceptar().setMetodo("aceptar_devolucion");
            dia_devolucion.getBot_aceptar().setUpdate("dia_devolucion");

            // fin de la devolucion


            rep_reporte.setId("rep_reporte");



            rep_reporte.getBot_aceptar().setMetodo("aceptar_reporte");
            rep_reporte.getBot_aceptar().setUpdate("rep_reporte,sel_tab,sel_cal");
            sel_tab.getBot_aceptar().setMetodo("aceptar_reporte");
            sel_tab.getBot_aceptar().setUpdate("sel_tab,sel_cal");
            gru_pantalla.getChildren().add(rep_reporte);
            sel_cal.setId("sel_cal");
            sel_cal.setMultiple(true);
            sel_cal.getBot_aceptar().setMetodo("aceptar_reporte");
            sel_cal.getBot_aceptar().setUpdate("sel_cal,sel_rep");
            agregarComponente(sel_cal);

            sel_rep.setId("sel_rep");
            agregarComponente(sel_rep);

            con_guardar.setId("con_guardar");
            con_guardar.setHeader("CONFIRMACION GUARDAR VENTA DE ESPECIE");
            con_guardar.setMessage("Esta seguro de Guardar los Datos");
            con_guardar.getBot_aceptar().setMetodo("aceptar_venta_especie");
            con_guardar.getBot_aceptar().setUpdate("con_guardar,tab_tabla1,tab_tabla2,grup_titulo");
            agregarComponente(con_guardar);
        } else {
            utilitario.agregarNotificacionInfo("Mensaje", "No  tiene Asignado un Empleado para poder Realizar Ventas");
        }


    }

    public void obtener_cantidad_devuelta() {
        if (txt_rani.getValue() == null || txt_ranf.getValue() == null || txt_rani.getValue().toString().isEmpty() || txt_ranf.getValue().toString().isEmpty()) {
            txt_cant.setValue(0);
        } else {
            int ri = Integer.parseInt(txt_rani.getValue().toString());
            int rf = Integer.parseInt(txt_ranf.getValue().toString());
            int cant = rf - ri + 1;
            txt_cant.setValue(cant);
        }
    }

    public void aceptar_devolucion() {

        try {
            int ri1 = Integer.parseInt(txt_rani_ven.getValue().toString());
            int rf1 = Integer.parseInt(txt_ranf_ven.getValue().toString());
            int ri = Integer.parseInt(txt_rani.getValue().toString());
            int rf = Integer.parseInt(txt_ranf.getValue().toString());
            int cant = Integer.parseInt(txt_cant.getValue().toString());
            if (cant == 0) {
                utilitario.agregarMensajeError("Error al Grabar los Datos", "La Cantidad devuelta es Cero ");
                dia_devolucion.cerrar();
            } else {
                if (ri == 0 || rf == 0) {
                    utilitario.agregarMensajeError("Error al Grabar los Datos", "Los Rangos no pueden ser Cero ");
                    dia_devolucion.cerrar();

                } else {
                    if ((ri >= ri1 && ri <= rf1) && (rf >= ri1 && rf <= rf1)) {
                        List list_sql1 = utilitario.getConexion().consultar("select rango_id,rango_fd from rec_devolucion "
                                + "where ide_entrega_detalle=" + tab_tabla2.getValor("ide_entrega_detalle") + " "
                                + "and ((rango_id>=" + ri + " and rango_fd>=" + ri + ") "
                                + "or (rango_id>=" + rf + " and rango_fd>=" + rf + "))"
                                + "order by rango_id");
                        String rango_i = "";
                        String rango_f = "";
                        for (int i = 0; i < list_sql1.size(); i++) {
                            Object[] fila = (Object[]) list_sql1.get(i);
                            rango_i = fila[0] + "";
                            rango_f = fila[1] + "";
                        }
                        if (rango_i == null || rango_i.isEmpty()) {
                            if (txt_razon.getValue() == null || txt_razon.getValue().toString().isEmpty()) {
                                utilitario.agregarMensajeError("Validacion Razon de Devolucion", "La Razon es un Campo Obligatorio");
                                dia_devolucion.cerrar();
                            } else {
                                int ide_dev = utilitario.getConexion().getMaximo("rec_devolucion", "ide_devolucion");
                                int ide_entre_det = Integer.parseInt(tab_tabla2.getValor("ide_entrega_detalle"));
                                List sql1 = utilitario.getConexion().consultar("SELECT  ide_empleado FROM munc_empleados WHERE ide_empleado =(SELECT ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
                                String emp = String.valueOf(sql1.get(0));
                                int ide_empl = Integer.parseInt(emp);
                                int ran_i = Integer.parseInt(txt_rani.getValue().toString());
                                int ran_f = Integer.parseInt(txt_ranf.getValue().toString());
                                int cant1 = Integer.parseInt(txt_cant.getValue().toString());
                                String razon = txt_razon.getValue().toString();
                                int ide_det = Integer.parseInt(tab_tabla2.getValor("ide_detalle"));
                                String mensaje = utilitario.getConexion().ejecutar("insert into rec_devolucion values(" + ide_dev + "," + ide_entre_det + "," + ide_empl + ",'" + utilitario.getFechaActual() + "'," + ran_i + "," + ran_f + "," + cant1 + ",'" + razon + "'," + ide_det + ")");
                                if (mensaje.isEmpty()) {
                                    utilitario.getConexion().commit();
                                    utilitario.agregarMensaje("Guardar Devolucion", "La Devolucion se Guardo Correctamente");

                                    dia_devolucion.cerrar();
                                    txt_rani.setValue(null);
                                    txt_ranf.setValue(null);
                                    txt_cant.setValue(null);
                                    txt_razon.setValue(null);
                                } else {
                                    utilitario.getConexion().rollback();

                                }
                            }

                        } else {
                            utilitario.agregarMensajeInfo("Mensaje", "Los rangos ya estan devueltos, Rango Inicial minimo a asignar " + (Integer.parseInt(rango_f) + 1));
                            dia_devolucion.cerrar();
                        }
                    } else {
                        utilitario.agregarMensajeError("Error al Grabar los Datos", "Los Rangos no pertenecen al talonario ");
                        dia_devolucion.cerrar();
                    }
                }
            }
        } catch (Exception e) {
            utilitario.agregarMensajeError("Error al Grabar los Datos", "Los rangos son Incorrectos");
            dia_devolucion.cerrar();
        }

        dia_devolucion.cerrar();

    }

    public void devolucion() {
        List sql = utilitario.getConexion().consultar("select descripcion from rec_especies where ide_documento=" + descripcion_actual);
        String des_esp = (String) sql.get(0);
        dia_devolucion.setTitle("Devolucion de Especies Vendidas ( " + des_esp + " )");
        List sql1 = utilitario.getConexion().consultar("SELECT nombres FROM munc_empleados WHERE ide_empleado =(SELECT ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        String res = (String) sql1.get(0);
        txt_empl.setValue(res);
        txt_rani_ven.setValue(tab_tabla2.getValor("rango_i1"));
        txt_ranf_ven.setValue(tab_tabla2.getValor("rango_f1"));
        txt_cant_ven.setValue(tab_tabla2.getValor("cantidad"));
        dia_devolucion.dibujar();

    }

    public void abrir_reporte() {
//Se ejecuta cuando da click en el/-//- boton de Reportes de la Barra  
        rep_reporte.dibujar();

    }
    Map parametros = new HashMap();

    public void aceptar_reporte() {
//Se ejecuta cuando se selecciona un reporte de la lista

        if (rep_reporte.getReporteSelecionado().equals("Total Ventas")) {
            if (rep_reporte.isVisible()) {
                parametros = new HashMap();
                rep_reporte.cerrar();
                sel_tab.dibujar();
            } else if (sel_tab.isVisible()) {
                sel_tab.cerrar();
                parametros.put("especie", sel_tab.getSeleccionados());
                sel_cal.dibujar();
            } else if (sel_cal.isVisible()) {
                parametros.put("fecha_ini", sel_cal.getFecha1());
                parametros.put("fecha_fin", sel_cal.getFecha2());
                sel_cal.cerrar();
                sel_rep.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
                sel_rep.dibujar();

            }


        } else if (rep_reporte.getReporteSelecionado().equals("Total Recaudado")) {
            if (rep_reporte.isVisible()) {
                sel_cal.dibujar();
                rep_reporte.cerrar();
            } else if (sel_cal.isVisible()) {
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
                    eti_cambio.setValue("CAMBIO : " + utilitario.getFormatoNumero(cambio));
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


        List asig = utilitario.getConexion().consultar("select *from rec_entrega_especies where mun_ide_empleado=" + ide_empleado + " and ide_detalle in (select d.ide_detalle from rec_detalle d where d.ide_documento=" + descripcion_actual + ") ");
        if (asig.isEmpty()) {
            text_valor.setDisabled(true);
            utilitario.agregarMensajeError("Mensaje", "No tiene Asignado Talonario, Contactese con el Administrador");
        } else {
            text_valor.setDisabled(false);


//        if (Integer.parseInt(descripcion_actual) == ide_tasa_deposte) {
//            tab_tabla2.getColumna("nom_cliente").setAncho(250);
//            tab_tabla2.getColumna("nom_cliente").setVisible(true);
//            tab_tabla2.getColumna("ced_ruc").setVisible(true);
//
//        } else {
//            tab_tabla2.getColumna("nom_cliente").setVisible(false);
//            tab_tabla2.getColumna("ced_ruc").setVisible(false);
//        }


            tab_tabla1.setSql("select esp.ide_entrega,rangof - c.rango_v as saldos,e.descripcion,rangoi,rangof,esp.cantidad,rd.valor,rd.valor*esp.cantidad as valor_total_esp "
                    + "from rec_entrega_especies esp,rec_especies e,rec_detalle rd, "
                    + "(select a.ide_entrega,(case when rango_venta is null then rangoi-1 else rango_venta end) as rango_v  from rec_entrega_especies a  "
                    + "left join (select max(rango_f1) as rango_venta,ide_entrega  FROM rec_entrega_detalle group by "
                    + "ide_entrega)b on a.ide_entrega = b.ide_entrega)c "
                    + "where esp.ide_entrega=c.ide_entrega and e.ide_documento in "
                    + "(select de.ide_documento from rec_detalle de where de.ide_detalle in ( "
                    + "(select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))) "
                    + "and rd.ide_detalle in ((select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega)) and e.ide_documento!=" + ide_tm1 + " "
                    + "and e.ide_documento= " + descripcion_actual + ""
                    + "and esp.estatus=TRUE and esp.estatus_reasig is not true and rangof - c.rango_v>0 and esp.mun_ide_empleado= " + ide_empleado);
            tab_tabla1.ejecutarSql();



            tab_tabla2.setCondicion("ide_empleado=" + ide_empleado + " and ide_entrega=" + tab_tabla1.getValor("ide_entrega") + " and cantidad > 0 ");
            tab_tabla2.dibujar();

        }
        cargar_titulo();
    }

    public String obtenerSaldoDisponible(String ide_entrega) {
        Tabla tab_saldo = utilitario.consultar("select esp.ide_entrega,rangof - c.rango_v as saldos "
                + "from rec_entrega_especies esp,rec_especies e,rec_detalle rd, "
                + "(select a.ide_entrega,(case when rango_venta is null then rangoi-1 else rango_venta end) as rango_v  from rec_entrega_especies a  "
                + "left join (select max(rango_f1) as rango_venta,ide_entrega  FROM rec_entrega_detalle group by "
                + "ide_entrega)b on a.ide_entrega = b.ide_entrega)c "
                + "where esp.ide_entrega=c.ide_entrega and e.ide_documento in "
                + "(select de.ide_documento from rec_detalle de where de.ide_detalle in ( "
                + "(select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))) "
                + "and rd.ide_detalle in ((select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega)) and e.ide_documento!=" + ide_tm1 + " "
                + "and e.ide_documento= " + descripcion_actual + " "
                + "and esp.estatus=TRUE and esp.estatus_reasig is not true and rangof - c.rango_v>0 and esp.mun_ide_empleado= " + ide_empleado + " "
                + "and esp.ide_entrega=" + ide_entrega);
        if (tab_saldo.getTotalFilas() > 0) {
            return tab_saldo.getValor(0, "saldos");
        } else {
            return null;
        }
    }

    public boolean validarVentaEspecie() {

        if (utilitario.validarCedula(tab_tabla2.getValor("ced_ruc")) == false) {
            utilitario.agregarMensajeError("No se puede guardar los Datos", "La cedula no es Valida");
            return false;
        }
        if (tab_tabla2.getValor("cantidad") == null || tab_tabla2.getValor("cantidad").isEmpty()) {
            utilitario.agregarMensajeError("No se puede guardar los Datos", "No ha ingresado ls cantidad a vender");
            return false;
        }

        String saldo_disp = obtenerSaldoDisponible(tab_tabla1.getValor("ide_entrega"));
        if (saldo_disp != null) {
            int cant_vender = Integer.parseInt(tab_tabla2.getValor("cantidad"));
            int cant_disp = Integer.parseInt(saldo_disp);
            if (cant_vender > cant_disp) {
                utilitario.agregarMensajeError("No se puede guardar los Datos", "Stock no dispobible solo puede vender " + (cant_vender - (cant_vender - cant_disp)) + " especies");
                return false;
            }
        }



        return true;
    }

    public void aceptar_venta_especie() {

//        if (Integer.parseInt(descripcion_actual) == ide_tasa_deposte) {
//            val_ced = utilitario.validarCedula(tab_tabla2.getValor("ced_ruc"));
//        } else {
//            val_ced = true;
//        }
        if (validarVentaEspecie()) {

//            List validar_rango = utilitario.getConexion().consultar("select rango_i1 from rec_entrega_detalle "
//                    + "where ide_entrega=23 and ((rango_i1>=" + Integer.parseInt(tab_tabla2.getValor("rango_i1")) + " and rango_f1>=" + Integer.parseInt(tab_tabla2.getValor("rango_f1")) + ") "
//                    + "or (rango_i1>=" + Integer.parseInt(tab_tabla2.getValor("rango_i1")) + " and rango_f1>=" + Integer.parseInt(tab_tabla2.getValor("rango_f1")) + ")) order by rango_i1");
//
//            if (validar_rango == null || validar_rango.isEmpty() ) {

            String cod = tab_tabla1.getValorSeleccionado();//para coger el ide de la tabla 


            tab_tabla2.guardar();
            utilitario.getConexion().guardarPantalla();
            descripcion_actual = aut_filtro_descripcion.getValor();
            tab_tabla1.setSql("select esp.ide_entrega,rangof - c.rango_v as saldos,e.descripcion,rangoi,rangof,esp.cantidad,rd.valor,rd.valor*esp.cantidad as valor_total_esp "
                    + "from rec_entrega_especies esp,rec_especies e,rec_detalle rd, "
                    + "(select a.ide_entrega,(case when rango_venta is null then rangoi-1 else rango_venta end) as rango_v  from rec_entrega_especies a  "
                    + "left join (select max(rango_f1) as rango_venta,ide_entrega  FROM rec_entrega_detalle group by "
                    + "ide_entrega)b on a.ide_entrega = b.ide_entrega)c "
                    + "where esp.ide_entrega=c.ide_entrega and e.ide_documento in "
                    + "(select de.ide_documento from rec_detalle de where de.ide_detalle in ( "
                    + "(select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega))) "
                    + "and rd.ide_detalle in ((select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega)) and e.ide_documento!=" + ide_tm1 + " "
                    + "and e.ide_documento= " + descripcion_actual + ""
                    + " and esp.estatus=TRUE and esp.estatus_reasig is not true and rangof - c.rango_v>0 and esp.mun_ide_empleado= " + ide_empleado);
            tab_tabla1.ejecutarSql();
            tab_tabla1.setFilaActual(cod);//para que el foco quede en la fila guardada
            text_valor.setValue("");
            utilitario.addUpdate("text_valor");

            cargar_titulo();
            con_guardar.cerrar();
//            } else {
//                utilitario.agregarMensajeError("Validacion de Venta", "Ya existe una venta realizada con los mismos rangos ");
//                cargar_titulo();
//                con_guardar.cerrar();
//            }
        } else {
            cargar_titulo();
            con_guardar.cerrar();
        }

    }

    public void calcularTotal(AjaxBehaviorEvent evt) {
        tab_tabla2.modificar(evt);
        if (!tab_tabla2.getValor("cantidad").isEmpty()) {
            try {
                int cant = Integer.parseInt(tab_tabla2.getValor("cantidad"));
                System.out.println("...   " + cant);
                if (cant != 0) {
                    Long rf = Long.parseLong(tab_tabla2.getValor("rango_i1"));
                    rf = rf + cant - 1;
                    valor = Float.parseFloat(tab_tabla1.getValor("valor"));
                    valor = cant * valor;
                    int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                    tab_tabla2.setValor(int_fila_modificada, "rango_f1", rf + "");
                    tab_tabla2.setValor(int_fila_modificada, "valor1", utilitario.getFormatoNumero(valor) + "");
                    utilitario.addUpdateTabla(tab_tabla2, "rango_f1,valor1", "");
                    eti_valor.setValue("TOTAL A PAGAR: " + utilitario.getFormatoNumero(valor));
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
        String rango_fi;
        int band = 0;
        if (list_sql1.get(0) == null) {

            List list_sql2 = utilitario.getConexion().consultar("select MAX(rangoi) from rec_entrega_especies where ide_entrega=" + tab_tabla1.getValor("ide_entrega"));
            rango_fi = String.valueOf(list_sql2.get(0));
            band = 1;
        } else {
            rango_fi = String.valueOf(list_sql1.get(0));
        }


        Long r = Long.parseLong(rango_fi);
        if (band != 1) {
            r = r + 1;
        }
        System.out.println("dd " + r);
        List ver_anuladas = utilitario.getConexion().consultar("select rangof from rec_especie_anulado where ide_detalle=" + tab_tabla2.getValor("ide_detalle") + " and rangoi<=" + r + " and rangof>=" + r + "");
        System.out.println("si " + ver_anuladas.size() + " ddd" + ver_anuladas);
        if (ver_anuladas.size() != 0) {
            String ran = ver_anuladas.get(0).toString();
            Long r1 = Long.parseLong(ran);
            r1 = r1 + 1;
            tab_tabla2.setValor("rango_i1", r1 + "");
            tab_tabla2.setValor("rango_f1", r1 + "");
        } else {
            tab_tabla2.setValor("rango_i1", r + "");
            tab_tabla2.setValor("rango_f1", r + "");
        }
    }

    public String obtener_ide_empleado() {
        String ide_empl = "-1";
        List list_sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
            ide_empl = String.valueOf(list_sql1.get(0));
        }
        return ide_empl;
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
                + "and rd.ide_detalle in ((select d.ide_detalle from rec_entrega_especies d where d.ide_entrega = c.ide_entrega)) and e.ide_documento!=" + ide_tm1 + " "
                + "and e.ide_documento= " + descripcion_actual + ""
                + "and esp.estatus=TRUE and esp.estatus_reasig is not true and rangof - c.rango_v>0 and esp.mun_ide_empleado= " + ide_empleado);
        tab_tabla1.ejecutarSql();
        tab_tabla2.setCondicion("ide_empleado=" + ide_empleado + " and ide_detalle=" + tab_tabla1.getValor("ide_detalle") + " and cantidad > 0 ");
        tab_tabla2.ejecutarSql();
    }
@Override
    public void insertar() {
        if (!tab_tabla2.isFilaInsertada()) {
            tab_tabla2.insertar();
            List sql2 = utilitario.getConexion().consultar("select ide_detalle from rec_entrega_especies where ide_entrega=" + tab_tabla1.getValor("ide_entrega"));
            String ide_det = String.valueOf(sql2.get(0));
            tab_tabla2.setValor("ide_detalle", ide_det);
            obtener_rangos();
        } else {
            utilitario.agregarMensajeInfo("Validacion Ventas", "Debe guardar la Transaccion en Proceso antes de Ingresar otro Registro");
        }
    }
@Override
    public void guardar() {
        con_guardar.dibujar();
    }

    public void cargar_titulo() {
        String des = "";
        if (tab_tabla1.getTotalFilas() > 0) {
            des = tab_tabla1.getValor("descripcion");
        }
        if (des != null) {
            eti_titulo.setValue("Descripcion: " + des);
        } else {
            eti_titulo.setValue("Descripcion: ");
        }
        String de = "";
        List sql = utilitario.getConexion().consultar("SELECT  des_caja FROM tes_caja WHERE ide_caja =(SELECT ide_caja from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql.isEmpty()) {
            de = (String) sql.get(0);
        }
        String rec = "";
        List sql1 = utilitario.getConexion().consultar("SELECT  nombres FROM munc_empleados WHERE ide_empleado =(SELECT ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql1.isEmpty()) {
            rec = (String) sql1.get(0);
        }
        eti_caja.setValue("Numero Caja: " + de);
        eti_recaudador.setValue("Recaudador: " + rec);
        eti_valor.setValue("TOTAL A PAGAR: ");
        eti_cambio.setValue("CAMBIO: ");
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

    public Reporte getRep_reporte() {
        return rep_reporte;
    }

    public void setRep_reporte(Reporte rep_reporte) {
        this.rep_reporte = rep_reporte;
    }

    public SeleccionCalendario getSel_cal() {
        return sel_cal;
    }

    public void setSel_cal(SeleccionCalendario sel_cal) {
        this.sel_cal = sel_cal;
    }

    public SeleccionFormatoReporte getSel_rep() {
        return sel_rep;
    }

    public void setSel_rep(SeleccionFormatoReporte sel_rep) {
        this.sel_rep = sel_rep;
    }

    public SeleccionTabla getSel_tab() {
        return sel_tab;
    }

    public void setSel_tab(SeleccionTabla sel_tab) {
        this.sel_tab = sel_tab;
    }

    public Dialogo getDia_devolucion() {
        return dia_devolucion;
    }

    public void setDia_devolucion(Dialogo dia_devolucion) {
        this.dia_devolucion = dia_devolucion;
    }
}
