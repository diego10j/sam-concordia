/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package especies;

import sistema.Pantalla;
import framework.*;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author user
 */
public class pre_asignacion_especie extends Pantalla{

 
    private Tabla tab_tabla = new Tabla();
    private Tabla tab_tabla1 = new Tabla();
 
    private Division div_division = new Division();

    private Etiqueta eti_titulo = new Etiqueta();
    private Etiqueta eti_responsable = new Etiqueta();
    private AutoCompletar aut_filtro_descripcion = new AutoCompletar();
    private String descripcion_actual = "-1";
    private Dialogo dia_reasignacion = new Dialogo();
    private Boton btn_reasignar = new Boton();
    private Texto txt_rango_i = new Texto();
    private Texto txt_rango_f = new Texto();
    private Texto txt_rangoi = new Texto();
    private Texto txt_rangof = new Texto();
    private Texto txt_empl1 = new Texto();
    private Texto txt_cant = new Texto();
    private Combo cmb_caja = new Combo();
    private Combo cmb_empleado = new Combo();
    private Boton bot_clean = new Boton();

    public pre_asignacion_especie() {
        bar_botones.getBot_insertar().setUpdate("tab_tabla,tab_tabla1");
        bar_botones.getBot_guardar().setUpdate("tab_tabla,tab_tabla1");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla,tab_tabla1");
        bar_botones.agregarBoton(btn_reasignar);

        btn_reasignar.setId("btn_reasignar");
        btn_reasignar.setValue("Reasignar");
        btn_reasignar.setValueExpression("disabled", "pre_index.clase.tab_tabla.totalFilas==0");
        btn_reasignar.setMetodo("reasignar");
        btn_reasignar.setUpdate("dia_reasignacion");

        bot_clean.setIcon("ui-icon-cancel");
        bot_clean.setTitle("Limpiar");
        bot_clean.setUpdate("aut_filtro_descripcion,tab_tabla1,tab_tabla,grup_titulo,btn_reasignar");
        bot_clean.setMetodo("limpiar");

        agregarComponente(dia_reasignacion);

        aut_filtro_descripcion.setId("aut_filtro_descripcion");
        aut_filtro_descripcion.setAutoCompletar("select ide_documento,descripcion from rec_especies ");
        aut_filtro_descripcion.setMetodoChange("filtrar_por_descripcion", "tab_tabla,tab_tabla1,grup_titulo,btn_reasignar");
        bar_botones.agregarComponente(aut_filtro_descripcion);
        bar_botones.agregarBoton(bot_clean);


        eti_titulo.setStyle("font-size: 15px;font-weight: bold");
        eti_responsable.setStyle("font-size: 13px;font-weight: bold");


        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setSql("select ide_detalle,descripcion,valor,rango_i,rango_f,numero_total_esp,valor_total_esp,fecha_esp,observacion from rec_detalle d,rec_especies e "
                + "where d.ide_documento=" + descripcion_actual + ""
                + "and e.ide_documento=" + descripcion_actual + " order by ide_detalle desc ");
        tab_tabla1.setCampoPrimaria("ide_detalle");
        tab_tabla1.setNumeroTabla(1);
        tab_tabla1.onSelect("seleccionar_tabla1");
        tab_tabla1.agregarRelacion(tab_tabla);

        tab_tabla1.setRows(4);
        tab_tabla1.setLectura(true);

        tab_tabla1.dibujar();

        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("rec_entrega_especies", "ide_entrega", 2);
        tab_tabla.setCampoForanea("ide_detalle");

        tab_tabla.getColumna("rec_ide_entrega").setVisible(false);
        String rec = "";
        List sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (!sql1.isEmpty()) {
            rec = String.valueOf(sql1.get(0));
        }
        tab_tabla.getColumna("ide_empleado").setValorDefecto(rec);
        tab_tabla.getColumna("ide_empleado").setVisible(false);

        tab_tabla.getColumna("mun_ide_empleado").setCombo("munc_empleados", "ide_empleado", "nombres", "");
        tab_tabla.getColumna("mun_ide_empleado").setPermitirNullCombo(false);
        //        tab_tabla.getColumna("mun_ide_empleado").setAutoCompletar();
        tab_tabla.getColumna("ide_caja").setCombo("tes_caja", "ide_caja", "des_caja", "");
//        tab_tabla.getColumna("ide_caja").setAutoCompletar();
        tab_tabla.getColumna("ide_caja").setMetodoChange("cargar_empleado");
        tab_tabla.getColumna("fecha_entrega").setValorDefecto(utilitario.getFechaActual());
        tab_tabla.getColumna("cantidad").setLectura(true);
        tab_tabla.getColumna("rangoi").setMetodoChange("calcular_valor");
        tab_tabla.getColumna("rangof").setMetodoChange("calcular_valor");
        tab_tabla.getColumna("estatus").setVisible(false);
        tab_tabla.getColumna("fecha_termina").setVisible(false);
        tab_tabla.getColumna("estatus").setValorDefecto("true");
        tab_tabla.getColumna("estatus_reasig").setLectura(true);
        tab_tabla.setTipoFormulario(true);
//        tab_tabla.setRecuperarLectura(true);
        tab_tabla.getGrid().setColumns(6);

        tab_tabla.dibujar();


        cargar_titulo();

        Grid grup_titulo = new Grid();
        grup_titulo.setColumns(1);
        grup_titulo.setWidth("100%");
        grup_titulo.setId("grup_titulo");
        grup_titulo.getChildren().add(eti_titulo);
        grup_titulo.getChildren().add(eti_responsable);

        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla1);
        pat_panel.getMenuTabla().getItem_eliminar().setRendered(false);
        pat_panel.getMenuTabla().getItem_guardar().setRendered(false);

