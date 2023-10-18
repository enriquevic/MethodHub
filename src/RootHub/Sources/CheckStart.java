package RootHub.Sources;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class CheckStart {
    
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
            "Verificando conecxão com a internet...%n",
            "Conectado à internet%n",
            "Sem conexão com a internet%n",
            "Erro ao verificar a conexão com a internet"};
        
        System.out.println(txt[0]);
        int timeout = 5000; // Tempo limite em milissegundos
        boolean check = true;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();

            // Verifica se a resposta é bem-sucedida (código 2xx)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println(txt[1]);
            } else {
                System.out.println(txt[2]);
            }

            connection.disconnect();
            
        } catch (IOException e) {    
            System.out.println(txt[3]);
            check = false;
        }
        return check;
    }
   
    public static boolean checkAndCreateFolder() {
        /**
            * Verifica a existência da pasta BD.
            * 
            * Este método verifica a existência da pasta bd (banco de dados) que é 
            * essencial para armazenar arquivos do sistema. Se a verificação for
            * bem-sucedida, imprime "Pasta bd existente".
            * Caso contrário, cria a pasta "bd" e imprime "Pasta bd criada com sucesso".
        */
        String diretory = "src/";
        String nameArquive = "bd";
        
        String folderPath = diretory + nameArquive;
        Path directoryPath = Paths.get(folderPath);
        
        boolean check = true;
        
        try {
            // Verificar se o diretório já existe
            if (Files.exists(directoryPath) && Files.isDirectory(directoryPath)) {
                System.out.println("Pasta bd existente");
            } else {
                // Criar o diretório se não existir
                Files.createDirectories(directoryPath);
                System.out.println("Pasta bd criada com sucesso");
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar ou criar a pasta: " + e.getMessage());
            check = false;
        }
        
        return check;
    }
    
    public static boolean checkAndCreateJsonFile() {
        /**
            * Verifica a existência do arquivo 0.json.
            * 
            * Este método verifica a existência do arquivo 0.json (checagem de primeiro acesso) 
            * que é essencial para armazenar uma informação binária. Se a verificação for
            * bem-sucedida, imprime "Arquivo 0.json existente".
            * Caso contrário, cria o arquivo "0.json" e imprime "Arquivo 0.json criado com sucesso".
        */
        String folderPath = "src/bd/";
        String fileName = "0.json";

        Path jsonFilePath = Paths.get(folderPath, fileName);
        
        boolean check = true;

        try {
            // Verificar se o arquivo JSON já existe
            if (Files.exists(jsonFilePath) && Files.isRegularFile(jsonFilePath)) {
                System.out.println("Arquivo 0.json existente");
            } else {
                // Criar o arquivo JSON se não existir
                System.out.println("Arquivo 0.json criado com sucesso");
                createJsonContent(jsonFilePath);
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar/criar o arquivo JSON: " + e.getMessage());
            check = false;
        }
        return check;
    }  
    
    public static boolean createJsonContent(Path jsonFilePath) {
        /**
            * Cria um objeto binário.
            * 
            * Este método cria um objeto binário de valor 0 caso seja o primeiro acesso. 
            * Se o objeto não existir ele cria o firstaccess de valor 0 e imprime
            * "Objeto 'first access' com valor 0 criado com sucesso".
        */
        
        boolean check = true;
        
        try {
            // Cria um objeto JSON com a chave "firstaccess" e valor 0
            String jsonContent = "{ \"firstAccess\": 0 }";

            // Escreve o JSON no arquivo
            Files.write(jsonFilePath, jsonContent.getBytes(), StandardOpenOption.CREATE);
            System.out.println("Objeto 'first access' com valor 0 criado com sucesso");
        } catch (Exception e) {
            System.err.println("Erro ao criar o arquivo JSON: " + e.getMessage());
            check = false;
        }
        return check;
    }

    
    
    static boolean CheckAfterStart() {
        boolean internetCheck = CheckStart.checkInternetConnection();
        boolean folderCheck = CheckStart.checkAndCreateFolder();
        boolean jsonCheck = CheckStart.checkAndCreateJsonFile();

        return internetCheck && folderCheck && jsonCheck;
    }
}
