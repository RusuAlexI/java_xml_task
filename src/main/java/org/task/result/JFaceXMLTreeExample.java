package org.task.result;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class JFaceXMLTreeExample extends ApplicationWindow {

	public JFaceXMLTreeExample(Shell shell) {
		super(shell);
		setBlockOnOpen(true);
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("JFaceXMLTreeExample");
		shell.setSize(640, 480);
	}

	protected Control createContents(Composite composite) {

		CheckboxTreeViewer treeViewer = new CheckboxTreeViewer(composite);
		treeViewer.setContentProvider(createTreeContentProvider());
		treeViewer.setLabelProvider(createLabelProvider());
		treeViewer.setInput(createLazyTreeModelFrom("C:/Users/Sandu/task-workspace/result/resources/struct2.xml"));
		return composite;
	}

	private IBaseLabelProvider createLabelProvider() {
		return new LabelProvider() {
			public String getText(Object element) {
				org.w3c.dom.Node node = (org.w3c.dom.Node) element;
				if (null == node) {
					return super.getText(element);
				} else if (org.w3c.dom.Node.TEXT_NODE == node.getNodeType()) {
					return node.getTextContent().trim();
				} else {
					StringBuilder elementRepresentation = new StringBuilder();
					NamedNodeMap attributes = node.getAttributes();
					int attributeCount = attributes.getLength();
					for (int i = 0; i < attributeCount; i++) {
						org.w3c.dom.Node attributeNode = attributes.item(i);
						elementRepresentation.append(" ");
						elementRepresentation.append(attributeNode
								.getNodeValue());
					}
					return elementRepresentation.toString();
				}
			}
		};
	}

	private IContentProvider createTreeContentProvider() {
		return new ITreeContentProvider() {
			public Object[] getChildren(Object parentElement) {
				NodeList nodeList = ((org.w3c.dom.Node) parentElement).getChildNodes();
				int nodesCount = nodeList.getLength();
				List<org.w3c.dom.Node> nodes = new ArrayList<>();
				for (int i = 0; i < nodesCount; i++) {
					org.w3c.dom.Node currentNode = nodeList.item(i);
					if (null != currentNode
							&& org.w3c.dom.Node.TEXT_NODE == currentNode.getNodeType()
							&& "".equals(currentNode.getNodeValue().trim())) {
						System.out.println("Skip empty textnode:"
								+ currentNode.getNodeName());
						continue;
					}
					nodes.add(currentNode);

				}
				return nodes.toArray();
			}

			public Object getParent(Object element) {
				return ((org.w3c.dom.Node) element).getParentNode();
			}

			public boolean hasChildren(Object element) {
				return ((org.w3c.dom.Node) element).hasChildNodes();
			}

			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			public void dispose() {
				
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				
			}
		};
	}

	private Object createLazyTreeModelFrom(String xmlFilePath) {
		org.w3c.dom.Node documentNode = null;
		try {
			documentNode = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(xmlFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documentNode;
	}
}
