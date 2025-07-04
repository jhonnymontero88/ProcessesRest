package min.gob.ec.tracingservices.constants;

public class TemplateConstants {
    public static final String MAIL_NEW_USER = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "</head>\n" +
            "<body style=\"padding: 20px; background-color: rgb(245, 245, 245);\">\n" +
            "\n" +
            "<div style=\"padding: 20px; box-shadow: rgb(204, 204, 204) 0 8px 6px -7px; border: 1px solid rgb(210, 210, 210);\n" +
            "            border-radius: 5px; background-color: #FFFFFF\">\n" +
            "  <span style=\"font-weight: bold; color: #083065; font-size: 12px;\">Hola $usuario:</span><br/><br/>\n" +
            "  <div style=\"color: slategrey; font-size: 12px;\">\n" +
            "    Su cuenta de usuario con la dirección de correo $correo ha sido creada en el Sistema de Seguimiento del Ministerio de Gobierno.<br/><br/>\n" +
            "    La clave de ingreso al sistema es la siguiente: $clave<br><br>\n" +
            "    Usted puede cambiar su clave una vez que ingrese al sistema.\n" +
            "  </div>\n" +
            "  <br/>\n" +
            "  <div style=\"font-weight: bold; font-size: 12px;\">\n" +
            "    Atentamente<br/>\n" +
            "    Ministerio de Gobierno\n" +
            "  </div>\n" +
            "  <hr />\n" +
            "  <div style=\"color: dimgray; text-align: center; font-size: 10px;\">\n" +
            "    <span style=\"font-weight: bold;\">No responda a este correo electrónico, es un envío automático</span><br/>\n" +
            "    Este correo electrónico fue enviado a través del sistema de notificaciones del Ministerio de Gobierno<br/>\n" +
            "    Quito - Ecuador\n" +
            "  </div>\n" +
            "</div>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";
}
