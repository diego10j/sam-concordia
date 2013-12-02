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
public class pre_ingreso_especies_1 extends Pantalla{


    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
  //  private Barra bar_botones = new Barra();
    private Division div_division = new Division();
  //  private Grupo gru_pantalla = new Grupo();
    private Confirmar con_guardar = new Confirmar();
    private String rango_i = "";
    private String rango_f = "";
    private AutoCompletar aut_filtro_descripcion = new AutoCompletar();
    private Etiqueta eti_titulo = new Etiqueta();
    private Etiqueta eti_descripcion = new Etiqueta();
    private Etiqueta eti_responsable = new Etiqueta();
    private Espacio espacio = new Espacio();
    private MarcaAgua maa_busqueda = new MarcaAgua();
    private String ide_empleado;
    private Boton bot_clean = new Boton();
    // para la anulacion
    private Boton btn_anular = new Boton();
    private Dialogo dia_anulacion = new Dialogo();
    private Texto txt_rango_i = new Texto();
    private Texto txt_rango_f = new Texto();
    private Texto txt_rangoi = new Texto();
    private Texto txt_rangof = new Texto();
    private Etiqueta eti_especie = new Etiqueta();
    private Reporte rep_reporte = new Reporte();
    private SeleccionFormatoReporte sel_rep = new SeleccionFormatoReporte();
    private SeleccionCalendario sel_cal = new SeleccionCalendario();
    private SeleccionTabla sel_tab = new SeleccionTabla();
    // fin del reporte
    private int bandera_anulacion = 0;
    //    private String descripcion_actual = "-1";

