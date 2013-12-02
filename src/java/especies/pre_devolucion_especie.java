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
public class pre_devolucion_especie extends Pantalla {

    //   private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    //  private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    //  private Grupo gru_pantalla = new Grupo();
    private Etiqueta eti_titulo = new Etiqueta();
    private Etiqueta eti_responsable = new Etiqueta();
    private Etiqueta eti_descripcion = new Etiqueta();
    private Confirmar con_guardar = new Confirmar();
    private AutoCompletar aut_filtro_descripcion = new AutoCompletar();
    private String descripcion_actual = "-1";
    private String ide_empleado;
    private int ide_tm1 = 1;
    private int ide_tasa_adm = 21;
//    private int ide_tm1 = 58;
//    private int ide_tasa_adm = 107;

    public pre_devolucion_especie() {
        ide_empleado = obtener_ide_empleado();
        if (!ide_empleado.equals("-1")) {
            bar_botones.getBot_insertar().setUpdate("tab_tabla1,tab_tabla2,grup_titulo");
            bar_botones.getBot_guardar().setUpdate("con_guardar");
            bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2,grup_titulo");
            
            aut_filtro_descripcion.setId("aut_filtro_descripcion");
            aut_filtro_descripcion.setAutoCompletar("select ide_documento,descripcion from rec_especies where ide_documento!=" + ide_tm1 + " and ide_documento !=" + ide_tasa_adm);
            aut_filtro_descripcion.setMetodoChange("filtrar_por_descripcion", "tab_tabla1,tab_tabla2,grup_titulo");
            bar_botones.agregarComponente(aut_filtro_descripcion);
            
            eti_titulo.setStyle("font-size: 14px;font-weight: bold");
            eti_responsable.setStyle("font-size: 12px;font-weight: bold");
            eti_descripcion.setStyle("font-size: 12px;font-weight: bold");
            
            tab_tabla1.setId("tab_tabla1");
            tab_tabla1.setSql("select ide_entrega_detalle,descripcion,rango_i1,rango_f1,cantidad,valor1,rec_entrega_detalle.fecha,ide_detalle "
                    + "from rec_entrega_detalle,rec_especies where ide_entrega in ( "
                    + "select ide_entrega from rec_entrega_especies where ide_detalle in ( "
                    + "select ide_detalle from rec_detalle where ide_documento=" + descripcion_actual + ") "
                    + "and mun_ide_empleado=" + ide_empleado + " ) "
                    + "and ide_documento=" + descripcion_actual + " order by ide_entrega_detalle DESC");
            tab_tabla1.setCampoPrimaria("ide_entrega_detalle");
            tab_tabla1.getColumna("ide_detalle").setVisible(false);
            tab_tabla1.setNumeroTabla(1);
            
            tab_tabla1.agregarRelacion(tab_tabla2);
            tab_tabla1.setLectura(true);
            tab_tabla1.setRows(4);
            tab_tabla1.dibujar();
            
            PanelTabla pat_panel1 = new PanelTabla();
            pat_panel1.setPanelTabla(tab_tabla1);
            pat_panel1.getMenuTabla().getItem_eliminar().setRendered(false);
            
            
            Grid grup_titulo = new Grid();
            grup_titulo.setColumns(1);
            grup_titulo.setId("grup_titulo");
            grup_titulo.getChildren().add(eti_titulo);
            grup_titulo.getChildren().add(eti_responsable);
            grup_titulo.getChildren().add(eti_descripcion);
            
            tab_tabla2.setId("tab_tabla2");
            tab_tabla2.setTabla("rec_devolucion", "ide_devolucion", 2);
            tab_tabla2.setCampoForanea("ide_entrega_detalle");
            String rec = "";
            List sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
            if (!sql1.isEmpty()) {
                rec = String.valueOf(sql1.get(0));
            }
            tab_tabla2.getColumna("ide_empleado").setValorDefecto(rec);
            tab_tabla2.getColumna("ide_empleado").setVisible(false);
            tab_tabla2.getColumna("rango_id").setMetodoChange("obtener_cantidad");
            tab_tabla2.getColumna("rango_fd").setMetodoChange("obtener_cantidad");
            tab_tabla2.getColumna("cantidad_d").setLectura(true);
            tab_tabla2.getColumna("fecha_d").setValorDefecto(utilitario.getFechaActual());
            tab_tabla2.getColumna("ide_detalle").setVisible(false);
            tab_tabla2.setRecuperarLectura(true);
            tab_tabla2.setRows(2);
            tab_tabla2.dibujar();
            
            cargar_titulo();
            PanelTabla pat_panel2 = new PanelTabla();
            pat_panel2.setPanelTabla(tab_tabla2);
            pat_panel2.getMenuTabla().getItem_eliminar().setRendered(false);
            
            Division div = new Division();
            div.setFooter(grup_titulo, pat_panel1, "35%");
            div_division.setId("div_division");
            div_division.dividir2(div, pat_panel2, "50%", "H");

            //  gru_pantalla.getChildren().add(bar_botones);
            //  gru_pantalla.getChildren().add(div_division);
            agregarComponente(div_division);
            con_guardar.setId("con_guardar");
            con_guardar.setHeader("CONFIRMACION GUARDAR DEVOLUCION DE ESPECIES");
            con_guardar.setMessage("Esta seguro de Guardr los Datos");
            con_guardar.getBot_aceptar().setMetodo("aceptar_venta_especie");
            con_guardar.getBot_aceptar().setUpdate("con_guardar,tab_tabla1,grup_titulo,tab_tabla2");
          //  gru_pantalla.getChildren().add(con_guardar);
            
            agregarComponente(con_guardar);
        } else {
            utilitario.agregarNotificacionInfo("Mensaje", "No  tiene Asignado un Empleado para poder Realizar Ventas");
        }
        
        
    }
    
