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
import org.primefaces.event.SelectEvent;
import sistema.Pantalla;

/**
*
* @author HP
*/
public class pre_coactivas extends Pantalla {


private Tabla tab_seleccion = new Tabla();
private Tabla tab_tabla1 = new Tabla();
//private Barra bar_botones = new Barra();
private Division div_division = new Division();
//private Grupo gru_pantalla = new Grupo();
private AutoCompletar aut_filtro_cliente = new AutoCompletar();
private Boton bot_limpiar = new Boton();
private Boton bot_coactivar = new Boton();
private String cliente_actual = "-1";
private String ide_estado_emitido = "4"; //estado EMITIDO de la tabla rec_estados
private Etiqueta eti_total_titulos = new Etiqueta();
private Etiqueta eti_valor = new Etiqueta();
private Etiqueta eti_empleado = new Etiqueta();
private Dialogo dia_coactivar = new Dialogo();
private String ide_empleado;
private String nombre_cliente = "";
private String val = "";
private VisualizarPDF vp = new VisualizarPDF();

public pre_coactivas() {

ide_empleado = obtener_ide_empleado();
//bar_botones.getBot_insertar().setUpdate("tab_tabla1");

bot_limpiar.setIcon("ui-icon-cancel");
bot_limpiar.setTitle("Limpiar");
bot_limpiar.setMetodo("limpiar");
bot_limpiar.setUpdate("aut_filtro_cliente,tab_seleccion,grup_titulo");

aut_filtro_cliente.setId("aut_filtro_cliente");
aut_filtro_cliente.setAutoCompletar("select ide_cliente,cedula,nombre from rec_clientes order by nombre");
aut_filtro_cliente.setMetodoChange("filtrar_por_cliente", "tab_seleccion,bot_coactivar,grup_titulo");
bar_botones.agregarComponente(aut_filtro_cliente);
bar_botones.agregarComponente(bot_limpiar);

eti_total_titulos.setStyle("font-size: 13px;font-weight: bold");
eti_valor.setStyle("font-size: 13px;font-weight: bold");
eti_empleado.setStyle("font-size: 13px;font-weight: bold");

bot_coactivar.setId("bot_coactivar");
bot_coactivar.setValue("Coactivar");
bot_coactivar.setMetodo("coactivar");
bot_coactivar.setValueExpression("disabled", "pre_index.clase.tab_seleccion.totalFilas==0");
bot_coactivar.setUpdate("dia_coactivar");

agregarComponente(dia_coactivar);

Espacio esp = new Espacio();
esp.setHeight("0");
esp.setWidth("25");
bar_botones.agregarComponente(esp);

bar_botones.agregarComponente(bot_coactivar);

//configuracion de la tabla de valores o titulos (cabecera)
tab_seleccion.setId("tab_seleccion");

tab_seleccion.setSql("select ide_titulo,des_ingreso,parroquia,calles,observaciones,valor "
+ "from tes_ingreso where ide_titulo in ("
+ "select ide_titulo from rec_valores where ide_estado=1 and ide_cliente=" + cliente_actual + " and fecha_vence<'" + utilitario.getFechaActual() + "')");
tab_seleccion.setCampoPrimaria("ide_titulo");
tab_seleccion.setLectura(true);
tab_seleccion.setCampoOrden("ide_coactiva");
tab_seleccion.setRows(3);
tab_seleccion.dibujar();
PanelTabla pat_panel1 = new PanelTabla();
pat_panel1.setPanelTabla(tab_seleccion);


tab_tabla1.setId("tab_tabla1");
tab_tabla1.setTabla("rec_coactivas", "ide_coactiva", 1);
tab_tabla1.getColumna("ide_cliente").setVisible(true);
tab_tabla1.getColumna("ide_empleado").setVisible(true);
tab_tabla1.getColumna("ide_gestor").setCombo("select ide_gestor,nombres,detalle from rec_gestor ge,rec_tipo_gestor tg WHERE ge.ide_tipo_gestor=tg.ide_tipo_gestor");
tab_tabla1.getColumna("ide_estado").setCombo("rec_estados", "ide_estado", "detalle", "coactivas is true");
tab_tabla1.getColumna("ide_empleado").setValorDefecto(ide_empleado);
tab_tabla1.getColumna("ide_empleado").setVisible(false);
tab_tabla1.getColumna("ide_cliente").setVisible(false);
tab_tabla1.getColumna("valor").setLectura(true);
tab_tabla1.getColumna("numero_coactiva").setLectura(true);
tab_tabla1.getColumna("ide_estado").setPermitirNullCombo(false);
tab_tabla1.getColumna("ide_gestor").setPermitirNullCombo(false);
tab_tabla1.getColumna("tipo").setLectura(true);
tab_tabla1.getColumna("tipo").setCombo(getListatTipo());
tab_tabla1.getColumna("fecha_coactiva").setValorDefecto(utilitario.getFechaActual());
tab_tabla1.getColumna("rec_ide_coactiva").setVisible(false);
tab_tabla1.setTipoFormulario(true);
tab_tabla1.setRecuperarLectura(true);
tab_tabla1.getGrid().setColumns(4);

tab_tabla1.dibujar();

PanelTabla pat_panel2 = new PanelTabla();
pat_panel2.setPanelTabla(tab_tabla1);

Espacio esp1 = new Espacio();
Espacio esp2 = new Espacio();
Grid grup_titulo = new Grid();
grup_titulo.setColumns(2);
grup_titulo.setWidth("100%");
grup_titulo.setId("grup_titulo");
grup_titulo.getChildren().add(eti_empleado);
grup_titulo.getChildren().add(esp2);
grup_titulo.getChildren().add(eti_total_titulos);
grup_titulo.getChildren().add(esp1);
grup_titulo.getChildren().add(eti_valor);

cargar_titulo();



div_division.setId("div_division");
div_division.dividir2(grup_titulo, pat_panel1, "15%", "H");

agregarComponente(div_division);
//gru_pantalla.getChildren().add(bar_botones);
//gru_pantalla.getChildren().add(div_division);

dia_coactivar.setId("dia_coactivar");
dia_coactivar.setWidth("80%");
dia_coactivar.setHeight("50%");

vp.setId("vp");
vp.getBot_cancelar().setMetodo("cerrar_visualizador");
vp.getBot_cancelar().setUpdate("vp,bot_coactivar");
agregarComponente(vp);

Grid grid_matriz = new Grid();
grid_matriz.setColumns(1);
grid_matriz.setStyle("width:" + (dia_coactivar.getAnchoPanel() - 5) + "px; height:" + dia_coactivar.getAltoPanel() + "px;overflow:auto;display:block;");
Etiqueta eti = new Etiqueta();
eti.setValue("Empleado");

grid_matriz.getChildren().add(pat_panel2);

dia_coactivar.setDialogo(grid_matriz);
dia_coactivar.getBot_aceptar().setMetodo("aceptar_coactivar");
dia_coactivar.getBot_aceptar().setUpdate("dia_coactivar");


}

public void aceptar_coactivar() {
Tabla tab_rec_detalle_coactiva = new Tabla();
tab_rec_detalle_coactiva.setTabla("rec_coactiva_detalle", "ide_coactiva_detalle", 2);
tab_rec_detalle_coactiva.setCondicion("ide_coactiva_detalle=-1");
tab_rec_detalle_coactiva.ejecutarSql();
tab_tabla1.guardar();

for (int i = 0; i < tab_seleccion.getTotalFilas(); i++) {
tab_rec_detalle_coactiva.insertar();
tab_rec_detalle_coactiva.setValor("ide_coactiva", tab_tabla1.getValor("ide_coactiva"));
tab_rec_detalle_coactiva.setValor("ide_titulo", tab_seleccion.getValor(i, "ide_titulo"));
tab_rec_detalle_coactiva.setValor("ide_estado", tab_tabla1.getValor("ide_estado"));
tab_rec_detalle_coactiva.setValor("valor", tab_seleccion.getValor(i, "valor"));

}
tab_rec_detalle_coactiva.guardar();
String mensaje = utilitario.getConexion().guardarPantalla();
dia_coactivar.cerrar();

if (mensaje.isEmpty()) {
Map parametros = new HashMap();

parametros.put("num_notificacion", "1");
parametros.put("ide_cliente", Integer.parseInt(cliente_actual));
parametros.put("fecha", utilitario.getFecha(utilitario.getFechaActual()));
parametros.put("fecha_coactiva", tab_tabla1.getValor("fecha_coactiva"));
vp.setVisualizarPDF("rep_coactivas/notificacion.jasper", parametros);
vp.dibujar();
utilitario.addUpdate("vp");
}

}

public void cerrar_visualizador() {
System.out.println("sss");
vp.cerrar();
bot_coactivar.setDisabled(true);

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
@Override
public void insertar() {
}

public void coactivar() {


tab_tabla1.getColumna("numero_coactiva").setValorDefecto(1 + "");
dia_coactivar.setTitle("COACTIVAS Nombre Cliente (" + nombre_cliente + " ) Valor a Coactivar (" + val + ")");
tab_tabla1.insertar();
dia_coactivar.dibujar();
}

public void limpiar() {
tab_seleccion.limpiar();
eti_valor.setValue("Valor a Coactivar: ");
aut_filtro_cliente.limpiar();
}
@Override
public void guardar() {
tab_seleccion.guardar();
utilitario.getConexion().guardarPantalla();
}
@Override
public void eliminar() {
utilitario.getTablaisFocus().eliminar();
}

public void seleccionar_tabla1(SelectEvent evt) {
tab_seleccion.seleccionarFila(evt);
}

public void filtrar_por_cliente(SelectEvent evt) {


aut_filtro_cliente.onSelect(evt);
cliente_actual = aut_filtro_cliente.getValor();

List list_numero_coact = utilitario.getConexion().consultar("select ide_coactiva from rec_coactivas where numero_coactiva=1 and ide_cliente=" + cliente_actual);
List list_consul_coactivar = utilitario.getConexion().consultar("select ide_titulo,des_ingreso,parroquia,calles,observaciones,valor "
+ "from tes_ingreso where ide_titulo in ("
+ "select ide_titulo from rec_valores where ide_estado=1 and ide_cliente=" + cliente_actual + " and fecha_vence<'" + utilitario.getFechaActual() + "')");
if (!list_numero_coact.isEmpty()) {
String ide_coactiva = "";
for (int i = 0; i < list_numero_coact.size(); i++) {
if (list_numero_coact.get(i) != null && !list_numero_coact.isEmpty()) {
if (i > 0) {
ide_coactiva += ",";
}
ide_coactiva += "'" + list_numero_coact.get(i) + "'";
}
}
List list_det_coact = utilitario.getConexion().consultar("select ide_coactiva_detalle from rec_coactiva_detalle where ide_coactiva in (" + ide_coactiva + ")");

if (list_det_coact.size() == list_consul_coactivar.size()) {
utilitario.agregarNotificacionInfo("Consulta de Coactivas", "El Cliente Consultado ya tiene Realizado una Coactiva y No Registra Titulos por Coactivar");
} else {
String ide_coact = String.valueOf(list_det_coact.get(list_det_coact.size() - 1));
int num_tit_coact=0;
if (list_consul_coactivar.size()>list_det_coact.size()) {
num_tit_coact = list_consul_coactivar.size() - list_det_coact.size();
}else{
num_tit_coact = list_det_coact.size()-list_consul_coactivar.size();
}
tab_seleccion.setSql("select ide_titulo,des_ingreso,parroquia,calles,observaciones,valor "
+ "from tes_ingreso where ide_titulo in ("
+ "select ide_titulo from rec_valores where ide_estado=1 and ide_cliente=" + cliente_actual + " and fecha_vence<'" + utilitario.getFechaActual() + "') "
+ "order by ide_ingreso DESC "
+ "limit (" + num_tit_coact + ")");
tab_seleccion.setCondicion("ide_cliente = " + cliente_actual);
tab_seleccion.setCondicionBuscar("");
List nom_cliente = utilitario.getConexion().consultar("select nombre from rec_clientes where ide_cliente=" + cliente_actual);
if (!nom_cliente.isEmpty()) {
nombre_cliente = (String) nom_cliente.get(0);
}
tab_tabla1.getColumna("tipo").setValorDefecto(1 + "");
tab_tabla1.getColumna("ide_cliente").setValorDefecto(cliente_actual);
tab_tabla1.getColumna("numero_coactiva").setValorDefecto(1 + "");
dia_coactivar.setTitle("COACTIVAS Nombre Cliente (" + nombre_cliente + " ) Valor a Coactivar (" + val + ")");

tab_seleccion.ejecutarSql();
}
} else {
if (list_consul_coactivar.size() != 0) {
tab_seleccion.setSql("select ide_titulo,des_ingreso,parroquia,calles,observaciones,valor "
+ "from tes_ingreso where ide_titulo in ("
+ "select ide_titulo from rec_valores where ide_estado=1 and ide_cliente=" + cliente_actual + " and fecha_vence<'" + utilitario.getFechaActual() + "')");
tab_seleccion.setCondicion("ide_cliente = " + cliente_actual);
tab_seleccion.setCondicionBuscar("");
List nom_cliente = utilitario.getConexion().consultar("select nombre from rec_clientes where ide_cliente=" + cliente_actual);
if (!nom_cliente.isEmpty()) {
nombre_cliente = (String) nom_cliente.get(0);
}
tab_tabla1.getColumna("tipo").setValorDefecto(1 + "");
tab_tabla1.getColumna("ide_cliente").setValorDefecto(cliente_actual);
tab_tabla1.getColumna("numero_coactiva").setValorDefecto(1 + "");
dia_coactivar.setTitle("COACTIVAS Nombre Cliente (" + nombre_cliente + " ) Valor a Coactivar (" + val + ")");

tab_seleccion.ejecutarSql();

} else {
utilitario.agregarNotificacionInfo("Consulta de Coactivas", "El Cliente No Tiene Deudas ");
}

}


cargar_titulo();
if (tab_seleccion.getTotalFilas() > 0) {
bot_coactivar.setDisabled(false);
utilitario.addUpdate("bot_coactivar");

}else{
bot_coactivar.setDisabled(true);
utilitario.addUpdate("bot_coactivar");


}

}

public String obtener_ide_empleado() {
String ide_empl = "-1";
List list_sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
ide_empl = String.valueOf(list_sql1.get(0));
}
return ide_empl;
}

public void cargar_titulo() {

String rec = "";
List sql1 = utilitario.getConexion().consultar("SELECT nombres FROM munc_empleados WHERE ide_empleado =(SELECT ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua") + ")");
if (!sql1.isEmpty()) {
rec = (String) sql1.get(0);
}
eti_empleado.setValue("Empleado: " + rec);
float suma = 0;
for (int i = 0; i < tab_seleccion.getTotalFilas(); i++) {
suma = Float.parseFloat(tab_seleccion.getValor(i, "valor")) + suma;
}

if (suma != 0) {
eti_valor.setValue("Valor a Coactivar: " + suma);
val = String.valueOf(suma);
tab_tabla1.getColumna("valor").setValorDefecto(suma + "");
} else {
eti_valor.setValue("Valor a Coactivar: ");
}
eti_total_titulos.setValue("Numero de Titulos a Coactivar: " + tab_seleccion.getTotalFilas());


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
}*/

public Dialogo getDia_coactivar() {
return dia_coactivar;
}

public void setDia_coactivar(Dialogo dia_coactivar) {
this.dia_coactivar = dia_coactivar;
}

public Tabla getTab_tabla1() {
return tab_tabla1;
}

public void setTab_tabla1(Tabla tab_tabla1) {
this.tab_tabla1 = tab_tabla1;
}

public VisualizarPDF getVp() {
return vp;
}

public void setVp(VisualizarPDF vp) {
this.vp = vp;
}
}