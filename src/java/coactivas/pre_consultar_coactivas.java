/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coactivas;

import framework.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sistema.Pantalla;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author user
 */
public class pre_consultar_coactivas extends Pantalla {

    // private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    private Tabla tab_tabla3 = new Tabla();
    // private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    // private Grupo gru_pantalla = new Grupo();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private String cliente_actual = "-1";
    private String ide_empleado;
    private Boton bot_limpiar = new Boton();
    private Boton bot_coactivar = new Boton();
    private Dialogo dia_coactivar = new Dialogo();
    private int num_coact;
    private VisualizarPDF vp = new VisualizarPDF();

    public pre_consultar_coactivas() {

        ide_empleado = obtener_ide_empleado();
        /*  bar_botones.getBot_insertar().setUpdate("tab_tabla1,tab_tabla2");
         bar_botones.getBot_guardar().setUpdate("tab_tabla1,tab_tabla2");
         bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2");*/

        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("aut_filtro_cliente,bot_coactivar,tab_tabla1,tab_tabla2");

        Espacio esp = new Espacio();
        esp.setHeight("0");
        esp.setWidth("5");

        Espacio esp1 = new Espacio();
        esp1.setHeight("0");
        esp1.setWidth("15");

        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
        aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "bot_coactivar,tab_tabla1,tab_tabla2");
        bar_botones.agregarComponente(aut_filtro_cliente);
        bar_botones.agregarComponente(esp);
        bar_botones.agregarBoton(bot_limpiar);


        bot_coactivar.setId("bot_coactivar");
        bot_coactivar.setTitle("Coactivar");
        bot_coactivar.setValue("Coactivar");
        bot_coactivar.setMetodo("coactivar");
        bot_coactivar.setUpdate("dia_coactivar");
        bot_coactivar.setValueExpression("disabled", "pre_index.clase.tab_tabla1.totalFilas==0");


