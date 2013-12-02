/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avaluos;

import sistema.*;
import framework.*;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author user
 */
public class pre_ficha_rural extends Pantalla{
    
    private Tabla tab_tabla = new Tabla();    
    private Division div_division = new Division();   
    private Tabulador tab_tabulador = new Tabulador();
    private Tabla tab_dominio = new Tabla();
    private Tabla tab_area = new Tabla();
    private Tabla tab_tenecias = new Tabla();
    private Tabla tab_documentacion = new Tabla();
    private Tabla tab_construccion = new Tabla();
    private Tabla tab_servicios = new Tabla();
    private Tabla tab_adicionales = new Tabla();
    private Tabla tab_control = new Tabla();
    private Tabla tab_foto = new Tabla();
    private Tabla tab_croquis = new Tabla();
    private Tabla tab_cultivos = new Tabla();
    private Tabla tab_agropecuaria = new Tabla();
    private Tabla tab_imagen = new Tabla();
    private Boton bot_avaluar = new Boton();
    private Boton bot_avaluar_varios = new Boton();
    private Boton bot_cambiar_dominio = new Boton();
    private Boton bot_busqueda = new Boton();
    /////////////////
    private Dialogo dia_panel = new Dialogo();
    private Calendario cal_avalua = new Calendario();
    private Texto tex_provincia = new Texto();
    private Texto tex_canton = new Texto();
    private Texto tex_parroquia = new Texto();
    private Texto tex_zona = new Texto();
    private Texto tex_sector = new Texto();
    private Texto tex_poligono = new Texto();
    private AutoCompletar aut_distribucion = new AutoCompletar();
    private SeleccionCalendario sec_calendario = new SeleccionCalendario();
    ///cambio de dominio
    private Dialogo dia_cambio = new Dialogo();
    private Tabla tab_cambio = new Tabla();
    private Tabla tab_nuevo = new Tabla();
///busqueda
    private Dialogo dia_dialogo = new Dialogo();
    private AutoCompletar auc_cliente = new AutoCompletar();
    private Tabla tab_busca_titulo = new Tabla();
    private MarcaAgua maa_marca = new MarcaAgua();
    private Boton bot_limpiar = new Boton();

