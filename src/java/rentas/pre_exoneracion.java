/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import java.util.ArrayList;
import java.util.List;
import sistema.*;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author user
 *
 * estado exonerado=4
 *
 */
public class pre_exoneracion {

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private MarcaAgua maa_marca = new MarcaAgua();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private Boton bot_limpiar = new Boton();
    private Radio rad_radio = new Radio();
    private Tabla tab_valores = new Tabla();
    private Boton bot_exonerar = new Boton();
    private Dialogo dia_dialogo = new Dialogo();
    private Etiqueta eti_total = new Etiqueta();
    private Calendario cal_titulo = new Calendario();
    private Calendario cal_vence = new Calendario();

    public pre_exoneracion() {

        bar_botones.quitarBotonEliminar();
        bar_botones.quitarBotonInsertar();
        bar_botones.quitarBotonGuardar();

        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("tab_valores,rad_radio,aut_filtro_cliente,eti_total");

        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
        aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_valores,eti_total");
        bar_botones.agregarComponente(aut_filtro_cliente);
        bar_botones.agregarComponente(bot_limpiar);

        Espacio esp = new Espacio();
        esp.setHeight("0");
        esp.setWidth("25");
        bar_botones.agregarComponente(esp);

        bot_exonerar.setValue("Exonerar");
        bot_exonerar.setMetodo("exonerar");
        bot_exonerar.setUpdate("dia_dialogo");
        bar_botones.agregarComponente(bot_exonerar);

        maa_marca.setFor("aut_filtro_cliente");
        maa_marca.setValue("Buscar Contribuyente");
        gru_pantalla.getChildren().add(maa_marca);

        List lis_lista = new ArrayList();
        Object obj1[] = {"ARTESANO", "Artesano"};
        Object obj2[] = {"DISCAPACITADO", "Discapacitado"};
        Object obj3[] = {"TERCERA EDAD", "Tercera Edad"};
        lis_lista.add(obj1);
        lis_lista.add(obj2);
        lis_lista.add(obj3);
        rad_radio.setId("rad_radio");
        rad_radio.setTitle("Tipo de exoneración");
        rad_radio.setRadio(lis_lista);
        rad_radio.setMetodoChange("validarExoneracion", "@this");

        tab_valores.setId("tab_valores");
        //estado activo=1
        tab_valores.setSql("select ide_titulo,clave_catastral,rec_valores.ide_concepto,des_concepto,detalle,fecha_emision,fecha_vence,valor_imponible,num_titulo from rec_valores,rec_concepto "
                + "where rec_valores.ide_concepto=rec_concepto.ide_concepto "
                + "and exoneracion=true "
                + "and dependencia=true "
                + "and ide_estado=1 and ide_cliente=-1");
        tab_valores.setCampoPrimaria("ide_titulo");
        tab_valores.setTipoSeleccion(true);
        tab_valores.dibujar();

        PanelTabla pat_panel = new PanelTabla();
        pat_panel.getChildren().add(rad_radio);
        pat_panel.setPanelTabla(tab_valores);
        pat_panel.getMenuTabla().getItem_insertar().setRendered(false);
        pat_panel.getMenuTabla().getItem_eliminar().setRendered(false);
        pat_panel.getMenuTabla().getItem_guardar().setRendered(false);

        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("rec_exoneracion", "ide_exoneracion", 1);
        tab_tabla1.setCondicion("ide_cliente =-1");
        tab_tabla1.getColumna("ide_cliente").setVisible(false);
        tab_tabla1.getColumna("ide_concepto").setCombo("rec_concepto", "ide_concepto", "des_concepto", "exoneracion=true and uso=true");
        tab_tabla1.getColumna("ide_concepto").setAutoCompletar();
        tab_tabla1.getColumna("ide_empleado").setCombo("SELECT IDE_EMPLEADO,NOMBRES FROM MUNC_EMPLEADOS WHERE IDE_EMPLEADO= (SELECT IDE_EMPLEADO FROM SIS_USUARIO WHERE IDE_USUA=" + utilitario.getVariable("ide_usua") + ")");
        tab_tabla1.getColumna("fecha_exoneracion").setValorDefecto(utilitario.getFechaActual());
        tab_tabla1.getColumna("ide_empleado").setLectura(true);
        tab_tabla1.getColumna("fecha_ingreso").setValorDefecto(utilitario.getFechaActual());
        tab_tabla1.getColumna("fecha_ingreso").setLectura(true);
        tab_tabla1.setTipoFormulario(true);

        List lis = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (lis.get(0) != null) {
            tab_tabla1.getColumna("ide_empleado").setValorDefecto(lis.get(0) + "");
        }

        tab_tabla1.dibujar();

        div_division.setId("div_division");
        //    div_division.dividir1(pat_panel);
        eti_total.setId("eti_total");
        eti_total.setValue("TOTAL: " + utilitario.getFormatoNumero("0"));
        eti_total.setStyle("padding-left: 80%;color: red;font-size: 18px;;width: 100%;font-weight: bold;");
        div_division.setFooter(pat_panel, eti_total, "90%");

        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);

