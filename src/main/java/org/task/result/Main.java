package org.task.result;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class Main {

	
	  static Display display = new Display();

	  static Shell shell = new Shell(display);

	  static final Tree tree = new Tree(shell, SWT.BORDER);

	  static Vector nodes = new Vector();

	  public static void traditional() {
	    for (int i = 0; nodes != null && i < nodes.size(); i++) {
	      Node node = (Node) nodes.elementAt(i);
	      addNode(null, node);
	    }
	  }

	  private static void addNode(TreeItem parentItem, Node node) {
	    TreeItem item = null;
	    if (parentItem == null)
	      item = new TreeItem(tree, SWT.NONE);
	    else
	      item = new TreeItem(parentItem, SWT.NONE);

	    item.setText(node.getName());

	    Vector subs = node.getSubCategories();
	    for (int i = 0; subs != null && i < subs.size(); i++)
	      addNode(item, (Node) subs.elementAt(i));
	  }
	  
	  
	  public static void main(String[] args) {
		  ParseFile parse = new ParseFile();
		  parse.read();
		  Node category = new Node("A", null);
		    nodes.add(category);

		    category = new Node("a1", category);
		    new Node("a11", category);
		    new Node("a12", category);

		    category = new Node("B", null);
		    nodes.add(category);

		    new Node("b1", category);
		    new Node("b2", category);

		    TreeViewer treeViewer = new TreeViewer(tree);

		    treeViewer.setContentProvider(new MyTreeContentProvider());

		    treeViewer.setLabelProvider(new MyLabelProvider());
		    treeViewer.setInput(nodes);
		    tree.setSize(300, 200);
		    shell.setSize(300, 200);

		    shell.open();

		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch()) {
		        display.sleep();
		      }
		    }
		    display.dispose();
	  }
	  
	}


class MyLabelProvider implements ILabelProvider {
	  public String getText(Object element) {
	    return ((Node) element).getName();
	  }

	  public Image getImage(Object arg0) {
	    return null;
	  }

	  public void addListener(ILabelProviderListener arg0) {
	  }

	  public void dispose() {
	  }

	  public boolean isLabelProperty(Object arg0, String arg1) {
	    return false;
	  }

	  public void removeListener(ILabelProviderListener arg0) {
	  }
	}


	class MyTreeContentProvider implements ITreeContentProvider{
	  public Object[] getChildren(Object parentElement) {
	    Vector subcats = ((Node) parentElement).getSubCategories();
	    return subcats == null ? new Object[0] : subcats.toArray();
	  }

	  public Object getParent(Object element) {
	    return ((Node) element).getParent();
	  }

	  public boolean hasChildren(Object element) {
	    return ((Node) element).getSubCategories() != null;
	  }

	  public Object[] getElements(Object inputElement) {
	    if (inputElement != null && inputElement instanceof Vector) {
	      return ((Vector) inputElement).toArray();
	    }
	    return new Object[0];
	  }

	  public void dispose() {
	  }

	  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	  }
	}


	class Node {
	  private String name;

	  private Vector subCategories;

	  private Node parent;

	  public Node(String name, Node parent) {
	    this.name = name;
	    this.parent = parent;
	    if (parent != null)
	      parent.addSubCategory(this);
	  }

	  public Vector getSubCategories() {
	    return subCategories;
	  }

	  private void addSubCategory(Node subcategory) {
	    if (subCategories == null)
	      subCategories = new Vector();
	    if (!subCategories.contains(subcategory))
	      subCategories.add(subcategory);
	  }

	  public String getName() {
	    return name;
	  }

	  public Node getParent() {
	    return parent;
	  }
	}

	