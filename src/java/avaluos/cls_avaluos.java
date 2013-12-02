/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avaluos;

import sistema.Utilitario;

/**
 *
 * @author Diego
 */
public class cls_avaluos {

    private Utilitario utilitario = new Utilitario();

    public void avaluarConstruccionUrbana(String ide_predio,String fecha) {
        String sql = "update sigt_construccion set valor_residual = 0, valor_depreciacion = 0, valor_reposicion = 0 where ide_predio =" + ide_predio;
        String mensaje = utilitario.getConexion().ejecutar(sql);
        if (mensaje.isEmpty()) {
            sql = "update sigt_construccion set vida_util = sigc_estructura.vida_util  from sigc_estructura  where sigt_construccion.ide_estructura = sigc_estructura.ide_estruc_construc and ide_predio =" + ide_predio;
            mensaje = utilitario.getConexion().ejecutar(sql);
            if (mensaje.isEmpty()) {
                sql = "update sigt_construccion set  fecha_avaluo = '"+fecha+"' where ide_predio =" + ide_predio;
                mensaje = utilitario.getConexion().ejecutar(sql);
                if (mensaje.isEmpty()) {
                    sql = "update sigt_construccion set  edad = to_number('"+utilitario.getAño(fecha) +"','9999')-ano_construccion where ide_predio =" + ide_predio;
                    mensaje = utilitario.getConexion().ejecutar(sql);
                    if (mensaje.isEmpty()) {
                        sql = "update sigt_construccion set fitto = valor "
                                + "from (select sigt_construccion.ide_predio,sigt_construccion.ide_construccion,sigt_construccion.edad,"
                                + "sigt_construccion.vida_util,sigt_construccion.edad*100/sigt_construccion.vida_util, "
                        + "valc_fitto_corvini.valor from sigt_construccion,valc_fitto_corvini "
                        + "where  sigt_construccion.ide_estado_conservacion=valc_fitto_corvini.ide_estado_conservacion and sigt_construccion.edad*100/sigt_construccion.vida_util <= edf "
                        + "and sigt_construccion.edad*100/sigt_construccion.vida_util >= edi) as tt "
                        + "where tt.ide_predio = sigt_construccion.ide_predio and  "
                        + "sigt_construccion.ide_construccion = tt.ide_construccion and sigt_construccion.ide_predio = " + ide_predio;
                        mensaje = utilitario.getConexion().ejecutar(sql);
                        if (mensaje.isEmpty()) {
                            sql = "update sigt_construccion set valor_reposicion = tt.total from "
                                    + "(select a.ide_predio,a.area_construida*a.nro_pisos*(b.valor+c.valor+d.valor+e.valor+f.valor) as total "
                                    + "from sigt_construccion a, sigc_puerven b, sigc_estructura c,sigc_pared d, "
                                    + "sigc_cubierta e, sigc_recubrimiento f where a.ide_puerven = b.ide_puerven "
                                    + "and a.ide_estructura = c.ide_estruc_construc and a.ide_pared = d.ide_pared "
                                    + "and a.ide_material_cubierta = e.ide_material_cubierta and a.ide_recubrimiento = f.ide_recubrimiento) as tt"
                                    + " where sigt_construccion.ide_predio  = tt.ide_predio and ide_etapa_construccion = 3 and sigt_construccion.ide_predio =" + ide_predio;
                            mensaje = utilitario.getConexion().ejecutar(sql);
                            if (mensaje.isEmpty()) {
                                sql = "update sigt_construccion set valor_residual = tt.total,fecha_avaluo= '"+fecha+"' from "
                                        + "(select a.ide_predio,a.area_construida*a.nro_pisos*(b.valor+c.valor+d.valor+e.valor+f.valor)*(0.08+(1-0.08)*(1-a.fitto)) as total "
                                        + "from sigt_construccion a, sigc_puerven b, sigc_estructura c,sigc_pared d, sigc_cubierta e, sigc_recubrimiento f "
                                        + "where a.ide_puerven = b.ide_puerven and a.ide_estructura = c.ide_estruc_construc "
                                        + "and a.ide_pared = d.ide_pared and a.ide_material_cubierta = e.ide_material_cubierta "
                                        + "and a.ide_recubrimiento = f.ide_recubrimiento) as tt where sigt_construccion.ide_predio  = tt.ide_predio "
                                        + "and ide_etapa_construccion = 3 and sigt_construccion.ide_predio = " + ide_predio;
                                mensaje = utilitario.getConexion().ejecutar(sql);
                                if (mensaje.isEmpty()) {
                                    sql = "update sigt_construccion set valor_reposicion = tt.total from "
                                            + "(select a.ide_predio,a.area_construida*a.nro_pisos*(c.valor+d.valor+e.valor) as total "
                                            + "from sigt_construccion a, sigc_estructura c,sigc_pared d, "
                                            + "sigc_cubierta e where a.ide_estructura = c.ide_estruc_construc "
                                            + "and a.ide_pared = d.ide_pared and a.ide_material_cubierta = e.ide_material_cubierta) as tt "
                                            + "where sigt_construccion.ide_predio  = tt.ide_predio "
                                            + "and ide_etapa_construccion = 2 and "
                                            + "sigt_construccion.ide_predio = " + ide_predio;
                                    mensaje = utilitario.getConexion().ejecutar(sql);
                                    if (mensaje.isEmpty()) {
                                        sql = "update sigt_construccion "
                                                + "set valor_residual = tt.total,fecha_avaluo= '"+fecha+"' "
                                                + "from "
                                                + "(select a.ide_predio,a.area_construida*a.nro_pisos*(c.valor+d.valor+e.valor)*(0.08+(1-0.08)*(1-a.fitto)) as total "
                                                + "from sigt_construccion a, sigc_estructura c,sigc_pared d, "
                                                + "sigc_cubierta e where  a.ide_estructura = c.ide_estruc_construc "
                                                + "and a.ide_pared = d.ide_pared "
                                                + "and a.ide_material_cubierta = e.ide_material_cubierta) as tt "
                                                + "where sigt_construccion.ide_predio  = tt.ide_predio "
                                                + "and ide_etapa_construccion = 2 and sigt_construccion.ide_predio =" + ide_predio;
                                        mensaje = utilitario.getConexion().ejecutar(sql);
                                        if (mensaje.isEmpty()) {
                                            sql = "update sigt_construccion set valor_reposicion = tt.total "
                                                    + "from (select a.ide_predio,a.area_construida*a.nro_pisos*(c.valor) as total "
                                                    + "from sigt_construccion a, sigc_estructura c "
                                                    + "where a.ide_estructura = c.ide_estruc_construc) as tt "
                                                    + "where sigt_construccion.ide_predio  = tt.ide_predio "
                                                    + "and ide_etapa_construccion = 1 and "
                                                    + "sigt_construccion.ide_predio =" + ide_predio;
                                            mensaje = utilitario.getConexion().ejecutar(sql);
                                            if (mensaje.isEmpty()) {
                                                sql = "update sigt_construccion set valor_residual = tt.total,fecha_avaluo= '"+fecha+"' "
                                                        + "from (select a.ide_predio,a.area_construida*a.nro_pisos*(c.valor)*(0.08+(1-0.08)*(1-a.fitto)) as total "
                                                        + "from sigt_construccion a, sigc_estructura c "
                                                        + "where a.ide_estructura = c.ide_estruc_construc) as tt "
                                                        + "where sigt_construccion.ide_predio  = tt.ide_predio "
                                                        + "and ide_etapa_construccion = 1 and sigt_construccion.ide_predio = " + ide_predio;
                                                mensaje = utilitario.getConexion().ejecutar(sql);
                                                if (mensaje.isEmpty()) {
                                                    sql = "update sigt_construccion "
                                                            + "set valor_depreciacion = valor_reposicion - valor_residual "
                                                            + "where sigt_construccion.ide_predio = " + ide_predio;
                                                    mensaje = utilitario.getConexion().ejecutar(sql);
                                                    if (mensaje.isEmpty()) {
                                                        utilitario.getConexion().commit();
                                                       
                                                    } else {
                                                        utilitario.getConexion().rollback();
                                                    }
                                                } else {
                                                    utilitario.getConexion().rollback();
                                                }
                                            } else {
                                                utilitario.getConexion().rollback();
                                            }
                                        } else {
                                            utilitario.getConexion().rollback();
                                        }
                                    } else {
                                        utilitario.getConexion().rollback();
                                    }
                                } else {
                                    utilitario.getConexion().rollback();
                                }
                            } else {
                                utilitario.getConexion().rollback();
                            }
                        } else {
                            utilitario.getConexion().rollback();
                        }
                    } else {
                        utilitario.getConexion().rollback();
                    }
                } else {
                    utilitario.getConexion().rollback();
                }
            } else {
                utilitario.getConexion().rollback();
            }
        } else {
            utilitario.getConexion().rollback();
        }
    }