    public pre_ficha_rural() {
        tab_tabulador.setId("tab_tabulador");
        tab_dominio.setId("tab_dominio");
        tab_dominio.setIdCompleto("tab_tabulador:tab_dominio");
        PanelTabla pat_panel2 = new PanelTabla();
        pat_panel2.setPanelTabla(tab_dominio);
        tab_tabulador.agregarTab("DOMINIO", pat_panel2);

        tab_area.setId("tab_area");
        tab_area.setIdCompleto("tab_tabulador:tab_area");
        PanelTabla pat_panel13 = new PanelTabla();
        pat_panel13.setPanelTabla(tab_area);
        tab_tabulador.agregarTab("AREA", pat_panel13);

        tab_tenecias.setId("tab_tenecias");
        tab_tenecias.setIdCompleto("tab_tabulador:tab_tenecias");
        PanelTabla pat_panel3 = new PanelTabla();
        pat_panel3.setPanelTabla(tab_tenecias);
        tab_tabulador.agregarTab("TENECIAS", pat_panel3);

        tab_documentacion.setId("tab_documentacion");
        tab_documentacion.setIdCompleto("tab_tabulador:tab_documentacion");
        PanelTabla pat_panel4 = new PanelTabla();
        pat_panel4.setPanelTabla(tab_documentacion);
        tab_tabulador.agregarTab("DOCUMENTACIÓN", pat_panel4);

        tab_construccion.setId("tab_construccion");
        tab_construccion.setIdCompleto("tab_tabulador:tab_construccion");
        PanelTabla pat_panel5 = new PanelTabla();
        pat_panel5.setPanelTabla(tab_construccion);
        tab_tabulador.agregarTab("CONSTRUCCIÓN", pat_panel5);

        tab_servicios.setId("tab_servicios");
        tab_servicios.setIdCompleto("tab_tabulador:tab_servicios");
        PanelTabla pat_panel6 = new PanelTabla();
        pat_panel6.setPanelTabla(tab_servicios);
        tab_tabulador.agregarTab("SERVICIOS", pat_panel6);

        tab_adicionales.setId("tab_adicionales");
        tab_adicionales.setIdCompleto("tab_tabulador:tab_adicionales");
        PanelTabla pat_panel7 = new PanelTabla();
        pat_panel7.setPanelTabla(tab_adicionales);
        tab_tabulador.agregarTab("ADICIONAL CONS", pat_panel7);

        tab_control.setId("tab_control");
        tab_control.setIdCompleto("tab_tabulador:tab_control");
        PanelTabla pat_panel8 = new PanelTabla();
        pat_panel8.setPanelTabla(tab_control);
        tab_tabulador.agregarTab("CONTROL", pat_panel8);

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

        tab_cultivos.setId("tab_cultivos");
        tab_cultivos.setIdCompleto("tab_tabulador:tab_cultivos");
        PanelTabla pat_panel11 = new PanelTabla();
        pat_panel11.setPanelTabla(tab_cultivos);
        tab_tabulador.agregarTab("CULTIVOS", pat_panel11);

        tab_agropecuaria.setId("tab_agropecuaria");
        tab_agropecuaria.setIdCompleto("tab_tabulador:tab_agropecuaria");
        PanelTabla pat_panel12 = new PanelTabla();
        pat_panel12.setPanelTabla(tab_agropecuaria);
        tab_tabulador.agregarTab("AGROPECUARIA", pat_panel12);

        tab_imagen.setId("tab_imagen");
        PanelTabla pat_panel14 = new PanelTabla();
        pat_panel14.setPanelTabla(tab_imagen);
        tab_tabulador.agregarTab("IMAGENES DOC.", pat_panel14);

      
        bar_botones.getBot_inicio().setMetodo("inicio");
        bar_botones.getBot_siguiente().setMetodo("siguiente");
        bar_botones.getBot_atras().setMetodo("atras");
        bar_botones.getBot_fin().setMetodo("fin");
        bot_avaluar.setValue("Avaluar");
        bot_avaluar.setMetodo("avaluar");
        bot_avaluar.setUpdate("sec_calendario");
        bar_botones.agregarBoton(bot_avaluar);

        bot_avaluar_varios.setValue("Avaluar Varios");
        bot_avaluar_varios.setMetodo("avaluarVarios");
        bot_avaluar_varios.setUpdate("dia_panel");
        bar_botones.agregarBoton(bot_avaluar_varios);

        Espacio esp = new Espacio();
        esp.setWidth("25");
        esp.setHeight("1");
        bar_botones.agregarComponente(esp);
        bot_cambiar_dominio.setValue("Cambio de Dominio");
        bot_cambiar_dominio.setMetodo("cambiarDominio");
        bot_cambiar_dominio.setUpdate("dia_cambio");


        bar_botones.agregarComponente(bot_cambiar_dominio);

        bot_busqueda.setValue("Buscar Ficha");
        bot_busqueda.setMetodo("BuscarFicha");
        bot_busqueda.setUpdate("dia_dialogo");

        Espacio esp1 = new Espacio();
        esp1.setWidth("25");
        esp1.setHeight("1");
        bar_botones.agregarComponente(esp1);

        bar_botones.agregarComponente(bot_busqueda);


        tab_tabla.setId("tab_tabla");        
        tab_tabla.setTabla("sigt_predio_rural", "ide_predio_rural", 1);
        tab_tabla.setTipoFormulario(true);
        tab_tabla.setValidarInsertar(true);
        tab_tabla.getColumna("fecha_digitacion").setLectura(true);
        tab_tabla.getColumna("fecha_digitacion").setValorDefecto(utilitario.getFechaActual());
        tab_tabla.getColumna("fecha_ficha").setValorDefecto(utilitario.getFechaActual());
        tab_tabla.getColumna("ZONA").setLectura(true);
        tab_tabla.getColumna("LOTE").setMetodoKeyPress("formarClave");
        tab_tabla.getColumna("POLIGONO").setMetodoKeyPress("formarClave");
        tab_tabla.getColumna("DIVISION").setMetodoKeyPress("formarClave");
        tab_tabla.getColumna("CLAVE").setEtiqueta();
        tab_tabla.getColumna("CLAVE").setEstilo("font-weight: bold;font-size:15px;color:red");
        tab_tabla.getColumna("cod_provincia").setLectura(true);
        tab_tabla.getColumna("cod_parroquia").setLectura(true);
        tab_tabla.getColumna("cod_canton").setLectura(true);
        tab_tabla.getColumna("sector").setLectura(true);
        tab_tabla.getColumna("IDE_PROPIEDAD").setCombo("sigc_propiedad", "IDE_PROPIEDAD", "IDE_PROPIEDAD,des_PROPIEDAD", "");
        tab_tabla.getColumna("ide_linderacion").setCombo("sigt_linderacion", "ide_linderacion", "des_linderacion", "");
        tab_tabla.getColumna("ide_localizacion").setCombo("sigt_localizacion", "ide_localizacion", "codigo,detalle", "");
        tab_tabla.getGrid().setColumns(4);
        tab_tabla.onSelect("seleccionar_tabla1");

        tab_tabla.getColumna("ide_tipo_propiedad").setCombo("sigc_tipo_propiedad", "ide_tipo_propiedad", "ide_tipo_propiedad,des_tipo_propiedad", "");
        tab_tabla.getColumna("ide_tipo_propiedad").setPermitirNullCombo(false);
        tab_tabla.getColumna("ide_tipo_propiedad").setMetodoChange("seleccionarTipoPropiedad");
        tab_tabla.getColumna("ide_distribucion").setCombo("SELECT b.ide_distribucion,b.des_distribucion,s.des_distribucion,z.des_distribucion,p.des_distribucion ,c.des_distribucion,pr.des_distribucion    FROM  inst_distribucion_politica b,inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE b.ide_tipo_distribucion=7 "
                + "and b.ins_ide_distribucion=s.ide_distribucion "
                + "and s.ins_ide_distribucion=z.ide_distribucion "
                + "and z.ins_ide_distribucion=p.ide_distribucion "
                + "and p.ins_ide_distribucion=c.ide_distribucion "
                + "and c.ins_ide_distribucion=pr.ide_distribucion");
        tab_tabla.getColumna("ide_distribucion").setAutoCompletar();
        tab_tabla.getColumna("ide_distribucion").setMetodoChange("buscarCodigos");
        tab_tabla.getColumna("IDE_USO").setCombo("sigc_uso", "IDE_USO", "COD_USO,DES_USO", "");
        tab_tabla.getColumna("IDE_USO").setAutoCompletar();
        tab_tabla.setBusquedaFormulario(false);
        tab_tabla.agregarRelacion(tab_dominio);
        tab_tabla.agregarRelacion(tab_tenecias);
        tab_tabla.agregarRelacion(tab_area);
        tab_tabla.agregarRelacion(tab_documentacion);
        tab_tabla.agregarRelacion(tab_construccion);
        tab_tabla.agregarRelacion(tab_servicios);
        tab_tabla.agregarRelacion(tab_adicionales);
        tab_tabla.agregarRelacion(tab_control);
        tab_tabla.agregarRelacion(tab_foto);
        tab_tabla.agregarRelacion(tab_croquis);
        tab_tabla.agregarRelacion(tab_cultivos);
        tab_tabla.agregarRelacion(tab_agropecuaria);
        tab_tabla.agregarRelacion(tab_imagen);
        tab_tabla.setCondicion("ide_predio_rural=-1");
        tab_tabla.dibujar();
        tab_tabla.setCondicion("");

        
        

        Etiqueta eti_titulo = new Etiqueta();
        eti_titulo.setValue("<strong>FICHA DE RELEVAMIENTO PREDIAL RURAL</strong>");
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.getChildren().add(eti_titulo);
        pat_panel.setPanelTabla(tab_tabla);
        tab_tabla.insertar();

        ////tabla del dominio
        tab_dominio.setTabla("sigt_titular_dominio", "ide_titular", 2);
        tab_dominio.setIdCompleto("tab_tabulador:tab_dominio");
        tab_dominio.getColumna("ide_predio").setVisible(false);
        tab_dominio.getColumna("IDE_TIPO_DOCUMENTO").setCombo("sigc_tipo_documento", "IDE_TIPO_DOCUMENTO", "DES_TIPO_DOCUMENTO", "");
        tab_dominio.getColumna("IDE_ESTADO_CIVIL").setCombo("sigc_estado_civil", "IDE_ESTADO_CIVIL", "DES_ESTADO_CIVIL", "");
        tab_dominio.getColumna("DOCUMENTO").setLectura(true);
        tab_dominio.getColumna("IDE_TIPO_DOCUMENTO").setLectura(true);
        tab_dominio.getColumna("FECHA_NACIMIENTO").setLectura(true);
        tab_dominio.getColumna("ide_estado_civil").setLectura(true);
        tab_dominio.getColumna("ide_cliente").setCombo("rec_clientes", "ide_cliente", "nombre,cedula", "");
        tab_dominio.getColumna("ide_cliente").setAutoCompletar();
        tab_dominio.getColumna("IDE_CLIENTE").setMetodoChange("buscarDatosCliente");
        tab_dominio.getColumna("porcentaje").setValorDefecto("100");
        tab_dominio.getColumna("porcentaje").setLectura(true);
        tab_dominio.getColumna("EMISION").setValorDefecto("true");
        // tab_dominio.getColumna("fecha_posesion").setLectura(true);
        tab_dominio.getColumna("fecha_posesion").setValorDefecto(utilitario.getFechaActual());
        tab_dominio.dibujar();

        ////tabla de tenecias
        tab_tenecias.setId("tab_tenecias");
        tab_tenecias.setTabla("sigt_tenecias", "ide_tenencia", 3);
        tab_tenecias.getColumna("ide_tipo_tenencia").setCombo("sigt_tipo_tenencias", "ide_tipo_tenencia", "codigo,detalle", "");
        tab_tenecias.dibujar();

        //tabla documentacion
        tab_documentacion.setTabla("sigt_documentacion", "ide_documentacion", 4);
        tab_documentacion.getColumna("ide_documento_legal").setCombo("sigc_documento_legal", "ide_documento_legal", "ide_documento_legal,des_documento_legal", "");
        tab_documentacion.getColumna("ide_tipo_transferencia").setCombo("sigc_tipo_transferencia", "ide_tipo_transferencia", "ide_tipo_transferencia,des_tipo_transferencia", "");
        tab_documentacion.getColumna("ide_predio").setVisible(false);
        tab_documentacion.getColumna("CANTON").setCombo("SELECT c.des_distribucion,c.des_distribucion,pr.des_distribucion    FROM  inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE c.ide_tipo_distribucion=3 "
                + "and c.ins_ide_distribucion=pr.ide_distribucion");
        tab_documentacion.getColumna("CANTON").setAutoCompletar();
        tab_documentacion.getColumna("CANTON").setMetodoChange("cargarProvincia");
        tab_documentacion.getColumna("PROVINCIA").setLectura(true);
        tab_documentacion.getColumna("fecha_registro").setLectura(true);
        tab_documentacion.getColumna("fecha_registro").setValorDefecto(utilitario.getFechaActual());
        tab_documentacion.setTipoFormulario(true);
        tab_documentacion.getGrid().setColumns(4);
        tab_documentacion.dibujar();

        ////tabla construccion
        tab_construccion.setTabla("sigt_construccion", "ide_construccion", 5);
        tab_construccion.getColumna("ide_predio").setVisible(false);
        tab_construccion.getColumna("ide_recubrimiento").setCombo("sigc_recubrimiento", "ide_recubrimiento", "ide_recubrimiento,des_material_recubrimiento", "");
        tab_construccion.getColumna("ide_unidad_constructiva").setCombo("sigc_unidad_constructiva", "ide_unidad_constructiva", "ide_unidad_constructiva,des_unidad_constructiva", "");
        tab_construccion.getColumna("ide_uso").setCombo(tab_tabla.getColumna("IDE_USO").getListaCombo());
        tab_construccion.getColumna("ide_uso").setAutoCompletar();
        tab_construccion.getColumna("ide_calidad_construccion").setCombo("sigc_calidad_construccion", "ide_calidad_construccion", "ide_calidad_construccion,des_calidad_construccion", "");
        tab_construccion.getColumna("ide_material_cubierta").setCombo("sigc_cubierta", "ide_material_cubierta", "ide_material_cubierta,des_material_cubierta", "");
        tab_construccion.getColumna("ide_estructura").setCombo("sigc_estructura", "ide_estructura", "ide_estructura,des_estructura", "");
        tab_construccion.getColumna("ide_etapa_construccion").setCombo("sigc_etapa_construccion", "ide_etapa_construccion", "ide_etapa_construccion,des_etapa_construccion", "");
        tab_construccion.getColumna("ide_estado_conservacion").setCombo("sigc_factor_estado", "ide_estado_conservacion", "ide_estado_conservacion,des_estado_conservacion", "");
        tab_construccion.getColumna("ide_pared").setCombo("sigc_pared", "ide_pared", "ide_pared,des_pared", "");
        tab_construccion.getColumna("ide_valor_cultural").setCombo("sigc_valor_cultural", "ide_valor_cultural", "codigo,detalle", "");
        tab_construccion.getColumna("fecha_d").setLectura(true);
        tab_construccion.getColumna("fecha_d").setValorDefecto(utilitario.getFechaActual());
        tab_construccion.setTipoFormulario(true);
        tab_construccion.getColumna("ide_puerven").setCombo("sigc_puerven", "ide_puerven", "ide_puerven,des_puerven", "");
        tab_construccion.getColumna("protector_ventanas").setCombo(tab_construccion.getColumna("ide_puerven").getListaCombo());
        tab_construccion.getColumna("ventanas").setCombo(tab_construccion.getColumna("ide_puerven").getListaCombo());
        tab_construccion.getColumna("ide_valor_cultural").setCombo("sigc_valor_cultural", "ide_valor_cultural", "codigo,detalle", "");
        tab_construccion.getColumna("closets").setCombo("select ide_valor_material,des_material,valor from valt_valor_material,valc_materiales where valt_valor_material.ide_material=valc_materiales.ide_material");
        tab_construccion.getColumna("escaleras").setCombo(tab_construccion.getColumna("closets").getListaCombo());
        tab_construccion.getColumna("revestimiento_pisos").setCombo(tab_construccion.getColumna("closets").getListaCombo());
        tab_construccion.getColumna("revestimiento_interior").setCombo(tab_construccion.getColumna("closets").getListaCombo());
        tab_construccion.getColumna("sanitarios").setCombo(tab_construccion.getColumna("closets").getListaCombo());
        tab_construccion.getColumna("revestimiento_exterior").setCombo(tab_construccion.getColumna("closets").getListaCombo());
        tab_construccion.getColumna("tumbado").setCombo(tab_construccion.getColumna("closets").getListaCombo());
        tab_construccion.getColumna("piso").setCombo(tab_construccion.getColumna("closets").getListaCombo());
        tab_construccion.getColumna("columnas").setCombo(tab_construccion.getColumna("closets").getListaCombo());
        tab_construccion.getColumna("vigas").setCombo(tab_construccion.getColumna("closets").getListaCombo());
        tab_construccion.getColumna("entrepisos").setCombo(tab_construccion.getColumna("closets").getListaCombo());
        tab_construccion.getGrid().setColumns(8);
        tab_construccion.dibujar();


        ////tabla servicios
        tab_servicios.setTabla("sigt_servicios_predio", "ide_servicio_predio", 6);
        tab_servicios.getColumna("ide_predio").setVisible(false);
        tab_servicios.getColumna("ide_servicio").setCombo("select ide_servicio codigo,detalle_serv,descripcion_tipo from sigt_servicios,sigt_detalle_serv,sigt_tipo_serv where "
                + "sigt_servicios.ide_tipo_serv =sigt_tipo_serv.ide_tipo_serv and sigt_servicios.ide_detalle_serv = sigt_detalle_serv.ide_detalle_serv");
        tab_servicios.getColumna("ide_servicio").setAutoCompletar();
        tab_servicios.dibujar();

        ////tabla adicionales
        tab_adicionales.setTabla("sigt_adicionales_constructivos", "ide_adicional", 7);
        tab_adicionales.getColumna("ide_predio").setVisible(false);
        tab_adicionales.getColumna("ide_medida").setCombo("valc_medida", "ide_medida", "des_medida", "");
        tab_adicionales.getColumna("ide_material_adicional").setCombo("valc_material_adicional", "ide_material_adicional", "des_material", "");
        tab_adicionales.dibujar();

        ////tabla control
        tab_control.setTabla("sigt_control_rural", "ide_control_rural", 8);
        tab_control.getColumna("ide_cargo_control").setCombo("sigt_cargo_control", "ide_cargo_control", "detalle", "");
        tab_control.setTipoFormulario(true);
        tab_control.getGrid().setColumns(4);
        tab_control.dibujar();

        ////tabla foto
        tab_foto.setTabla("sigt_fotografia_predio", "ide_fotografia_predio", 9);
        tab_foto.setTipoFormulario(true);
        tab_foto.getColumna("ide_predio").setVisible(false);
        tab_foto.getColumna("path").setUpload("upload/fotos");
        tab_foto.getColumna("path").setImagen("256", "256");
        tab_foto.getColumna("fecha").setLectura(true);
        tab_foto.getColumna("fecha").setValorDefecto(utilitario.getFechaActual());
        tab_foto.dibujar();


        ////tabla croquis
        tab_croquis.setTabla("sigt_fotografia_croquis", "ide_fotografia_croquis", 10);
        tab_croquis.setTipoFormulario(true);
        tab_croquis.getColumna("ide_predio").setVisible(false);
        tab_croquis.getColumna("path").setUpload("upload/fotos");
        tab_croquis.getColumna("path").setImagen("256", "256");
        tab_croquis.getColumna("fecha").setLectura(true);
        tab_croquis.getColumna("fecha").setValorDefecto(utilitario.getFechaActual());
        tab_croquis.dibujar();


        ////tabla cultivos
        tab_cultivos.setTabla("sigt_cultivos", "ide_cultivos", 11);
        tab_cultivos.getColumna("ide_tipo_cultivo").setCombo("sigt_tipo_cultivo", "ide_tipo_cultivo", "codigo,detalle", "");
        tab_cultivos.dibujar();

        ////tabla agropecuaria
        tab_agropecuaria.setTabla("sigt_infra_agropecuaria", "ide_infra_agropecuaria", 12);
        tab_agropecuaria.getColumna("ide_medida").setCombo("valc_medida", "ide_medida", "des_medida", "");
        tab_agropecuaria.getColumna("ide_estado").setCombo("sigt_estados", "ide_estado", "codigo,detalle", "");
        tab_agropecuaria.getColumna("ide_infraestructura").setCombo("sigt_infraestructura", "ide_infraestructura", "codigo,detalle", "");
        tab_agropecuaria.dibujar();

        ////tabla de areas
        tab_area.setTabla("sigt_area_terreno", "ide_area_terreno", 13);
        tab_area.getColumna("ide_localizacion").setCombo("sigt_localizacion", "ide_localizacion", "codigo,detalle", "rural=true");
        tab_area.getColumna("ide_medida").setCombo("valc_medida", "ide_medida", "des_medida", "");
        tab_area.getColumna("ide_caracteristica_suelo").setVisible(false);
        tab_area.getColumna("ide_drenaje").setCombo("sigt_drenaje", "ide_drenaje", "codigo,detalle", "");
        tab_area.getColumna("ide_erosion").setCombo("sigt_erosion", "ide_erosion", "codigo,detalle", "");
        tab_area.getColumna("ide_riesgo").setCombo("sigt_riesgos", "ide_riesgo", "codigo,detalle", "");
        tab_area.getColumna("ide_topografia").setCombo("sigt_topografia", "ide_topografia", "codigo,detalle", "rural=true");
        tab_area.getColumna("ide_predio").setVisible(false);
        tab_area.setTipoFormulario(true);
        tab_area.getGrid().setColumns(6);
        tab_area.dibujar();

        ////tabla ESCRITURAS
        tab_imagen.setTabla("sigt_fotografia_documentacion", "ide_fotografia_documentacion", 14);
        tab_imagen.setTipoFormulario(true);
        tab_imagen.getColumna("ide_predio").setVisible(false);
        tab_imagen.getColumna("path").setUpload("upload/fotos");
        tab_imagen.getColumna("path").setImagen("256", "256");
        tab_imagen.dibujar();

        div_division.setId("div_division");
        div_division.dividir2(pat_panel, tab_tabulador, "60%", "H");

        gru_pantalla.getChildren().add(bar_botones);
        gru_pantalla.getChildren().add(div_division);

        ///////////////////Dialogo
        dia_panel.setId("dia_panel");
        dia_panel.setTitle("Avaluar Construcción Urbana");
        dia_panel.setWidth("45%");
        dia_panel.setResizable(false);
        dia_panel.setHeight("35%");

        Grid gri_matriz = new Grid();
        gri_matriz.setColumns(4);

        Grid gri_fecha = new Grid();
        gri_fecha.setColumns(2);

        Etiqueta eti0 = new Etiqueta();
        eti0.setValue("Fecha:");

        cal_avalua.setTipoBoton(true);
        gri_fecha.getChildren().add(eti0);
        gri_fecha.getChildren().add(cal_avalua);

        gri_matriz.setHeader(gri_fecha);

        Etiqueta eti_provincia = new Etiqueta();
        eti_provincia.setValue("PROVINCIA :");
        gri_matriz.getChildren().add(eti_provincia);
        tex_provincia.setId("tex_provincia");
        tex_provincia.setMetodoKeyPress("borrarValores", "tex_canton,tex_parroquia,tex_zona,tex_sector,tex_poligono,aut_distribucion");
        tex_provincia.setMaxlength(2);
        tex_provincia.setSize(3);
        gri_matriz.getChildren().add(tex_provincia);

        Etiqueta eti_canton = new Etiqueta();
        eti_canton.setValue("CANTON :");
        gri_matriz.getChildren().add(eti_canton);
        tex_canton.setId("tex_canton");
        tex_canton.setMetodoKeyPress("borrarValores", "tex_provincia,tex_parroquia,tex_zona,tex_sector,tex_poligono,aut_distribucion");
        tex_canton.setMaxlength(2);
        tex_canton.setSize(3);
        gri_matriz.getChildren().add(tex_canton);

        Etiqueta eti_parroquia = new Etiqueta();
        eti_parroquia.setValue("PARROQUIA :");
        gri_matriz.getChildren().add(eti_parroquia);
        tex_parroquia.setId("tex_parroquia");
        tex_parroquia.setMetodoKeyPress("borrarValores", "tex_provincia,tex_canton,tex_zona,tex_sector,tex_poligono,aut_distribucion");
        tex_parroquia.setMaxlength(2);
        tex_parroquia.setSize(3);
        gri_matriz.getChildren().add(tex_parroquia);

        Etiqueta eti_zona = new Etiqueta();
        eti_zona.setValue("ZONA :");
        gri_matriz.getChildren().add(eti_zona);
        tex_zona.setId("tex_zona");
        tex_zona.setMetodoKeyPress("borrarValores", "tex_provincia,tex_canton,tex_parroquia,tex_sector,tex_poligono,aut_distribucion");
        tex_zona.setMaxlength(2);
        tex_zona.setSize(3);
        gri_matriz.getChildren().add(tex_zona);

        Etiqueta eti_sector = new Etiqueta();
        eti_sector.setValue("SECTOR :");
        gri_matriz.getChildren().add(eti_sector);
        tex_sector.setId("tex_sector");
        tex_sector.setMetodoKeyPress("borrarValores", "tex_provincia,tex_canton,tex_parroquia,tex_zona,tex_poligono,aut_distribucion");
        tex_sector.setMaxlength(2);
        tex_sector.setSize(3);
        gri_matriz.getChildren().add(tex_sector);

        Etiqueta eti_poligono = new Etiqueta();
        eti_poligono.setValue("POLIGONO :");
        gri_matriz.getChildren().add(eti_poligono);
        tex_poligono.setId("tex_poligono");
        tex_poligono.setMetodoKeyPress("borrarValores", "tex_provincia,tex_canton,tex_parroquia,tex_zona,tex_sector,aut_distribucion");
        tex_poligono.setMaxlength(3);
        tex_poligono.setSize(3);
        gri_matriz.getChildren().add(tex_poligono);

        Grid gri_barrio = new Grid();
        gri_barrio.setColumns(2);
        Etiqueta eti_barrio = new Etiqueta();
        eti_barrio.setValue("BARRIO:");

        gri_barrio.getChildren().add(eti_barrio);
        aut_distribucion.setId("aut_distribucion");
        aut_distribucion.setAutoCompletar(tab_tabla.getColumna("ide_distribucion").getListaCombo());
        aut_distribucion.setMetodoChange("borrarValores", "tex_provincia,tex_canton,tex_zona,tex_sector,tex_poligono,tex_parroquia");
        gri_barrio.getChildren().add(aut_distribucion);
        aut_distribucion.setSize(85);
        gri_matriz.setFooter(gri_barrio);


        dia_panel.setDialogo(gri_matriz);
        dia_panel.getBot_aceptar().setMetodo("aceptarAvaluarVarios");
        dia_panel.getBot_aceptar().setUpdate("dia_panel,tab_tabulador:tab_construccion");
        gru_pantalla.getChildren().add(dia_panel);

        sec_calendario.setId("sec_calendario");
        sec_calendario.setMultiple(false);
        sec_calendario.getBot_aceptar().setUpdate("sec_calendario,tab_tabulador:tab_construccion");
        sec_calendario.getBot_aceptar().setMetodo("avaluar");
        gru_pantalla.getChildren().add(sec_calendario);

        //cambio de dominio
        dia_cambio.setId("dia_cambio");
        dia_cambio.setWidth("65%");
        dia_cambio.setHeight("85%");
        dia_cambio.setTitle("Cambiar de Dominio");
        Grid gri_cambio = new Grid();
        gri_cambio.setStyle("width:" + (dia_cambio.getAnchoPanel() - 5) + "px;height:" + dia_cambio.getAltoPanel() + "px;overflow: auto;display: block;");
        Etiqueta eti = new Etiqueta();
        eti.setEstiloCabecera("");
        eti.setValue("DOMINIO ACTUAL");
        gri_cambio.getChildren().add(eti);
        tab_cambio.setId("tab_cambio");
        tab_cambio.setSql("select ide_titular,nombre,des_tipo_documento,cedula,porcentaje from sigt_titular_dominio,rec_clientes,sigc_tipo_documento where rec_clientes.ide_cliente =sigt_titular_dominio.ide_cliente and sigt_titular_dominio.ide_tipo_documento=sigc_tipo_documento.ide_tipo_documento and ide_predio=-1");
        tab_cambio.setCampoPrimaria("ide_titular");
        tab_cambio.getColumna("ide_titular").setVisible(false);
//        tab_cambio.setRows(8);
        tab_cambio.setStyle("height:200px;");
        tab_cambio.setLectura(true);
        tab_cambio.dibujar();
        gri_cambio.getChildren().add(tab_cambio);
        ///nuevo nominio
        Etiqueta etin = new Etiqueta();
        etin.setEstiloCabecera("");
        etin.setValue("CAMBIAR DE DOMINIO");
        etin.setTitle("Dar click derecho en la tabla y seleccionar insertar");
        gri_cambio.getChildren().add(etin);

        tab_nuevo.setId("tab_nuevo");
        tab_nuevo.setTabla("sigt_titular_dominio", "ide_titular", 15);
        tab_nuevo.getColumna("ide_predio").setVisible(false);
        tab_nuevo.getColumna("IDE_TIPO_DOCUMENTO").setCombo("sigc_tipo_documento", "IDE_TIPO_DOCUMENTO", "DES_TIPO_DOCUMENTO", "");
        tab_nuevo.getColumna("DOCUMENTO").setLectura(true);
        tab_nuevo.getColumna("IDE_TIPO_DOCUMENTO").setLectura(true);
        tab_nuevo.getColumna("FECHA_NACIMIENTO").setVisible(false);
        tab_nuevo.getColumna("ide_estado_civil").setVisible(false);
        tab_nuevo.getColumna("ide_predio").setVisible(false);

        tab_nuevo.getColumna("ide_cliente").setCombo(tab_dominio.getColumna("ide_cliente").getListaCombo());
        tab_nuevo.getColumna("ide_cliente").setAutoCompletar();
        tab_nuevo.getColumna("IDE_CLIENTE").setMetodoChange("buscarDatosClienteCambio");
        tab_nuevo.getColumna("porcentaje").setValorDefecto("100");
        tab_nuevo.getColumna("porcentaje").setLectura(true);
        tab_nuevo.getColumna("EMISION").setValorDefecto("true");
        tab_nuevo.getColumna("fecha_posesion").setLectura(true);
        tab_nuevo.getColumna("fecha_posesion").setValorDefecto(utilitario.getFechaActual());
        tab_nuevo.setCondicion("ide_predio_rural=-1");
        tab_nuevo.dibujar();
        tab_nuevo.setCondicion("");
        PanelTabla pat_panel15 = new PanelTabla();
        pat_panel15.setPanelTabla(tab_nuevo);
        pat_panel15.getMenuTabla().getItem_guardar().setRendered(false);
        gri_cambio.getChildren().add(pat_panel15);



        dia_cambio.getBot_aceptar().setMetodo("cambiarDominio");
        dia_cambio.getBot_aceptar().setUpdate("dia_cambio");
        dia_cambio.getBot_aceptar().setMetodo("aceptarCambiarDominio");
        dia_cambio.getBot_aceptar().setUpdate("tab_tabulador:tab_dominio,dia_cambio");

        dia_cambio.setDialogo(gri_cambio);
        gru_pantalla.getChildren().add(dia_cambio);

        //buscar
        auc_cliente.setId("auc_cliente");
        auc_cliente.setAutoCompletar(tab_dominio.getColumna("ide_cliente").getListaCombo());
        auc_cliente.setMetodoChange("busca_Ficha", "tab_busca_titulo");
        auc_cliente.setSize(80);
        Grid gri_busca = new Grid();
        Grupo gru_grupo = new Grupo();
        gru_grupo.getChildren().add(auc_cliente);

        maa_marca.setFor("auc_cliente");
        maa_marca.setValue("Buscar Contribuyente");
        gru_grupo.getChildren().add(maa_marca);

        bot_limpiar.setIcon("ui-icon-cancel");
        bot_limpiar.setTitle("Limpiar");
        bot_limpiar.setMetodo("limpiar");
        bot_limpiar.setUpdate("tab_busca_titulo,auc_cliente");
        gru_grupo.getChildren().add(bot_limpiar);

        gri_busca.setHeader(gru_grupo);
        tab_busca_titulo.setId("tab_busca_titulo");
        tab_busca_titulo.setSql("SELECT ide_predio_rural,nombre,clave from  sigt_predio_rural where ide_predio_rural=-1");
        tab_busca_titulo.setRows(10);
        tab_busca_titulo.setLectura(true);
        tab_busca_titulo.setCampoPrimaria("ide_predio_rural");
        tab_busca_titulo.dibujar();
        tab_busca_titulo.setStyle("width:" + (dia_dialogo.getAnchoPanel() - 15) + "px");


        gri_busca.getChildren().add(tab_busca_titulo);
        gri_busca.setStyle("width:" + (dia_dialogo.getAnchoPanel() - 5) + "px;height:" + dia_dialogo.getAltoPanel() + "px;overflow: auto;display: block;");

        dia_dialogo.setId("dia_dialogo");
        dia_dialogo.setTitle("Buscar Ficha");
        dia_dialogo.setResizable(false);
        dia_dialogo.setDialogo(gri_busca);
        dia_dialogo.getBot_aceptar().setMetodo("aceptaBuscar");
        gru_pantalla.getChildren().add(dia_dialogo);
       
    }