        bar_botones.agregarComponente(esp1);
        bar_botones.agregarComponente(bot_coactivar);
        agregarComponente(dia_coactivar);


        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setSql("select co.ide_coactiva,co.rec_ide_coactiva,ge.nombres||'-'||tg.detalle as gestor,e.detalle as estado,cl.nombre as cliente,em.nombres as empleado, "
                + "co.nro_documento,co.fecha_coactiva,co.valor,case when co.tipo=1 then 'NOTIFICACION' WHEN co.tipo=2 then 'CITACION' END as tipo,co.numero_coactiva,co.observaciones "
                + "from rec_coactivas co,rec_gestor ge,rec_tipo_gestor tg, rec_estados e,rec_clientes cl,munc_empleados em "
                + "where co.ide_cliente=" + cliente_actual + " "
                + "and tg.ide_tipo_gestor=ge.ide_tipo_gestor "
                + "and e.coactivas=TRUE "
                + "and cl.ide_cliente=" + cliente_actual + " "
                + "and em.ide_empleado=" + ide_empleado + " order by co.ide_coactiva desc");
        tab_tabla1.setCampoPrimaria("ide_coactiva");
        tab_tabla1.setNumeroTabla(1);
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
        tab_tabla2.setRows(11);
        tab_tabla2.dibujar();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla2);

        div_division.setId("div_division");
        div_division.dividir2(pat_panel1, pat_panel2, "50%", "H");

     //   gru_pantalla.getChildren().add(bar_botones);
      //  gru_pantalla.getChildren().add(div_division);
        agregarComponente(div_division);

        tab_tabla3.setId("tab_tabla3");
        tab_tabla3.setTabla("rec_coactivas", "ide_coactiva", 3);
        tab_tabla3.getColumna("ide_cliente").setVisible(true);
        tab_tabla3.getColumna("ide_empleado").setVisible(true);
        tab_tabla3.getColumna("ide_gestor").setCombo("select ide_gestor,nombres,detalle from rec_gestor ge,rec_tipo_gestor tg WHERE ge.ide_tipo_gestor=tg.ide_tipo_gestor");
        tab_tabla3.getColumna("ide_estado").setCombo("rec_estados", "ide_estado", "detalle", "coactivas is true");
        tab_tabla3.getColumna("ide_empleado").setValorDefecto(ide_empleado);
        tab_tabla3.getColumna("ide_empleado").setVisible(false);
        tab_tabla3.getColumna("ide_cliente").setVisible(false);
        tab_tabla3.getColumna("valor").setLectura(true);
        tab_tabla3.getColumna("numero_coactiva").setLectura(true);
        tab_tabla3.getColumna("ide_estado").setPermitirNullCombo(false);
        tab_tabla3.getColumna("ide_gestor").setPermitirNullCombo(false);
        tab_tabla3.getColumna("tipo").setLectura(true);
        tab_tabla3.getColumna("tipo").setCombo(getListatTipo());
        tab_tabla3.getColumna("fecha_coactiva").setValorDefecto(utilitario.getFechaActual());
        tab_tabla3.getColumna("rec_ide_coactiva").setVisible(false);
        tab_tabla3.setTipoFormulario(true);
        tab_tabla3.setRecuperarLectura(true);
        tab_tabla3.getGrid().setColumns(4);

        tab_tabla3.dibujar();

        PanelTabla pat_panel3 = new PanelTabla();
        pat_panel3.setPanelTabla(tab_tabla3);

        dia_coactivar.setId("dia_coactivar");
        dia_coactivar.setWidth("80%");
        dia_coactivar.setHeight("50%");

        Grid grid_matriz = new Grid();
        grid_matriz.setColumns(1);
        grid_matriz.setStyle("width:" + (dia_coactivar.getAnchoPanel() - 5) + "px; height:" + dia_coactivar.getAltoPanel() + "px;overflow:auto;display:block;");
        Etiqueta eti = new Etiqueta();
        eti.setValue("Empleado");

        grid_matriz.getChildren().add(pat_panel3);

        dia_coactivar.setDialogo(grid_matriz);
        dia_coactivar.getBot_aceptar().setMetodo("aceptar_coactivar");
        dia_coactivar.getBot_aceptar().setUpdate("dia_coactivar");


        vp.setId("vp");
        vp.getBot_cancelar().setMetodo("cerrar_visualizador");
        vp.getBot_cancelar().setUpdate("vp,bot_coactivar");
        agregarComponente(vp);


    }

    public void cerrar_visualizador() {
        vp.cerrar();

    }

    public List getListatTipo() {

        List lista = new ArrayList();
        Object fila1[] = {
            "1", "NOTIFICACION"
        };
        Object fila2[] = {
            "2", "CITACION"
        };
        lista.add(fila1);
        lista.add(fila2);
        return lista;
    }

    public void coactivar() {
        String ide_coac = "";
        List lis_ide_coa = utilitario.getConexion().consultar("select rec_ide_coactiva from rec_coactivas where ide_coactiva=" + tab_tabla1.getValor("ide_coactiva"));
        if (lis_ide_coa.get(0) == null) {
            ide_coac = tab_tabla1.getValor("ide_coactiva");
            System.out.println("ide coac " + ide_coac);
        } else {
            ide_coac = String.valueOf(lis_ide_coa.get(0));
            System.out.println("ide coac " + ide_coac);
        }

        List lis_num_coac = utilitario.getConexion().consultar("select ide_coactiva from rec_coactivas where rec_ide_coactiva=" + ide_coac);
        System.out.println("size " + lis_num_coac.size());
        num_coact = lis_num_coac.size() + 2;

//        int num_coact=Integer.parseInt(tab_tabla1.getValor("numero_coactiva"))+1;
        if (num_coact <= 3 && num_coact > 1) {
            tab_tabla3.getColumna("tipo").setValorDefecto(1 + "");
        } else {
            tab_tabla3.getColumna("tipo").setValorDefecto(2 + "");
        }

        if (num_coact < 5) {
            System.out.println("num_coac " + num_coact);
            tab_tabla3.getColumna("rec_ide_coactiva").setValorDefecto(ide_coac);
            tab_tabla3.getColumna("valor").setValorDefecto(tab_tabla1.getValor("valor"));
            tab_tabla3.getColumna("ide_cliente").setValorDefecto(cliente_actual);
            tab_tabla3.getColumna("numero_coactiva").setValorDefecto(num_coact + "");
            dia_coactivar.setTitle("COACTIVAS     Nombre Cliente (" + tab_tabla1.getValor("cliente") + " ) Valor a Coactivar (" + tab_tabla1.getValor("valor") + ")");
            tab_tabla3.insertar();
            dia_coactivar.dibujar();
            utilitario.addUpdate("dia_coactivar");
        } else {
            utilitario.agregarNotificacionInfo("Coactivas", "Ya no se puede Coactivar, la coactiva ya tiene un Estado de Citacion  ");
        }
//
//        List list_numero_coact = utilitario.getConexion().consultar("select ide_coactiva from rec_coactivas where ide_cliente=" + cliente_actual);
//        if (!list_numero_coact.isEmpty()) {
//            System.out.println("size " + list_numero_coact.size());
//            if (list_numero_coact.size() < 2) {
//                tab_tabla3.getColumna("tipo").setValorDefecto(1 + "");
//            } else {
//                tab_tabla3.getColumna("tipo").setValorDefecto(2 + "");
//            }
//
//            tab_tabla3.getColumna("valor").setValorDefecto(tab_tabla1.getValor("valor"));
//            tab_tabla3.getColumna("ide_cliente").setValorDefecto(cliente_actual);
//            tab_tabla3.getColumna("numero_coactiva").setValorDefecto((list_numero_coact.size() + 1) + "");
//            dia_coactivar.setTitle("COACTIVAS     Nombre Cliente (" + tab_tabla1.getValor("cliente") + " ) Valor a Coactivar (" + tab_tabla1.getValor("valor") + ")");
//            tab_tabla3.insertar();
//            dia_coactivar.dibujar();
//            utilitario.addUpdate("dia_coactivar");
//        }
    }

    public void aceptar_coactivar() {
        Tabla tab_rec_detalle_coactiva = new Tabla();
        tab_rec_detalle_coactiva.setTabla("rec_coactiva_detalle", "ide_coactiva_detalle", 4);
        tab_rec_detalle_coactiva.setCondicion("ide_coactiva_detalle=-1");
        tab_rec_detalle_coactiva.ejecutarSql();

        tab_tabla3.guardar();

        for (int i = 0; i < tab_tabla2.getTotalFilas(); i++) {
            tab_rec_detalle_coactiva.insertar();
            tab_rec_detalle_coactiva.setValor("ide_coactiva", tab_tabla3.getValor("ide_coactiva"));
            tab_rec_detalle_coactiva.setValor("ide_titulo", tab_tabla2.getValor(i, "ide_titulo"));
            tab_rec_detalle_coactiva.setValor("ide_estado", tab_tabla3.getValor("ide_estado"));
            tab_rec_detalle_coactiva.setValor("valor", tab_tabla2.getValor(i, "valor"));

        }
        tab_rec_detalle_coactiva.guardar();


        utilitario.getConexion().guardarPantalla();

        dia_coactivar.cerrar();
        tab_tabla1.ejecutarSql();
        utilitario.addUpdate("tab_tabla1");

        Map parametros = new HashMap();

        if (num_coact < 4) {
            parametros.put("num_notificacion", num_coact + "");
            parametros.put("ide_cliente", Integer.parseInt(cliente_actual));
            parametros.put("fecha", utilitario.getFecha(utilitario.getFechaActual()));
            parametros.put("fecha_coactiva", tab_tabla3.getValor("fecha_coactiva"));
            vp.setVisualizarPDF("rep_coactivas/notificacion.jasper", parametros);
            vp.dibujar();
            utilitario.addUpdate("vp");
        }

    }

    public void limpiar() {
        tab_tabla1.limpiar();
        tab_tabla2.limpiar();
        aut_filtro_cliente.limpiar();
    }

    public String obtener_ide_empleado() {
        String ide_empl = "-1";
        List list_sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
            ide_empl = String.valueOf(list_sql1.get(0));
        }
        return ide_empl;
    }

    public void filtrar_por_cliente(SelectEvent evt) {
        aut_filtro_cliente.onSelect(evt);
        cliente_actual = aut_filtro_cliente.getValor();
        tab_tabla1.setSql("select co.ide_coactiva,co.rec_ide_coactiva,ge.nombres||'-'||tg.detalle as gestor,e.detalle as estado,cl.nombre as cliente,em.nombres as empleado,"
                + "co.nro_documento,co.fecha_coactiva,co.valor,case when co.tipo=1 then 'NOTIFICACION' WHEN co.tipo=2 then 'CITACION' END as tipo,co.numero_coactiva,co.observaciones "
                + "from rec_coactivas co,rec_gestor ge,rec_tipo_gestor tg, rec_estados e,rec_clientes cl,munc_empleados em "
                + "where co.ide_cliente=" + cliente_actual + " "
                + "and tg.ide_tipo_gestor=ge.ide_tipo_gestor "
                + "and co.ide_gestor = ge.ide_gestor " // vita la duplicidad al comprar los datos de la coactiva con los de la tabla de gesto
                + "and e.coactivas=TRUE "
                + "and cl.ide_cliente=" + cliente_actual + " "
                + "and em.ide_empleado=" + ide_empleado + " order by co.ide_coactiva desc");
        tab_tabla1.ejecutarSql();

        if (tab_tabla1.getTotalFilas() == 0) {
            utilitario.agregarNotificacionInfo("Consulta de Coactivas", "El Cliente No Tiene Coactivas ");
        }

    }
@Override
    public void insertar() {
    }
@Override
    public void guardar() {
        tab_tabla1.guardar();
        tab_tabla2.guardar();
        utilitario.getConexion().guardarPantalla();
    }
@Override
    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
    }

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla1.seleccionarFila(evt);
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
*/
    public AutoCompletar getAut_filtro_cliente() {
        return aut_filtro_cliente;
    }

    public void setAut_filtro_cliente(AutoCompletar aut_filtro_cliente) {
        this.aut_filtro_cliente = aut_filtro_cliente;
    }

    public Dialogo getDia_coactivar() {
        return dia_coactivar;
    }

    public void setDia_coactivar(Dialogo dia_coactivar) {
        this.dia_coactivar = dia_coactivar;
    }

    public Tabla getTab_tabla3() {
        return tab_tabla3;
    }

    public void setTab_tabla3(Tabla tab_tabla3) {
        this.tab_tabla3 = tab_tabla3;
    }
}
