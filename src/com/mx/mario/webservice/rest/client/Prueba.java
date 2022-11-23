package com.mx.mario.webservice.rest.client;

import com.google.gson.Gson;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import javax.ws.rs.core.Response;

/**
 *
 * @author smarv
 */
public class Prueba {

    public static void main(String[] args) {

        //Esta variable res la usaremos únicamente para dar un respuesta final
        String res = "";
        //String URL = "http://localhost:8080/ServicioRest/app/";
        //String URL = "https://192.168.1.61/portalservicios/v1/759/disposicionimss/certificacion";
        String URL = "http://localhost/PA/mesnajesSMS.php";
        
        try {
            //Creamos el cliente de conexión al API Restful
            Client client = ClientBuilder.newClient();

            //Creamos el target lo cuál es nuestra URL junto con el nombre del método a llamar
            //WebTarget target = client.target(URL + "datosPOSTJSON");
            WebTarget target = client.target(URL);

            //Creamos nuestra solicitud que realizará el request
            Invocation.Builder solicitud = target.request();
            System.out.println("target.request(): " + target.request());

            //Creamos y llenamos nuestro objeto BaseReq con los datos que solicita el API
            ParametrosVO parametrosVO = new ParametrosVO();
            parametrosVO.setNombre("Mario");
            parametrosVO.setApellido("Ramirez");
            parametrosVO.setEdad(33);
            
            //seervicio php param
            parametrosVO.setDestinatario("+525531750509");
            parametrosVO.setMensaje("oli desde servicio");
            
            //Convertimos el objeto req a un json
            Gson gson = new Gson();
            String jsonString = gson.toJson(parametrosVO);
            System.out.println("jsonString: " + jsonString);

            //Enviamos nuestro json vía post al API Restful
            //Response resp = solicitud.post(Entity.json(jsonString));
            
            //Enviamos nuestro json vía post al API Restful
            Response resp = solicitud.get();
            
            //Recibimos la respuesta y la leemos en una clase de tipo String, en caso de que el json sea tipo json y no string,
            //debemos usar la clase de tipo JsonObject.class en lugar de String.class
            String responseJson = resp.readEntity(String.class);
            res = responseJson;

            //Imprimimos el status de la solicitud
            System.out.println("Estatus: " + resp.getStatus());

            switch (resp.getStatus()) {
                case 200:
                    res = responseJson;
                    break;
                default:
                    res = "Error";
                    break;
            }

        } catch (Exception e) {
            //En caso de un error en la solicitud, llenaremos res con la exceptión para verificar que sucedió
            res = e.toString();
        }
        //Imprimimos la respuesta del API Restful
        System.out.println("res: " + res);
    }

}