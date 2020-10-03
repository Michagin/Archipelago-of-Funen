import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class IOHandler {

    private Ticket ticket = null;
    private final String fileEnding = ".tck";
    private final String userHome = System.getProperty("user.home");
    private JFileChooser saveFileChooser;
    private JFileChooser loadFileChooser;

    public IOHandler(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }


    public void saveToDisk(Ticket ticket) throws IOException {
        String filename;
        saveFileChooser = new JFileChooser(userHome, FileSystemView.getFileSystemView());
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Ticket", fileEnding));
        saveFileChooser.setDialogTitle("Please save your ticket somewhere safe.");

        int selectedFile = saveFileChooser.showSaveDialog(null);

        if (selectedFile == JFileChooser.APPROVE_OPTION) {
            File fileToSave = saveFileChooser.getSelectedFile();
            filename = fileToSave.getAbsolutePath();

            if (getOS().matches("Windows...")) {
                if (!fileToSave.getName().endsWith(fileEnding)) {
                    filename = fileToSave.getAbsolutePath() + fileEnding;
                }
            }
            System.out.println("saving file to: " + fileToSave.getAbsolutePath());
            saveFileAsBin(filename);
        }
        else if(selectedFile == JFileChooser.CANCEL_OPTION | selectedFile == JFileChooser.ERROR_OPTION){
            System.out.println("Window was closed. No ticket dispensed.");
        }

    }

    private String getOS() {
        return System.getProperty("os.name");
    }

    public Ticket loadTicketFileAsBin() {
        String filename = null;
        loadFileChooser = new JFileChooser(userHome, FileSystemView.getFileSystemView());
        loadFileChooser.setFileFilter(new FileNameExtensionFilter("Ticket", "tck"));
        loadFileChooser.setDialogTitle("Please find your ticket.");
        int selectedFile = loadFileChooser.showOpenDialog(null);

        if(selectedFile == JFileChooser.APPROVE_OPTION){
            filename = loadFileChooser.getSelectedFile().getAbsolutePath();
        }

        try {
            ObjectInputStream bin = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
            ticket = null;
            ticket = (Ticket) bin.readObject();
            return ticket;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveFileAsBin(String filename) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
        try {
            out.writeObject(ticket);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}