    public void limpiar() {
        auc_cliente.limpiar();
        tab_busca_titulo.limpiar();
    }

    public void aceptaBuscar() {
        if (tab_busca_titulo.getValorSeleccionado() != null) {
            tab_tabla.setCondicion("ide_predio_rural=" + tab_busca_titulo.getValorSeleccionado());
            tab_tabla.setCondicionBuscar("");
            tab_tabla.ejecutarSql();
            tab_tabla.actualizarRelaciones();
            tab_tabla.setCondicion("");
            dia_dialogo.cerrar();
            utilitario.addUpdate("tab_tabla,tab_tabulador:tab_cultivos,tab_tabulador:tab_area,tab_tabulador:tab_dominio,tab_tabulador:tab_tenecias,tab_tabulador:tab_documentacion,tab_tabulador:tab_construccion,tab_tabulador:tab_servicios,tab_tabulador:tab_adicionales,tab_tabulador:tab_control,tab_tabulador:tab_foto,tab_tabulador:tab_croquis,tab_tabulador:tab_agropecuaria,tab_tabulador:tab_imagen,dia_dialogo");
        }
    }

    public void busca_Ficha(SelectEvent evt) {
        auc_cliente.onSelect(evt);
        tab_busca_titulo.setSql("SELECT ide_predio_rural,nombre,clave from  sigt_predio_rural where ide_predio_rural=" + auc_cliente.getValor());
        tab_busca_titulo.ejecutarSql();
    }

