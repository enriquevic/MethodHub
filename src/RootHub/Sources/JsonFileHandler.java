package RootHub.Sources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFileHandler {
    private static final String JSON_EXTENSION = ".json";
   
    public void createJsonFile(String directory, String jsonName, String [][] objectNames, String[][] informations) {
        String[] txt = {
            "Iniciando a verificação da existência do arquivo %s no diretório %s%n",
            "Arquivo %s criado com sucesso%n",
            "Erro ao criar o arquivo %s no diretório %s%n",
            "O arquivo %s já existe no diretório%n"};
        
        if (!directory.endsWith("/")) {
            directory = directory + "/";
        }
        String jsonPath = directory + jsonName + JSON_EXTENSION;
        Path jsonFilePath = Paths.get(jsonPath);
        
        boolean fileExists = checkJsonFile(directory, jsonName);
        
        System.out.printf(txt[0], jsonName, directory);
        if (!fileExists) {
            try {
                // Cria o arquivo JSON
                Files.createFile(jsonFilePath);
                inputObjects(directory, jsonName, objectNames, informations);
                
                System.out.printf(txt[1], jsonName);
            } catch (IOException e) {
                System.err.println(txt[2] + e.getMessage());
            }
        } else {
            System.out.printf(txt[3], jsonName);
        }
    }    
    
    public static boolean checkJsonFile(String directory, String jsonName) {     
        if (!directory.endsWith("/")) {
            directory = directory + "/";
        }
        Path jsonFilePath = Paths.get(directory + jsonName + JSON_EXTENSION);
        return Files.exists(jsonFilePath) && Files.isRegularFile(jsonFilePath);
    }
    
    public void inputObjects(String directory, String jsonName, String[][] objectNames, String[][] informations) {
        if (!directory.endsWith("/")) {
            directory = directory + "/";
        }        
        Gson gson = new Gson();
        String filePath = directory + jsonName + JSON_EXTENSION;

        boolean contentExists = checkContentJsonFile(directory, jsonName, objectNames, informations);
        boolean checkJsonFile = checkJsonFile(directory, jsonName);
        
        if(checkJsonFile){
            if (!contentExists) {
                for (int i = 0; i < objectNames.length; i++) {
                    String objectName = objectNames[i][0];
                    String[] info = informations[i];

                    // Criando um objeto JSON para o objeto atual
                    StringBuilder jsonStringBuilder = new StringBuilder("{\"" + objectName + "\": {");
                    for (int j = 0; j < info.length; j++) {
                        // Adicionando chave e valor ao objeto JSON
                        jsonStringBuilder.append("\"").append(info[j].split(":")[0]).append("\": \"")
                                         .append(info[j].split(":")[1]).append("\"");
                        if (j < info.length - 1) {
                            jsonStringBuilder.append(", ");
                        }
                    }
                    jsonStringBuilder.append("}}");

                    // Convertendo o JSON para um objeto Java
                    Object obj = gson.fromJson(jsonStringBuilder.toString(), Object.class);

                    // Escrevendo o JSON no arquivo
                    try (FileWriter writer = new FileWriter(filePath)) {
                        gson.toJson(obj, writer);
                        // Imprimindo o objeto criado
                    System.out.printf("Informações adicionadas com sucesso no arquivo %s", jsonName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.printf("Informações já existem no arquivo %s. Informações não foram gravadas", jsonName);
            }
        } else {
            System.out.printf("Arquivo %s não existe no diretório %s", jsonName, directory);
        }
        
    }
    
    public boolean checkContentJsonFile(String directory, String jsonName, String[][] objectNames, String[][] informations) {
        try {
            // Caminho do arquivo no diretório src/bd/
            String filePath = directory + jsonName + JSON_EXTENSION;

            // Lê o arquivo JSON
            FileReader fileReader = new FileReader(filePath);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

            // Cria uma instância de JsonFileHandler
            JsonFileHandler jsonFileHandler = new JsonFileHandler();

            // Verifica se os dados correspondem
            if (jsonFileHandler.checkDataMatch(jsonObject, objectNames, informations)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    private boolean checkDataMatch(JsonObject jsonObject, String[][] objectNames, String[][] informations) {
        for (int i = 0; i < objectNames.length; i++) {
            String objectName = objectNames[i][0];

            // Verifica se o objeto está presente no JSON
            if (jsonObject.has(objectName)) {
                JsonObject objectData = jsonObject.getAsJsonObject(objectName);
                String[] info = informations[i];

                // Verifica se os dados correspondem
                for (int j = 0; j < info.length; j++) {
                    String[] keyValue = info[j].split(":");
                    String key = keyValue[0];
                    String expectedValue = keyValue[1];

                    // Verifica se a chave está presente e tem o valor esperado
                    if (objectData.has(key) && objectData.get(key).getAsString().equals(expectedValue)) {
                        continue;  // Dados correspondem, continue para a próxima chave
                    } else {
                        return false;  // Dados não correspondem
                    }
                }
            } else {
                return false;  // Objeto não encontrado no JSON
            }
        }
        return true;  // Todos os objetos e dados correspondem
    }
}
