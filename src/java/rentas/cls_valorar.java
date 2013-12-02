/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import sistema.Utilitario;

/**
 *
 * @author Diego
 */
public class cls_valorar {

    public boolean valorar_Interes_Multa_Descuento(String fecha) {
        String sql = "update descuentos_multas "
                + "set interes = a.interes, multa = a.multa "
                + "from ( select ide_ingreso,detalle,fecha_emision,fecha_vence,fecha_titulo,ide_estado,val1,valor_impuesto,ano1,mes1, "
                + "valor_acum,val1 * valor_acum/100 as interes, valor_impuesto*10/100 as multa "
                + "from ( "
                + "select b.ide_ingreso,a.ide_titulo,detalle,fecha_emision,fecha_vence,fecha_titulo,a.ide_estado, "
                + "(case when val1 is null then 0 else val1 end) as val1, "
                + "(case when valor_impuesto is null then 0 else valor_impuesto end) as valor_impuesto, "
                + "extract (year from fecha_emision) as ano1,extract(month from fecha_emision) as mes1 "
                + "from rec_valores a "
                + "left join tes_ingreso b on a.ide_titulo = b.ide_titulo "
                + "left join (select sum(valor) as val1,ide_titulo from rec_valor_detalle group by ide_titulo) c on a.ide_titulo = c.ide_titulo "
                + "left join (select valor as valor_impuesto,ide_titulo from rec_valor_detalle where ide_impuesto in (select ide_impuesto from rec_impuesto where imp_urbano='t')) d on a.ide_titulo=d.ide_titulo "
                + "where extract (year from fecha_emision) < extract(year from cast ('" + fecha + "' as date)) "
                + "and ide_concepto in ( "
                + "select ide_concepto from rec_concepto where urbano='t' ) "
                + "and a.ide_estado =1 ) a, rec_interes_acumulado "
                + "where ano=ano1 and ide_periodo = mes1 ) a "
                + "where a.ide_ingreso = descuentos_multas.ide_ingreso; "
                + ""
                + "update descuentos_multas set interes = a.interes, multa = a.multa "
                + "from ( select ide_ingreso,detalle,fecha_emision,fecha_vence,fecha_titulo,ide_estado,val1,valor_impuesto,ano1,mes1, "
                + "valor_acum,val1 * valor_acum/100 as interes, valor_impuesto*10/100 as multa "
                + "from ( select b.ide_ingreso,a.ide_titulo,detalle,fecha_emision,fecha_vence,fecha_titulo,a.ide_estado, "
                + "(case when val1 is null then 0 else val1 end) as val1, (case when valor_impuesto is null then 0 else valor_impuesto end) as valor_impuesto, "
                + "extract (year from fecha_titulo) as ano1,extract(month from fecha_titulo) as mes1 "
                + "from rec_valores a "
                + "left join tes_ingreso b on a.ide_titulo = b.ide_titulo "
                + "left join (select sum(valor) as val1,ide_titulo from rec_valor_detalle group by ide_titulo) c on a.ide_titulo = c.ide_titulo "
                + "left join (select valor as valor_impuesto,ide_titulo from rec_valor_detalle where ide_impuesto in (select ide_impuesto from rec_impuesto where imp_rural='t')) d on a.ide_titulo=d.ide_titulo "
                + "where extract (year from fecha_titulo) < extract(year from cast ('" + fecha + "' as date)) "
                + "and ide_concepto in ( select ide_concepto from rec_concepto where rural='t' ) "
                + "and a.ide_estado =1 ) a, rec_interes_acumulado where ano=ano1 "
                + "and ide_periodo = mes1 ) a where a.ide_ingreso = descuentos_multas.ide_ingreso; "
                + ""
                + "update descuentos_multas set interes = a.interes "
                + "from ( select ide_ingreso,detalle,fecha_emision,fecha_vence,fecha_titulo,ide_estado,val1,ano1,mes1, "
                + "valor_acum,val1 * valor_acum/100 as interes "
                + "from ( select b.ide_ingreso,a.ide_titulo,detalle,fecha_emision,fecha_vence,fecha_titulo,a.ide_estado, "
                + "(case when val1 is null then 0 else val1 end) as val1, "
                + "extract (year from fecha_titulo) as ano1,extract(month from fecha_titulo) as mes1 "
                + "from rec_valores a left join tes_ingreso b on a.ide_titulo = b.ide_titulo "
                + "left join (select sum(valor) as val1,ide_titulo from rec_valor_detalle group by ide_titulo) c on a.ide_titulo = c.ide_titulo "
                + "where extract (year from fecha_titulo) < extract(year from cast ('" + fecha + "' as date)) "
                + "and not ide_concepto in ( select ide_concepto from rec_concepto where rural='t' "
                + "union select ide_concepto from rec_concepto where urbano='t' ) "
                + "and a.ide_estado =1 ) a, rec_interes_acumulado where ano=ano1 "
                + "and ide_periodo = mes1 ) a "
                + "where a.ide_ingreso = descuentos_multas.ide_ingreso; "
                + ""
                + "update descuentos_multas set multa = a.multa, descuento = a.descuento1 "
                + "from ( select ide_ingreso,detalle,fecha_emision,fecha_vence,fecha_titulo,ide_estado,valor_impuesto, "
                + "rango_i,  rango_f ,  descuento ,  desrec,(case when desrec='r' then valor_impuesto * descuento/100 else 0 end) as multa, "
                + "(case when desrec='d' then valor_impuesto * descuento/100 else 0 end) as descuento1 "
                + "from ( select b.ide_ingreso,a.ide_titulo,detalle,fecha_emision,fecha_vence,fecha_titulo,a.ide_estado, "
                + "(case when valor_impuesto is null then 0 else valor_impuesto end) as valor_impuesto "
                + "from rec_valores a left join tes_ingreso b on a.ide_titulo = b.ide_titulo "
                + "left join (select valor as valor_impuesto,ide_titulo from rec_valor_detalle where ide_impuesto in (select ide_impuesto from rec_impuesto where imp_urbano='t')) d on a.ide_titulo=d.ide_titulo "
                + "where extract (year from fecha_titulo) = extract(year from cast ('" + fecha + "' as date)) "
                + "and ide_concepto in ( select ide_concepto from rec_concepto where urbano='t' ) "
                + "and a.ide_estado =1 ) a, rec_descuentos b "
                + "where cast ('" + fecha + "' as date) between cast (rango_i as date) and cast (rango_f as date) "
                + ") a where a.ide_ingreso = descuentos_multas.ide_ingreso; "
                + "update descuentos_multas set multa = a.multa, "
                + "descuento = a.descuento1 from ( "
                + "select ide_ingreso,detalle,fecha_emision,fecha_vence,fecha_titulo,ide_estado,valor_impuesto, "
                + "rango_i,  rango_f ,  descuento ,  desrec,(case when desrec='r' then valor_impuesto * descuento/100 else 0 end) as multa, "
                + "(case when desrec='d' then valor_impuesto * descuento/100 else 0 end) as descuento1 "
                + "from ( select b.ide_ingreso,a.ide_titulo,detalle,fecha_emision,fecha_vence,fecha_titulo,a.ide_estado, "
                + "(case when valor_impuesto is null then 0 else valor_impuesto end) as valor_impuesto "
                + "from rec_valores a left join tes_ingreso b on a.ide_titulo = b.ide_titulo "
                + "left join (select valor as valor_impuesto,ide_titulo from rec_valor_detalle where ide_impuesto in (select ide_impuesto from rec_impuesto where imp_rural='t')) d on a.ide_titulo=d.ide_titulo "
                + "where extract (year from fecha_titulo) = extract(year from cast ('" + fecha + "' as date)) "
                + "and ide_concepto in ( "
                + "select ide_concepto from rec_concepto where rural='t' ) "
                + "and a.ide_estado =1 ) a, rec_descuentos b "
                + "where cast ('" + fecha + "' as date) between cast (rango_i as date) and cast (rango_f as date) ) a "
                + "where a.ide_ingreso = descuentos_multas.ide_ingreso";
        Utilitario utilitario = new Utilitario();
        String mensaje = utilitario.getConexion().ejecutar(sql);
        if (mensaje != null && mensaje.isEmpty()) {
            utilitario.getConexion().commit();
            return true;
        } else {
            utilitario.getConexion().rollback();
        }
        return false;
    }
}