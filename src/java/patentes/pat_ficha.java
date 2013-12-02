/*
 * /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package patentes;

import framework.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import sistema.Pantalla;

/**
 *
 * @author HP
 */
public class pat_ficha extends Pantalla {

    private String clave_patentes = "";
   // private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
    //private Barra bar_botones = new Barra();
    private Tabulador tab_tabulador = new Tabulador();
    private Division div_division = new Division();
  //  private Grupo gru_pantalla = new Grupo();
    private Tabla tab_foto_ficha = new Tabla();
    private Tabla tab_exoneracion = new Tabla();
    private Tabla tab_ficha_control = new Tabla();
    private Tabla tab_actividad = new Tabla();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private Boton bot_limpiar_p = new Boton();
    ///// BUSQUEDA DE CLAVES
    private Dialogo dia_dialogo = new Dialogo();
    private AutoCompletar auc_cliente = new AutoCompletar();
    private Tabla tab_busca_titulo = new Tabla();
    private MarcaAgua maa_marca = new MarcaAgua();
    private Boton bot_limpiar = new Boton();
    private Boton bot_busqueda = new Boton();
    private Boton bot_lupa = new Boton();
    private Confirmar con_guardar = new Confirmar();
    private VisualizarPDF vp = new VisualizarPDF();
    private Reporte rep_reporte = new Reporte();
    private Boton bot_impresion = new Boton();

