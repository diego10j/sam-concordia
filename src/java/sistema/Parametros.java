/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import framework.Parametro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parametros implements Serializable {

    public List<Parametro> getParametrosSistema() {
        List<Parametro> lis_parametros = new ArrayList();




//////////////////////////////////////////////////////////////////////
        /*
         * ESPECIES MODULO =0
         */

        lis_parametros.add(new Parametro("0", "p_esp_tm1", "Indica la especie de no adeuda al municipio ", "1", "rec_especies", "ide_documento", "descripcion"));
        lis_parametros.add(new Parametro("0", "p_sueldo_bas_uni", "El valor del sueldo basico unificado", "318"));
        lis_parametros.add(new Parametro("0", "p_porcen_no_adeuda", "El porcentaje para el calculo del valor de la especie Certificacion de no adeudar al municipio", "2"));
//////////////////////////////////////////////////////////////////////
        return lis_parametros;
    }
}