    public void aceptarCambiarDominio() {
        if (tab_nuevo.getTotalFilas() > 0) {
            Tabla tab_hist = new Tabla();
            tab_hist.setTabla("sigt_cambio_dominio", "ide_cambio_dominio", 0);
            for (int i = 0; i < tab_dominio.getTotalFilas(); i++) {
                tab_hist.insertar();
                tab_hist.setValor("ide_cliente", tab_dominio.getValor(i, "ide_cliente"));
                tab_hist.setValor("ide_estado_civil", tab_dominio.getValor(i, "ide_estado_civil"));
                tab_hist.setValor("ide_tipo_documento", tab_dominio.getValor(i, "ide_tipo_documento"));
                tab_hist.setValor("documento", tab_dominio.getValor(i, "documento"));
                tab_hist.setValor("fecha_nacimiento", tab_dominio.getValor(i, "fecha_nacimiento"));
                tab_hist.setValor("porcentaje", tab_dominio.getValor(i, "porcentaje"));
                tab_hist.setValor("fecha_inicial", tab_dominio.getValor(i, "fecha_posesion"));
                tab_hist.setValor("fecha_final", utilitario.getFechaActual());
                tab_hist.setValor("ide_predio", tab_tabla.getValor(i, "ide_predio"));
                tab_hist.setValor("ide_empleado", obtener_ide_empleado());
                tab_hist.setValor("responsable", obtener_responsable());
                tab_hist.setValor("ip_responsable", utilitario.getIp());
                tab_hist.setValor("fecha_ingreso", utilitario.getFechaActual());
            }
            tab_dominio.getConexion().agregarSqlPantalla("delete from sigt_titular_dominio where ide_predio=" + tab_tabla.getValor("ide_predio"));
            tab_hist.guardar();
            tab_nuevo.guardar();
            utilitario.getConexion().guardarPantalla();
            tab_dominio.ejecutarSql();
        }
        dia_cambio.cerrar();
    }

