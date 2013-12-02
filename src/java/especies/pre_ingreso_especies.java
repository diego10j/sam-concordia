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
public class pre_ingreso_especies extends Pantalla {
    
    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private Confirmar con_guardar =new Confirmar();
    private String rango_i="";
    private String rango_f="";

    
    public pre_ingreso_especies() {
    
        bar_botones.getBot_insertar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.getBot_guardar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2");
        
        tab_tabla1.setId("tab_tabla1");

        tab_tabla1.setTabla("rec_especies", "ide_documento", 1);
        tab_tabla1.onSelect("seleccionar_tabla1");
        
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.getColumna("ide_cuenta").setCombo("conc_catalogo_cuentas","ide_cuenta","cue_codigo","");
        tab_tabla1.getColumna("ide_cuenta").setAutoCompletar();

        tab_tabla1.getColumna("ide_clasificador").setCombo("conc_clasificador","ide_clasificador","pre_codigo,pre_descripcion","");
        tab_tabla1.getColumna("ide_clasificador").setAutoCompletar();
        //tab_tabla1.setTipoFormulario(true);
        tab_tabla1.setRows(6);
        tab_tabla1.setRecuperarLectura(true);
        tab_tabla1.setCondicion("ide_documento=ide_documento");
        tab_tabla1.dibujar();
        PanelTabla pat_panel1=new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);
        
        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("rec_detalle", "ide_detalle", 2);
        tab_tabla2.setCampoForanea("ide_documento");
        tab_tabla2.getColumna("ide_detalle").setOrden(1);
        tab_tabla2.getColumna("rango_i").setOrden(2);
        tab_tabla2.getColumna("rango_f").setOrden(3);
        tab_tabla2.getColumna("valor").setOrden(4);
        
        tab_tabla2.getColumna("fecha_esp").setValorDefecto(utilitario.getFechaActual());
        tab_tabla2.getColumna("rango_f").setMetodoChange("validar_rango");
        tab_tabla2.getColumna("valor").setMetodoChange("calcular_valor");
        tab_tabla2.getColumna("valor").setValorDefecto("0");
        tab_tabla2.getColumna("rango_i").setValorDefecto("0");
        tab_tabla2.getColumna("rango_f").setValorDefecto("0");
        tab_tabla2.getColumna("numero_total_esp").setValorDefecto("0");
        tab_tabla2.getColumna("valor_total_esp").setValorDefecto("0");
        tab_tabla2.getColumna("numero_total_esp").setLectura(true);
        tab_tabla2.getColumna("valor_total_esp").setLectura(true);
        tab_tabla2.getColumna("ide_empleado").setCombo("munc_empleados", "ide_empleado", "nombre1,nombre2", "");
        tab_tabla2.getColumna("ide_empleado").setAutoCompletar();
        tab_tabla2.setCondicion("ide_documento="+tab_tabla1.getValor("ide_documento") +"");
        
        tab_tabla2.setRecuperarLectura(true);
        tab_tabla2.setRows(5);
        tab_tabla2.dibujar();
        
