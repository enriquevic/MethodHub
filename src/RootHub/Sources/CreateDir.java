package RootHub.Sources;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateDir {
    public static boolean checkAndCreateFolder(String diretory, String folderName) {
           
    String[] txt = {
            "Verificando existência da pasta %s no diretório %s...%n",
            "Pasta existente%n",
            "Pasta %s criada com sucesso%n",
            "Erro ao criar pasta. Verifique o diretório%n",
            "Verificação concluída%n"};
    
    // Verifica se o último caractere de diretory é uma barra
    if (!diretory.endsWith("/")) {
        diretory = diretory + "/";
    }
    String folderPath = diretory + folderName;
    

    Path directoryPath = Paths.get(folderPath);
    System.out.printf(txt[0],folderName, diretory);
    boolean check = true;
    try {
        // Verificar se o diretório já existe
        if (Files.exists(directoryPath) && Files.isDirectory(directoryPath)) {
            System.out.printf(txt[1]);
        } else {
            // Criar o diretório se não existir
            Files.createDirectories(directoryPath);
            System.out.printf(txt[2],folderName);
        }
    } catch (Exception e) {
        System.err.printf(txt[3] + e.getMessage());
        check = false;
    }
        System.out.printf(txt[4]);
        return check;
    }
}
