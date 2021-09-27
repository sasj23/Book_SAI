/*
Autor: Sergio Alejandro Sánchez Juárez
Fecha: 27 de septiembre de 2021
*/

package gui;

import agent.BookBuyerAgent;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class BookBuyerGui extends JFrame {
    private BookBuyerAgent myAgent;
	
	private JTextField titleField;
        private JTextArea textArea;
	
	public BookBuyerGui(BookBuyerAgent a) {
		super(a.getLocalName());
		
		myAgent = a;
		
		JPanel head = new JPanel(new FlowLayout());
                JPanel tag = new JPanel(new FlowLayout());
                JPanel action = new JPanel(new FlowLayout());
		head.add(new JLabel("Titulo de Libro:"));
		titleField = new JTextField(15);
		head.add(titleField);
		
		JButton addButton = new JButton("Buscar");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String title = titleField.getText().trim();
					
					myAgent.search(title);
					titleField.setText("");
				}catch(Exception e) {
					JOptionPane.showMessageDialog(BookBuyerGui.this, "Datos ingresados incorrectos","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		head.add(addButton);
                getContentPane().add(head, BorderLayout.NORTH);
                tag.add(new JLabel("-- Acciones --"));
		getContentPane().add(tag, BorderLayout.CENTER);
                textArea = new JTextArea(10,40);
                JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                action.add(scroll);
                getContentPane().add(action, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
		  public void windowClosing(WindowEvent e) {
		    myAgent.doDelete();
		  }
		});
		
		setResizable(false);
	}
        
        public void showMessage(String text){
            textArea.append(text+"\n");
        }
	
	public void showGui() {
            setSize(500,300);
            setLocationRelativeTo(this);
            pack();
            //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            super.setVisible(true);
	}
}
