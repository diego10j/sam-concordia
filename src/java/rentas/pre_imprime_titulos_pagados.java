/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import org.primefaces.event.SelectEvent;
import java.util.HashMap;
import java.util.Map;
import persistencia.Fila;
import sistema.Pantalla;

/**
 *
 * @author byron
 */
public class pre_imprime_titulos_pagados extends Pantalla {

    private Division div_division = new Division();
    private Tabla tab_sql = new Tabla();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private String cliente_actual = "-1";
    
    private Boton imprimir = new Boton();
    private Dialogo dia_cambio_estado = new Dialogo();
    private Combo com_estados = new Combo();
    private AreaTexto art_razon = new AreaTexto();
    private Boton bot_limpiar = new Boton();
    private VisualizarPDF vp = new VisualizarPDF();

    public pre_imprime_titulos_pagados() {
            bar_botones.getBot_insertar().setRendered(false);
            bar_botones.getBot_guardar().setRendered(false);
            bar_botones.getBot_eliminar().setRendered(false);
            bot_limpiar.setIcon("ui-icon-cancel");
            bot_limpiar.setTitle("Limpiar");
            bot_limpiar.setMetodo("limpiar");
            bot_limpiar.setUpdate("aut_filtro_cliente,tab_sql");
        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
        aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_sql");
        aut_filtro_cliente.setSize(100);
        bar_botones.agregarComponente(aut_filtro_cliente);
        bar_botones.agregarBoton(bot_limpiar);
        imprimir.setValue("IMPRIMIR");
        imprimir.setMetodo("listaSeleccionados");
        bar_botones.agregarBoton(imprimir);
       

        tab_sql.setId("tab_sql");
        tab_sql.setSql("select a.ide_ingreso,e.ide_estado,e.detalle,fecha_pago,des_ingreso,parroquia,calles,observaciones,(valor +(multa +interes -descuento) ) as valor_total,descuento,multa,interes "
                + "from tes_ingreso a, descuentos_multas b, rec_estados e "
                + "where a.ide_ingreso=b.ide_ingreso "
                + "and ide_titulo in (select ide_titulo from rec_valores where ide_cliente = " + cliente_actual + ") "
                + "and a.ide_estado=e.ide_estado and a.ide_estado=2 "
                + "order by des_ingreso");
        
        tab_sql.setCampoPrimaria("ide_ingreso");
        tab_sql.getColumna("ide_estado").setVisible(false);
        tab_sql.getColumna("detalle").setNombreVisual("ESTADO");
        tab_sql.getColumna("fecha_pago").setNombreVisual("FECHA PAGO");
        tab_sql.getColumna("des_ingreso").setNombreVisual("DETALLE DEL CONCEPTO DE COBRO");
        tab_sql.getColumna("parroquia").setNombreVisual("DIRECCION");
        tab_sql.getColumna("calles").setNombreVisual("UBICACION");
        tab_sql.getColumna("observaciones").setNombreVisual("CLAVE CATASTRAL");
        tab_sql.getColumna("valor_total").setNombreVisual("VAL. TOTAL");
        tab_sql.getColumna("descuento").setNombreVisual("DESCUENTO");
        tab_sql.getColumna("multa").setNombreVisual("MULTA");
        tab_sql.getColumna("interes").setNombreVisual("INTERES");
        tab_sql.getColumna("observaciones").setFiltro(true);
        tab_sql.getColumna("calles").setFiltro(true);
        tab_sql.getColumna("des_ingreso").setFiltro(true);     
        tab_sql.getColumna("parroquia").setFiltro(true);
        tab_sql.getColumna("des_ingreso").setLongitud(50);
        tab_sql.getColumna("parroquia").setLongitud(30);
        tab_sql.getColumna("calles").setLongitud(30);
        tab_sql.getColumna("observaciones").setLongitud(30);
        tab_sql.setRows(20);
        tab_sql.setTipoSeleccion(true);
        tab_sql.dibujar();
        PanelTabla pat_panel1 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_sql);
        
