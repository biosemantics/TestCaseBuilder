package edu.arizona.sirls.testcasebuilder;


import java.io.FileNotFoundException;

import javax.swing.SwingUtilities;

import edu.arizona.sirls.testcasebuilder.models.*;
import edu.arizona.sirls.testcasebuilder.views.*;
import edu.arizona.sirls.testcasebuilder.controllers.*;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BuilderModel builderModel = new BuilderModel();
				BuilderView builderView;
				try {
					builderView = new BuilderView();
					BuilderController builderController = new BuilderController(builderModel, builderView);
					builderController.control();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

}
