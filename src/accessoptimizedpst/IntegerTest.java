package accessoptimizedpst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class IntegerTest {
    public static void main(String[] args) throws IOException{
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            ints.add(i);
        
        Collections.shuffle(ints);

        SplayTree splayTree = new SplayTree();
        ArrayList<PointerPSTNode> nodes = new ArrayList<>();
        
        for (Integer i : ints){
            splayTree.insert(i);
            nodes.add(new PointerPSTNode(i, 0));
        }
        
        PointerPST aopsTree = new PointerPST(nodes);
        
        String fileName = "test_results.xlsx";
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

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
        XSSFCell cell2 = row.createCell(2);
        cell2.setCellValue("Splay Tree");
        XSSFCell cell3 = row.createCell(3);
        cell3.setCellValue("Difference (A - S)");
        

        String queryFileName = "population.txt";
        File queryFile = new File(queryFileName);
        
        Scanner sc = new Scanner(queryFile);
        ArrayList<Integer> queries = new ArrayList<>();
        
        while (sc.hasNextLine()){
            char[] chars = sc.nextLine().toCharArray();
            queries.add(Character.getNumericValue(chars[0]));
        }
        
        Collections.shuffle(queries);
        int count = 1;
        for (Integer query : queries) {
            int aopstComps = aopsTree.find(query);
            int splayComps = splayTree.find(query);
            row = sheet.createRow(count);
            count++;
            cell0 = row.createCell(0);
            cell0.setCellValue(query);
            cell1 = row.createCell(1);
            cell1.setCellValue(aopstComps);
            cell2 = row.createCell(2);
            cell2.setCellValue(splayComps);
            cell3 = row.createCell(3);
            cell3.setCellFormula("B" + count + "-C" + count);    
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
