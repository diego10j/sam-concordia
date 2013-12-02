/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import javax.faces.event.AjaxBehaviorEvent;
import sistema.Pantalla;

/**
 *
 * @author HP
 */
public class pre_impuesto extends Pantalla{
    
  //  private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
 //   private Barra bar_botones = new Barra();
    private Division div_division = new Division();
 //   private Grupo gru_pantalla = new Grupo();

    public pre_impuesto() {
        bar_botones.getBot_insertar().setUpdate("tab_tabla");
        bar_botones.getBot_guardar().setUpdate("tab_tabla");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla");

        //configuracion de la tabla impuesto
        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("rec_impuesto", "ide_impuesto", 1);
        tab_tabla.setCampoOrden("ide_impuesto");
        tab_tabla.getColumna("ide_cuenta").setCombo("conc_catalogo_cuentas", "ide_cuenta", "ide_cuenta,cue_codigo,cue_descripcion", "");
        tab_tabla.getColumna("ide_cuenta").setAutoCompletar();
        tab_tabla.getColumna("ide_clasificador").setCombo("conc_clasificador", "ide_clasificador", "ide_clasificador,pre_codigo,pre_descripcion", "");
        tab_tabla.getColumna("ide_clasificador").setAutoCompletar();
        tab_tabla.setRows(20);
        tab_tabla.getColumna("porcentaje").setMetodoKeyPress("cambiaPorcentaje");
        tab_tabla.getColumna("valor").setMetodoKeyPress("cambiaValor");
        tab_tabla.getColumna("tipo_con").setVisible(false);
        tab_tabla.dibujar();
        
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        div_division.setId("div_division");
        div_division.dividir1(pat_panel);

       // gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);

       
    }
    
    public void cambiaValor(AjaxBehaviorEvent evt){
        tab_tabla.modificar(evt);
        tab_tabla.setValor(tab_tabla.getUltimaFilaModificada(), "porcentaje","");
        utilitario.addUpdateTabla(tab_tabla, "porcentaje", "");
    }

     public void cambiaPorcentaje(AjaxBehaviorEvent evt){
        tab_tabla.modificar(evt);
        tab_tabla.setValor(tab_tabla.getUltimaFilaModificada(), "valor","");
        utilitario.addUpdateTabla(tab_tabla, "valor", "");
    }
@Override
    public void insertar() {
        tab_tabla.insertar();
    }
@Override
    public void guardar() {
        tab_tabla.guardar();
        utilitario.getConexion().guardarPantalla();
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

  /*  public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }*/
}