       public void avaluarConstruccionRural(String ide_predio_rural,String fecha) {
        String sql = "update sigt_construccion set valor_residual = 0, valor_depreciacion = 0, valor_reposicion = 0 where ide_predio_rural =" + ide_predio_rural;
        String mensaje = utilitario.getConexion().ejecutar(sql);
        if (mensaje.isEmpty()) {
            sql = "update sigt_construccion set vida_util = sigc_estructura.vida_util  from sigc_estructura  where sigt_construccion.ide_estructura = sigc_estructura.ide_estruc_construc and ide_predio_rural =" + ide_predio_rural;
            mensaje = utilitario.getConexion().ejecutar(sql);
            if (mensaje.isEmpty()) {
                sql = "update sigt_construccion set  fecha_avaluo = '"+fecha+"' where ide_predio_rural =" + ide_predio_rural;
                mensaje = utilitario.getConexion().ejecutar(sql);
                if (mensaje.isEmpty()) {
                    sql = "update sigt_construccion set  edad = to_number('"+utilitario.getAño(fecha) +"','9999')-ano_construccion where ide_predio_rural =" + ide_predio_rural;
                    mensaje = utilitario.getConexion().ejecutar(sql);
                    if (mensaje.isEmpty()) {
                        sql = "update sigt_construccion set fitto = valor "
                                + "from (select sigt_construccion.ide_predio,sigt_construccion.ide_construccion,sigt_construccion.edad,"
                                + "sigt_construccion.vida_util,sigt_construccion.edad*100/sigt_construccion.vida_util, "
                        + "valc_fitto_corvini.valor from sigt_construccion,valc_fitto_corvini "
                        + "where  sigt_construccion.ide_estado_conservacion=valc_fitto_corvini.ide_estado_conservacion and sigt_construccion.edad*100/sigt_construccion.vida_util <= edf "
                        + "and sigt_construccion.edad*100/sigt_construccion.vida_util >= edi) as tt "
                        + "where tt.ide_predio = sigt_construccion.ide_predio and  "
                        + "sigt_construccion.ide_construccion = tt.ide_construccion and sigt_construccion.ide_predio_rural = " + ide_predio_rural;
                        mensaje = utilitario.getConexion().ejecutar(sql);
                        if (mensaje.isEmpty()) {
                            sql = "update sigt_construccion set valor_reposicion = tt.total from "
                                    + "(select a.ide_predio,a.area_construida*a.nro_pisos*(b.valor+c.valor+d.valor+e.valor+f.valor) as total "
                                    + "from sigt_construccion a, sigc_puerven b, sigc_estructura c,sigc_pared d, "
                                    + "sigc_cubierta e, sigc_recubrimiento f where a.ide_puerven = b.ide_puerven "
                                    + "and a.ide_estructura = c.ide_estruc_construc and a.ide_pared = d.ide_pared "
                                    + "and a.ide_material_cubierta = e.ide_material_cubierta and a.ide_recubrimiento = f.ide_recubrimiento) as tt"
                                    + " where sigt_construccion.ide_predio  = tt.ide_predio and ide_etapa_construccion = 3 and sigt_construccion.ide_predio_rural=" + ide_predio_rural;
                            mensaje = utilitario.getConexion().ejecutar(sql);
                            if (mensaje.isEmpty()) {
                                sql = "update sigt_construccion set valor_residual = tt.total,fecha_avaluo= '"+fecha+"' from "
                                        + "(select a.ide_predio,a.area_construida*a.nro_pisos*(b.valor+c.valor+d.valor+e.valor+f.valor)*(0.08+(1-0.08)*(1-a.fitto)) as total "
                                        + "from sigt_construccion a, sigc_puerven b, sigc_estructura c,sigc_pared d, sigc_cubierta e, sigc_recubrimiento f "
                                        + "where a.ide_puerven = b.ide_puerven and a.ide_estructura = c.ide_estruc_construc "
                                        + "and a.ide_pared = d.ide_pared and a.ide_material_cubierta = e.ide_material_cubierta "
                                        + "and a.ide_recubrimiento = f.ide_recubrimiento) as tt where sigt_construccion.ide_predio  = tt.ide_predio "
                                        + "and ide_etapa_construccion = 3 and sigt_construccion.ide_predio_rural= " + ide_predio_rural;
                                mensaje = utilitario.getConexion().ejecutar(sql);
                                if (mensaje.isEmpty()) {
                                    sql = "update sigt_construccion set valor_reposicion = tt.total from "
                                            + "(select a.ide_predio,a.area_construida*a.nro_pisos*(c.valor+d.valor+e.valor) as total "
                                            + "from sigt_construccion a, sigc_estructura c,sigc_pared d, "
                                            + "sigc_cubierta e where a.ide_estructura = c.ide_estruc_construc "
                                            + "and a.ide_pared = d.ide_pared and a.ide_material_cubierta = e.ide_material_cubierta) as tt "
                                            + "where sigt_construccion.ide_predio  = tt.ide_predio "
                                            + "and ide_etapa_construccion = 2 and "
                                            + "sigt_construccion.ide_predio_rural= " + ide_predio_rural;
                                    mensaje = utilitario.getConexion().ejecutar(sql);
                                    if (mensaje.isEmpty()) {
                                        sql = "update sigt_construccion "
                                                + "set valor_residual = tt.total,fecha_avaluo= '"+fecha+"' "
                                                + "from "
                                                + "(select a.ide_predio,a.area_construida*a.nro_pisos*(c.valor+d.valor+e.valor)*(0.08+(1-0.08)*(1-a.fitto)) as total "
                                                + "from sigt_construccion a, sigc_estructura c,sigc_pared d, "
                                                + "sigc_cubierta e where  a.ide_estructura = c.ide_estruc_construc "
                                                + "and a.ide_pared = d.ide_pared "
                                                + "and a.ide_material_cubierta = e.ide_material_cubierta) as tt "
                                                + "where sigt_construccion.ide_predio  = tt.ide_predio "
                                                + "and ide_etapa_construccion = 2 and sigt_construccion.ide_predio_rural =" + ide_predio_rural;
                                        mensaje = utilitario.getConexion().ejecutar(sql);
                                        if (mensaje.isEmpty()) {
                                            sql = "update sigt_construccion set valor_reposicion = tt.total "
                                                    + "from (select a.ide_predio,a.area_construida*a.nro_pisos*(c.valor) as total "
                                                    + "from sigt_construccion a, sigc_estructura c "
                                                    + "where a.ide_estructura = c.ide_estruc_construc) as tt "
                                                    + "where sigt_construccion.ide_predio  = tt.ide_predio "
                                                    + "and ide_etapa_construccion = 1 and "
                                                    + "sigt_construccion.ide_predio_rural=" + ide_predio_rural;
                                            mensaje = utilitario.getConexion().ejecutar(sql);
                                            if (mensaje.isEmpty()) {
                                                sql = "update sigt_construccion set valor_residual = tt.total,fecha_avaluo= '"+fecha+"' "
                                                        + "from (select a.ide_predio,a.area_construida*a.nro_pisos*(c.valor)*(0.08+(1-0.08)*(1-a.fitto)) as total "
                                                        + "from sigt_construccion a, sigc_estructura c "
                                                        + "where a.ide_estructura = c.ide_estruc_construc) as tt "
                                                        + "where sigt_construccion.ide_predio  = tt.ide_predio "
                                                        + "and ide_etapa_construccion = 1 and sigt_construccion.ide_predio_rural = " + ide_predio_rural;
                                                mensaje = utilitario.getConexion().ejecutar(sql);
                                                if (mensaje.isEmpty()) {
                                                    sql = "update sigt_construccion "
                                                            + "set valor_depreciacion = valor_reposicion - valor_residual "
                                                            + "where sigt_construccion.ide_predio_rural = " + ide_predio_rural;
                                                    mensaje = utilitario.getConexion().ejecutar(sql);
                                                    if (mensaje.isEmpty()) {
                                                        utilitario.getConexion().commit();
                                                       
                                                    } else {
                                                        utilitario.getConexion().rollback();
                                                    }
                                                } else {
                                                    utilitario.getConexion().rollback();
                                                }
                                            } else {
                                                utilitario.getConexion().rollback();
                                            }
                                        } else {
                                            utilitario.getConexion().rollback();
                                        }
                                    } else {
                                        utilitario.getConexion().rollback();
                                    }
                                } else {
                                    utilitario.getConexion().rollback();
                                }
                            } else {
                                utilitario.getConexion().rollback();
                            }
                        } else {
                            utilitario.getConexion().rollback();
                        }
                    } else {
                        utilitario.getConexion().rollback();
                    }
                } else {
                    utilitario.getConexion().rollback();
                }
            } else {
                utilitario.getConexion().rollback();
            }
        } else {
            utilitario.getConexion().rollback();
        }
    }

    
    
