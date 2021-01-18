package mars.venus;

import mars.simulator.*;
import mars.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.io.*;
	

/**
 * Action class for setting the default working directory
 * @author Jakob
 */
public class settingsDefaultDirectoryAction extends GuiAction {
	
	JDialog defaultDirectoryDialog;
	JButton	defualtDirecotryButton;
	JTextField defaultDirectoryDisplay;
	JButton selectDirectoryButton;
	
	String initialDefaultDirectory;

	public settingsDefaultDirectoryAction(String name, Icon icon, String descrip, 
								Integer mnemonic, KeyStroke accel, VenusUI gui) {
		super(name, icon, descrip, mnemonic, accel, gui);
		initialDefaultDirectory = gui.getEditor().getDefaultOpenDirectory();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(initialDefaultDirectory);
		defaultDirectoryDialog = new JDialog(Globals.getGui(), "Default Working Directory", true);
		defaultDirectoryDialog.setContentPane(buildDialogPanel());
		defaultDirectoryDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		defaultDirectoryDialog.addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						closeDialog();
					}
				});
		defaultDirectoryDialog.pack();
		defaultDirectoryDialog.setLocationRelativeTo(Globals.getGui());
		defaultDirectoryDialog.setVisible(true);
	}
	
	private JPanel buildDialogPanel() {
		JPanel contents = new JPanel(new BorderLayout(20,20));
		contents.setBorder(new EmptyBorder(10,10,10,10));
		
		JPanel specifyDirectory = new JPanel();
		selectDirectoryButton = new JButton("Browse");
		selectDirectoryButton.setEnabled(true);
		selectDirectoryButton.addActionListener(new selectDirectoryAction());
		defaultDirectoryDisplay = new JTextField(initialDefaultDirectory);
		defaultDirectoryDisplay.setEditable(true);
		specifyDirectory.add(selectDirectoryButton);
		specifyDirectory.add(defaultDirectoryDisplay);
		contents.add(specifyDirectory);
		
      	// Bottom row - the control buttons for OK and Cancel
         Box controlPanel = Box.createHorizontalBox();
         JButton okButton = new JButton("OK");
         okButton.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     performOK();
                     closeDialog();
                  }
               });
         JButton cancelButton = new JButton("Cancel");
         cancelButton.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     closeDialog();
                  }
               });	
         controlPanel.add(Box.createHorizontalGlue());
         controlPanel.add(okButton);
         controlPanel.add(Box.createHorizontalGlue());
         controlPanel.add(cancelButton);
         controlPanel.add(Box.createHorizontalGlue());
         contents.add(controlPanel,BorderLayout.SOUTH);

		return contents;
	}
	
	protected void performOK() {
		// TODO Auto-generated method stub
		
	}

	private void closeDialog() {
		defaultDirectoryDialog.setVisible(false);
		defaultDirectoryDialog.dispose();
	}
	
	// Associated action class: selecting default working directory. Attached to directory selector
	private class selectDirectoryAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			String pathname = initialDefaultDirectory;
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(Globals.getGui());
			if (result == JFileChooser.APPROVE_OPTION) {
				pathname = chooser.getSelectedFile().getPath();
				defaultDirectoryDisplay.setText(pathname);
			}
		}
	}
}
