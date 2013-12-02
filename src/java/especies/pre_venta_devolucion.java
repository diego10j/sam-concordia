/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package especies;

import framework.*;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import sistema.Pantalla;

/**
 *
 * @author Byron
 */
public class pre_venta_devolucion extends Pantalla {

    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    // private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    //  private Grupo gru_pantalla = new Grupo();
    private Etiqueta eti_titulo = new Etiqueta();
    private Etiqueta eti_empleado = new Etiqueta();
    private Etiqueta eti_caja = new Etiqueta();
    private Etiqueta eti_descripcion = new Etiqueta();
    private Confirmar con_guardar = new Confirmar();
    private AutoCompletar aut_filtro_descripcion = new AutoCompletar();
    private String descripcion_actual = "-1";
    private String ide_empleado;
    private Etiqueta eti_recaudador = new Etiqueta();
    private Etiqueta eti_valor = new Etiqueta();
    private Etiqueta eti_cambio = new Etiqueta();

    public pre_venta_devolucion() {
        ide_empleado = obtener_ide_empleado();
        if (!ide_empleado.equals("-1")) {

            bar_botones.getBot_insertar().setUpdate("tab_tabla2,grup_titulo");
            bar_botones.getBot_guardar().setUpdate("con_guardar");
            bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2");

            aut_filtro_descripcion.setId("aut_filtro_descripcion");
            aut_filtro_descripcion.setAutoCompletar("select ide_documento,descripcion from rec_especies ");
            aut_filtro_descripcion.setMetodoChange("filtrar_por_descripcion", "tab_tabla1,tab_tabla2,grup_titulo");
            bar_botones.agregarComponente(aut_filtro_descripcion);

            eti_titulo.setStyle("font-size: 14px;font-weight: bold");
            eti_descripcion.setStyle("font-size: 12px;font-weight: bold");
            eti_empleado.setStyle("font-size: 12px;font-weight: bold");
            eti_caja.setStyle("font-size: 12px;font-weight: bold");

            tab_tabla1.setId("tab_tabla1");
            tab_tabla1.setSql("select dv.ide_devolucion,dv.ide_detalle,cantidad_d,descripcion,rango_id,rango_fd,d.valor,cantidad_d*d.valor as valor_total,razon from rec_devolucion dv,rec_detalle d,rec_especies e "
                    + "WHERE dv.ide_empleado=" + ide_empleado + " "
                    + "and d.ide_detalle in (select ee.ide_detalle from rec_entrega_especies ee where ee.ide_entrega IN "
                    + "(select ed1.ide_entrega from rec_entrega_detalle ed1 where ed1.ide_entrega_detalle=dv.ide_entrega_detalle )) "
                    + "and e.ide_documento =d.ide_documento and d.ide_documento=" + descripcion_actual + " order by dv.ide_devolucion desc ");
            tab_tabla1.setCampoPrimaria("ide_devolucion");
            tab_tabla1.setNumeroTabla(1);
            tab_tabla1.getColumna("ide_detalle").setVisible(false);
            tab_tabla1.onSelect("seleccionar_tabla1");
            tab_tabla1.agregarRelacion(tab_tabla2);
            tab_tabla1.setLectura(true);
            tab_tabla1.setRows(4);
            tab_tabla1.dibujar();

            PanelTabla pat_panel1 = new PanelTabla();
            pat_panel1.setPanelTabla(tab_tabla1);

            Grid grup_titulo = new Grid();
            grup_titulo.setColumns(1);
            grup_titulo.setId("grup_titulo");
            grup_titulo.getChildren().add(eti_titulo);
            grup_titulo.getChildren().add(eti_descripcion);
            grup_titulo.getChildren().add(eti_empleado);
            grup_titulo.getChildren().add(eti_caja);

            Division div = new Division();
            div.setFooter(grup_titulo, pat_panel1, "35%");


            pat_panel1.getMenuTabla().getItem_eliminar().setRendered(false);
            pat_panel1.getMenuTabla().getItem_guardar().setRendered(false);
            tab_tabla2.setId("tab_tabla2");
            tab_tabla2.setTabla("rec_venta_devolucion", "ide_venta_dev", 2);
            tab_tabla2.setCampoForanea("ide_devolucion");
            tab_tabla2.getColumna("fecha_vd").setValorDefecto(utilitario.getFechaActual());
            tab_tabla2.getColumna("ide_detalle").setVisible(false);
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
            tab_tabla2.getColumna("rango_iv").setMetodoChange("obtener_valores");
            tab_tabla2.getColumna("rango_fv").setMetodoChange("obtener_valores");
            tab_tabla2.getColumna("cantidad").setLectura(true);
            tab_tabla2.getColumna("valor_vd").setLectura(true);
            tab_tabla2.setRecuperarLectura(true);
            tab_tabla2.setRows(3);
            tab_tabla2.dibujar();

            cargar_titulo();

            PanelTabla pat_panel2 = new PanelTabla();
            pat_panel2.setPanelTabla(tab_tabla2);

            div_division.setId("div_division");
            div_division.dividir2(div, pat_panel2, "50%", "H");

            //   gru_pantalla.getChildren().add(bar_botones);
            agregarComponente(div_division);
            con_guardar.setId("con_guardar");
            con_guardar.setHeader("CONFIRMACION GUARDAR VENTA DEVOLUCION");
            con_guardar.setMessage("Esta seguro de Guardr los Datos");
            con_guardar.getBot_aceptar().setMetodo("aceptar_venta_especie");
            con_guardar.getBot_aceptar().setUpdate("con_guardar,tab_tabla1,tab_tabla2");
            agregarComponente(con_guardar);
        } else {
            utilitario.agregarNotificacionInfo("Mensaje", "No  tiene Asignado un Empleado para poder Realizar Ventas");
        }

    }