    public pre_ingreso_especies_1() {
        ide_empleado = obtener_ide_empleado();
        if (!ide_empleado.equals("-1")) {

            /*bar_botones.getBot_insertar().setUpdate("tab_tabla1,tab_tabla2,grup_titulo");
            bar_botones.getBot_guardar().setUpdate("con_guardar");
            bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2,grup_titulo");*/
            
            
            bar_botones.agregarBoton(btn_anular);
            bar_botones.agregarReporte();
            btn_anular.setId("btn_anular");
            btn_anular.setValue("Anular");
            btn_anular.setValueExpression("disabled", "pre_index.clase.tab_tabla2.totalFilas==0");
            btn_anular.setMetodo("anular");
            btn_anular.setUpdate("dia_anulacion");

            aut_filtro_descripcion.setId("aut_filtro_descripcion");
            aut_filtro_descripcion.setAutoCompletar("select ide_documento,descripcion from rec_especies  order by descripcion");
            aut_filtro_descripcion.setMetodoChange("filtrar_por_descripcion", "tab_tabla1,tab_tabla2,grup_titulo,btn_anular");
            bar_botones.agregarComponente(aut_filtro_descripcion);

            maa_busqueda.setValue("Ingrese Nombre de Especie");
            maa_busqueda.setFor("aut_filtro_descripcion");
            bar_botones.agregarComponente(maa_busqueda);
            bot_clean.setIcon("ui-icon-cancel");
            bot_clean.setTitle("Limpiar");
            bot_clean.setUpdate("aut_filtro_descripcion,tab_tabla1,tab_tabla2,grup_titulo,btn_anular");
            bot_clean.setMetodo("limpiar");

            agregarComponente(dia_anulacion);

            bar_botones.agregarBoton(bot_clean);
            eti_titulo.setStyle("font-size: 15px;font-weight: bold");
            eti_descripcion.setStyle("font-size: 13px;font-weight: bold");
            eti_responsable.setStyle("font-size: 13px;font-weight: bold");

            espacio.setWidth("20");
            espacio.setHeight("17");

            tab_tabla1.setId("tab_tabla1");
            tab_tabla1.setTabla("rec_especies", "ide_documento", 1);
            tab_tabla1.onSelect("seleccionar_tabla1");
            tab_tabla1.agregarRelacion(tab_tabla2);
            tab_tabla1.getColumna("ide_cuenta").setCombo("conc_catalogo_cuentas", "ide_cuenta", "cue_codigo,cue_descripcion", "");
            tab_tabla1.getColumna("ide_cuenta").setAutoCompletar();
            tab_tabla1.getColumna("ide_clasificador").setCombo("conc_clasificador", "ide_clasificador", "pre_codigo,pre_descripcion", "");
            tab_tabla1.getColumna("ide_clasificador").setAutoCompletar();
            tab_tabla1.getColumna("fecha").setValorDefecto(utilitario.getFechaActual());
            //tab_tabla1.setTipoFormulario(true);
            tab_tabla1.setRows(4);
//        tab_tabla1.setRecuperarLectura(true);
//        tab_tabla1.setCondicion("ide_documento=-1");
            tab_tabla1.setCampoOrden("descripcion");
            tab_tabla1.dibujar();
            PanelTabla pat_panel1 = new PanelTabla();
            pat_panel1.setPanelTabla(tab_tabla1);

            tab_tabla2.setId("tab_tabla2");
            tab_tabla2.setTabla("rec_detalle", "ide_detalle", 2);
            tab_tabla2.setCampoForanea("ide_documento");
            tab_tabla2.getColumna("fecha_esp").setValorDefecto(utilitario.getFechaActual());
            tab_tabla2.getColumna("valor").setMetodoChange("calcular_valores");
            tab_tabla2.getColumna("rango_i").setMetodoChange("calcular_valores");
            tab_tabla2.getColumna("rango_f").setMetodoChange("calcular_valores");
            tab_tabla2.getColumna("numero_total_esp").setLectura(true);
            tab_tabla2.getColumna("valor_total_esp").setLectura(true);
            String rec = "";
            List sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
            if (!sql1.isEmpty()) {
                rec = String.valueOf(sql1.get(0));
            }
            tab_tabla2.getColumna("ide_empleado").setValorDefecto(ide_empleado);
            tab_tabla2.getColumna("ide_empleado").setVisible(false);
            tab_tabla2.setRecuperarLectura(true);
//        tab_tabla2.setTipoFormulario(true);
            tab_tabla2.getGrid().setColumns(4);
            if (tab_tabla1.getValorSeleccionado() != null) {
                tab_tabla2.setCampoOrden("rango_i");
            }
            tab_tabla2.setRows(4);
            tab_tabla2.setCampoOrden("ide_detalle desc");
            tab_tabla2.dibujar();

            cargar_titulo();

            Grid grup_titulo = new Grid();
            grup_titulo.setColumns(1);
            grup_titulo.setId("grup_titulo");
            grup_titulo.getChildren().add(eti_titulo);
            grup_titulo.getChildren().add(eti_descripcion);
            grup_titulo.getChildren().add(eti_responsable);

            Division div = new Division();
            div.setFooter(grup_titulo, pat_panel1, "33%");

            PanelTabla pat_panel2 = new PanelTabla();
            pat_panel2.setPanelTabla(tab_tabla2);

            div_division.setId("div_division");
            div_division.dividir2(div, pat_panel2, "55%", "H");

           // gru_pantalla.getChildren().add(bar_botones);
            agregarComponente(div_division);

            dia_anulacion.setId("dia_anulacion");
            dia_anulacion.setTitle("Anulacion de Especies");
            dia_anulacion.setWidth("30%");
            dia_anulacion.setHeight("60%");

            con_guardar.setId("con_guardar");
            con_guardar.setHeader("CONFIRMACION GUARDAR INGRESO DE ESPECIE");
            con_guardar.setMessage("Esta seguro de Guardar los Datos");
            con_guardar.getBot_aceptar().setMetodo("aceptar_venta_especie");
            con_guardar.getBot_aceptar().setUpdate("con_guardar,tab_tabla1,tab_tabla2,grup_titulo");
            agregarComponente(con_guardar);
        } else {
            utilitario.agregarNotificacionInfo("Mensaje", "No  tiene Asignado un Empleado para poder Realizar Ingreso de Especies");
        }

        Grid grid_matriz = new Grid();
        grid_matriz.setColumns(2);
        Etiqueta eti_tit = new Etiqueta();
        Etiqueta eti_tit1 = new Etiqueta();
        Etiqueta eti_ran1 = new Etiqueta();
        Etiqueta eti_ran2 = new Etiqueta();
        Etiqueta eti_ranin1 = new Etiqueta();
        Etiqueta eti_ranin2 = new Etiqueta();
        Espacio esp = new Espacio();
        Espacio esp1 = new Espacio();
        eti_ran1.setValue("Rango Inicial");
        eti_ran2.setValue("Rango Final");
        eti_ranin1.setValue("Rango Inicial");
        eti_ranin2.setValue("Rango Final");
        eti_tit.setValue("Especies Ingresadas");
        eti_tit.setStyle("font-size:15px");
        eti_tit1.setValue("Especies a Anular");
        eti_tit1.setStyle("font-size:15px");
        grid_matriz.getChildren().add(eti_tit);
        grid_matriz.getChildren().add(espacio);
        grid_matriz.getChildren().add(eti_especie);
        grid_matriz.getChildren().add(esp);
        grid_matriz.getChildren().add(eti_ranin1);
        grid_matriz.getChildren().add(txt_rangoi);
        grid_matriz.getChildren().add(eti_ranin2);
        grid_matriz.getChildren().add(txt_rangof);

        grid_matriz.getChildren().add(eti_tit1);
        grid_matriz.getChildren().add(esp1);

        grid_matriz.getChildren().add(eti_ran1);
        grid_matriz.getChildren().add(txt_rango_i);
        grid_matriz.getChildren().add(eti_ran2);
        grid_matriz.getChildren().add(txt_rango_f);

        txt_rangoi.setDisabled(true);
        txt_rangof.setDisabled(true);

        dia_anulacion.setDialogo(grid_matriz);
        dia_anulacion.getBot_aceptar().setMetodo("aceptar_anulacion");
//        dia_anulacion.getBot_aceptar().setUpdate("dia_anulacion");

        //para el reporte 

        rep_reporte.setId("rep_reporte");



        rep_reporte.getBot_aceptar().setMetodo("aceptar_reporte");
        rep_reporte.getBot_aceptar().setUpdate("rep_reporte,sel_tab,sel_cal");
        sel_tab.getBot_aceptar().setMetodo("aceptar_reporte");
        sel_tab.getBot_aceptar().setUpdate("sel_tab,sel_cal");
       agregarComponente(rep_reporte);

        sel_rep.setId("sel_rep");
       agregarComponente(sel_rep);

        sel_cal.setId("sel_cal");
        sel_cal.setMultiple(true);
        sel_cal.getBot_aceptar().setMetodo("aceptar_reporte");
        sel_cal.getBot_aceptar().setUpdate("sel_cal,sel_rep");
   agregarComponente(sel_cal);

        sel_tab.setId("sel_tab");
        sel_tab.setSeleccionTabla("select ide_documento,descripcion from rec_especies order by descripcion", "ide_documento");
//            sel_tab.getTab_seleccion().getColumna("descripcion").setFiltro(true);
        sel_tab.getTab_seleccion().setRows(8);

        sel_tab.getBot_aceptar().setMetodo("aceptar_reporte");
        sel_tab.getBot_aceptar().setUpdate("sel_tab,sel_cal");

   agregarComponente(sel_tab);

        //

   


    }
    Map parametros = new HashMap();

