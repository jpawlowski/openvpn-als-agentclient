
				/*
 *  Adito
 *
 *  Copyright (C) 2003-2006 3SP LTD. All Rights Reserved
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
			
package com.adito.agent.client.gui.awt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
//import java.awt.Frame;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

//import com.sshtools.ui.awt.ImageCanvas;
//import com.sshtools.ui.awt.UIUtil;
//import com.sshtools.ui.awt.tooltips.ToolTipManager;
import com.adito.agent.client.Agent;
import com.adito.agent.client.AgentAction;

/**
 * Implementation of an {@link AbstractAWTGUI} that uses a simple frame to provide
 * a GUI for the Agent. This is most likely to be used on platforms that do not
 * support system tray functionality.
 */
public class BasicFrameGUI extends AbstractAWTGUI {

    // Private instance variables

    private Image idle;
    private Image tx;
    private Image rx;
    private Image txrx;
    private Image disconnected;
    private Image banner;
    
    //private ImageCanvas activityPanel;
    private JLabel activityPanelLabel;
    private JFrame frame;
    private JMenu file;
    private JMenuBar menu;
    private Hashtable menuLookup;
    private TreeJPanel appPanel;
    private SwingPortMonitorWindow monitorFrame;
    
    private JPanel middle;
    private JPanel main;

