package viewer;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class SQLiteViewer extends JFrame {
    private final JTextField textFieldFileName;
    private final JComboBox comboBox;
    private JTextArea queryTextArea;
    private JTable table;

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
        buttonOpenFile.addActionListener(
                e -> readNewTable()
        );
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
        center.add(queryTextArea);

        JButton buttonExecute = new JButton("Execute");
        buttonExecute.setName("ExecuteQueryButton");
        buttonExecute.setPreferredSize(new Dimension(100, 40));
        buttonExecute.addActionListener(e -> {
            databaseManagement.setRequest(queryTextArea.getText());
            TableModel tableModel = new TableSqlData(
                    databaseManagement.getColumnNames(),
                    databaseManagement.getData()
            );

            table.setModel(tableModel);
            SwingUtilities.updateComponentTreeUI(this);
        });
        center.add(buttonExecute);

        table = new JTable();
        table.setName("Table");

        JScrollPane scrollPaneTable = new JScrollPane(table);
        scrollPaneTable.setPreferredSize(new Dimension(550, 220));
        scrollPaneTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        center.add(scrollPaneTable);

        content.add(north, BorderLayout.NORTH);
        content.add(center, BorderLayout.CENTER);
        content.add(east, BorderLayout.EAST);
        setVisible(true);
    }

    private void readNewTable() {
        String fileName = textFieldFileName.getText();

        if (!fileName.equals("")) {
            comboBox.removeAllItems();
            databaseManagement = new DatabaseManagement(fileName);
            databaseManagement.getTablesNames()
                    .forEach(comboBox::addItem);
        }
        queryTextArea.removeAll();
    }

}



