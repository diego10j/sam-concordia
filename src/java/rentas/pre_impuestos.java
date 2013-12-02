/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import sistema.Pantalla;

/**
 *
 * @author HP
 */
public class pre_impuestos extends Pantalla{

  //  private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
   // private Barra bar_botones = new Barra();
    private Division div_division = new Division();
   // private Grupo gru_pantalla = new Grupo();
    private AutoCompletar aut_filtro_cliente = new AutoCompletar();
    private String cliente_actual = "-1";
    private double idou_total = 0;
    private Etiqueta total_valores = new Etiqueta();
    private MarcaAgua maa_marca = new MarcaAgua();
    private Boton bot_limpiar = new Boton();
    private String p_estado_activo = "1";

    public pre_impuestos() {
        bar_botones.getBot_insertar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.getBot_guardar().setUpdate("tab_tabla1,tab_tabla2");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla1,tab_tabla2");

        bar_botones.getBot_inicio().setMetodo("inicio");
        bar_botones.getBot_siguiente().setMetodo("siguiente");
        bar_botones.getBot_atras().setMetodo("atras");
        bar_botones.getBot_fin().setMetodo("fin");

        aut_filtro_cliente.setId("aut_filtro_cliente");
        aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
        aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_tabla1,tab_tabla2");
        aut_filtro_cliente.setSize(100);
        bar_botones.agregarComponente(aut_filtro_cliente);

        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("tab_tabla1,tab_tabla2,aut_filtro_cliente");
        bar_botones.agregarComponente(bot_limpiar);

        maa_marca.setFor("aut_filtro_cliente");
        maa_marca.setValue("Buscar Contribuyente");
        agregarComponente(maa_marca);


        //configuracion de la tabla de valores o titulos (cabecera)
        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("rec_valores", "ide_titulo", 1);
        tab_tabla1.getColumna("ide_cliente").setVisible(false);
        tab_tabla1.getColumna("ide_concepto").setCombo("rec_concepto", "ide_concepto", "des_concepto", "");
        tab_tabla1.getColumna("ide_concepto").setAutoCompletar();
        tab_tabla1.setCondicion("ide_cliente = " + cliente_actual);
        tab_tabla1.setCampoOrden("ide_titulo");
        tab_tabla1.setTipoFormulario(true);
        tab_tabla1.getGrid().setColumns(4);
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.getColumna("ide_estado").setCombo("rec_estados", "ide_estado", "detalle", "rentas=true");
        tab_tabla1.getColumna("ide_estado").setValorDefecto("1");
        tab_tabla1.getColumna("ide_empleado").setCombo("SELECT IDE_EMPLEADO,NOMBRES FROM MUNC_EMPLEADOS WHERE IDE_EMPLEADO= (SELECT IDE_EMPLEADO FROM SIS_USUARIO WHERE IDE_USUA=" + utilitario.getVariable("ide_usua") + ")");
        tab_tabla1.getColumna("ide_empleado").setLectura(true);
        tab_tabla1.getColumna("ide_concepto").setMetodoChange("seleccionarConcepto");
        List lis = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (lis.get(0) != null) {
            tab_tabla1.getColumna("ide_empleado").setValorDefecto(lis.get(0) + "");
        }
        tab_tabla1.getColumna("ide_exoneracion").setVisible(false);
        tab_tabla1.getColumna("IDE_REGISTRO_PAGO").setVisible(false);
        tab_tabla1.getColumna("fecha_control").setVisible(false);
        tab_tabla1.getColumna("tipo").setVisible(false);
        tab_tabla1.getColumna("CODIGO").setVisible(false);
        tab_tabla1.getColumna("NUM_TITULO").setLectura(true);
        tab_tabla1.getColumna("fecha_control").setValorDefecto(utilitario.getFechaActual());
        tab_tabla1.getColumna("FECHA_EMISION").setValorDefecto(utilitario.getFechaActual());
        tab_tabla1.getColumna("FECHA_TITULO").setValorDefecto(utilitario.getFechaActual());
        tab_tabla1.dibujar();
        
        //configuracion de la tabla detalle del titulo (detalle)
        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setTabla("rec_valor_detalle", "ide_valdet", 2);
        tab_tabla2.setGenerarPrimaria(false);
        tab_tabla2.getColumna("detalle").setLectura(true);
        tab_tabla2.getColumna("ide_valdet").setEtiqueta();
        tab_tabla2.setCampoOrden("ide_valdet");

        tab_tabla2.getColumna("ide_impuesto").setCombo("rec_impuesto", "ide_impuesto", "des_impuesto,valor,porcentaje", "");
        tab_tabla2.getColumna("ide_impuesto").setAutoCompletar();
        tab_tabla2.getColumna("ide_impuesto").setMetodoChange("seleccionarImpuesto");
        tab_tabla2.getColumna("valor").setMetodoChange("ingresoValor");
        tab_tabla2.dibujar();

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

       // gru_pantalla.getChildren().add(bar_botones);
        agregarComponente(div_division);
       
    }

