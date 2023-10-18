package RootHub.Sources;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckInternet {
    public static boolean checkInternetConnection() {
        /**
            * Verifica a conexão com a internet.
            * 
            * Este método tenta estabelecer uma conexão com o Google. Se a resposta do
            * servidor for bem-sucedida (código 2xx), imprime "Conectado à internet".
            * Caso contrário, exibe um alerta informando a falta de conexão.
        */
        String url = "http://www.google.com";
        String[] txt = {
            "Verificando conexão com a internet...%n",
            "Conectado à internet%n",
            "Sem conexão com a internet%n",
            "Erro ao verificar a conexão com a internet"};
        
        System.out.printf(txt[0]);
        int timeout = 5000; // Tempo limite em milissegundos
        boolean check = true;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();

            // Verifica se a resposta é bem-sucedida (código 2xx)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.printf(txt[1]);
            } else {
                System.out.printf(txt[2]);
            }

            connection.disconnect();
            
        } catch (IOException e) {    
            System.out.printf(txt[3]);
            check = false;
        }
        return check;
    }
}
