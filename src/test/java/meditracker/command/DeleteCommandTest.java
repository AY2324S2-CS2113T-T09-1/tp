package meditracker.command;

import meditracker.exception.ArgumentNotFoundException;
import meditracker.medication.Medication;
import meditracker.medication.MedicationManager;
import meditracker.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteCommandTest {
    @Test
    void execute_inOrderArgument_expectMedicationDeleted() throws ArgumentNotFoundException {
        MedicationManager medicationManager = new MedicationManager();
        Medication medication = new Medication(
                "Medication_A",
                60.0,
                500.0,
                null,
                null,
                null,
                "01/07/25",
                "morning",
                "cause_dizziness",
                "Fortnightly");
        medicationManager.addMedication(medication);

        String inputString = "delete -l 1";
        DeleteCommand command = new DeleteCommand(inputString);
        Ui ui = new Ui();
        command.execute(medicationManager, null, ui);

        assertThrows(IndexOutOfBoundsException.class, () -> medicationManager.getMedication(1));
    }
}