    public void seleccionarConcepto(SelectEvent evt) {
        tab_tabla1.modificar(evt);
        List lis_deta = utilitario.getConexion().consultar("select des_concepto from rec_concepto where ide_concepto=" + tab_tabla1.getValor("ide_concepto"));
        tab_tabla1.setValor("detalle", lis_deta.get(0) + " " + utilitario.getAño(tab_tabla1.getValor("fecha_emision")));


        List lis_uti = utilitario.getConexion().consultar("select utilidad from rec_concepto where ide_concepto=" + tab_tabla1.getValor("ide_concepto"));
        if (lis_uti.get(0) != null && lis_uti.get(0).toString().equals("true")) {

            activarCamposConcepto(true);
        } else {
            activarCamposConcepto(false);
        }
        utilitario.addUpdateTabla(tab_tabla1, "detalle", "");
    }

    public void seleccionarImpuesto(SelectEvent evt) {
        tab_tabla2.modificar(evt);
        List lis_deta = utilitario.getConexion().consultar("select des_impuesto,valor,porcentaje from rec_impuesto where ide_impuesto=" + tab_tabla2.getValor("ide_impuesto"));
        Object fila[] = (Object[]) lis_deta.get(0);
        tab_tabla2.setValor("detalle", fila[0] + "");
        double dou_valor = 0;
        try {
            dou_valor = Double.parseDouble(tab_tabla1.getValor("VALOR_IMPONIBLE"));
        } catch (Exception e) {
        }
        double dou_v = 0;
        double dou_p = 0;

        try {
            dou_v = Double.parseDouble(fila[1] + "");
        } catch (Exception e) {
        }
        try {
            dou_p = Double.parseDouble(fila[2] + "");
        } catch (Exception e) {
        }
        if (dou_v == 0 && dou_p == 0) {
            tab_tabla2.setValor("valor", "0");
        } else {
            if (dou_v > dou_p) {
                tab_tabla2.setValor("valor", utilitario.getFormatoNumero(dou_v));
            } else {
                dou_p = dou_valor * (dou_p / 100);
                tab_tabla2.setValor("valor", utilitario.getFormatoNumero(dou_p));
            }
        }
        calcula_totales();
        utilitario.addUpdateTabla(tab_tabla2, "detalle,valor", "");
    }
@Override
    public void insertar() {
        if (aut_filtro_cliente.getValor() != null) {
            if (tab_tabla1.isFocus()) {
                if (!tab_tabla1.isFilaInsertada()) {
                    tab_tabla1.insertar();
                    tab_tabla1.setValor("ide_cliente", cliente_actual);
                    tab_tabla1.getColumna("ide_estado").setLectura(false);
                    calcula_totales();
                } else {
                    utilitario.agregarMensaje("No se puede insertar", "Debe grabar el registro actual");
                }

            } else if (tab_tabla2.isFocus()) {
                tab_tabla2.insertar();
            }
        } else {
            utilitario.agregarMensajeInfo("Atención", "Debe seleccionar un contribuyente");
        }

    }
@Override
    public void guardar() {
        boolean esnuevo = tab_tabla1.isFilaInsertada();
        boolean esdetalle_nuevo = false;
        boolean esdetalle_modificado = false;
        boolean esdetalle_eliminado = false;
        if (tab_tabla2.getInsertadas().size() > 0) {
            esdetalle_nuevo = true;
        }
        if (tab_tabla2.getModificadas().size() > 0) {
            esdetalle_modificado = true;
        }

        if (tab_tabla2.getEliminadas().size() > 0) {
            esdetalle_eliminado = true;
        }
        //valida que si es prdio o rural le obligue a escribir la clave catastral
        tab_tabla1.guardar();
        tab_tabla2.guardar();
       
        utilitario.getConexion().agregarSqlPantalla("update tes_ingreso set des_ingreso = detalle from ( select detalle,ide_titulo from rec_valores  where ide_titulo =" + tab_tabla1.getValor("ide_titulo") + ") a where a.ide_titulo = tes_ingreso.ide_titulo");
        if (utilitario.getConexion().guardarPantalla().isEmpty()) {
            if (esnuevo) {
                disparador(tab_tabla1.getValor("ide_titulo"));
            }
            if (esdetalle_nuevo || esdetalle_modificado || esdetalle_eliminado) {
                 utilitario.getConexion().agregarSqlPantalla("UPDATE tes_ingreso SET valor=" + idou_total + " where ide_titulo=" + tab_tabla1.getValor("ide_titulo"));
                disparadorDetalle(tab_tabla1.getValor("ide_titulo"));
            }

            tab_tabla2.limpiar();
            tab_tabla2.ejecutarValorForanea(tab_tabla1.getValorSeleccionado());
            calcula_totales();
        }
    }
@Override
    public void eliminar() {
        if (tab_tabla1.isFocus()) {
            if (tab_tabla1.getValor("ide_estado") != null && tab_tabla1.getValor("ide_estado").equals("1")) {
                tab_tabla1.eliminar();
            }
        } else if (tab_tabla2.isFocus()) {
            if (tab_tabla1.getValor("ide_estado") != null && tab_tabla1.getValor("ide_estado").equals("1")) {
                tab_tabla2.eliminar();
            } else {
                utilitario.agregarMensajeInfo("No se puede Eliminar", "Solo se pueden eliminar detalles cuande el título esta en estado ACTIVO");
            }

        }

        calcula_totales();
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
        if (tab_tabla1.isFocus() && tab_tabla1.getTotalFilas() > 0) {
            List lis_uti = utilitario.getConexion().consultar("select utilidad from rec_concepto where ide_concepto=" + tab_tabla1.getValor("ide_concepto"));
            if (lis_uti != null && !lis_uti.isEmpty()) {
                if (lis_uti.get(0) != null && lis_uti.get(0).toString().equals("true")) {
                    activarCamposConcepto(true);
                } else {
                    activarCamposConcepto(false);
                }
            } else {
                activarCamposConcepto(false);
            }
            activarDetalle();
        }
        calcula_totales();
    }