    public void obtener_cantidad(AjaxBehaviorEvent evt) {
        tab_tabla2.modificar(evt);
        if (!tab_tabla2.getValor("rango_fd").isEmpty() && !tab_tabla2.getValor("rango_id").isEmpty()) {
            try {
                int ri = Integer.parseInt(tab_tabla2.getValor("rango_id"));
                int rf = Integer.parseInt(tab_tabla2.getValor("rango_fd"));
                if (rf < ri) {
                    utilitario.agregarMensajeInfo("Advertencia", "el rango final no puede ser menor que el rango inicial");
                    int fila_modificada = tab_tabla2.getUltimaFilaModificada();
                    tab_tabla2.setValor(fila_modificada, "cantidad_d", 0 + "");
                    utilitario.addUpdateTabla(tab_tabla2, "cantidad_d", "");
                } else {
                    int cant = rf - ri + 1;
                    int fila_modificada = tab_tabla2.getUltimaFilaModificada();
                    tab_tabla2.setValor(fila_modificada, "cantidad_d", cant + "");
                    utilitario.addUpdateTabla(tab_tabla2, "cantidad_d", "");
                }
                
            } catch (Exception e) {
                utilitario.agregarMensajeInfo("Advertencia", "Los rangos son Incorrectos");
                int fila_modificada = tab_tabla2.getUltimaFilaModificada();
                tab_tabla2.setValor(fila_modificada, "cantidad_d", 0 + "");
                utilitario.addUpdateTabla(tab_tabla2, "cantidad_d", "");
            }
        }
    }
    