        div_division.setId("div_division");
        div_division.dividir1(pat_panel1);
        vp.setId("vp");        
        gru_pantalla.getChildren().add(vp);
        
        com_estados.setCombo("select ide_estado,detalle from rec_estados");
        art_razon.setStyle("width:280px");
        Grid grid_datos_cambio_estado = new Grid();
        grid_datos_cambio_estado.setColumns(2);
        grid_datos_cambio_estado.getChildren().add(new Etiqueta("ESTADO:"));
        grid_datos_cambio_estado.getChildren().add(com_estados);
        grid_datos_cambio_estado.getChildren().add(new Etiqueta("RAZON:"));
        grid_datos_cambio_estado.getChildren().add(art_razon);

        dia_cambio_estado.setId("dia_cambio_estado");
        dia_cambio_estado.setTitle("Cambio de Estado de Titulos");

        dia_cambio_estado.setWidth("30%");
        dia_cambio_estado.setHeight("30%");
        dia_cambio_estado.setDialogo(grid_datos_cambio_estado);
        grid_datos_cambio_estado.setStyle("width:" + (dia_cambio_estado.getAnchoPanel() - 5) + "px;height:" + dia_cambio_estado.getAltoPanel() + "px;overflow: auto;display: block;");
        dia_cambio_estado.getBot_aceptar().setMetodo("aceptarCambioEstado");
        dia_cambio_estado.getBot_cancelar().setMetodo("cancelarDialogo");

        gru_pantalla.getChildren().add(dia_cambio_estado);

        gru_pantalla.getChildren().add(div_division);



    }