    /* (non-Javadoc)
     * @see com.adito.agent.client.AgentClientGUI#init(com.adito.agent.client.Agent)
     */
    public void init(Agent agent) {
        super.init(agent);

        monitorFrame = getPortMonitorWindow();
        monitorFrame.setVisible(true);
        
        JMenuItem open = new JMenuItem(Messages.getString("GUI.menu.openBrowser")); //$NON-NLS-1$
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openBrowser(null);
            }
        });

        // #ifdef DEBUG
        JMenuItem console = new JMenuItem(Messages.getString("GUI.menu.debugConsole")); //$NON-NLS-1$
        console.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                getConsole().show();
            }
        });
        // #endif

        JMenuItem ports = new JMenuItem(Messages.getString("GUI.menu.tunnelMonitor")); //$NON-NLS-1$
        ports.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                getPortMonitor().setVisible(!getPortMonitor().isVisible());
            }
        });

        JMenuItem exit = new JMenuItem(Messages.getString("GUI.menu.exit")); //$NON-NLS-1$
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                getAgent().disconnect();
            }
        });

        middle = new JPanel(new BorderLayout());
        
        main = new JPanel(new BorderLayout());
        main.setBackground(Color.black);
        

        banner = UIUtil.loadImage(getClass(), "/images/banner-small.gif"); //$NON-NLS-1$
        idle = UIUtil.loadImage(getClass(), "/images/tray-idle.gif"); //$NON-NLS-1$        
        tx = UIUtil.loadImage(getClass(), "/images/tray-tx.gif"); //$NON-NLS-1$
        rx = UIUtil.loadImage(getClass(), "/images/tray-rx.gif"); //$NON-NLS-1$
        txrx = UIUtil.loadImage(getClass(), "/images/tray-txrx.gif"); //$NON-NLS-1$
        disconnected = UIUtil.loadImage(getClass(), "/images/tray-disconnecting.gif"); //$NON-NLS-1$
        
        UIUtil.waitFor(banner, main);
        UIUtil.waitFor(idle, main);
        UIUtil.waitFor(tx, main);
        UIUtil.waitFor(rx, main);
        UIUtil.waitFor(txrx, main);
        UIUtil.waitFor(disconnected, main);
        
        JLabel bannerLabel = new JLabel();
        bannerLabel.setIcon(new javax.swing.ImageIcon(banner)); 
        //ImageCanvas bannerCanvas = new ImageCanvas(banner); 
        //bannerCanvas.setHalign(ImageCanvas.LEFT_ALIGNMENT);
        //bannerCanvas.setBackground(Color.black);
        main.add(bannerLabel, BorderLayout.WEST);
                
        //activityPanel = new ImageCanvas(idle);
        //activityPanel.setSize(new Dimension(32, 32));
        //activityPanel.setBorder(4);
        activityPanelLabel = new JLabel();
        activityPanelLabel.setSize(new Dimension(32, 32));
        activityPanelLabel.setIcon(new javax.swing.ImageIcon(idle));
        
        //main.add(activityPanel, BorderLayout.EAST);
        main.add(activityPanelLabel, BorderLayout.EAST);
               
        appPanel = new TreeJPanel();
        
        middle.add(appPanel, BorderLayout.CENTER);
        middle.add(monitorFrame, BorderLayout.EAST);
        middle.add(main, BorderLayout.SOUTH);
        
        
        menu = new JMenuBar();
        menuLookup = new Hashtable();

        file = new JMenu(Messages.getString("GUI.menu.file")); //$NON-NLS-1$
        // #ifdef DEBUG
        file.add(console);
        // #endif
        file.add(open);
        file.add(ports);
        file.addSeparator();
        file.add(exit);
        menu.add(file);

        frame = new JFrame(Messages.getString("GUI.title")); //$NON-NLS-1$
        frame.setContentPane(middle);
        //frame.add(main);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screensize.width/2, screensize.height/2);
        //frame.add(menu);
        frame.setJMenuBar(menu);
        frame.setIconImage(UIUtil.loadImage(getClass(), "/images/frame-agent.gif")); //$NON-NLS-1$
        
        frame.setLocation(screensize.width/4, screensize.height/4);
        
        //frame.pack();
        //frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                getAgent().disconnect();
            }
        });
    }
    
    /* (non-Javadoc)
     * @see com.adito.agent.client.AgentClientGUI#addMenu(java.lang.String)
     */
    public void addMenu(final String name) {
    	//javax.swing.JOptionPane.showMessageDialog(frame, name);
    /*	JMenu foo = new JMenu(name);
    	menuLookup.put(name, foo);    	
    	menu.add(foo);*/
    	appPanel.addRoot(name.trim());
    }

	/* (non-Javadoc)
	 * @see com.adito.agent.client.AgentClientGUI#removeMenu(java.lang.String)
	 */
	public void removeMenu(String name) {
	/*	JMenu menu = (JMenu)menuLookup.get(name);
		if(menu != null) {
			menuLookup.remove(name);
			this.menu.remove(menu);
		}*/
		appPanel.removeRoot(name);
	}
	
	/* (non-Javadoc)
	 * @see com.adito.agent.client.AgentClientGUI#clearMenu(java.lang.String)
	 */
	public void clearMenu(String name) {
	/*	JMenu menu = (JMenu)menuLookup.get(name);
		if(menu != null) {
			while(menu.getItemCount() > 0) {
				menu.remove(0);
			}
		}		*/
		appPanel.removeRoot(name);
	}
	
	/* (non-Javadoc)
	 * @see com.adito.agent.client.AgentClientGUI#isMenuExists(java.lang.String)
	 */
	public boolean isMenuExists(String name) {
		/*return menuLookup.containsKey(name);*/
		return (appPanel.findRootNode(name)!=null);
	}
    
    /* (non-Javadoc)
     * @see com.adito.agent.client.AgentClientGUI#addMenuItem(java.lang.String, com.adito.agent.client.AgentAction)
     */
    public void addMenuItem(final String parentName, final AgentAction action) {
    	/*
    	JMenu menu = parentName == null ? file : (JMenu) menuLookup.get(parentName);
        if(menu==null)
        	return;
        JMenuItem item = new JMenuItem(action.getAction());
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                action.actionPerformed();
            }
        });
        menu.add(item);*/
        //menu.repaint();
        //System.out.println(action.getAction());
       	appPanel.addNode(parentName.trim(), action.getAction().trim(), action);

        //appPanel.repaint();
      //  frame.getContentPane().repaint();
    }

    public void addMenuLeaf(final String leafInfo, final AgentAction action){
    	appPanel.addLeaf(leafInfo, action);
    }
    
    /* (non-Javadoc)
     * @see com.adito.vpn.client.VPNClientGUI#showIdle()
     */
    public void showIdle() {
        //activityPanel.setImage(idle);
    	activityPanelLabel.setIcon(new ImageIcon(idle));
    }

    /* (non-Javadoc)
     * @see com.adito.vpn.client.VPNClientGUI#showDisconnected()
     */
    public void showDisconnected() {
        //activityPanel.setImage(disconnected);
    	activityPanelLabel.setIcon(new ImageIcon(disconnected));
    	frame.setVisible(false);
    }

    /* (non-Javadoc)
     * @see com.adito.vpn.client.VPNClientGUI#showTx()
     */
    public void showTx() {
        //activityPanel.setImage(tx);
    	activityPanelLabel.setIcon(new ImageIcon(tx));
    }

    /* (non-Javadoc)
     * @see com.adito.vpn.client.VPNClientGUI#showRx()
     */
    public void showRx() {
        //activityPanel.setImage(rx);
    	activityPanelLabel.setIcon(new ImageIcon(rx));
    }

	/* (non-Javadoc)
	 * @see com.adito.agent.client.gui.awt.AbstractAWTGUI#dispose()
	 */
	public void dispose() {
		frame.dispose();
		super.dispose();
	}

	public void setVisible(boolean v){
    	frame.setVisible(v);
	}
	/* (non-Javadoc)
	 * @see com.adito.agent.client.AgentClientGUI#addMenuSeperator(java.lang.String)
	 */
	public void addMenuSeperator(String parentName) {
	
		JMenu m = parentName == null ? file : (JMenu)menuLookup.get(parentName);
		if(m==null)
			return;
		
		m.addSeparator();
		
	}

    /* (non-Javadoc)
     * @see com.adito.vpn.client.VPNClientGUI#showTxRx()
     */
    public void showTxRx() {
        //activityPanel.setImage(txrx);
    	activityPanelLabel.setIcon(new ImageIcon(txrx));
    }

    /* (non-Javadoc)
     * @see com.adito.vpn.client.VPNClientGUI#setInfo(java.lang.String)
     */
    public void setInfo(String info) {
        //ToolTipManager.getInstance().requestToolTip(activityPanel, info);
    	activityPanelLabel.setToolTipText(info);
    }

    /* (non-Javadoc)
     * @see com.adito.vpn.client.AbstractGUI#getGUIComponent()
     */
    public Component getGUIComponent() {
        return frame;
    }

}
