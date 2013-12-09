/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avaluos;

import framework.*;
import java.util.*;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.component.fieldset.Fieldset;
import org.primefaces.event.SelectEvent;
import sistema.Pantalla;

/**
 *
 * @author Diego
 */
public class pre_urbana extends Pantalla {

    private Tabla tab_tabla = new Tabla();
    private Division div_division = new Division();
    private Tabulador tab_tabulador = new Tabulador();
    private Tabla tab_topografia = new Tabla();//aux
    private Tabla tab_superficie = new Tabla();//aux
    private Tabla tab_colindantes = new Tabla();//aux
    private Tabla tab_bloque_predio = new Tabla();
    private Lista lis_dominio = new Lista();
    private Lista lis_traslacion = new Lista();
    private Radio rad_escritura = new Radio(); /// si o  no
    private Tabla tab_escritura = new Tabla(); // se muestra si escritura == si
    private Lista lis_escritura = new Lista();
    ///uso suelo
    private Tabla tab_uso1 = new Tabla();
    private Tabla tab_uso2 = new Tabla();
    private Tabla tab_uso3 = new Tabla();
    private Tabla tab_uso4 = new Tabla();
    private Combo com_combo1 = new Combo();
    private Combo com_combo2 = new Combo();
    private Combo com_combo3 = new Combo();
    private Combo com_combo4 = new Combo();
    ///edificaciones
    private ListaSeleccion ls_estructura = new ListaSeleccion();
    private ListaSeleccion ls_estado_conserva = new ListaSeleccion();
    private ListaSeleccion ls_columnas = new ListaSeleccion();
    private ListaSeleccion ls_vigas = new ListaSeleccion();
    private ListaSeleccion ls_entrepisos = new ListaSeleccion();
    private ListaSeleccion ls_paredes = new ListaSeleccion();
    private ListaSeleccion ls_escaleras = new ListaSeleccion();
    private ListaSeleccion ls_cubierta = new ListaSeleccion();
    private Texto tex_edad_construccion = new Texto();
    private Texto tex_reparacion = new Texto();
    private Texto tex_num_pisos = new Texto();
    //pestaña 2
    private ListaSeleccion ls_rev_pisos = new ListaSeleccion();
    private ListaSeleccion ls_rev_interior = new ListaSeleccion();
    private ListaSeleccion ls_rev_exterior = new ListaSeleccion();
    private ListaSeleccion ls_rev_escl = new ListaSeleccion();
    private ListaSeleccion ls_tumbados = new ListaSeleccion();
    private ListaSeleccion ls_cubierta_acabados = new ListaSeleccion();
    private ListaSeleccion ls_puertas = new ListaSeleccion();
    private ListaSeleccion ls_ventanas = new ListaSeleccion();
    private ListaSeleccion ls_cubreventanas = new ListaSeleccion();
    private ListaSeleccion ls_closets = new ListaSeleccion();
    //pestaña 3
    private ListaSeleccion ls_sanitarias = new ListaSeleccion();
    private ListaSeleccion ls_electricas = new ListaSeleccion();
    private ListaSeleccion ls_baños = new ListaSeleccion();
    private Combo com_bloque = new Combo();
    private LinkedList lis_bloques = new LinkedList();
    private LinkedList lis_seleccionados_infra = new LinkedList();
    /////////////////
    ///TERRENOS/INFRA
    private Lista lis_ocupacion = new Lista();
    private Lista lis_carac_suelo = new Lista();
    private Lista lis_topografia = new Lista();
    private Lista lis_localizacion = new Lista();
    private Lista lis_forma = new Lista();
    private Lista lis_uso = new Lista();
    private Lista lis_material = new Lista();
    private ListaSeleccion ls_energia = new ListaSeleccion();
    private ListaSeleccion ls_agua = new ListaSeleccion();
    private Lista lis_alcantarillado = new Lista();
    private ListaSeleccion ls_otros = new ListaSeleccion();
    ////OTAS INVERSIONES
    private Tabla tab_inversiones = new Tabla();
    ///
    private Tabla tab_foto = new Tabla();
    private Tabla tab_croquis = new Tabla();
    private Tabla tab_imagen = new Tabla();
    ///
    private AreaTexto ate_observacion = new AreaTexto();
    private Check che_dime = new Check();
    private Check che_desconoce_propietario = new Check();
    private Check che_otra_informacion = new Check();
    private Radio rad_otros_linderos = new Radio();
    private Check che_nuevo_bloque = new Check();
    private Check che_ampliacion_bloque = new Check();
    private Combo com_combobloques2 = new Combo();
    private Texto txt_nuevo_bloque = new Texto();
    private Reporte rep_reporte = new Reporte();
    private SeleccionFormatoReporte sfr_formato = new SeleccionFormatoReporte();
    //ALICUOTAS 
    private Combo com_tipo_alicuota = new Combo();
    private Radio rad_alicuota = new Radio();
    private Texto tex_terreno_privado = new Texto();
    private Texto tex_edificacion_privado = new Texto();
    private Texto tex_terreno_publico = new Texto();
    private Texto tex_edificacion_publico = new Texto();

