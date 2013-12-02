/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import framework.Boton;
import framework.Confirmar;
import framework.Encriptar;
import framework.ErrorSQL;
import framework.Etiqueta;
import framework.Foco;
import framework.Framework;
import framework.Grid;
import framework.Imagen;
import framework.Texto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.component.blockui.BlockUI;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.password.Password;
import seguridad.AuthenticationService;
import sistema.Utilitario;

/**
 *
 * @author Diego
 */
@ManagedBean
@SessionScoped
public class pre_login {

    private HtmlForm formulario = new HtmlForm();
    private Panel pan_panel = new Panel();
    private Etiqueta eti_usuario = new Etiqueta();
    private Etiqueta eti_clave = new Etiqueta();
    private Texto tex_usuario = new Texto();
    private Password pas_clave = new Password();
    private Grid gri_login = new Grid();
    private Imagen ima_llave = new Imagen();
    private Grid gri_matriz = new Grid();
    private Boton bot_login = new Boton();
    private ErrorSQL error_sql = new ErrorSQL();
    private Utilitario utilitario = new Utilitario();
    private HtmlInputHidden alto = new HtmlInputHidden(); //Alto Browser
    private HtmlInputHidden ancho = new HtmlInputHidden();//Ancho Browser
    private Encriptar encriptar = new Encriptar();
    private Foco foc_foco = new Foco();
    private List lis_detalle_modulos = new ArrayList();
    @ManagedProperty(value = "#{authenticationService}")
    private AuthenticationService authenticationService; // injected Spring defined service for bikes

    /**
     * Creates a new instance of pre_login
     */
    public pre_login() {
        crearDetalleModulos();
        formulario.setTransient(true);
        gri_matriz.setColumns(2);
        gri_matriz.setWidth("100%");
        pan_panel.setId("pan_panel");
        pan_panel.setStyle("width: 350px;");
        pan_panel.setHeader("Ingresar al Sistema");
        eti_usuario.setValue("Usuario : ");
        eti_clave.setValue("Clave : ");
        tex_usuario.setId("tex_usuario");
        tex_usuario.setRequired(true);
        tex_usuario.setRequiredMessage("Debe ingresar el usuario");
        tex_usuario.setStyle("width: 99%;");
        gri_matriz.getChildren().add(eti_usuario);
        gri_matriz.getChildren().add(tex_usuario);
        gri_matriz.getChildren().add(eti_clave);
        pas_clave.setFeedback(false);
        pas_clave.setRequired(true);
        pas_clave.setRequiredMessage("Debe ingresar la clave");
        pas_clave.setStyle("width: 99%;");
        gri_matriz.getChildren().add(pas_clave);
        bot_login.setId("bot_login");
        bot_login.setValue("Aceptar");
        bot_login.setIcon("ui-icon-locked");
        bot_login.setUpdate("pan_panel");
        bot_login.setMetodoRuta("pre_login.login");
        bot_login.setOnclick("var na = $(window).height();"
                + " document.getElementById('formulario:alto').value=na; "
                + " var ancho = $(window).width();"
                + " document.getElementById('formulario:ancho').value=ancho;");
        gri_matriz.setFooter(bot_login);
        foc_foco.setFor("tex_usuario");
        formulario.getChildren().add(foc_foco);
        gri_login.setColumns(2);
        ima_llave.setValue("imagenes/im_llave.png");
        gri_login.setWidth("100%");
        gri_login.getChildren().add(ima_llave);
        gri_login.getChildren().add(gri_matriz);
        pan_panel.getChildren().add(gri_login);
        pan_panel.setTransient(true);
        BlockUI blo_panel = new BlockUI();
        blo_panel.setBlock("pan_panel");
        blo_panel.setTrigger("bot_login");
        formulario.getChildren().add(blo_panel);
        error_sql.setId("error_sql");
        error_sql.setMetodoAceptar("pre_login.cerrarSql");
        formulario.getChildren().add(error_sql);
        formulario.getChildren().add(pan_panel);
        alto.setId("alto");
        formulario.getChildren().add(alto);
        ancho.setId("ancho");
        formulario.getChildren().add(ancho);
    }

    public void login() {

        boolean boo_login = authenticationService.login(tex_usuario.getValue() + "", encriptar.getEncriptar(pas_clave.getValue() + ""));
        if (boo_login) {
            try {
                utilitario.crearVariable("ALTO", alto.getValue() + "");  //Alto browser
                utilitario.crearVariable("ANCHO", ancho.getValue() + ""); //Ancho browser
                utilitario.crearVariable("NICK", tex_usuario.getValue() + ""); //Usuario
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pre_index");
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
            } catch (Exception e) {
            }

        } else {
            utilitario.agregarMensajeError("El nombre del usuario o la calve son incorrectos", "");
        }
    }

    public void salir() {
        try {
            if (utilitario.getConexion() != null) {
                utilitario.getConexion().desconectar();
            }
            Framework framework = new Framework();
            System.out.println("SALIO EL USUARIO :" + utilitario.getVariable("NICK"));
            framework.cerrarSesion();
            authenticationService.logout();

            FacesContext.getCurrentInstance().getExternalContext().redirect(framework.getURL());
        } catch (Exception ex) {
        }
    }

    private void crearDetalleModulos() {
        String obj1[] = {"Avaluos", "imagenes/avaluos.gif", "Avaluos Y Catastros: Certificados de Avaluó. Documentos que se utilizan para diferentes trámites y que es emitido por la Officina de Avaluos y Catastros"};
        String obj2[] = {"Recaudación", "imagenes/recaudacion.png", "Recaudación: venta de Especies Valoradas. Conjunto de documentos que sirven para los diferentes trámites Municipales"};
        lis_detalle_modulos.add(obj1);
        lis_detalle_modulos.add(obj2);
    }

    public ErrorSQL getError_sql() {
        return error_sql;
    }

    public void cerrarSql() {
        error_sql.setVisible(false);
    }

    public void cerrarConfirmar(ActionEvent evt) {
        UIComponent com_padre = evt.getComponent();
        while (com_padre != null) {
            com_padre = com_padre.getParent();
            String str_tipo = com_padre.getRendererType();
            if (str_tipo != null && str_tipo.equals("org.primefaces.component.ConfirmDialogRenderer")) {
                break;
            }
        }
        if (com_padre != null) {
            ((Confirmar) com_padre).setVisible(false);
        }
    }

    public void setError_sql(ErrorSQL error_sql) {
        this.error_sql = error_sql;
    }

    public HtmlForm getFormulario() {
        return formulario;
    }

    public void setFormulario(HtmlForm formulario) {
        this.formulario = formulario;
    }

    public List getLis_detalle_modulos() {
        return lis_detalle_modulos;
    }

    public void setLis_detalle_modulos(List lis_detalle_modulos) {
        this.lis_detalle_modulos = lis_detalle_modulos;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