    public pat_ficha() {

        bar_botones.getBot_insertar().setUpdate("tab_tabla,tab_tabulador:tab_foto_ficha,tab_tabulador:tab_exoneracion,tab_tabulador:tab_ficha_control");
        bar_botones.getBot_guardar().setUpdate("tab_tabla,tab_tabulador:tab_foto_ficha,tab_tabulador:tab_exoneracion,tab_tabulador:tab_ficha_control");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla,tab_tabulador:tab_foto_ficha,tab_tabulador:tab_exoneracion,tab_tabulador:tab_ficha_control");

        bar_botones.getBot_inicio().setMetodo("inicio");
        bar_botones.getBot_siguiente().setMetodo("siguiente");
        bar_botones.getBot_atras().setMetodo("atras");
        bar_botones.getBot_fin().setMetodo("fin");

        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
        aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_tabla,tab_tabulador:tab_foto_ficha,tab_tabulador:tab_exoneracion,tab_tabulador:tab_ficha_control");
        bar_botones.agregarComponente(aut_filtro_cliente);

        Espacio esp = new Espacio();
        esp.setWidth("25");
        esp.setHeight("1");
        bar_botones.agregarComponente(esp);

        bot_limpiar_p.setIcon("ui-icon-cancel");
        bot_limpiar_p.setTitle("Limpiar");
        bot_limpiar_p.setMetodo("limpiar");
        bot_limpiar_p.setUpdate("aut_filtro_cliente,tab_tabla,tab_tabulador");
        bar_botones.agregarBoton(bot_limpiar_p);


        bot_busqueda.setValue("Buscar Ficha");
        bot_busqueda.setMetodo("BuscarFicha");
        bot_busqueda.setUpdate("dia_dialogo");
        bar_botones.agregarBoton(bot_busqueda);

        /**
         * ****************************************************************************************
         */
        auc_cliente.setId("auc_cliente");
        auc_cliente.setAutoCompletar("select ide_predio,clave,nombre from sigt_predio order by clave");
        auc_cliente.setMetodoChange("busca_Ficha", "tab_busca_titulo");
        auc_cliente.setSize(80);
        Grid gri_busca = new Grid();
        Grupo gru_grupo = new Grupo();
        gru_grupo.getChildren().add(auc_cliente);

        maa_marca.setFor("auc_cliente");
        maa_marca.setValue("Buscar Clave ");
        gru_grupo.getChildren().add(maa_marca);

        bot_lupa.setValue("buscar");
        bot_lupa.setTitle("buscador");
        bot_lupa.setMetodo("buscador");
        bot_lupa.setUpdate("tab_busca_titulo");
        gru_grupo.getChildren().add(bot_lupa);

        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("tab_busca_titulo,auc_cliente");
        gru_grupo.getChildren().add(bot_limpiar);

        gri_busca.setHeader(gru_grupo);
        tab_busca_titulo.setId("tab_busca_titulo");
        tab_busca_titulo.setSql("SELECT ide_predio,clave,nombre from  sigt_predio where ide_predio=-1");
        tab_busca_titulo.setRows(10);
        tab_busca_titulo.setLectura(true);
        tab_busca_titulo.setCampoPrimaria("ide_predio");
        tab_busca_titulo.dibujar();
        tab_busca_titulo.setStyle("width:" + (dia_dialogo.getAnchoPanel() - 15) + "px");


        gri_busca.getChildren().add(tab_busca_titulo);
        gri_busca.setStyle("width:" + (dia_dialogo.getAnchoPanel() - 5) + "px;height:" + dia_dialogo.getAltoPanel() + "px;overflow: auto;display: block;");

        dia_dialogo.setId("dia_dialogo");
        dia_dialogo.setTitle("Buscar Ficha");
        dia_dialogo.setResizable(false);
        dia_dialogo.setDialogo(gri_busca);
        dia_dialogo.getBot_aceptar().setMetodo("aceptaBuscar");
        agregarComponente(dia_dialogo);
       

        /**
         * ************************************************************************************************************************
         */
        tab_actividad.setId("tab_actividad");
        tab_actividad.setTabla("pat_actividad", "pat_actividad", 1);
        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("pat_ficha", "ide_pat_ficha", 1);
        tab_tabla.setCampoOrden("ide_pat_ficha");
        // tab_tabla.setCondicion("ide_cliente = " + aut_filtro_cliente.getValor() );
        tab_tabla.setCondicion("ide_cliente = -1");
        tab_tabla.getColumna("ide_cliente").setVisible(false);
        tab_tabla.getColumna("ide_predio").setVisible(false);
        tab_tabla.getColumna("razon_baja").setVisible(false);
        tab_tabla.getColumna("FECHA_INGRESO").setValorDefecto(utilitario.getFechaActual());
        // tab_tabla.getColumna("ide_pat_capital").setCombo("pat_capital", "ide_pat_capital", "rango_inicial", "");
        tab_tabla.getColumna("ide_tipo_propietario").setCombo("pat_tipo_propietario", "ide_tipo_propietario", "detalle", "");
        tab_tabla.getColumna("ide_pat_capital").setCombo("select ide_pat_capital,rango_inicial,rango_final from pat_capital");
        //  tab_tabla.getColumna("ide_pat_actividad").setCombo("pat_actividad", "ide_pat_actividad", "detalle_actividad", "");
        tab_tabla.getColumna("ide_pat_actividad").setCombo("select ide_pat_actividad,codigo,detalle_actividad from pat_actividad where nivel=2;");
        tab_tabla.getColumna("ide_pat_actividad").setAutoCompletar();
        tab_tabla.getColumna("clave_patentes").setEtiqueta();
        tab_tabla.getColumna("clave_patentes").setEstilo("font-weight: bold;font-size:15px;color:red");
        tab_tabla.getColumna("ide_pat_actividad").setMetodoChange("cambio_acti_combo");
        tab_tabla.getColumna("nro_uso").setValorDefecto("001");
        tab_tabla.getColumna("nro_uso").setMetodoChange("clave_xxx");
        tab_tabla.getColumna("base_imponible").setMetodoChange("calc_base");
        tab_tabla.getColumna("aval_terreno").setValorDefecto("0");
        tab_tabla.getColumna("aval_terreno").setMetodoChange("cambio_acti_txt");
        tab_tabla.getColumna("costo_mantenimiento").setValorDefecto("0");
        tab_tabla.getColumna("costo_mantenimiento").setMetodoChange("cambio_acti_txt");
        tab_tabla.getColumna("costo_cosecha_transporte").setValorDefecto("0");
        tab_tabla.getColumna("costo_cosecha_transporte").setMetodoChange("cambio_acti_txt");
        tab_tabla.getColumna("exoneracion").setLectura(true);
        tab_tabla.getColumna("clave_catastral").setMetodoChange("clave_xxx2");
        tab_tabla.setTipoFormulario(true);
        tab_tabla.getGrid().setColumns(4);
        tab_tabla.dibujar();
        tab_tabla.agregarRelacion(tab_foto_ficha);
        tab_tabla.agregarRelacion(tab_exoneracion);
        tab_tabla.agregarRelacion(tab_ficha_control);
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);
        tab_foto_ficha.setId("tab_foto_ficha");
        tab_foto_ficha.setIdCompleto("tab_tabulador:tab_foto_ficha");
        tab_foto_ficha.setTabla("pat_foto_ficha", "ide_pat_foto_ficha", 2);
        tab_foto_ficha.getColumna("ide_pat_foto_ficha").setVisible(false);
        tab_foto_ficha.getColumna("path").setUpload("upload/fotos");
        tab_foto_ficha.getColumna("path").setImagen("256", "256");
        tab_foto_ficha.getColumna("fecha").setLectura(true);
        tab_foto_ficha.getColumna("fecha").setValorDefecto(utilitario.getFechaActual());
        tab_foto_ficha.setTipoFormulario(true);
        tab_foto_ficha.setCampoForanea("ide_pat_ficha");
        tab_foto_ficha.dibujar();
        PanelTabla pat_panel3 = new PanelTabla();
        pat_panel3.setPanelTabla(tab_foto_ficha);
        tab_exoneracion.setId("tab_exoneracion");
        tab_exoneracion.setIdCompleto("tab_tabulador:tab_exoneracion");
        tab_exoneracion.setTabla("pat_exoneracion", "ide_pat_exoneracion", 3);
        tab_exoneracion.getColumna("path").setUpload("upload/fotos");
        tab_exoneracion.getColumna("path").setImagen("256", "256");
        tab_exoneracion.getColumna("fecha_inicio").setLectura(true);
        tab_exoneracion.getColumna("fecha_inicio").setValorDefecto(utilitario.getFechaActual());
        tab_exoneracion.setTipoFormulario(true);
        tab_exoneracion.setCampoForanea("ide_pat_ficha");
        tab_exoneracion.dibujar();
        PanelTabla pat_panel4 = new PanelTabla();
        pat_panel4.setPanelTabla(tab_exoneracion);
        tab_ficha_control.setId("tab_ficha_control");
        tab_ficha_control.setIdCompleto("tab_tabulador:tab_ficha_control");
        tab_ficha_control.setTabla("sigt_ficha_control", "ide_ficha_control", 4);
        tab_ficha_control.setTipoFormulario(true);
        tab_ficha_control.setCampoForanea("ide_pat_ficha");
        tab_ficha_control.getGrid().setColumns(4);
        tab_ficha_control.dibujar();
        PanelTabla pat_panel5 = new PanelTabla();
        pat_panel5.setPanelTabla(tab_ficha_control);