    public String obtener_ide_empleado() {
        List list_sql1 = utilitario.getConexion().consultar("select ide_empleado from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
            return String.valueOf(list_sql1.get(0));
        } else {
            return null;
        }
    }

    public String obtener_responsable() {
        List list_sql1 = utilitario.getConexion().consultar("select nom_usua from sis_usuario where ide_usua=" + utilitario.getVariable("ide_usua"));
        if (!list_sql1.isEmpty() && list_sql1.get(0) != null) {
            return String.valueOf(list_sql1.get(0));
        } else {
            return null;
        }
    }

    public void cambiarDominio() {
        if (tab_tabla.getTotalFilas() > 0 && !tab_tabla.isFilaInsertada() && tab_dominio.getTotalFilas() > 0) {
            tab_cambio.setSql("select ide_titular,nombre,des_tipo_documento,cedula,porcentaje from sigt_titular_dominio,rec_clientes,sigc_tipo_documento where  rec_clientes.ide_cliente =sigt_titular_dominio.ide_cliente  and rec_clientes.ide_tipo_documento=sigc_tipo_documento.ide_tipo_documento and ide_predio_rural=" + tab_tabla.getValor("ide_predio_rural"));

            tab_cambio.ejecutarSql();
            tab_nuevo.getColumna("ide_predio_rural").setValorDefecto(tab_tabla.getValor("ide_predio_rural"));
            tab_nuevo.limpiar();
            dia_cambio.dibujar();
        } else {
            utilitario.agregarMensajeInfo("No se puede cambiar de dominio la ficha actual", "");
        }

    }