        Division div = new Division();
        div.setFooter(grup_titulo, pat_panel, "20%");

        PanelTabla pat_panel1 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla);


        div_division.setId("div_division");
        div_division.dividir2(div, pat_panel1, "50%", "H");

   //     gru_pantalla.getChildren().add(bar_botones);
      //  gru_pantalla.getChildren().add(div_division);
        agregarComponente(div_division);

        dia_reasignacion.setId("dia_reasignacion");
        dia_reasignacion.setTitle("Rasignacion de Especies");
        dia_reasignacion.setWidth("40%");
        dia_reasignacion.setHeight("50%");

        txt_rango_i.setId("txt_rango_i");
        txt_rango_f.setId("txt_rango_f");
        txt_rango_i.setSoloEnteros();
        txt_rango_f.setSoloEnteros();
        txt_rango_i.setSize(5);
        txt_rango_f.setSize(5);


        cmb_caja.setCombo("select ide_caja,des_caja from tes_caja ");
        cmb_caja.setMetodo("cargar_empleado_reasignacion", "cmb_empleado");

        txt_rango_f.setMetodoChange("calcular_cant_reasignada", "txt_cant");

        txt_cant.setId("txt_cant");


        cmb_empleado.setId("cmb_empleado");
        cmb_empleado.setCombo("select ide_empleado,nombres from munc_empleados where ide_empleado=-1");
        cmb_empleado.eliminarVacio();

        txt_rangoi.setDisabled(true);
        txt_rangof.setDisabled(true);
        txt_empl1.setDisabled(true);
        txt_rango_i.setDisabled(true);
        txt_cant.setDisabled(true);

        Grid grid_matriz = new Grid();
        grid_matriz.setColumns(2);
        Etiqueta eti_ran1 = new Etiqueta();
        Etiqueta eti_ran2 = new Etiqueta();
        Etiqueta eti_caja = new Etiqueta();
        Etiqueta eti_empleado = new Etiqueta();
        Etiqueta eti_rango_i = new Etiqueta();
        Etiqueta eti_rango_f = new Etiqueta();
        Etiqueta eti_empl1 = new Etiqueta();
        Etiqueta eti_cant = new Etiqueta();
        eti_empl1.setValue("Empleado Asignado");
        eti_rango_i.setValue("Rango Inicial");
        eti_rango_f.setValue("Rango Final");
        eti_ran1.setValue("Rango Inicial");
        eti_ran2.setValue("Rango Final");
        eti_caja.setValue("Caja");
        eti_empleado.setValue("Empleado");
        eti_cant.setValue("Cantidad");
        grid_matriz.getChildren().add(eti_empl1);
        grid_matriz.getChildren().add(txt_empl1);
        grid_matriz.getChildren().add(eti_rango_i);
        grid_matriz.getChildren().add(txt_rangoi);
        grid_matriz.getChildren().add(eti_rango_f);
        grid_matriz.getChildren().add(txt_rangof);
        grid_matriz.getChildren().add(eti_ran1);
        grid_matriz.getChildren().add(txt_rango_i);
        grid_matriz.getChildren().add(eti_ran2);
        grid_matriz.getChildren().add(txt_rango_f);
        grid_matriz.getChildren().add(eti_cant);
        grid_matriz.getChildren().add(txt_cant);
        grid_matriz.getChildren().add(eti_caja);
        grid_matriz.getChildren().add(cmb_caja);
        grid_matriz.getChildren().add(eti_empleado);
        grid_matriz.getChildren().add(cmb_empleado);


        dia_reasignacion.setDialogo(grid_matriz);
        dia_reasignacion.getBot_aceptar().setMetodo("aceptar_reasignacion1");
        dia_reasignacion.getBot_aceptar().setUpdate("dia_reasignacion");
     
    }

    public void calcular_cant_reasignada() {
        int ran_i = Integer.parseInt(txt_rango_i.getValue().toString());
        int ran_f = Integer.parseInt(txt_rango_f.getValue().toString());
        int cant = ran_f - ran_i + 1;
        txt_cant.setValue(cant);

    }

    public void aceptar_reasignacion1() {
        if ((txt_rango_i.getValue() != null && !txt_rango_i.getValue().toString().isEmpty()) && (txt_rango_f.getValue() != null && !txt_rango_f.getValue().toString().isEmpty())) {
            String rango_i = "";
            String rango_f = "";
            int rin_i = Integer.parseInt(txt_rango_i.getValue().toString());
            int rin_f = Integer.parseInt(txt_rango_f.getValue().toString());
            int ri = Integer.parseInt(tab_tabla1.getValor("rango_i"));
            int rf = Integer.parseInt(tab_tabla1.getValor("rango_f"));
//            if (ra == null || ra.isEmpty() || ra.equals("null")) {
            int band = -1;
            if (rin_f < rin_i) {
                utilitario.agregarMensajeInfo("No se pude Asignar ", "El rango final es menor que el rango inicial ");
                dia_reasignacion.cerrar();
            } else {
                if (rin_i < ri || rin_f > rf) {
                    utilitario.agregarMensajeInfo("No se pude Asignar ", "Los rangos no pertenecen al talonario");
                    dia_reasignacion.cerrar();
                } else {

                    List list_sql1 = utilitario.getConexion().consultar("select rangoi,rangof from rec_entrega_especies "
                            + "where ide_detalle=" + tab_tabla1.getValor("ide_detalle")
                            + "and ((rangoi<=" + tab_tabla.getValor("rangoi") + " and rangof>=" + tab_tabla.getValor("rangof") + ") "
                            + "or (rangoi>=" + tab_tabla.getValor("rangoi") + " and rangof<=" + tab_tabla.getValor("rangof") + "))"
                            + "and rec_ide_entrega is NOT null order by rangoi");
                    for (int i = 0; i < list_sql1.size(); i++) {
                        Object[] fila = (Object[]) list_sql1.get(i);
                        rango_i = fila[0] + "";
                        rango_f = fila[1] + "";
                    }
                    if (rango_i == null || rango_i.isEmpty()) {
                        band = 1;
                    } else {
                        utilitario.agregarMensajeInfo("Mensaje", "Los rangos ya estan asignados, Rango Inicial minimo a asignar " + (Integer.parseInt(rango_f) + 1));
                        dia_reasignacion.cerrar();
                    }
                }
            }

            if (band > 0) {
                int ide_ent = utilitario.getConexion().getMaximo("rec_entrega_especies", "ide_entrega");
                int ide_caja = Integer.parseInt(cmb_caja.getValue().toString());
                int ran_i = Integer.parseInt(txt_rango_i.getValue().toString());
                int ran_f = Integer.parseInt(txt_rango_f.getValue().toString());
                String emp = "";
                List sql1 = utilitario.getConexion().consultar("SELECT  ide_empleado FROM munc_empleados WHERE ide_empleado =(SELECT ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
                if (!sql1.isEmpty()) {
                    emp = String.valueOf(sql1.get(0));
                }
                int ide_empl = Integer.parseInt(emp);
                int mun_ide_empl = Integer.parseInt(cmb_empleado.getValue().toString());
                int rec_ide_ent = Integer.parseInt(tab_tabla.getValor("ide_entrega"));
                int ide_det = Integer.parseInt(tab_tabla1.getValor("ide_detalle"));
                int cant = Integer.parseInt(txt_cant.getValue().toString());
                utilitario.getConexion().ejecutar("update rec_entrega_especies set estatus_reasig=true where ide_entrega="+rec_ide_ent);
                String mensaje = utilitario.getConexion().ejecutar("insert into rec_entrega_especies values(" + ide_ent + "," + ide_caja + "," + ide_empl + "," + rec_ide_ent + "," + ide_det + "," + mun_ide_empl + ",'" + utilitario.getFechaActual() + "'," + ran_i + "," + ran_f + "," + cant + ",null,null,true,false)");
                if (mensaje.isEmpty()) {
                    utilitario.getConexion().commit();
                    utilitario.agregarMensaje("Guardar Reaisgnacion", "La Reasignacion se Guardo Correctamente");
                    dia_reasignacion.cerrar();
                } else {
                    utilitario.getConexion().rollback();

                }

            }

        }


    }

    public void reasignar() {
        txt_rangoi.setValue(tab_tabla.getValor("rangoi"));
        txt_rangof.setValue(tab_tabla.getValor("rangof"));
        
        String nombres = "";
        List sql = utilitario.getConexion().consultar("select nombres from munc_empleados where ide_empleado=" + tab_tabla.getValor("mun_ide_empleado"));
        if (!sql.isEmpty()) {
            nombres = (String) sql.get(0);
        }
        txt_empl1.setValue(nombres);
        String ra = "";
        List rango = utilitario.getConexion().consultar("select max(rango_f1) from rec_entrega_detalle where ide_entrega=" + tab_tabla.getValor("ide_entrega"));
        if (!rango.isEmpty()) {
            ra = String.valueOf(rango.get(0));
        }
        if (ra == null || ra.isEmpty() || ra.equals("null")) {
            txt_rango_i.setValue(tab_tabla.getValor("rangoi"));
        } else {
            int ra1 = Integer.parseInt(ra);
            ra1 = ra1 + 1;
            txt_rango_i.setValue(ra1 + "");
        }

        dia_reasignacion.dibujar();

    }

    public void cargar_empleado_reasignacion() {
        List sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_caja=" + cmb_caja.getValue() + " and ide_empleado != " + tab_tabla.getValor("mun_ide_empleado"));
        if (!sql1.isEmpty()) {
            String ide_empl = "";
            for (int i = 0; i < sql1.size(); i++) {
                if (i > 0) {
                    ide_empl += ",";
                }
                ide_empl += "'" + sql1.get(i) + "'";
            }
            cmb_empleado.setCombo("select ide_empleado,nombres from munc_empleados where ide_empleado in (" + ide_empl + ")");
            cmb_empleado.eliminarVacio();

        } else {
            utilitario.agregarMensajeInfo("Cajas", "La caja no tiene asignado a un usuario");
            cmb_empleado.getChildren().clear();
        }

    }

    public void limpiar() {
        aut_filtro_descripcion.setValue(null);
        btn_reasignar.setDisabled(true);
        tab_tabla1.limpiar();
        cargar_titulo();
    }

    public void cargar_empleado(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        List sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_caja=" + tab_tabla.getValor("ide_caja"));
        if (!sql1.isEmpty()) {
            String ide_empl = "";
            for (int i = 0; i < sql1.size(); i++) {
                if (sql1.get(i) != null && !sql1.isEmpty()) {
                    if (i > 0) {
                        ide_empl += ",";
                    }
                    ide_empl += "'" + sql1.get(i) + "'";
                }
            }
            if (!ide_empl.isEmpty()) {
                tab_tabla.getColumna("mun_ide_empleado").setCombo("munc_empleados", "ide_empleado", "nombres", "ide_empleado in (" + ide_empl + ")");
                utilitario.addUpdate("tab_tabla");
            } else {
                tab_tabla.getColumna("mun_ide_empleado").setCombo("munc_empleados", "ide_empleado", "nombres", "ide_empleado=-1 ");
                utilitario.addUpdate("tab_tabla");
                utilitario.agregarMensajeInfo("Cajas", "La caja no tiene asignado usuarios");
            }
        } else {
            tab_tabla.getColumna("mun_ide_empleado").setCombo("munc_empleados", "ide_empleado", "nombres", "ide_empleado=-1 ");
            utilitario.addUpdate("tab_tabla");
            utilitario.agregarMensajeInfo("Cajas", "La caja no tiene asignado a un usuario");
        }

    }

    public void calcular_valor(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        if (!tab_tabla.getValor("rangof").isEmpty()) {
            try {
                int rango_i = Integer.parseInt(tab_tabla.getValor("rangoi"));
                int rango_f = Integer.parseInt(tab_tabla.getValor("rangof"));
                if (rango_f < rango_i) {
                    int fila_mofificada = tab_tabla.getUltimaFilaModificada();
                    tab_tabla.setValor(fila_mofificada, "cantidad", 0 + "");
                    utilitario.addUpdateTabla(tab_tabla, "cantidad", "");
                } else {
                    int cant = rango_f - rango_i + 1;
                    int fila_mofificada = tab_tabla.getUltimaFilaModificada();
                    tab_tabla.setValor(fila_mofificada, "cantidad", cant + "");
                    utilitario.addUpdateTabla(tab_tabla, "cantidad", "");
                }
            } catch (Exception e) {
                utilitario.agregarMensajeInfo("Mensaje", "Los Rangos son Incorrectos ");

            }
        }


    }

    public void filtrar_por_descripcion(SelectEvent evt) {
        aut_filtro_descripcion.onSelect(evt);
        descripcion_actual = aut_filtro_descripcion.getValor();
        tab_tabla1.setSql("select ide_detalle,descripcion,valor,rango_i,rango_f,numero_total_esp,valor_total_esp,fecha_esp,observacion from rec_detalle d,rec_especies e "
                + "where d.ide_documento=" + descripcion_actual + ""
                + "and e.ide_documento=" + descripcion_actual + " order by ide_detalle desc");
        tab_tabla1.ejecutarSql();

        cargar_titulo();
    }

    public void cargar_titulo() {
        eti_titulo.setValue("ASIGNACION DE ESPECIES ");
        String rec = "";
        List sql1 = utilitario.getConexion().consultar("SELECT  nombres FROM munc_empleados WHERE ide_empleado =(SELECT ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql1.isEmpty()) {
            rec = (String) sql1.get(0);
        }
        eti_responsable.setValue("Empleado: " + rec);

    }

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla1.seleccionarFila(evt);
        cargar_titulo();

    }

    public Tabla getTab_tabla1() {
        return tab_tabla1;
    }

    public void setTab_tabla1(Tabla tab_tabla1) {
        this.tab_tabla1 = tab_tabla1;
    }
