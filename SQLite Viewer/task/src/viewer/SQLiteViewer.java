package viewer;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class SQLiteViewer extends JFrame {
    private final JTextField textFieldFileName;
    private final JComboBox comboBox;
    private final JButton buttonExecute;
    private JTextArea queryTextArea;
    private JTable table;
    private TableModel tableModel;

    private DatabaseManagement databaseManagement;

    public SQLiteViewer() {
        super("SQLite Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        /*
        *JPanels
        * */
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        setContentPane(content);

        JPanel north = new JPanel();
        north.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JPanel center = new JPanel();
        center.setLayout(new FlowLayout());

        JPanel east = new JPanel();
        east.setLayout(new FlowLayout());

        JPanel south = new JPanel();
        south.setLayout(new FlowLayout());

        /*
        * North
        * */

        textFieldFileName = new JTextField(55);
        textFieldFileName.setName("FileNameTextField");
        north.add(textFieldFileName);

        JButton buttonOpenFile = new JButton("Open");
        buttonOpenFile.setName("OpenFileButton");
        buttonOpenFile.addActionListener(e -> readNewTable());
        north.add(buttonOpenFile);

        /*
        * Center
        * */

        comboBox = new JComboBox();
        comboBox.setName("TablesComboBox");
        comboBox.setPreferredSize(new Dimension(640, 20));
        comboBox.addActionListener(e ->
                queryTextArea.setText("SELECT * FROM " + comboBox.getSelectedItem() + ";")
        );
        center.add(comboBox);

        queryTextArea = new JTextArea();
        queryTextArea.setName("QueryTextArea");
        queryTextArea.setPreferredSize(new Dimension(550, 150));
        queryTextArea.setEnabled(false);
        center.add(queryTextArea);

        buttonExecute = new JButton("Execute");
        buttonExecute.setName("ExecuteQueryButton");
        buttonExecute.setPreferredSize(new Dimension(100, 40));
        buttonExecute.setEnabled(false);
        buttonExecute.addActionListener(e -> {
            try {
                databaseManagement.setRequest(queryTextArea.getText());
                SwingUtilities.updateComponentTreeUI(this);
                tableModel = new TableSqlData(
                        databaseManagement.getColumnNames(),
                        databaseManagement.getData()
                );
                table.setModel(tableModel);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(new Frame(), "ERROR");
            }

        });
        center.add(buttonExecute);

        table = new JTable();
        table.setName("Table");

        JScrollPane scrollPaneTable = new JScrollPane(table);
        scrollPaneTable.setPreferredSize(new Dimension(650, 220));
        scrollPaneTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        center.add(scrollPaneTable);

        content.add(north, BorderLayout.NORTH);
        content.add(center, BorderLayout.CENTER);
        content.add(east, BorderLayout.EAST);
        setVisible(true);
    }

    private void readNewTable() {
        String fileName = textFieldFileName.getText();
            comboBox.removeAllItems();
            queryTextArea.setText("");
            queryTextArea.setEnabled(false);
            buttonExecute.setEnabled(false);
            try {
                databaseManagement = new DatabaseManagement(fileName);
                databaseManagement.getTablesNames()
                        .forEach(comboBox::addItem);
                queryTextArea.setEnabled(true);
                buttonExecute.setEnabled(true);
            } catch (FileNotFoundException | SQLException fileNotFoundException) {
                JOptionPane.showMessageDialog(new Frame(), "File doesn't exist!");
            }
        queryTextArea.removeAll();
    }

}



