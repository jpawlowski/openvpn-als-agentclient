package com.adito.agent.client;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class JFrameLogin extends javax.swing.JFrame {
	private JPanel mainPane;
	private JTextField tUsername;
	private JTextField tServerIp;
	private JPasswordField tPassword;
	private JLabel jLabel2;
	private JLabel jLabel1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) throws Throwable {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrameLogin inst = new JFrameLogin();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public JFrameLogin() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				mainPane = new JPanel();
				
		        GridBagLayout gridbag = new GridBagLayout();
		        GridBagConstraints c = new GridBagConstraints();

				mainPane.setLayout(gridbag);
				getContentPane().add(mainPane, BorderLayout.CENTER);
				tUsername = new JTextField();
				tPassword = new JPasswordField();
				tServerIp = new JTextField();
				JLabel passwordFieldLabel = new JLabel("Password");
				passwordFieldLabel.setLabelFor(tPassword);
				jLabel1 = new JLabel("UserName");
				jLabel1.setLabelFor(tUsername);
				JLabel jLabelServerIp = new JLabel("Server IP");
				jLabelServerIp.setLabelFor(tServerIp);
		        JLabel[] labels = {jLabel1, passwordFieldLabel, jLabelServerIp};
		        JTextField[] textFields = {tUsername, tPassword, tServerIp};
		        addLabelTextRows(labels, textFields, gridbag, mainPane);

		        JLabel actionLabel = new JLabel("action label");
		        
		        c.gridwidth = GridBagConstraints.REMAINDER; //last
		        c.anchor = GridBagConstraints.WEST;
		        c.weightx = 1.0;
		        mainPane.add(actionLabel, c);
		        mainPane.setBorder(
		                BorderFactory.createCompoundBorder(
		                                BorderFactory.createTitledBorder("Text Fields"),
		                                BorderFactory.createEmptyBorder(5,5,5,5)));

			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private void addLabelTextRows(JLabel[] labels,
            JTextField[] textFields,
            GridBagLayout gridbag,
            Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;
		
		for (int i = 0; i < numLabels; i++) {
		c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
		c.fill = GridBagConstraints.NONE;      //reset to default
		c.weightx = 0.0;                       //reset to default
		container.add(labels[i], c);
		
		c.gridwidth = GridBagConstraints.REMAINDER;     //end row
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		container.add(textFields[i], c);
	}
}

}
