/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avaluos;

import framework.*;
import sistema.*;
import javax.faces.event.AjaxBehaviorEvent;


/**
 *
 * @author user
 */
public class pre_usuarios {

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
    private Barra bar_botones = new Barra();
    private Grupo gru_pantalla = new Grupo();
    private Division div_division = new Division();
    private Encriptar encriptar = new Encriptar();
    private Boton bot_generar = new Boton();
    private Dialogo dia_clave = new Dialogo();
    private Etiqueta eti_clave = new Etiqueta();
    private Etiqueta eti_usuario = new Etiqueta();

    public pre_usuarios() {
        bar_botones.getBot_insertar().setUpdate("tab_tabla,bar_botones");
        bar_botones.getBot_guardar().setUpdate("tab_tabla");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla,bar_botones");
        bot_generar.setValue("Generar Nueva Clave");
        bot_generar.setUpdate("dia_clave");
        bot_generar.setMetodo("abrir_generar_clave");
        bar_botones.agregarBoton(bot_generar);
        eti_usuario.setValue("Cuando se crean un usuario nuevo la clave es la misma que el valor del campo <strong>NICK NAME </strong>");

        tab_tabla.setId("tab_tabla");
        tab_tabla.setTipoFormulario(true);
        tab_tabla.setTabla("sis_usuario", "ide_usua", 1);
        tab_tabla.getColumna("IDE_EMPLEADO").setCombo("select ide_empleado,cedula,nombres from munc_empleados");        
        tab_tabla.getColumna("IDE_EMPLEADO").setAutoCompletar();
        
        tab_tabla.getColumna("IDE_UBICACION").setCombo("select IDE_UBICACION, des_UBICACION from munc_ubicacion");
        tab_tabla.getColumna("IDE_UBICACION").setAutoCompletar();
        tab_tabla.getColumna("ide_caja").setCombo("SELECT ide_caja,DES_CAJA FROM tes_caja");
        tab_tabla.getColumna("IDE_PERF").setCombo("SIS_PERFIL", "IDE_PERF", "NOM_PERF", "");
        tab_tabla.getColumna("NICK_USUA").setNombreVisual("NICK NAME");        
        tab_tabla.getColumna("CLAVE_USUA").setClave();
        tab_tabla.getColumna("CLAVE_USUA").setLectura(true);
        tab_tabla.getColumna("ACTIVO_USUA").setValorDefecto("true");
        tab_tabla.getColumna("FECHA_REG_USUA").setValorDefecto(utilitario.getFechaActual());
        tab_tabla.getColumna("NICK_USUA").setMetodoChange("asignar_clave");     
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.getChildren().add(eti_usuario);
        pat_panel.setPanelTabla(tab_tabla);
        
        div_division.setId("div_division");
        div_division.dividir1(pat_panel);

        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);

    

        dia_clave.setId("dia_clave");
        dia_clave.setTitle("Generar Nueva Clave");
        dia_clave.setWidth("40%");
        dia_clave.setHeight("18%");
        dia_clave.setResizable(false);
        Grupo gru_cuerpo = new Grupo();

        Etiqueta eti_mensaje = new Etiqueta();
        eti_mensaje.setValue("El sistema generó una nueva clave para el usuario seleccionado, para asignar la clave presionar el botón aceptar");
        eti_clave.setStyle("font-size: 25px;");
        Grid gri_clave = new Grid();
        gri_clave.setWidth("100%");
        gri_clave.setStyle("text-align: center;");
        gri_clave.getChildren().add(eti_clave);

        gru_cuerpo.getChildren().add(eti_mensaje);
        gru_cuerpo.getChildren().add(gri_clave);

        dia_clave.getBot_aceptar().setMetodo("aceptar_clave");
        dia_clave.getBot_aceptar().setUpdate("dia_clave");
        dia_clave.setDialogo(gru_cuerpo);
        gru_pantalla.getChildren().add(dia_clave);
    }
  
    public void insertar() {
        tab_tabla.insertar();
    }

    public void asignar_clave(AjaxBehaviorEvent evt) {   
        tab_tabla.modificar(evt);
        
        int int_fila_modificada = tab_tabla.getUltimaFilaModificada();        
        if (tab_tabla.isFilaInsertada(int_fila_modificada)) {
            tab_tabla.setValor(int_fila_modificada, "CLAVE_USUA", encriptar.getEncriptar(tab_tabla.getValor(int_fila_modificada, "NICK_USUA")));
            utilitario.addUpdateTabla(tab_tabla, "CLAVE_USUA", "");
        }
    }

    public void guardar() {
        tab_tabla.guardar();
        utilitario.getConexion().guardarPantalla();
    }

    public void eliminar() {
        tab_tabla.eliminar();
    }

    public void abrir_generar_clave() {
        if (!tab_tabla.isFilaInsertada()) {
            eti_clave.setValue(encriptar.getGenerarClave());
            dia_clave.dibujar();
        } else {
            utilitario.agregarMensaje("No se puede generar una nueva clave a usuarios nuevos", "");
        }
    }

    public void aceptar_clave() {
        String str_sql = "UPDATE SIS_USUARIO SET CLAVE_USUA='" + encriptar.getEncriptar(eti_clave.getValue() + "") + "' WHERE IDE_USUA=" + tab_tabla.getValorSeleccionado();
        utilitario.getConexion().ejecutar(str_sql);
        utilitario.getConexion().commit();
        utilitario.agregarMensaje("Cambio clave", "La clave a sido cambiada correctamente");
        dia_clave.cerrar();
    }

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }

    public Dialogo getDia_clave() {
        return dia_clave;
    }

    public void setDia_clave(Dialogo dia_clave) {
        this.dia_clave = dia_clave;
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }
}
