import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class IOHandler {

    private Ticket ticket = null;
    private final String userHome = System.getProperty("user.home");
    private JFileChooser saveFileChooser;
    private JFileChooser loadFileChooser;


    public IOHandler(Ticket ticket) {
        this.ticket = ticket;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public IOHandler(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public void saveToDisk() throws IOException {
        String filename;
        saveFileChooser = new JFileChooser(userHome, FileSystemView.getFileSystemView());
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Ticket", "tck"));
        saveFileChooser.setDialogTitle("Please save your ticket somewhere safe.");

        int selectedFile = saveFileChooser.showSaveDialog(null);

        if (selectedFile == JFileChooser.APPROVE_OPTION) {
            File fileToSave = saveFileChooser.getSelectedFile();
            filename = fileToSave.getAbsolutePath();

            if (getOS().matches("Windows...")) {
                if (!fileToSave.getName().endsWith(".ticket")) {
                    filename = fileToSave.getAbsolutePath() + ".tck";
                }
            }
            System.out.println("saving file to: " + fileToSave.getAbsolutePath());
            saveFileAsBin(filename);
        }

    }

    private String getOS() {
        return System.getProperty("os.name");
    }

    private Ticket loadTicketFileAsBin(String filename) {
        loadFileChooser = new JFileChooser(userHome, FileSystemView.getFileSystemView());
        loadFileChooser.setFileFilter(new FileNameExtensionFilter("Ticket", "tck"));
        loadFileChooser.setDialogTitle("Please find your ticket.");

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

