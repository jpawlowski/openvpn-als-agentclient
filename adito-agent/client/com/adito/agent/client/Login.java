package com.adito.agent.client;

import javax.swing.JOptionPane;
import javax.swing.SpinnerListModel;

import com.maverick.ssl.SSLTransportFactory;
import com.maverick.ssl.SSLTransportImpl;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Login.java
 *
 * Created on Mar 11, 2009, 1:33:37 PM
 */
/**
 *
 * @author zhou peng
 */
public class Login extends javax.swing.JFrame {

    /** Creates new form Login */
    public Login() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

        tUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tServerIP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        tServerPort = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        bLogin = new javax.swing.JButton();
        bExit = new javax.swing.JButton();
        protocolSpinner = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        tUsername.setText(("admin")); // NOI18N
        tUsername.setName("tUsername"); // NOI18N

        jLabel1.setText(Messages.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(Messages.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        tServerIP.setText(("127.0.0.1")); // NOI18N
        tServerIP.setName("tServerIP"); // NOI18N

        jLabel3.setText(Messages.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        tPassword.setText(("111111")); // NOI18N
        tPassword.setName("tPassword"); // NOI18N

        jLabel4.setText(Messages.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        tServerPort.setText(("8080")); // NOI18N
        tServerPort.setName("tServerPort"); // NOI18N

        jLabel5.setText(Messages.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        bLogin.setText(Messages.getString("bLogin.text")); // NOI18N
        bLogin.setName("bLogin"); // NOI18N
        bLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLoginActionPerformed(evt);
            }
        });
        getRootPane().setDefaultButton(bLogin); 

        bExit.setText(Messages.getString("bExit.text")); // NOI18N
        bExit.setName("bExit"); // NOI18N
        bExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExitActionPerformed(evt);
            }
        });

        String[] pStrings = {"http","https"}; 
        CyclingSpinnerListModel pModel = new CyclingSpinnerListModel(pStrings);
        protocolSpinner.setModel(pModel);
        protocolSpinner.setName("protocolSpinner"); // NOI18N
        protocolSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                protocolSpinnerStateChanged(evt);
            }
        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(170, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(tServerIP, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(tPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                        .addGap(98, 98, 98))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(protocolSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(bLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(bExit, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tServerIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(protocolSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bLogin)
                    .addComponent(bExit))
                .addGap(73, 73, 73))
        );

        this.setTitle("Agent Client");
        this.setLocationRelativeTo(null);
        pack();
    }// </editor-fold>                        

    private void bLoginActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
        String username = tUsername.getText();
        char[] passwd = tPassword.getPassword();
        String serverIp = tServerIP.getText();
        String serverPort = tServerPort.getText();
        String protocol = (String)protocolSpinner.getValue();

        String[] args = {"host="+serverIp+":"+serverPort, "protocol="+protocol, "username="+username,
        		"password="+new String(passwd), "forceBasicUI=true"};
        
        try{
        	AgentArgs agentArgs = Agent.initArgs(args);
        	
        	Agent agent = Agent.initAgent(agentArgs);
        	
            if (agentArgs.isDisableNewSSLEngine())
                SSLTransportFactory.setTransportImpl(SSLTransportImpl.class);

			boolean success = agent.initMain(agentArgs.getHostname(), agentArgs.getPort(), agentArgs.isSecure(), agentArgs.getUsername(), agentArgs.getPassword(), agentArgs.getTicket());

			if (success && agentArgs.getExtensionClasses() != null)
				agent.startExtensions(agentArgs.getExtensionClasses());
			
			if(success){
				this.dispose();
			}
		}catch(Throwable e){
        	JOptionPane.showMessageDialog(this, e.getMessage());
        }

}                                      

    private void protocolSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {
        // TODO add your handling code here:
    	if(protocolSpinner.getValue()=="http"){
    		tServerPort.setText("80");
    	}
    	else{
    		tServerPort.setText("443");
    	}
    }


    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {                                      
        // TODO add your handling code here:
        System.exit(0);
}                                     

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    public class CyclingSpinnerListModel extends SpinnerListModel {
        Object firstValue, lastValue;
        SpinnerListModel linkedModel = null;

        public CyclingSpinnerListModel(Object[] values) {
            super(values);
            firstValue = values[0];
            lastValue = values[values.length - 1];
        }

        public void setLinkedModel(SpinnerListModel linkedModel) {
            this.linkedModel = linkedModel;
        }

        public Object getNextValue() {
            Object value = super.getNextValue();
            if (value == null) {
                value = firstValue;
                if (linkedModel != null) {
                    linkedModel.setValue(linkedModel.getNextValue());
                }
            }
            return value;
        }

        public Object getPreviousValue() {
            Object value = super.getPreviousValue();
            if (value == null) {
                value = lastValue;
                if (linkedModel != null) {
                    linkedModel.setValue(linkedModel.getPreviousValue());
                }
            }
            return value;
        }
    }


    // Variables declaration - do not modify                     
    private javax.swing.JButton bExit;
    private javax.swing.JButton bLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField tPassword;
    private javax.swing.JTextField tServerIP;
    private javax.swing.JTextField tServerPort;
    private javax.swing.JTextField tUsername;
    private javax.swing.JSpinner protocolSpinner;
    // End of variables declaration                   

}
