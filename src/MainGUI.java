import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class MainGUI {

	private JFrame frmTaskPlanner;
	private JTextField txtEnter;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frmTaskPlanner.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTaskPlanner = new JFrame();
		frmTaskPlanner.setTitle("Task Planner");
		frmTaskPlanner.setBounds(100, 100, 450, 300);
		frmTaskPlanner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextArea textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		frmTaskPlanner.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		txtEnter = new JTextField(10);
		txtEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = txtEnter.getText();
				textArea.append(input + "\n");
				txtEnter.setText("");
			}
		});
		frmTaskPlanner.getContentPane().add(txtEnter, BorderLayout.SOUTH);
		txtEnter.setColumns(10);
	}

}
