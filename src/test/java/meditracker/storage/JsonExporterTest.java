package meditracker.storage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import meditracker.medication.Medication;
import meditracker.medication.MedicationManager;


/**
 * A class to test the JSON export functionality.
 */
public class JsonExporterTest {
    private static Path fileToExport = null;

    /**
     * Pre-populate the medication manager with some medications that we need to simulate data exporting. They can be
     * potentially errornous (i.e. empty field where they are not supposed to be).
     */
    @BeforeAll
    public static void initiateMedicationManager()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method addMedicationWithoutChecksMethod =
                MedicationManager.class.getDeclaredMethod("addMedicationWithoutChecks", Medication.class);
        addMedicationWithoutChecksMethod.setAccessible(true);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Medication med1 = new Medication(
                "Test Valid Medication one",
                69.0,
                0.0,
                1.0,
                2.0,
                LocalDate.parse("2024-11-23", dateTimeFormatter),
                "No Remarks",
                1,
                87
        );

        Medication med2 = new Medication(
                "Test Valid Medication two",
                10000.0,
                1.0,
                0.0,
                0.0,
                LocalDate.parse("2025-01-01", dateTimeFormatter),
                "",
                1,
                87
        );

        Medication med3 = new Medication(
                "Invalid Medication name 4",
                999.0,
                0.0,
                0.0,
                0.0,
                LocalDate.parse("2025-01-01", dateTimeFormatter),
                "null",
                1,
                87
        );

        addMedicationWithoutChecksMethod.invoke(MedicationManager.class, med1);
        addMedicationWithoutChecksMethod.invoke(MedicationManager.class, med2);
        addMedicationWithoutChecksMethod.invoke(MedicationManager.class, med3);
    }

    @BeforeEach
    public void setUpWriteFile() {
        Path jsonSaveFile = MediTrackerFileConfig.getDefaultJsonSaveFilePath();
        fileToExport = FileReaderWriter. getCreatedTemporarySaveFile(jsonSaveFile);
    }

    @AfterEach
    public void cleanup() {
        try {
            if (fileToExport != null) {
                Files.deleteIfExists(fileToExport);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void placeHolder() {
        JsonExporter.saveMedicationDataToJson(fileToExport);
    }
}