        PanelTabla pat_panel2=new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla2);
        
        div_division.setId("div_division");
        div_division.dividir2(pat_panel1, pat_panel2, "50%", "H");
        
        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);

        con_guardar.setId("con_guardar");
        con_guardar.setHeader("CONFIRMACION GUARDAR ASIENTO");
        con_guardar.setMessage("Esta seguro de Guardr los Datos");
        con_guardar.getBot_aceptar().setMetodo("aceptar_transaccion");
        con_guardar.getBot_aceptar().setUpdate("con_guardar,tab_tabla1,tab_tabla2,eti_total_credito,eti_total_debito");

        
    }

    public void validar_rango(AjaxBehaviorEvent evt){
        tab_tabla2.modificar(evt);
        rango_i="";
        rango_f="";
        if (tab_tabla2.getValor("rango_i")==null || tab_tabla2.getValor("rango_i").isEmpty()){
             utilitario.agregarMensajeInfo("Mensaje", "El Rango no puede ser Cero ");
        }else{    
            int ri=Integer.parseInt(tab_tabla2.getValor("rango_i"));
            int rf=Integer.parseInt(tab_tabla2.getValor("rango_f"));
        
            if (rf<ri){
                    rango_i="ssss";
                    utilitario.agregarMensajeInfo("Mensaje", "El Rango Final no puede ser menor que el Rango Inicial ");
            }else{
                List list_sql1 = utilitario.getConexion().consultar("select rango_i,rango_f from rec_detalle where rango_i >=" + tab_tabla2.getValor("rango_i") +"and ide_documento=" + tab_tabla1.getValor("ide_documento")+"order by rango_f DESC");
                for (int i = 0; i < list_sql1.size(); i++) {
                    Object[] fila = (Object[]) list_sql1.get(i);
                    rango_i = fila[0] + "";
                    rango_f = fila[1] + "";
                }
                System.out.println("primera "+rango_i);
                if (rango_i==null || rango_i.isEmpty()){
                        List list_sql3 = utilitario.getConexion().consultar("select rango_i,rango_f from rec_detalle where rango_f >=" + tab_tabla2.getValor("rango_i") +"and ide_documento=" + tab_tabla1.getValor("ide_documento")+"order by rango_f DESC");
                        for (int i = 0; i < list_sql3.size(); i++) {
                            Object[] fila = (Object[]) list_sql3.get(i);
                            rango_i = fila[0] + "";
                            rango_f = fila[1] + "";
                        }
                        System.out.println("segunda "+rango_i);

                }else{
                    int rango_ini=Integer.parseInt(rango_i);
                    List list_sql2 = utilitario.getConexion().consultar("select rango_i,rango_f from rec_detalle where rango_i <" + rango_ini +"and ide_documento=" + tab_tabla1.getValor("ide_documento")+"order by rango_f ASC");
                    for (int i = 0; i < list_sql2.size(); i++) {
                        Object[] fila = (Object[]) list_sql2.get(i);
                        rango_i = fila[0] + "";
                        rango_f = fila[1] + "";
                    }
                    System.out.println("segunda "+rango_i);

                }
                int tot_esp=rf-ri+1;
                int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                tab_tabla2.setValor(int_fila_modificada, "numero_total_esp", tot_esp+"");
                utilitario.addUpdateTabla(tab_tabla2, "numero_total_esp", "");
                if (rango_i==null || rango_i.isEmpty()){
                }else{
                     utilitario.agregarMensajeInfo("Mensaje", "Los rangos interfieren en rango inicial " + rango_i + " y en rango final " + rango_f);
                }        
              }
        }
    }
    public void calcular_valor(AjaxBehaviorEvent evt){
        tab_tabla2.modificar(evt);
        int num_esp=Integer.parseInt(tab_tabla2.getValor("numero_total_esp"));
        if (num_esp==0){
             utilitario.agregarMensajeInfo("Mensaje", "No Se pude calcular el Valor total no existe numero total de Especies ");
        }else{ 
            if (rango_i==null || rango_i.isEmpty() ){
                int ri=Integer.parseInt(tab_tabla2.getValor("rango_i"));
                int rf=Integer.parseInt(tab_tabla2.getValor("rango_f"));

                if (rf<ri){
                        rango_i="gggg"; 
                        utilitario.agregarMensajeInfo("Mensaje", "El Rango Final no puede ser menor que el Rango Inicial ");
                }else{
                            int tot_esp=rf-ri+1;
                            float valor=Float.parseFloat(tab_tabla2.getValor("valor"));
                            float valor_total=tot_esp*valor;
                            int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                            tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", valor_total+"");
                            utilitario.addUpdateTabla(tab_tabla2, "valor_total_esp", "");
                }
            }else{
                            int int_fila_modificada = tab_tabla2.getUltimaFilaModificada();
                            tab_tabla2.setValor(int_fila_modificada, "valor_total_esp", "0");
                            utilitario.addUpdateTabla(tab_tabla2, "valor_total_esp", "");
                
            }
         }

    }

    public void insertar(){
      //  tab_tabla2.setRecuperarLectura(true);

        if (tab_tabla1.isFocus()){
           if (tab_tabla1.isFilaInsertada()==false){
               tab_tabla1.insertar();
           }
           else{
               utilitario.agregarMensajeInfo("No se pudo insertar", "debe guardar la transaccion que se esta trabajando"); 
           }
        }else if(tab_tabla2.isFocus()) {
                tab_tabla2.insertar();
        }
    }
    
    public void guardar(){

        if (tab_tabla1.isFocus()){
             tab_tabla1.guardar();
             utilitario.getConexion().guardarPantalla();
           
        }
        if (tab_tabla2.isFocus() && tab_tabla2.isFilaInsertada()){
               int num_esp=Integer.parseInt(tab_tabla2.getValor("numero_total_esp"));
               if (rango_i==null || rango_i.isEmpty()){
                   if (num_esp==0){
                        utilitario.agregarMensajeError("Error el Grabar los Datos", "Los datos no se Grabaron, No se Calculo el Valor Total ");
                   }else{
                        tab_tabla2.guardar();
                        utilitario.getConexion().guardarPantalla();
                        List list_sql1 = utilitario.getConexion().consultar("select max(ide_entrega_detalle) from rec_entrega_detalle ");
                        String ide=String.valueOf(list_sql1.get(0));
                        Long ide_entrega=Long.parseLong(ide);
                        ide_entrega=ide_entrega+1;
                        String rango=tab_tabla2.getValor("rango_i");
                        Float rango_i=Float.parseFloat(rango);
                        rango_i=rango_i-1;
                        utilitario.getConexion().ejecutar("INSERT INTO rec_entrega_detalle VALUES ("+ide_entrega+", "+tab_tabla2.getValor("ide_detalle") +",null,"+tab_tabla2.getValor("ide_empleado")+", "+rango_i+", "+rango_i+", -1,0,null,NULL, NULL)");
                        utilitario.getConexion().guardarPantalla();
                   }
               }else{
                   utilitario.agregarMensajeError("Error el Grabar los Datos", "Los datos no se Grabaron, Los rangos interfieren en rango inicial " + rango_i + " y en rango final " + rango_f);
               }
        }else{
            tab_tabla2.guardar();
            utilitario.getConexion().guardarPantalla();
        }      
    }
    
    public void eliminar(){
        utilitario.getTablaisFocus().eliminar();
    }
    
    public void seleccionar_tabla1(SelectEvent evt){
        tab_tabla1.seleccionarFila(evt);
        tab_tabla2.setCondicion("ide_documento="+tab_tabla1.getValor("ide_documento") +"");
        tab_tabla2.ejecutarSql();
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
