import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class IOHandler {

    private final String fileEnding = ".stub";
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
        saveFileChooser = makeFileChooser("Please find your ticket", 2);

        saveFileChooser.setDialogTitle("Please save your ticket somewhere safe.");

        int selectedFile = saveFileChooser.showSaveDialog(null);

        if (selectedFile == JFileChooser.APPROVE_OPTION) {
            File fileToSave = saveFileChooser.getSelectedFile();
            filename = fileToSave.getAbsolutePath();

            if (getOS().matches("(\\bWindows\\b)\\s?(([0-9]{1,2})|\\s?(Vista))?")) {
                if (!fileToSave.getName().endsWith(fileEnding)) {
                    filename = fileToSave.getAbsolutePath() + fileEnding;
                }
            }
            System.out.println("saving file to: " + fileToSave.getAbsolutePath() + "as: " + filename);
            saveFileAsBin(filename, ticket);
        }
        else if(selectedFile == JFileChooser.CANCEL_OPTION | selectedFile == JFileChooser.ERROR_OPTION){
            System.out.println("Window was closed. No ticket dispensed.");
        }

    }

    private String getOS() {
        return System.getProperty("os.name");
    }

    private JFileChooser makeFileChooser(String dialogTitle, int dialogType){
        JFileChooser fileChooser = new JFileChooser(userHome, FileSystemView.getFileSystemView());
        fileChooser.setFileFilter(new FileNameExtensionFilter("Ticket", "stub"));
        fileChooser.setDialogTitle(dialogTitle);
        fileChooser.setDialogType(dialogType);
        fileChooser.setMultiSelectionEnabled(false);
        if(dialogType == 2){
            fileChooser.setSelectedFile(new File("Ticket"));
        }
        return fileChooser;
    }

    public Ticket loadTicketFileFromBin() {
        String filename = "";
        Ticket loadedTicket;

        loadFileChooser = makeFileChooser("Please find your ticket", 1);
        int selectedFile = loadFileChooser.showOpenDialog(null);

        if(selectedFile == JFileChooser.APPROVE_OPTION){
            filename = loadFileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(filename);
        } else if(selectedFile == JFileChooser.ERROR_OPTION || selectedFile == JFileChooser.CANCEL_OPTION){
            System.out.println("I did not get your ticket.\n");
            return null;
        }

        try {
            final ObjectInputStream bin = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
            loadedTicket = (Ticket) bin.readObject();
            bin.close();
            return loadedTicket;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveFileAsBin(String filename, Ticket ticket) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
        try {
            out.writeObject(ticket);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
            out.flush();
        }
    }

    public String getCreateTimeFromFile(Path file) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
        return attr.creationTime().toString();
    }
}