    public void obtener_valores(AjaxBehaviorEvent evt) {
        tab_tabla2.modificar(evt);
        if (!tab_tabla2.getValor("rango_iv").isEmpty() && !tab_tabla2.getValor("rango_fv").isEmpty()) {
            try {
                int ri = Integer.parseInt(tab_tabla2.getValor("rango_iv"));
                int rf = Integer.parseInt(tab_tabla2.getValor("rango_fv"));
                if (rf < ri) {
                    utilitario.agregarMensajeInfo("Validacion Rangos", "Los Rangos Final no puede ser menor que el Rango Inicial");
                    int fila = tab_tabla2.getUltimaFilaModificada();
                    tab_tabla2.setValor(fila, "cantidad", 0 + "");
                    tab_tabla2.setValor(fila, "valor_vd", 0 + "");
                    utilitario.addUpdateTabla(tab_tabla2, "cantidad,valor_vd", "");
                } else {
                    int cant = rf - ri + 1;
                    float valor = cant * Float.parseFloat(tab_tabla1.getValor("valor"));
                    int fila = tab_tabla2.getUltimaFilaModificada();
                    tab_tabla2.setValor(fila, "cantidad", cant + "");
                    tab_tabla2.setValor(fila, "valor_vd", valor + "");
                    utilitario.addUpdateTabla(tab_tabla2, "cantidad,valor_vd", "");
                }
            } catch (Exception e) {
                utilitario.agregarMensajeInfo("Validacion Rangos", "Los Rangos son Erroneos");
                int fila = tab_tabla2.getUltimaFilaModificada();
                tab_tabla2.setValor(fila, "cantidad", 0 + "");
                tab_tabla2.setValor(fila, "valor_vd", 0 + "");
                utilitario.addUpdateTabla(tab_tabla2, "cantidad,valor_vd", "");
            }
        }
    }

    public void cargar_titulo() {
        String des = tab_tabla1.getValor("descripcion");
        eti_titulo.setValue("VENTA DE DEVOLUCIONES");
        eti_descripcion.setValue("Descripcion " + des);
        String rec = "";
        List sql1 = utilitario.getConexion().consultar("SELECT  nombres FROM munc_empleados WHERE ide_empleado =(SELECT ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql1.isEmpty()) {
            rec = (String) sql1.get(0);
        }
        String de = "";
        List sql = utilitario.getConexion().consultar("SELECT  des_caja FROM tes_caja WHERE ide_caja =(SELECT ide_caja from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql.isEmpty()) {
            de = (String) sql.get(0);
        }
        eti_caja.setValue("Numero Caja: " + de);
        eti_recaudador.setValue("Recaudador: " + rec);
//        eti_valor.setValue("TOTAL A PAGAR: ");
//        eti_cambio.setValue("CAMBIO: ");

    }

