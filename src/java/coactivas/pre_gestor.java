/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coactivas;

import framework.*;
import javax.faces.event.AjaxBehaviorEvent;
import sistema.Pantalla;

/**
 *
 * @author HP
 */
public class pre_gestor extends Pantalla
{


    private Tabla tab_tabla = new Tabla();
    //private Barra bar_botones = new Barra();
    private Division div_division = new Division();
   // private Grupo gru_pantalla = new Grupo();
    private boolean boo_documento_valido = true;

    public pre_gestor()
    {
       /* bar_botones.getBot_insertar().setUpdate("tab_tabla");
        bar_botones.getBot_guardar().setUpdate("tab_tabla");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla");*/
bar_botones.setConfirmarGuardar();
        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("rec_gestor", "ide_gestor", 1);
        tab_tabla.setCampoOrden("ide_gestor");
        tab_tabla.getColumna("ide_tipo_gestor").setCombo("rec_tipo_gestor", "ide_tipo_gestor", "detalle", "");
        tab_tabla.getColumna("cedula").setMetodoChange("validar_documento");
        tab_tabla.setTipoFormulario(true);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        div_division.setId("div_division");
        div_division.dividir1(pat_panel);

        agregarComponente(div_division);
     //   gru_pantalla.getChildren().add(bar_botones);
       // gru_pantalla.getChildren().add(div_division);
  
    }

    public void validar_documento(AjaxBehaviorEvent evt)
    {
        tab_tabla.modificar(evt);
        String cedula = tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "cedula");
        //valido la cedula
        boo_documento_valido = utilitario.validarCedula(cedula);
    }
 @Override
    public void insertar()
    {
        tab_tabla.insertar();
    }
 @Override
    public void guardar()
    {
        if (boo_documento_valido)
        {
            tab_tabla.guardar();
            utilitario.getConexion().guardarPantalla();
        }
        else
        {
            utilitario.agregarMensajeError("Validación", "La cédula ingresada no es válida, "
                    + "por favor corrija antes de guardar.");
        }
    }
 @Override
    public void eliminar()
    {
        tab_tabla.eliminar();
    }

    public Tabla getTab_tabla()
    {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla)
    {
        this.tab_tabla = tab_tabla;
    }

  /*  public Barra getBar_botones()
    {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones)
    {
        this.bar_botones = bar_botones;
    }*/
}
