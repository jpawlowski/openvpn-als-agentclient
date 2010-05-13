package com.adito.agent.client.gui.awt;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.adito.agent.client.AgentAction;
import com.adito.agent.client.PortMonitor;


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
public class TreeJPanel extends javax.swing.JPanel implements TreeSelectionListener{
	private JTree jTreeApps;
	private JScrollPane jScrollPane1;
	private DefaultMutableTreeNode top;
	private DefaultTreeModel treeModel;

    private Image rootImage;
    private Image appImage;

    private javax.swing.JPanel buttonPane;
    private JButton bStart;
    
    private TreePath tree_path;
    
    private String rootName;
    
    /**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new TreeJPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public TreeJPanel() {
		super();
		initGUI();
	}
	
	public TreeJPanel(javax.swing.JPanel bPane) {
		super();
		buttonPane = bPane;
		initGUI();
	}
		
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
		        rootImage = UIUtil.loadImage(getClass(), "/images/32.gif");
		        appImage = UIUtil.loadImage(getClass(), "/images/19.gif");
/*
		        Object iconObject = LookAndFeel.makeIcon(this.getClass(), "/images/19.gif");
		        UIManager.put("Tree.leafIcon", iconObject);
		        iconObject = LookAndFeel.makeIcon(this.getClass(), "/images/32.gif");
		        UIManager.put("Tree.openIcon", iconObject);
	*/	        
		        if(buttonPane != null){
		        	JPanel pn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		        	bStart = new JButton("New");
		        	bStart.setSize(20, 10);
		        	bStart.addActionListener(new java.awt.event.ActionListener() {
		                public void actionPerformed(java.awt.event.ActionEvent evt) {
		                	bStartActionPerformed(evt);
		                }
		            });
		        	pn.add(bStart);
		        	pn.setBackground(Color.BLACK);
		        	buttonPane.add(pn, BorderLayout.CENTER);
		        }

				top = new DefaultMutableTreeNode("My Agent");
				treeModel = new DefaultTreeModel(top);
				jTreeApps = new JTree(treeModel);
				//jTreeApps.setToggleClickCount(1);
			    jTreeApps.setSelectionPath(new TreePath(top.getPath()));
			    jTreeApps.setShowsRootHandles(true);
				jTreeApps.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
				
				jTreeApps.setCellRenderer(new MyRenderer());
				