    public void filtrar_por_descripcion(SelectEvent evt) {
        aut_filtro_descripcion.onSelect(evt);
        descripcion_actual = aut_filtro_descripcion.getValor();
        tab_tabla1.setSql("select dv.ide_devolucion,dv.ide_detalle,cantidad_d,descripcion,rango_id,rango_fd,d.valor,cantidad_d*d.valor as valor_total,razon from rec_devolucion dv,rec_detalle d,rec_especies e "
                + "WHERE dv.ide_empleado=" + ide_empleado + " "
                + "and d.ide_detalle in (select ee.ide_detalle from rec_entrega_especies ee where ee.ide_entrega IN "
                + "(select ed1.ide_entrega from rec_entrega_detalle ed1 where ed1.ide_entrega_detalle=dv.ide_entrega_detalle )) "
                + "and e.ide_documento =d.ide_documento and d.ide_documento=" + descripcion_actual + " order by dv.ide_devolucion desc ");

        tab_tabla1.ejecutarSql();

        cargar_titulo();
    }

    public void aceptar_venta_especie() {
        try {
            int ri1 = Integer.parseInt(tab_tabla1.getValor("rango_id"));
            int rf1 = Integer.parseInt(tab_tabla1.getValor("rango_fd"));
            int ri = Integer.parseInt(tab_tabla2.getValor("rango_iv"));
            int rf = Integer.parseInt(tab_tabla2.getValor("rango_fv"));
            int cant = Integer.parseInt(tab_tabla2.getValor("cantidad"));
            if (cant == 0) {
                utilitario.agregarMensajeError("Error al Grabar los Datos", "La Cantidad devuelta es Cero ");
                con_guardar.cerrar();
            } else {
                if (ri == 0 || rf == 0) {
                    utilitario.agregarMensajeError("Error al Grabar los Datos", "Los Rangos no pueden ser Cero ");
                    con_guardar.cerrar();
                } else {
                    if ((ri >= ri1 && ri <= rf1) && (rf >= ri1 && rf <= rf1)) {
                        List list_sql1 = utilitario.getConexion().consultar("select rango_iv,rango_fv from rec_venta_devolucion "
                                + "where ide_devolucion=" + tab_tabla1.getValor("ide_devolucion") + " "
                                + "and ((rango_iv<=" + ri + " and rango_fv>=" + ri + ") "
                                + "or (rango_iv<=" + rf + " and rango_fv>=" + rf + ")) "
                                + "order by rango_iv");
                        String rango_i = "";
                        String rango_f = "";
                        for (int i = 0; i < list_sql1.size(); i++) {
                            Object[] fila = (Object[]) list_sql1.get(i);
                            rango_i = fila[0] + "";
                            rango_f = fila[1] + "";
                        }
                        if (rango_i == null || rango_i.isEmpty()) {

                            int fila = tab_tabla2.getFilaActual();
                            tab_tabla2.setValor(fila, "ide_detalle", tab_tabla1.getValor("ide_detalle"));
                            utilitario.addUpdateTabla(tab_tabla2, "ide_detalle", "");

                            tab_tabla2.guardar();
                            utilitario.getConexion().guardarPantalla();
                            con_guardar.cerrar();
                        } else {
                            utilitario.agregarMensajeInfo("Mensaje", "Los rangos ingresados ya estan vendidos, Rango Inicial minimo a asignar " + (Integer.parseInt(rango_f) + 1));
                            con_guardar.cerrar();
                        }
                    } else {
                        utilitario.agregarMensajeError("Error al Grabar los Datos", "Los Rangos no pertenecen al talonario ");
                        con_guardar.cerrar();
                    }
                }
            }

        } catch (Exception e) {
            utilitario.agregarMensajeError("Error al Grabar los Datos", "Los rangos son Incorrectos");
            con_guardar.cerrar();
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
    public void insertar() {
        tab_tabla2.insertar();
    }
@Override
    public void guardar() {
        con_guardar.dibujar();
    }
@Override
    public void eliminar() {
        tab_tabla2.eliminar();
    }

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla1.seleccionarFila(evt);
    }
/*
    public Grupo getGru_pantalla() {
        return gru_pantalla;
    }

    public void setGru_pantalla(Grupo gru_pantalla) {
        this.gru_pantalla = gru_pantalla;
    }
*/
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
}
