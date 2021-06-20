package com.company.newsPortal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubscriberUI extends JFrame{

    private JTextField txtName;
    private JTextField txtEmail;
    private JButton btnSubscribe;
    private JPanel mainPanel;

    private static NewsPublisher publisher;
    public SubscriberUI(String title){
        super(title);
        this.setContentPane(mainPanel);
        this.pack();

        btnSubscribe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Subscriber newsSubscriber=new NewsSubscriber(txtName.getText(),txtEmail.getText());
                publisher.subscribe(newsSubscriber);
                JOptionPane.showMessageDialog(mainPanel,"Subscription successful");
                txtName.setText("");
                txtEmail.setText("");

            }
        });
    }





    public static void main(String[] args) {

        JFrame subscribeUI=new SubscriberUI("Subscribe Now");
        subscribeUI.setSize(400,200);
        subscribeUI.setVisible(true);
        subscribeUI.setLocationRelativeTo(null);


        publisher = new NewsPublisher();
        publisher.run();

    }
}
