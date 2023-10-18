package RootHub.Sources;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileHandler {    
    private static final String EXCEL_EXTENSION = ".xlsx";
    
    public static void createExcelFile(String directory, String fileName, String sheetName, String[][] cellData) {  
        String[] txt = {
            "Iniciando a verificação da existência do arquivo %s no diretório %s...%n",
            "Criando arquivo %s no diretório %s...%n",
            "Arquivo %s criado com sucesso...%n",
            "A planilha não foi nomeada corretamente na entrada. Renomeada para 'Planilha'%n",
            "Planilha '%s' criada com sucesso...%n",
            "Verificando dados para transportar a planilha %s...%n",
            "Dados transportados para a planilha %s com sucesso...%n",
            "Não há dados para transportar para a planilha %s%n",
            "Salvando e fechando o arquivo %s...%n",
            "Arquivo criado com sucesso",
            "Erro ao criar o arquivo: %s %s%n",
            "Arquivo %s existente no diretório %s%nNão foi possível criar o arquivo nem transportar os dados para a planilha%n"
        };
        
        if (!directory.endsWith("/")) {
            directory = directory + "/";
        }
        
        fileName = fileName + EXCEL_EXTENSION;
        Path excelPath = Paths.get(directory, fileName);

        // Se o arquivo não existe, criar um novo
        System.out.printf(txt[0], fileName, directory);
        boolean fileExists = checkExcelFile(directory, fileName);
        if (!fileExists) {
            // Cria o arquivo .xlsx
            System.out.printf(txt[1], fileName, directory);
            try (Workbook workbook = new XSSFWorkbook();
                FileOutputStream fileOut = new FileOutputStream(excelPath.toFile())) {
                
                System.out.printf(txt[2], fileName);

                // Se não existir nome para a planilha, dá o nome de "Planilha"
                if (sheetName == null){
                    sheetName = "Planilha";
                    System.out.printf(txt[3]);
                }

                // Cria a planilha
                Sheet sheet = workbook.createSheet(sheetName);
                System.out.printf(txt[4], sheetName);

                // Adicionar dados às células, se fornecidos
                System.out.printf(txt[5], sheetName);
                if (cellData != null) {
                    for (String[] rowData : cellData) {
                        Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
                        for (int i = 0; i < rowData.length; i++) {
                            row.createCell(i).setCellValue(rowData[i]);
                        }
                    }
                    System.out.printf(txt[6], sheetName);
                } else {
                    System.out.printf(txt[7], sheetName);
                }

                workbook.write(fileOut);
                System.out.printf(txt[8], fileName);
                System.out.printf("%n" + txt[9]);

            } catch (Exception e) {
                System.err.printf(txt[10], fileName, e.getClass().getSimpleName());
            }
        } else {
            System.out.printf(txt[11], fileName, directory);
        }
    }

    // Método para verificar se o arquivo Excel existe
    private static boolean checkExcelFile(String directory, String fileName) {
        Path filePath = Paths.get(directory, fileName);
        return Files.exists(filePath);
    }
}
