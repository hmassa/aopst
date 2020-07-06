/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accessoptimizedpst;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author flipp
 */
public class AccessOptimizedPST {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        ArrayList<PointerPSTNode> nodes = new ArrayList<>();
        
//        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
//        for (char c : alphabet)
//            nodes.add(new PointerPSTNode(c, 0));

        for (int i = 1; i < 10; i++){
            nodes.add(new PointerPSTNode(i, 0));
        }
        
        PointerPST tree = new PointerPST(nodes);
        
        String fileName = "C:\\Users\\flipp\\Documents\\Computer Science\\Research\\AOPST\\test_results.xlsx";
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        
//        String sheetName = "Character";
        String sheetName = "Population";
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
        
//        String queryFileName = "C:\\Users\\flipp\\Documents\\Computer Science\\Research\\AOPST\\character.txt";
        String queryFileName = "C:\\Users\\flipp\\Documents\\Computer Science\\Research\\AOPST\\population.txt";
        File queryFile = new File(queryFileName);
        
        // For character tree
//        FileReader reader = new FileReader(queryFile);
//        int content, count = 1;
//        while ((content = reader.read()) != -1){
//            char searchKey = Character.toLowerCase((char)content);
//            int comparisons = tree.aopstSearch(searchKey);
//            if (comparisons != 0){
//                row = sheet.createRow(count);
//                cell0 = row.createCell(0);
//                cell0.setCellValue(String.valueOf(searchKey));
//                cell1 = row.createCell(1);
//                cell1.setCellValue(comparisons);
//                count++;
//            }
//        }
        
        Scanner sc = new Scanner(queryFile);
        ArrayList<Integer> queries = new ArrayList<>();
        
        while (sc.hasNextLine()){
            char[] chars = sc.nextLine().toCharArray();
            queries.add(Character.getNumericValue(chars[0]));
        }
        
        Collections.shuffle(queries);
        int count = 1;
        for (Integer query : queries) {
            int comparisons = tree.aopstSearch(query);
            row = sheet.getRow(count);
            cell0 = row.createCell(0);
            cell0.setCellValue(query);
            cell1 = row.createCell(3);
            cell1.setCellFormula("B" + count + "-C" + count);
            count++;
        }
        
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
    }
}