    public void buscarCodigos(SelectEvent evt) {
        tab_tabla.modificar(evt);
        //Busca y asigna los códigos de provincia canton
        //1) Sector
        List lis = utilitario.getConexion().consultar("SELECT s.cod_distribucion   FROM  inst_distribucion_politica b,inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE b.ide_tipo_distribucion=7 "
                + " and b.ins_ide_distribucion=s.ide_distribucion "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and b.ide_distribucion=" + tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "ide_distribucion"));
        tab_tabla.setValor("sector", lis.get(0) + "");
        //2) zona
        lis = utilitario.getConexion().consultar("SELECT z.cod_distribucion   FROM  inst_distribucion_politica b,inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE b.ide_tipo_distribucion=7 "
                + " and b.ins_ide_distribucion=s.ide_distribucion "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and b.ide_distribucion=" + tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "ide_distribucion"));


        tab_tabla.setValor(tab_tabla.getUltimaFilaModificada(), "zona", lis.get(0) + "");
        //3) parroquia
        lis = utilitario.getConexion().consultar("SELECT p.cod_distribucion   FROM  inst_distribucion_politica b,inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE b.ide_tipo_distribucion=7 "
                + " and b.ins_ide_distribucion=s.ide_distribucion "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and b.ide_distribucion=" + tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "ide_distribucion"));

        tab_tabla.setValor(tab_tabla.getUltimaFilaModificada(), "cod_parroquia", lis.get(0) + "");
        //3) canton
        lis = utilitario.getConexion().consultar("SELECT c.cod_distribucion   FROM  inst_distribucion_politica b,inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE b.ide_tipo_distribucion=7 "
                + " and b.ins_ide_distribucion=s.ide_distribucion "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and b.ide_distribucion=" + tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "ide_distribucion"));


        tab_tabla.setValor(tab_tabla.getUltimaFilaModificada(), "cod_canton", lis.get(0) + "");
        //3) provinica
        lis = utilitario.getConexion().consultar("SELECT pr.cod_distribucion   FROM  inst_distribucion_politica b,inst_distribucion_politica s ,inst_distribucion_politica z,inst_distribucion_politica p,inst_distribucion_politica c,inst_distribucion_politica pr "
                + " WHERE b.ide_tipo_distribucion=7 "
                + " and b.ins_ide_distribucion=s.ide_distribucion "
                + " and s.ins_ide_distribucion=z.ide_distribucion "
                + " and z.ins_ide_distribucion=p.ide_distribucion "
                + " and p.ins_ide_distribucion=c.ide_distribucion "
                + " and c.ins_ide_distribucion=pr.ide_distribucion"
                + " and b.ide_distribucion=" + tab_tabla.getValor(tab_tabla.getUltimaFilaModificada(), "ide_distribucion"));


        tab_tabla.setValor(tab_tabla.getUltimaFilaModificada(), "cod_provincia", lis.get(0) + "");
        //utilitario.addUpdateTabla(tab_tabla, "cod_provinicia,cod_canton,cod_parroquia,zona,sector", "");
        utilitario.addUpdate("tab_tabla");
        formarClave();
    }

    public void seleccionarTipoPropiedad(AjaxBehaviorEvent evt) {
        //Cuando selecciona un tipo de propiedad
        tab_tabla.modificar(evt);
        if (tab_tabla.getValor("IDE_TIPO_PROPIEDAD").equals("2")) {
            tab_dominio.getColumna("porcentaje").setLectura(false);

        } else {
            tab_dominio.getColumna("porcentaje").setLectura(true);
        }
        utilitario.addUpdate("tab_tabulador:tab_dominio");
    }

    public void borrarValores(SelectEvent evt) {
        aut_distribucion.onSelect(evt);
        tex_canton.setValue("");
        tex_provincia.setValue("");
        tex_parroquia.setValue("");
        tex_poligono.setValue("");
        tex_sector.setValue("");
        tex_zona.setValue("");
    }

    public void avaluarVarios() {
        dia_panel.dibujar();
    }

    public void borrarValores(AjaxBehaviorEvent evt) {
        String str_id = evt.getComponent().getId();
        if (!tex_provincia.getId().equals(str_id)) {
            tex_provincia.setValue("");
        }
        if (!tex_canton.getId().equals(str_id)) {
            tex_canton.setValue("");
        }
        if (!tex_parroquia.getId().equals(str_id)) {
            tex_parroquia.setValue("");
        }
        if (!tex_zona.getId().equals(str_id)) {
            tex_zona.setValue("");
        }
        if (!tex_sector.getId().equals(str_id)) {
            tex_sector.setValue("");
        }
        if (!tex_poligono.getId().equals(str_id)) {
            tex_poligono.setValue("");
        }
        aut_distribucion.setValue(null);
    }

    public void avaluar() {
        if (!tab_tabla.isFilaInsertada()) {
            if (!sec_calendario.isVisible()) {
                sec_calendario.dibujar();
            } else {
                if (utilitario.isFechaValida(sec_calendario.getFecha1String())) {
                    cls_avaluos cls_avaluar = new cls_avaluos();
                    cls_avaluar.avaluarConstruccionRural(tab_tabla.getValorSeleccionado(), sec_calendario.getFecha1String());
                    utilitario.agregarMensaje("Se avaluo correctamente el predio", "");
                }
                sec_calendario.cerrar();
            }
        } else {
            utilitario.agregarMensajeInfo("No se puede avaluar la ficha actual", "");
        }
    }

    public void aceptarAvaluarVarios() {
        String str_avaluar_varios = "";
        if (tex_provincia.getValue() != null && !tex_provincia.getValue().toString().isEmpty()) {
            str_avaluar_varios = "COD_PROVINCIA='" + tex_provincia.getValue() + "'";
        } else if (tex_canton.getValue() != null && !tex_canton.getValue().toString().isEmpty()) {
            str_avaluar_varios = "COD_CANTON='" + tex_canton.getValue() + "'";
        } else if (tex_parroquia.getValue() != null && !tex_parroquia.getValue().toString().isEmpty()) {
            str_avaluar_varios = "COD_PARROQUIA='" + tex_parroquia.getValue() + "'";
        } else if (tex_zona.getValue() != null && !tex_zona.getValue().toString().isEmpty()) {
            str_avaluar_varios = "ZONA='" + tex_zona.getValue() + "'";
        } else if (tex_sector.getValue() != null && !tex_sector.getValue().toString().isEmpty()) {
            str_avaluar_varios = "SECTOR='" + tex_sector.getValue() + "'";
        } else if (tex_poligono.getValue() != null && !tex_poligono.getValue().toString().isEmpty()) {
            str_avaluar_varios = "POLIGONO='" + tex_poligono.getValue() + "'";
        } else if (aut_distribucion.getValor() != null && !aut_distribucion.getValor().toString().isEmpty()) {
            str_avaluar_varios = "IDE_DISTRIBUCION=" + aut_distribucion.getValor();
        }

        if (!str_avaluar_varios.isEmpty()) {

            List lis = utilitario.getConexion().consultar("SELECT IDE_PREDIO_RURAL FROM sigt_predio_rural where " + str_avaluar_varios);
            System.out.println("SELECT IDE_PREDIO_RURAL FROM sigt_predio_rural where " + str_avaluar_varios);
            cls_avaluos cls_avaluar = new cls_avaluos();

            if (!lis.isEmpty()) {
                if (cal_avalua.getDate() != null) {
                    for (int i = 0; i < lis.size(); i++) {
                        System.out.println("...  " + lis.get(i));
                        cls_avaluar.avaluarConstruccionRural(lis.get(i) + "", cal_avalua.getFecha());
                    }
                    utilitario.agregarMensaje("Se avaluaron correctamente " + (lis.size()) + " predios ", "");

                }
            }
        }
        dia_panel.cerrar();
    }

    public void formarClave() {
        //Forma la clave
        if (tab_tabla.getValor("IDE_DISTRIBUCION") != null) {
            String str_clave =  tab_tabla.getValor("COD_PROVINCIA") + tab_tabla.getValor("COD_CANTON") + tab_tabla.getValor("COD_PARROQUIA") + tab_tabla.getValor("ZONA") + tab_tabla.getValor("SECTOR") + tab_tabla.getValor("POLIGONO") + tab_tabla.getValor("LOTE") + tab_tabla.getValor("DIVISION");
            tab_tabla.setValor("CLAVE", str_clave);
            utilitario.addUpdateTabla(tab_tabla, "CLAVE", "");
        }
    }

    public void formarClave(AjaxBehaviorEvent evt) {
        tab_tabla.modificar(evt);
        //Forma la clave
        if (tab_tabla.getValor("IDE_DISTRIBUCION") != null) {
            String str_clave = tab_tabla.getValor("COD_PROVINCIA") + tab_tabla.getValor("COD_CANTON") + tab_tabla.getValor("COD_PARROQUIA") + tab_tabla.getValor("ZONA") + tab_tabla.getValor("SECTOR") + tab_tabla.getValor("POLIGONO") + tab_tabla.getValor("LOTE") + tab_tabla.getValor("DIVISION");
            tab_tabla.setValor("CLAVE", str_clave);
            utilitario.addUpdateTabla(tab_tabla, "CLAVE", "");
        }
    }

    public void cargarProvincia(SelectEvent evt) {
        tab_documentacion.modificar(evt);
        tab_documentacion.setValor("PROVINCIA", "");
        List lis = utilitario.getConexion().consultar("SELECT pr.des_distribucion from inst_distribucion_politica c,inst_distribucion_politica pr where c.des_distribucion='" + tab_documentacion.getValor("CANTON") + "' and c.ins_ide_distribucion=pr.ide_distribucion");
        if (!lis.isEmpty()) {
            tab_documentacion.setValor("PROVINCIA", lis.get(0) + "");

        }
        utilitario.addUpdateTabla(tab_documentacion, "provincia", "");
    }

    public void buscarDatosCliente(SelectEvent evt) {
        tab_dominio.modificar(evt);
        List lis = utilitario.getConexion().consultar("SELECT ide_tipo_documento,cedula,fec_nacimiento,ide_estado_civil from rec_clientes where ide_cliente= " + tab_dominio.getValor(tab_dominio.getUltimaFilaModificada(), "ide_cliente"));
        if (lis != null) {
            Object[] obj_fila = (Object[]) lis.get(0);
            tab_dominio.setValor(tab_dominio.getUltimaFilaModificada(), "IDE_TIPO_DOCUMENTO", obj_fila[0] + "");
            tab_dominio.setValor(tab_dominio.getUltimaFilaModificada(), "DOCUMENTO", obj_fila[1] + "");
            if (obj_fila[2] != null) {
                tab_dominio.setValor(tab_dominio.getUltimaFilaModificada(), "FECHA_NACIMIENTO", obj_fila[2] + "");
            }
            tab_dominio.setValor(tab_dominio.getUltimaFilaModificada(), "ide_estado_civil", obj_fila[3] + "");
            utilitario.addUpdateTabla(tab_dominio, "IDE_TIPO_DOCUMENTO,DOCUMENTO,FECHA_NACIMIENTO,ide_estado_civil", "");
        }
    }

    public void buscarDatosClienteCambio(SelectEvent evt) {
        tab_nuevo.modificar(evt);
        List lis = utilitario.getConexion().consultar("SELECT ide_tipo_documento,cedula,fec_nacimiento,ide_estado_civil from rec_clientes where ide_cliente= " + tab_nuevo.getValor(tab_nuevo.getUltimaFilaModificada(), "ide_cliente"));
        if (lis != null) {
            Object[] obj_fila = (Object[]) lis.get(0);
            tab_nuevo.setValor(tab_nuevo.getUltimaFilaModificada(), "IDE_TIPO_DOCUMENTO", obj_fila[0] + "");
            tab_nuevo.setValor(tab_nuevo.getUltimaFilaModificada(), "DOCUMENTO", obj_fila[1] + "");
            if (obj_fila[2] != null) {
                tab_nuevo.setValor(tab_nuevo.getUltimaFilaModificada(), "FECHA_NACIMIENTO", obj_fila[2] + "");
            }
            tab_nuevo.setValor(tab_nuevo.getUltimaFilaModificada(), "ide_estado_civil", obj_fila[3] + "");
            utilitario.addUpdateTabla(tab_nuevo, "IDE_TIPO_DOCUMENTO,DOCUMENTO,FECHA_NACIMIENTO,ide_estado_civil", "");
        }
    }

    public void seleccionar_tabla1(SelectEvent evt) {
        tab_tabla.seleccionarFila(evt);
    }

    public boolean validarDominio() {
        if (!tab_tabla.getValor("IDE_TIPO_PROPIEDAD").equals("2")) {
            if (tab_dominio.getTotalFilas() == 0) {
                utilitario.agregarMensajeError("No se puede guardar", "Debe insertar un titular del dominio");
                return false;
            }
            if (tab_dominio.getTotalFilas() > 1) {
                utilitario.agregarMensajeError("No se puede guardar", "Solo debe existir un titular del dominio");
                return false;
            }
        }
        //valido porcentaje y emision
        double dob_total = 0;
        int num_emitidos = 0;
        for (int i = 0; i < tab_dominio.getTotalFilas(); i++) {
            try {
                dob_total += Double.parseDouble(tab_dominio.getValor(i, "porcentaje"));
            } catch (Exception e) {
            }
            if (tab_dominio.getValor(i, "EMISION").equals("true")) {
                num_emitidos++;
            }
        }
        if (num_emitidos != 0) {
            if (tab_tabla.getValor("IDE_TIPO_PROPIEDAD").equals("2") && (num_emitidos > 1 && num_emitidos != tab_dominio.getTotalFilas())) {
                utilitario.agregarMensajeError("No se puede guardar", "La emsión de los títulos del dominio no es válida");
                return false;
            }
            if (!tab_tabla.getValor("IDE_TIPO_PROPIEDAD").equals("2") && num_emitidos != 1) {
                utilitario.agregarMensajeError("No se puede guardar", "La emsión de los títulos del dominio no es válida");
                return false;
            }
        } else {
            utilitario.agregarMensajeError("No se puede guardar", "Debe seleccionar la emisión del titulo del dominio ");
            return false;
        }

        if (dob_total != 100.0) {
            utilitario.agregarMensajeError("No se puede guardar", "La suma del porcentaje del dominio debe ser igual a 100%");
            return false;
        }
        return true;
    }

    @Override
    public void insertar() {
        if (tab_nuevo.isFocus()) {
            if (tab_tabla.getValor("IDE_TIPO_PROPIEDAD") != null && tab_tabla.getValor("IDE_TIPO_PROPIEDAD").equals("2")) {
                tab_nuevo.getColumna("porcentaje").setLectura(false);
                tab_nuevo.insertar();
            } else {
                if (tab_nuevo.getTotalFilas() < 1) {
                    tab_nuevo.insertar();
                    tab_nuevo.getColumna("porcentaje").setLectura(true);
                } else {
                    utilitario.agregarMensajeInfo("No se puede Insertar", "El predio seleccionado es de tipo unipropiedad");
                }
            }
        } else if (tab_dominio.isFocus()) {

            if (tab_tabla.getValor("IDE_TIPO_PROPIEDAD") != null && tab_tabla.getValor("IDE_TIPO_PROPIEDAD").equals("2")) {
                tab_dominio.getColumna("porcentaje").setLectura(false);
                tab_dominio.insertar();
            } else {
                if (tab_dominio.getTotalFilas() < 1) {
                    tab_dominio.insertar();
                    tab_dominio.getColumna("porcentaje").setLectura(true);
                } else {
                    utilitario.agregarMensajeInfo("No se puede Insertar", "El predio seleccionado es de tipo unipropiedad");
                }
            }
        } else {
            utilitario.getTablaisFocus().insertar();
        }
    }

    public boolean validar() {
        //Aqui las validaciones 

        //1) La clave
        String str_clave = tab_tabla.getValor("CLAVE");
        if (str_clave != null && str_clave.length() == 18) {
            //Validar ruc persona Juridica
            String str_ruc = tab_tabla.getValor("CEDULA");
            if (str_ruc != null && !str_ruc.isEmpty()) {
                if (!utilitario.validarRUC(str_ruc)) {
                    utilitario.agregarMensajeError("No se puede guardar", "La Cedula ingresada no es válido");
                    return false;
                }
            }
            return true;
        } else {
            utilitario.agregarMensajeError("No se puede guardar", "La clave generada no es válida");
            return false;
        }
    }

    @Override
    public void guardar() {
        if (validar() && validarDominio()) {
            tab_tabla.guardar();
            tab_dominio.guardar();
            tab_area.guardar();
            tab_tenecias.guardar();
            tab_documentacion.guardar();
            tab_construccion.guardar();
            tab_servicios.guardar();
            tab_adicionales.guardar();
            tab_control.guardar();
            tab_cultivos.guardar();
            tab_agropecuaria.guardar();
            tab_foto.guardar();
            tab_croquis.guardar();
            utilitario.getConexion().guardarPantalla();
        }
    }

    @Override
    public void eliminar() {
        utilitario.getTablaisFocus().eliminar();
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

    public Tabla getTab_dominio() {
        return tab_dominio;
    }

    public void setTab_dominio(Tabla tab_dominio) {
        this.tab_dominio = tab_dominio;
    }

    public Tabla getTab_area() {
        return tab_area;
    }

    public void setTab_area(Tabla tab_area) {
        this.tab_area = tab_area;
    }

    public Tabla getTab_documentacion() {
        return tab_documentacion;
    }

    public void setTab_documentacion(Tabla tab_documentacion) {
        this.tab_documentacion = tab_documentacion;
    }

    public Tabla getTab_construccion() {
        return tab_construccion;
    }

    public void setTab_construccion(Tabla tab_construccion) {
        this.tab_construccion = tab_construccion;
    }

    public Tabla getTab_adicionales() {
        return tab_adicionales;
    }

    public void setTab_adicionales(Tabla tab_adicionales) {
        this.tab_adicionales = tab_adicionales;
    }

    public Tabla getTab_control() {
        return tab_control;
    }

    public void setTab_control(Tabla tab_control) {
        this.tab_control = tab_control;
    }

    public Tabulador getTab_tabulador() {
        return tab_tabulador;
    }

    public void setTab_tabulador(Tabulador tab_tabulador) {
        this.tab_tabulador = tab_tabulador;
    }

    public Tabla getTab_servicios() {
        return tab_servicios;
    }

    public void setTab_servicios(Tabla tab_servicios) {
        this.tab_servicios = tab_servicios;
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

    public void inicio() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.inicio();
            if (tab_tabla.isFocus()) {
                if (tab_tabla.getValor("IDE_TIPO_PROPIEDAD") != null && tab_tabla.getValor("IDE_TIPO_PROPIEDAD").equals("2")) {
                    tab_dominio.getColumna("porcentaje").setLectura(false);

                } else {
                    tab_dominio.getColumna("porcentaje").setLectura(true);
                }
            }
        }

    }

    public void fin() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.fin();
            if (tab_tabla.isFocus()) {
                if (tab_tabla.getValor("IDE_TIPO_PROPIEDAD") != null && tab_tabla.getValor("IDE_TIPO_PROPIEDAD").equals("2")) {
                    tab_dominio.getColumna("porcentaje").setLectura(false);

                } else {
                    tab_dominio.getColumna("porcentaje").setLectura(true);
                }
            }
        }
    }

    public void siguiente() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.siguiente();
            if (tab_tabla.isFocus()) {
                if (tab_tabla.getValor("IDE_TIPO_PROPIEDAD") != null && tab_tabla.getValor("IDE_TIPO_PROPIEDAD").equals("2")) {
                    tab_dominio.getColumna("porcentaje").setLectura(false);
                } else {
                    tab_dominio.getColumna("porcentaje").setLectura(true);
                }
            }
        }
    }

