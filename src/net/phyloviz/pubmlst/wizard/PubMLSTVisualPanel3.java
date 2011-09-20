package net.phyloviz.pubmlst.wizard;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import net.phyloviz.pubmlst.soap.PubmlstSOAP;
import org.openide.util.NbPreferences;

public final class PubMLSTVisualPanel3 extends JPanel {

	private int iMaxIsolate;
	private Task task;
	private String sIsolateData;
	private boolean bEmpty;
	private String sPubMLSTDB;
	private DefaultComboBoxModel keyListModel;
	PubmlstSOAP soapClient;

	/** Creates new form PubMLSTVisualPanel2 */
	public PubMLSTVisualPanel3() {
		keyListModel = new DefaultComboBoxModel();
		initComponents();

		setEditorPanel("PubMLSTVisualPanel3a.html");
	}
	
	private void setEditorPanel(String sFile) {
		try {
			URL url = PubMLSTVisualPanel3.class.getResource(sFile);
			jEditorPane1.setEditorKit(new HTMLEditorKit());
			jEditorPane1.setPage(url);
			Font font = UIManager.getFont("Label.font");
			String bodyRule = "body { font-family: " + font.getFamily() + "; "
					+ "font-size: " + font.getSize() + "pt; width: " + jEditorPane1.getSize().width + "px;}";
			((HTMLDocument) jEditorPane1.getDocument()).getStyleSheet().addRule(bodyRule);
		} catch (IOException e) {
			// Do nothing...
			System.err.println(e.getMessage());
		}
	}

	public void setDatabase(String sDBShort) {
		// PubMLST initialization
		sPubMLSTDB = sDBShort;
		bEmpty = true;

		// To prevent bad UI experience when comming back to this UI
		// Pubmlst.org loader
		jRadioButton1.setSelected(true);
		jProgressBar1.setString(null);
		jProgressBar1.setValue(0);
		jToggleButton1.setSelected(false);
		jToggleButton1.setEnabled(false);
		// File selecter
		jTextField1.setEnabled(false);
		jButton1.setEnabled(false);
		// Isolate Key List
		jComboBox1.setEnabled(false);
		jButton2.setEnabled(false);
		updateKeyList(null);
	}

	@Override
	public String getName() {
		return "Isolate Data";
	}

	public StringReader getIsolateData() {
		String sTmp = "";
		for (int i = 1; i < keyListModel.getSize(); i++) {
			sTmp += "\t" + keyListModel.getElementAt(i);
		}
		sTmp = keyListModel.getElementAt(0) + sTmp + sIsolateData;
		return new StringReader(sTmp);
	}

	public int getKeyIndex() {
		return jComboBox1.getSelectedIndex();
	}

	public String getFilename() {
		return jTextField1.getText();
	}

	public boolean isEmpty() {
		return bEmpty;
	}

	public boolean hasPubMLSTData() {
		return jRadioButton2.isSelected();
	}

