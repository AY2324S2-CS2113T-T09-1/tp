package meditracker;

import meditracker.command.Command;
import meditracker.dailymedication.DailyMedicationManager;
import meditracker.exception.ArgumentNotFoundException;
import meditracker.exception.FileReadWriteException;
import meditracker.exception.DuplicateArgumentFoundException;
import meditracker.exception.MediTrackerException;
import meditracker.logging.MediLogger;
import meditracker.medication.MedicationManager;
import meditracker.command.CommandParser;
import meditracker.storage.FileReaderWriter;
import meditracker.ui.Ui;

import java.util.List;

/**
 * The main class for the MediTracker application.
 * It initializes the user interface and runs the application loop.
 */
public class MediTracker {

    private MedicationManager medicationManager;

    /**
     * Constructs a new MediTracker object and initializes both medicationManager and
     * dailyMedicationManager.
     */
    public MediTracker() {
        medicationManager = new MedicationManager();
        DailyMedicationManager.createDailyMedicationManager(medicationManager);
    }

    /**
     * Constructs a new MediTracker object with data from save file for DailyMedicationManager
     *
     * @param dailyMedicationList Daily medication
     */
    public MediTracker(List<String> dailyMedicationList) {
        medicationManager = new MedicationManager();
        DailyMedicationManager.importDailyMedicationManager(dailyMedicationList);
    }

    /**
     * Runs the MediTracker application.
     * This method displays a welcome message, reads user commands, and processes them until the user exits the
     * application.
     *
     * @throws NullPointerException When the command does not exist.
     * @throws NumberFormatException If the argument of type double is not supplied as type double.
     * @throws FileReadWriteException when there is error to write into text file.
     */
    public void run() throws FileReadWriteException {
        //@@author nickczh-reused
        //Reused from https://github.com/nickczh/ip
        //with minor modifications
        FileReaderWriter.loadMediTrackerData(medicationManager);
        Ui.showWelcomeMessage();
        boolean isExit = false;
        while (!isExit) {
            String fullCommand = Ui.readCommand();
            Command command = null;
            Ui.showLine();
            try {
                command = CommandParser.parse(fullCommand);
                command.execute(medicationManager);
            } catch (ArgumentNotFoundException | DuplicateArgumentFoundException | MediTrackerException ex) {
                System.out.println(ex.getMessage());
            } catch (NullPointerException ex) {
                System.out.println("Invalid MediTracker command! Please refer to the user guide.");
            } catch (NumberFormatException ex) {
                System.out.println("Dosage/Quantity should be of type double!");
            } catch (FileReadWriteException e) {
                throw new FileReadWriteException("IO Error: Unable to write to text File");
            }
            if (command != null) {
                isExit = command.isExit();
            }
        }
    }

    /**
     * Starts the MediTracker application.
     * It creates a new MediTracker object and calls its run() method.
     *
     * @param args Command-line arguments.
     * @throws FileReadWriteException when there is error to write into text file.
     */
    public static void main(String[] args) throws FileReadWriteException {
        MediLogger.initialiseLogger();

        List<String> dailyMedicationList = FileReaderWriter.loadDailyMedicationData();
        if (dailyMedicationList == null) {
            new MediTracker().run();
        } else {
            new MediTracker(dailyMedicationList).run();
        }
    }
}
