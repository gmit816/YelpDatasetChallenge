package main.java.UI;

import main.java.Helper.DBHelper;
import main.java.Models.Attribute;
import main.java.Models.Business;
import main.java.Models.BusinessCategories;
import main.java.Models.BusinessSubCategories;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.BorderFactory.createEtchedBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class HW3 {
    ArrayList<String> selectedCategories = new ArrayList<String>();
    ArrayList<String> selectedSubCategories = new ArrayList<String>();
    ArrayList<String> selectedAttributes = new ArrayList<String>();
    ArrayList<String> generatedBusinessIds = new ArrayList<String>();


    private JFrame frame;
    private JButton jButton1;
    private JButton jButton2;
    private JComboBox jComboBox1;
    private JComboBox jComboBox3;
    private JComboBox jComboBox4;
    private JComboBox comboBox;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel dayLabel;
    private JLabel cityLabel;
    private JLabel stateLabel;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JTable jTable1;
    private JTextField jTextField1;
    private JTextField jTextField2;

    private JLabel lblFrom;
    private JLabel lblTo;
    private JLabel lblYelpSearch;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;

    private DBHelper db;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HW3 window = new HW3();
                    window.frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public HW3() {

        db = new DBHelper();
        db.DBConnect();

        initComponents();
        loadCategories();
    }

    private void initComponents() {

        frame = new JFrame();
        frame.setPreferredSize(new java.awt.Dimension(1600, 900));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        jScrollPane1 = new JScrollPane();
        jScrollPane2 = new JScrollPane();
        jPanel1 = new JPanel();
        jScrollPane3 = new JScrollPane();
        jTable1 = new JTable();
        jScrollPane4 = new JScrollPane();
        jPanel2 = new JPanel();
        jScrollPane5 = new JScrollPane();
        jPanel3 = new JPanel();
        jComboBox3 = new JComboBox();
        jComboBox4 = new JComboBox();
        comboBox = new JComboBox();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jComboBox1 = new JComboBox();
        dayLabel = new JLabel();
        jTextField1 = new JTextField();
        cityLabel = new JLabel();
        stateLabel = new JLabel();
        jTextField2 = new JTextField();



        jScrollPane2.setBorder(createEtchedBorder());
        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel1.setBackground(new java.awt.Color(41, 149, 204));
        jPanel1.setToolTipText("");
        jPanel1.setMaximumSize(new java.awt.Dimension(200, 800));
        jPanel1.setMinimumSize(new java.awt.Dimension(200, 800));
        jPanel1.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane2.setViewportView(jPanel1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] {
                        "Business", "City", "State", "Stars"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);

        jScrollPane4.setMaximumSize(new java.awt.Dimension(300, 800));

        jPanel2.setBackground(new java.awt.Color(41, 149, 204));
        jPanel2.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane4.setViewportView(jPanel2);

        jScrollPane5.setMaximumSize(new java.awt.Dimension(300, 800));

        jPanel3.setBackground(new java.awt.Color(41, 149, 204));
        jPanel3.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane5.setViewportView(jPanel3);

        jComboBox3.setModel(new DefaultComboBoxModel(new String[] { "None", "00:00",  "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"}));
//        jComboBox3.setEnabled(false);

        jComboBox4.setModel(new DefaultComboBoxModel(new String[] { "None", "00:00",  "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00" }));
//        jComboBox4.setEnabled(false);

        jComboBox1.setModel(new DefaultComboBoxModel(new String[] { "None", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }));

        comboBox.setModel(new DefaultComboBoxModel(new String[] { "AND", "OR" }));

        dayLabel.setText("Day");

        jTextField1.setText("");

        cityLabel.setText("City");

        stateLabel.setText("State");

        jTextField2.setText("");

        JButton jButton1 = new JButton("Search");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });

        lblFrom = new JLabel("From");

        lblTo = new JLabel("To");

        lblYelpSearch = new JLabel("Yelp Search");

        lblNewLabel = new JLabel("");

        lblNewLabel_1 = new JLabel("Condition");


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel3)
                                                                                .addGap(50))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel2)
                                                                                .addGap(40)))
                                                                .addGap(32))
                                                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                                                                .addComponent(dayLabel)
                                                                .addGap(6)))
                                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(28)
                                                                .addComponent(cityLabel)
                                                                .addGap(18)
                                                                .addComponent(jTextField1, 132, 132, 132))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18)
                                                                .addComponent(lblFrom)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(11)
                                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(7)
                                                                .addComponent(lblTo)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(jComboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(22)
                                                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(lblNewLabel)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                                .addComponent(lblNewLabel_1)
                                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(stateLabel)
                                                                .addGap(18)
                                                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(31))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(304)
                                                .addComponent(lblYelpSearch))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(32)
                                                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 603, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(300)
                                                .addComponent(jButton1)))
                                .addGap(147))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(16)
                                                        .addComponent(lblYelpSearch)
                                                        .addGap(27)
                                                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18)
                                                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                                                .addComponent(dayLabel)
                                                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblNewLabel)
                                                                .addComponent(lblFrom)
                                                                .addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblTo)
                                                                .addComponent(jComboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblNewLabel_1)
                                                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(27)
                                                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(cityLabel)
                                                                .addComponent(stateLabel)
                                                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(18)
                                                        .addComponent(jButton1)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(44)
                                                .addComponent(jLabel3))
                                        .addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(jScrollPane5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                                .addComponent(jScrollPane4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)))
                                .addContainerGap(72, Short.MAX_VALUE))
        );

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                if (!(generatedBusinessIds.size() < row)) {
                    frame.setEnabled(false);
                    jTable1.setOpaque(false);
                    ReviewsPage rp = new ReviewsPage(generatedBusinessIds.get(row));
                    rp.setVisible(true);
                    System.out.println("here");
                    frame.setEnabled(true);
                }
            }
        });

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        jTable1.setDefaultRenderer(Object.class, new MyTableCellRender());

