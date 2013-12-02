/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import framework.Boton;
import framework.Dialogo;
import framework.Division;
import framework.Encriptar;
import framework.Etiqueta;
import framework.Grid;
import framework.Grupo;
import framework.PanelTabla;
import framework.Tabla;
import javax.faces.event.AjaxBehaviorEvent;


/**
 *
 * @author Diego
 */
public class pre_usuarios extends Pantalla {

    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    private Division div_division = new Division();
    private Encriptar encriptar = new Encriptar();
    private Boton bot_generar = new Boton();
    private Dialogo dia_clave = new Dialogo();
    private Etiqueta eti_clave = new Etiqueta();
    private Etiqueta eti_usuario = new Etiqueta();

    public pre_usuarios() {
        bot_generar.setValue("Generar Nueva Clave");
        bot_generar.setMetodo("abrir_generar_clave");
        bar_botones.agregarBoton(bot_generar);
        eti_usuario.setValue("Cuando se crean un usuario nuevo la clave es la misma que el valor del campo <strong>NICK NAME </strong>");
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTipoFormulario(true);
        tab_tabla1.setTabla("sis_usuario", "ide_usua", 1);
        tab_tabla1.getColumna("IDE_PERF").setCombo("SIS_PERFIL", "IDE_PERF", "NOM_PERF", "");
        tab_tabla1.getColumna("NICK_USUA").setNombreVisual("NICK NAME");
        tab_tabla1.getColumna("CLAVE_USUA").setClave();
        tab_tabla1.getColumna("CLAVE_USUA").setLectura(true);
        tab_tabla1.getColumna("ACTIVO_USUA").setValorDefecto("true");
        tab_tabla1.getColumna("FECHA_REG_USUA").setValorDefecto(utilitario.getFechaActual());
        tab_tabla1.getColumna("NICK_USUA").setMetodoChange("asignar_clave");
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.dibujar();
        PanelTabla pat_panel1 = new PanelTabla();
        pat_panel1.getChildren().add(eti_usuario);
        pat_panel1.setPanelTabla(tab_tabla1);
System.out.println("3333");

        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("sis_usuario_sucursal", "ide_ussu", 2);
        tab_tabla2.getColumna("sis_ide_sucu").setCombo("sis_sucursal", "ide_sucu", "nom_sucu", "ide_empr=" + utilitario.getVariable("IDE_EMPR"));
        tab_tabla2.dibujar();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla2);
System.out.println("4444");
        div_division.setId("div_division");
        div_division.dividir2(pat_panel1, pat_panel2, "60%", "H");
        agregarComponente(div_division);

        dia_clave.setId("dia_clave");
        dia_clave.setTitle("Generar Nueva Clave");
        dia_clave.setWidth("40%");
        dia_clave.setHeight("18%");
        dia_clave.setResizable(false);
        Grupo gru_cuerpo = new Grupo();
        Etiqueta eti_mensaje = new Etiqueta();
        eti_mensaje.setValue("El sistema generó una nueva clave para el usuario seleccionado, para asignar la clave presionar el botón aceptar");
        eti_mensaje.setStyle("font-size: 13px;border: none;text-shadow: 0px 2px 3px #ccc;background: none;");
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
        agregarComponente(dia_clave);
    }

    @Override
    public void insertar() {
        utilitario.getTablaisFocus().insertar();
    }

    public void asignar_clave(AjaxBehaviorEvent evt) {
        tab_tabla1.modificar(evt);
        int int_fila_modificada = tab_tabla1.getUltimaFilaModificada();
        if (tab_tabla1.isFilaInsertada(int_fila_modificada)) {
            tab_tabla1.setValor(int_fila_modificada, "CLAVE_USUA", encriptar.getEncriptar(tab_tabla1.getValor(int_fila_modificada, "NICK_USUA")));
            utilitario.addUpdateTabla(tab_tabla1, "CLAVE_USUA", "");
        }
    }

    @Override
    public void guardar() {
        tab_tabla1.guardar();
        tab_tabla2.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
    }

    public void abrir_generar_clave() {
        if (!tab_tabla1.isFilaInsertada()) {
            eti_clave.setValue(encriptar.getGenerarClave());
            dia_clave.dibujar();
        } else {
            utilitario.agregarMensaje("No se puede generar una nueva clave a usuarios nuevos", "");
        }
    }

    public void aceptar_clave() {
        String str_sql = "UPDATE SIS_USUARIO SET CLAVE_USUA='" + encriptar.getEncriptar(eti_clave.getValue() + "") + "' WHERE IDE_USUA=" + tab_tabla1.getValorSeleccionado();
        utilitario.getConexion().ejecutar(str_sql);
        utilitario.getConexion().commit();
        utilitario.agregarMensaje("Cambio clave", "La clave a sido cambiada correctamente");
        dia_clave.cerrar();
    }

    public Dialogo getDia_clave() {
        return dia_clave;
    }

    public void setDia_clave(Dialogo dia_clave) {
        this.dia_clave = dia_clave;
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