    public void fin() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.fin();
        }
        if (tab_tabla1.isFocus() && tab_tabla1.getTotalFilas() > 0) {
            List lis_uti = utilitario.getConexion().consultar("select utilidad from rec_concepto where ide_concepto=" + tab_tabla1.getValor("ide_concepto"));
            if (lis_uti != null && !lis_uti.isEmpty()) {
                if (lis_uti.get(0) != null && lis_uti.get(0).toString().equals("true")) {
                    activarCamposConcepto(true);
                } else {
                    activarCamposConcepto(false);
                }
            } else {
                activarCamposConcepto(false);
            }
            activarDetalle();
        }
        calcula_totales();
    }

    public void siguiente() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.siguiente();
        }
        if (tab_tabla1.isFocus() && tab_tabla1.getTotalFilas() > 0) {
            List lis_uti = utilitario.getConexion().consultar("select utilidad from rec_concepto where ide_concepto=" + tab_tabla1.getValor("ide_concepto"));
            if (lis_uti != null && !lis_uti.isEmpty()) {
                if (lis_uti.get(0) != null && lis_uti.get(0).toString().equals("true")) {
                    activarCamposConcepto(true);
                } else {
                    activarCamposConcepto(false);
                }
            } else {
                activarCamposConcepto(false);
            }
            activarDetalle();
        }
        calcula_totales();
    }

    public void atras() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.atras();
        }
        if (tab_tabla1.isFocus() && tab_tabla1.getTotalFilas() > 0) {
            List lis_uti = utilitario.getConexion().consultar("select utilidad from rec_concepto where ide_concepto=" + tab_tabla1.getValor("ide_concepto"));
            if (lis_uti != null && !lis_uti.isEmpty()) {
                if (lis_uti.get(0) != null && lis_uti.get(0).toString().equals("true")) {
                    activarCamposConcepto(true);
                } else {
                    activarCamposConcepto(false);
                }
            } else {
                activarCamposConcepto(false);
            }
            activarDetalle();
        }
        calcula_totales();
    }

    public void disparador(String ide_titulo) {
        String sql = "insert into rec_valor_detalle (ide_titulo,detalle,valor,ide_impuesto) select ide_titulo,des_impuesto,valor - (valor*por_exonera/100) as nuevo,ide_impuesto "
                + "from ( select a.ide_concepto,exoneracion,(case when porcentaje_exonera is null then 0 else porcentaje_exonera end) as por_exonera,ide_titulo,des_impuesto,(case when porcentaje > 0 then  "
                + "(case when urbano = 't' then ( case when imp_urbano ='t' then (case when valor_imponible  <= base_urbano then 0 else (porcentaje * valor_imponible/100) end) else (porcentaje * valor_imponible/100) end) "
                + "when rural = 't' then ( case when imp_rural='t' then (case when valor_imponible  <= base_rural then 0 else (porcentaje * valor_imponible/100) end) else (porcentaje * valor_imponible/100) end ) "
                + "else (porcentaje * valor_imponible/100) end ) else valor end ) as valor,c.ide_impuesto "
                + ",valor_imponible from rec_valores a, rec_forma_impuesto b, rec_impuesto c,rec_concepto d "
                + "where a.ide_concepto = b.ide_concepto and a.ide_concepto = d.ide_concepto "
                + "and b.ide_impuesto = c.ide_impuesto and ide_titulo = " + ide_titulo + " order by ide_titulo ) a where (valor - (valor*por_exonera/100)) !=0; "
                + ""
                + " insert into tes_ingreso(ide_titulo,fecha_pago,des_ingreso,valor,nombres,cedula,parroquia,calles,observaciones,ide_estado) "
                + "select a.ide_titulo,fecha_emision,detalle,val,c.nombre,cedula,des_distribucion,(case when calle1 is null then '' else calle1 end)||''||(case when calle2 is null then '' else calle2 end) as calle "
                + ",(case when nombrepredio is null then '' else nombrepredio end)||' NUM: '||(case when numero is null then 'S/N' else numero end)||' '||clave as onservacion,ide_estado "
                + "from rec_valores a,(select sum (valor) as val, ide_titulo from rec_valor_detalle group by ide_titulo ) b,rec_clientes c, ( select ide_predio,des_distribucion,nombre as nombrepredio,numero,clave,calle1,calle2 from sigt_predio a "
                + "left join (select cod_distribucion,des_distribucion from inst_distribucion_politica where ide_tipo_distribucion =4) b "
                + "on cod_parroquia =cod_distribucion )d where a.ide_titulo=b.ide_titulo "
                + "and a.ide_cliente = c.ide_cliente and a.codigo = ide_predio and ide_concepto "
                + "in ( select ide_concepto from rec_concepto where urbano ='t' ) and a.ide_titulo =" + ide_titulo + ";"
                + ""
                + " insert into tes_ingreso(ide_titulo,fecha_pago,des_ingreso,valor,nombres,cedula,parroquia,observaciones,ide_estado) select a.ide_titulo,fecha_emision,detalle,val,c.nombre,cedula,des_distribucion "
                + ",(case when nombrepredio is null then '' else nombrepredio end)||' ACUERDO MINI: '||(case when numero is null then 'S/N' else numero end)||' '||clave as onservacion,ide_estado "
                + "from rec_valores a,(select sum (valor) as val, ide_titulo from rec_valor_detalle group by ide_titulo ) b,rec_clientes c, "
                + "( select ide_predio_rural,des_distribucion,nombre as nombrepredio,acuerdo_ministerial as numero,clave from sigt_predio_rural a  "
                + "left join (select cod_distribucion,des_distribucion from inst_distribucion_politica where ide_tipo_distribucion =4) b "
                + "on cod_parroquia =cod_distribucion )d where a.ide_titulo=b.ide_titulo "
                + "and a.ide_cliente = c.ide_cliente and a.codigo = ide_predio_rural "
                + "and ide_concepto in ( select ide_concepto from rec_concepto where rural ='t' ) "
                + "and a.ide_titulo = " + ide_titulo + ";"
                + ""
                + " insert into tes_ingreso(ide_titulo,fecha_pago,des_ingreso,valor,nombres,cedula,observaciones,ide_estado) "
                + " select a.ide_titulo,fecha_emision,detalle,val,nombre,cedula,(case when clave_catastral is null then '' else clave_catastral end)||' '|| "
                + " (case when edad is null then '' else 'EDAD: '||' '||edad end)||' '|| (case when precio_venta is null then '' else 'PRECIO VENTA: '||' '||precio_venta end) "
                + " ||' '|| (case when precio_compra is null then '' else 'PRECIO COMPRA: '||' '||precio_compra end) as detalle, ide_estado "
                + "from rec_valores a, (select sum(valor) as val,ide_titulo from rec_valor_detalle group by ide_titulo) b, rec_clientes c "
                + " where a.ide_titulo = b.ide_titulo  and a.ide_cliente = c.ide_cliente "
                + " and not ide_concepto in ( "
                + "select ide_concepto from rec_concepto where urbano ='t' "
                + "union select ide_concepto from rec_concepto where rural ='t' ) "
                + "and a.ide_titulo = " + ide_titulo + ";"
                + ""
                + "insert into aux_valores (ide_titulo,ide_impuesto,ide_concepto,ide_estado,ide_cliente,fecha_emision,fecha_vence,fecha_titulo,detalle,valor,valor_aval, "
                + "concepto,clave_catastral,barrios,parroquias,calles,nombre,mul,des,inte,cedula,edad,precio_venta,precio_compra,descuento) "
                + "select a.ide_titulo,b.ide_impuesto,a.ide_concepto,a.ide_estado,a.ide_cliente,fecha_emision,fecha_vence,fecha_titulo,des_impuesto,b.valor,valor_imponible, "
                + "a.detalle,clave_catastral,(case when barrio is null then '' else barrio end),des_distribucion, "
                + "(case when calle1 is null then '' else calle1 end)||''||(case when calle2 is null then '' else calle2 end) as calle,nombre, "
                + "0,0,0,cedula,edad,precio_venta,precio_compra,descuento "
                + "from rec_valores a,rec_valor_detalle b,rec_concepto c, "
                + "( select ide_predio,des_distribucion,barrio,nombre as nombrepredio,numero,clave,calle1,calle2 from sigt_predio a "
                + "left join (select cod_distribucion,des_distribucion from inst_distribucion_politica where ide_tipo_distribucion =4) b "
                + "on cod_parroquia =cod_distribucion "
                + "left join (select cod_distribucion,des_distribucion as barrio,ide_distribucion from inst_distribucion_politica) c "
                + "on a.ide_distribucion =c.ide_distribucion "
                + ")d, rec_clientes e,rec_impuesto f "
                + "where a.ide_titulo = b.ide_titulo "
                + "and a.ide_concepto = c.ide_concepto "
                + "and a.codigo = d.ide_predio "
                + "and a.ide_cliente = e.ide_cliente "
                + "and b.ide_impuesto = f.ide_impuesto and a.ide_concepto in "
                + "(select ide_concepto from rec_concepto where urbano ='t' ) "
                + "and a.ide_titulo = " + ide_titulo + ";"
                + ""
                + " insert into aux_valores (ide_titulo,ide_impuesto,ide_concepto,ide_estado,ide_cliente,fecha_emision,fecha_vence,fecha_titulo,detalle,valor,valor_aval, "
                + "concepto,clave_catastral,barrios,parroquias,nombre,mul,des,inte,cedula,edad,precio_venta,precio_compra,descuento) "
                + "select a.ide_titulo,b.ide_impuesto,a.ide_concepto,a.ide_estado,a.ide_cliente,fecha_emision,fecha_vence,fecha_titulo,des_impuesto,b.valor,valor_imponible, "
                + "a.detalle,clave_catastral,(case when barrio is null then '' else barrio end),des_distribucion,nombre, "
                + "0,0,0,cedula,edad,precio_venta,precio_compra,descuento "
                + "from rec_valores a,rec_valor_detalle b,rec_concepto c, "
                + "( select ide_predio_rural,des_distribucion,barrio,nombre as nombrepredio,acuerdo_ministerial,clave from sigt_predio_rural a "
                + "left join (select cod_distribucion,des_distribucion from inst_distribucion_politica where ide_tipo_distribucion =4) b "
                + "on cod_parroquia =cod_distribucion "
                + "left join (select cod_distribucion,des_distribucion as barrio,ide_distribucion from inst_distribucion_politica) c "
                + "on a.ide_distribucion =c.ide_distribucion )d, rec_clientes e,rec_impuesto f "
                + "where a.ide_titulo = b.ide_titulo and a.ide_concepto = c.ide_concepto "
                + "and a.codigo = d.ide_predio_rural "
                + "and a.ide_cliente = e.ide_cliente and b.ide_impuesto = f.ide_impuesto "
                + "and a.ide_concepto in (select ide_concepto from rec_concepto where rural ='t' ) "
                + "and a.ide_titulo = " + ide_titulo + ";"
                + ""
                + "insert into aux_valores (ide_titulo,ide_impuesto,ide_concepto,ide_estado,ide_cliente,fecha_emision,fecha_vence,fecha_titulo,detalle,valor,valor_aval,"
                + "concepto,nombre,mul,des,inte,cedula,edad,precio_venta,precio_compra,descuento) "
                + " select a.ide_titulo,b.ide_impuesto,a.ide_concepto,ide_estado,a.ide_cliente,fecha_emision,fecha_vence,fecha_titulo,des_impuesto,b.valor,valor_imponible, "
                + "a.detalle,nombre,0,0,0,cedula,edad,precio_venta,precio_compra,descuento "
                + " from rec_valores a,rec_valor_detalle b,rec_impuesto c,rec_clientes d "
                + " where a.ide_titulo = b.ide_titulo "
                + " and b.ide_impuesto = c.ide_impuesto  and a.ide_cliente = d.ide_cliente "
                + " and not ide_concepto in ( select ide_concepto from rec_concepto where urbano ='t' "
                + "union select ide_concepto from rec_concepto where rural ='t' ) and a.ide_titulo =" + ide_titulo + ";";
        if (utilitario.getConexion().ejecutar(sql).isEmpty()) {
            utilitario.getConexion().commit();
        } else {
            System.out.println("Fallo el disparador");
            utilitario.getConexion().rollback();
        }
    }

    public void disparadorDetalle(String ide_titulo) {
        String sql = "update tes_ingreso "
                + "set valor = val from (select (case when sum(valor) is null then 0 else sum(valor) end ) as val,ide_titulo "
                + "from ( select valor,a.ide_titulo from rec_valores a left join rec_valor_detalle b on a.ide_titulo = b.ide_titulo "
                + "where a.ide_titulo =" + ide_titulo + " "
                + "order by b.ide_titulo ) a group by ide_titulo ) a where a.ide_titulo = tes_ingreso.ide_titulo ;"
                + ""
                + " delete from aux_valores where ide_titulo = " + ide_titulo + "; "
                + "insert into aux_valores (ide_titulo,ide_impuesto,ide_concepto,ide_estado,ide_cliente,fecha_emision,fecha_vence,fecha_titulo,detalle,valor,valor_aval,  "
                + "concepto,clave_catastral,barrios,parroquias,calles,nombre,mul,des,inte,cedula,edad,precio_venta,precio_compra,descuento) "
                + "select a.ide_titulo,b.ide_impuesto,a.ide_concepto,a.ide_estado,a.ide_cliente,fecha_emision,fecha_vence,fecha_titulo,des_impuesto,b.valor,valor_imponible, "
                + "a.detalle,clave_catastral,(case when barrio is null then '' else barrio end),des_distribucion, "
                + "(case when calle1 is null then '' else calle1 end)||''||(case when calle2 is null then '' else calle2 end) as calle,nombre, "
                + "0,0,0,cedula,edad,precio_venta,precio_compra,descuento "
                + "from rec_valores a,rec_valor_detalle b,rec_concepto c, "
                + "( select ide_predio,des_distribucion,barrio,nombre as nombrepredio,numero,clave,calle1,calle2 from sigt_predio a "
                + "left join (select cod_distribucion,des_distribucion from inst_distribucion_politica where ide_tipo_distribucion =4) b "
                + "on cod_parroquia =cod_distribucion "
                + "left join (select cod_distribucion,des_distribucion as barrio,ide_distribucion from inst_distribucion_politica) c "
                + "on a.ide_distribucion =c.ide_distribucion "
                + ")d, rec_clientes e,rec_impuesto f "
                + "where a.ide_titulo = b.ide_titulo "
                + "and a.ide_concepto = c.ide_concepto "
                + "and a.codigo = d.ide_predio "
                + "and a.ide_cliente = e.ide_cliente "
                + "and b.ide_impuesto = f.ide_impuesto and a.ide_concepto in "
                + "(select ide_concepto from rec_concepto where urbano ='t' ) "
                + "and a.ide_titulo =" + ide_titulo + " ;"
                + " insert into aux_valores (ide_titulo,ide_impuesto,ide_concepto,ide_estado,ide_cliente,fecha_emision,fecha_vence,fecha_titulo,detalle,valor,valor_aval, "
                + "concepto,clave_catastral,barrios,parroquias,nombre,mul,des,inte,cedula,edad,precio_venta,precio_compra,descuento) "
                + "select a.ide_titulo,b.ide_impuesto,a.ide_concepto,a.ide_estado,a.ide_cliente,fecha_emision,fecha_vence,fecha_titulo,des_impuesto,b.valor,valor_imponible, "
                + "a.detalle,clave_catastral,(case when barrio is null then '' else barrio end),des_distribucion,nombre, "
                + "0,0,0,cedula,edad,precio_venta,precio_compra,descuento "
                + "from rec_valores a,rec_valor_detalle b,rec_concepto c, "
                + "( select ide_predio_rural,des_distribucion,barrio,nombre as nombrepredio,acuerdo_ministerial,clave from sigt_predio_rural a "
                + "left join (select cod_distribucion,des_distribucion from inst_distribucion_politica where ide_tipo_distribucion =4) b "
                + "on cod_parroquia =cod_distribucion "
                + "left join (select cod_distribucion,des_distribucion as barrio,ide_distribucion from inst_distribucion_politica) c "
                + "on a.ide_distribucion =c.ide_distribucion )d, rec_clientes e,rec_impuesto f "
                + "where a.ide_titulo = b.ide_titulo and a.ide_concepto = c.ide_concepto "
                + "and a.codigo = d.ide_predio_rural "
                + "and a.ide_cliente = e.ide_cliente and b.ide_impuesto = f.ide_impuesto "
                + "and a.ide_concepto in (select ide_concepto from rec_concepto where rural ='t' ) "
                + "and a.ide_titulo =" + ide_titulo + "; "
                + "insert into aux_valores (ide_titulo,ide_impuesto,ide_concepto,ide_estado,ide_cliente,fecha_emision,fecha_vence,fecha_titulo,detalle,valor,valor_aval,"
                + "concepto,nombre,mul,des,inte,cedula,edad,precio_venta,precio_compra,descuento) "
                + " select a.ide_titulo,b.ide_impuesto,a.ide_concepto,ide_estado,a.ide_cliente,fecha_emision,fecha_vence,fecha_titulo,des_impuesto,b.valor,valor_imponible, "
                + "a.detalle,nombre,0,0,0,cedula,edad,precio_venta,precio_compra,descuento "
                + " from rec_valores a,rec_valor_detalle b,rec_impuesto c,rec_clientes d "
                + " where a.ide_titulo = b.ide_titulo "
                + " and b.ide_impuesto = c.ide_impuesto  and a.ide_cliente = d.ide_cliente "
                + " and not ide_concepto in ( select ide_concepto from rec_concepto where urbano ='t' "
                + "union select ide_concepto from rec_concepto where rural ='t' ) and a.ide_titulo = " + ide_titulo + " ;";

        if (utilitario.getConexion().ejecutar(sql).isEmpty()) {
            utilitario.getConexion().commit();
        } else {
            System.out.println("Fallo el disparador del detalle");
            utilitario.getConexion().rollback();
        }

    }

    public void limpiar() {
        tab_tabla1.limpiar();
        aut_filtro_cliente.limpiar();
        calcula_totales();
    }

    public void ingresoValor(AjaxBehaviorEvent evt){
        tab_tabla2.modificar(evt);
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
        cliente_actual = aut_filtro_cliente.getValor();
        tab_tabla1.setCondicion("ide_cliente = " + cliente_actual);
        tab_tabla1.ejecutarSql();
        tab_tabla2.ejecutarValorForanea(tab_tabla1.getValorSeleccionado());

        if (tab_tabla1.getTotalFilas() > 0) {
            List lis_uti = utilitario.getConexion().consultar("select utilidad from rec_concepto where ide_concepto=" + tab_tabla1.getValor("ide_concepto"));
            if (lis_uti != null && !lis_uti.isEmpty()) {
                if (lis_uti.get(0) != null && lis_uti.get(0).toString().equals("true")) {
                    activarCamposConcepto(true);
                } else {
                    activarCamposConcepto(false);
                }
            } else {
                activarCamposConcepto(false);
            }
        }
        activarDetalle();
        calcula_totales();
    }

    public void activarCamposConcepto(boolean estado) {
        tab_tabla1.getColumna("edad").setLectura(!estado);
        tab_tabla1.getColumna("precio_venta").setLectura(!estado);
        tab_tabla1.getColumna("precio_compra").setLectura(!estado);
        tab_tabla1.getColumna("descuento").setLectura(!estado);
        //   tab_tabla1.dibujar();
        utilitario.addUpdate("tab_tabla1");
    }

    public void activarDetalle() {
        boolean boo = true;
        if (tab_tabla1.getTotalFilas() > 0) {

            if (tab_tabla1.getValor("ide_estado") != null && tab_tabla1.getValor("ide_estado").equals(p_estado_activo)) {
                boo = false;
            }
            for (int i = 0; i < tab_tabla2.getTotalFilas(); i++) {
                tab_tabla2.getFilas().get(i).setLectura(boo);
            }

            tab_tabla1.getColumna("ide_estado").setLectura(boo);
            //  tab_tabla1.dibujar();
            utilitario.addUpdate("tab_tabla1,tab_tabla2");
        }


    }

    public void buscar() {
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

/*    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }*/
}