        /**
         * ************************************************************
         */
        vp.setId("vp");
        vp.setTitle("Ficha Patentes");
       agregarComponente(vp);

        bot_impresion.setValue("Impresion");
        bot_impresion.setMetodo("impresion");
        bar_botones.agregarBoton(bot_impresion);


        /**
         * ******************************************************
         */
        tab_tabulador.setId("tab_tabulador");
        tab_tabulador.agregarTab("FOTO FICHA", pat_panel3);
        tab_tabulador.agregarTab("EXONERACION", pat_panel4);
        tab_tabulador.agregarTab("CONTROL DE FICHAS", pat_panel5);
        div_division.setId("div_division");
        div_division.dividir2(pat_panel, tab_tabulador, "50%", "H");
        //gru_pantalla.getChildren().add(bar_botones);
        agregarComponente(div_division);
      
    }

    public void cambio_acti_combo(SelectEvent evt) {
        tab_tabla.modificar(evt);
        String dato;
        boolean a;
        dato = (utilitario.getConexion().consultar("select pecuario from pat_actividad where ide_pat_actividad=" + tab_tabla.getValor("ide_pat_actividad")).toString());
        if (dato.equals("[true]")) {
            tab_tabla.getColumna("base_imponible").setLectura(true);
            tab_tabla.getColumna("aval_terreno").setLectura(false);
            tab_tabla.getColumna("costo_mantenimiento").setLectura(false);
            tab_tabla.getColumna("costo_cosecha_transporte").setLectura(false);
            utilitario.addUpdate("tab_tabla");
        } else {
            tab_tabla.getColumna("base_imponible").setLectura(false);
            tab_tabla.getColumna("aval_terreno").setLectura(true);
            tab_tabla.getColumna("costo_mantenimiento").setLectura(true);
            tab_tabla.getColumna("costo_cosecha_transporte").setLectura(true);
            utilitario.addUpdate("tab_tabla");
        }

    }

    public void cambio_acti_txt(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        String dato;
        double vt, cct, cm, bi;
        dato = (utilitario.getConexion().consultar("select pecuario from pat_actividad where ide_pat_actividad=" + tab_tabla.getValor("ide_pat_actividad")).toString());
        if (dato.equals("[true]")) {
            try {
                cct = Double.parseDouble(tab_tabla.getValor("costo_cosecha_transporte"));
                cm = Double.parseDouble(tab_tabla.getValor("costo_mantenimiento"));
                vt = Double.parseDouble(tab_tabla.getValor("aval_terreno"));
                bi = vt - (cct + cm);
                tab_tabla.setValor("base_imponible", bi + "");
                utilitario.addUpdate("tab_tabla");
            } catch (Exception e) {
                utilitario.agregarMensajeInfo("valores nulos", "existen campos nulos para realizar la formula");
            }
        }
    }

    public void calc_base(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        Tabla tab_dato = new Tabla();
        double valmin = 0, valmax = 0;
        String a = "a", b = "b";
        try {
            tab_dato = utilitario.consultar("select ide_pat_actividad,val_inferior,val_maximo from pat_actividad where ide_pat_actividad=" + tab_tabla.getValor("ide_pat_actividad").toString());
            System.out.println("select ide_pat_actividad,val_inferior from pat_actividad where ide_pat_actividad=" + tab_tabla.getValor("ide_pat_actividad").toString());
            //a = tab_dato.getValor("val_inferior");
            //b = tab_dato.getValor("val_maximo");
            //System.out.println(tab_dato.getTotalFilas() + a + b + "");
            valmin = Double.parseDouble(tab_dato.getValor("val_inferior").toString());
            valmax = Double.parseDouble(tab_dato.getValor("val_maximo").toString());
            System.out.println(valmin + "               " + valmax + "");
            if (Integer.parseInt(tab_tabla.getValor("base_imponible")) < valmin) {
                tab_tabla.setValor("base_imponible", valmin + "");
            } else if (Integer.parseInt(tab_tabla.getValor("base_imponible")) > valmax) {
                tab_tabla.setValor("base_imponible", valmax + "");
            }
            utilitario.addUpdate("tab_tabla");
        } catch (Exception e) {
            utilitario.agregarMensajeInfo("Atención", "Seleccione la Actividad para calcular la base imponible  " + valmin + "" + valmax + "");
        }
    }

    public void clave_xxx(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        int dato, val;
        dato = tab_tabla.getValor("nro_uso").length();
        val = Integer.parseInt(tab_tabla.getValor("nro_uso"));
        if (dato == 1) {
            tab_tabla.setValor("nro_uso", "00" + val + "");
        } else if (dato == 2) {
            tab_tabla.setValor("nro_uso", "0" + val + "");
        }
        utilitario.addUpdate("tab_tabla");
        tab_tabla.setValor("clave_patentes", clave_patentes + tab_tabla.getValor("nro_uso"));
        utilitario.addUpdate("tab_tabla");
    }

    public void clave_xxx2(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        int dato, val;
        dato = tab_tabla.getValor("nro_uso").length();
        val = Integer.parseInt(tab_tabla.getValor("nro_uso"));
        if (dato == 1) {
            tab_tabla.setValor("nro_uso", "00" + val + "");
        } else if (dato == 2) {
            tab_tabla.setValor("nro_uso", "0" + val + "");
        }
        utilitario.addUpdate("tab_tabla");
        clave_patentes = tab_tabla.getValor("clave_catastral");
        tab_tabla.setValor("clave_patentes", clave_patentes + tab_tabla.getValor("nro_uso"));
        utilitario.addUpdate("tab_tabla");
    }

    /**
     * **************************************************************
     */
    public void limpiar() {
        tab_tabla.limpiar();
        aut_filtro_cliente.limpiar();
    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro_cliente.onSelect(evt);
        tab_tabla.setCondicion("ide_cliente = " + aut_filtro_cliente.getValor());
        tab_tabla.ejecutarSql();
        tab_foto_ficha.ejecutarValorForanea(tab_tabla.getValorSeleccionado());
        tab_exoneracion.ejecutarValorForanea(tab_tabla.getValorSeleccionado());
        tab_ficha_control.ejecutarValorForanea(tab_tabla.getValorSeleccionado());
        utilitario.addUpdate("tab_tabulador");
    }

    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
    }

    /**
     * ********************************************************************
     */
    public void cambiar_estado() {
        String dato;

        dato = (utilitario.getConexion().consultar("SELECT rec_tipo_contribuyente.artesano from rec_clientes,rec_tipo_contribuyente where "
                + "rec_clientes.ide_tipo_contribuyente=rec_tipo_contribuyente.ide_tipo_contribuyente and rec_clientes.ide_cliente=" + aut_filtro_cliente.getValor()).toString());
        if (dato.equals("[true]")) {
            tab_tabla.setValor("exoneracion", "true");
        }
        utilitario.addUpdate("tab_tabla");
    }
