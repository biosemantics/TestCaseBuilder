package edu.arizona.sirls.testcasebuilder.views;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BuilderView {

	private JFrame frame;

	private String githubInforSaveMsg, githubLoginInfor, githubPasswordInfor;

	private JPanel textPanel, panelForInputFields, panelForGitHub;
	private JLabel titleLabel, testTargetLabel, sourceFileLabel,
			correctMarkupLabel, output1Label, output2Label, githubLoginLabel,
			githubPasswordLabel;
	private JTextField sourceFileNameField, newTestTargetField,
			githubLoginField;
	private JButton buildButton, refreshButton, submitToGitHubButton,
			saveGitHubInforButton;
	private JComboBox testCaseChooser;
	private JTextArea correctMarkupTextArea, output1TextArea, output2TextArea,
			githubInforSaveMsgTextArea;
	private JPanel totalGUI;
	private JPasswordField githubPasswordField;

	public BuilderView() throws FileNotFoundException {
		frame = new JFrame("Test Case Builder");

		frame.setContentPane(createContentPane());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);

	}

	public JPanel createContentPane() throws FileNotFoundException {

		totalGUI = new JPanel();

		totalGUI.setLayout(null);

		titleLabel = new JLabel("Create a new Test Case");
		titleLabel.setLocation(0, 0);
		titleLabel.setSize(300, 30);
		titleLabel.setHorizontalAlignment(0);
		totalGUI.add(titleLabel);

		output1Label = new JLabel("Output 1");
		output1Label.setLocation(0, 200);
		output1Label.setSize(300, 300);
		output1Label.setHorizontalAlignment(0);
		totalGUI.add(output1Label);

		output1TextArea = new JTextArea(30, 50);
		output1TextArea.setLocation(50, 370);
		output1TextArea.setSize(300, 200);
		output1TextArea.setEditable(true);
		output1TextArea.setLineWrap(true);
		output1TextArea.setWrapStyleWord(true);
		totalGUI.add(new JScrollPane(output1TextArea));
		//totalGUI.add(output1TextArea);

		output2Label = new JLabel("Output 2");
		output2Label.setLocation(300, 200);
		output2Label.setSize(300, 300);
		output2Label.setHorizontalAlignment(0);
		totalGUI.add(new JScrollPane(output2TextArea));
		//totalGUI.add(output2Label);

		output2TextArea = new JTextArea(30, 50);
		output2TextArea.setLocation(370, 370);
		output2TextArea.setSize(300, 200);
		output2TextArea.setEditable(true);
		output2TextArea.setLineWrap(true);
		output2TextArea.setWrapStyleWord(true);
		totalGUI.add(output2TextArea);

		// Creation of a Panel to contain the JLabels
		textPanel = new JPanel();
		textPanel.setLayout(null);
		textPanel.setLocation(10, 35);
		textPanel.setSize(200, 250);
		totalGUI.add(textPanel);

		// Test Target Label
		testTargetLabel = new JLabel("Test Target");
		testTargetLabel.setLocation(0, 0);
		testTargetLabel.setSize(150, 20);
		testTargetLabel.setHorizontalAlignment(4);
		textPanel.add(testTargetLabel);

		// Source File Label
		sourceFileLabel = new JLabel("Source File Name");
		sourceFileLabel.setLocation(0, 65);
		sourceFileLabel.setSize(150, 20);
		sourceFileLabel.setHorizontalAlignment(4);
		textPanel.add(sourceFileLabel);

		// Correct Markup Label
		correctMarkupLabel = new JLabel("Correct markup");
		correctMarkupLabel.setLocation(0, 120);
		correctMarkupLabel.setSize(150, 20);
		correctMarkupLabel.setHorizontalAlignment(4);
		textPanel.add(correctMarkupLabel);

		// InputFields Panel Container
		panelForInputFields = new JPanel();

		// panelForInputFields.setLayout(new BoxLayout(panelForInputFields,
		// BoxLayout.LINE_AXIS));
		panelForInputFields.setLayout(new BoxLayout(panelForInputFields,
				BoxLayout.Y_AXIS));
		// panelForInputFields.setLayout(new BoxLayout(panelForInputFields,
		// BoxLayout.PAGE_AXIS));
		// panelForInputFields.setLayout(null);

		panelForInputFields.setLocation(230, 30);
		panelForInputFields.setSize(300, 250);
		totalGUI.add(panelForInputFields);

		List<String> testTargetList = new ArrayList<String>();
		// testTargetList.add("NEW");
		Scanner scanner = new Scanner(new File("TestTargetList.csv"));
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			System.out.println("line : " + line);
			testTargetList.add(line);
		}
		scanner.close();

		System.out.println(testTargetList.toString());

		testCaseChooser = new JComboBox(testTargetList.toArray());
		testCaseChooser.setLocation(0, 0);
		testCaseChooser.setSize(100, 10);
		//testCaseChooser.setSelectedIndex(0);
		// testCaseChooser.addActionListener(this);
		panelForInputFields.add(testCaseChooser);

		panelForInputFields.add(Box.createRigidArea(new Dimension(100, 0)));

		JLabel orLabel = new JLabel("OR type new");
		// titleLabel.setLocation(0, 0);
		// titleLabel.setSize(290, 30);
		// titleLabel.setHorizontalAlignment(0);
		panelForInputFields.add(orLabel);

		// New test target Textfield
		newTestTargetField = new JTextField(100);
		// newTestTargetField.setLocation(0, 40);
		newTestTargetField.setSize(100, 10);
		panelForInputFields.add(newTestTargetField);

		panelForInputFields.add(Box.createRigidArea(new Dimension(100, 0)));

		// Source File Name Textfield
		sourceFileNameField = new JTextField(100);
		// sourceFileNameField.setLocation(0, 40);
		sourceFileNameField.setSize(100, 10);
		panelForInputFields.add(sourceFileNameField);

		panelForInputFields.add(Box.createRigidArea(new Dimension(100, 15)));

		// Correcct Markup TextArea
		correctMarkupTextArea = new JTextArea(50, 100);
		// correctMarkupTextArea.setLocation(0, 90);
		// correctMarkupTextArea.setSize(100, 120);
		correctMarkupTextArea.setEditable(true);
		correctMarkupTextArea.setLineWrap(true);
		correctMarkupTextArea.setWrapStyleWord(true);
		panelForInputFields.add(new JScrollPane(correctMarkupTextArea));

		// GitHub Panel Container
		panelForGitHub = new JPanel();
		panelForGitHub.setLayout(null);
		panelForGitHub.setLocation(550, 30);
		panelForGitHub.setSize(150, 250);
		totalGUI.add(panelForGitHub);

		githubLoginLabel = new JLabel("GitHub Login");
		githubLoginLabel.setLocation(0, 0);
		githubLoginLabel.setSize(150, 30);
		githubLoginLabel.setHorizontalAlignment(0);
		panelForGitHub.add(githubLoginLabel);

		// GitHub Login Textfield
		githubLoginField = new JTextField(100);
		githubLoginField.setLocation(0, 30);
		githubLoginField.setSize(150, 30);
		panelForGitHub.add(githubLoginField);

		githubPasswordLabel = new JLabel("GitHub Password");
		githubPasswordLabel.setLocation(0, 60);
		githubPasswordLabel.setSize(150, 30);
		githubPasswordLabel.setHorizontalAlignment(0);
		panelForGitHub.add(githubPasswordLabel);

		// GitHub Password Passwordfield
		githubPasswordField = new JPasswordField(50);
		githubPasswordField.setEchoChar('*');
		githubPasswordField.setLocation(0, 90);
		githubPasswordField.setSize(150, 30);
		panelForGitHub.add(githubPasswordField);

		// Button for Save GitHub Information
		saveGitHubInforButton = new JButton("Save GitHub Infor");
		saveGitHubInforButton.setLocation(0, 120);
		saveGitHubInforButton.setSize(150, 30);
		// saveGitHubInforButton.addActionListener(this);
		panelForGitHub.add(saveGitHubInforButton);

		githubInforSaveMsg = "GitHub information is not saved.";
		githubInforSaveMsgTextArea = new JTextArea(150, 100);
		githubInforSaveMsgTextArea.setText(githubInforSaveMsg);
		githubInforSaveMsgTextArea.setLocation(0, 150);
		githubInforSaveMsgTextArea.setSize(150, 120);
		githubInforSaveMsgTextArea.setLineWrap(true);
		githubInforSaveMsgTextArea.setWrapStyleWord(true);
		// panelForGitHub.add(new JScrollPane(githubInforSaveMsgTextArea));
		panelForGitHub.add(githubInforSaveMsgTextArea);

		// Button for Build
		buildButton = new JButton("Build and preview");
		buildButton.setLocation(50, 300);
		buildButton.setSize(150, 30);
		// buildButton.addActionListener(this);
		totalGUI.add(buildButton);

		// Button for Refresh
		refreshButton = new JButton("Clear above");
		refreshButton.setLocation(200, 300);
		refreshButton.setSize(150, 30);
		// refreshButton.addActionListener(this);
		totalGUI.add(refreshButton);

		// Button for Submit to Github
		submitToGitHubButton = new JButton("Submit to GitHub");
		submitToGitHubButton.setLocation(350, 300);
		submitToGitHubButton.setSize(150, 30);
		// submitToGitHubButton.addActionListener(this);
		totalGUI.add(submitToGitHubButton);

		totalGUI.setOpaque(true);
		return totalGUI;
	}

	public String getSelectedTestCaseItem() {
		String selectedTestCaseItem = (String) testCaseChooser
				.getSelectedItem();
		return selectedTestCaseItem;
	}

	public String getNewTestTargetField() {
		return newTestTargetField.getText();
	}

	public String getSourceFileNameField() {
		return sourceFileNameField.getText();
	}

	public String getCorrectMarkupTextArea() {
		return correctMarkupTextArea.getText();
	}

	public String getGithubLoginField() {
		return githubLoginField.getText();
	}

	public String getGithubPasswordField() {
		return String.valueOf(githubPasswordField.getPassword());
	}

	public JButton getBuildButton() {
		return buildButton;
	}

	public JButton getRefreshButton() {
		return refreshButton;
	}

	public JButton getSubmitToGitHubButton() {
		return submitToGitHubButton;
	}

	public JButton getSaveGitHubInforButton() {
		return saveGitHubInforButton;
	}

	public void setTestCaseChooser(List<String> newTestTargetList) {

		testCaseChooser.removeAllItems();

		for (int i = 0; i < newTestTargetList.size(); i++) {
			testCaseChooser.addItem(newTestTargetList.get(i).toString());
			;
		}

		testCaseChooser.setSelectedIndex(0);
		testCaseChooser.repaint();// no use or no effect ?

	}

	public void setNewTestTargetField(String outputText) {
		newTestTargetField.setText(outputText);
	}

	public void setSourceFileNameField(String outputText) {
		sourceFileNameField.setText(outputText);
	}

	public void setCorrectMarkupTextArea(String outputText) {
		correctMarkupTextArea.setText(outputText);
	}

	public void setOutput1TextArea(String outputText) {
		output1TextArea.setText(outputText);
	}

	public void setOutput2TextArea(String outputText) {
		output2TextArea.setText(outputText);
	}

	public void setGithubLoginField(String outputText) {
		githubLoginField.setText(outputText);
	}

	public void setGithubPasswordField(String outputText) {
		githubPasswordField.setText(outputText);
	}

	public void setGithubInforSaveMsgTextArea(String outputText) {
		githubInforSaveMsgTextArea.setText(outputText);
	}

	public void noGitHubLoginOrPasswdWhenBuildPopUpDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"Need GitHub loginname and password beferore build and preview!",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
						//http://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
	}

	public void noGitHubLoginOrPasswdWhenCommitPopUpDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"Need GitHub loginname and password beferore committing files!",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
	}
	
	public void noSourceFileNameFieldPopUpDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"Need Source File Name for build and preview!",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
	}	

	public void noCorrectMarkupTextAreaPopUpDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"Need Correct Markup for build and preview!",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
	}
	
	public void noBuildAndPreviewWhenCommitPopUpDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"Need \"Build and preview\" beferore committing files!",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
	}	
	
	public void commitSuccessPopUpDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"Files have been committed successfully!",
						"Info",
						JOptionPane.INFORMATION_MESSAGE);
	}	
	
}
