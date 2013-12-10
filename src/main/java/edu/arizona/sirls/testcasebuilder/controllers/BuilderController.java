package edu.arizona.sirls.testcasebuilder.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import edu.arizona.sirls.gitClient.AddResult;
import edu.arizona.sirls.gitClient.GitClient;
import edu.arizona.sirls.testcasebuilder.models.*;
import edu.arizona.sirls.testcasebuilder.views.*;

public class BuilderController {

	private BuilderModel model;
	private BuilderView view;
	private ActionListener actionListener;

	public BuilderController(BuilderModel model, BuilderView view) {
		this.model = model;
		this.view = view;

	}

	public void control() {

		actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (actionEvent.getSource() == view.getBuildButton()) {

					System.out.println("Catch this event:: Build and Preview.");

					view.setOutput1TextArea("");
					view.setOutput2TextArea("");

					String output1 = "";
					String output2 = "";

					String selectedTestCaseItem = view
							.getSelectedTestCaseItem();
					String newTestTargetField = view.getNewTestTargetField();
					String sourceFileNameField = view.getSourceFileNameField();
					// System.out.println("sourceFileName" + sourceFileName);
					String correctMarkupTextArea = view
							.getCorrectMarkupTextArea();

					model.xmlParser(selectedTestCaseItem, newTestTargetField,
							sourceFileNameField, correctMarkupTextArea);

					output1 = model.getOutput1();
					output2 = model.getOutput2();

					view.setOutput1TextArea(output1);
					view.setOutput2TextArea(output2);

				}
				if (actionEvent.getSource() == view.getRefreshButton()) {
					System.out.println("Catch this event:: Clear above.");

					List<String> newTestTargetList = model.refresh();
					view.setTestCaseChooser(newTestTargetList);

					view.setNewTestTargetField("");
					view.setSourceFileNameField("");
					view.setCorrectMarkupTextArea("");

					view.setOutput1TextArea("");
					view.setOutput2TextArea("");

				}
				if (actionEvent.getSource() == view.getSaveGitHubInforButton()) {
					System.out
							.println("Catch this event:: Save GitHub Information.");

					String githubLoginFieldString, githubPasswordFieldString;

					String githubLoginField = view.getGithubLoginField();
					String githubPasswordField = view.getGithubPasswordField();
					String githubLoginInfor = "";
					String githubPasswordInfor = "";

					if (githubLoginField != null
							|| !githubLoginField.equals("")) {
						githubLoginFieldString = githubLoginField;
						githubLoginInfor = githubLoginFieldString;
					} else {
						githubLoginFieldString = "EmptyLogin";
					}
					if (githubPasswordField != null
							|| !githubPasswordField.equals("")) {
						githubPasswordFieldString = githubPasswordField;
						githubPasswordInfor = githubPasswordFieldString;
					} else {
						githubPasswordFieldString = "EmptyPassword";
					}

					if (githubLoginInfor.length() == 0
							|| githubPasswordInfor.length() == 0) {
						view.setGithubInforSaveMsgTextArea("GitHub information is not saved.");
					} else {
						view.setGithubInforSaveMsgTextArea("GitHub information is saved.");

						view.setGithubLoginField(githubLoginFieldString);
						view.setGithubPasswordField(githubPasswordFieldString);
					}
					model.setGithubLoginField(githubLoginFieldString);
					model.setGithubPasswordField(githubPasswordFieldString);

				}
				if (actionEvent.getSource() == view.getSubmitToGitHubButton()) {
					System.out.println("Catch this event:: Submit to GitHub.");
					String gitHubLoginname = model.getGitHubLoginName();
					//String gitHubPassword = model.getGitHubLoginPasswd();

					System.out.println("gitHubLoginname :" + gitHubLoginname);
					//System.out.println("gitHubPassword :" + gitHubPassword);

					String user = model.getGitHubLoginName();
					String password = model.getGitHubLoginPasswd();
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

					AddResult addResult;
					AddResult addResult2;
					AddResult addResult3;
					
					int commmitFileName = model.getMaximunNumOfSourceFile()+1;
					
					try {
						addResult = clientA.addFile("output1.xml",
								"source/"+commmitFileName+".xml", "master", "Submit " + "output1"
										+ " to source");
						System.out.println(addResult.toString());

						addResult2 = clientA.addFile("output2.xml",
								"targetoutcome/"+commmitFileName+".xml", "master", "Submit "
										+ "output2" + " to targetoutcome");
						System.out.println(addResult2.toString());

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					File file;
					try {
						file = clientA.getFile("testglossaryfixed.csv",
								"master");
						System.out.println(file.getAbsolutePath() + " "
								+ file.exists());

						List<String> oldtestglossaryfixedList = new ArrayList<String>();

						Scanner scanner = new Scanner(new File(
								file.getAbsolutePath()));// old
															// testglossaryfixed.csv
						while (scanner.hasNext()) {
							String line = scanner.nextLine();
							oldtestglossaryfixedList.add(line);
							// System.out.println(oldtestglossaryfixedList.toString());
						}
						scanner.close();

						Scanner scanner2 = new Scanner(new File(
								"testglossaryfixed-local.csv"));// new
																// testglossaryfixed.csv

						while (scanner2.hasNext()) {
							String line2 = scanner2.nextLine();
							// System.out.println(line2);
							boolean isNewLine = true;
							for (String lineInOldtestglossaryfixedList : oldtestglossaryfixedList) {
								if (line2
										.equals(lineInOldtestglossaryfixedList)) {
									isNewLine = false;
									// System.out.println("line2::" + line2 +
									// "::" + lineInOldtestglossaryfixedList +
									// "::lineInOldtestglossaryfixedList");
								}
							}
							if (isNewLine == true) {
								oldtestglossaryfixedList.add(line2);
								System.out.println("add::" + line2);
							}

						}
						scanner2.close();

						String newLine = "";
						for (String lineInOldtestglossaryfixedList : oldtestglossaryfixedList) {
							newLine += lineInOldtestglossaryfixedList + "\n";
						}

						FileWriter outFile = new FileWriter(
								"testglossaryfixed.csv");
						PrintWriter out = new PrintWriter(outFile);
						out.println(newLine);
						out.close();
						outFile.close();

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						addResult3 = clientA.addFile("testglossaryfixed.csv",
								"testglossaryfixed.csv", "master", "Submit "
										+ "testglossaryfixed.csv"
										+ " to testcases folder");
						System.out.println(addResult3.toString());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					/*
					 * GitClient clientB = new GitClient(repository, "localB",
					 * user, password, authorName, authorEmail, committerName,
					 * committerEmail); clientA.addFile("pom.xml", "pom.xml",
					 * Branch.master.toString(), "1");
					 * clientB.addFile("clientB.txt", "clientB.txt",
					 * Branch.development.toString(), "clientB");
					 * clientA.addFile(".project", "test/test/test/project",
					 * Branch.development.toString(), "3");
					 * clientB.addFile("clientB.txt", "clientB.txt",
					 * Branch.master.toString(), "clientB");
					 * clientA.addFile(".classpath",
					 * "test/test/test/test/classpath",
					 * Branch.development.toString(), "2");
					 * clientA.addFile(".classpath", "test/classpath",
					 * Branch.development.toString(), "2");
					 * 
					 * File file = clientA.getFile("test/test/test/project",
					 * Branch.development.toString());
					 * System.out.println(file.getAbsolutePath() + " " +
					 * file.exists());
					 * 
					 * clientA.addFile("C://test//fnav19_posedsentences.txt",
					 * "fnav19_posedsentences.txt", Branch.master.toString(),
					 * "4");
					 * 
					 * file = clientA.getFile("pom.xml",
					 * Branch.master.toString());
					 * System.out.println(file.getAbsolutePath() + " " +
					 * file.exists());
					 * 
					 * file = clientA.getFile("test/classpath",
					 * Branch.development.toString());
					 * System.out.println(file.getAbsolutePath() + " " +
					 * file.exists());
					 * 
					 * file = clientA.getFile("fnav19_posedsentences.txt",
					 * Branch.master.toString());
					 * System.out.println(file.getAbsolutePath() + " " +
					 * file.exists());
					 * 
					 * file = clientA.getFile("clientB.txt",
					 * Branch.master.toString());
					 * System.out.println(file.getAbsolutePath() + " " +
					 * file.exists());
					 * 
					 * file = clientA.getFile("clientB.txt",
					 * Branch.development.toString());
					 * System.out.println(file.getAbsolutePath() + " " +
					 * file.exists());
					 */

				}
				// sampleAction();

			}
		};
		view.getBuildButton().addActionListener(actionListener);
		view.getRefreshButton().addActionListener(actionListener);
		view.getSubmitToGitHubButton().addActionListener(actionListener);
		view.getSaveGitHubInforButton().addActionListener(actionListener);

	}
	// The following is the example MVC structure
	// private void sampelAction() {
	// model.data_manipulation();
	// view.setSampleText(Integer.toString(model.getData()));
	// }
}
