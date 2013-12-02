/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.event.SelectEvent;
import sistema.Pantalla;

/**
 *
 * @author HP
 */
public class pre_consulta_deudas extends Pantalla {

    //private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    // private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    //  private Grupo gru_pantalla = new Grupo();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private double idou_total = 0;
    private Etiqueta total_valores = new Etiqueta();
    private MarcaAgua maa_marca = new MarcaAgua();
    private Boton bot_limpiar = new Boton();
    private AutoCompletar aut_filtro_razon = new AutoCompletar();
    private Boton bot_anular = new Boton();
    private Boton bot_numero = new Boton();
    private Tabla tab_busca_titulo = new Tabla();
    private Tabla tab_busca_titulo2 = new Tabla();
    private Dialogo dia_dialogo = new Dialogo();
    private Dialogo dia_dialogo2 = new Dialogo();
    public String razon = "1";
    private String dato = "", empleado = "";
    public Texto tex = new Texto();
    public Texto tex2 = new Texto();
    private boolean boo_documento_valido = true;
    private String ide_empleado;
    private Confirmar con_guardar = new Confirmar();
    private VisualizarPDF vp = new VisualizarPDF();
    private Reporte rep_reporte = new Reporte();
    private Boton bot_busqueda = new Boton();

    public pre_consulta_deudas() {
        bar_botones.quitarBotonInsertar();
        bar_botones.quitarBotonGuardar();
        bar_botones.quitarBotonEliminar();
        bar_botones.quitarBotonsNavegacion();

        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
        aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_tabla1,tab_tabla2");
        bar_botones.agregarComponente(aut_filtro_cliente);

        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("tab_tabla1,tab_tabla2,aut_filtro_cliente");
        bar_botones.agregarBoton(bot_limpiar);

        maa_marca.setFor("aut_filtro_cliente");
        maa_marca.setValue("Buscar Contribuyente");
        agregarComponente(maa_marca);

        bot_anular.setValue("Anular");
        bot_anular.setMetodo("anular");
        bot_anular.setUpdate("dia_dialogo");
        bar_botones.agregarBoton(bot_anular);

        bot_numero.setValue("Cambiar Nro de Titulo");
        bot_numero.setMetodo("numerotitu");
        bot_numero.setUpdate("dia_dialogo2");
        bar_botones.agregarBoton(bot_numero);


        // configurar ventana de Dialogo para Anulacion 


        Grid gri_busca = new Grid();
        Grupo gru_grupo = new Grupo();
        Etiqueta label = new Etiqueta();
        label.setId("label");
        label.setValue("Razon de Anulacion");
        label.setStyle("color: black; font-size: 10px; align: right;");
        tex.setStyle("width:300px");
        gru_grupo.getChildren().add(label);
        gru_grupo.getChildren().add(tex);
        gri_busca.setHeader(gru_grupo);
        tab_busca_titulo.setId("tab_busca_titulo");
        tab_busca_titulo.setSql("select ide_titulo,ide_estado,des_ingreso,valor,num_titulo,fecha_pago,ide_ingreso from tes_ingreso where ide_titulo=-1");
        tab_busca_titulo.setRows(10);
        tab_busca_titulo.setLectura(true);
        tab_busca_titulo.setCampoPrimaria("ide_titulo");
        tab_busca_titulo.dibujar();
        tab_busca_titulo.setStyle("width:" + (dia_dialogo.getAnchoPanel() - 15) + "px");
        gri_busca.getChildren().add(tab_busca_titulo);
        gri_busca.setStyle("width:" + (dia_dialogo.getAnchoPanel() - 5) + "px;height:" + dia_dialogo.getAltoPanel() + "px;overflow: auto;display: block;");
        dia_dialogo.setId("dia_dialogo");
        dia_dialogo.setTitle("Anular Titulo");
        dia_dialogo.setResizable(false);
        dia_dialogo.setDialogo(gri_busca);
        dia_dialogo.getBot_aceptar().setMetodo("aceptaanular");
        agregarComponente(dia_dialogo);
        // utilitario.crearPantalla(gru_pantalla);

        // Termina Configuracion de Ventana de dialogo Para Anulacion

        // configurar ventana de Dialogo para cambio de numeros de titulos

        Grid gri_busca2 = new Grid();
        Grupo gru_grupo2 = new Grupo();
        Etiqueta label2 = new Etiqueta();

        label2.setId("label2");
        label2.setValue("Numero de Dcto Fisico");
        label2.setStyle("color: black; font-size: 10px; align: right;");
        tex2.setStyle("width:125px");
        gru_grupo2.getChildren().add(label2);
        gru_grupo2.getChildren().add(tex2);



        gri_busca2.setHeader(gru_grupo2);
        tab_busca_titulo2.setId("tab_busca_titulo2");
        tab_busca_titulo2.setSql("select ide_titulo,ide_estado,des_ingreso,valor,num_titulo,fecha_pago,ide_ingreso from tes_ingreso where ide_titulo=-1");
        tab_busca_titulo2.setLectura(true);
        tab_busca_titulo2.setCampoPrimaria("ide_titulo");
        tab_busca_titulo2.dibujar();
        tab_busca_titulo2.setStyle("width:" + (dia_dialogo2.getAnchoPanel() - 15) + "px");
        gri_busca2.getChildren().add(tab_busca_titulo2);
        gri_busca2.setStyle("width:" + (dia_dialogo2.getAnchoPanel() - 5) + "px;height:" + dia_dialogo2.getAltoPanel() + "px;overflow: auto;display: block;");
        dia_dialogo2.setId("dia_dialogo2");
        dia_dialogo2.setTitle("Cambiar nro de Titulo");
        dia_dialogo2.setResizable(false);
        dia_dialogo2.setDialogo(gri_busca2);
        dia_dialogo2.getBot_aceptar().setMetodo("aceptanro");
        agregarComponente(dia_dialogo2);
        // utilitario.crearPantalla(gru_pantalla);

        // Termina Configuracion de Ventana de dialogo Para cambios de numeros de titulos


        //configuracion de la tabla de valores o titulos (cabecera)
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setSql("select ide_titulo,detalle,des_ingreso,valor,num_titulo,fecha_pago,ide_ingreso from tes_ingreso  ,rec_estados  "
                + " where tes_ingreso.ide_estado=rec_estados.ide_estado and ide_titulo in(select ide_titulo from rec_valores where ide_cliente=-1)");
        tab_tabla1.setNumeroTabla(1);
        tab_tabla1.setCampoPrimaria("ide_titulo");
        tab_tabla1.getColumna("des_ingreso").setFiltro(true);
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.onSelect("seleccionar_tabla1");
        tab_tabla1.setLectura(true);
        tab_tabla1.dibujar();

        //configuracion de la tabla detalle del titulo (detalle)
        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("rec_valor_detalle", "ide_valdet", 2);
        tab_tabla2.getColumna("ide_impuesto").setVisible(false);
        //tab_tabla2.getColumna("detalle").setFiltro(true);
        tab_tabla2.setLectura(true);
        tab_tabla2.dibujar();

        /**
         * ************************************************************
         */
        vp.setId("vp");
        vp.setTitle("certificado");
        agregarComponente(vp);

        bot_busqueda.setValue("Re - Impresion");
        bot_busqueda.setMetodo("impresion");
        bar_botones.agregarBoton(bot_busqueda);


        /**
         * ******************************************************
         */
        PanelTabla pat_panel1 = new PanelTabla();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);
        pat_panel2.setPanelTabla(tab_tabla2);

