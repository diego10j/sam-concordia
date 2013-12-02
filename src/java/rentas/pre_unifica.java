/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import java.util.HashMap;
import java.util.Map;
import sistema.*;

/**
 *
 * @author HP
 */
public class pre_unifica extends Pantalla{

    //private Utilitario utilitario = new Utilitario();
    private Tabla tab_seleccion = new Tabla();
    //private Barra bar_botones = new Barra();
    private Division div_division = new Division();
   // private Grupo gru_pantalla = new Grupo();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private Boton bot_limpiar = new Boton();
    private Texto tex_busca = new Texto();
    private Boton bot_busca = new Boton();
    private Boton bot_unificar = new Boton();
    private Boton bot_consulta = new Boton();
    private VisualizarPDF vip_consulta = new VisualizarPDF();
    private Consulta con_titulos = new Consulta();
    private Boton bot_titulos = new Boton();

    public pre_unifica() {
        bar_botones.getBot_insertar().setRendered(false);
        bar_botones.getBot_guardar().setRendered(false);
        bar_botones.getBot_eliminar().setRendered(false);
        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("aut_filtro_cliente,tab_seleccion,tex_busca");

        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
        bar_botones.agregarComponente(aut_filtro_cliente);
        bar_botones.agregarComponente(bot_limpiar);

        Espacio esp = new Espacio();
        esp.setHeight("0");
        esp.setWidth("25");
        bar_botones.agregarComponente(esp);
        bot_unificar.setValue("Unificar Información");
        bot_unificar.setMetodo("unificar");
        bar_botones.agregarComponente(bot_unificar);


        Espacio esp1 = new Espacio();
        esp1.setHeight("0");
        esp1.setWidth("35");
        bar_botones.agregarComponente(esp1);
        bot_consulta.setValue("Consultar Deudas");
        bot_consulta.setMetodo("consultar");
        bot_consulta.setUpdate("vip_consulta");
        bar_botones.agregarComponente(bot_consulta);

        //configuracion de la tabla de valores o titulos (cabecera)
        tab_seleccion.setId("tab_seleccion");
        tab_seleccion.setTabla("rec_clientes", "ide_cliente", 1);
        tab_seleccion.setCondicion("nombre like '-1%' OR CEDULA like '-1%'");
        tab_seleccion.setNumeroTabla(1);
        tab_seleccion.setRows(15);
        tab_seleccion.setTipoSeleccion(true);

        tab_seleccion.dibujar();

        Grid gri_busca = new Grid();
        gri_busca.setColumns(4);

        tex_busca.setId("tex_busca");
        tex_busca.setSize(45);
        bot_busca.setTitle("Buscar");
        bot_busca.setIcon("ui-icon-search");
        bot_busca.setMetodo("buscar");
        bot_busca.setUpdate("tab_seleccion");

        gri_busca.getChildren().add(tex_busca);
        gri_busca.getChildren().add(bot_busca);

        Espacio esp2 = new Espacio();
        esp2.setHeight("0");
        esp2.setWidth("35");

        gri_busca.getChildren().add(esp2);

        bot_titulos.setValue("Ver Títulos");
        bot_titulos.setMetodo("verTitulos");
        bot_titulos.setUpdate("con_titulos");
        gri_busca.getChildren().add(bot_titulos);

        PanelTabla pat_panel1 = new PanelTabla();
        pat_panel1.getChildren().add(gri_busca);
        pat_panel1.setPanelTabla(tab_seleccion);

        div_division.setId("div_division");
        div_division.dividir1(pat_panel1);

        //gru_pantalla.getChildren().add(bar_botones);
        agregarComponente(div_division);
  

        vip_consulta.setId("vip_consulta");
        vip_consulta.setWidth("90%");
        agregarComponente(vip_consulta);

        con_titulos.setId("con_titulos");
        con_titulos.setConsulta("select * from rec_valores where ide_cliente=-1", "ide_titulo");
        con_titulos.getBot_aceptar().setRendered(false);
        agregarComponente(con_titulos);

    }

    public void verTitulos() {
        if (tab_seleccion.getValorSeleccionado() != null) {
            vip_consulta.setTitle("TITULOS");
            con_titulos.getTab_consulta_dialogo().setSql("select * from rec_valores where ide_cliente in(" + tab_seleccion.getFilasSeleccionadas()+")");
            con_titulos.getTab_consulta_dialogo().ejecutarSql();
            con_titulos.dibujar();
        }
    }

    public void buscar() {
        if (aut_filtro_cliente.getValor() != null) {
            if (tex_busca.getValue() == null || tex_busca.getValue().toString().isEmpty()) {
                tab_seleccion.setCondicion("nombre like '-1%' OR CEDULA like '-1%' ");
            } else {
                tab_seleccion.setCondicion("nombre like '" + tex_busca.getValue().toString().toUpperCase() + "%' OR CEDULA like '" + tex_busca.getValue().toString().toUpperCase() + "%' and ide_cliente != " + aut_filtro_cliente.getValor());
            }
            tab_seleccion.ejecutarSql();
            tab_seleccion.setFilaActual(aut_filtro_cliente.getValor());
          //  tab_seleccion.eliminar();
        } else {
            utilitario.agregarMensajeInfo("", "Debe seleccionar el contribuyente al que se le unificarar la información");
        }
    }

