package edu.arizona.sirls.testcasebuilder.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import edu.arizona.sirls.gitClient.GitClient;

public class BuilderModel {

	private String output1;
	private String output2;
	private String term_category_pair="";

	private String githubLoginName;
	private String gitHubLoginPasswd;
	
	private int maximunNumOfSourceFile = 0;
	
	public BuilderModel() {

	}

	public void xmlParser(String selectedTestCaseItem,
			String newTestTargetField, String sourceFileNameField,
			String correctMarkupTextArea) {

		
		
		// Deprecated
		// December 10, 2013 Tuesday add
		// Read Directory "local" and its subfolder "source" and "targetoutcome"
		/*
		 * 
		String sourceFolderName = "local/source";
		File sourceFolder = new File(sourceFolderName);
		
		
		if (sourceFolder.exists()) {
			File[] listOfSourceFiles = sourceFolder.listFiles();
			for (File sourceFile : listOfSourceFiles) {
				if (sourceFile.isFile()) {
					String sourceFileName = sourceFile.getName();
					if (!sourceFileName.equals(".DS_Store")) {
						String[] sourceFileNameArray = sourceFileName.split("\\.");
						// System.out.println("sourceFileName number:" +
						// sourceFileNameArray[0]);
						int localMaximunNumOfSourceFile = Integer
								.parseInt(sourceFileNameArray[0]);
						if (maximunNumOfSourceFile < localMaximunNumOfSourceFile) {
							maximunNumOfSourceFile = localMaximunNumOfSourceFile;
						}

					}
				}
			}			
		}
		 */
		// Deprecated

		// December 10, 2013 Tuesday add
		// Read GitHub Directory and its subfolder "source"		
		String user = githubLoginName;
		String password = gitHubLoginPasswd;
		String authorName = "Test Case Builder";
		String authorEmail = "testcasebuilder@gmail.com";
		String committerName = "committerName";
		String committerEmail = "committerEmail@gmail.com";
		String repository = "https://github.com/biosemantics/testcases.git";

		List<String> branches = new LinkedList<String>();
		branches.add("master");
		// branches.add("development");

		GitClient clientA = new GitClient(repository, branches,
				"local", user, password, authorName, authorEmail,
				committerName, committerEmail);
		try {
			List<File> sourceFileList = clientA.getFiles("source", "master");
			for (File fileInSourceFileList : sourceFileList) {
				String fileName = fileInSourceFileList.getName();
				System.out.println(fileName);

				if (fileInSourceFileList.isFile()) {
					String sourceFileName = fileInSourceFileList.getName();
					if (!sourceFileName.equals(".DS_Store")) {
						String[] sourceFileNameArray = sourceFileName.split("\\.");
						// System.out.println("sourceFileName number:" +
						// sourceFileNameArray[0]);
						int localMaximunNumOfSourceFile = Integer
								.parseInt(sourceFileNameArray[0]);
						if (maximunNumOfSourceFile < localMaximunNumOfSourceFile) {
							maximunNumOfSourceFile = localMaximunNumOfSourceFile;
						}

					}
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		if (newTestTargetField != null && !newTestTargetField.equals("")) {
			selectedTestCaseItem = newTestTargetField;

			// update TestTargetList.csv
			String newLine = "";
			Scanner scanner;
			try {
				scanner = new Scanner(new File("TestTargetList.csv"));

				boolean isNewLine = true;
				while (scanner.hasNext()) {
					String line = scanner.nextLine();
					newLine += line + "\n";
					if (line.equals(selectedTestCaseItem)) {
						isNewLine = false;
					}
				}
				scanner.close();
				if (isNewLine == true) {
					newLine += selectedTestCaseItem + "\n";
				}

				FileWriter outFile = new FileWriter("TestTargetList.csv");
				PrintWriter out = new PrintWriter(outFile);
				out.println(newLine);
				out.close();
				outFile.close();

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		}

		PrintWriter writer;
		try {
			writer = new PrintWriter("source.xml", "UTF-8");
			writer.println(correctMarkupTextArea);
			writer.close();
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File("source.xml");
			try {

				Document xmlDocument = (Document) builder.build(xmlFile);

				Element rootNode = xmlDocument.getRootElement();

				System.out.println(rootNode.getName());
				System.out.println(rootNode.getAttributeValue("id"));

				String text = rootNode.getChildText("text");
				List structureList = rootNode.getChildren("structure");

				List relationList = rootNode.getChildren("relation");
				System.out.println("relationList:" + relationList.toString());

				xmlDocument.getRootElement().setAttribute("id", String.valueOf((maximunNumOfSourceFile+1)) + ".txt");
				// update statement id

				int structureBaseNumeber = 1;

				for (int i = 0; i < structureList.size(); i++) {

					Element structureNode = (Element) structureList.get(i);
					if (i == 0) {
						structureBaseNumeber = (Integer.parseInt(structureNode
								.getAttributeValue("id").replaceAll("o", "")) - 1);
					}
					structureNode.setAttribute("id",
							"o" + String.valueOf(i + 1));

					System.out.println(structureNode.getName());
					term_category_pair += structureNode
							.getAttributeValue("name_original")
							+ ","
							+ "structure\n";
					term_category_pair += structureNode
							.getAttributeValue("name") + "," + "structure\n";

					List characterList = structureNode.getChildren("character");
					for (int j = 0; j < characterList.size(); j++) {
						Element characterNode = (Element) characterList.get(j);
						// character name and character value
						if (characterNode.getAttributeValue("value") != null) {
							String name = characterNode
									.getAttributeValue("name");
							String value = characterNode
									.getAttributeValue("value");
							System.out.println("character name : " + name);
							System.out.println("character value : " + value);
							term_category_pair += value + "," + name + "\n";
						}

					}

				}
				for (int i = 0; i < relationList.size(); i++) {
					Element relationNode = (Element) relationList.get(i);
					System.out.println("relationNode:id:"
							+ relationNode.getAttributeValue("id"));
					relationNode
							.setAttribute("id", "r" + String.valueOf(i + 1));
					int relationFrom = Integer.parseInt(relationNode
							.getAttributeValue("from").replaceAll("o", ""))
							- structureBaseNumeber;
					int relationTo = Integer.parseInt(relationNode
							.getAttributeValue("to").replaceAll("o", ""))
							- structureBaseNumeber;
					relationNode.setAttribute("from", "o" + relationFrom);
					relationNode.setAttribute("to", "o" + relationTo);

				}
				System.out.println("structureBaseNumeber:"
						+ structureBaseNumeber);

				Document document = new Document();
				Element root = new Element("treatment");
				Element treatment_meta = new Element("meta");
				treatment_meta.addContent(new Element("source")
						.addContent(sourceFileNameField));
				// treatment_meta.addContent(new
				// Element("source").setText(sourceFileNameField.getText()));
				treatment_meta.addContent(new Element("test_target")
						.addContent(selectedTestCaseItem));
				// treatment_meta.setAttribute(new Attribute("id", "1"));
				root.addContent(treatment_meta);
				root.addContent(new Element("taxon_identification"));
				root.addContent(new Element("description").addContent(text
						.toString()));
				document.addContent(root);

				Document document2 = new Document();
				Element root2 = new Element("treatment");
				Element treatment_meta2 = new Element("meta");
				treatment_meta2.addContent(new Element("source")
						.addContent(sourceFileNameField));
				// treatment_meta2.addContent(new
				// Element("source").setText(sourceFileNameField.getText()));
				treatment_meta2.addContent(new Element("test_target")
						.addContent(selectedTestCaseItem));
				// treatment_meta2.setAttribute(new Attribute("id", "1"));
				root2.addContent(treatment_meta2);
				root2.addContent(new Element("taxon_identification"));

				Element description2 = new Element("description");

				List xmlDocumentContent = xmlDocument.cloneContent();

				// http://www.coderanch.com/t/129233/XML/JDOM-attach-Content-Element

				description2.addContent(xmlDocumentContent);
				root2.addContent(description2);

				document2.addContent(root2);

				try {
					String encoding = "UTF-8";

					FileWriter xmlWriter = new FileWriter(new File(
							"output1.xml"));
					XMLOutputter outputter = new XMLOutputter();

					// Set the XLMOutputter to pretty formatter. This
					// formatter
					// use the TextMode.TRIM, which mean it will remove the
					// trailing white-spaces of both side (left and right)

					outputter.setFormat(Format.getPrettyFormat().setEncoding(
							encoding));

					// Write the document to a file and also display it on
					// the
					// screen through System.out.

					outputter.output(document, xmlWriter);
					outputter.output(document, System.out);
					xmlWriter.close();

					FileWriter xmlWriter2 = new FileWriter(new File(
							"output2.xml"));
					XMLOutputter outputter2 = new XMLOutputter();

					outputter2.setFormat(Format.getPrettyFormat().setEncoding(
							encoding));

					outputter.output(document2, xmlWriter2);
					outputter.output(document2, System.out);

					xmlWriter2.close();

				} catch (IOException ioe) {
					ioe.printStackTrace();
				}

				System.out.println("term_category_pair:" + term_category_pair);

				System.out.println();
				FileWriter testglossaryfixedOutFile = new FileWriter(
						"testglossaryfixed-local.csv");
				PrintWriter testglossaryfixedOut = new PrintWriter(
						testglossaryfixedOutFile);
				testglossaryfixedOut.println(term_category_pair);
				testglossaryfixedOut.close();
				testglossaryfixedOutFile.close();

				output1 = new XMLOutputter().outputString(document);
				output2 = new XMLOutputter().outputString(document2);
				// http://stackoverflow.com/questions/4343683/how-to-convert-org-jdom-document-to-string

				// output3TextArea.append(text);

			} catch (IOException io) {
				System.out.println(io.getMessage());
			} catch (JDOMException jdomex) {
				System.out.println(jdomex.getMessage());
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public List<String> refresh() {
		List<String> testTargetList = new ArrayList<String>();
		// testTargetList.add("NEW");
		Scanner scanner;
		try {
			scanner = new Scanner(new File("TestTargetList.csv"));
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				testTargetList.add(line);
			}
			scanner.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return testTargetList;
	}

	public void setGithubLoginField(String githubLoginFieldString) {
		githubLoginName = githubLoginFieldString;
	}

	public void setGithubPasswordField(String githubPasswordFieldString) {
		gitHubLoginPasswd = githubPasswordFieldString;
	}

	public String getOutput1() {
		return output1;
	}

	public String getOutput2() {
		return output2;
	}

	public String getGitHubLoginName() {
		return githubLoginName;
	}

	public String getGitHubLoginPasswd() {
		return gitHubLoginPasswd;
	}

	public int getMaximunNumOfSourceFile() {
		return maximunNumOfSourceFile;
	}

}