import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class IOHandler {

    private final String fileEnding = ".stub";                                      //file-ending to be used on saved binary files.
    private final String userHome = System.getProperty("user.home");                //gets the users home-folder. Works for MacOS, Linux and Win10/7.
    private JFileChooser saveFileChooser;                                           //Java Swing element to make a file-chooser.
    private JFileChooser loadFileChooser;

    /**
     * This constructor sets the 'look and feel' (theme) of the swing elements (GUI). The selected theme is system specific
     * to match the rest of the os.
     */
    public IOHandler(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method used to write Ticket to disk.
     *
     * @param ticket ticket to be save to disk.
     * @throws IOException
     */
    public void saveToDisk(Ticket ticket) throws IOException {
        String filename;

        /* GUI file chooser */
        saveFileChooser = makeFileChooser("Please save your ticket somewhere safe.", 2);                // 2 is Save-dialog type.
        int selectedFile = saveFileChooser.showSaveDialog(null);                                                    //opens the dialog window and returns an integer depending on what the user did (approve, cancel, abort).

        if (selectedFile == JFileChooser.APPROVE_OPTION) {                                                                //if 'save' was pressed.
            File fileToSave = saveFileChooser.getSelectedFile();                                                          //get the file from the file-chooser that was specified.
            filename = fileToSave.getAbsolutePath();                                                                      //get the absolute path to that file.

            if (getOS().matches("(\\bWindows\\b)\\s?(([0-9]{1,2})|\\s?(Vista))?")) {                                //checks if you are on windows.
                if (!fileToSave.getName().endsWith(fileEnding)) {                                                         //in case the file the user specified does *not* end with file-ending, the program adds the file-ending (Windows like file-endings).
                    filename = fileToSave.getAbsolutePath() + fileEnding;
                }
            }
            //System.out.println("saving file to: " + fileToSave.getAbsolutePath() + "as: " + filename);                  /* used for debug */
            saveFileAsBin(filename, ticket);                                                                              //saves file to disk.
        }
        else if(selectedFile == JFileChooser.CANCEL_OPTION | selectedFile == JFileChooser.ERROR_OPTION){                  //if the user pressed cancel or close...
            System.out.println("Window was closed. No ticket dispensed.");
        }

    }

    /**
     * Gets whatever OS the user is using.
     * @return a string with the OS.
     * (example, for Windows 10 this method returns "Windows 10", for Arch Linux, it returns "Linux")
     */
    private String getOS() {
        return System.getProperty("os.name");
    }

    /**
     * This method makes a new file-chooser dialog window by using Java Swing elements and sets it up with
     * title and opening destination as well as suggested filename and file-extensions. It also takes into account
     * what type for dialog window it is (save/open(load)).
     *
     * @param dialogTitle The title of the dialog window.
     * @param dialogType 1 = open dialog, 2 = save dialog.
     * @return the file-chooser object created and sat op.
     */
    private JFileChooser makeFileChooser(String dialogTitle, int dialogType){
        JFileChooser fileChooser = new JFileChooser(userHome, FileSystemView.getFileSystemView());                        //creates a new dialog windows which starts in the users home folder.
        fileChooser.setFileFilter(new FileNameExtensionFilter("Ticket", "stub"));                     //sets up a filter for file-ending/extension to accept "Ticket"-files (.stub).
        fileChooser.setDialogTitle(dialogTitle);
        fileChooser.setDialogType(dialogType);
        fileChooser.setMultiSelectionEnabled(false);                                                                      //disable the option for selecting multiple files in dialog window.
        if(dialogType == 2){
            fileChooser.setSelectedFile(new File("Ticket"));                                                     //suggested filename
        }
        return fileChooser;
    }

    /**
     * Takes in a binary file and makes it into a Ticket object, using a {@ObjectInputStream}.
     * @return a ticket with data from binary file.
     */
    public Ticket loadTicketFileFromBin() {
        String filename = "";
        Ticket loadedTicket;

        /* GUI file chooser */
        loadFileChooser = makeFileChooser("Please find your ticket", 1);                                //1 is open dialog type (load file).
        int selectedFile = loadFileChooser.showOpenDialog(null);                                                    //opens the dialog window and returns an integer depending on what the user did (approve, cancel, abort).

        if(selectedFile == JFileChooser.APPROVE_OPTION){                                                                  //if 'open' was pressed.
            filename = loadFileChooser.getSelectedFile().getAbsolutePath();                                               //get the absolute path to that file.
            //System.out.println(filename);                                                                               /* used for debug */
        } else if(selectedFile == JFileChooser.ERROR_OPTION || selectedFile == JFileChooser.CANCEL_OPTION){               //if user closes the dialog window without pressing open, return null.
            System.out.println("I did not get your ticket.\n");
            return null;
        }

        try {
            final ObjectInputStream bin = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename))); //BufferedInputStream is added for speed.
            loadedTicket = (Ticket) bin.readObject();                                                                    //The binary file is read and assigned to a ticket object.
            bin.close();                                                                                                 //The objectInputStream is closed and the object finalized.
            return loadedTicket;                                                                                         //The new ticket is returned.

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;                                                                                                 //return null is something goes wrong...
        }
    }

    /**
     * This method writes a Ticket-object to a binary file using a {@ObjectOutputStream}
     * @param filename the path to where the file is to be created.
     * @param ticket the ticket from which the data will be read.
     * @throws IOException in case the filename is either invalid or in some way inaccessible.
     */
    private void saveFileAsBin(String filename, Ticket ticket) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
        try {
            out.writeObject(ticket);                                                                                     //Here the file is written to the specified path.
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();                                                                                                 //a finally clause happen regardless of the operation was successful or not.
            out.flush();                                                                                                 //we then close and empty the ObjectOutputStream
        }
    }
}

