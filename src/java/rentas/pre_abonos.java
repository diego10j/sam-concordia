/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import org.primefaces.event.SelectEvent;
import sistema.Utilitario;

/**
 *
 * @author Diego
 */
public class pre_abonos {

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private MarcaAgua maa_marca = new MarcaAgua();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private Boton bot_limpiar = new Boton();

    public pre_abonos() {

        bar_botones.quitarBotonInsertar();
        bar_botones.quitarBotonGuardar();
        bar_botones.quitarBotonEliminar();





        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("aut_filtro_cliente");

        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
        aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_tabla1");
        bar_botones.agregarComponente(aut_filtro_cliente);
        bar_botones.agregarComponente(bot_limpiar);

        maa_marca.setFor("aut_filtro_cliente");
        maa_marca.setValue("Buscar Contribuyente");
        gru_pantalla.getChildren().add(maa_marca);

        tab_tabla1.setId("tab_tabla1");

        tab_tabla1.setSql("select ide_ingreso,tes_ingreso.ide_titulo,des_ingreso,valor,parroquia,calles,observaciones from tes_ingreso,rec_valores,rec_concepto where tes_ingreso.ide_titulo=rec_valores.ide_titulo "
                + "and rec_concepto.ide_concepto=rec_valores.ide_concepto and abonos=true and ide_cliente=-1");
        tab_tabla1.setNumeroTabla(1);
        tab_tabla1.setLectura(true);
        tab_tabla1.setCampoPrimaria("ide_ingreso");
        tab_tabla1.getColumna("ide_titulo").setVisible(false);
        tab_tabla1.dibujar();

        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla1);
        
        div_division.dividir1(pat_panel2);


        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);

    

    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro_cliente.onSelect(evt);
        tab_tabla1.setSql("select ide_ingreso,tes_ingreso.ide_titulo,des_ingreso,valor,parroquia,calles,observaciones from tes_ingreso,rec_valores,rec_concepto where tes_ingreso.ide_titulo=rec_valores.ide_titulo "
                + "and rec_concepto.ide_concepto=rec_valores.ide_concepto and abonos=true and ide_cliente=" + aut_filtro_cliente.getValor());
        tab_tabla1.ejecutarSql();

    }

    public void limpiar() {
        aut_filtro_cliente.limpiar();
    }

    public MarcaAgua getMaa_marca() {
        return maa_marca;
    }

    public void setMaa_marca(MarcaAgua maa_marca) {
        this.maa_marca = maa_marca;
    }

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }

    public Tabla getTab_tabla1() {
        return tab_tabla1;
    }

    public void setTab_tabla1(Tabla tab_tabla1) {
        this.tab_tabla1 = tab_tabla1;
    }

    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
    }
}