	public boolean hasFileData() {
		return jRadioButton3.isSelected();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jToggleButton1 = new javax.swing.JToggleButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jEditorPane1 = new javax.swing.JEditorPane();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridLayout(7, 0));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton1, org.openide.util.NbBundle.getMessage(PubMLSTVisualPanel3.class, "PubMLSTVisualPanel3.jRadioButton1.text_1")); // NOI18N
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButton1);

        buttonGroup1.add(jRadioButton2);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton2, org.openide.util.NbBundle.getMessage(PubMLSTVisualPanel3.class, "PubMLSTVisualPanel3.jRadioButton2.text_1")); // NOI18N
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButton2);

        jPanel1.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PubMLSTVisualPanel3.class, "PubMLSTVisualPanel3.jLabel1.text")); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 12, 2, 8));
        jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);

        jProgressBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 12, 2, 8));
        jProgressBar1.setEnabled(false);
        jProgressBar1.setStringPainted(true);
        jPanel1.add(jProgressBar1, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(jToggleButton1, org.openide.util.NbBundle.getMessage(PubMLSTVisualPanel3.class, "PubMLSTVisualPanel3.jToggleButton1.text_1")); // NOI18N
        jToggleButton1.setEnabled(false);
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton1, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel1);

        buttonGroup1.add(jRadioButton3);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton3, org.openide.util.NbBundle.getMessage(PubMLSTVisualPanel3.class, "PubMLSTVisualPanel3.jRadioButton3.text_1")); // NOI18N
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jRadioButton3);

        jPanel3.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(PubMLSTVisualPanel3.class, "PubMLSTVisualPanel3.jLabel2.text")); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 12, 2, 8));
        jPanel3.add(jLabel2, java.awt.BorderLayout.WEST);

        jTextField1.setText(org.openide.util.NbBundle.getMessage(PubMLSTVisualPanel3.class, "PubMLSTVisualPanel3.jTextField1.text")); // NOI18N
        jTextField1.setEnabled(false);
        jPanel3.add(jTextField1, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(PubMLSTVisualPanel3.class, "PubMLSTVisualPanel3.jButton1.text")); // NOI18N
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel3);
        jPanel2.add(jSeparator1);

        jPanel4.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(PubMLSTVisualPanel3.class, "PubMLSTVisualPanel3.jLabel3.text")); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 12, 2, 8));
        jPanel4.add(jLabel3, java.awt.BorderLayout.WEST);

        jComboBox1.setModel(keyListModel);
        jComboBox1.setEnabled(false);
        jPanel4.add(jComboBox1, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(PubMLSTVisualPanel3.class, "PubMLSTVisualPanel3.jButton2.text")); // NOI18N
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel4);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jEditorPane1.setBackground(jPanel2.getBackground());
        jEditorPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        jEditorPane1.setEditable(false);
        jEditorPane1.setMaximumSize(new java.awt.Dimension(200, 200));
        add(jEditorPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

	private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
		// Disable pubmlst.org loader
		jToggleButton1.setEnabled(false);
		jProgressBar1.setEnabled(false);
		// Disable file loader
		jTextField1.setEnabled(false);
		jButton1.setEnabled(false);
		// Disable key chooser
		jComboBox1.setEnabled(false);
		jButton2.setEnabled(false);
		setEditorPanel("PubMLSTVisualPanel3a.html");
	}//GEN-LAST:event_jRadioButton1ActionPerformed

	private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
		// Enable pubmlst.org loader
		jToggleButton1.setEnabled(true);
		jProgressBar1.setEnabled(true);
		// Disable file loader
		jTextField1.setEnabled(false);
		jButton1.setEnabled(false);
		// Enable key chooser
		jComboBox1.setEnabled(true);
		jButton2.setEnabled(true);
		setEditorPanel("PubMLSTVisualPanel3b.html");
	}//GEN-LAST:event_jRadioButton2ActionPerformed

	private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
		// Disable pubmlst.org loader
		jToggleButton1.setEnabled(false);
		jProgressBar1.setEnabled(false);
		// Enable file loader
		jTextField1.setEnabled(true);
		jButton1.setEnabled(true);
		// Enable key chooser
		jComboBox1.setEnabled(true);
		jButton2.setEnabled(true);
		setEditorPanel("PubMLSTVisualPanel3c.html");
	}//GEN-LAST:event_jRadioButton3ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
		String[] result = null;
		if (jRadioButton2.isSelected()) {
			result = soapClient.getIsolateFields(sPubMLSTDB);
		} else {
			try {
				// Get headers...
				result = new BufferedReader(new FileReader(new File(jTextField1.getText()))).readLine().split("\t", -1);
			} catch (FileNotFoundException ex) {
				// If there is any problem with the file, we will check it later.
			} catch (IOException ex) {
				// If there is any problem with the file, we will check it later.
			}
		}
		updateKeyList(result);
	}//GEN-LAST:event_jButton2ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		if (fc == null) {
			fc = new JFileChooser();
		}
		String dir = NbPreferences.forModule(PubMLSTWizardAction.class).get("LAST_DIR", "");
		if (dir != null) {
			fc.setCurrentDirectory(new File(dir));
		}
		int r = fc.showDialog(this, "Open");
		if (r == JFileChooser.APPROVE_OPTION && fc.getSelectedFile() != null) {
			jTextField1.setText(fc.getSelectedFile().getAbsolutePath());
			NbPreferences.forModule(PubMLSTWizardAction.class).put("LAST_DIR", fc.getCurrentDirectory().getPath());

			String[] result = null;
			try {
				// Get headers...
				result = new BufferedReader(new FileReader(fc.getSelectedFile())).readLine().split("\t", -1);
			} catch (FileNotFoundException ex) {
				// If there is any problem with the file, we will check it later.
			} catch (IOException ex) {
				// If there is any problem with the file, we will check it later.
			}
			updateKeyList(result);
		}
	}//GEN-LAST:event_jButton1ActionPerformed

	private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
		JToggleButton tb = (JToggleButton) evt.getSource();
		if (tb.isSelected()) {
			soapClient = new PubmlstSOAP();
			iMaxIsolate = soapClient.getIsolateCount(sPubMLSTDB);

			jProgressBar1.setString(null);
			task = new Task();
			task.addPropertyChangeListener(new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent pce) {
					if (pce.getPropertyName().equals("progress")) {
						int progress = (Integer) pce.getNewValue();
						jProgressBar1.setValue(progress);
					}
				}
			});
			task.execute();
		} else {
			task.cancel(true);
		}
	}//GEN-LAST:event_jToggleButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
	private JFileChooser fc;

	private void updateKeyList(String[] result) {

		keyListModel.removeAllElements();
		if (result == null) {
			return;
		}

		for (int i = 0; i < result.length; i++) {
			keyListModel.addElement(result[i]);
		}
	}

	class Task extends SwingWorker<Void, Void> {

		@Override
		public Void doInBackground() {
			soapClient = new PubmlstSOAP();
			jButton2ActionPerformed(null);

			sIsolateData = "";
			for (int is = 1, i = 1; is <= iMaxIsolate; i++) {
				if (isCancelled()) {
					bEmpty = true;
					jProgressBar1.setString("Canceled!");
					return null;
				}
				Map<String, String> hmFields = soapClient.getIsolate(sPubMLSTDB, i);
				if (!hmFields.isEmpty()) {
					for (int f = 0; f < keyListModel.getSize(); f++) {
						sIsolateData += (f == 0) ? "\n" : "\t";
						String field = (String) keyListModel.getElementAt(f);
						if (hmFields.containsKey(field)) {
							sIsolateData += hmFields.get(field);
						}
					}
					int percent = is * 100 / iMaxIsolate;
					setProgress(percent);
					jProgressBar1.setString(percent + "% - " + is + " isolates");
					is++;
				}
				if (i > 3 * iMaxIsolate) {
					jProgressBar1.setString("Force cancel: too many missing data!");
					break;
				}
			}
			bEmpty = false;
			jToggleButton1.setEnabled(false);
			jProgressBar1.setString("Done!");
			return null;
		}

		@Override
		public void done() {
			setCursor(null); //turn off the wait cursor
		}
	}
}