        dia_dialogo.setId("dia_dialogo");
        dia_dialogo.setTitle("Exonerar");
        dia_dialogo.setWidth("50%");
        dia_dialogo.setHeight("62%");
        dia_dialogo.setResizable(false);

        Grid gri_panel = new Grid();
        gri_panel.setStyle("width:" + (dia_dialogo.getAnchoPanel() - 5) + "px;height:" + dia_dialogo.getAltoPanel() + "px;overflow: auto;display: block;");
        gri_panel.getChildren().add(tab_tabla1);

        Grid gri_fechas = new Grid();
        Etiqueta eti_titulo = new Etiqueta();
        eti_titulo.setValue("<strong>FECHA TITULO:</strong>");

        Etiqueta eti_vence = new Etiqueta();
        eti_vence.setValue("<strong>FECHA VENCIMIENTO:</strong>");
        gri_fechas.setColumns(4);
        gri_fechas.getChildren().add(eti_titulo);
        gri_fechas.getChildren().add(cal_titulo);
        gri_fechas.getChildren().add(eti_vence);
        gri_fechas.getChildren().add(cal_vence);

        gri_panel.getChildren().add(gri_fechas);

        dia_dialogo.setDialogo(gri_panel);

        dia_dialogo.getBot_aceptar().setMetodo("aceptar_exonerar");