    public void BuscarFicha() {
        dia_dialogo.dibujar();
    }

    public void atras() {
        Tabla tabla_foco = utilitario.getTablaisFocus();
        if (tabla_foco != null) {
            tabla_foco.atras();
            if (tab_tabla.isFocus()) {
                if (tab_tabla.getValor("IDE_TIPO_PROPIEDAD") != null && tab_tabla.getValor("IDE_TIPO_PROPIEDAD").equals("2")) {
                    tab_dominio.getColumna("porcentaje").setLectura(false);

                } else {
                    tab_dominio.getColumna("porcentaje").setLectura(true);
                }
            }
        }
    }

    public AutoCompletar getAut_distribucion() {
        return aut_distribucion;
    }

    public void setAut_distribucion(AutoCompletar aut_distribucion) {
        this.aut_distribucion = aut_distribucion;
    }

    public Dialogo getDia_panel() {
        return dia_panel;
    }

    public void setDia_panel(Dialogo dia_panel) {
        this.dia_panel = dia_panel;
    }

    public SeleccionCalendario getSec_calendario() {
        return sec_calendario;
    }

    public void setSec_calendario(SeleccionCalendario sec_calendario) {
        this.sec_calendario = sec_calendario;
    }

    public Dialogo getDia_cambio() {
        return dia_cambio;
    }

