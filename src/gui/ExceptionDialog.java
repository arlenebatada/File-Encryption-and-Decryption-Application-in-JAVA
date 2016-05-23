
package gui;

/**
 *
 * @author Arlene
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
 
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
 

public class ExceptionDialog extends JDialog {
 
	private int dialogWidth = 500;
	private int dialogHeight = 140;
 
	private JLabel iconLabel = new JLabel();
 
	// is error panel opened up
	private boolean open = false;
 
	private JLabel errorLabel = new JLabel();
	private JTextArea errorTextArea = new JTextArea("");
 
	private JTextArea exceptionTextArea = new JTextArea("");
	private JScrollPane exceptionTextAreaSP = new JScrollPane();
 
	private JButton okButton = new JButton("OK");
	private JButton viewButton = new JButton("View Error");
	
	private JPanel topPanel = new JPanel(new BorderLayout());
 
	public ExceptionDialog(String errorLabelText, String errorDescription,
			Throwable e) {
 
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
 
		setSize(dialogWidth, dialogHeight);
 
		setResizable(true);
 
		errorTextArea.setText(errorDescription);
                errorTextArea.setEditable(false);
 
		errorLabel.setText(errorLabelText);
 
		exceptionTextArea.setText(errors.toString());
                exceptionTextArea.setEditable(false);
 
		exceptionTextAreaSP = new JScrollPane(exceptionTextArea);
 
		iconLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
 
		iconLabel.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
		setupUI();
 
		setUpListeners();
	}
 
	public ExceptionDialog(String errorLabelText, Throwable e) {
		this(errorLabelText, null, e);
	}
 
	public void setupUI() {
 
		this.setTitle("Error");
 
		errorTextArea.setLineWrap(true);
		errorTextArea.setWrapStyleWord(true);
		errorTextArea.setEditable(false);
 
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
 
		buttonPanel.add(okButton);
		buttonPanel.add(viewButton);
 
		errorTextArea.setBackground(iconLabel.getBackground());
 
		JScrollPane textAreaSP = new JScrollPane(errorTextArea);
 
		textAreaSP.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
 
		errorLabel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
 
		exceptionTextArea.setPreferredSize(new Dimension(100, 100));
 
		topPanel.add(iconLabel, BorderLayout.WEST);
 
		JPanel p = new JPanel(new BorderLayout());
		p.add(errorLabel, BorderLayout.NORTH);
		p.add(textAreaSP);
 
		topPanel.add(p);
 
		this.add(topPanel);
 
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
 
	private void setUpListeners() {
 
		okButton.addActionListener(new ActionListener() {
 
			@Override
			public void actionPerformed(ActionEvent e) {
				ExceptionDialog.this.setVisible(false);
			}
		});
 
		viewButton.addActionListener(new ActionListener() {
 
			@Override
			public void actionPerformed(ActionEvent e) {
 
				if (open) {
					viewButton.setText("View Error");
 
					topPanel.remove(exceptionTextAreaSP);
 
					ExceptionDialog.this.setSize(dialogWidth, dialogHeight);
 
					topPanel.revalidate();
 
					open = false;
 
				} else {
 
					viewButton.setText("Hide Error");
 
					topPanel.add(exceptionTextAreaSP, BorderLayout.SOUTH);
 
					ExceptionDialog.this.setSize(dialogWidth,
							dialogHeight + 100);
 
					topPanel.revalidate();
 
					open = true;
				}
			}
		});
 
	}
	
}