    public pre_urbana() {

        bar_botones.agregarReporte();
        tab_tabulador.setId("tab_tabulador");
        PanelTabla pat_panel = new PanelTabla();
        tab_tabulador.agregarTab("ID. PREDIAL I", pat_panel);

        tab_tabla.setId("tab_tabla");
        tab_tabla.setIdCompleto("tab_tabulador:tab_tabla");
        tab_tabla.setTabla("sigt_predio", "ide_predio", 1);
        tab_tabla.setTipoFormulario(true);
        tab_tabla.setBusquedaFormulario(false);
        tab_tabla.getGrid().setColumns(4);
        tab_tabla.setCondicion("ide_predio=-1");
        tab_tabla.getColumna("c_topografica").setVisible(false);
        tab_tabla.getColumna("foto_aerea").setVisible(false);
        tab_tabla.getColumna("otros_cartografia").setVisible(false);
        tab_tabla.getColumna("coordenada_este").setVisible(false);
        tab_tabla.getColumna("coordenada_norte").setVisible(false);
        tab_tabla.getColumna("cod_provincia").setLectura(true);
        tab_tabla.getColumna("cod_parroquia").setLectura(true);
        tab_tabla.getColumna("cod_canton").setLectura(true);


        tab_tabla.getColumna("colindante_norte").setVisible(false);
        tab_tabla.getColumna("colindante_sur").setVisible(false);
        tab_tabla.getColumna("colindante_este").setVisible(false);
        tab_tabla.getColumna("colindante_oeste").setVisible(false);
        tab_tabla.getColumna("area_total_terreno").setVisible(false);
        tab_tabla.getColumna("frente_principal").setVisible(false);
        tab_tabla.getColumna("fondo_relativo").setVisible(false);
        tab_tabla.getColumna("frente_fondo").setVisible(false);
        tab_tabla.getColumna("dato_superficie").setVisible(false);
        tab_tabla.getColumna("numero_bloques").setVisible(false);
        tab_tabla.getColumna("escritura").setVisible(true);
        tab_tabla.getColumna("FECHA_DIGITACION").setLectura(true);
        tab_tabla.getColumna("FECHA_DIGITACION").setValorDefecto(utilitario.getFechaHoraActual());
        tab_tabla.getColumna("FECHA_FICHA").setValorDefecto(utilitario.getFechaActual());
        tab_tabla.getColumna("CLAVE").setEtiqueta();
        tab_tabla.getColumna("CLAVE").setEstilo("font-weight: bold;font-size:15px;color:red");

        tab_tabla.getColumna("forma_propiedad").setVisible(false);
        tab_tabla.getColumna("traslacion_domino").setVisible(false);

        tab_tabla.getColumna("notaria").setVisible(false);
        tab_tabla.getColumna("fecha_inscripcion_notaria").setVisible(false);
        tab_tabla.getColumna("lugar_notaria").setVisible(false);
        tab_tabla.getColumna("reg_propiedad").setVisible(false);
        tab_tabla.getColumna("fecha_registro").setVisible(false);
        tab_tabla.getColumna("situacion_legal").setVisible(false);

        tab_tabla.agregarRelacion(tab_bloque_predio);

        tab_tabla.agregarRelacion(tab_foto);
        tab_tabla.agregarRelacion(tab_croquis);
        tab_tabla.agregarRelacion(tab_imagen);
        tab_tabla.agregarRelacion(tab_inversiones);

        tab_tabla.getColumna("zona").setCombo("SELECT z.ide_distribucion,z.des_distribucion,p.des_distribucion ,c.des_distribucion,pr.des_distribucion  FROM  inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + "WHERE z.ide_tipo_distribucion=5 "
                + "and z.ins_ide_distribucion=p.ide_distribucion "
                + "and p.ins_ide_distribucion=c.ide_distribucion "
                + "and c.ins_ide_distribucion=pr.ide_distribucion");
        tab_tabla.getColumna("zona").setAutoCompletar();
        tab_tabla.getColumna("zona").setMetodoChange("buscarCodigos");

        String sql = " SELECT s.ide_distribucion,s.des_distribucion  FROM  inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE s.ide_tipo_distribucion=6 "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + "";
        tab_tabla.getColumna("sector").setCombo(sql);

        sql = " SELECT b.ide_distribucion,b.des_distribucion "
                + " FROM  inst_distribucion_politica b,inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE b.ide_tipo_distribucion=7 "
                + " and b.ins_ide_distribucion=s.ide_distribucion "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " ";
        tab_tabla.getColumna("ide_distribucion").setCombo(sql);


        tab_tabla.getColumna("SECTOR").setMetodoChange("cargaBarrios");
        tab_tabla.getColumna("ide_distribucion").setMetodoChange("formarClave");
        tab_tabla.getColumna("MANZANA").setMetodoKeyPress("formarClave");

        tab_tabla.getColumna("ide_cliente").setCombo("rec_clientes", "ide_cliente", "nombre,cedula", "");
        tab_tabla.getColumna("ide_cliente").setAutoCompletar();


        tab_tabla.dibujar();
        tab_tabla.setCondicion("");
        //tab_tabla.agregarRelacion(tab_bloque_predio);


        pat_panel.setPanelTabla(tab_tabla);
        pat_panel.getMenuTabla().getItem_buscar().setMetodo("buscar");

        tab_topografia.setId("tab_topografia");
        //    PanelTabla pat_panel2 = new PanelTabla();
        Etiqueta eti = new Etiqueta();
        eti.setValue("REFERENCIAS CARTOGRÁFICAS");
        eti.setEstiloCabecera("font-size:14px");
        //    pat_panel2.getFacets().put("header", eti);
        //    pat_panel2.setPanelTabla(tab_topografia);

        tab_superficie.setId("tab_superficie");
        Grid pat_panel3 = new Grid();
        pat_panel3.setColumns(3);
        Etiqueta eti2 = new Etiqueta();
        eti2.setValue("SUPERFICIE DEL PREDIO");
        eti2.setEstiloCabecera("font-size:14px");

        pat_panel3.getChildren().add(tab_superficie);
        tab_bloque_predio.setId("tab_bloque_predio");
        Grupo pat_panel4 = new Grupo();
        pat_panel4.getChildren().add(tab_bloque_predio);
        pat_panel4.setStyle("width:100%;height:140px;overflow: auto;display: block;");

        pat_panel3.getChildren().add(pat_panel4);

        tab_colindantes.setId("tab_colindantes");
        //  PanelTabla pat_panel5 = new PanelTabla();
        Etiqueta eti3 = new Etiqueta();
        eti3.setValue("COLINDANTES");
        eti3.setEstiloCabecera("font-size:14px");
        //  pat_panel5.getFacets().put("header", eti3);
        //    pat_panel5.setPanelTabla(tab_colindantes);
        Grid gri_tenencia = new Grid();
        gri_tenencia.setColumns(3);
        Grupo gri = new Grupo();
        gri.getChildren().add(eti);
        gri.getChildren().add(tab_topografia);
        gri.getChildren().add(eti2);
        gri.getChildren().add(pat_panel3);
        gri.getChildren().add(eti3);
        gri.getChildren().add(tab_colindantes);
        gri.getChildren().add(gri_tenencia);
        tab_tabulador.agregarTab("ID. PREDIAL II", gri);

        ////////////////////////////////////////Infraestructura
        Grupo gru_infra = new Grupo();
        gru_infra.setId("gru_infra");

        Grupo gru_pesta1 = new Grupo();
        Grid gri_pesta2 = new Grid();
        Grid gri_pesta3 = new Grid();

        Grid gri_bloque = new Grid();
        gri_bloque.setColumns(2);
        Etiqueta eti_blo = new Etiqueta();
        eti_blo.setValue("BLOQUE N");
        gri_bloque.getChildren().add(eti_blo);
        com_bloque.setStyle("width:90px");
        com_bloque.setMetodo("cambioBloque", "tab_tabulador:gru_infra");
        gri_bloque.getChildren().add(com_bloque);
        gru_infra.getChildren().add(gri_bloque);

        Tabulador tab_pesta = new Tabulador();
        tab_pesta.agregarTab("C. GENERALES/ESTRUCTURA", gru_pesta1);
        tab_pesta.agregarTab("ACABADOS", gri_pesta2);
        tab_pesta.agregarTab("INSTALACIONES", gri_pesta3);
        gru_infra.getChildren().add(tab_pesta);

//////////PESTAÑA 1 
        Etiqueta eti_car = new Etiqueta();
        eti_car.setEstiloCabecera("font-size:14px");
        eti_car.setValue("CARACTERISTICAS GENERALES");
        gru_pesta1.getChildren().add(eti_car);
        Grid gri1 = new Grid();
        gri1.setColumns(5);
        ls_estructura.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =101  order by codigo");
        ls_estructura.setLayout("pageDirection");
        ls_estructura.setMetodoChange("crearSeleccion", "@this");
        Fieldset f11 = new Fieldset();
        f11.setLegend("ESTRUCTURA");
        f11.getChildren().add(ls_estructura);
        gri1.getChildren().add(f11);
        Grid gri_eda_c = new Grid();
        gri_eda_c.setColumns(2);
        tex_edad_construccion.setSoloEnteros();
        tex_edad_construccion.setMetodoChange("crearSeleccion", "@this");
        tex_edad_construccion.setSize(3);
        gri_eda_c.getChildren().add(tex_edad_construccion);
        Etiqueta eti_edad_c = new Etiqueta();
        eti_edad_c.setValue("Años");
        gri_eda_c.getChildren().add(eti_edad_c);
        Fieldset f13 = new Fieldset();
        f13.setLegend("EDAD DE CONSTRUCCIÓN");
        f13.getChildren().add(gri_eda_c);
        gri1.getChildren().add(f13);
        ls_estado_conserva.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =103  order by codigo ");
        ls_estado_conserva.setLayout("pageDirection");
        ls_estado_conserva.setMetodoChange("crearSeleccion", "@this");
        Fieldset f12 = new Fieldset();
        f12.setLegend("ESTADO DE CONSERVACION");
        f12.getChildren().add(ls_estado_conserva);
        gri1.getChildren().add(f12);
        Grid gri_repara = new Grid();
        gri_repara.setColumns(2);
        tex_reparacion.setSoloEnteros();
        tex_reparacion.setSize(3);
        tex_reparacion.setMetodoChange("crearSeleccion", "@this");
        gri_repara.getChildren().add(tex_reparacion);
        Etiqueta eti_repara = new Etiqueta();
        eti_repara.setValue("Años");
        gri_repara.getChildren().add(eti_repara);
        Fieldset f14 = new Fieldset();
        f14.setLegend("REPARACIÓN");
        f14.getChildren().add(gri_repara);
        gri1.getChildren().add(f14);
        Grid gri_pisos = new Grid();
        gri_pisos.setColumns(2);
        tex_num_pisos.setSoloEnteros();
        tex_num_pisos.setSize(3);
        tex_num_pisos.setMetodoChange("crearSeleccion", "@this");
        gri_pisos.getChildren().add(tex_num_pisos);
        Etiqueta eti_pisos = new Etiqueta();
        eti_pisos.setValue("Pisos");
        gri_pisos.getChildren().add(eti_pisos);
        Fieldset f15 = new Fieldset();
        f15.setLegend("NÚMERO DE PISOS ");
        f15.getChildren().add(gri_pisos);
        gri1.getChildren().add(f15);
        gru_pesta1.getChildren().add(gri1);
        Etiqueta eti_estr = new Etiqueta();
        eti_estr.setEstiloCabecera("font-size:14px");
        eti_estr.setValue("ESTRUCTURA");
        gru_pesta1.getChildren().add(eti_estr);
        Grid gri_carac = new Grid();
        gri_carac.setColumns(3);
        ls_columnas.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =201  order by codigo ");
        ls_columnas.setLayout("pageDirection");
        ls_columnas.setMetodoChange("crearSeleccion", "@this");
        Grupo pan1 = new Grupo();
        pan1.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        pan1.getChildren().add(ls_columnas);
        Fieldset f1 = new Fieldset();
        f1.setLegend("COLUMNAS");
        f1.getChildren().add(pan1);
        gri_carac.getChildren().add(f1);
        ls_vigas.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =202  order by codigo");
        ls_vigas.setLayout("pageDirection");
        ls_vigas.setMetodoChange("crearSeleccion", "@this");
        Grupo pan2 = new Grupo();
        pan2.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        pan2.getChildren().add(ls_vigas);
        Fieldset f2 = new Fieldset();
        f2.setLegend("VIGAS Y CADENAS");
        f2.getChildren().add(pan2);
        gri_carac.getChildren().add(f2);
        ls_entrepisos.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =203  order by codigo");
        ls_entrepisos.setLayout("pageDirection");
        ls_entrepisos.setMetodoChange("crearSeleccion", "@this");
        Grupo pan3 = new Grupo();
        pan3.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        pan3.getChildren().add(ls_entrepisos);
        Fieldset f3 = new Fieldset();
        f3.setLegend("ENTREPISOS");
        f3.getChildren().add(pan3);
        gri_carac.getChildren().add(f3);
        ls_paredes.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =204  order by codigo");
        ls_paredes.setLayout("pageDirection");
        ls_paredes.setMetodoChange("crearSeleccion", "@this");
        Grupo pan4 = new Grupo();
        pan4.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        pan4.getChildren().add(ls_paredes);
        Fieldset f4 = new Fieldset();
        f4.setLegend("PAREDES");
        f4.getChildren().add(pan4);
        gri_carac.getChildren().add(f4);
        ls_escaleras.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =205  order by codigo");
        ls_escaleras.setLayout("pageDirection");
        ls_escaleras.setMetodoChange("crearSeleccion", "@this");
        Grupo pan5 = new Grupo();
        pan5.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        pan5.getChildren().add(ls_escaleras);
        Fieldset f5 = new Fieldset();
        f5.setLegend("ESCALERAS");
        f5.getChildren().add(pan5);
        gri_carac.getChildren().add(f5);
        ls_cubierta.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =206  order by codigo");
        ls_cubierta.setLayout("pageDirection");
        ls_cubierta.setMetodoChange("crearSeleccion", "@this");
        Grupo pan6 = new Grupo();
        pan6.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        pan6.getChildren().add(ls_cubierta);
        Fieldset f6 = new Fieldset();
        f6.setLegend("CUBIERTA");
        f6.getChildren().add(pan6);
        gri_carac.getChildren().add(f6);
        gru_pesta1.getChildren().add(gri_carac);

//////PESTAÑA 2

        gri_pesta2.setColumns(3);
        ls_rev_pisos.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =301  order by codigo ");
        ls_rev_pisos.setLayout("pageDirection");
        ls_rev_pisos.setMetodoChange("crearSeleccion", "@this");
        Grupo g1 = new Grupo();
        g1.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        g1.getChildren().add(ls_rev_pisos);
        Fieldset fp1 = new Fieldset();
        fp1.setLegend("REV. DE PISOS");
        fp1.getChildren().add(g1);
        gri_pesta2.getChildren().add(fp1);


        ls_rev_interior.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =302  order by codigo ");
        ls_rev_interior.setLayout("pageDirection");
        ls_rev_interior.setMetodoChange("crearSeleccion", "@this");
        Grupo g2 = new Grupo();
        g2.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        g2.getChildren().add(ls_rev_interior);
        Fieldset fp2 = new Fieldset();
        fp2.setLegend("REV. INTERIOR");
        fp2.getChildren().add(g2);
        gri_pesta2.getChildren().add(fp2);

        ls_rev_exterior.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =303  order by codigo ");
        ls_rev_exterior.setLayout("pageDirection");
        ls_rev_exterior.setMetodoChange("crearSeleccion", "@this");
        Grupo g3 = new Grupo();
        g3.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        g3.getChildren().add(ls_rev_exterior);
        Fieldset fp3 = new Fieldset();
        fp3.setLegend("REV. EXTERIOR");
        fp3.getChildren().add(g3);
        gri_pesta2.getChildren().add(fp3);

        ls_rev_escl.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =304  order by codigo ");
        ls_rev_escl.setLayout("pageDirection");
        ls_rev_escl.setMetodoChange("crearSeleccion", "@this");
        Grupo g4 = new Grupo();
        g4.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        g4.getChildren().add(ls_rev_escl);
        Fieldset fp4 = new Fieldset();
        fp4.setLegend("REV. ESCALERA");
        fp4.getChildren().add(g4);
        gri_pesta2.getChildren().add(fp4);


        ls_tumbados.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =305 order by codigo ");
        ls_tumbados.setLayout("pageDirection");
        ls_tumbados.setMetodoChange("crearSeleccion", "@this");
        Grupo g5 = new Grupo();
        g5.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        g5.getChildren().add(ls_tumbados);
        Fieldset fp5 = new Fieldset();
        fp5.setLegend("TUMBADOS");
        fp5.getChildren().add(g5);
        gri_pesta2.getChildren().add(fp5);

        ls_cubierta_acabados.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =306 order by codigo ");
        ls_cubierta_acabados.setLayout("pageDirection");
        ls_cubierta_acabados.setMetodoChange("crearSeleccion", "@this");
        Grupo g6 = new Grupo();
        g6.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        g6.getChildren().add(ls_cubierta_acabados);
        Fieldset fp6 = new Fieldset();
        fp6.setLegend("CUBIERTA");
        fp6.getChildren().add(g6);
        gri_pesta2.getChildren().add(fp6);

        ls_puertas.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =307 order by codigo ");
        ls_puertas.setLayout("pageDirection");
        ls_puertas.setMetodoChange("crearSeleccion", "@this");
        Grupo g7 = new Grupo();
        g7.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        g7.getChildren().add(ls_puertas);
        Fieldset fp7 = new Fieldset();
        fp7.setLegend("PUERTAS");
        fp7.getChildren().add(g7);
        gri_pesta2.getChildren().add(fp7);


        ls_ventanas.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =308 order by codigo ");
        ls_ventanas.setLayout("pageDirection");
        ls_ventanas.setMetodoChange("crearSeleccion", "@this");
        Grupo g8 = new Grupo();
        g8.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        g8.getChildren().add(ls_ventanas);
        Fieldset fp8 = new Fieldset();
        fp8.setLegend("VENTANAS");
        fp8.getChildren().add(g8);
        gri_pesta2.getChildren().add(fp8);


        ls_cubreventanas.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =309 order by codigo ");
        ls_cubreventanas.setLayout("pageDirection");
        ls_cubreventanas.setMetodoChange("crearSeleccion", "@this");
        Grupo g9 = new Grupo();
        g9.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        g9.getChildren().add(ls_cubreventanas);
        Fieldset fp9 = new Fieldset();
        fp9.setLegend("CUBRE VENTANAS");
        fp9.getChildren().add(g9);
        gri_pesta2.getChildren().add(fp9);

        Espacio e1 = new Espacio();
        gri_pesta2.getChildren().add(e1);

        ls_closets.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =310 order by codigo ");
        ls_closets.setLayout("pageDirection");
        ls_closets.setMetodoChange("crearSeleccion", "@this");
        Grupo g10 = new Grupo();
        g10.setStyle("height:150px;overflow: auto;display: block;width:280px;");
        g10.getChildren().add(ls_closets);
        Fieldset fp10 = new Fieldset();
        fp10.setLegend("CLOSETS");
        fp10.getChildren().add(g10);
        gri_pesta2.getChildren().add(fp10);
///////pestaña 3
        gri_pesta3.setColumns(3);
        ls_sanitarias.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =401  order by codigo ");
        ls_sanitarias.setLayout("pageDirection");
        ls_sanitarias.setMetodoChange("crearSeleccion", "@this");
        Grupo g11 = new Grupo();
        g11.setStyle("height:250px;overflow: auto;display: block;width:280px;");
        g11.getChildren().add(ls_sanitarias);
        Fieldset fp11 = new Fieldset();
        fp11.setLegend("SANITARIAS");
        fp11.getChildren().add(g11);
        gri_pesta3.getChildren().add(fp11);


        ls_baños.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =402  order by codigo ");
        ls_baños.setLayout("pageDirection");
        ls_baños.setMetodoChange("crearSeleccion", "@this");
        Grupo g12 = new Grupo();
        g12.setStyle("height:250px;overflow: auto;display: block;width:280px;");
        g12.getChildren().add(ls_baños);
        Fieldset fp12 = new Fieldset();
        fp12.setLegend("BAÑOS");
        fp12.getChildren().add(g12);
        gri_pesta3.getChildren().add(fp12);

        ls_electricas.SetLista("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =403  order by codigo ");
        ls_electricas.setLayout("pageDirection");
        ls_electricas.setMetodoChange("crearSeleccion", "@this");
        Grupo g13 = new Grupo();
        g13.setStyle("height:250px;overflow: auto;display: block;width:280px;");
        g13.getChildren().add(ls_electricas);
        Fieldset fp13 = new Fieldset();
        fp13.setLegend("ELÉCTRICAS");
        fp13.getChildren().add(g13);
        gri_pesta3.getChildren().add(fp13);
/////////////////TERRENOS INFRAESTRUCTURA
        Grid gri_terreno = new Grid();
        gri_terreno.setColumns(5);
        lis_ocupacion.SetLista("select ide_detalle_terrenos,codigo,detalle from sigt_detalle_terrenos where sig_ide_detalle_terrenos =1  order by codigo ");
        lis_ocupacion.setStyle("height:189px;overflow: auto;display: block;width:200px;");
        Grupo g21 = new Grupo();
        g21.setStyle("height:200px;overflow: auto;display: block;width:210px;");
        g21.getChildren().add(lis_ocupacion);
        Fieldset fp21 = new Fieldset();
        fp21.setLegend("OCUPACIÓN");
        fp21.getChildren().add(g21);
        gri_terreno.getChildren().add(fp21);

        lis_carac_suelo.SetLista("select ide_detalle_terrenos,codigo,detalle from sigt_detalle_terrenos where sig_ide_detalle_terrenos =2  order by codigo ");
        lis_carac_suelo.setStyle("height:189px;overflow: auto;display: block;width:200px;");
        Grupo g22 = new Grupo();
        g22.setStyle("height:200px;overflow: auto;display: block;width:210px;");
        g22.getChildren().add(lis_carac_suelo);
        Fieldset fp22 = new Fieldset();
        fp22.setLegend("CARACTERISTICAS DEL SUELO");
        fp22.getChildren().add(g22);
        gri_terreno.getChildren().add(fp22);

        lis_topografia.SetLista("select ide_detalle_terrenos,codigo,detalle from sigt_detalle_terrenos where sig_ide_detalle_terrenos =3  order by codigo ");
        lis_topografia.setStyle("height:189px;overflow: auto;display: block;width:200px;");
        Grupo g23 = new Grupo();
        g23.setStyle("height:200px;overflow: auto;display: block;width:210px;");
        g23.getChildren().add(lis_topografia);
        Fieldset fp23 = new Fieldset();
        fp23.setLegend("TOPOGRAFÍA");
        fp23.getChildren().add(g23);
        gri_terreno.getChildren().add(fp23);


        lis_localizacion.SetLista("select ide_detalle_terrenos,codigo,detalle from sigt_detalle_terrenos where sig_ide_detalle_terrenos =4 order by codigo ");
        lis_localizacion.setStyle("height:189px;overflow: auto;display: block;width:200px;");
        Grupo g24 = new Grupo();
        g24.setStyle("height:200px;overflow: auto;display: block;width:210px;");
        g24.getChildren().add(lis_localizacion);
        Fieldset fp24 = new Fieldset();
        fp24.setLegend("LOCALIZACIÓN");
        fp24.getChildren().add(g24);
        gri_terreno.getChildren().add(fp24);

        lis_forma.SetLista("select ide_detalle_terrenos,codigo,detalle from sigt_detalle_terrenos where sig_ide_detalle_terrenos =5 order by codigo ");
        lis_forma.setStyle("height:189px;overflow: auto;display: block;width:200px;");
        Grupo g25 = new Grupo();
        g25.setStyle("height:200px;overflow: auto;display: block;width:210px;");
        g25.getChildren().add(lis_forma);
        Fieldset fp25 = new Fieldset();
        fp25.setLegend("FORMA");
        fp25.getChildren().add(g25);
        gri_terreno.getChildren().add(fp25);


        lis_uso.SetLista("select ide_detalle_servicios,codigo,detalle from sigt_detalle_servicios where sig_ide_detalle_servicios =101  order by codigo ");
        lis_uso.setStyle("height:80px;overflow: auto;display: block;width:200px;");
        Grupo g26 = new Grupo();
        Etiqueta et11 = new Etiqueta();
        et11.setValue("1.1 USO");
        g26.getChildren().add(et11);
        g26.setStyle("height:255px;overflow: auto;display: block;width:210px;");
        g26.getChildren().add(lis_uso);
        Etiqueta et12 = new Etiqueta();
        et12.setValue("1.2 MATERIAL");
        g26.getChildren().add(et12);
        lis_material.SetLista("select ide_detalle_servicios,codigo,detalle from sigt_detalle_servicios where sig_ide_detalle_servicios =102  order by codigo ");
        lis_material.setStyle("height:125px;overflow: auto;display: block;width:200px;");
        g26.getChildren().add(lis_material);
        Fieldset fp26 = new Fieldset();
        fp26.setLegend("VIAS");
        fp26.getChildren().add(g26);
        gri_terreno.getChildren().add(fp26);



        ls_energia.SetLista("select ide_detalle_servicios,codigo,detalle from sigt_detalle_servicios where sig_ide_detalle_servicios =2  order by codigo ");
        ls_energia.setLayout("pageDirection");
        Grupo g27 = new Grupo();
        g27.setStyle("height:255px;overflow: auto;display: block;width:210px;");
        g27.getChildren().add(ls_energia);
        Fieldset fp27 = new Fieldset();
        fp27.setLegend("ENERGÍA ELÉCTRICA");
        fp27.getChildren().add(g27);
        gri_terreno.getChildren().add(fp27);


        ls_agua.SetLista("select ide_detalle_servicios,codigo,detalle from sigt_detalle_servicios where sig_ide_detalle_servicios =3  order by codigo ");
        ls_agua.setLayout("pageDirection");
        Grupo g28 = new Grupo();
        g28.setStyle("height:255px;overflow: auto;display: block;width:210px;");
        g28.getChildren().add(ls_agua);
        Fieldset fp28 = new Fieldset();
        fp28.setLegend("ABASTECIMIENTO DE AGUA");
        fp28.getChildren().add(g28);
        gri_terreno.getChildren().add(fp28);


        lis_alcantarillado.SetLista("select ide_detalle_servicios,codigo,detalle from sigt_detalle_servicios where sig_ide_detalle_servicios =4  order by codigo ");
        lis_alcantarillado.setStyle("height:245px;overflow: auto;display: block;width:200px;");
        Grupo g29 = new Grupo();
        g29.setStyle("height:255px;overflow: hidden;display: block;width:210px;");
        g29.getChildren().add(lis_alcantarillado);
        Fieldset fp29 = new Fieldset();
        fp29.setLegend("ALCANTARILLADO");
        fp29.getChildren().add(g29);
        gri_terreno.getChildren().add(fp29);


        ls_otros.SetLista("select ide_detalle_servicios,codigo,detalle from sigt_detalle_servicios where sig_ide_detalle_servicios =4  order by codigo ");
        ls_otros.setLayout("pageDirection");
        Grupo g30 = new Grupo();
        g30.setStyle("height:255px;overflow: auto;display: block;width:210px;");
        g30.getChildren().add(ls_otros);
        Fieldset fp30 = new Fieldset();
        fp30.setLegend("OTROS");
        fp30.getChildren().add(g30);
        gri_terreno.getChildren().add(fp30);


        tab_tabulador.agregarTab("TERRENOS / INFRAESTRUCTURA", gri_terreno);

        /////Suelo
        List lis_procu = utilitario.getConexion().consultar("select ide_uso_suelo,detalle from sigt_uso_suelo where sig_ide_uso_suelo is null ");
        Grid gri_su = new Grid();
        Etiqueta et1i = new Etiqueta();
        et1i.setValue("Para seleccionar mas filas precione la tecla CTRL + Click en la fila");
        gri_su.setHeader(et1i);
        gri_su.setColumns(4);
        tab_uso1.setId("tab_uso1");
        gri_su.getChildren().add(tab_uso1);
        tab_uso2.setId("tab_uso2");
        gri_su.getChildren().add(tab_uso2);
        tab_uso3.setId("tab_uso3");
        gri_su.getChildren().add(tab_uso3);
        tab_uso4.setId("tab_uso4");
        gri_su.getChildren().add(tab_uso4);
        gri_su.setStyle("width:100%;height:100%;overflow: auto;display: block;");
        tab_tabulador.agregarTab("USO SUELOS", gri_su);
        tab_tabulador.agregarTab("EDIFICACIÓN", gru_infra);

        ////OTAS INVERSIONES
        Grupo gru_otras = new Grupo();
        tab_tabulador.agregarTab("OTRAS INVERSIONES", gru_otras);

        Fieldset fo = new Fieldset();
        fo.setLegend("OTRAS INVERSIONES");
        fo.setStyle("height:255px;overflow: auto;display: block;width:640px;");
        tab_inversiones.setId("tab_inversiones");
        tab_inversiones.setIdCompleto("tab_tabulador:tab_inversiones");
        tab_inversiones.setTabla("sigt_inversiones", "ide_inversion", 6);
        tab_inversiones.getColumna("ide_detalle_edificacion").setCombo("select ide_detalle_edificacion,codigo,detalle from sigt_detalle_edificacion where sig_ide_detalle_edificacion =5  order by codigo ");
        tab_inversiones.dibujar();

        PanelTabla pat_inv = new PanelTabla();
        pat_inv.setPanelTabla(tab_inversiones);

        fo.getChildren().add(pat_inv);

        Fieldset fo1 = new Fieldset();
        fo1.setLegend("ALÍCUOTAS");
        fo1.setStyle("height:255px;overflow: auto;display: block;width:210px;");

        Object[] o1 = {"1", "PROPIEDAD HORIZONTAL"};
        Object[] o2 = {"2", "PRIVADA"};
        Object[] o3 = {"3", "GENERAL"};

        List lis_ta = new ArrayList();
        lis_ta.add(o1);
        lis_ta.add(o2);
        lis_ta.add(o3);
        com_tipo_alicuota.setCombo(lis_ta);
        com_tipo_alicuota.setStyle("width:200px;");

        Grid gri_ta = new Grid();
        fo1.getChildren().add(gri_ta);

        gri_ta.getChildren().add(new Etiqueta("TIPO DE ALÍCUOTA"));
        gri_ta.getChildren().add(com_tipo_alicuota);

        Object[] oo1 = {"0", "UNIDAD"};
        Object[] oo2 = {"1", "DECIMAL"};

        List lis_ta1 = new ArrayList();
        lis_ta1.add(oo1);
        lis_ta1.add(oo2);

        rad_alicuota.setRadio(lis_ta1);
        gri_ta.getChildren().add(rad_alicuota);

        Grid gri_a1 = new Grid();
        gri_a1.setColumns(2);
        gri_ta.getChildren().add(gri_a1);

        tex_terreno_privado.setSize(5);
        tex_terreno_privado.setSoloNumeros();
        tex_terreno_publico.setSize(5);
        tex_terreno_publico.setSoloNumeros();
        tex_edificacion_publico.setSize(5);
        tex_edificacion_publico.setSoloNumeros();
        tex_edificacion_privado.setSize(5);
        tex_edificacion_privado.setSoloNumeros();

        gri_a1.getChildren().add(new Etiqueta("TERRENO PRIVADO"));
        gri_a1.getChildren().add(tex_terreno_privado);
        gri_a1.getChildren().add(new Etiqueta("EDIFICACIÓN PRIVADO"));
        gri_a1.getChildren().add(tex_edificacion_privado);
        gri_a1.getChildren().add(new Etiqueta("TERRENO PÚBLICO"));
        gri_a1.getChildren().add(tex_terreno_publico);
        gri_a1.getChildren().add(new Etiqueta("EDIFICACIÓN PÚBLICO"));
        gri_a1.getChildren().add(tex_edificacion_publico);






        /////////////////////
        Grid go1 = new Grid();
        go1.setColumns(2);
        go1.getChildren().add(fo);
        go1.getChildren().add(fo1);

        gru_otras.getChildren().add(go1);


        Fieldset fo2 = new Fieldset();
        fo2.setLegend("OBSERVACIONES");
        fo2.setStyle("height:80px;overflow: auto;display: block;width:99%;");

        ate_observacion.setStyle("height:40px;overflow: auto;display: block;width:99%;");
        fo2.getChildren().add(ate_observacion);
        gru_otras.getChildren().add(fo2);

        Panel po3 = new Panel();
        po3.setStyle("padding-top:8px;width:100%;");

        Grid gri_u = new Grid();
        gri_u.setColumns(4);
        gri_u.setBorder(1);
        gri_u.setWidth("100%");


        Grid greu1 = new Grid();
        Etiqueta etu1 = new Etiqueta();
        etu1.setValue("Dimensiones del terreno tomadas de planos");
        greu1.setColumns(2);
        greu1.getChildren().add(etu1);
        greu1.getChildren().add(che_dime);
        Etiqueta etuh = new Etiqueta();
        etuh.setValue("Este dato es relacionado con el dato de superficie");
        etuh.setStyle("color:red");
        greu1.setFooter(etuh);
        gri_u.getChildren().add(greu1);

        Grid greu2 = new Grid();
        greu2.setColumns(2);
        Etiqueta etu2 = new Etiqueta();
        etu2.setValue("Se desconoce el propietario");
        Etiqueta etu3 = new Etiqueta();
        etu3.setValue("Otra fuente de informacion");
        greu2.getChildren().add(etu2);
        greu2.getChildren().add(che_desconoce_propietario);
        greu2.getChildren().add(etu3);
        greu2.getChildren().add(che_otra_informacion);

        gri_u.getChildren().add(greu2);

        Grid greu3 = new Grid();
        greu3.setColumns(2);

        Etiqueta etu4 = new Etiqueta();
        etu4.setValue("Linderos definidos");

        greu3.getChildren().add(etu4);
        //3 Escritura
        List lis_op1 = new ArrayList();
        Object[] ob11 = {"true", "SI"};
        Object[] ob12 = {"false", "NO"};
        lis_op1.add(ob11);
        lis_op1.add(ob12);
        rad_otros_linderos.setRadio(lis_op1);
        greu3.getChildren().add(rad_otros_linderos);

        gri_u.getChildren().add(greu3);


        Grid greu4 = new Grid();
        greu4.setColumns(3);

        Etiqueta etuh5 = new Etiqueta();
        etuh5.setValue("En Construcción");

        greu4.setHeader(etuh5);
        greu4.getChildren().add(che_nuevo_bloque);

        Etiqueta etu6 = new Etiqueta();
        etu6.setValue("Nuevo Bloque No");

        greu4.getChildren().add(etu6);

        txt_nuevo_bloque.setSoloEnteros();
        txt_nuevo_bloque.setSize(4);

        greu4.getChildren().add(txt_nuevo_bloque);

        greu4.getChildren().add(che_ampliacion_bloque);

        Etiqueta etu7 = new Etiqueta();
        etu7.setValue("Nuevo Bloque No");

        greu4.getChildren().add(etu7);

        greu4.getChildren().add(com_combobloques2);

        gri_u.getChildren().add(greu4);

        po3.getChildren().add(gri_u);
        gru_otras.getChildren().add(po3);


        tab_foto.setId("tab_foto");
        tab_foto.setIdCompleto("tab_tabulador:tab_foto");
        PanelTabla pat_panel9 = new PanelTabla();
        pat_panel9.setPanelTabla(tab_foto);
        tab_tabulador.agregarTab("FOTOS", pat_panel9);

        tab_croquis.setId("tab_croquis");
        tab_croquis.setIdCompleto("tab_tabulador:tab_croquis");
        PanelTabla pat_panel10 = new PanelTabla();
        pat_panel10.setPanelTabla(tab_croquis);
        tab_tabulador.agregarTab("CROQUIS", pat_panel10);

        tab_imagen.setId("tab_imagen");
        tab_imagen.setIdCompleto("tab_tabulador:tab_imagen");
        PanelTabla pat_panel11 = new PanelTabla();
        pat_panel11.setPanelTabla(tab_imagen);
        tab_tabulador.agregarTab("IMAGENES DOC.", pat_panel11);


        tab_topografia.setIdCompleto("tab_tabulador:tab_topografia");
        tab_topografia.setSql("select ide_predio as ide_aux,c_topografica,foto_aerea,otros_cartografia,coordenada_este,coordenada_norte from sigt_predio where ide_predio=-1");
        tab_topografia.setCampoPrimaria("ide_aux");
        tab_topografia.getColumna("ide_aux").setVisible(false);
        tab_topografia.setNumeroTabla(2);
        tab_topografia.setTipoFormulario(true);
        tab_topografia.getGrid().setColumns(4);
        tab_topografia.setMostrarNumeroRegistros(false);
        tab_topografia.dibujar();



        tab_superficie.setIdCompleto("tab_tabulador:tab_superficie");
        tab_superficie.setSql("select ide_predio as ide_aux,area_total_terreno,frente_principal,fondo_relativo,frente_fondo,dato_superficie,numero_bloques from sigt_predio where ide_predio=-1");
        tab_superficie.setCampoPrimaria("ide_aux");
        tab_superficie.getColumna("ide_aux").setVisible(false);
        tab_superficie.setTipoFormulario(true);
        tab_superficie.getGrid().setColumns(4);
        tab_superficie.setNumeroTabla(3);
        tab_superficie.getColumna("numero_bloques").setMetodoChange("ingresaNumeroBloques");
        tab_superficie.getColumna("area_total_terreno").setValorDefecto("0");
        tab_superficie.getColumna("area_total_terreno").setLectura(true);
        tab_superficie.getColumna("area_total_terreno").setEtiqueta();
        tab_superficie.getColumna("area_total_terreno").setEstilo("font-size:13px;font-weight: bold;padding-left: 5px;");
        tab_superficie.getColumna("numero_bloques").setValorDefecto("0");

        List lis_super = new ArrayList();
        Object obj1[] = {"1", "Datos Escritura"};
        Object obj2[] = {"2", "Desc. Propietario"};
        Object obj3[] = {"3", "Campo Cartografía"};

        lis_super.add(obj1);
        lis_super.add(obj2);
        lis_super.add(obj3);

        tab_superficie.getColumna("dato_superficie").setCombo(lis_super);
        tab_superficie.getColumna("dato_superficie").setPermitirNullCombo(false);
        tab_superficie.getColumna("dato_superficie").setValorDefecto("2");
        tab_superficie.setMostrarNumeroRegistros(false);
        tab_superficie.dibujar();


        tab_bloque_predio.setIdCompleto("tab_tabulador:tab_bloque_predio");
        tab_bloque_predio.setTabla("sigt_bloque_predio", "ide_bloque_predio", 4);
        tab_bloque_predio.getColumna("ide_tipo_construccion").setVisible(false);
        tab_bloque_predio.getColumna("ide_predio").setVisible(false);
        tab_bloque_predio.getColumna("ide_bloque_predio").setVisible(false);
        tab_bloque_predio.getColumna("edad_construccion").setVisible(false);
        tab_bloque_predio.getColumna("edad_reparacion").setVisible(false);
        tab_bloque_predio.getColumna("nro_pisos").setVisible(false);
        tab_bloque_predio.getColumna("suma_indicador").setVisible(false);
        tab_bloque_predio.getColumna("val_m2_reposicion").setVisible(false);
        tab_bloque_predio.getColumna("val_comercial").setVisible(false);
        tab_bloque_predio.getColumna("porcentaje_reparacion").setVisible(false);
        tab_bloque_predio.getColumna("nro_bloque").setLectura(true);
        tab_bloque_predio.getColumna("val_m2_comercial").setVisible(false);
        tab_bloque_predio.getColumna("superficie_construccion").setMetodoChange("ingresarValorSuperficie");
        tab_bloque_predio.dibujar();


        ///////1 Radio de Dominio

        List lis_domi = utilitario.getConexion().consultar("SELECT ide_posecionario,CODIGO,detalle from sigt_tenecia where sig_ide_posecionario=1 and urbano=true");
        lis_dominio.SetLista(lis_domi);
        lis_dominio.setId("lis_dominio");

        Fieldset fie_dominio = new Fieldset();
        fie_dominio.setStyle("height:200px;");
        fie_dominio.setLegend("DOMINIO");
        fie_dominio.getChildren().add(lis_dominio);
        gri_tenencia.getChildren().add(fie_dominio);


        //2 Traslaccion de dominio
        List lis_trasla_dominio = utilitario.getConexion().consultar("SELECT ide_posecionario,CODIGO,detalle from sigt_tenecia where sig_ide_posecionario=2 and urbano=true");
        lis_traslacion.SetLista(lis_trasla_dominio);
        lis_traslacion.setId("lis_traslacion");
        Fieldset fie_tras_dominio = new Fieldset();
        fie_tras_dominio.setStyle("height:200px;");
        fie_tras_dominio.setLegend("TRASLACIÓN DE DOMINIO");
        fie_tras_dominio.getChildren().add(lis_traslacion);
        gri_tenencia.getChildren().add(fie_tras_dominio);


        //3 Escritura
        List lis_op = new ArrayList();
        Object[] ob1 = {"true", "SI"};
        Object[] ob2 = {"false", "NO"};
        lis_op.add(ob1);
        lis_op.add(ob2);

        rad_escritura.setRadio(lis_op);
        rad_escritura.setValue("true");
        rad_escritura.setMetodoChange("cambioEscritura", "tab_tabulador:fie_escritura");

        Fieldset fie_escritura = new Fieldset();
        fie_escritura.setStyle("height:200px;");
        fie_escritura.setId("fie_escritura");
        fie_escritura.setLegend("ESCRITURA");
        fie_escritura.getChildren().add(rad_escritura);

        tab_escritura.setId("tab_escritura");
        tab_escritura.setIdCompleto("tab_tabulador:tab_escritura");
        tab_escritura.setSql("select ide_predio as ide_aux,notaria,fecha_inscripcion_notaria,lugar_notaria,reg_propiedad,fecha_registro from sigt_predio where ide_predio=-1");
        tab_escritura.setCampoPrimaria("ide_aux");
        tab_escritura.getColumna("fecha_registro").setLectura(true);
        tab_escritura.getColumna("fecha_registro").setValorDefecto(utilitario.getFechaActual());
        tab_escritura.getColumna("ide_aux").setVisible(false);
        tab_escritura.setTipoFormulario(true);
        tab_escritura.getGrid().setColumns(4);
        tab_escritura.setNumeroTabla(5);
        tab_escritura.setMostrarNumeroRegistros(false);
        tab_escritura.dibujar();



        PanelTabla pat_panel6 = new PanelTabla();
        pat_panel6.setPanelTabla(tab_escritura);
        fie_escritura.getChildren().add(pat_panel6);
        gri_tenencia.getChildren().add(fie_escritura);

        List lis_escri = utilitario.getConexion().consultar("SELECT ide_posecionario,CODIGO,detalle from sigt_tenecia where sig_ide_posecionario=3 and urbano=true");
        lis_escritura.SetLista(lis_escri);
        lis_escritura.setRendered(false);
        lis_escritura.setStyle("height:100%;");
        lis_escritura.setId("lis_escritura");
        fie_escritura.getChildren().add(lis_escritura);
        //////
        tab_colindantes.setIdCompleto("tab_tabulador:tab_colindantes");
        tab_colindantes.setSql("select ide_predio as ide_aux,colindante_norte,colindante_sur,colindante_este,colindante_oeste from sigt_predio where ide_predio=-1");
        tab_colindantes.setCampoPrimaria("ide_aux");
        tab_colindantes.getColumna("ide_aux").setVisible(false);
        tab_colindantes.setNumeroTabla(2);
        tab_colindantes.setTipoFormulario(true);
        tab_colindantes.getGrid().setColumns(4);
        tab_colindantes.setMostrarNumeroRegistros(false);
        tab_colindantes.dibujar();


        ///
///nivel 1 produccion
        Object[] fila = (Object[]) lis_procu.get(0);
        List com1 = utilitario.getConexion().consultar("select ide_uso_suelo,codigo,detalle from sigt_uso_suelo where sig_ide_uso_suelo=" + fila[0] + " order by codigo");
        com_combo1.setCombo(com1);
        com_combo1.eliminarVacio();
        String str_com1 = "";
        for (int i = 0; i < com1.size(); i++) {
            Object[] f = (Object[]) com1.get(i);
            if (!str_com1.isEmpty()) {
                str_com1 += ",";
            }
            str_com1 += "'" + f[0] + "'";
        }
        com_combo1.setMetodo("cambioCombo1", "tab_tabulador:tab_uso1");
        tab_uso1.setIdCompleto("tab_tabulador:tab_uso1");
        tab_uso1.setTabla("sigt_uso_suelo", "ide_uso_suelo", 0);
        tab_uso1.setCondicion("sig_ide_uso_suelo in (" + str_com1 + ")");
        tab_uso1.getColumna("ide_uso_suelo").setVisible(false);
        tab_uso1.setCampoOrden("sig_ide_uso_suelo");
        tab_uso1.getColumna("sig_ide_uso_suelo").setVisible(false);
        tab_uso1.getColumna("coeficiente").setVisible(false);
        tab_uso1.getColumna("estado").setVisible(false);
        tab_uso1.getColumna("nivel").setVisible(false);
        tab_uso1.setTipoSeleccionMultiple(true);
        tab_uso1.getColumna("detalle").setLongitud(30);
        tab_uso1.setScrollable(true);
        tab_uso1.setScrollHeight(400);

//        tab_uso1.onCkeckSelect("seleccionarCheck", "tab_tabulador:tab_uso1");
        tab_uso1.dibujar();
        Grid gri_t1 = new Grid();
        Etiqueta et1 = new Etiqueta();
        et1.setValue(fila[1]);
        gri_t1.getChildren().add(et1);
        com_combo1.setStyle("width:180px");
        gri_t1.getChildren().add(com_combo1);
        tab_uso1.setHeader(gri_t1);
        ocultaFilas1(com_combo1.getValue() + "");

///nivel 2 produccion
        fila = (Object[]) lis_procu.get(1);
        List com2 = utilitario.getConexion().consultar("select ide_uso_suelo,codigo,detalle from sigt_uso_suelo where sig_ide_uso_suelo=" + fila[0] + " order by codigo");
        com_combo2.setCombo(com2);
        com_combo2.eliminarVacio();
        String str_com2 = "";
        for (int i = 0; i < com2.size(); i++) {
            Object[] f = (Object[]) com2.get(i);
            if (!str_com2.isEmpty()) {
                str_com2 += ",";
            }
            str_com2 += "'" + f[0] + "'";
        }
        com_combo2.setMetodo("cambioCombo2", "tab_tabulador:tab_uso2");
        tab_uso2.setIdCompleto("tab_tabulador:tab_uso2");
        tab_uso2.setTabla("sigt_uso_suelo", "ide_uso_suelo", 0);
        tab_uso2.setCondicion("sig_ide_uso_suelo in (" + str_com2 + ")");
        tab_uso2.getColumna("ide_uso_suelo").setVisible(false);
        tab_uso2.setCampoOrden("sig_ide_uso_suelo");
        tab_uso2.getColumna("ide_uso_suelo").setVisible(false);
        tab_uso2.getColumna("sig_ide_uso_suelo").setVisible(false);
        tab_uso2.getColumna("coeficiente").setVisible(false);
        tab_uso2.getColumna("estado").setVisible(false);
        tab_uso2.getColumna("nivel").setVisible(false);
        tab_uso2.setTipoSeleccionMultiple(true);
        tab_uso2.getColumna("detalle").setLongitud(30);
        tab_uso2.setScrollable(true);
        tab_uso2.setScrollHeight(400);
//        tab_uso2.onCkeckSelect("seleccionarCheck", "tab_tabulador:tab_uso2");
        tab_uso2.dibujar();
        Grid gri_t2 = new Grid();
        Etiqueta et2 = new Etiqueta();
        et2.setValue(fila[1]);
        gri_t2.getChildren().add(et2);
        com_combo2.setStyle("width:180px");
        gri_t2.getChildren().add(com_combo2);
        tab_uso2.setHeader(gri_t2);
        ocultaFilas2(com_combo2.getValue() + "");
///nivel 3 produccion
        fila = (Object[]) lis_procu.get(2);
        List com3 = utilitario.getConexion().consultar("select ide_uso_suelo,codigo,detalle from sigt_uso_suelo where sig_ide_uso_suelo=" + fila[0] + " order by codigo");
        com_combo3.setCombo(com3);
        com_combo3.eliminarVacio();
        String str_com3 = "";
        for (int i = 0; i < com3.size(); i++) {
            Object[] f = (Object[]) com3.get(i);
            if (!str_com3.isEmpty()) {
                str_com3 += ",";
            }
            str_com3 += "'" + f[0] + "'";
        }
        com_combo3.setMetodo("cambioCombo3", "tab_tabulador:tab_uso3");
        tab_uso3.setIdCompleto("tab_tabulador:tab_uso3");
        tab_uso3.setTabla("sigt_uso_suelo", "ide_uso_suelo", 0);
        tab_uso3.setCondicion("sig_ide_uso_suelo in (" + str_com3 + ")");
        tab_uso3.getColumna("ide_uso_suelo").setVisible(false);
        tab_uso3.setCampoOrden("sig_ide_uso_suelo");
        tab_uso3.getColumna("sig_ide_uso_suelo").setVisible(false);
        tab_uso3.getColumna("coeficiente").setVisible(false);
        tab_uso3.getColumna("estado").setVisible(false);
        tab_uso3.getColumna("nivel").setVisible(false);
        tab_uso3.setTipoSeleccionMultiple(true);
        tab_uso3.getColumna("detalle").setLongitud(30);
        tab_uso3.setScrollable(true);
//        tab_uso3.onCkeckSelect("seleccionarCheck", "tab_tabulador:tab_uso3");
        tab_uso3.setScrollHeight(400);
        tab_uso3.dibujar();
        Grid gri_t3 = new Grid();
        Etiqueta et3 = new Etiqueta();
        et3.setValue(fila[1]);
        gri_t3.getChildren().add(et3);
        com_combo3.setStyle("width:180px");
        gri_t3.getChildren().add(com_combo3);
        tab_uso3.setHeader(gri_t3);
        ocultaFilas3(com_combo3.getValue() + "");

        ///nivel 4 produccion
        fila = (Object[]) lis_procu.get(3);
        List com4 = utilitario.getConexion().consultar("select ide_uso_suelo,codigo,detalle from sigt_uso_suelo where sig_ide_uso_suelo=" + fila[0] + " order by codigo");
        com_combo4.setCombo(com4);
        com_combo4.eliminarVacio();
        String str_com4 = "";
        for (int i = 0; i < com4.size(); i++) {
            Object[] f = (Object[]) com4.get(i);
            if (!str_com4.isEmpty()) {
                str_com4 += ",";
            }
            str_com4 += "'" + f[0] + "'";
        }
        com_combo4.setMetodo("cambioCombo4", "tab_tabulador:tab_uso4");
        tab_uso4.setIdCompleto("tab_tabulador:tab_uso4");
        tab_uso4.setTabla("sigt_uso_suelo", "ide_uso_suelo", 0);
        tab_uso4.setCondicion("sig_ide_uso_suelo in (" + str_com4 + ")");
        tab_uso4.getColumna("ide_uso_suelo").setVisible(false);
        tab_uso4.setCampoOrden("sig_ide_uso_suelo");
        tab_uso4.getColumna("sig_ide_uso_suelo").setVisible(false);
        tab_uso4.getColumna("coeficiente").setVisible(false);
        tab_uso4.getColumna("estado").setVisible(false);
        tab_uso4.getColumna("nivel").setVisible(false);
        tab_uso4.setTipoSeleccionMultiple(true);
        tab_uso4.getColumna("detalle").setLongitud(30);
        tab_uso4.setScrollable(true);
        tab_uso4.setScrollHeight(400);
//        tab_uso4.onCkeckSelect("seleccionarCheck", "tab_tabulador:tab_uso4");
        tab_uso4.dibujar();
        Grid gri_t4 = new Grid();
        Etiqueta et4 = new Etiqueta();
        et4.setValue(fila[1]);
        gri_t4.getChildren().add(et4);
        com_combo4.setStyle("width:180px");
        gri_t4.getChildren().add(com_combo4);
        tab_uso4.setHeader(gri_t4);
        ocultaFilas4(com_combo4.getValue() + "");



        ////tabla foto
        tab_foto.setIdCompleto("tab_tabulador:tab_foto");
        tab_foto.setTabla("sigt_fotografia_predio", "ide_fotografia_predio", 10);
        tab_foto.setTipoFormulario(true);
        tab_foto.getColumna("ide_predio_rural").setVisible(false);
        tab_foto.getColumna("path").setUpload("upload/fotos");
        tab_foto.getColumna("path").setImagen("256", "256");
        tab_foto.getColumna("fecha").setLectura(true);
        tab_foto.getColumna("fecha").setValorDefecto(utilitario.getFechaActual());
        tab_foto.dibujar();

        ////tabla croquis
        tab_croquis.setIdCompleto("tab_tabulador:tab_croquis");
        tab_croquis.setTabla("sigt_fotografia_croquis", "ide_fotografia_croquis", 11);
        tab_croquis.setTipoFormulario(true);
        tab_croquis.getColumna("ide_predio_rural").setVisible(false);
        tab_croquis.getColumna("path").setUpload("upload/fotos");
        tab_croquis.getColumna("path").setImagen("256", "256");
        tab_croquis.getColumna("fecha").setLectura(true);
        tab_croquis.getColumna("fecha").setValorDefecto(utilitario.getFechaActual());
        tab_croquis.dibujar();

        ////tabla ESCRITURAS
        tab_imagen.setIdCompleto("tab_tabulador:tab_imagen");
        tab_imagen.setTabla("sigt_fotografia_documentacion", "ide_fotografia_documentacion", 12);
        tab_imagen.setTipoFormulario(true);
        tab_imagen.getColumna("ide_predio_rural").setVisible(false);
        tab_imagen.getColumna("path").setUpload("upload/fotos");
        tab_imagen.getColumna("path").setImagen("256", "256");
        tab_imagen.dibujar();

        div_division.setId("div_division");
        div_division.dividir1(tab_tabulador);

        agregarComponente(rep_reporte);
        rep_reporte.getBot_aceptar().setMetodo("aceptar_reporte");
        agregarComponente(sfr_formato);
        sfr_formato.setId("sfr_formato");
        agregarComponente(div_division);

        tab_tabla.insertar();
        tab_tabla.getColumna("sector").getListaCombo().clear();
        tab_tabla.getColumna("ide_distribucion").getListaCombo().clear();
        tab_topografia.insertar();
        tab_colindantes.insertar();
        tab_escritura.insertar();
        tab_superficie.insertar();
    }

