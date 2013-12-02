/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package especies;

import framework.*;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import sistema.*;

/**
 *
 * @author Byron
 */
public class pre_venta_especies1 {

    private Utilitario utilitario=new Utilitario();
    private Tabla tab_tabla1=new Tabla();
    private Tabla tab_tabla2=new Tabla();
    private Barra bar_botones=new Barra();
    private Division div_division=new Division();
    private Grupo gru_pantalla = new Grupo();
    private Etiqueta eti_titulo=new Etiqueta();
    private Confirmar con_guardar =new Confirmar();

    public pre_venta_especies1() {
    
        bar_botones.getBot_insertar().setUpdate("tab_tabla2,grup_titulo");
        bar_botones.getBot_guardar().setUpdate("con_guardar");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2,grup_titulo");
        
        eti_titulo.setValue("VENTA DE ");
        eti_titulo.setStyle("font-size: 12px;color: #000066;font-weight: bold");
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setSql("select r.ide_detalle,(case when (max(r.rango_f)-max(e.rango_f1)) > 0 then (max(r.rango_f)-max(e.rango_f1)) else rango_i-1 end) as saldos,re.descripcion,r.fecha_esp,r.rango_i,r.rango_f,r.valor,r.numero_total_esp,r.valor_total_esp from rec_detalle r,rec_entrega_detalle e, rec_especies re where r.ide_documento=r.ide_documento and r.ide_detalle=e.ide_detalle  and e.ide_detalle=e.ide_detalle and r.ide_documento=re.ide_documento GROUP BY r.ide_detalle,re.descripcion ORDER BY re.descripcion ASC");
        tab_tabla1.setCampoPrimaria("ide_detalle");
        tab_tabla1.setNumeroTabla(1);
        tab_tabla1.onSelect("seleccionar_tabla1");
        
        tab_tabla1.getColumna("descripcion").setNombreVisual("Descripcion ");

        tab_tabla1.getColumna("ide_detalle").setNombreVisual("Codigo ");
        tab_tabla1.getColumna("ide_detalle").setOrden(1);
        tab_tabla1.getColumna("saldos").setNombreVisual("SALDO ");
        tab_tabla1.getColumna("saldos").setOrden(2);
        tab_tabla1.getColumna("descripcion").setOrden(3);
        tab_tabla1.getColumna("fecha_esp").setNombreVisual("Fecha Ingreso Especie ");
        tab_tabla1.getColumna("fecha_esp").setOrden(4);
        tab_tabla1.getColumna("rango_i").setNombreVisual("Rango Inicial ");
        tab_tabla1.getColumna("rango_i").setOrden(5);
        tab_tabla1.getColumna("rango_f").setNombreVisual("Rango Final ");
        tab_tabla1.getColumna("rango_f").setOrden(6);
        tab_tabla1.getColumna("valor").setNombreVisual("Valor ");
        tab_tabla1.getColumna("valor").setOrden(7);
        tab_tabla1.getColumna("numero_total_esp").setNombreVisual("Nro. Esp ");
        tab_tabla1.getColumna("numero_total_esp").setOrden(8);
        tab_tabla1.getColumna("valor_total_esp").setNombreVisual("VT Esp ");
        tab_tabla1.getColumna("valor_total_esp").setOrden(9);
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.setLectura(true);
        tab_tabla1.setRows(10);
        tab_tabla1.dibujar();
        
        PanelTabla pat_panel1=new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);
        
//        pat_panel1.getMenuTabla().getItem_insertar().setRendered(false);
        pat_panel1.getMenuTabla().getItem_eliminar().setRendered(false);
        pat_panel1.getMenuTabla().getItem_guardar().setRendered(false);
        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("rec_entrega_detalle", "ide_entrega_detalle", 2);
        tab_tabla2.setCampoForanea("ide_detalle");
//        tab_tabla2.setCondicion("rango_f1 in (select max(rango_f1) from rec_entrega_detalle where ide_detalle="+tab_tabla1.getValor("ide_detalle") +")"); 
        tab_tabla2.setCondicion("ide_empleado= (select e.ide_empleado from munc_empleados e where ide_usua="+utilitario.getVariable("ide_usua") +") and ide_detalle="+tab_tabla1.getValor("ide_detalle") +"and cantidad > 0 ");

