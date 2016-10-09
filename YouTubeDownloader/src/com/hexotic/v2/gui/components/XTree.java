package com.hexotic.v2.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.hexotic.utils.Settings;
import com.hexotic.v2.gui.theme.Theme;

public class XTree extends JTree{

	public XTree(){
		this.setCellRenderer(new XTreeRenderer());
		
		DefaultTreeModel startTree = new DefaultTreeModel(getRoot());
		this.setModel(startTree);
		
		this.setBorder(BorderFactory.createEmptyBorder());
		this.addTreeSelectionListener(new TreeSelectionListener(){
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				try{
					DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.getNewLeadSelectionPath().getLastPathComponent();
					addNodes(node, new File(getFilePathFromNode(node)));
				}catch(Exception ex){}
			}
		});
	}

	private String getFilePathFromNode(DefaultMutableTreeNode node){
		String path = "";
		if (Settings.getOS().contains("WIN")) {
			for(TreeNode t : node.getPath()){
				path = path + ((DefaultMutableTreeNode)t).toString()+"\\";
			}
		} else if (Settings.getOS().contains("MAC")) {
			boolean first = true;
			for(TreeNode t : node.getPath()){
				path = path + ((DefaultMutableTreeNode)t).toString()+"/";
			}
		}
		path = path.substring(1, path.length()-1);
		//  If mac or linux remove extra / in beginning of path
		if (!Settings.getOS().contains("WIN")) {
			if (path.length() > 1) {
				path = path.substring(1);
			}
		}
		return path;
	}
	
	public String getSelected(){
		return getFilePathFromNode((DefaultMutableTreeNode)this.getLastSelectedPathComponent()).replace("\\\\", "\\");
	}
	
	public DefaultMutableTreeNode getRoot(){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
		File[] roots = File.listRoots();
		for(int i=0;i<roots.length;i++)
			root.add(new DefaultMutableTreeNode(roots[i]));
		return root;
	}
	
	public  DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curDir, File dir) {
	    String curPath = dir.getAbsolutePath();
	    if(curDir == null)
	    	curDir = new DefaultMutableTreeNode(curPath);
	    if(dir.canRead())
	    for(File f : dir.listFiles()){
	    	if(f.isDirectory() && !f.isHidden()){
	    		curDir.add(new DefaultMutableTreeNode(f.getName()));
	    	}
	    }
	    return curDir;
	  }
}

class XTreeRenderer extends DefaultTreeCellRenderer {
	private Icon closedFolderIcon;
	private Icon openFolderIcon;
	private ClassLoader cldr = this.getClass().getClassLoader();
	public XTreeRenderer() {
		java.net.URL folder = cldr.getResource("images/folder.png");
		closedFolderIcon = new ImageIcon(folder);
		folder   = cldr.getResource("images/vfolder.png");
		openFolderIcon = new ImageIcon(folder);
		this.setTextSelectionColor(Theme.DARK_SHADOW);
		this.setTextNonSelectionColor(Theme.DARK);
		this.setBackgroundSelectionColor(Theme.MAIN_COLOR_FOUR);
		this.setBorderSelectionColor(Theme.MAIN_COLOR_THREE);
	}
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
		if (leaf) {
			setIcon(closedFolderIcon);
		} else {
			setIcon(openFolderIcon);
		}
		return this;
	}
	
}