@Override
    public void insertar() {
        if (aut_filtro_cliente.getValor() != null) {
            if (tab_tabla.isFocus()) {
                if (!tab_tabla.isFilaInsertada()) {
                    tab_tabla.getColumna("ide_cliente").setValorDefecto(aut_filtro_cliente.getValor());
                    tab_tabla.insertar();
                    cambiar_estado();
                } else {
                    utilitario.agregarMensaje("No se puede insertar", "Debe grabar el registro actual");
                }

            } else if (tab_foto_ficha.isFocus()) {
                tab_foto_ficha.insertar();
            } else if (tab_exoneracion.isFocus()) {
                if (tab_tabla.getValor("exoneracion").toString().equals("true")) {
                    utilitario.agregarMensajeInfo("Atención", tab_tabla.getValor("exoneracion").toString());
                    tab_exoneracion.insertar();
                } else {
                    utilitario.agregarMensajeInfo("Atención", "Este cliente no es sujeto de exoneracion");
                }
            } else if (tab_ficha_control.isFocus()) {
                tab_ficha_control.insertar();
            }
        } else {
            utilitario.agregarMensajeInfo("Atención", "Debe seleccionar un contribuyente");
        }
    }
@Override
    public void guardar() {
        tab_tabla.guardar();
        tab_foto_ficha.guardar();
        tab_exoneracion.guardar();
        tab_ficha_control.guardar();
        utilitario.getConexion().guardarPantalla();
    }