    public void cargaBarrios(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        formarClave();
        String sql = " SELECT b.ide_distribucion,b.des_distribucion "
                + " FROM  inst_distribucion_politica b,inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE b.ide_tipo_distribucion=7 "
                + " and b.ins_ide_distribucion=s.ide_distribucion "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and s.ide_distribucion=" + tab_tabla.getValor("sector");
        tab_tabla.getColumna("ide_distribucion").setCombo(sql);
        utilitario.addUpdate("tab_tabulador:tab_tabla");
    }

    public void buscarCodigos(SelectEvent evt) {
        tab_tabla.modificar(evt);
        //Busca y asigna los códigos de provincia canton
        //3) parroquia
        List lis = utilitario.getConexion().consultar("SELECT p.cod_distribucion FROM  inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr  "
                + "  WHERE z.ide_tipo_distribucion=5 "
                + "  and z.ins_ide_distribucion=p.ide_distribucion "
                + "  and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and z.ide_distribucion=" + tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "zona"));
        tab_tabla.setValor(tab_tabla.getUltimaFilaModificada(), "cod_parroquia", lis.get(0) + "");
        //3) canton
        lis = utilitario.getConexion().consultar("SELECT c.cod_distribucion FROM  inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr  "
                + "  WHERE z.ide_tipo_distribucion=5 "
                + "  and z.ins_ide_distribucion=p.ide_distribucion "
                + "  and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and z.ide_distribucion=" + tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "zona"));
        tab_tabla.setValor(tab_tabla.getUltimaFilaModificada(), "cod_canton", lis.get(0) + "");
        //3) provinica
        lis = utilitario.getConexion().consultar("SELECT pr.cod_distribucion FROM  inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr  "
                + "  WHERE z.ide_tipo_distribucion=5 "
                + "  and z.ins_ide_distribucion=p.ide_distribucion "
                + "  and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and z.ide_distribucion=" + tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "zona"));
        tab_tabla.setValor(tab_tabla.getUltimaFilaModificada(), "cod_provincia", lis.get(0) + "");
        //utilitario.addUpdateTabla(tab_tabla, "cod_provinicia,cod_canton,cod_parroquia,zona,sector", "");

        formarClave();

        ////carga los sectores de la zona seleccionada

        String sql = " SELECT s.ide_distribucion,s.des_distribucion  FROM  inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE s.ide_tipo_distribucion=6 "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and z.ide_distribucion=" + tab_tabla.getValor("zona");
        tab_tabla.getColumna("sector").setCombo(sql);
        utilitario.addUpdate("tab_tabulador:tab_tabla");

        tab_tabla.getColumna("ide_distribucion").getListaCombo().clear();
    }

    public void formarClave() {
        //Forma la clave
        if (tab_tabla.getValor("zona") != null) {
            String str_clave = "";
            str_clave = tab_tabla.getValor("COD_PROVINCIA") + tab_tabla.getValor("COD_CANTON") + tab_tabla.getValor("COD_PARROQUIA") + tab_tabla.getValor("ZONA") + tab_tabla.getValor("SECTOR") + tab_tabla.getValor("MANZANA");
            tab_tabla.setValor("CLAVE", str_clave);
            utilitario.addUpdateTabla(tab_tabla, "CLAVE", "");
        }
    }

    public void formarClave(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        //Forma la clave
        if (tab_tabla.getValor("zona") != null) {
            String str_clave = "";
            str_clave = tab_tabla.getValor("COD_PROVINCIA") + tab_tabla.getValor("COD_CANTON") + tab_tabla.getValor("COD_PARROQUIA") + tab_tabla.getValor("ZONA") + tab_tabla.getValor("SECTOR") + tab_tabla.getValor("MANZANA") + tab_tabla.getValor("LOTE") + tab_tabla.getValor("UNIDAD");
            tab_tabla.setValor("CLAVE", str_clave);
            utilitario.addUpdateTabla(tab_tabla, "CLAVE", "");
        }
    }

    private void ocultaFilas1(String cod) {

        if (cod != null && !cod.isEmpty()) {
            for (int i = 0; i < tab_uso1.getTotalFilas(); i++) {
                if (tab_uso1.getValor(i, "sig_ide_uso_suelo").equals(cod)) {
                    tab_uso1.getFilas().get(i).setVisible(true);
                } else {
                    tab_uso1.getFilas().get(i).setVisible(false);
                }
            }
        }
    }

    private void ocultaFilas2(String cod) {
        if (cod != null && !cod.isEmpty()) {
            for (int i = 0; i < tab_uso2.getTotalFilas(); i++) {
                if (tab_uso2.getValor(i, "sig_ide_uso_suelo").equals(cod)) {
                    tab_uso2.getFilas().get(i).setVisible(true);
                } else {
                    tab_uso2.getFilas().get(i).setVisible(false);
                }
            }
        }
    }

    private void ocultaFilas3(String cod) {
        if (cod != null && !cod.isEmpty()) {
            for (int i = 0; i < tab_uso3.getTotalFilas(); i++) {
                if (tab_uso3.getValor(i, "sig_ide_uso_suelo").equals(cod)) {
                    tab_uso3.getFilas().get(i).setVisible(true);
                } else {
                    tab_uso3.getFilas().get(i).setVisible(false);
                }
            }
        }
    }

    private void ocultaFilas4(String cod) {
        if (cod != null && !cod.isEmpty()) {
            for (int i = 0; i < tab_uso4.getTotalFilas(); i++) {
                if (tab_uso4.getValor(i, "sig_ide_uso_suelo").equals(cod)) {
                    tab_uso4.getFilas().get(i).setVisible(true);
                } else {
                    tab_uso4.getFilas().get(i).setVisible(false);
                }
            }
        }
    }