        tab_tabla2.getColumna("ide_caja").setCombo("tes_caja", "ide_caja", "des_caja", "ide_empleado= (select e.ide_empleado from munc_empleados e where ide_usua="+utilitario.getVariable("ide_usua")+")");
        tab_tabla2.getColumna("ide_caja").setNombreVisual("Descripcion ");
        
//        tab_tabla2.getColumna("ide_empleado").setCombo("munc_empleados", "ide_empleado", "apellido1,apellido2,nombre1,nombre2", "ide_empleado IN(select ide_empleado from tes_caja) ");
        tab_tabla2.getColumna("ide_empleado").setCombo("munc_empleados", "ide_empleado", "apellido1,apellido2,nombre1,nombre2", "ide_empleado= (select e.ide_empleado from munc_empleados e where ide_usua="+utilitario.getVariable("ide_usua")+")");
        tab_tabla2.getColumna("ide_empleado").setNombreVisual("Recaudador ");
        tab_tabla2.getColumna("rango_i1").setLectura(true);
        tab_tabla2.getColumna("rango_f1").setLectura(true);
        tab_tabla2.getColumna("ide_caja").setMetodoChange("obtener_rango");
        tab_tabla2.getColumna("ide_entrega_detalle").setNombreVisual("Codigo");
        tab_tabla2.getColumna("ide_entrega_detalle").setLongitud(10);
        tab_tabla2.getColumna("nom_cliente").setVisible(false);
        tab_tabla2.getColumna("ced_ruc").setVisible(false);
        tab_tabla2.getColumna("cantidad").setMetodoChange("controlar_stock");
        tab_tabla2.getColumna("valor1").setLectura(true);
        tab_tabla2.getColumna("fecha").setLectura(true);
        tab_tabla2.getColumna("fecha").setValorDefecto(utilitario.getFechaActual());
        tab_tabla2.setRecuperarLectura(true);
        tab_tabla2.setRows(3);
        tab_tabla2.dibujar();
    
        if (tab_tabla1.getValorSeleccionado()!=null){
            cargar_titulo();
        }

        Grid grup_titulo=new Grid();
        grup_titulo.setColumns(2);
        grup_titulo.setId("grup_titulo");
        grup_titulo.getChildren().add(eti_titulo);
        
        PanelTabla pat_panel2=new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla2);
        
        div_division.setId("div_division");
