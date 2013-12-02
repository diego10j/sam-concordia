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
public class pre_abonos_coactivas {

    private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    private Grupo gru_pantalla = new Grupo();
    private MarcaAgua maa_marca = new MarcaAgua();
    private AutoCompletar aut_filtro = new AutoCompletar();
    private Boton bot_limpiar = new Boton();

    public pre_abonos_coactivas() {

        bar_botones.quitarBotonInsertar();
        bar_botones.quitarBotonGuardar();
        bar_botones.quitarBotonEliminar();

        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("aut_filtro,tab_tabla1,tab_tabla2");

        aut_filtro.setId("aut_filtro");
        aut_filtro.setAutoCompletar("select rec_coactivas.ide_cliente,nro_documento,nombre,cedula from rec_coactivas,rec_clientes where rec_coactivas.ide_cliente=rec_clientes.ide_cliente order by nro_documento");
        aut_filtro.setMetodoChange("filtrar_por_cliente", "tab_tabla1");
        bar_botones.agregarComponente(aut_filtro);
        bar_botones.agregarComponente(bot_limpiar);

        maa_marca.setFor("aut_filtro");
        maa_marca.setValue("Buscar Contribuyente");
        gru_pantalla.getChildren().add(maa_marca);

        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setSql("select co.ide_coactiva,ge.nombres||'-'||tg.detalle as gestor,e.detalle as estado,em.nombres as empleado, "
                + "co.nro_documento,co.fecha_coactiva,co.valor,case when co.tipo=1 then 'NOTIFICACION' WHEN co.tipo=2 then 'CITACION' END as tipo,co.numero_coactiva,co.observaciones "
                + "from rec_coactivas co,rec_gestor ge,rec_tipo_gestor tg, rec_estados e,rec_clientes cl,munc_empleados em "
                + "where co.ide_cliente=-1 "
                + "and tg.ide_tipo_gestor=ge.ide_tipo_gestor "
                + "and e.coactivas=true ");
        tab_tabla1.setCampoPrimaria("ide_coactiva");
        tab_tabla1.onSelect("seleccionar_tabla1");
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.setLectura(true);
        tab_tabla1.setRows(3);
        tab_tabla1.dibujar();

        PanelTabla pat_panel1 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);

        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("rec_coactiva_detalle", "ide_coactiva_detalle", 2);
        tab_tabla2.setCampoForanea("ide_coactiva");
        tab_tabla2.setLectura(true);
        tab_tabla2.setRows(10);
        tab_tabla2.dibujar();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla2);

        div_division.dividir2(pat_panel1, pat_panel2, "60%", "H");


        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);

   

    }

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla1.seleccionarFila(evt);
    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro.onSelect(evt);
      tab_tabla1.setSql("select co.ide_coactiva,ge.nombres||'-'||tg.detalle as gestor,e.detalle as estado,em.nombres as empleado, "
                + "co.nro_documento,co.fecha_coactiva,co.valor,case when co.tipo=1 then 'NOTIFICACION' WHEN co.tipo=2 then 'CITACION' END as tipo,co.numero_coactiva,co.observaciones "
                + "from rec_coactivas co,rec_gestor ge,rec_tipo_gestor tg, rec_estados e,rec_clientes cl,munc_empleados em "
                + "where co.ide_cliente="+aut_filtro.getValor()+" "
                + "and tg.ide_tipo_gestor=ge.ide_tipo_gestor "
                + "and e.coactivas=true ");
        tab_tabla1.ejecutarSql();

    }

    public void limpiar() {
        aut_filtro.limpiar();
        tab_tabla1.limpiar();
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

    public AutoCompletar getAut_filtro() {
        return aut_filtro;
    }

    public void setAut_filtro(AutoCompletar aut_filtro) {
        this.aut_filtro = aut_filtro;
    }

    public Tabla getTab_tabla2() {
        return tab_tabla2;
    }

    public void setTab_tabla2(Tabla tab_tabla2) {
        this.tab_tabla2 = tab_tabla2;
    }
}
