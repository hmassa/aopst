package accessoptimizedpst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author flipp
 */
public abstract class Test {
    private SplayTree splayTree;
    private PointerPST aopsTree;
    
    protected ArrayList<Comparable> keys = new ArrayList<>();
    protected ArrayList<Comparable> queries = new ArrayList<>();
    
    private File file;
    
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell0;
    private XSSFCell cell1;
    private XSSFCell cell2;
    private XSSFCell cell3;
    
    public void generateTrees(){
        splayTree = new SplayTree();
        
        ArrayList<PointerPSTNode> nodes = new ArrayList<>();
        for (Comparable k : keys){
            splayTree.insert(k);
            nodes.add(new PointerPSTNode(k, 0));
        }
        
        aopsTree = new PointerPST(nodes);
    }
    
    public void openExcel() throws FileNotFoundException, IOException{
        String fileName = "test_results.xlsx";
        file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        workbook = new XSSFWorkbook(fis);
        
        sheet = workbook.getSheet(getSheetName());
        if (sheet == null){
            sheet = workbook.createSheet(getSheetName());
            System.out.println("");
        }
        
        row = sheet.createRow(0);
        cell0 = row.createCell(0);
        cell0.setCellValue("Search Key");
        cell1 = row.createCell(1);
        cell1.setCellValue("AOPST");
        cell2 = row.createCell(2);
        cell2.setCellValue("Splay Tree");
        cell3 = row.createCell(3);
        cell3.setCellValue("Difference (A - S)");
    }
    
    public void searchAndWrite() throws IOException{
        int count = 1;
        for (Comparable query : queries) {
            int aopstComps = aopsTree.find(query);
            int splayComps = splayTree.find(query);
            if (aopstComps != 0){
                row = sheet.createRow(count);
                count++;
                cell0 = row.createCell(0);
                cell0.setCellValue(getString(query));
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
    
    abstract void generateKeys();
    abstract String getSheetName();
    abstract void generateQueries();
    abstract String getString(Comparable x);
}
