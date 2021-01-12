package main.java.UI;

import main.java.Helper.DBHelper;
import main.java.Models.Reviews;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReviewsPage extends JFrame{
    private JFrame frame;
    private JButton jButton1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private String businessId;
    private DBHelper db;

    public ReviewsPage() {
        initComponents();
    }

    public ReviewsPage(String businessId){
        this.businessId = businessId;
        db = new DBHelper();
        db.DBConnect();
        initComponents();
        loadReviews();
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 800));
        getContentPane().setBackground(UIManager.getColor("Desktop.background"));
        getContentPane().setForeground(Color.BLUE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}
                },
                new String [] {
                        "Review Date", "Stars", "Review Text", "UserID",  "Usefull Votes"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Close");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 992, Short.MAX_VALUE)
                                        .addComponent(jButton1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 632, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                                .addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();

    }

    public void loadReviews(){
        DefaultTableModel tmodel = new DefaultTableModel();
        jTable1.setModel(tmodel);
        tmodel.addColumn("Review Date");
        tmodel.addColumn("Stars");
        tmodel.addColumn("Review Text");
        tmodel.addColumn("UserID");
        tmodel.addColumn("Usefull Votes");

        ArrayList<Reviews> reviews = db.getReviews(businessId);
        for(int i=0; i<reviews.size(); i++){
            Reviews review = reviews.get(i);
            tmodel.addRow(new Object[]{review.getDate(), review.getStars(), review.getText(), review.getUserId(), review.getVotesUseful()});
        }

        pack();

    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ReviewsPage().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