        total_valores.setId("total_valores");
        total_valores.setValue(idou_total);
        total_valores.setStyle("padding-left: 80%;color: red;font-size: 18px;;width: 100%;font-weight: bold;");

        Etiqueta total_valores_titulo = new Etiqueta();
        total_valores_titulo.setId("total_valores_titulo");
        total_valores_titulo.setValue("TOTAL VALORES: ");
        total_valores_titulo.setStyle("color: red; font-size: 15px; align: right;");

        Grid lgri_resumen = new Grid();
        lgri_resumen.setId("lgri_resumen");
        lgri_resumen.setColumns(2);
        lgri_resumen.getChildren().add(total_valores_titulo);
        lgri_resumen.getChildren().add(total_valores);

        Division ldiv_pie = new Division();
        ldiv_pie.setFooter(pat_panel2, lgri_resumen, "80%");

        div_division.setId("div_division");
        div_division.dividir2(pat_panel1, ldiv_pie, "60%", "H");

        //gru_pantalla.getChildren().add(bar_botones);
        agregarComponente(div_division);

    }

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla1.seleccionarFila(evt);
        calcula_totales();
    }

    public void inicio() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.inicio();
        }
        calcula_totales();
    }

    public void fin() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.fin();
        }
        calcula_totales();
    }

    public void siguiente() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.siguiente();
        }
        calcula_totales();
    }

    public void atras() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.atras();
        }
        calcula_totales();
    }

    public void limpiar() {
        tab_tabla1.limpiar();
        aut_filtro_cliente.limpiar();
        calcula_totales();
    }

    public void calcula_totales() {
        double ldou_subtotal = tab_tabla2.getSumaColumna("valor");
        idou_total = ldou_subtotal;
        total_valores.setValue(utilitario.getFormatoNumero(idou_total));
        utilitario.addUpdate("lgri_resumen");
    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro_cliente.onSelect(evt);
        tab_tabla1.setSql("select ide_titulo,detalle,des_ingreso,valor,num_titulo,fecha_pago,ide_ingreso from tes_ingreso,rec_estados  "
                + " where tes_ingreso.ide_estado=rec_estados.ide_estado and ide_titulo in(select ide_titulo from rec_valores where ide_cliente=" + aut_filtro_cliente.getValor() + ")");
        tab_tabla1.ejecutarSql();
        tab_tabla2.ejecutarValorForanea(tab_tabla1.getValorSeleccionado());

        calcula_totales();
    }

    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
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
/*
    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }

    /**
     * *****************************************************************
     */
    public Dialogo getDia_dialogo() {
        return dia_dialogo;
    }

    public void setDia_dialogo(Dialogo dia_dialogo) {
        this.dia_dialogo = dia_dialogo;
    }

    public Tabla getTab_busca_titulo() {
        return tab_busca_titulo;
    }

    public void setTab_busca_titulo(Tabla tab_busca_titulo) {
        this.tab_busca_titulo = tab_busca_titulo;
    }

    public Boton getBot_limpiar() {
        return bot_limpiar;
    }

    public void setBot_limpiar(Boton bot_limpiar) {
        this.bot_limpiar = bot_limpiar;
    }

    public Boton getBot_anular() {
        return bot_anular;
    }

    public void setBot_anular(Boton bot_anular) {
        this.bot_anular = bot_anular;
    }

    public void anular() { ///Este metodo invoca al cuadro de dialogo para la anulacion
        if (aut_filtro_cliente.getValor() != null) {
            dia_dialogo.dibujar();
            tab_busca_titulo.setSql("select ide_titulo,ide_estado,des_ingreso,valor,num_titulo,fecha_pago from tes_ingreso where ide_titulo=" + tab_tabla1.getValor("ide_titulo") + "");
            tab_busca_titulo.ejecutarSql();
        }
    }

    public void aceptaanular() {

        String sqla, sqlb, sqlc, fecha = "", estado = "";
        fecha = tab_busca_titulo.getValor("fecha_pago").substring(0, 10);
        List sql1 = utilitario.getConexion().consultar("select nombres from munc_empleados where ide_empleado=(select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
        if (!sql1.isEmpty()) {
            empleado = sql1.get(0) + "";
        }
        estado = tab_busca_titulo.getValor("ide_estado");
        if (tab_busca_titulo.getValor("ide_titulo") != null) {
            if (estado.equals("2")) {
                if (fecha.equals(utilitario.getFechaActual())) {
                    if (tex.getValue() != null) {
                        sqla = "update aux_valores set ide_estado='3' where ide_titulo='" + tab_busca_titulo.getValor("ide_titulo") + "'";
                        utilitario.getConexion().ejecutar(sqla);
                        utilitario.getConexion().commit();
                        System.out.println(sqla + "");
                        sqlb = "update tes_ingreso set ide_estado=3,fecha_modificacion='" + utilitario.getFechaHoraActual()
                                + "',responsable_modificacion='" + empleado
                                + "',ip_modifica='" + utilitario.getIp()
                                + "',observaciones='" + tex.getValue() + "'"
                                + "where ide_titulo='" + tab_busca_titulo.getValor("ide_titulo") + "'";
                        System.out.println(sqlb + "");
                        utilitario.getConexion().ejecutar(sqlb);
                        utilitario.getConexion().commit();
                        sqlc = "update rec_valores set ide_estado='3' where ide_titulo='" + tab_busca_titulo.getValor("ide_titulo") + "'";
                        System.out.println(sqlc + "");
                        utilitario.getConexion().ejecutar(sqlc);
                        utilitario.getConexion().commit();
                        tab_tabla1.limpiar();
                        aut_filtro_cliente.limpiar();
                        calcula_totales();
                        utilitario.addUpdate("dia_dialogo");
                        dia_dialogo.cerrar();
                    } else {
                        utilitario.agregarMensajeInfo("Escriba la razon de la baja", "EN EL CAMPO RAZON DIGITE LA RAZON DE LA BAJA");
                    }
                } else {
                    utilitario.agregarMensajeInfo("IMPOSIBLE ANULAR", "El titulo esta pagado y la vigencia para anular ha caducado");
                }
            } else if (estado.equals("1")) {
                if (tex.getValue() != null) {
                    sqla = "update aux_valores set ide_estado='3' where ide_titulo='" + tab_busca_titulo.getValor("ide_titulo") + "'";
                    utilitario.getConexion().ejecutar(sqla);
                    utilitario.getConexion().commit();
                    System.out.println(sqla + "");
                    sqlb = "update tes_ingreso set ide_estado=3,fecha_modificacion='" + utilitario.getFechaHoraActual()
                            + "',responsable_modificacion='" + empleado
                            + "',ip_modifica='" + utilitario.getIp()
                            + "',observaciones='" + tex.getValue() + "'"
                            + "where ide_titulo='" + tab_busca_titulo.getValor("ide_titulo") + "'";
                    System.out.println(sqlb + "");
                    utilitario.getConexion().ejecutar(sqlb);
                    utilitario.getConexion().commit();
                    sqlc = "update rec_valores set ide_estado='3' where ide_titulo='" + tab_busca_titulo.getValor("ide_titulo") + "'";
                    System.out.println(sqlc + "");
                    utilitario.getConexion().ejecutar(sqlc);
                    utilitario.getConexion().commit();
                    tab_tabla1.limpiar();
                    aut_filtro_cliente.limpiar();
                    calcula_totales();
                    utilitario.addUpdate("dia_dialogo");
                    dia_dialogo.cerrar();
                } else {
                    utilitario.agregarMensajeInfo("Escriba la razon de la baja", "EN EL CAMPO RAZON DIGITE LA RAZON DE LA BAJA");
                }
            } else if (estado.equals("3")) {
                utilitario.agregarMensajeInfo("Ya esta anulado", " El TITULO YA SE ENCUENTRA ANULADO");
            }
        } else {
            utilitario.agregarMensajeInfo("no Hay titulo Seleccionado", " POR FAVOR SELECCIONE EL TITULO A ANULAR");
        }
        System.out.println(tab_busca_titulo.getValor("ide_estado") + "" + estado);
    }

    public Dialogo getDia_dialogo2() {
        return dia_dialogo;
    }

    public void setDia_dialogo2(Dialogo dia_dialogo2) {
        this.dia_dialogo2 = dia_dialogo2;
    }

    public Tabla getTab_busca_titulo2() {
        return tab_busca_titulo2;
    }

    public void setTab_busca_titulo2(Tabla tab_busca_titulo2) {
        this.tab_busca_titulo2 = tab_busca_titulo2;
    }

    public Boton getBot_numero() {
        return bot_numero;
    }

    public void setBot_numero(Boton bot_numero) {
        this.bot_numero = bot_numero;
    }

    public void numerotitu() { ///Este metodo invoca al cuadro de dialogo para la anulacion
        if (aut_filtro_cliente.getValor() != null) {
            dia_dialogo2.dibujar();
            tab_busca_titulo2.setSql("select ide_titulo,ide_estado,des_ingreso,valor,num_titulo,fecha_pago from tes_ingreso where ide_titulo=" + tab_tabla1.getValor("ide_titulo") + "");
            tab_busca_titulo2.ejecutarSql();
        }
    }

    public void aceptanro() {
        String sqla, sqlb, sqlc, fecha = "";
        fecha = tab_busca_titulo2.getValor("fecha_pago").substring(0, 10);
        if (tab_busca_titulo2.getValor("ide_titulo") != null) {
            if (fecha.equals(utilitario.getFechaActual())) {
                if (tex2.getValue() != null) {
                    sqla = "update tes_ingreso set num_titulo=" + tex2.getValue() + "where ide_titulo='" + tab_busca_titulo2.getValor("ide_titulo") + "'";
                    utilitario.getConexion().ejecutar(sqla);
                    utilitario.getConexion().commit();
                    System.out.println(sqla + "");
                    sqlb = "update rec_valores set num_titulo=" + tex2.getValue() + "where ide_titulo='" + tab_busca_titulo2.getValor("ide_titulo") + "'";
                    utilitario.getConexion().ejecutar(sqla);
                    utilitario.getConexion().commit();
                    System.out.println(sqlb + "");
                    sqlc = "update aux_valores set num_titulo=" + tex2.getValue() + "where ide_titulo='" + tab_busca_titulo2.getValor("ide_titulo") + "'";
                    utilitario.getConexion().ejecutar(sqla);
                    utilitario.getConexion().commit();
                    System.out.println(sqlc + "");

                    tab_tabla1.limpiar();
                    aut_filtro_cliente.limpiar();
                    calcula_totales();
                    utilitario.addUpdate("dia_dialogo2");
                    dia_dialogo2.cerrar();
                } else {
                    utilitario.agregarMensajeInfo("Numero  de Titulo", "Escriba el numero de titulo (solo numeros)");
                }
            } else {
                utilitario.agregarMensajeInfo("IMPOSIBLE ANULAR", " La vigencia para anular a expirado");
            }
        } else {
            utilitario.agregarMensajeInfo("no Hay titulo Seleccionado", " POR FAVOR SELECCIONE EL TITULO A ANULAR");
        }





        System.out.println("" + fecha + "" + utilitario.getFechaActual() + "");
    }

    public VisualizarPDF getVp() {
        return vp;
    }

    public void setVp(VisualizarPDF vp) {
        this.vp = vp;
    }

    public Reporte getRep_reporte() {
        return rep_reporte;
    }

    public void setRep_reporte(Reporte rep_reporte) {
        this.rep_reporte = rep_reporte;
    }

    public void impresion() {
        Map parametros = new HashMap();
        parametros.put("ide_ingresos", Integer.parseInt(tab_tabla1.getValor("ide_ingreso").toString()));
        vp.setVisualizarPDF("rep_rentas/rep_carta_predial.jasper", parametros);
        vp.dibujar();
        utilitario.addUpdate("vp");

    }

    @Override
    public void insertar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
