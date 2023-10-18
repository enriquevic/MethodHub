package RootHub.Pages;

import RootHub.Sources.CheckInternet;
import RootHub.Sources.CreateDir;
import RootHub.Sources.ExcelFileHandler;
import RootHub.Sources.JsonFileHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class WindowLoading implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        boolean checkInternet = CheckInternet.checkInternetConnection();
                
        if(checkInternet){
            String diretory = "src/", folderName = "bd", fileName = "Key", sheetName = "users", cellData[][] = new String[][]{{"Name", "Password", "Date"}, {"Enrique", "1234A", "12/10/23"}},
            jsonName = "0", finalDiretory = diretory + folderName;
            String[][] objectNames = {{"access", "Colors"}};
            String[][] informations = {{"firstAccess:0", "Defaut:ffffff"}};
            JsonFileHandler jsonHandler = new JsonFileHandler();
            
            CreateDir.checkAndCreateFolder(diretory,folderName);
            ExcelFileHandler.createExcelFile(finalDiretory, fileName, sheetName, cellData);
            jsonHandler.createJsonFile(finalDiretory, jsonName, objectNames, informations);
        }
        else{
            System.out.printf("Não foi possível carregar o sistema, exclua a pasta bd do sistema");
        }
    }    
}