//        div_division.dividir2(pat_panel1,pat_panel2,"50%", "H");
        div_division.dividir3(pat_panel1,grup_titulo, pat_panel2,"50%" ,"45%", "H");
        
        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);
        
     
        con_guardar.setId("con_guardar");
        con_guardar.setHeader("CONFIRMACION GUARDAR ASIENTO");
        con_guardar.setMessage("Esta seguro de Guardr los Datos");
        con_guardar.getBot_aceptar().setMetodo("aceptar_venta_especie");
        con_guardar.getBot_aceptar().setUpdate("con_guardar,tab_tabla1,grup_titulo,tab_tabla2");
        gru_pantalla.getChildren().add(con_guardar);

    }
    
    public void aceptar_venta_especie(){
        if (tab_tabla2.getTotalFilas() > 0) {
                    //tab_tabla1.guardar();
                    tab_tabla2.guardar();
                    utilitario.getConexion().guardarPantalla();

                    //                    int cant=Integer.parseInt(tab_tabla2.getValor("cantidad"));
//                    utilitario.getConexion().ejecutar("update rec_detalle set saldo=saldo-"+cant+" where ide_detalle="+tab_tabla1.getValor("ide_detalle"));
//                    tab_tabla1.setCondicion("saldo>0 ");
//                    tab_tabla1.ejecutarSql();
//                    tab_tabla2.setCondicion("ide_empleado= (select e.ide_empleado from munc_empleados e where ide_usua="+utilitario.getVariable("ide_usua") +") and ide_detalle="+tab_tabla1.getValor("ide_detalle") +"");
//                    tab_tabla2.ejecutarSql();
                    String des=tab_tabla1.getValor("descripcion");
                    eti_titulo.setValue("VENTA DE LA ESPECIE "+des);
                    con_guardar.cerrar();
        } else {
            utilitario.agregarMensajeError("No se pudo guargar", "Debe ingresar detalle a la cabecera");
            con_guardar.cerrar();
        }
        
    }

    
    public void controlar_stock(AjaxBehaviorEvent evt){
        tab_tabla2.modificar(evt);
        int cant=Integer.parseInt(tab_tabla2.getValor("cantidad"));
        if (cant!=0){
            Long rf=Long.parseLong(tab_tabla2.getValor("rango_i1"));
            rf=rf+cant-1;
            Float valor=Float.parseFloat(tab_tabla1.getValor("valor"));
            valor=cant*valor;
            int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
            tab_tabla2.setValor(int_fila_modificada, "rango_f1", rf+"");
 //           utilitario.addUpdateTabla(tab_tabla2, "rango_f1", "");
            tab_tabla2.setValor(int_fila_modificada, "valor1", valor+"");
  
            int saldo=Integer.parseInt(tab_tabla1.getValor("saldos"));
            int nuevo_saldo=saldo-cant;
            System.out.println("ddd "+nuevo_saldo);

            int int_fila_modificada1 = tab_tabla1.getFilaActual();
            
            tab_tabla1.setValor(int_fila_modificada1, "saldos", nuevo_saldo+"");
            tab_tabla1.modificar(tab_tabla1.getFilaActual());
            utilitario.addUpdateTabla(tab_tabla2, "rango_f1,valor1", "tab_tabla1");

            //            utilitario.addUpdateTabla(tab_tabla1, "saldo", "tab_tabla1");
            
        }
        
    }

    public void obtener_rango(AjaxBehaviorEvent evt){
        tab_tabla2.modificar(evt);
        List list_sql1=utilitario.getConexion().consultar("select MAX(rango_f1) from rec_entrega_detalle where ide_detalle="+tab_tabla1.getValor("ide_detalle"));
        String rango_fi;
        int band=0;
        if (list_sql1.get(0)==null){
            List list_sql2=utilitario.getConexion().consultar("select MAX(rango_i) from rec_detalle where ide_detalle="+tab_tabla1.getValor("ide_detalle"));
            rango_fi=String.valueOf(list_sql2.get(0));
            band=1;
        }else{
            rango_fi=String.valueOf(list_sql1.get(0));
        }
        Long r=Long.parseLong(rango_fi);
        if (band!=1){
            r=r+1;
        }
        int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
        tab_tabla2.setValor(int_fila_modificada, "rango_i1", r+"");
        utilitario.addUpdateTabla(tab_tabla2, "rango_i1", "");
        tab_tabla2.setValor(int_fila_modificada, "rango_f1", r+"");
        utilitario.addUpdateTabla(tab_tabla2, "rango_f1", "");
    } 
            
            
    public int obtener_ide_empleado(){
        List list_sql1=utilitario.getConexion().consultar("select ide_empleado from munc_empleados where ide_usua="+utilitario.getVariable("ide_usua"));
        String ide_empl=(String) list_sql1.get(0);
        int ide_emp=Integer.parseInt(ide_empl);
        return ide_emp;
    }

    public void insertar(){
        tab_tabla2.insertar();
    }
    public void guardar(){
        con_guardar.dibujar();
    }
    
    public void cargar_titulo(){
//        List sql=utilitario.getConexion().consultar("select descripcion from rec_especies where ide_documento="+tab_tabla1.getValor("ide_documento"));
        String des=tab_tabla1.getValor("descripcion");
        eti_titulo.setValue("VENTA DE LA ESPECIE "+des);
        
    }
    public void seleccionar_tabla1(SelectEvent evt){
        tab_tabla1.seleccionarFila(evt);
//        List sql=utilitario.getConexion().consultar("select descripcion from rec_especies where ide_documento="+tab_tabla1.getValor("ide_documento"));
        String des=tab_tabla1.getValor("descripcion");
        eti_titulo.setValue("VENTA DE LA ESPECIE "+des);
        tab_tabla2.setCondicion("ide_empleado= (select e.ide_empleado from munc_empleados e where ide_usua="+utilitario.getVariable("ide_usua") +") and ide_detalle="+tab_tabla1.getValor("ide_detalle") +"and rango_i1 > 0 ");
//        tab_tabla2.setCondicion("rango_f1 in (select max(rango_f1) from rec_entrega_detalle where ide_detalle="+tab_tabla1.getValor("ide_detalle") +")"); 
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

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }

    public Confirmar getCon_guardar() {
        return con_guardar;
    }

    public void setCon_guardar(Confirmar con_guardar) {
        this.con_guardar = con_guardar;
    }
    
    
    
}
