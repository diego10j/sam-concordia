/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import sistema.Pantalla;

/**
 *
 * @author HP
 */
public class pre_clientes extends Pantalla {

  //  private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
  //  private Barra bar_botones = new Barra();
    private Division div_division = new Division();
//    private Grupo gru_pantalla = new Grupo();
    private boolean boo_documento_valido = true;
    private List lis_genero = new ArrayList();

    public pre_clientes()
    {
        bar_botones.getBot_insertar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.getBot_guardar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2");

        //configuracion de la tabla de clientes (cabecera)
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("rec_clientes", "ide_cliente", 1);
        tab_tabla1.setCampoOrden("ide_cliente");
        tab_tabla1.getColumna("ide_tipo_documento").setMetodoChange("validar_documento");
        tab_tabla1.getColumna("cedula").setMetodoChange("validar_documento");
        tab_tabla1.getColumna("ide_tipo_documento").setCombo("sigc_tipo_documento", "ide_tipo_documento", "des_tipo_documento", "");
        tab_tabla1.getColumna("ide_tipo_contribuyente").setCombo("rec_tipo_contribuyente", "ide_tipo_contribuyente", "des_tipo_contribuyente", "");
        tab_tabla1.getColumna("ide_distribucion").setCombo("SELECT b.ide_distribucion,b.des_distribucion,s.des_distribucion,z.des_distribucion,p.des_distribucion ,c.des_distribucion,pr.des_distribucion    FROM  inst_distribucion_politica b,inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE b.ide_tipo_distribucion=7 "
                + "and b.ins_ide_distribucion=s.ide_distribucion "
                + "and s.ins_ide_distribucion=z.ide_distribucion "
                + "and z.ins_ide_distribucion=p.ide_distribucion "
                + "and p.ins_ide_distribucion=c.ide_distribucion "
                + "and c.ins_ide_distribucion=pr.ide_distribucion");
        tab_tabla1.getColumna("ide_distribucion").setAutoCompletar();        
        tab_tabla1.getColumna("ide_estado_civil").setCombo("sigc_estado_civil", "ide_estado_civil", "des_estado_civil", "");
        tab_tabla1.getColumna("ide_tipo_asistencia").setCombo("rec_tipo_asistencia", "ide_tipo_asistencia", "des_tipo_asistencia", "");
        
        Object fila1[] = {
            "m", "Masculino"
        };
        Object fila2[] = {
            "f", "Femenino"
        };
        lis_genero.add(fila1);
        lis_genero.add(fila2);
        
        tab_tabla1.getColumna("genero").setCombo(lis_genero);
        
        tab_tabla1.setTipoFormulario(true);
        tab_tabla1.getGrid().setColumns(4);
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.dibujar();

        //configuracion de la tabla de operadoras moviles (detalle)
        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("rec_cliente_telefono", "ide_cliente_telefono", 2);
        tab_tabla2.setCampoOrden("ide_operadora");
        tab_tabla2.getColumna("ide_operadora").setCombo("rec_nombre_operadora", "ide_operadora", "des_operadora", "");
        tab_tabla2.setCampoForanea("ide_cliente");
        tab_tabla2.dibujar();

        PanelTabla pat_panel1 = new PanelTabla();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);
        pat_panel2.setPanelTabla(tab_tabla2);

        div_division.setId("div_division");
        div_division.dividir2(pat_panel1, pat_panel2, "50%", "H");

     //   gru_pantalla.getChildren().add(bar_botones);
       agregarComponente(div_division);
      
    }
@Override
    public void insertar()
    {
        utilitario.getTablaisFocus().insertar();
    }
@Override
    public void guardar()
    {
        if (boo_documento_valido)
        {
            tab_tabla1.guardar();
            tab_tabla2.guardar();
            utilitario.getConexion().guardarPantalla();
        }
        else
        {
            utilitario.agregarMensajeError("Validación", "El documento ingresado no es válido, "
                    + "por favor corrija antes de guardar.");
        }

    }
    public void validar_documento (AjaxBehaviorEvent evt)
    {
        tab_tabla1.modificar(evt);
        String cedula = tab_tabla1.getValor(tab_tabla1.getUltimaFilaModificada(),"cedula");
        String tipo_documento = tab_tabla1.getValor(tab_tabla1.getUltimaFilaModificada(),"ide_tipo_documento");
        //valido la cedula ide_tipo_documento = 1
        if (tipo_documento != null && tipo_documento.equalsIgnoreCase("1"))
        {
            boo_documento_valido = utilitario.validarCedula(cedula);
        }
        //valido el ruc ide_tipo_documento = 2
        if (tipo_documento != null && tipo_documento.equalsIgnoreCase("2"))
        {
            boo_documento_valido = utilitario.validarRUC(cedula);
        }
    }
@Override
    public void eliminar()
    {
        utilitario.getTablaisFocus().eliminar();
    }

    public void seleccionar_tabla1(SelectEvent evt)
    {
        tab_tabla1.seleccionarFila(evt);
    }

    public Tabla getTab_tabla1()
    {
        return tab_tabla1;
    }

    public void setTab_tabla1(Tabla tab_tabla1)
    {
        this.tab_tabla1 = tab_tabla1;
    }

    public Tabla getTab_tabla2()
    {
        return tab_tabla2;
    }

    public void setTab_tabla2(Tabla tab_tabla2)
    {
        this.tab_tabla2 = tab_tabla2;
    }
/*
    public Barra getBar_botones()
    {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones)
    {
        this.bar_botones = bar_botones;
    }*/
}
