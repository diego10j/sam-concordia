/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import framework.Division;
import framework.PanelTabla;
import framework.Tabla;
import framework.Tabulador;
/**
 *
 * @author Diego
 */
public class pre_permisos extends Pantalla {

    private Tabla tab_tabla1 = new Tabla();
    private Tabla tab_tabla2 = new Tabla();
    private Tabla tab_tabla3 = new Tabla();
    private Tabla tab_tabla4 = new Tabla();
    private Tabla tab_tabla5 = new Tabla();
    private Division div_division = new Division();
    private Tabulador tab_tabulador = new Tabulador();

    public pre_permisos() {
        tab_tabulador.setId("tab_tabulador");

        tab_tabla1.setId("tab_tabla1");
        tab_tabla1.setTabla("SIS_PERFIL", "IDE_PERF", 1);
        tab_tabla1.agregarRelacion(tab_tabla2);
        tab_tabla1.agregarRelacion(tab_tabla3);
        tab_tabla1.agregarRelacion(tab_tabla4);
        tab_tabla1.agregarRelacion(tab_tabla5);
        tab_tabla1.setRows(20);
        tab_tabla1.dibujar();
        PanelTabla pat_panel1 = new PanelTabla();
        pat_panel1.setPanelTabla(tab_tabla1);

        tab_tabla2.setId("tab_tabla2");
        tab_tabla2.setIdCompleto("tab_tabulador:tab_tabla2");
        tab_tabla2.setTabla("SIS_PERFIL_REPORTE", "IDE_PERE", 2);
        tab_tabla2.getColumna("IDE_REPO").setCombo("select repo.ide_repo,nom_repo,nom_opci from sis_reporte repo inner join sis_opcion opcion on repo.ide_opci=opcion.ide_opci order by nom_repo,nom_opci");
        tab_tabla2.getColumna("IDE_REPO").setAutoCompletar();
        tab_tabla2.setRows(20);
        tab_tabla2.dibujar();
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_tabla2);

        tab_tabla3.setId("tab_tabla3");
        tab_tabla3.setIdCompleto("tab_tabulador:tab_tabla3");
        tab_tabla3.setTabla("SIS_PERFIL_OPCION", "IDE_PEOP", 3);
        tab_tabla3.getColumna("IDE_OPCI").setCombo("SIS_OPCION", "IDE_OPCI", "NOM_OPCI", "");
        tab_tabla3.getColumna("IDE_OPCI").setAutoCompletar();
        tab_tabla3.setRows(20);
        tab_tabla3.dibujar();
        PanelTabla pat_panel3 = new PanelTabla();
        pat_panel3.setPanelTabla(tab_tabla3);

        tab_tabla4.setId("tab_tabla4");
        tab_tabla4.setIdCompleto("tab_tabulador:tab_tabla4");
        tab_tabla4.setTabla("SIS_PERFIL_OBJETO", "IDE_PEOB", 4);
        tab_tabla4.getColumna("IDE_OBOP").setCombo("SELECT IDE_OBOP,NOM_OBOP,NOM_OPCI FROM SIS_OBJETO_OPCION,SIS_OPCION WHERE SIS_OPCION.IDE_OPCI = SIS_OBJETO_OPCION.IDE_OPCI ORDER BY NOM_OPCI,NOM_OBOP");
        tab_tabla4.getColumna("IDE_OBOP").setAutoCompletar();
        tab_tabla4.getColumna("VISIBLE_PEOB").setValorDefecto("true");
        tab_tabla4.setRows(20);
        tab_tabla4.dibujar();
        PanelTabla pat_panel4 = new PanelTabla();
        pat_panel4.setPanelTabla(tab_tabla4);

        tab_tabla5.setId("tab_tabla5");
        tab_tabla5.setIdCompleto("tab_tabulador:tab_tabla5");
        tab_tabla5.setTabla("SIS_PERFIL_CAMPO", "IDE_PECA", 5);
        tab_tabla5.getColumna("IDE_CAMP").setCombo("SELECT IDE_CAMP,NOM_CAMP,TABLA_TABL FROM SIS_CAMPO,SIS_TABLA WHERE SIS_CAMPO.IDE_TABL = SIS_TABLA.IDE_TABL ORDER BY TABLA_TABL,NOM_CAMP");
        tab_tabla5.getColumna("IDE_CAMP").setAutoCompletar();
        tab_tabla5.getColumna("VISIBLE_PECA").setValorDefecto("true");
        tab_tabla5.setRows(20);
        tab_tabla5.dibujar();
        PanelTabla pat_panel5 = new PanelTabla();
        pat_panel5.setPanelTabla(tab_tabla5);

        tab_tabulador.agregarTab("OPCIONES", pat_panel3);
        tab_tabulador.agregarTab("REPORTES", pat_panel2);
        tab_tabulador.agregarTab("OBJETOS COMPONENTES", pat_panel4);
        tab_tabulador.agregarTab("CAMPOS", pat_panel5);

        div_division.setId("div_division");
        div_division.dividir2(pat_panel1, tab_tabulador, "30%", "H");
        agregarComponente(div_division);
    }

    @Override
    public void insertar() {
        utilitario.getTablaisFocus().insertar();
    }

    @Override
    public void guardar() {
        tab_tabla1.guardar();
        tab_tabla2.guardar();
        tab_tabla3.guardar();
        tab_tabla4.guardar();
        tab_tabla5.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
    }

    public Tabla getTab_tabla5() {
        return tab_tabla5;
    }

    public void setTab_tabla5(Tabla tab_tabla5) {
        this.tab_tabla5 = tab_tabla5;
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

    public Tabla getTab_tabla3() {
        return tab_tabla3;
    }

    public void setTab_tabla3(Tabla tab_tabla3) {
        this.tab_tabla3 = tab_tabla3;
    }

    public Tabla getTab_tabla4() {
        return tab_tabla4;
    }

    public void setTab_tabla4(Tabla tab_tabla4) {
        this.tab_tabla4 = tab_tabla4;
    }
}