@Override
    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
    }

    public void seleccionar_tabla(SelectEvent evt) {
        tab_tabla.seleccionarFila(evt);
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }

    public Tabla gettab_foto_ficha() {
        return tab_foto_ficha;
    }

    public void settab_foto_ficha(Tabla tab_foto_ficha) {
        this.tab_foto_ficha = tab_foto_ficha;
    }

    public Tabla gettab_exoneracion() {
        return tab_exoneracion;
    }

    public void settab_exoneracion(Tabla tab_exoneracion) {
        this.tab_exoneracion = tab_exoneracion;
    }

    public Tabla gettab_ficha_control() {
        return tab_ficha_control;
    }

    public void settab_ficha_control(Tabla tab_ficha_control) {
        this.tab_ficha_control = tab_ficha_control;
    }

    public Tabla gettab_actividad() {
        return tab_actividad;
    }

    public void settab_actividad(Tabla tab_actividad) {
        this.tab_actividad = tab_actividad;
    }
/*
    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }
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

    public Boton getBot_limpiar_p() {
        return bot_limpiar;
    }

    public void setBot_limpiar_p(Boton bot_limpiar) {
        this.bot_limpiar = bot_limpiar;
    }

    public Boton getBot_limpiar() {
        return bot_limpiar;
    }

    public void setBot_limpiar(Boton bot_limpiar) {
        this.bot_limpiar = bot_limpiar;
    }

    public Boton getBot_busqueda() {
        return bot_busqueda;
    }

    public void setBot_busqueda(Boton bot_busqueda) {
        this.bot_busqueda = bot_busqueda;
    }

    public void BuscarFicha() { ///Este metodo invoca al cuadro de dialogo para buscar clave catastral
        if (aut_filtro_cliente.getValor() != null) {
            dia_dialogo.dibujar();
        }
    }

    public void aceptaBuscar() {
        if (tab_busca_titulo.getValor("ide_predio") != null) {
            tab_tabla.setValor("ide_predio", tab_busca_titulo.getValor("ide_predio"));
            System.out.println(tab_busca_titulo.getValor("ide_predio"));
            clave_patentes = tab_busca_titulo.getValor("clave");
            tab_tabla.setValor("clave_patentes", tab_busca_titulo.getValor("clave") + tab_tabla.getValor("nro_uso").toString());
            tab_tabla.setValor("clave_catastral", clave_patentes);
            System.out.println(tab_busca_titulo.getValor("clave"));
            utilitario.addUpdate("tab_tabla");

        }
        dia_dialogo.cerrar();
        utilitario.addUpdate("dia_dialogo");
    }

    public void busca_Ficha(SelectEvent evt) {
        auc_cliente.onSelect(evt);
        tab_busca_titulo.setSql("select ide_predio,clave,nombre from sigt_predio where ide_predio =" + auc_cliente.getValor() + "");
        // System.out.println("select ide_predio,clave from sigt_predio where clave like '%" + auc_cliente.getValor() + "%'");
        tab_busca_titulo.ejecutarSql();
    }

    public AutoCompletar getAuc_cliente() {
        return auc_cliente;
    }

    public void setAuc_cliente(AutoCompletar auc_cliente) {
        this.auc_cliente = auc_cliente;
    }

    /**
     * ***********************************************************
     */
    public void impresion() {
        Map parametros = new HashMap();
        parametros.put("IDE", Integer.parseInt(tab_tabla.getValor("ide_pat_ficha").toString()));
        System.out.println(" " + Integer.parseInt(tab_tabla.getValor("ide_pat_ficha").toString()));
        vp.setVisualizarPDF("rep_patente/rep_patentes.jasper", parametros);
        vp.dibujar();
        utilitario.addUpdate("vp");

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
}