    public void abrir_reporte() {
//Se ejecuta cuando da click en el/-//- boton de Reportes de la Barra  
        rep_reporte.dibujar();

    }

    public void aceptar_reporte() {
//Se ejecuta cuando se selecciona un reporte de la lista

        if (rep_reporte.getReporteSelecionado().equals("Reporte Especies Anuladas")) {
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


        }
    }

    public void aceptar_anulacion() {


        List ver_anu = utilitario.getConexion().consultar("select *from rec_especie_anulado "
                + "where ide_detalle=" + tab_tabla2.getValor("ide_detalle") + " and ((rangoi<=" + txt_rango_i.getValue() + " and rangof>=" + txt_rango_i.getValue() + ") "
                + "or (rangoi<=" + txt_rango_f.getValue() + " and rangof>=" + txt_rango_f.getValue() + "))");

        if (ver_anu.size() == 0) {
            if ((txt_rango_i.getValue() != null && !txt_rango_i.getValue().toString().isEmpty()) && (txt_rango_f.getValue() != null && !txt_rango_f.getValue().toString().isEmpty())) {
                List sql = utilitario.getConexion().consultar("select rango_f1 from rec_entrega_detalle where ide_detalle=" + tab_tabla2.getValor("ide_detalle") + " "
                        + "and ((rango_i1<=" + txt_rango_i.getValue() + " and rango_f1>=" + txt_rango_i.getValue() + ") "
                        + "or (rango_i1<=" + txt_rango_f.getValue() + " and rango_f1>=" + txt_rango_f.getValue() + ")) order by rango_f1 desc");

                if (sql == null || sql.isEmpty()) {

                    bandera_anulacion = 1;
                    con_guardar.dibujar();
                    utilitario.addUpdate("con_guardar");
                    int canti = Integer.parseInt(txt_rango_f.getValue().toString()) - Integer.parseInt(txt_rango_i.getValue().toString()) + 1;
                    int ide_esp_anu = utilitario.getConexion().getMaximo("rec_especie_anulado ", "ide_especie_anulado");
                    String mensaje = utilitario.getConexion().ejecutar("insert into rec_especie_anulado (ide_especie_anulado,ide_detalle,ide_empleado,mun_ide_empleado,fecha_anulado,rangoi,rangof,cantidad,razon_baja) "
                            + "values(" + ide_esp_anu + "," + tab_tabla2.getValor("ide_detalle") + ",1," + obtener_ide_empleado() + ",'" + utilitario.getFechaActual() + "'," + txt_rango_i.getValue() + "," + txt_rango_f.getValue() + " "
                            + "," + canti + ",null ) ");
                    if (mensaje.isEmpty()) {
                        utilitario.getConexion().commit();
                        System.out.println("si se puede anular");
                    } else {
                        utilitario.getConexion().rollback();

                    }


//                String mensaje = utilitario.getConexion().ejecutar("insert rec_especie_anulado values(" + ide_esp_anu + "," + tab_tabla2.getValor("ide_Detalle") + "," + tab_tabla2.getValor("ide_empleado") + ",null," + utilitario.getFechaActual() + "," + txt_rango_i + ",'" + txt_rango_f + "'," + ran_i + "," + ran_f + "," + cant + ",null,null,null)");
//                if (mensaje.isEmpty()) {
//                    utilitario.getConexion().commit();
//                    utilitario.agregarMensaje("Guardar Reaisgnacion", "La Reasignacion se Guardo Correctamente");
//                    dia_anulacion.cerrar();
//                } else {
//                    utilitario.getConexion().rollback();
//
//                }
                    dia_anulacion.cerrar();
                    utilitario.addUpdate("dia_anulacion");
                } else {
                    int num = Integer.parseInt(sql.get(0).toString());
                    num = num + 1;
                    utilitario.agregarMensajeError("Anulacion de Especies", "No se puede Anular Una de las Especies ya se Encuentra Vendida, puede anular desde la especie numero " + num);
                }
            }
        } else {
            utilitario.agregarMensajeError("Anulacion de Especies", "Una de las especies ya se Encuentran Anuladas ");
        }

    }