				final JPopupMenu popMenu = new JPopupMenu();
				JMenuItem itemStart = new JMenuItem("Start");
				itemStart.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						mStartActionPerformed(e);
					}
					
				});
				popMenu.add(itemStart);
				
				jTreeApps.add(popMenu);
				jTreeApps.addMouseListener(new MouseAdapter() {
					private void popupHandler(MouseEvent evt){
						tree_path = jTreeApps.getPathForLocation(evt.getX(), evt.getY());
						if(tree_path.getPathCount()<3)
							return;
						jTreeApps.setSelectionPath(tree_path);
						popMenu.show(jTreeApps,evt.getX(), evt.getY());
						
					}
					
					public void mouseReleased(MouseEvent evt) {
						//System.out.println("jTreeApps.mouseExited, event="+evt);
						if(evt.isPopupTrigger()){
							popupHandler(evt);
						}
					}
					public void mousePressed(MouseEvent evt) {
						//System.out.println("jTreeApps.mouseEntered, event="+evt);
						//jTreeApps.getPathForLocation(evt.getX(), evt.getY());
						if(evt.isPopupTrigger()){
							popupHandler(evt);
						}
					}
				});
				jTreeApps.addTreeSelectionListener(this);
				
				jScrollPane1 = new JScrollPane(jTreeApps);;
				this.add(jScrollPane1, BorderLayout.CENTER);
				jScrollPane1.setPreferredSize(new java.awt.Dimension(131, 300));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private class AppInfo {
        public String nodeName=null;
        public int localPort=-1;
        public AgentAction action=null;

        public AppInfo(String nodeName, AgentAction action) {
        	this.nodeName = nodeName;
            this.action = action;
        }
        
        public AppInfo(int localPort, AgentAction action){
        	this.localPort = localPort;
        	this.action = action;
        }
        
        public String toString() {
        	if(nodeName!=null)
        		return nodeName;
        	else
        		return "tunnel:"+localPort;
        }
    }
    
    public DefaultMutableTreeNode findRootNode(String nodeName){
    	Enumeration e= top.children();
    	while (e.hasMoreElements()){
    		DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();
    		AppInfo nodeInfo = (AppInfo)node.getUserObject();
    		if(nodeName.equals(nodeInfo.nodeName))
    			return node;
    	}
    	return null;
    }
	public void addNode(String parentName, String nodeName, AgentAction action){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				new AppInfo(nodeName, action));
	    //top.add(node);
	    //treeModel.insertNodeInto(node, top, top.getChildCount());
		DefaultMutableTreeNode parentNode = findRootNode(parentName);
		if(parentNode!=null){
			treeModel.insertNodeInto(node, parentNode, parentNode.getChildCount());
			jTreeApps.scrollPathToVisible(new TreePath(node.getPath()));
		}
	    
	}
	public void addRoot(String nodeName){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				new AppInfo(nodeName, null));
	    //top.add(node);
	    treeModel.insertNodeInto(node, top, top.getChildCount());
		jTreeApps.scrollPathToVisible(new TreePath(node.getPath()));
	    
	}
	public void removeRoot(String nodeName){
		DefaultMutableTreeNode node = findRootNode(nodeName);
	    //top.add(node);
		treeModel.removeNodeFromParent(node);
		jTreeApps.repaint();
	}
	public void addLeaf(String localPort, AgentAction action){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				new AppInfo(Integer.parseInt(localPort), action));
	    //top.add(node);
		
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)
				jTreeApps.getLastSelectedPathComponent();
		
	    treeModel.insertNodeInto(node, parentNode, parentNode.getChildCount());
	    
	    jTreeApps.scrollPathToVisible(new TreePath(node.getPath()));
	}
	
	public void valueChanged(TreeSelectionEvent e) {
/*		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		jTreeApps.getLastSelectedPathComponent();

		if (node == null) return;
		
		if(node.getParent()==null)
			bStart.setVisible(false);
		else
			bStart.setVisible(true);
		
		AppInfo appinfo = (AppInfo)node.getUserObject();
		
		if(appinfo.action != null)
			bStart.setText("New");
		else
			bStart.setText("Stop");*/
	}
	
	class MyRenderer extends DefaultTreeCellRenderer{
		public Component getTreeCellRendererComponent(
                JTree tree,Object value,boolean sel,
                boolean expanded, boolean leaf, int row, boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel,
                expanded, leaf, row, hasFocus);
			
			TreePath path= tree.getPathForRow(row);
			if(path != null && path.getPathCount()==3)
				setIcon(new ImageIcon(appImage));
			if(path != null && path.getPathCount()==1)
				setIcon(new ImageIcon(rootImage));
			/*
			if (leaf && isTutorialBook(value)) {
			    setIcon(tutorialIcon);
			    setToolTipText("This book is in the Tutorial series.");
			} else {
			    setToolTipText(null); //no tool tip
			} 
			*/
			return this;
		}
	}
	
	public void bStartActionPerformed(java.awt.event.ActionEvent evt){
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)
					jTreeApps.getLastSelectedPathComponent();
		
		if (treeNode==null)
			return;
		
		AppInfo appinfo = (AppInfo)treeNode.getUserObject();
		
		if(appinfo.action != null)
			appinfo.action.actionPerformed();
		else{
			
		}
	}
	
	public void mStartActionPerformed(java.awt.event.ActionEvent evt){
		
		if (tree_path==null)
			return;
		
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)tree_path.getLastPathComponent();
		
		AppInfo appinfo = (AppInfo)treeNode.getUserObject();
		
		if(appinfo.action != null)
			appinfo.action.actionPerformed();
		else{
			
		}
	}
	/*
    public void setVisible(boolean visible) {
         if(visible && updateThread == null) {
            updateThread = new Thread() {
                public void run() {
                    while(updateThread != null) {
                        try {
                            Thread.sleep(1000);
                        }
                        catch(Exception e) {                            
                        }
                        try {
                            Method invokeAndWaitMethod = Class.forName("java.awt.EventQueue").getMethod("invokeAndWait", new Class[] { Runnable.class }); //$NON-NLS-1$ //$NON-NLS-2$
                            Runnable r = new Runnable() {
                                public void run() {
                                	Vector v = portMonitor.getModel();
                                	DefaultMutableTreeNode node;
                                	TreePath path;
                                	AppInfo info;
                                	int row=0;

                                	while(true){
                                		path = jTreeApps.getPathForRow(row);
                                		if (path == null) break;
                                		node = (DefaultMutableTreeNode) path.getLastPathComponent();
                                		if (!node.isLeaf())
                                			continue;
                                		
                                		info = (AppInfo)node.getUserObject();
                                		if(info.nodeName == null){
	                                		boolean in = false;
	                                		for(int i=0;i<v.size();i++){
	                                			AbstractPortItem item = (AbstractPortItem)v.get(i);
	                                			if (item.getLocalPort() == info.localPort){
	                                				in = true;
	                                				break;
	                                			}
	                                		}
	                                		if(!in){
	                                			//drop this node from tree
	                                			jTreeApps.removeSelectionRow(row);
	                                		}
                                		}
                                		row++;
                                	}
                                	
                                	
                                }
                            };
                            invokeAndWaitMethod.invoke(null, new Object[] { r });
                        }
                        catch(Exception e) {
                        	
                        }
                    }
                }
            };
            updateThread.start();
        }
        else if(!visible && updateThread != null){
            updateThread = null;
        }
    }
    */
}