    public void avaluarTerrenoUrbano(String ide_predio) {
        String sql = "delete from sigt_avaluo_lote where ide_predio=" + ide_predio;
        String mensaje = utilitario.getConexion().ejecutar(sql);
        if (mensaje.isEmpty()) {
            sql = "insert into sigt_avaluo_lote "
                    + "select distinct c.ide_predio,now(), a.valor_unitario*c.escritura as valor,power(c.frente/b.frente,0.25) as fac_frente , "
                    + "power(b.fondo/c.fondo,0.5) as fac_fondo, "
                    + "1 as fac_forma ,(case when c.escritura/b.superficie < 5 then 1 "
                    + "when (c.escritura/b.superficie >4 and c.escritura/b.superficie < 10) then 0.9 "
                    + "when (c.escritura/b.superficie >9 and c.escritura/b.superficie < 20) then 0.8 "
                    + "when (c.escritura/b.superficie >20) then 0.7 "
                    + "  else 1 end) as fac_tamano,1 as fac_esquina, "
                    + "a.valor_unitario*c.escritura*power(c.frente/b.frente,0.25)*power(b.fondo/c.fondo,0.5)*1*(case when c.escritura/b.superficie < 5 then 1  "
                    + "when (c.escritura/b.superficie >4 and c.escritura/b.superficie < 10) then 0.9 "
                    + "when (c.escritura/b.superficie >9 and c.escritura/b.superficie < 20) then 0.8 "
                    + "when (c.escritura/b.superficie >20) then 0.7 "
                    + "  else 1 end) as valor_total from sigt_lote a, valc_lote_tipo b, sigt_area_terreno c, sigt_predio d "
                    + "where a.ide_lote_tipo = b.ide_lote_tipo and a.valor_unitario >0 "
                    + "and a.clv_lote = d.clave and d.ide_predio = c.ide_predio "
                    + "and c.frente > 0 and b.fondo >0 and b.superficie > 0 and c.fondo >0 and c.escritura > 0 "
                    + "and (c.medida =0 or c.medida <> c.escritura) and d.ide_predio =" + ide_predio;
            mensaje = utilitario.getConexion().ejecutar(sql);
            if (mensaje.isEmpty()) {
                sql = "insert into sigt_avaluo_lote "
                        + "select distinct c.ide_predio,now(), a.valor_unitario*c.medida as valor,power(c.frente/b.frente,0.25) as fac_frente , "
                        + "power(b.fondo/c.fondo,0.5) as fac_fondo, "
                        + "1 as fac_forma ,(case when c.medida/b.superficie < 5 then 1  "
                        + "when (c.medida/b.superficie >4 and c.medida/b.superficie < 10) then 0.9 "
                        + "when (c.medida/b.superficie >9 and c.medida/b.superficie < 20) then 0.8 "
                        + "when (c.medida/b.superficie >20) then 0.7 "
                        + "else 1 end) as fac_tamano,1 as fac_esquina, "
                        + "a.valor_unitario*c.medida*power(c.frente/b.frente,0.25)*power(b.fondo/c.fondo,0.5)*1*(case when c.medida/b.superficie < 5 then 1  "
                        + "when (c.medida/b.superficie >4 and c.medida/b.superficie < 10) then 0.9 "
                        + "when (c.medida/b.superficie >9 and c.medida/b.superficie < 20) then 0.8 "
                        + "when (c.medida/b.superficie >20) then 0.7 else 1 end) as valor_total "
                        + "from sigt_lote a, valc_lote_tipo b, sigt_area_terreno c, sigt_predio d "
                        + "where a.ide_lote_tipo = b.ide_lote_tipo and a.valor_unitario >0 "
                        + "and a.clv_lote = d.clave and d.ide_predio = c.ide_predio "
                        + "and c.frente > 0 and b.fondo >0 and b.superficie > 0 "
                        + "and c.fondo >0 and c.medida > 0 and (c.escritura <=0 or c.medida = c.escritura) "
                        + "and (d.ide_predio =" + ide_predio + ")";
                mensaje = utilitario.getConexion().ejecutar(sql);
                if (mensaje.isEmpty()) {
                    sql = "update sigt_avaluo_lote set valor_total = 1.30 * valor "
                            + "where fac_frente*fac_fondo*fac_forma*fac_tamano*fac_esquina > 1.30 and ide_predio =" + ide_predio;
                    mensaje = utilitario.getConexion().ejecutar(sql);
                    if (mensaje.isEmpty()) {
                        sql = " insert into sigt_avaluo_lote(ide_predio,valor_total,fecha,valor,fac_frente,fac_fondo, "
                                + "fac_forma,fac_tamano,fac_esquina) "
                                + "select a.ide_predio,(c.medida/b.medida)* b.aval,now(),0,1,1,1,1,1 from "
                                + "(select a.ide_predio as predmas,a.valor,a.medida,b.clave,c.valor_total as aval "
                                + "from sigt_area_terreno a,sigt_predio b,sigt_avaluo_lote c "
                                + "where a.ide_predio = b.ide_predio "
                                + "and a.ide_predio = c.ide_predio "
                                + "and a.ide_predio in ( select ide_predio from sigt_predio "
                                + "where unidad =0 and clave in ( "
                                + "select clave from sigt_predio where unidad > 0))) b,sigt_predio a,sigt_area_terreno c "
                                + "where a.clave = b.clave "
                                + "and a.ide_predio = c.ide_predio and c.medida < b.medida "
                                + "and c.medida > 0 and a.ide_predio =" + ide_predio;
                        mensaje = utilitario.getConexion().ejecutar(sql);

                        if (mensaje.isEmpty()) {
                            utilitario.getConexion().commit();
                            utilitario.agregarMensaje("Se ejecuto correctamente", "");
                        } else {
                            utilitario.getConexion().rollback();
                        }
                    } else {
                        utilitario.getConexion().rollback();
                    }
                } else {
                    utilitario.getConexion().rollback();
                }
            } else {
                utilitario.getConexion().rollback();
            }
        } else {
            utilitario.getConexion().rollback();
        }
    }
}
