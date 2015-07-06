/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.phyloviz.project.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Properties;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import net.phyloviz.core.data.DataSet;
import net.phyloviz.core.data.Population;
import net.phyloviz.core.data.TypingData;
import net.phyloviz.core.explorer.DataSetNode;
import net.phyloviz.core.util.PopulationFactory;
import net.phyloviz.core.util.TypingFactory;
import net.phyloviz.project.ProjectItem;
import net.phyloviz.project.ProjectItemFactory;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.actions.NodeAction;

public final class SaveAsProjectAction extends NodeAction {

    private static SaveAsProjectAction _instance;

    private final String ext = ".csv";

    public SaveAsProjectAction() {
        putValue(Action.NAME, "Save as Project");
    }

    @Override
    protected void performAction(Node[] n) {

        DataSetNode dsn = (DataSetNode) n[0];
        DataSet ds = dsn.getLookup().lookup(DataSet.class);
        Properties props = new Properties();

        String dataSetName = dsn.getDisplayName();
        props.put("dataset-name", dataSetName);

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(dataSetName);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            String directory = fc.getSelectedFile().getAbsolutePath();
            File f = new File(directory, dataSetName);
            f.mkdir();
            directory = f.getAbsolutePath();

            TypingData td = ds.getLookup().lookup(TypingData.class);
            String typingFactory = td.getNode().getDisplayName();

            Collection<? extends TypingFactory> tfs = Lookup.getDefault().lookupAll(TypingFactory.class);
            for (TypingFactory next : tfs) {
                String tf = next.toString();
                if (tf.equals(typingFactory)) {
                    props.put("typing-factory", next.getClass().getCanonicalName());
                    String filename = dataSetName + ".typing" + ext;
                    props.put("typing-file", filename);
                    save(directory, filename, td.tableModel());
                    break;
                }
            }

            Population pop = ds.getLookup().lookup(Population.class);
            if (pop != null) {
                props.setProperty("population-factory", PopulationFactory.class.getCanonicalName());
                String key = String.valueOf(pop.getKey());
                props.setProperty("population-foreign-key", key);

                String filename = dataSetName + ".isolate" + ext;
                props.put("population-file", filename);
                save(directory, filename, pop.tableModel());
            }

            Collection<? extends ProjectItem> c = td.getLookup().lookupAll(ProjectItem.class);
            if (c.size() > 0) {
                int algos = 1;
                Collection<? extends ProjectItemFactory> pif = Lookup.getDefault().lookupAll(ProjectItemFactory.class);
                StringBuilder algorithmsFactory = new StringBuilder(), algorithms = new StringBuilder();
                for (ProjectItem item : c) {

                    for (ProjectItemFactory factory : pif) {

                        String itemFactory = factory.getClass().getName();

                        if (itemFactory.contains(item.getMainName())) {

//                            Class klass = item.getClass();
//
//                            for (TopComponent tc : TopComponent.getRegistry().getOpened()) {
//
//                                if (tc.getLookup().lookup(klass) == item) {
//
//                                    GView view = ((IGTPanel) tc).getGView();
//                                    try {
//                                        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Marta Nascimento\\Documents\\employee.ser");
//                                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
//                                        out.writeObject(view);
//                                        out.close();
//                                        fileOut.close();
//                                        System.out.printf("Serialized data is saved in /tmp/employee.ser");
//                                    } catch (IOException i) {
//                                        Exceptions.printStackTrace(i);
//                                    }
//
//                                }
//                            }

                            algorithmsFactory.append(itemFactory).append(",");

                            String filename = dataSetName + ".output." + item.getName() + algos++ + ".json";
                            algorithms.append(filename).append(",");
                            save(directory, filename, item.getOutput());

                            break;
                        }
                    }
                }

                //removing last comma
                algorithmsFactory.deleteCharAt(algorithmsFactory.length() - 1);
                algorithms.deleteCharAt(algorithms.length() - 1);
                //add props
                props.put("algorithm-output", algorithms.toString());
                props.put("algorithm-output-factory", algorithmsFactory.toString());
            }

            saveConfigFile(directory, props);
            StatusDisplayer.getDefault().setStatusText("Project saved successfully.");
        }
    }

    @Override
    protected boolean enable(Node[] nodes) {
        return nodes.length == 1;
    }

    @Override
    public String getName() {
        return "Saving Project";
    }

    @Override
    public boolean asynchronous() {
        return false;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    private void save(String dir, String filename, String output) {
        try {

            File f = new File(dir, filename);
            PrintWriter pw = new PrintWriter(f);

            pw.print(output);

            pw.close();

        } catch (IOException ie) {
            Exceptions.printStackTrace(ie);
        }
    }

    private void save(String dir, String filename, AbstractTableModel tableModel) {
        try {

            File f = new File(dir, filename);
            PrintWriter pw = new PrintWriter(f);
            TableModel tm = tableModel;

            pw.print(tm.getColumnName(0));
            for (int i = 1; i < tm.getColumnCount(); i++) {
                pw.print("\t" + tm.getColumnName(i));
            }
            pw.println();

            for (int k = 0; k < tm.getRowCount(); k++) {
                pw.print(tm.getValueAt(k, 0));
                for (int i = 1; i < tm.getColumnCount(); i++) {
                    pw.print("\t" + tm.getValueAt(k, i));
                }
                pw.println();
            }

            pw.close();

        } catch (IOException ie) {
            Exceptions.printStackTrace(ie);
        }
    }

    private void saveConfigFile(String dir, Properties prop) {

        try {
            FileOutputStream fos = new FileOutputStream(new File(dir, "config.properties"));
            prop.store(fos, "Project Configuration File");
            fos.close();
        } catch (IOException ie) {
            Exceptions.printStackTrace(ie);
        }
    }

}
