package RootHub.Pages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import RootHub.Sources.CheckInternet;
import RootHub.Sources.CreateDir;
import RootHub.Sources.ExcelFileHandler;
import RootHub.Sources.JsonFileHandler;
import static RootHub.Sources.JsonFileHandler.checkJsonFile;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class WindowLoading implements Initializable {
    private static final String JSON_EXTENSION = ".json";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        boolean checkInternet = CheckInternet.checkInternetConnection();
                
        if(checkInternet){
            // Directory cursor
            String diretory = "src/";
            
            // Name Folder make
            String folderName = "bd";
            
            // Complete Directory
            String finalDiretory = diretory + folderName;
            
            // Excel File make
            String fileName = "Key";
            String sheetName = "users"; 
            String cellData[][] = new String[][]{{"Name", "Password", "Date"}, {"Enrique", "1234A", "12/10/23"}};
            
            // Json Name and informations
            String jsonName = "0";
            
            // Content Json
            String[][] objectNames = {{"Access"}};
            String[][] informations = {{"firstAccess:0"}};
            
            // Check and Create Folder
            CreateDir.checkAndCreateFolder(diretory,folderName);
            
            //Create Excel Archive
            ExcelFileHandler.createExcelFile(finalDiretory, fileName, sheetName, cellData);

            boolean checkJsonFile = checkJsonFile(finalDiretory, jsonName);
            
            if (!finalDiretory.endsWith("/")) {
                finalDiretory = finalDiretory + "/";
            }
            
            if (checkJsonFile){
                String filePath = finalDiretory + jsonName + JSON_EXTENSION;
                ObjectMapper objectMapper = new ObjectMapper();
                File file = new File(filePath);
                
                // See content
                try {
                    JsonNode rootNode = objectMapper.readTree(file);
                    JsonNode accessNode = rootNode.path("Access");

                    String firstAccess = accessNode.path("firstAccess").asText();

                    System.out.println("String firstAccess = " + firstAccess);

                    if ("0".equals(firstAccess)){
                        System.out.println("Acessa bem vindo");
                    } else {
                        System.out.println("Acessa login");
                    } 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Create Json Arquive
                JsonFileHandler jsonHandler = new JsonFileHandler();
                jsonHandler.createJsonFile(finalDiretory, jsonName, objectNames, informations);
            }
        } else{
            System.out.printf("Não foi possível carregar o sistema, exclua a pasta bd do sistema");
        }
    }    
}