//        jTable1.showHorizontalLines(true);
//        jTable1.showVerticalLines(false);
        jTable1.setGridColor(Color.BLACK);
        frame.getContentPane().setLayout(layout);

        frame.pack();
    }



    private void loadCategories() {
        jPanel1.removeAll();
        ArrayList<BusinessCategories> cats = db.getAllCategories();
        for(int i=0; i<cats.size(); i++){
            JCheckBox mycheckbox = new JCheckBox();
            mycheckbox.setSize(10,10);
            mycheckbox.setText(cats.get(i).getCategoryName());
            mycheckbox.setForeground(Color.BLACK);
            mycheckbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    Object source = e.getItemSelectable();
                    JCheckBox checkbox = (JCheckBox) source;
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        System.out.println("selected");
                        System.out.println(checkbox.getText());
                        selectedCategories.add(checkbox.getText());

                    } else {
                        System.out.println("unselected");
                        System.out.println(checkbox.getText());
                        selectedCategories.remove(checkbox.getText());
                    }
                    if(selectedCategories.size()>0) {
                        loadSubCategories();
                        updateData();
                    }else{
                        jPanel2.removeAll();
                        jPanel2.repaint();

                        jPanel3.removeAll();
                        jPanel3.repaint();

                        DefaultTableModel tmodel = new DefaultTableModel();
                        jTable1.removeAll();
                        jTable1.setModel(tmodel);
                        tmodel.addColumn("Business");
                        tmodel.addColumn("City");
                        tmodel.addColumn("State");
                        tmodel.addColumn("Stars");
                    }
                }
            });
            jPanel1.add(mycheckbox);
            frame.pack();
        }

//        jPanel1.validate();
//        jPanel1.repaint();
    }

    private void loadSubCategories(){

        String condition = comboBox.getSelectedItem().toString();
        ArrayList<BusinessSubCategories> subs = db.getSubCategories( selectedCategories, condition);
        jPanel2.removeAll();
        for(int i=0; i<subs.size(); i++) {
            JCheckBox mycheckbox = new JCheckBox();
            mycheckbox.setSize(10, 10);
            mycheckbox.setText(subs.get(i).getSubCategoryName());
            mycheckbox.setForeground(Color.BLACK);
            mycheckbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    Object source = e.getItemSelectable();
                    JCheckBox checkbox = (JCheckBox) source;
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        System.out.println("selected");
                        System.out.println(checkbox.getText());
                        selectedSubCategories.add(checkbox.getText());

                    } else {
                        System.out.println("unselected");
                        System.out.println(checkbox.getText());
                        selectedSubCategories.remove(checkbox.getText());
                    }
                    if(selectedSubCategories.size()>0) {
                        loadAttributes();
                        updateData();
                    }
                }
            });
            jPanel2.add(mycheckbox);
            frame.pack();
        }