public void listaSeleccionados(){
    String lstr_seleccionados = tab_sql.getFilasSeleccionadas();
    if (!lstr_seleccionados.isEmpty()) {
          ///////////AQUI ABRE EL REPORTE
                    Map parametros = new HashMap();
                    parametros.put("ide_ingresos", lstr_seleccionados);
                    vp.setVisualizarPDF("rep_rentas/rep_carta_predial_pagado.jasper", parametros);
                    vp.dibujar();
        
         } else {
            utilitario.agregarMensajeInfo("Atencion", "Debe seleccionar por lo menos un registro para imprimir.");
        }
    
}
    
 public void limpiar() {
        tab_sql.limpiar();
        aut_filtro_cliente.limpiar();
    }
    public boolean validarCambioEstado() {
        if (com_estados.getValue() == null) {
            utilitario.agregarMensajeError("Atencion", "Debe seleccionar el estado a cambiar");
            return false;
        }
        if (art_razon.getValue() == null) {
            utilitario.agregarMensajeError("Atencion", "Debe ingresar la razon del cambio de estado");
            return false;
        } else {
            if (art_razon.getValue().toString().isEmpty()) {
                utilitario.agregarMensajeError("Atencion", "Debe ingresar la razon del cambio de estado");
                return false;
            }
        }
        return true;
    }

    public String getNombreEmpleado(String ide_usua) {
        Tabla tab_empl = utilitario.consultar("select * from munc_empleados where "
                + "ide_empleado in (select ide_empleado from sis_usuario where ide_usua=" + ide_usua + ")");
        if (tab_empl.getTotalFilas() > 0) {
            if (tab_empl.getValor(0, "nombres") != null && !tab_empl.getValor(0, "nombres").isEmpty()) {
                return tab_empl.getValor(0, "nombres");
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void aceptarCambioEstado() {
        if (validarCambioEstado()) {
            Tabla tab_rec_archivo_estados = new Tabla();
            tab_rec_archivo_estados.setTabla("rec_archivo_estados", "ide_archivo_estados", -1);
            tab_rec_archivo_estados.ejecutarSql();
            for (Fila actual : tab_sql.getSeleccionados()) {
                System.out.println("ide_estado " + actual.getCampos()[tab_sql.getNumeroColumna("ide_estado")]);
                if (!com_estados.getValue().equals(actual.getCampos()[tab_sql.getNumeroColumna("ide_estado")])) {
                    tab_rec_archivo_estados.insertar();
                    tab_rec_archivo_estados.setValor("ide_titulo", actual.getRowKey());
                    tab_rec_archivo_estados.setValor("ide_estado", actual.getCampos()[tab_sql.getNumeroColumna("ide_estado")]+"");
                    tab_rec_archivo_estados.setValor("fecha", utilitario.getFechaActual());
                    tab_rec_archivo_estados.setValor("hora", utilitario.getHoraActual());
                    tab_rec_archivo_estados.setValor("razon", art_razon.getValue() + "");
                    String responsable = getNombreEmpleado(utilitario.getVariable("ide_usua"));
                    if (responsable != null) {
                        tab_rec_archivo_estados.setValor("responsable", responsable);
                    }
                    utilitario.getConexion().agregarSqlPantalla("update tes_ingreso set ide_estado="+com_estados.getValue()+", fecha_modificacion='"+utilitario.getFechaActual()+"',responsable_modificacion='"+utilitario.getVariable("ide_usua")+"' "
                            + "where ide_titulo="+actual.getRowKey()+"");
                    utilitario.getConexion().agregarSqlPantalla("update rec_valores set ide_estado="+com_estados.getValue()+" "
                            + "where ide_titulo="+actual.getRowKey()+"");
                    utilitario.getConexion().agregarSqlPantalla("update aux_valores set ide_estado="+com_estados.getValue()+" "
                            + "where ide_titulo="+actual.getRowKey()+"");
            
                }

            }
            tab_rec_archivo_estados.guardar();
            dia_cambio_estado.cerrar();
            utilitario.getConexion().guardarPantalla();
            tab_sql.setSql("select a.ide_ingreso,e.ide_estado,e.detalle,fecha_pago,des_ingreso,parroquia,calles,observaciones,(valor +(multa +interes -descuento) ) as valor_total,descuento,multa,interes "
                    + "from tes_ingreso a, descuentos_multas b, rec_estados e "
                    + "where a.ide_ingreso=b.ide_ingreso "
                    + "and ide_titulo in (select ide_titulo from rec_valores where ide_cliente = " + cliente_actual + ") "
                    + "and a.ide_estado=e.ide_estado and a.ide_estado=2 "
                    + "order by des_ingreso");
            tab_sql.ejecutarSql();
            utilitario.addUpdate("tab_sql");
        }
    }

    public void cambiar_estado_titulo() {
        if (tab_sql.getSeleccionados().length > 0) {
            art_razon.setValue("");
            dia_cambio_estado.dibujar();
        } else {
            utilitario.agregarMensajeInfo("Atencion", "No ha seleccionado ningun titulo para cambiar el estado");
        }

    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro_cliente.onSelect(evt);
        cliente_actual = aut_filtro_cliente.getValor();
        tab_sql.setSql("select a.ide_ingreso,e.ide_estado,e.detalle,fecha_pago,des_ingreso,parroquia,calles,observaciones,(valor +(multa +interes -descuento) ) as valor_total,descuento,multa,interes "
                + "from tes_ingreso a, descuentos_multas b, rec_estados e "
                + "where a.ide_ingreso=b.ide_ingreso "
                + "and ide_titulo in (select ide_titulo from rec_valores where ide_cliente = " + cliente_actual + ") "
                + "and a.ide_estado=e.ide_estado and a.ide_estado=2 "
                + "order by des_ingreso");
        tab_sql.ejecutarSql();

    }

    @Override
    public void insertar() {
    }

    @Override
    public void guardar() {
    }

    @Override
    public void eliminar() {
    }

    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
    }

    public Tabla getTab_sql() {
        return tab_sql;
    }

    public void setTab_sql(Tabla tab_sql) {
        this.tab_sql = tab_sql;
    }

    public Dialogo getDia_cambio_estado() {
        return dia_cambio_estado;
    }

    public void setDia_cambio_estado(Dialogo dia_cambio_estado) {
        this.dia_cambio_estado = dia_cambio_estado;
    }
     public VisualizarPDF getVp() {
        return vp;
    }

    public void setVp(VisualizarPDF vp) {
        this.vp = vp;
    }
}