    public void aceptar_venta_especie() {
        try {
            int ri1 = Integer.parseInt(tab_tabla1.getValor("rango_i1"));
            int rf1 = Integer.parseInt(tab_tabla1.getValor("rango_f1"));
            int ri = Integer.parseInt(tab_tabla2.getValor("rango_id"));
            int rf = Integer.parseInt(tab_tabla2.getValor("rango_fd"));
            int cant = Integer.parseInt(tab_tabla2.getValor("cantidad_d"));
            if (cant == 0) {
                utilitario.agregarMensajeError("Error al Grabar los Datos", "La Cantidad devuelta es Cero ");
                con_guardar.cerrar();
            } else {
                if (ri == 0 || rf == 0) {
                    utilitario.agregarMensajeError("Error al Grabar los Datos", "Los Rangos no pueden ser Cero ");
                    con_guardar.cerrar();
                } else {
                    if ((ri >= ri1 && ri <= rf1) && (rf >= ri1 && rf <= rf1)) {
                        List list_sql1 = utilitario.getConexion().consultar("select rango_id,rango_fd from rec_devolucion "
                                + "where ide_entrega_detalle=" + tab_tabla1.getValor("ide_entrega_detalle") + " "
                                + "and ((rango_id>=" + ri + " and rango_fd<=" + rf + ") "
                                + "or (rango_id>=" + ri + " and rango_fd<=" + rf + "))"
                                + "order by rango_id");
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
                            utilitario.agregarMensajeInfo("Mensaje", "Los rangos ya estan devueltos, Rango Inicial minimo a asignar " + (Integer.parseInt(rango_f) + 1));
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

//        if (Long.parseLong(tab_tabla3.getValor("rango_fd"))!=0){
//            int cant=validar_cantidad();
//            int cant2=Integer.parseInt(tab_tabla2.getValor("cantidad"));
//            if (cant!=0){
//                if (cant>cant2){
//                    utilitario.agregarMensajeInfo("Guardar Devolucion", "No se guardaron los datos error en la cantidad");
//                    con_guardar.cerrar();
//                }else{
//                    tab_tabla3.guardar();
//                    utilitario.getConexion().guardarPantalla();
//                    List list_sql1 = utilitario.getConexion().consultar("select max(ide_venta_dev) from rec_venta_dev ");
//                    String ide=String.valueOf(list_sql1.get(0));
//                    Long ide_venta=Long.parseLong(ide);
//                    ide_venta=ide_venta+1;
//                    int ide_emp=ide_empleado;
//                    System.out.println("sss "+ide_emp);
//                    int devol=Integer.parseInt(tab_tabla3.getValor("ide_devolucion"));
//                    utilitario.getConexion().ejecutar("INSERT INTO rec_venta_dev VALUES ("+ide_venta+", "+devol +","+ide_emp+",null,0,0,0,0)");
//                    utilitario.getConexion().guardarPantalla();
//                    cargar_titulo();
//                    con_guardar.cerrar();
//                }
//            }
//        }else{
//            utilitario.agregarMensajeInfo("Guardar Devolucion", "No se guardaron los datos error en la cantidad");
//            con_guardar.cerrar();
//        }
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
    
    public void cargar_titulo() {
        String des = tab_tabla1.getValor("descripcion");
        eti_titulo.setValue("DEVOLUCION ESPECIES VENDIDAS ");
        eti_descripcion.setValue("Descripcion " + des);
        String rec = "";
        List sql1 = utilitario.getConexion().consultar("SELECT  nombres FROM munc_empleados WHERE ide_empleado =(SELECT ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql1.isEmpty()) {
            rec = (String) sql1.get(0);
        }
        eti_responsable.setValue("Empleado: " + rec);
        
    }
    
    public void filtrar_por_descripcion(SelectEvent evt) {
        aut_filtro_descripcion.onSelect(evt);
        descripcion_actual = aut_filtro_descripcion.getValor();
        tab_tabla1.setSql("select ide_entrega_detalle,descripcion,rango_i1,rango_f1,cantidad,valor1,rec_entrega_detalle.fecha,ide_detalle "
                + "from rec_entrega_detalle,rec_especies where ide_entrega in ( "
                + "select ide_entrega from rec_entrega_especies where ide_detalle in ( "
                + "select ide_detalle from rec_detalle where ide_documento=" + descripcion_actual + ") "
                + "and mun_ide_empleado=" + ide_empleado + " ) "
                + "and ide_documento=" + descripcion_actual + " order by ide_entrega_detalle DESC");
        
        tab_tabla1.ejecutarSql();
        
        cargar_titulo();
    }
    
    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla1.seleccionarFila(evt);
        cargar_titulo();
    }
    
    public void seleccionar_tabla2(SelectEvent evt) {
        tab_tabla2.seleccionarFila(evt);
        
    }
  /*  
    public Grupo getGru_pantalla() {
        return gru_pantalla;
    }
    
    public void setGru_pantalla(Grupo gru_pantalla) {
        this.gru_pantalla = gru_pantalla;
    }*/
    
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
    }*/
    
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