    public void anular() {
        txt_rangoi.setValue(tab_tabla2.getValor("rango_i"));
        txt_rangof.setValue(tab_tabla2.getValor("rango_f"));
        String des = tab_tabla1.getValor("descripcion");

        eti_especie.setValue("Descripcion: " + des);

        dia_anulacion.dibujar();

    }

    public String obtener_ide_empleado() {
        String ide_empl = "-1";
        List list_sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
            ide_empl = String.valueOf(list_sql1.get(0));
        }
        return ide_empl;
    }

    public void limpiar() {
        aut_filtro_descripcion.setValue(null);
        btn_anular.setDisabled(true);
        tab_tabla1.setCondicion("");
        tab_tabla1.ejecutarSql();
        cargar_titulo();
    }

    public void aceptar_venta_especie() {
        if (bandera_anulacion == 0) {

            if (tab_tabla1.isFocus()) {
                tab_tabla1.guardar();
                utilitario.getConexion().guardarPantalla();
                cargar_titulo();
                con_guardar.cerrar();
            }

            if (tab_tabla2.isFocus()) {
                boolean val = tab_tabla2.isFilaInsertada();
                if (val == true) {

                    rango_i = "";
                    rango_f = "";
                    try {
                        if (Integer.parseInt(tab_tabla2.getValor("rango_i")) == 0
                                || Integer.parseInt(tab_tabla2.getValor("rango_f")) == 0) {
                            utilitario.agregarMensajeInfo("Mensaje", "Los Rangos no pueden ser Cero ");
                        } else {
                            if (tab_tabla2.getValor("rango_i") == null || tab_tabla2.getValor("rango_i").isEmpty()
                                    || tab_tabla2.getValor("rango_f") == null || tab_tabla2.getValor("rango_f").isEmpty()) {
                                utilitario.agregarMensajeInfo("Mensaje", "Los Rangos no pueden ser Nullos ");
                            }
                            int ri = Integer.parseInt(tab_tabla2.getValor("rango_i"));
                            int rf = Integer.parseInt(tab_tabla2.getValor("rango_f"));

                            if (rf < ri) {
                                rango_i = "ddd";
                                utilitario.agregarMensajeInfo("Mensaje", "El Rango Final no puede ser menor que el Rango Inicial ");
                            } else {
                                List list_sql1 = utilitario.getConexion().consultar("select rango_i,rango_f from rec_detalle "
                                        + "where ide_documento=" + tab_tabla1.getValor("ide_documento")
                                        + "and ((rango_i<=" + tab_tabla2.getValor("rango_i") + " and rango_f>=" + tab_tabla2.getValor("rango_i") + ") "
                                        + "or (rango_i<=" + tab_tabla2.getValor("rango_f") + " and rango_f>=" + tab_tabla2.getValor("rango_f") + "))"
                                        + "order by rango_i");
                                for (int i = 0; i < list_sql1.size(); i++) {
                                    Object[] fila = (Object[]) list_sql1.get(i);
                                    rango_i = fila[0] + "";
                                    rango_f = fila[1] + "";
                                }
                            }

                            if (rango_i == null || rango_i.isEmpty()) {
                                tab_tabla2.guardar();
                                utilitario.getConexion().guardarPantalla();
                            } else {
                                utilitario.agregarMensajeInfo("Mensaje", "Los rangos interfieren en rango inicial " + rango_i + " y en rango final " + rango_f);
                            }
                        }

                    } catch (Exception e) {
                        utilitario.agregarMensajeInfo("Mensaje", "Los Rangos deben ser numeros no letras ");
                    }
                } else {
                    tab_tabla2.guardar();
                    utilitario.getConexion().guardarPantalla();
                }
                cargar_titulo();
                con_guardar.cerrar();
            }
        }
        if (bandera_anulacion == 1) {
            con_guardar.cerrar();
            System.out.println("guaradr ");
        }

    }

    public void cargar_titulo() {
        String des = tab_tabla1.getValor("descripcion");
        if (des != null) {
            eti_descripcion.setValue("Descripcion: " + des);
        } else {
            eti_descripcion.setValue("Descripcion: ");
        }
        String rec = "";
        List sql1 = utilitario.getConexion().consultar("SELECT  nombres FROM munc_empleados WHERE ide_empleado =(SELECT ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql1.isEmpty()) {
            rec = (String) sql1.get(0);
        }
        eti_titulo.setValue("INGRESO DE ESPECIES Y TALONARIOS ");
        eti_responsable.setValue("Responsable: " + rec);
    }

    public void filtrar_por_descripcion(SelectEvent evt) {
        aut_filtro_descripcion.onSelect(evt);
        tab_tabla1.setCondicion("ide_documento=" + aut_filtro_descripcion.getValor());
        tab_tabla1.ejecutarSql();
        cargar_titulo();
    }

    public void calcular_valores(AjaxBehaviorEvent evt) {
        tab_tabla2.modificar(evt);
        String ran_i = tab_tabla2.getValor("rango_i");
        String ran_f = tab_tabla2.getValor("rango_f");
        String val = tab_tabla2.getValor("valor");
        if ((!ran_i.isEmpty() || !ran_i.equalsIgnoreCase("null") || ran_i != null)
                && (!ran_f.isEmpty() || !ran_f.equalsIgnoreCase("null") || ran_f != null)
                && (!val.isEmpty() || !val.equalsIgnoreCase("null") || val != null)) {

            try {
                int r_ini = Integer.parseInt(tab_tabla2.getValor("rango_i"));
                int r_fin = Integer.parseInt(tab_tabla2.getValor("rango_f"));
                int tot_esp = r_fin - r_ini + 1;
                float val_tot = tot_esp * Float.parseFloat(tab_tabla2.getValor("valor"));

                int fila_modificada = tab_tabla2.getUltimaFilaModificada();
                tab_tabla2.setValor(fila_modificada, "numero_total_esp", tot_esp + "");
                tab_tabla2.setValor(fila_modificada, "valor_total_esp", utilitario.getFormatoNumero(val_tot) + "");
                utilitario.addUpdateTabla(tab_tabla2, "valor_total_esp,numero_total_esp", "");

            } catch (Exception e) {
                utilitario.agregarMensajeInfo("Mensaje", "Los Rangos son Incorrectos ");
            }

        }

    }

    public void insertar() {
        //  tab_tabla2.setRecuperarLectura(true);
        if (tab_tabla1.isFocus()) {
            if (tab_tabla1.isFilaInsertada() == false) {
                tab_tabla1.insertar();
            } else {
                utilitario.agregarMensajeInfo("No se pudo insertar", "debe guardar la transaccion que se esta trabajando");
            }
        } else if (tab_tabla2.isFocus()) {
            if (tab_tabla2.isFilaInsertada() == false) {
                tab_tabla2.insertar();
            } else {
                utilitario.agregarMensajeInfo("No se pudo insertar", "debe guardar la transaccion que se esta trabajando");
            }
        }
    }

    public void guardar() {
        bandera_anulacion = 0;
        con_guardar.dibujar();
    }

    public void eliminar() {
        if (tab_tabla2.getTotalFilas() == 0) {
            tab_tabla1.eliminar();

        }
        tab_tabla2.eliminar();

    }

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla1.seleccionarFila(evt);
        cargar_titulo();
        tab_tabla2.setCampoOrden("rango_i");

    }

    public Division getDiv_division() {
        return div_division;
    }

    public void setDiv_division(Division div_division) {
        this.div_division = div_division;
    }

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }

    public Grupo getGru_pantalla() {
        return gru_pantalla;
    }

    public void setGru_pantalla(Grupo gru_pantalla) {
        this.gru_pantalla = gru_pantalla;
    }

    public AutoCompletar getAut_filtro_descripcion() {
        return aut_filtro_descripcion;
    }

    public void setAut_filtro_descripcion(AutoCompletar aut_filtro_descripcion) {
        this.aut_filtro_descripcion = aut_filtro_descripcion;
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

    public Dialogo getDia_anulacion() {
        return dia_anulacion;
    }

    public void setDia_anulacion(Dialogo dia_anulacion) {
        this.dia_anulacion = dia_anulacion;
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

    public SeleccionTabla getSel_tab() {
        return sel_tab;
    }

    public void setSel_tab(SeleccionTabla sel_tab) {
        this.sel_tab = sel_tab;
    }
}
