package accessoptimizedpst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WordTest {
    public static void main(String[] args) throws IOException{
        SplayTree splayTree = new SplayTree();
        ArrayList<PointerPSTNode> nodes = new ArrayList<>();
        
        File wordList = new File("google-10000-english.txt");
        Scanner sc = new Scanner(wordList);
        while (sc.hasNextLine()){
            String word = sc.nextLine();
            nodes.add(new PointerPSTNode(word, 0));
            splayTree.insert(word);
        }
        
        PointerPST aopsTree = new PointerPST(nodes);
        
        String fileName = "test_results.xlsx";
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        String sheetName = "Script";
        XSSFSheet sheet = workbook.getSheet(sheetName);
        if (sheet == null){
            sheet = workbook.createSheet(sheetName);
            System.out.println("");
        }
        
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell0 = row.createCell(0);
        cell0.setCellValue("Search Key");
        XSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("AOPST");
        XSSFCell cell2 = row.createCell(2);
        cell2.setCellValue("Splay Tree");
        XSSFCell cell3 = row.createCell(3);
        cell3.setCellValue("Difference (A - S)");

        PDDocument doc = PDDocument.load(new File("star-wars-episode-iv-a-new-hope-1977.pdf"));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String[] scriptWords = pdfStripper.getText(doc).split("\\W+");
        
        int count = 1;
        for (String key : scriptWords){
            int aopstComps = aopsTree.find(key.toLowerCase());
            int splayComps = splayTree.find(key.toLowerCase());
            if (aopstComps != 0){
                row = sheet.createRow(count);
                count++;
                cell0 = row.createCell(0);
                cell0.setCellValue(String.valueOf(key));
                cell1 = row.createCell(1);
                cell1.setCellValue(aopstComps);
                cell2 = row.createCell(2);
                cell2.setCellValue(splayComps);
                cell3 = row.createCell(3);
                cell3.setCellFormula("B" + count + "-C" + count);
            }
        }
        
        row = sheet.createRow(count);
        cell2 = row.createCell(2);
        cell2.setCellValue("Total:");
        cell3 = row.createCell(3);
        cell3.setCellFormula("SUM(D2:D" + count +")");
        
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
    }
}