//    public void seleccionarCheck() {
//    }
    public void cambioEscritura() {
        //ejecuta si el radio cambio si - no
        if (rad_escritura.getValue().toString().equals("false")) {
            tab_escritura.setRendered(false);
            lis_escritura.setRendered(true);
        } else {
            tab_escritura.setRendered(true);
            lis_escritura.setRendered(false);
        }
    }

    public void ingresarValorSuperficie(AjaxBehaviorEvent evt) {
        tab_bloque_predio.modificar(evt);
        tab_superficie.setValor("area_total_terreno", utilitario.getFormatoNumero(tab_bloque_predio.getSumaColumna("superficie_construccion")) + "");
        utilitario.addUpdate("tab_tabulador:tab_superficie");
    }

    public void ingresaNumeroBloques(AjaxBehaviorEvent evt) {
        tab_superficie.modificar(evt);

        try {
            int num = Integer.parseInt(tab_superficie.getValor("numero_bloques"));
            if (num < 0) {
                tab_superficie.setValor("numero_bloques", "0");
                utilitario.addUpdateTabla(tab_superficie, "numero_bloques", "");
                tab_superficie.limpiar();
            } else if (num == 0) {
                tab_superficie.limpiar();
            } else if (num > 0) {
                if (tab_bloque_predio.getTotalFilas() == 0) {
                    for (int i = 0; i < num; i++) {
                        tab_bloque_predio.insertar();
                        tab_bloque_predio.setValor("nro_bloque", (i + 1) + "");
                        Object obj[] = {(i + 1), (i + 1)};
                        lis_bloques.addFirst(obj);
                        lis_seleccionados_infra.addFirst(null);
                    }
                } else {
                    int inicia = num - tab_bloque_predio.getTotalFilas();

                    int maximo = 0;

                    for (int i = 0; i < tab_bloque_predio.getTotalFilas(); i++) {
                        try {
                            int aux = Integer.parseInt(tab_bloque_predio.getValor(i, "nro_bloque"));
                            if (aux >= maximo) {
                                maximo = aux;
                            }
                        } catch (Exception e) {
                        }
                    }


                    if (inicia > 0) {
                        for (int i = 0; i < inicia; i++) {
                            maximo++;
                            tab_bloque_predio.insertar();
                            tab_bloque_predio.setValor("nro_bloque", maximo + "");
                            Object obj[] = {maximo, maximo};
                            lis_bloques.addLast(obj);
                            lis_seleccionados_infra.addLast(null);
                        }
                    } else {
                        inicia = 0 - inicia;
                        for (int i = 0; i < inicia; i++) {
                            tab_bloque_predio.eliminar();
                            lis_bloques.removeLast();
                            lis_seleccionados_infra.removeLast();
                        }
                    }

                }

            }
        } catch (Exception e) {
        }
        utilitario.addUpdate("tab_tabulador:tab_bloque_predio");
        tab_superficie.setValor("area_total_terreno", utilitario.getFormatoNumero(tab_bloque_predio.getSumaColumna("superficie_construccion")) + "");
        utilitario.addUpdate("tab_tabulador:tab_superficie");
        ////////////////para         
        com_bloque.setCombo(lis_bloques);
        com_bloque.eliminarVacio();
        cambioBloque();
        utilitario.addUpdate("tab_tabulador:gru_infra");

    }

    @Override
    public void insertar() {
        if (tab_tabla.isFocus()) {
            tab_tabla.insertar();
        } else if (tab_imagen.isFocus()) {
            tab_imagen.insertar();
        } else if (tab_foto.isFocus()) {
            tab_foto.insertar();
        } else if (tab_croquis.isFocus()) {
            tab_croquis.insertar();
        } else if (tab_inversiones.isFocus()) {
            tab_inversiones.insertar();
        }
    }

    public void crearSeleccion() {
        //Cuando seleeciono algun check por bloque

        if (com_bloque.getValue() != null && lis_bloques.size() > 0) {
            try {
                int num = Integer.parseInt(com_bloque.getValue() + "");
                num--;
                List lis_valores_seleccionados_infra = new ArrayList();
                //pestaña1
                lis_valores_seleccionados_infra.add(ls_estructura.getValue());
                lis_valores_seleccionados_infra.add(tex_edad_construccion.getValue());
                lis_valores_seleccionados_infra.add(ls_estado_conserva.getValue());
                lis_valores_seleccionados_infra.add(tex_reparacion.getValue());
                lis_valores_seleccionados_infra.add(tex_num_pisos.getValue());
                lis_valores_seleccionados_infra.add(ls_columnas.getValue());
                lis_valores_seleccionados_infra.add(ls_vigas.getValue());
                lis_valores_seleccionados_infra.add(ls_entrepisos.getValue());
                lis_valores_seleccionados_infra.add(ls_paredes.getValue());
                lis_valores_seleccionados_infra.add(ls_escaleras.getValue());
                lis_valores_seleccionados_infra.add(ls_cubierta.getValue());
                //pestaña2
                lis_valores_seleccionados_infra.add(ls_rev_pisos.getValue());
                lis_valores_seleccionados_infra.add(ls_rev_interior.getValue());
                lis_valores_seleccionados_infra.add(ls_rev_exterior.getValue());
                lis_valores_seleccionados_infra.add(ls_rev_escl.getValue());
                lis_valores_seleccionados_infra.add(ls_tumbados.getValue());
                lis_valores_seleccionados_infra.add(ls_cubierta_acabados.getValue());
                lis_valores_seleccionados_infra.add(ls_puertas.getValue());
                lis_valores_seleccionados_infra.add(ls_ventanas.getValue());
                lis_valores_seleccionados_infra.add(ls_cubreventanas.getValue());
                lis_valores_seleccionados_infra.add(ls_closets.getValue());
                //pestaña3
                lis_valores_seleccionados_infra.add(ls_sanitarias.getValue());
                lis_valores_seleccionados_infra.add(ls_baños.getValue());
                lis_valores_seleccionados_infra.add(ls_electricas.getValue());


                lis_seleccionados_infra.set(num, lis_valores_seleccionados_infra);

                ///asigno a tab_bloque los valores
                tab_bloque_predio.setValor(num, "edad_construccion", (String) tex_edad_construccion.getValue());
                tab_bloque_predio.setValor(num, "edad_reparacion", (String) tex_reparacion.getValue());
                tab_bloque_predio.setValor(num, "nro_pisos", (String) tex_num_pisos.getValue());



            } catch (Exception e) {
            }
        }

    }

    public void cambioBloque() {

        if (com_bloque.getValue() != null && lis_bloques.size() > 0) {

            try {
                int num = Integer.parseInt(com_bloque.getValue() + "");

                num--;
                List lis_valores_seleccionados_infra = (ArrayList) lis_seleccionados_infra.get(num);

                if (lis_valores_seleccionados_infra != null) {
                    //pestaña1
                    ls_estructura.setValue(lis_valores_seleccionados_infra.get(0));
                    tex_edad_construccion.setValue(lis_valores_seleccionados_infra.get(1));
                    ls_estado_conserva.setValue(lis_valores_seleccionados_infra.get(2));
                    tex_reparacion.setValue(lis_valores_seleccionados_infra.get(3));
                    tex_num_pisos.setValue(lis_valores_seleccionados_infra.get(4));
                    ls_columnas.setValue(lis_valores_seleccionados_infra.get(5));
                    ls_vigas.setValue(lis_valores_seleccionados_infra.get(6));
                    ls_entrepisos.setValue(lis_valores_seleccionados_infra.get(7));
                    ls_paredes.setValue(lis_valores_seleccionados_infra.get(8));
                    ls_escaleras.setValue(lis_valores_seleccionados_infra.get(9));
                    ls_cubierta.setValue(lis_valores_seleccionados_infra.get(10));
                    //pestaña2
                    ls_rev_pisos.setValue(lis_valores_seleccionados_infra.get(11));
                    ls_rev_interior.setValue(lis_valores_seleccionados_infra.get(12));
                    ls_rev_exterior.setValue(lis_valores_seleccionados_infra.get(13));
                    ls_rev_escl.setValue(lis_valores_seleccionados_infra.get(14));
                    ls_tumbados.setValue(lis_valores_seleccionados_infra.get(15));
                    ls_cubierta_acabados.setValue(lis_valores_seleccionados_infra.get(16));
                    ls_puertas.setValue(lis_valores_seleccionados_infra.get(17));
                    ls_ventanas.setValue(lis_valores_seleccionados_infra.get(18));
                    ls_cubreventanas.setValue(lis_valores_seleccionados_infra.get(19));
                    ls_closets.setValue(lis_valores_seleccionados_infra.get(20));
                    //pestaña3
                    ls_sanitarias.setValue(lis_valores_seleccionados_infra.get(21));
                    ls_baños.setValue(lis_valores_seleccionados_infra.get(22));
                    ls_electricas.setValue(lis_valores_seleccionados_infra.get(23));

                } else {
                    //pestaña1
                    ls_estructura.setValue(null);
                    tex_edad_construccion.setValue(null);
                    ls_estado_conserva.setValue(null);
                    tex_reparacion.setValue(null);
                    tex_num_pisos.setValue(null);
                    ls_columnas.setValue(null);
                    ls_vigas.setValue(null);
                    ls_entrepisos.setValue(null);
                    ls_paredes.setValue(null);
                    ls_escaleras.setValue(null);
                    ls_cubierta.setValue(null);
                    //pestaña2
                    ls_rev_pisos.setValue(null);
                    ls_rev_interior.setValue(null);
                    ls_rev_exterior.setValue(null);
                    ls_rev_escl.setValue(null);
                    ls_tumbados.setValue(null);
                    ls_cubierta_acabados.setValue(null);
                    ls_puertas.setValue(null);
                    ls_ventanas.setValue(null);
                    ls_cubreventanas.setValue(null);
                    ls_closets.setValue(null);
                    //pestaña3
                    ls_sanitarias.setValue(null);
                    ls_baños.setValue(null);
                    ls_electricas.setValue(null);
                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void guardar() {
        //asigna valores a la tabla 1
        if (tab_tabla.isFilaInsertada() == false) {
            tab_tabla.modificar(tab_tabla.getFilaActual());
        }
        tab_tabla.setValor("c_topografica", tab_topografia.getValor("c_topografica"));
        tab_tabla.setValor("foto_aerea", tab_topografia.getValor("foto_aerea"));
        tab_tabla.setValor("otros_cartografia", tab_topografia.getValor("otros_cartografia"));
        tab_tabla.setValor("coordenada_este", tab_topografia.getValor("coordenada_este"));
        tab_tabla.setValor("coordenada_norte", tab_topografia.getValor("coordenada_norte"));

        tab_tabla.setValor("area_total_terreno", tab_superficie.getValor("area_total_terreno"));
        tab_tabla.setValor("frente_principal", tab_superficie.getValor("frente_principal"));
        tab_tabla.setValor("fondo_relativo", tab_superficie.getValor("fondo_relativo"));
        tab_tabla.setValor("frente_fondo", tab_superficie.getValor("frente_fondo"));
        tab_tabla.setValor("dato_superficie", tab_superficie.getValor("dato_superficie"));
        tab_tabla.setValor("numero_bloques", tab_superficie.getValor("numero_bloques"));

        tab_tabla.setValor("colindante_norte", tab_colindantes.getValor("colindante_norte"));
        tab_tabla.setValor("colindante_sur", tab_colindantes.getValor("colindante_sur"));
        tab_tabla.setValor("colindante_este", tab_colindantes.getValor("colindante_este"));
        tab_tabla.setValor("colindante_oeste", tab_colindantes.getValor("colindante_oeste"));

        //dominio ide_posecionario = situacion_legal
        tab_tabla.setValor("forma_propiedad", (String) lis_dominio.getValue());
        tab_tabla.setValor("traslacion_domino", (String) lis_traslacion.getValue());
        tab_tabla.setValor("escritura", rad_escritura.getValue() + "");
        if (rad_escritura.getValue().equals("true")) {
            tab_tabla.setValor("notaria", tab_escritura.getValor("notaria"));
            tab_tabla.setValor("fecha_inscripcion_notaria", tab_escritura.getValor("fecha_inscripcion_notaria"));
            tab_tabla.setValor("lugar_notaria", tab_escritura.getValor("lugar_notaria"));
            tab_tabla.setValor("reg_propiedad", tab_escritura.getValor("reg_propiedad"));
            tab_tabla.setValor("fecha_registro", tab_escritura.getValor("fecha_registro"));
            tab_tabla.setValor("situacion_legal", null);
        } else {
            tab_tabla.setValor("notaria", null);
            tab_tabla.setValor("fecha_inscripcion_notaria", null);
            tab_tabla.setValor("lugar_notaria", null);
            tab_tabla.setValor("reg_propiedad", null);
            tab_tabla.setValor("fecha_registro", null);
            //no 
            tab_tabla.setValor("situacion_legal", (String) lis_escritura.getValue());
        }

        //ALÍCUOTAS
        tab_tabla.setValor("tipo_alicuota", String.valueOf(com_tipo_alicuota.getValue()));
        tab_tabla.setValor("alicuota", String.valueOf(rad_alicuota.getValue()));

        if (tab_tabla.isFilaInsertada()) {

            Tabla tab_ali = new Tabla();
            tab_ali.setTabla("sigt_alicuota ", "ide_alicuota", -1);
            tab_ali.setCondicion("ide_alicuota=-1");
            tab_ali.ejecutarSql();
            tab_ali.insertar();
            tab_ali.setValor("ide_predio", tab_tabla.getValorSeleccionado());
            tab_ali.setValor("edificacion_privado", String.valueOf(tex_edificacion_privado.getValue()));
            tab_ali.setValor("edificacion_publico", String.valueOf(tex_edificacion_publico.getValue()));
            tab_ali.setValor("terreno_privado", String.valueOf(tex_terreno_privado.getValue()));
            tab_ali.setValor("terreno_publico", String.valueOf(tex_terreno_publico.getValue()));
            tab_ali.guardar();

        } else {
            Tabla tab_ali = new Tabla();
            tab_ali.setTabla("sigt_alicuota ", "ide_alicuota", -1);
            tab_ali.setCondicion("ide_predio=" + tab_tabla.getValorSeleccionado());
            tab_ali.ejecutarSql();
            if (tab_ali.getTotalFilas() > 0) {
                tab_ali.setValor("edificacion_privado", String.valueOf(tex_edificacion_privado.getValue()));
                tab_ali.setValor("edificacion_publico", String.valueOf(tex_edificacion_publico.getValue()));
                tab_ali.setValor("terreno_privado", String.valueOf(tex_terreno_privado.getValue()));
                tab_ali.setValor("terreno_publico", String.valueOf(tex_terreno_publico.getValue()));
                tab_ali.modificar(0);
                tab_ali.guardar();
            } else {
                tab_ali.insertar();
                tab_ali.setValor("ide_predio", tab_tabla.getValorSeleccionado());
                tab_ali.setValor("edificacion_privado", String.valueOf(tex_edificacion_privado.getValue()));
                tab_ali.setValor("edificacion_publico", String.valueOf(tex_edificacion_publico.getValue()));
                tab_ali.setValor("terreno_privado", String.valueOf(tex_terreno_privado.getValue()));
                tab_ali.setValor("terreno_publico", String.valueOf(tex_terreno_publico.getValue()));
                tab_ali.guardar();
            }
        }


        tab_tabla.guardar();
        //TERRENOS

        if (tab_tabla.isFilaInsertada()) {
            Tabla t_terrenos = new Tabla();
            t_terrenos.setTabla("sigt_terreno_predio", "ide_terreno_predio", -1);
            t_terrenos.setCondicion("ide_predio=-1");
            t_terrenos.ejecutarSql();
            t_terrenos.getColumna("ide_predio").setValorDefecto(tab_tabla.getValor("ide_predio"));
            t_terrenos.insertar();
            t_terrenos.setValor("ide_detalle_terrenos", (String) lis_ocupacion.getValue());
            t_terrenos.insertar();
            t_terrenos.setValor("ide_detalle_terrenos", (String) lis_carac_suelo.getValue());
            t_terrenos.insertar();
            t_terrenos.setValor("ide_detalle_terrenos", (String) lis_topografia.getValue());
            t_terrenos.insertar();
            t_terrenos.setValor("ide_detalle_terrenos", (String) lis_localizacion.getValue());
            t_terrenos.insertar();
            t_terrenos.setValor("ide_detalle_terrenos", (String) lis_forma.getValue());
            t_terrenos.guardar();
        } else {
            Tabla t_terrenos = new Tabla();
            t_terrenos.setTabla("sigt_terreno_predio", "ide_terreno_predio", -1);
            t_terrenos.setCondicion("ide_predio=" + tab_tabla.getValorSeleccionado());
            t_terrenos.ejecutarSql();
            if (t_terrenos.getTotalFilas() != 5) {
                //si no tiene insertada 4 filas
                utilitario.getConexion().agregarSqlPantalla("delete from sigt_terreno_predio where ide_predio=" + tab_tabla.getValorSeleccionado());

                t_terrenos.getColumna("ide_predio").setValorDefecto(tab_tabla.getValor("ide_predio"));
                t_terrenos.insertar();
                t_terrenos.setValor("ide_detalle_terrenos", (String) lis_ocupacion.getValue());
                t_terrenos.insertar();
                t_terrenos.setValor("ide_detalle_terrenos", (String) lis_carac_suelo.getValue());
                t_terrenos.insertar();
                t_terrenos.setValor("ide_detalle_terrenos", (String) lis_topografia.getValue());
                t_terrenos.insertar();
                t_terrenos.setValor("ide_detalle_terrenos", (String) lis_localizacion.getValue());
                t_terrenos.insertar();
                t_terrenos.setValor("ide_detalle_terrenos", (String) lis_forma.getValue());
                t_terrenos.guardar();
            } else {
                //modifico
                t_terrenos.modificar(0);
                t_terrenos.setValor(0, "ide_detalle_terrenos", (String) lis_ocupacion.getValue());
                t_terrenos.modificar(1);
                t_terrenos.setValor(1, "ide_detalle_terrenos", (String) lis_carac_suelo.getValue());
                t_terrenos.modificar(2);
                t_terrenos.setValor(2, "ide_detalle_terrenos", (String) lis_topografia.getValue());
                t_terrenos.modificar(3);
                t_terrenos.setValor(3, "ide_detalle_terrenos", (String) lis_localizacion.getValue());
                t_terrenos.modificar(4);
                t_terrenos.setValor(4, "ide_detalle_terrenos", (String) lis_forma.getValue());
                t_terrenos.guardar();
            }
        }
//SERVICIOS
        utilitario.getConexion().agregarSqlPantalla("delete from sigt_serv_predio where ide_predio=" + tab_tabla.getValorSeleccionado());
        Tabla t_servicios = new Tabla();
        t_servicios.setTabla("sigt_serv_predio", "ide_serv_predio", -1);
        t_servicios.setCondicion("ide_predio=" + tab_tabla.getValorSeleccionado());
        t_servicios.ejecutarSql();
        t_servicios.getColumna("ide_predio").setValorDefecto(tab_tabla.getValor("ide_predio"));
        t_servicios.insertar();
        t_servicios.setValor("ide_detalle_servicios", (String) lis_uso.getValue());
        t_servicios.insertar();
        t_servicios.setValor("ide_detalle_servicios", (String) lis_material.getValue());
        t_servicios.insertar();
        t_servicios.setValor("ide_detalle_servicios", (String) lis_alcantarillado.getValue());

        String[] l1 = (String[]) ls_energia.getValue();
        if (l1 != null) {
            for (int i = 0; i < l1.length; i++) {
                t_servicios.insertar();
                t_servicios.setValor("ide_detalle_servicios", l1[i]);
            }
        }

        l1 = (String[]) ls_agua.getValue();
        if (l1 != null) {
            for (int i = 0; i < l1.length; i++) {
                t_servicios.insertar();
                t_servicios.setValor("ide_detalle_servicios", l1[i]);
            }
        }
        l1 = (String[]) ls_otros.getValue();
        if (l1 != null) {
            for (int i = 0; i < l1.length; i++) {
                t_servicios.insertar();
                t_servicios.setValor("ide_detalle_servicios", l1[i]);
            }
        }

        t_servicios.guardar();

        ///Guarda uso suelos
        utilitario.getConexion().agregarSqlPantalla("delete from sigt_uso_suelo_predio where ide_predio=" + tab_tabla.getValorSeleccionado());
        Tabla t_uso = new Tabla();
        t_uso.setTabla("sigt_uso_suelo_predio", "ide_uso_suelo_predio", -1);
        t_uso.setCondicion("ide_predio=" + tab_tabla.getValorSeleccionado());
        t_uso.ejecutarSql();
        t_uso.getColumna("ide_predio").setValorDefecto(tab_tabla.getValor("ide_predio"));
        if (tab_uso1.getSeleccionados() != null) {
            for (int i = 0; i < tab_uso1.getSeleccionados().length; i++) {
                t_uso.insertar();
                t_uso.setValor("ide_uso_suelo", tab_uso1.getSeleccionados()[i].getRowKey());
            }
        }
        if (tab_uso2.getSeleccionados() != null) {
            for (int i = 0; i < tab_uso2.getSeleccionados().length; i++) {
                t_uso.insertar();
                t_uso.setValor("ide_uso_suelo", tab_uso2.getSeleccionados()[i].getRowKey());
            }
        }

        if (tab_uso3.getSeleccionados() != null) {
            for (int i = 0; i < tab_uso3.getSeleccionados().length; i++) {
                t_uso.insertar();
                t_uso.setValor("ide_uso_suelo", tab_uso3.getSeleccionados()[i].getRowKey());
            }
        }

        if (tab_uso4.getSeleccionados() != null) {
            for (int i = 0; i < tab_uso4.getSeleccionados().length; i++) {
                t_uso.insertar();
                t_uso.setValor("ide_uso_suelo", tab_uso4.getSeleccionados()[i].getRowKey());
            }
        }

        t_uso.guardar();

///edificacion de bloques bloques 

        //    if (tab_tabla.isFilaInsertada()) {
        utilitario.getConexion().agregarSqlPantalla("delete from sigt_edificacion_predio where ide_predio=" + tab_tabla.getValorSeleccionado());
        Tabla t_edificacion = new Tabla();
        t_edificacion.setTabla("sigt_edificacion_predio", "ide_edificacion_predio", -1);
        t_edificacion.setCondicion("ide_predio=" + tab_tabla.getValorSeleccionado());
        t_edificacion.ejecutarSql();
        t_edificacion.getColumna("ide_predio").setValorDefecto(tab_tabla.getValor("ide_predio"));

        for (int i = 0; i < lis_seleccionados_infra.size(); i++) {
            List lis_valores_seleccionados_infra = (ArrayList) lis_seleccionados_infra.get(i);
            String num_bloque = tab_bloque_predio.getValor(i, "nro_bloque");
            t_edificacion.getColumna("nro_bloque").setValorDefecto(num_bloque);
            if (lis_valores_seleccionados_infra != null) {
                //pestaña1
                l1 = (String[]) lis_valores_seleccionados_infra.get(0);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(2);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(5);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }


                l1 = (String[]) lis_valores_seleccionados_infra.get(6);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }

                }
                l1 = (String[]) lis_valores_seleccionados_infra.get(7);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(8);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(9);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(10);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                //pestaña2
                l1 = (String[]) lis_valores_seleccionados_infra.get(11);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(12);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(13);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(14);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(15);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(16);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(17);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(18);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(19);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(20);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                //pestaña3
                l1 = (String[]) lis_valores_seleccionados_infra.get(21);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(22);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                l1 = (String[]) lis_valores_seleccionados_infra.get(23);
                if (l1 != null) {
                    for (int j = 0; j < l1.length; j++) {
                        t_edificacion.insertar();
                        t_edificacion.setValor("ide_detalle_edificacion", l1[j]);
                    }
                }

                //     }
            }
        }
        t_edificacion.guardar();

        ////utilitario.getConexion().agregarSqlPantalla("delete from sigt_bloque_predio where ide_predio=" + tab_tabla.getValorSeleccionado());
        tab_bloque_predio.guardar();

        tab_foto.guardar();
        tab_imagen.guardar();
        tab_croquis.guardar();
        tab_inversiones.guardar();
        utilitario.getConexion().guardarPantalla();
    }

    private void cargarFicha() {

        String sql = " SELECT s.ide_distribucion,s.des_distribucion  FROM  inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE s.ide_tipo_distribucion=6 "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and z.ide_distribucion=" + tab_tabla.getValor("zona");
        tab_tabla.getColumna("sector").setCombo(sql);

        sql = " SELECT b.ide_distribucion,b.des_distribucion "
                + " FROM  inst_distribucion_politica b,inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE b.ide_tipo_distribucion=7 "
                + " and b.ins_ide_distribucion=s.ide_distribucion "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and s.ide_distribucion=" + tab_tabla.getValor("sector");
        tab_tabla.getColumna("ide_distribucion").setCombo(sql);


        tab_topografia.setValor("c_topografica", tab_tabla.getValor("c_topografica"));
        tab_topografia.setValor("foto_aerea", tab_tabla.getValor("foto_aerea"));
        tab_topografia.setValor("otros_cartografia", tab_tabla.getValor("otros_cartografia"));
        tab_topografia.setValor("coordenada_este", tab_tabla.getValor("coordenada_este"));
        tab_topografia.setValor("coordenada_norte", tab_tabla.getValor("coordenada_norte"));

        tab_superficie.setValor("area_total_terreno", tab_tabla.getValor("area_total_terreno"));
        tab_superficie.setValor("frente_principal", tab_tabla.getValor("frente_principal"));
        tab_superficie.setValor("fondo_relativo", tab_tabla.getValor("fondo_relativo"));
        tab_superficie.setValor("frente_fondo", tab_tabla.getValor("frente_fondo"));
        tab_superficie.setValor("dato_superficie", tab_tabla.getValor("dato_superficie"));
        tab_superficie.setValor("numero_bloques", tab_tabla.getValor("numero_bloques"));


        tab_colindantes.setValor("colindante_norte", tab_tabla.getValor("colindante_norte"));
        tab_colindantes.setValor("colindante_sur", tab_tabla.getValor("colindante_sur"));
        tab_colindantes.setValor("colindante_este", tab_tabla.getValor("colindante_este"));
        tab_colindantes.setValor("colindante_oeste", tab_tabla.getValor("colindante_oeste"));

        //alicuotas        
        com_tipo_alicuota.setValue(tab_tabla.getValor("tipo_alicuota"));
        rad_alicuota.setValue(tab_tabla.getValor("alicuota"));


        Tabla tab_ali = utilitario.consultar("SELECT * FROM sigt_alicuota WHERE ide_predio=" + tab_tabla.getValorSeleccionado());
        if (tab_ali.getTotalFilas() > 0) {
            tex_edificacion_privado.setValue(tab_ali.getValor("edificacion_privado"));
            tex_edificacion_publico.setValue(tab_ali.getValor("edificacion_publico"));
            tex_terreno_privado.setValue(tab_ali.getValor("terreno_privado"));
            tex_terreno_publico.setValue(tab_ali.getValor("terreno_publico"));
        }

        //dominio ide_posecionario = situacion_legal
        lis_dominio.setValue(tab_tabla.getValor("forma_propiedad"));

        lis_traslacion.setValue(tab_tabla.getValor("traslacion_domino"));

        if (rad_escritura.getValue().equals("true")) {
            tab_escritura.setValor("notaria", tab_tabla.getValor("notaria"));
            tab_escritura.setValor("fecha_inscripcion_notaria", tab_tabla.getValor("fecha_inscripcion_notaria"));
            tab_escritura.setValor("lugar_notaria", tab_tabla.getValor("lugar_notaria"));
            tab_escritura.setValor("reg_propiedad", tab_tabla.getValor("reg_propiedad"));
            tab_escritura.setValor("fecha_registro", tab_tabla.getValor("fecha_registro"));

        } else {
            lis_escritura.setValue(tab_tabla.getValor("situacion_legal"));

        }

        //cargo las listas de terrenos

        Tabla t_terrenos = new Tabla();
        t_terrenos.setTabla("sigt_terreno_predio", "ide_terreno_predio", -1);
        t_terrenos.setCondicion("ide_predio=" + tab_tabla.getValorSeleccionado());
        t_terrenos.ejecutarSql();
        lis_ocupacion.setValue(null);
        lis_carac_suelo.setValue(null);
        lis_topografia.setValue(null);
        lis_localizacion.setValue(null);
        lis_forma.setValue(null);
        utilitario.addUpdate("tab_tabulador");
        for (int i = 0; i < t_terrenos.getTotalFilas(); i++) {
            if (i == 0) {
                lis_ocupacion.setValue(t_terrenos.getValor(0, "ide_detalle_terrenos"));
            }
            if (i == 1) {
                lis_carac_suelo.setValue(t_terrenos.getValor(1, "ide_detalle_terrenos"));
            }
            if (i == 2) {
                lis_topografia.setValue(t_terrenos.getValor(2, "ide_detalle_terrenos"));
            }
            if (i == 3) {
                lis_localizacion.setValue(t_terrenos.getValor(3, "ide_detalle_terrenos"));
            }
            if (i == 4) {
                lis_forma.setValue(t_terrenos.getValor(4, "ide_detalle_terrenos"));
            }
        }

        ///Carga servicios
        Tabla t_servicios = new Tabla();
        t_servicios.setTabla("sigt_serv_predio", "ide_serv_predio", -1);
        t_servicios.setCondicion("ide_predio=" + tab_tabla.getValorSeleccionado());
        t_servicios.ejecutarSql();

        String selec1 = "";
        String selec2 = "";
        String selec3 = "";

        for (int i = 0; i < t_servicios.getTotalFilas(); i++) {
            String v = t_servicios.getValor(i, "ide_detalle_servicios");

            for (int c1 = 0; c1 < lis_uso.getLista().size(); c1++) {
                Object obj[] = (Object[]) lis_uso.getLista().get(c1);
                if (obj[0].toString().equals(v)) {
                    lis_uso.setValue(v);
                    break;
                }
            }

            for (int c1 = 0; c1 < lis_material.getLista().size(); c1++) {
                Object obj[] = (Object[]) lis_material.getLista().get(c1);
                if (obj[0].toString().equals(v)) {
                    lis_material.setValue(v);
                    break;
                }
            }

            for (int c1 = 0; c1 < ls_energia.getLista().size(); c1++) {
                Object obj[] = (Object[]) ls_energia.getLista().get(c1);
                if (obj[0].toString().equals(v)) {
                    if (!selec1.isEmpty()) {
                        selec1 += ",";
                    }
                    selec1 += obj[0];
                }
            }

            for (int c1 = 0; c1 < ls_agua.getLista().size(); c1++) {
                Object obj[] = (Object[]) ls_agua.getLista().get(c1);
                if (obj[0].toString().equals(v)) {
                    if (!selec2.isEmpty()) {
                        selec2 += ",";
                    }
                    selec2 += obj[0];
                }
            }

            for (int c1 = 0; c1 < lis_alcantarillado.getLista().size(); c1++) {
                Object obj[] = (Object[]) lis_alcantarillado.getLista().get(c1);
                if (obj[0].toString().equals(v)) {
                    lis_alcantarillado.setValue(v);
                    break;
                }
            }


            for (int c1 = 0; c1 < ls_otros.getLista().size(); c1++) {
                Object obj[] = (Object[]) ls_otros.getLista().get(c1);
                if (obj[0].toString().equals(v)) {
                    if (!selec3.isEmpty()) {
                        selec3 += ",";
                    }
                    selec3 += obj[0];
                }
            }
        }

        ls_energia.setValue(selec1.split(","));
        ls_agua.setValue(selec2.split(","));
        ls_otros.setValue(selec3.split(","));


        //usos

        Tabla t_uso = new Tabla();
        t_uso.setTabla("sigt_uso_suelo_predio", "ide_uso_suelo_predio", -1);
        t_uso.setCondicion("ide_predio=" + tab_tabla.getValorSeleccionado());
        t_uso.ejecutarSql();
        t_uso.getColumna("ide_predio").setValorDefecto(tab_tabla.getValorSeleccionado());

        String str_seleccionados = t_uso.getStringColumna("ide_uso_suelo");


        tab_uso1.setFilasSeleccionados(str_seleccionados);
        tab_uso2.setFilasSeleccionados(str_seleccionados);
        tab_uso3.setFilasSeleccionados(str_seleccionados);
        tab_uso4.setFilasSeleccionados(str_seleccionados);

        //bloques

        lis_seleccionados_infra = new LinkedList();


        lis_bloques.clear();
        String[] valores = new String[21];
        for (int i = 0; i < tab_bloque_predio.getTotalFilas(); i++) {
            //pestaña1
            Object ob[] = {tab_bloque_predio.getValor(i, "nro_bloque"), tab_bloque_predio.getValor(i, "nro_bloque")};

            lis_bloques.addLast(ob);
            List lis_sql = utilitario.getConexion().consultar("SELECT ide_detalle_edificacion from  sigt_edificacion_predio where nro_bloque=" + tab_bloque_predio.getValor(i, "nro_bloque") + " and ide_predio=" + tab_tabla.getValor("ide_predio"));

            for (int h = 0; h < valores.length; h++) {
                valores[h] = "";
            }


            if (lis_sql != null && !lis_sql.isEmpty()) {
                for (int j = 0; j < lis_sql.size(); j++) {


                    for (int c1 = 0; c1 < ls_estructura.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_estructura.getLista().get(c1);

                        if (obj[0].equals(lis_sql.get(j))) {

                            if (!valores[0].isEmpty()) {
                                valores[0] += ",";
                            }
                            valores[0] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_estado_conserva.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_estado_conserva.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[1].isEmpty()) {
                                valores[1] += ",";
                            }
                            valores[1] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_columnas.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_columnas.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[2].isEmpty()) {
                                valores[2] += ",";
                            }
                            valores[2] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_vigas.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_vigas.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[3].isEmpty()) {
                                valores[3] += ",";
                            }
                            valores[3] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_entrepisos.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_entrepisos.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[4].isEmpty()) {
                                valores[4] += ",";
                            }
                            valores[4] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_paredes.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_paredes.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[5].isEmpty()) {
                                valores[5] += ",";
                            }
                            valores[5] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_escaleras.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_escaleras.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[6].isEmpty()) {
                                valores[6] += ",";
                            }
                            valores[6] += obj[0];
                        }
                    }

                    for (int c1 = 0; c1 < ls_cubierta.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_cubierta.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[7].isEmpty()) {
                                valores[7] += ",";
                            }
                            valores[7] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_rev_pisos.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_rev_pisos.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[8].isEmpty()) {
                                valores[8] += ",";
                            }
                            valores[8] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_rev_interior.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_rev_interior.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[9].isEmpty()) {
                                valores[9] += ",";
                            }
                            valores[9] += obj[0];
                        }
                    }

                    for (int c1 = 0; c1 < ls_rev_exterior.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_rev_exterior.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[10].isEmpty()) {
                                valores[10] += ",";
                            }
                            valores[10] += obj[0];
                        }
                    }

                    for (int c1 = 0; c1 < ls_rev_escl.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_rev_escl.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[11].isEmpty()) {
                                valores[11] += ",";
                            }
                            valores[11] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_tumbados.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_tumbados.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[12].isEmpty()) {
                                valores[12] += ",";
                            }
                            valores[12] += obj[0];
                        }
                    }

                    for (int c1 = 0; c1 < ls_cubierta_acabados.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_cubierta_acabados.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[13].isEmpty()) {
                                valores[13] += ",";
                            }
                            valores[13] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_puertas.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_puertas.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[14].isEmpty()) {
                                valores[14] += ",";
                            }
                            valores[14] += obj[0];
                        }
                    }


                    for (int c1 = 0; c1 < ls_ventanas.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_ventanas.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[15].isEmpty()) {
                                valores[15] += ",";
                            }
                            valores[15] += obj[0];
                        }
                    }

                    for (int c1 = 0; c1 < ls_cubreventanas.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_cubreventanas.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[16].isEmpty()) {
                                valores[16] += ",";
                            }
                            valores[16] += obj[0];
                        }
                    }



                    for (int c1 = 0; c1 < ls_closets.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_closets.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[17].isEmpty()) {
                                valores[17] += ",";
                            }
                            valores[17] += obj[0];
                        }
                    }

                    for (int c1 = 0; c1 < ls_sanitarias.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_sanitarias.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[18].isEmpty()) {
                                valores[18] += ",";
                            }
                            valores[18] += obj[0];
                        }
                    }

                    for (int c1 = 0; c1 < ls_baños.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_baños.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[19].isEmpty()) {
                                valores[19] += ",";
                            }
                            valores[19] += obj[0];
                        }
                    }

                    for (int c1 = 0; c1 < ls_electricas.getLista().size(); c1++) {
                        Object obj[] = (Object[]) ls_electricas.getLista().get(c1);
                        if (obj[0].equals(lis_sql.get(j))) {
                            if (!valores[20].isEmpty()) {
                                valores[20] += ",";
                            }
                            valores[20] += obj[0];
                        }
                    }

                }
            }
            List lis = new ArrayList();

            lis.add(valores[0].split(","));
            lis.add(tab_bloque_predio.getValor(i, "edad_construccion"));
            lis.add(valores[1].split(","));
            lis.add(tab_bloque_predio.getValor(i, "edad_reparacion"));
            lis.add(tab_bloque_predio.getValor(i, "nro_pisos"));


            lis.add(valores[2].split(","));
            lis.add(valores[3].split(","));
            lis.add(valores[4].split(","));
            lis.add(valores[5].split(","));
            lis.add(valores[6].split(","));
            lis.add(valores[7].split(","));
            //pestaña2
            lis.add(valores[8].split(","));
            lis.add(valores[9].split(","));
            lis.add(valores[10].split(","));
            lis.add(valores[11].split(","));
            lis.add(valores[12].split(","));
            lis.add(valores[13].split(","));
            lis.add(valores[14].split(","));
            lis.add(valores[15].split(","));
            lis.add(valores[16].split(","));
            lis.add(valores[17].split(","));
            //pestaña3
            lis.add(valores[18].split(","));
            lis.add(valores[19].split(","));
            lis.add(valores[20].split(","));
            lis_seleccionados_infra.addFirst(lis);
        }
        com_bloque.setCombo(lis_bloques);
        com_bloque.eliminarVacio();
        cambioBloque();
    }

    public void eliminar() {
        if (tab_tabla.isFocus()) {
            tab_tabla.eliminar();
        } else if (tab_imagen.isFocus()) {
            tab_imagen.eliminar();
        } else if (tab_foto.isFocus()) {
            tab_foto.eliminar();
        } else if (tab_croquis.isFocus()) {
            tab_croquis.eliminar();
        } else if (tab_inversiones.isFocus()) {
            tab_inversiones.eliminar();
        }
    }

    public void cambioCombo1() {
        ocultaFilas1(com_combo1.getValue() + "");
    }

    public void cambioCombo2() {
        ocultaFilas2(com_combo2.getValue() + "");
    }

    public void cambioCombo3() {
        ocultaFilas3(com_combo3.getValue() + "");
    }

    public void cambioCombo4() {
        ocultaFilas4(com_combo4.getValue() + "");
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }

    public Tabla getTab_topografia() {
        return tab_topografia;
    }

    public void setTab_topografia(Tabla tab_topografia) {
        this.tab_topografia = tab_topografia;
    }

    public Tabla getTab_superficie() {
        return tab_superficie;
    }

    public void setTab_superficie(Tabla tab_superficie) {
        this.tab_superficie = tab_superficie;
    }

    public Tabla getTab_bloque_predio() {
        return tab_bloque_predio;
    }

    public void setTab_bloque_predio(Tabla tab_bloque_predio) {
        this.tab_bloque_predio = tab_bloque_predio;
    }

    public Tabla getTab_colindantes() {
        return tab_colindantes;
    }

    public void setTab_colindantes(Tabla tab_colindantes) {
        this.tab_colindantes = tab_colindantes;
    }

    public Tabla getTab_escritura() {
        return tab_escritura;
    }

    public void setTab_escritura(Tabla tab_escritura) {
        this.tab_escritura = tab_escritura;
    }

    public Tabla getTab_uso1() {
        return tab_uso1;
    }

    public void setTab_uso1(Tabla tab_uso1) {
        this.tab_uso1 = tab_uso1;
    }

    public Tabla getTab_uso2() {
        return tab_uso2;
    }

    public void setTab_uso2(Tabla tab_uso2) {
        this.tab_uso2 = tab_uso2;
    }

    public Tabla getTab_uso3() {
        return tab_uso3;
    }

    public void setTab_uso3(Tabla tab_uso3) {
        this.tab_uso3 = tab_uso3;
    }

    public Tabla getTab_uso4() {
        return tab_uso4;
    }

    public void setTab_uso4(Tabla tab_uso4) {
        this.tab_uso4 = tab_uso4;
    }

    public Tabla getTab_croquis() {
        return tab_croquis;
    }

    public void setTab_croquis(Tabla tab_croquis) {
        this.tab_croquis = tab_croquis;
    }

    public Tabla getTab_foto() {
        return tab_foto;
    }

    public void setTab_foto(Tabla tab_foto) {
        this.tab_foto = tab_foto;
    }

    public Tabla getTab_imagen() {
        return tab_imagen;
    }

    public void setTab_imagen(Tabla tab_imagen) {
        this.tab_imagen = tab_imagen;
    }

    public Tabla getTab_inversiones() {
        return tab_inversiones;
    }

    public void setTab_inversiones(Tabla tab_inversiones) {
        this.tab_inversiones = tab_inversiones;
    }

    public void buscar() {
        utilitario.getBuscaTabla().setBuscar(tab_tabla);
        utilitario.getBuscaTabla().getBot_aceptar().setMetodo("aceptaBuscar");
    }

    public void aceptaBuscar() {
        utilitario.getBuscaTabla().aceptarBuscar();
        if (utilitario.getBuscaTabla().isVisible() == false) {
            cargarFicha();
        }
    }

    public Reporte getRep_reporte() {
        return rep_reporte;
    }

    public void setRep_reporte(Reporte rep_reporte) {
        this.rep_reporte = rep_reporte;
    }

    public SeleccionFormatoReporte getSfr_formato() {
        return sfr_formato;
    }

    public void setSfr_formato(SeleccionFormatoReporte sfr_formato) {
        this.sfr_formato = sfr_formato;
    }

    @Override
    public void abrir_reporte() {
//Se ejecuta cuando da click en el boton de Reportes de la Barra
        rep_reporte.dibujar();
    }

    @Override
    public void aceptar_reporte() {
//Se ejecuta cuando se selecciona un reporte de la lista
        if (rep_reporte.getReporteSelecionado().equalsIgnoreCase("Ficha Urbana 2")) {
            rep_reporte.cerrar();
            Map parametros = new HashMap();
            parametros.put("ide_predio", Integer.parseInt(tab_tabla.getValorSeleccionado()));
            sfr_formato.setSeleccionFormatoReporte(parametros, rep_reporte.getPath());
            sfr_formato.dibujar();
            utilitario.addUpdate("rep_reporte,sfr_formato");
        }
    }
}
