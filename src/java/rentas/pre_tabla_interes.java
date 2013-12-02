/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.*;
import javax.faces.event.AjaxBehaviorEvent;
import sistema.*;

/**
 *
 * @author HP
 */
public class pre_tabla_interes extends Pantalla {

   // private Utilitario utilitario = new Utilitario();
    private Tabla tab_tabla = new Tabla();
    //private Barra bar_botones = new Barra();
    private Division div_division = new Division();
    //private Grupo gru_pantalla = new Grupo();
    private Boton bot_interes = new Boton();
    private Consulta con_interes = new Consulta();

    public pre_tabla_interes() {
        bar_botones.getBot_insertar().setUpdate("tab_tabla");
        bar_botones.getBot_guardar().setUpdate("tab_tabla");
        bar_botones.getBot_eliminar().setUpdate("tab_tabla");

        bar_botones.getBot_guardar().setTitle("Guardar y Generar Tabla de Intereses Acumulada");

        //configuracion de la tabla de intereses
        tab_tabla.setId("tab_tabla");
        tab_tabla.setTabla("rec_intereses", "ide_interes", 1);
        tab_tabla.setCampoOrden("ide_interes");
        tab_tabla.getColumna("num_meses").setLectura(true);
        tab_tabla.getColumna("fec_inicio").setMetodoChange("configurar_num_meses");
        tab_tabla.getColumna("fec_final").setMetodoChange("configurar_num_meses");
        tab_tabla.setRows(20);
        tab_tabla.setRecuperarLectura(true);
        tab_tabla.getColumna("fec_inicio").setControl("Texto");
        tab_tabla.getColumna("fec_final").setControl("Texto");
        tab_tabla.setCampoOrden("ide_interes desc");
        tab_tabla.dibujar();

        bot_interes.setValue("Interes Acumulado");
        bot_interes.setMetodo("abrir_interes");
        bot_interes.setUpdate("con_interes");
        bar_botones.agregarBoton(bot_interes);

        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        div_division.setId("div_division");
        div_division.dividir1(pat_panel);

        //gru_pantalla.getChildren().add(bar_botones);
      agregarComponente(div_division);

        con_interes.setId("con_interes");
        con_interes.setTitle("TABLA DE INTERES ACUMULADO");
        con_interes.setHeight("85%");
        con_interes.setConsulta("SELECT ano,per_descripcion,valor_acum FROM rec_interes_acumulado,cont_periodo_actual where  rec_interes_acumulado.ide_periodo=cont_periodo_actual.ide_periodo order by ano desc,rec_interes_acumulado.ide_periodo desc", "valor_acum");
        con_interes.getBot_aceptar().setRendered(false);
        con_interes.getTab_consulta_dialogo().setRows(25);

        agregarComponente(con_interes);
      
    }

    public void abrir_interes() {
        con_interes.dibujar();
    }

    public void generar_interes_acumulado() {

        String str_borra = "delete from rec_interes_acumulado";
        utilitario.getConexion().ejecutar(str_borra);
        String sql = "SELECT ide_interes, num_meses, interes_men, fec_final  FROM rec_intereses order by ide_interes desc";
        Tabla aux_rec_interes = new Tabla();
        aux_rec_interes.setSql(sql);
        aux_rec_interes.ejecutarSql();
        double dou_acumula_interes = 0;
        for (int i = 0; i < aux_rec_interes.getTotalFilas(); i++) {
            int num_meses = Integer.parseInt(aux_rec_interes.getValor(i, "num_meses"));
            int int_num_mes = utilitario.getMes(aux_rec_interes.getValor(i, "fec_final"));
            int int_num_ano = utilitario.getAño(aux_rec_interes.getValor(i, "fec_final"));
            if (num_meses == 1) {
                dou_acumula_interes += Double.parseDouble(aux_rec_interes.getValor(i, "interes_men"));
                String str_sql = "INSERT INTO rec_interes_acumulado values(" + int_num_mes + "," + int_num_ano + "," + dou_acumula_interes + ")";
                utilitario.getConexion().ejecutar(str_sql);
            } else {
                int aux_mes = int_num_mes;
                int aux_ano = int_num_ano;
                for (int j = 0; j < num_meses; j++) {
                    dou_acumula_interes += Double.parseDouble(aux_rec_interes.getValor(i, "interes_men"));
                    if (aux_mes == 0) {
                        aux_mes = 12;
                        aux_ano--;
                    }
                    String str_sql = "INSERT INTO rec_interes_acumulado values(" + aux_mes + "," + aux_ano + "," + dou_acumula_interes + ")";
                    utilitario.getConexion().ejecutar(str_sql);
                    aux_mes--;
                }
            }
        }
        utilitario.getConexion().commit();
        con_interes.getTab_consulta_dialogo().ejecutarSql();
    }

    public void configurar_num_meses(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        try {
            String fecha_inicial = tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "fec_inicio");
            String fecha_final = tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "fec_final");
            int num_meses = utilitario.getMes(fecha_final) - utilitario.getMes(fecha_inicial);

            num_meses += 1;

            tab_tabla.setValor(tab_tabla.getUltimaFilaModificada(), "num_meses", "" + num_meses);
            utilitario.addUpdateTabla(tab_tabla, "num_meses", "");
        } catch (Exception e) {
        }

    }
@Override
    public void insertar() {
        tab_tabla.insertar();
    }
@Override
    public void guardar() {

        tab_tabla.guardar();
        utilitario.getConexion().guardarPantalla();
        generar_interes_acumulado();

    }
@Override
    public void eliminar() {
        tab_tabla.eliminar();
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }
/*
    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }*/

    public Consulta getCon_interes() {
        return con_interes;
    }

    public void setCon_interes(Consulta con_interes) {
        this.con_interes = con_interes;
    }
}
