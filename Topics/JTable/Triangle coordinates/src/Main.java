import javax.swing.table.DefaultTableModel;
import java.util.Scanner;

class TriangleTable {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Object[] columns = new Object[]{"x", "y"};
        Object[][] data = new Object[][] {
                { Integer.valueOf(scanner.nextInt()), Integer.valueOf(scanner.nextInt()) },
                {Integer.valueOf(scanner.nextInt()), Integer.valueOf(scanner.nextInt())} ,
                {Integer.valueOf(scanner.nextInt()), Integer.valueOf(scanner.nextInt())} ,

        };

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        for (Object[] row : data) {
            model.addRow(row);
        }
        // do not remove the code below
        TableModelTest.test(model);
    }
}