@Override
    public void insertar() {
        tab_tabla.insertar();
        tab_tabla.getColumna("mun_ide_empleado").setCombo("munc_empleados", "ide_empleado", "nombres", "ide_empleado=-1 ");
        utilitario.addUpdate("tab_tabla");

    }
@Override
    public void guardar() {
        String rango_i = "";
        String rango_f = "";
        int ri = Integer.parseInt(tab_tabla1.getValor("rango_i"));
        int rf = Integer.parseInt(tab_tabla1.getValor("rango_f"));
        int rin_i = Integer.parseInt(tab_tabla.getValor("rangoi"));
        int rin_f = Integer.parseInt(tab_tabla.getValor("rangof"));


        if (tab_tabla.isFilaInsertada()) {
            if (rin_f < rin_i) {
                utilitario.agregarMensajeInfo("No se pude Asignar ", "El rango final es menor que el rango inicial ");
            } else {
                if (rin_i < ri || rin_f > rf) {
                    utilitario.agregarMensajeInfo("No se pude Asignar ", "Los rangos no pertenecen al talonario");
                } else {

                    List list_sql1 = utilitario.getConexion().consultar("select rangoi,rangof from rec_entrega_especies "
                            + "where ide_detalle=" + tab_tabla1.getValor("ide_detalle")
                            + "and ((rangoi<=" + tab_tabla.getValor("rangoi") + " and rangof>=" + tab_tabla.getValor("rangoi") + " ) "
                            + "or (rangoi<=" + tab_tabla.getValor("rangof") + " and rangof>=" + tab_tabla.getValor("rangof") + "))"
                            + "order by rangoi");
                    for (int i = 0; i < list_sql1.size(); i++) {
                        Object[] fila = (Object[]) list_sql1.get(i);
                        rango_i = fila[0] + "";
                        rango_f = fila[1] + "";
                    }
                    if (rango_i == null || rango_i.isEmpty()) {
                        int fila=tab_tabla.getUltimaFilaModificada();
                        tab_tabla.setValor(fila, "estatus_reasig", "null");
                        tab_tabla.guardar();
                        utilitario.getConexion().guardarPantalla();
                    } else {
                        utilitario.agregarMensajeInfo("Mensaje", "Los rangos ya estan asignados, Rango Inicial minimo a asignar " + (Integer.parseInt(rango_f) + 1));
                    }
                }
            }
        }
        if (tab_tabla.isFilaModificada()) {
            List vend = utilitario.getConexion().consultar("select *from rec_entrega_detalle where ide_entrega=" + tab_tabla.getValor("ide_entrega"));
            if (vend == null || vend.isEmpty()) {

                List rangos_asig = utilitario.getConexion().consultar("select rangoi,rangof from rec_entrega_especies where ide_entrega=" + tab_tabla.getValor("ide_entrega"));
                Object[] fila = (Object[]) rangos_asig.get(0);
                ri = Integer.parseInt(fila[0] + "");
                rf = Integer.parseInt(fila[1] + "");
                if (rin_f < rin_i) {
                    utilitario.agregarMensajeInfo("No se pude Asignar ", "El rango final es menor que el rango inicial ");
                } else {
                    List list_sql1 = utilitario.getConexion().consultar("select rangoi,rangof from rec_entrega_especies "
                            + "where ide_detalle=" + tab_tabla1.getValor("ide_detalle")
                            + "and ((rangoi<=" + tab_tabla.getValor("rangoi") + " and rangof>=" + tab_tabla.getValor("rangoi") + " ) "
                            + "or (rangoi<=" + tab_tabla.getValor("rangof") + " and rangof>=" + tab_tabla.getValor("rangof") + "))"
                            + "and ide_entrega!=" + tab_tabla.getValor("ide_entrega") + " order by rangoi");
                    for (int i = 0; i < list_sql1.size(); i++) {
                        Object[] fila1 = (Object[]) list_sql1.get(i);
                        rango_i = fila1[0] + "";
                        rango_f = fila1[1] + "";
                    }
                    if (rango_i == null || rango_i.isEmpty()) {
                        int fila1=tab_tabla.getUltimaFilaModificada();
                        tab_tabla.setValor(fila1, "estatus_reasig", "null");
                        tab_tabla.guardar();
                        utilitario.getConexion().guardarPantalla();
                    } else {
                        utilitario.agregarMensajeInfo("Mensaje", "Los rangos ya estan asignados, Rango Inicial minimo a asignar " + (Integer.parseInt(rango_f) + 1));
                    }
                }
            } else {
                utilitario.agregarMensajeError("Atencion", "No se puede Modificar la Asignacion ya tiene Realizada Ventas");
            }
        }
    }
@Override
    public void eliminar() {
        tab_tabla.eliminar();
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
    }
*/
    public AutoCompletar getAut_filtro_descripcion() {
        return aut_filtro_descripcion;
    }

    public void setAut_filtro_descripcion(AutoCompletar aut_filtro_descripcion) {
        this.aut_filtro_descripcion = aut_filtro_descripcion;
    }

    public Dialogo getDia_reasignacion() {
        return dia_reasignacion;
    }

    public void setDia_reasignacion(Dialogo dia_reasignacion) {
        this.dia_reasignacion = dia_reasignacion;
    }
}