        gru_pantalla.getChildren().add(dia_dialogo);

     
    }

    public void exonerar() {
        String str_seleccionados = tab_valores.getFilasSeleccionadas();
        if (rad_radio.getValue() != null && aut_filtro_cliente.getValor() != null && !str_seleccionados.isEmpty()) {
            tab_tabla1.limpiar();
            tab_tabla1.insertar();
            dia_dialogo.dibujar();
        } else {
            utilitario.agregarMensajeInfo("Tiene que seleccionar un contribuyente,el tipo de exoneración y títulos", "");
        }
    }

    public void aceptar_exonerar() {
        tab_tabla1.guardar();

        String str_concepto = tab_tabla1.getValor("ide_concepto");
        if (str_concepto != null && !str_concepto.isEmpty()) {

            Tabla tab_detalle = new Tabla();
            tab_detalle.setTabla("rec_exoneracion_detalle", "ide_exoneracion_detalle", 0);
            tab_detalle.setCondicion("ide_exoneracion_detalle=-1");
            tab_detalle.ejecutarSql();

            Tabla tab_rac_valores = new Tabla();
            tab_rac_valores.setTabla("rec_valores", "ide_titulo", 0);
            tab_rac_valores.setCondicion("ide_titulo=-1");
            tab_rac_valores.ejecutarSql();
            for (int i = 0; i < tab_valores.getSeleccionados().length; i++) {
                //  if (tab_valores.getFilas().get(i).isSeleccionada()) {
                //detalle
                tab_detalle.insertar();
                tab_detalle.setValor("ide_exoneracion", tab_tabla1.getValor("ide_exoneracion"));
                tab_detalle.setValor("ide_titulo", tab_valores.getSeleccionados()[i].getCampos()[tab_valores.getNumeroColumna("ide_titulo")] + "");
                tab_detalle.setValor("valor", tab_valores.getSeleccionados()[i].getCampos()[tab_valores.getNumeroColumna("valor_imponible")] + "");

                tab_rac_valores.insertar();
                tab_rac_valores.setValor("ide_concepto", tab_tabla1.getValor("ide_concepto"));
                tab_rac_valores.setValor("ide_cliente", aut_filtro_cliente.getValor());
                tab_rac_valores.setValor("ide_estado", "1");
                tab_rac_valores.setValor("ide_empleado", tab_tabla1.getValor("ide_empleado"));
                tab_rac_valores.setValor("ide_exoneracion", tab_tabla1.getValor("ide_exoneracion"));
                ///

                tab_rac_valores.setValor("detalle", tab_valores.getSeleccionados()[i].getCampos()[tab_valores.getNumeroColumna("des_concepto")] + " " + utilitario.getConexion().consultar("select des_concepto from rec_concepto where ide_concepto=" + str_concepto).get(0) + "  " + tab_tabla1.getValor("ide_concepto") + " (" + rad_radio.getValue() + ")" + " " + utilitario.getAño(tab_tabla1.getValor("fecha_exoneracion")));
                ///
                tab_rac_valores.setValor("fecha_emision", tab_tabla1.getValor("fecha_exoneracion"));
                tab_rac_valores.setValor("fecha_vence", cal_vence.getFecha());
                tab_rac_valores.setValor("fecha_titulo", cal_titulo.getFecha());
                tab_rac_valores.setValor("fecha_control", tab_tabla1.getValor("fecha_ingreso"));

                tab_rac_valores.setValor("valor_imponible", tab_valores.getSeleccionados()[i].getCampos()[tab_valores.getNumeroColumna("valor_imponible")] + "");
                tab_rac_valores.setValor("tipo", "EXO");

                tab_rac_valores.setValor("clave_catastral", tab_valores.getSeleccionados()[i].getCampos()[tab_valores.getNumeroColumna("clave_catastral")] + "");

                tab_rac_valores.setValor("num_titulo", tab_valores.getSeleccionados()[i].getCampos()[tab_valores.getNumeroColumna("num_titulo")] + "");

                String str_cambia = "UPDATE rec_valores set ide_estado=4 where ide_titulo=" + tab_valores.getSeleccionados()[i].getCampos()[tab_valores.getNumeroColumna("ide_titulo")];
                utilitario.getConexion().agregarSqlPantalla(str_cambia);

            }
            tab_detalle.guardar();
            tab_rac_valores.guardar();
            if (utilitario.getConexion().guardarPantalla().isEmpty()) {
                dia_dialogo.cerrar();
                tab_valores.ejecutarSql();
                utilitario.addUpdate("tab_valores,dia_dialogo,eti_total");
                if (tab_valores.getTotalFilas() > 0) {
                    eti_total.setValue("TOTAL: " + utilitario.getFormatoNumero(tab_valores.getSumaColumna("valor_imponible")));
                } else {
                    eti_total.setValue("TOTAL: " + utilitario.getFormatoNumero("0"));
                }
            }
        } else {
            utilitario.agregarMensajeInfo("Es necesario seleccionar un concepto", "");
        }

    }

    public void validarExoneracion() {
        //valida si selecciona tercera edad
        if (aut_filtro_cliente.getValor() != null) {
            if (rad_radio.getValue().equals("TERCERA EDAD")) {
                List lis = utilitario.getConexion().consultar("select fec_nacimiento from rec_clientes where ide_cliente=" + aut_filtro_cliente.getValor());
                if (lis.get(0) != null && !lis.get(0).toString().isEmpty()) {
                    String fecha_nac = lis.get(0) + "";
                    if ((utilitario.getAño(utilitario.getFechaActual()) - utilitario.getAño(fecha_nac)) >= 65) {
                    } else {
                        rad_radio.setValue(null);
                        utilitario.agregarMensajeError("", "El contribuyente seleccionado no tiene la edad necesaria para la exoneración");
                    }
                } else {
                    rad_radio.setValue(null);
                    utilitario.agregarMensajeError("No se puede validar", "El contribuyente seleccionado no tiene registrado la fecha de nacimiento");
                }
            }
        } else {
            rad_radio.setValue(null);
            utilitario.agregarMensajeInfo("Debe seleccionar un contribuyente", "");
        }
    }

    public void limpiar() {
        tab_valores.limpiar();
        aut_filtro_cliente.limpiar();
        rad_radio.setValue(null);
        eti_total.setValue("TOTAL: " + utilitario.getFormatoNumero("0"));
    }

    public void insertar() {

        if (rad_radio.getValue() != null && aut_filtro_cliente.getValor() != null) {
            tab_tabla1.insertar();
        } else {
            utilitario.agregarMensajeInfo("No se puede Insertar", "Debe seleccionar un contribuyente y el tipo de exoneración");
        }
    }

    public void guardar() {
        if (rad_radio.getValue() != null) {
            tab_tabla1.guardar();
            utilitario.getConexion().guardarPantalla();
        } else {
            utilitario.agregarMensajeInfo("No se puede guardar", "Debe seleccionar el tipo de exoneración");
        }
    }

    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro_cliente.onSelect(evt);

        //estado activo=1
        tab_valores.setSql("select ide_titulo,clave_catastral,rec_valores.ide_concepto,des_concepto,detalle,fecha_emision,fecha_vence,valor_imponible,num_titulo from rec_valores,rec_concepto "
                + "where rec_valores.ide_concepto=rec_concepto.ide_concepto "
                + "and exoneracion=true "
                + "and dependencia=true "
                + "and ide_estado=1 and ide_cliente=" + aut_filtro_cliente.getValor());
        tab_valores.getColumna("clave_catastral").setVisible(false);
        tab_valores.getColumna("ide_concepto").setVisible(false);
        tab_valores.setCondicionBuscar("");
        tab_valores.ejecutarSql();
        if (tab_valores.getTotalFilas() > 0) {
            eti_total.setValue("TOTAL: " + utilitario.getFormatoNumero(tab_valores.getSumaColumna("valor_imponible")));
        } else {
            eti_total.setValue("TOTAL: " + utilitario.getFormatoNumero("0"));
        }
    }

    public Tabla getTab_tabla1() {
        return tab_tabla1;
    }

    public void setTab_tabla1(Tabla tab_tabla1) {
        this.tab_tabla1 = tab_tabla1;
    }

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }

    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
    }

    public Tabla getTab_valores() {
        return tab_valores;
    }

    public void setTab_valores(Tabla tab_valores) {
        this.tab_valores = tab_valores;
    }

    public Dialogo getDia_dialogo() {
        return dia_dialogo;
    }

    public void setDia_dialogo(Dialogo dia_dialogo) {
        this.dia_dialogo = dia_dialogo;
    }
}