//        jPanel2.repaint();
    }

    public void loadAttributes(){
        String condition = comboBox.getSelectedItem().toString();

        ArrayList<Attribute> attrs = db.getAttributes( selectedSubCategories, selectedCategories, condition);
        jPanel3.removeAll();
        for(int i=0; i<attrs.size(); i++) {
            JCheckBox mycheckbox = new JCheckBox();
            mycheckbox.setSize(10, 10);
            mycheckbox.setText(attrs.get(i).getAttribute_name());
            mycheckbox.setForeground(Color.BLACK);
            mycheckbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    Object source = e.getItemSelectable();
                    JCheckBox checkbox = (JCheckBox) source;
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        System.out.println("selected");
                        System.out.println(checkbox.getText());
                        selectedAttributes.add(checkbox.getText());

                    } else {
                        System.out.println("unselected");
                        System.out.println(checkbox.getText());
                        selectedAttributes.remove(checkbox.getText());
                    }
                    if(selectedAttributes.size()>0) {
//                        fillTable();
                        updateData();
                    }
                }
            });
            jPanel3.add(mycheckbox);
            frame.pack();
        }

    }

//    public void fillTable(){
//        DefaultTableModel tmodel = new DefaultTableModel();
//        jTable1.removeAll();
//        jTable1.setModel(tmodel);
//        tmodel.addColumn("Business");
//        tmodel.addColumn("City");
//        tmodel.addColumn("State");
//        tmodel.addColumn("Stars");
//        ArrayList<Business> businesses = db.queryBusiness(selectedAttributes, selectedCategories, selectedSubCategories);
//        for(int i=0; i<businesses.size(); i++){
//            Business business = businesses.get(i);
//            tmodel.addRow(new Object[]{business.getName() , business.getCity(), business.getState(), business.getStars()});
//            generatedBusinessIds.add(business.getBusinessId());
//        }
//
//        frame.pack();
//    }

    public void updateData(){

        DefaultTableModel tmodel = new DefaultTableModel();
        jTable1.removeAll();
        jTable1.setModel(tmodel);
        tmodel.addColumn("Business");
        tmodel.addColumn("City");
        tmodel.addColumn("State");
        tmodel.addColumn("Stars");

        String city = jTextField1.getText();
        String state = jTextField2.getText();
        String day = "";
        if (jComboBox1.getSelectedItem().toString() != "None") {
            day = jComboBox1.getSelectedItem().toString();

        }
        String fromTime = "";
        if(jComboBox3.getSelectedItem().toString() != "None"){
            fromTime = jComboBox3.getSelectedItem().toString();
        }

        String toTime = "";
        if(jComboBox4.getSelectedItem().toString() != "None"){
            toTime = jComboBox4.getSelectedItem().toString();
        }

        String condition = comboBox.getSelectedItem().toString();

        ArrayList<Business> businesses = new ArrayList<Business>();
        if(selectedAttributes.size() ==0 && selectedSubCategories.size() == 0 && selectedCategories.size() >0){
            businesses = db.queryBusinessByCategory(selectedCategories,condition);
        }
        else if(selectedCategories.size() >0 && selectedSubCategories.size()>0 && selectedAttributes.size() ==0){
            businesses = db.queryBusinessByCategorySubCategory(selectedCategories,selectedSubCategories,condition);
        }
        else if(selectedCategories.size()>0 && selectedSubCategories.size()>0 && selectedAttributes.size()>0){
            if(day.length()>0 || fromTime.length()>0 || toTime.length()>0 || city.length() >0 || state.length() >0){
                businesses = db.advancedQueryBusiness(selectedCategories, selectedSubCategories, selectedAttributes, city, state, day, fromTime, toTime, condition);
            }else {
                businesses = db.queryBusiness(selectedAttributes, selectedCategories, selectedSubCategories, condition);
            }
        }
        generatedBusinessIds = new ArrayList<String>();

        System.out.println("row count :"+businesses.size());
        for (int i = 0; i < businesses.size(); i++) {
            Business business = businesses.get(i);
            tmodel.addRow(new Object[]{business.getName(), business.getCity(), business.getState(), business.getStars()});
            generatedBusinessIds.add(business.getBusinessId());
        }
        frame.pack();

    }


    class MyTableCellRender extends DefaultTableCellRenderer {

        public MyTableCellRender() {
        }

        final JLabel headerLabel = new JLabel();
        {
            //setBorder(BorderFactory.createEmptyBorder());
            headerLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.RED));
            headerLabel.setOpaque(true);
            headerLabel.setBackground(Color.WHITE);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            setForeground(Color.black);
            setBackground(Color.white);
            setText(value != null ? value.toString() : "");
            return this;
        }
    }
}
