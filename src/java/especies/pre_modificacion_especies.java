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
public class pre_modificacion_especies extends Pantalla {

   
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
 
    private Division div_division = new Division();
    
    private Confirmar con_guardar = new Confirmar();
    private String rango_i = "";
    private String rango_f = "";
    private AutoCompletar aut_filtro_descripcion = new AutoCompletar();
//    private String descripcion_actual = "-1";
    private Boton bot_clean = new Boton();
    private int ide_tasa_adm = 107;
    private int documento_actual;

    public pre_modificacion_especies() {

        bar_botones.getBot_insertar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.getBot_guardar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2");

        aut_filtro_descripcion.setId("aut_filtro_descripcion");
        aut_filtro_descripcion.setAutoCompletar("select ide_documento,descripcion from rec_especies  order by descripcion");
        aut_filtro_descripcion.setMetodoChange("filtrar_por_descripcion", "tab_tabla1,tab_tabla2");
        bar_botones.agregarComponente(aut_filtro_descripcion);
        bar_botones.agregarComponente(bot_clean);

        bot_clean.setIcon("ui-icon-cancel");
        bot_clean.setTitle("Limpiar");
        bot_clean.setUpdate("aut_filtro_descripcion,tab_tabla1,tab_tabla2");
        bot_clean.setMetodo("limpiar");
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("rec_especies", "ide_documento", 1);
        tab_tabla1.onSelect("seleccionar_tabla1");
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.getColumna("ide_cuenta").setCombo("conc_catalogo_cuentas", "ide_cuenta", "cue_codigo,cue_descripcion", "");
        tab_tabla1.getColumna("ide_cuenta").setAutoCompletar();
        tab_tabla1.getColumna("ide_clasificador").setCombo("conc_clasificador", "ide_clasificador", "pre_codigo,pre_descripcion", "");
        tab_tabla1.getColumna("ide_clasificador").setAutoCompletar();
        //tab_tabla1.setTipoFormulario(true);
        tab_tabla1.setRows(6);
//        tab_tabla1.setCondicion("ide_documento=-1");
        tab_tabla1.setCampoOrden("descripcion");
        tab_tabla1.setLectura(true);
        tab_tabla1.dibujar();
        PanelTabla pat_panel1 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);
        pat_panel1.getMenuTabla().getItem_insertar().setRendered(false);

        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("rec_detalle", "ide_detalle", 2);
        tab_tabla2.setCampoForanea("ide_documento");
        tab_tabla2.getColumna("fecha_esp").setValorDefecto(utilitario.getFechaActual());
        tab_tabla2.getColumna("rango_i").setMetodoChange("validar_rango");
        tab_tabla2.getColumna("rango_f").setMetodoChange("validar_rango");
        tab_tabla2.getColumna("valor").setMetodoChange("calcular_valor");
        tab_tabla2.getColumna("numero_total_esp").setLectura(true);
        tab_tabla2.getColumna("valor_total_esp").setLectura(true);
        tab_tabla2.getColumna("ide_empleado").setVisible(false);
//        tab_tabla2.getColumna("ide_empleado").setCombo("munc_empleados", "ide_empleado", "nombres", "");
//        tab_tabla2.getColumna("ide_empleado").setValorDefecto("ide_empleado");
        if (tab_tabla1.getValorSeleccionado() != null) {
            tab_tabla2.setCampoOrden("rango_i");
        }
        //    tab_tabla2.setTipoFormulario(true);
        tab_tabla2.setCampoOrden("ide_detalle desc");
        tab_tabla2.getGrid().setColumns(5);
        tab_tabla2.setRows(4);
        tab_tabla2.dibujar();

        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla2);

        pat_panel2.getMenuTabla().getItem_insertar().setRendered(false);


        div_division.setId("div_division");
        div_division.dividir2(pat_panel1, pat_panel2, "50%", "H");

        //gru_pantalla.getChildren().add(bar_botones);
      agregarComponente(div_division);

      
        con_guardar.setId("con_guardar");
        con_guardar.setHeader("CONFIRMACION GUARDAR MODIFICACION DE ESPECIE");
        con_guardar.setMessage("Esta seguro de Guardr los Datos");
        con_guardar.getBot_aceptar().setMetodo("aceptar_transaccion");
        con_guardar.getBot_aceptar().setUpdate("con_guardar,tab_tabla1,tab_tabla2,eti_total_credito,eti_total_debito");


    }

    public void limpiar() {
        aut_filtro_descripcion.setValue(null);
        tab_tabla1.limpiar();
    }

    public void filtrar_por_descripcion(SelectEvent evt) {
        aut_filtro_descripcion.onSelect(evt);
        documento_actual = Integer.parseInt(aut_filtro_descripcion.getValor());
        tab_tabla1.setCondicion("ide_documento=" + aut_filtro_descripcion.getValor());
        tab_tabla1.ejecutarSql();
    }

    public void validar_rango(AjaxBehaviorEvent evt) {
        tab_tabla2.modificar(evt);
        rango_i = "";
        rango_f = "";
        String ra = "";
        List rango = utilitario.getConexion().consultar("select max(rango_f1) from rec_entrega_detalle where ide_detalle=" + tab_tabla2.getValor("ide_detalle") + " and cantidad>0");
        if (!rango.isEmpty()) {
            ra = String.valueOf(rango.get(0));
        }
        int band = -1;
        if (ra == null || ra.isEmpty() || ra.equals("null")) {
            band = 0;
        } else {
            if (Float.parseFloat(tab_tabla2.getValor("rango_i")) <= Float.parseFloat(ra)
                    || Float.parseFloat(tab_tabla2.getValor("rango_f")) <= Float.parseFloat(ra)) {
                band = 1;
            } else {
                band = 0;
            }
        }
        if (!tab_tabla2.getValor("rango_f").isEmpty() || tab_tabla2.getValor("rango_f").equals("null")) {
            try {
                if (band == 0) {

                    if (Integer.parseInt(tab_tabla2.getValor("rango_i")) == 0
                            || Integer.parseInt(tab_tabla2.getValor("rango_f")) == 0) {
                        utilitario.agregarMensajeInfo("Mensaje", "Los Rangos no pueden ser Cero ");
                        int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                        tab_tabla2.setValor(int_fila_modificada, "rango_i", "");
                        tab_tabla2.setValor(int_fila_modificada, "rango_f", "");
                        tab_tabla2.setValor(int_fila_modificada, "valor", "");
                        tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", "");
                        tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", "");
                        utilitario.addUpdateTabla(tab_tabla2, "rango_i,rango_f,valor,valor_total_esp,numero_total_esp", "");
                    } else {
                        if (tab_tabla2.getValor("rango_i") == null || tab_tabla2.getValor("rango_i").isEmpty()
                                || tab_tabla2.getValor("rango_f") == null || tab_tabla2.getValor("rango_f").isEmpty()) {
                            utilitario.agregarMensajeInfo("Mensaje", "Los Rangos no pueden ser Nullos ");
                            int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                            tab_tabla2.setValor(int_fila_modificada, "rango_i", "");
                            tab_tabla2.setValor(int_fila_modificada, "rango_f", "");
                            tab_tabla2.setValor(int_fila_modificada, "valor", "");
                            tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", "");
                            tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", "");
                            utilitario.addUpdateTabla(tab_tabla2, "rango_i,rango_f,valor,valor_total_esp,numero_total_esp", "");
                        }
                        int ri = Integer.parseInt(tab_tabla2.getValor("rango_i"));
                        int rf = Integer.parseInt(tab_tabla2.getValor("rango_f"));

                        if (rf < ri) {
                            rango_i = "ddd";
                            utilitario.agregarMensajeInfo("Mensaje", "El Rango Final no puede ser menor que el Rango Inicial ");
                            int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                            tab_tabla2.setValor(int_fila_modificada, "rango_i", "");
                            tab_tabla2.setValor(int_fila_modificada, "rango_f", "");
                            tab_tabla2.setValor(int_fila_modificada, "valor", "");
                            tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", "");
                            tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", "");
                            utilitario.addUpdateTabla(tab_tabla2, "rango_i,rango_f,valor,valor_total_esp,numero_total_esp", "");
                        } else {
                            List list_sql1 = utilitario.getConexion().consultar("select rango_i,rango_f from rec_detalle "
                                    + "where ide_documento=" + tab_tabla1.getValor("ide_documento")
                                    + "and ((rango_i<=" + tab_tabla2.getValor("rango_i") + " and rango_f>=" + tab_tabla2.getValor("rango_i") + ") "
                                    + "or (rango_i<=" + tab_tabla2.getValor("rango_f") + " and rango_f>=" + tab_tabla2.getValor("rango_f") + "))"
                                    + "and ide_detalle !=" + tab_tabla2.getValor("ide_detalle")
                                    + "order by rango_i");
                            for (int i = 0; i < list_sql1.size(); i++) {
                                Object[] fila = (Object[]) list_sql1.get(i);
                                rango_i = fila[0] + "";
                                rango_f = fila[1] + "";
                            }
                        }

                        if (rango_i == null || rango_i.isEmpty()) {
                            int tot_esp = rf - ri + 1;
                            int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                            tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", tot_esp + "");
                            utilitario.addUpdateTabla(tab_tabla2, "numero_total_esp", "");
                        } else {
                            utilitario.agregarMensajeInfo("Mensaje", "Los rangos interfieren en rango inicial " + rango_i + " y en rango final " + rango_f);
                            int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                            tab_tabla2.setValor(int_fila_modificada, "rango_i", "");
                            tab_tabla2.setValor(int_fila_modificada, "rango_f", "");
                            tab_tabla2.setValor(int_fila_modificada, "valor", "");
                            tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", "");
                            tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", "");
                            utilitario.addUpdateTabla(tab_tabla2, "rango_i,rango_f,valor,valor_total_esp,numero_total_esp", "");
                        }
                    }
                } else {
                    utilitario.agregarMensajeInfo("No se pude Modificar ", "El valor minimo del rango inicial debe ser " + (Float.parseFloat(ra) + 1));
                    tab_tabla2.actualizar();
                    utilitario.addUpdate("tab_tabla2");
                }
            } catch (Exception e) {
                utilitario.agregarMensajeInfo("Mensaje", "Los Rangos deben ser numeros no letras ");
                int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                tab_tabla2.setValor(int_fila_modificada, "rango_i", "");
                tab_tabla2.setValor(int_fila_modificada, "rango_f", "");
                tab_tabla2.setValor(int_fila_modificada, "valor", "");
                tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", "");
                tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", "");
                utilitario.addUpdateTabla(tab_tabla2, "rango_i,rango_f,valor,valor_total_esp,numero_total_esp", "");
            }
        }

    }

    public void calcular_valor(AjaxBehaviorEvent evt) {
        tab_tabla2.modificar(evt);
        try {
            int num_esp = Integer.parseInt(tab_tabla2.getValor("numero_total_esp"));
            if (num_esp == 0) {
                utilitario.agregarMensajeInfo("Mensaje", "No Se pude calcular el Valor total no existe numero total de Especies ");
                int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                tab_tabla2.setValor(int_fila_modificada, "rango_i", "");
                tab_tabla2.setValor(int_fila_modificada, "rango_f", "");
                tab_tabla2.setValor(int_fila_modificada, "valor", "");
                tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", "");
                tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", "");
                utilitario.addUpdateTabla(tab_tabla2, "rango_i,rango_f,valor,valor_total_esp,numero_total_esp", "");
            } else {
                if (rango_i != null || !rango_i.isEmpty()) {
                    int ri = Integer.parseInt(tab_tabla2.getValor("rango_i"));
                    int rf = Integer.parseInt(tab_tabla2.getValor("rango_f"));

                    if (rf < ri) {
                        rango_i = "gggg";
                        utilitario.agregarMensajeInfo("Mensaje", "El Rango Final no puede ser menor que el Rango Inicial ");
                        int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                        tab_tabla2.setValor(int_fila_modificada, "rango_i", "");
                        tab_tabla2.setValor(int_fila_modificada, "rango_f", "");
                        tab_tabla2.setValor(int_fila_modificada, "valor", "");
                        tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", "");
                        tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", "");
                        utilitario.addUpdateTabla(tab_tabla2, "rango_i,rango_f,valor,valor_total_esp,numero_total_esp", "");
                    } else {
                        int tot_esp = rf - ri + 1;
                        float valor = Float.parseFloat(tab_tabla2.getValor("valor"));
                        float valor_total = tot_esp * valor;
                        int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                        tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", valor_total + "");
                        utilitario.addUpdateTabla(tab_tabla2, "valor_total_esp", "");
                    }
                } else {

                    int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                    tab_tabla2.setValor(int_fila_modificada, "rango_i", "");
                    tab_tabla2.setValor(int_fila_modificada, "rango_f", "");
                    tab_tabla2.setValor(int_fila_modificada, "valor", "");
                    tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", "");
                    tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", "");
                    utilitario.addUpdateTabla(tab_tabla2, "rango_i,rango_f,valor,valor_total_esp,numero_total_esp", "");

                }
            }

        } catch (Exception e) {
            utilitario.agregarMensajeInfo("Mensaje", "No Se pude calcular el Valor total no existe numero total de Especies ");
            int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
            tab_tabla2.setValor(int_fila_modificada, "rango_i", "");
            tab_tabla2.setValor(int_fila_modificada, "rango_f", "");
            tab_tabla2.setValor(int_fila_modificada, "valor", "");
            tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", "");
            tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", "");
            utilitario.addUpdateTabla(tab_tabla2, "rango_i,rango_f,valor,valor_total_esp,numero_total_esp", "");
        }
    }