    public void consultar() {
        if (aut_filtro_cliente.getValor() != null) {
            Map parametros = new HashMap();
            parametros.put("ide_cliente", Long.parseLong(aut_filtro_cliente.getValor()));
            vip_consulta.setVisualizarPDF("rep_rentas/rep_deudas_cliente.jasper", parametros);
            vip_consulta.dibujar();
        } else {
            utilitario.agregarMensajeInfo("", "Debe seleccionar el contribuyente al que se le unificarar la información");
        }
    }

    public void unificar() {
        if (aut_filtro_cliente.getValor() != null) {
            if (!tab_seleccion.getFilasSeleccionadas().isEmpty()) {
                for (int i = 0; i < tab_seleccion.getSeleccionados().length; i++) {
                    Object obj_clie = tab_seleccion.getSeleccionados()[i].getCampos()[tab_seleccion.getNumeroColumna("ide_cliente")];
                    if (obj_clie != null) {
                        String sql1 = "UPDATE rec_valores SET IDE_CLIENTE=" + aut_filtro_cliente.getValor() + " where ide_cliente=" + obj_clie;
                        /// tes_ingreso no tiene ????
                        String sql2 = "UPDATE sigt_titular_dominio SET IDE_CLIENTE=" + aut_filtro_cliente.getValor() + " where ide_cliente=" + obj_clie;
                        String sql3 = "UPDATE sigt_cambio_dominio SET IDE_CLIENTE=" + aut_filtro_cliente.getValor() + " where ide_cliente=" + obj_clie;
                        String sql4 = "UPDATE rec_coactivas SET IDE_CLIENTE=" + aut_filtro_cliente.getValor() + " where ide_cliente=" + obj_clie;
                        String sql5 = "UPDATE aux_valores SET IDE_CLIENTE=" + aut_filtro_cliente.getValor() + " where ide_cliente=" + obj_clie;
                        String sql6 = "UPDATE tes_convenio_pago SET IDE_CLIENTE=" + aut_filtro_cliente.getValor() + " where ide_cliente=" + obj_clie;
                        String sql7 = "UPDATE rec_exoneracion SET IDE_CLIENTE=" + aut_filtro_cliente.getValor() + " where ide_cliente=" + obj_clie;
                        utilitario.getConexion().agregarSqlPantalla(sql1);
                        utilitario.getConexion().agregarSqlPantalla(sql2);
                        utilitario.getConexion().agregarSqlPantalla(sql3);
                        utilitario.getConexion().agregarSqlPantalla(sql4);
                        utilitario.getConexion().agregarSqlPantalla(sql5);
                        utilitario.getConexion().agregarSqlPantalla(sql6);
                        utilitario.getConexion().agregarSqlPantalla(sql7);
                    }


                }
                if (utilitario.getConexion().guardarPantalla().isEmpty()) {
                    String mensaje = "";
                    for (int i = 0; i < tab_seleccion.getSeleccionados().length; i++) {
                        Object obj_clie = tab_seleccion.getSeleccionados()[i].getCampos()[tab_seleccion.getNumeroColumna("ide_cliente")];
                        if (obj_clie != null) {
                            if (!aut_filtro_cliente.getValor().equals(obj_clie + "")) {
                                mensaje += utilitario.getConexion().ejecutar("Delete from rec_clientes where ide_cliente=" + obj_clie);
                            }
                        }
                    }
                    if (mensaje.isEmpty()) {
                        utilitario.getConexion().commit();
                        aut_filtro_cliente.actualizar();
                    } else {
                        utilitario.getConexion().rollback();
                        utilitario.agregarMensajeInfo("No se pudieron borrar todos los contribuyentes seleccionados", "Se a unificado la información al contribuyente seleccionado");
                    }
                }

            } else {
                utilitario.agregarMensajeInfo("", "Debe seleccionar al menos un contribuyente para unificar información");
            }
        } else {
            utilitario.agregarMensajeInfo("", "Debe seleccionar el contribuyente al que se le unificarar la información");
        }
    }

    public void limpiar() {
        tab_seleccion.limpiar();
        aut_filtro_cliente.limpiar();
        tex_busca.limpiar();
    }

    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
    }

    public Tabla getTab_seleccion() {
        return tab_seleccion;
    }

    public void setTab_seleccion(Tabla tab_seleccion) {
        this.tab_seleccion = tab_seleccion;
    }
/*
    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }
*/
    public VisualizarPDF getVip_consulta() {
        return vip_consulta;
    }

    public void setVip_consulta(VisualizarPDF vip_consulta) {
        this.vip_consulta = vip_consulta;
    }

    public Consulta getCon_titulos() {
        return con_titulos;
    }

    public void setCon_titulos(Consulta con_titulos) {
        this.con_titulos = con_titulos;
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