    public void setDia_cambio(Dialogo dia_cambio) {
        this.dia_cambio = dia_cambio;
    }

    public Tabla getTab_cambio() {
        return tab_cambio;
    }

    public void setTab_cambio(Tabla tab_cambio) {
        this.tab_cambio = tab_cambio;
    }

    public Tabla getTab_nuevo() {
        return tab_nuevo;
    }

    public void setTab_nuevo(Tabla tab_nuevo) {
        this.tab_nuevo = tab_nuevo;
    }

    public AutoCompletar getAuc_cliente() {
        return auc_cliente;
    }

    public void setAuc_cliente(AutoCompletar auc_cliente) {
        this.auc_cliente = auc_cliente;
    }

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

    public Tabla getTab_imagen() {
        return tab_imagen;
    }

    public void setTab_imagen(Tabla tab_imagen) {
        this.tab_imagen = tab_imagen;
    }

    public Tabla getTab_agropecuaria() {
        return tab_agropecuaria;
    }

    public void setTab_agropecuaria(Tabla tab_agropecuaria) {
        this.tab_agropecuaria = tab_agropecuaria;
    }

    public Tabla getTab_cultivos() {
        return tab_cultivos;
    }

    public void setTab_cultivos(Tabla tab_cultivos) {
        this.tab_cultivos = tab_cultivos;
    }

    public Tabla getTab_tenecias() {
        return tab_tenecias;
    }

    public void setTab_tenecias(Tabla tab_tenecias) {
        this.tab_tenecias = tab_tenecias;
    }
}