@Override
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
@Override
    public void guardar() {

        if (documento_actual == ide_tasa_adm) {
            tab_tabla1.guardar();
            tab_tabla2.guardar();
            //utilitario.getConexion().getMaximo("rec_entrega_detalle", "ide_entrega_detalle");
            String rango = tab_tabla2.getValor("rango_i");
            Float rango_i = Float.parseFloat(rango);
            rango_i = rango_i - 1;
            utilitario.getConexion().guardarPantalla();
        } else {
            try {
                int num_esp = Integer.parseInt(tab_tabla2.getValor("numero_total_esp"));
                if (rango_i == null || rango_i.isEmpty() || tab_tabla2.getValor("valor_total_esp") == null || tab_tabla2.getValor("valor_total_esp").isEmpty()) {
                    if (num_esp == 0) {
                        utilitario.agregarMensajeError("Error el Grabar los Datos", "Los datos no se Grabaron, No se Calculo el Valor Total ");
                    } else {
                        List sql = utilitario.getConexion().consultar("select *from rec_entrega_detalle where ide_detalle=" + tab_tabla2.getValor("ide_detalle") + "");
                        if (sql.isEmpty() || sql == null) {
                            tab_tabla1.guardar();
                            tab_tabla2.guardar();
                            //utilitario.getConexion().getMaximo("rec_entrega_detalle", "ide_entrega_detalle");
                            String rango = tab_tabla2.getValor("rango_i");
                            Float rango_i = Float.parseFloat(rango);
                            rango_i = rango_i - 1;
                            utilitario.getConexion().guardarPantalla();

                        } else {
                            utilitario.agregarMensajeError("Error el Grabar los Datos", "Los datos no se Grabaron, La especie ya tiene realizada ventas");
                            tab_tabla1.ejecutarSql();
                            utilitario.addUpdate("tab_tabla2");
                        }
                        // utilitario.getConexion().agregarSqlPantalla("INSERT INTO rec_entrega_detalle VALUES (" + utilitario.getConexion().getMaximo("rec_entrega_detalle", "ide_entrega_detalle") + ", " + tab_tabla2.getValor("ide_detalle") + ",null," + tab_tabla2.getValor("ide_empleado") + ", " + rango_i + ", " + rango_i + ", -1,0,null,NULL, NULL)");
//                        utilitario.getConexion().ejecutar("INSERT INTO rec_entrega_detalle VALUES (" + ide_entrega + ", " + tab_tabla2.getValor("ide_detalle") + ",null," + tab_tabla2.getValor("ide_empleado") + ", " + rango_i + ", " + rango_i + ", -1,0,null,NULL, NULL)");
                    }
                } else {
                    utilitario.agregarMensajeError("Error el Grabar los Datos", "Los datos no se Grabaron, Los rangos interfieren en rango inicial " + rango_i + " y en rango final " + rango_f);
                }

            } catch (Exception e) {
                utilitario.agregarMensajeError("Error el Grabar los Datos", "Los datos no se Grabaron, Los datos no pueden ser nullos ");
            }
        }
    }
@Override
    public void eliminar() {
        List vendidos = utilitario.getConexion().consultar("select ide_detalle from rec_entrega_detalle where ide_detalle=" + tab_tabla2.getValor("ide_detalle") + " and cantidad>0 group by ide_detalle");
        if (vendidos == null || vendidos.isEmpty()) {
            utilitario.getConexion().ejecutar("delete from rec_entrega_detalle where ide_detalle=" + tab_tabla2.getValor("ide_detalle"));
//            utilitario.getConexion().guardarPantalla();
            utilitario.getTablaisFocus().eliminar();
        } else {
            utilitario.agregarMensajeError("Error al Eliminar los Datos", "No se pude eliminar, Ya tiene realizado Ventas ");
        }
    }

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla1.seleccionarFila(evt);
        tab_tabla2.setCampoOrden("rango_i");

    }

    public Division getDiv_division() {
        return div_division;
    }

    public void setDiv_division(Division div_division) {
        this.div_division = div_division;
    }
/*
    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }
    * */

  /*  public Grupo getGru_pantalla() {
        return gru_pantalla;
    }

    public void setGru_pantalla(Grupo gru_pantalla) {
        this.gru_pantalla = gru_pantalla;
    }
*/
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
}
