package test;

import java.util.ArrayList;
import java.util.List;

import main.ClassificationType;
import org.junit.After;
import org.junit.Test;

import implementation.FileParserService;
import junit.framework.TestCase;
import model.DirectoryStructure;
import model.TreeModel;

/**
 * Created by j.reddy.yedla on 10/20/2016.
 */
public class FileParserServiceTest extends TestCase {
    FileParserService fileParserService = null;
    List<TreeModel> listOfFiles = null;
    TreeModel directoryStructure = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        fileParserService = new FileParserService();
        listOfFiles = fileParserService.csvFileParser("./src/main/resources/directory-structure-testCases.csv");
        directoryStructure = fileParserService.folderStructureCreation(listOfFiles);
    }

    @After
    public void closeGlobalResources() {
        FileParserService fileParserService = null;
        List<TreeModel> directories = null;
        TreeModel directoryStructure = null;
    }

    @Test
    public void testFetchFilesByClassification() throws Exception {
        // Testing Top secret files
        String expected = "name = file9, type = file, size = 90, classification = Top secret, checksum = 42";
        assertEquals(expected, fileParserService.getDataByClassification(listOfFiles, ClassificationType.TOP_SECRET.stringValue()));

        // Testing Secret files
        expected = "name = file4, type = file, size = 40, classification = Secret, checksum = 42";
        assertEquals(expected, fileParserService.getDataByClassification(listOfFiles, ClassificationType.SECRET.stringValue()));

        // Testing negative case - Fetching public files and comparing with secret files
        expected = "name = file4, type = file, size = 40, classification = Secret, checksum = 42";
        assertNotSame(expected, fileParserService.getDataByClassification(listOfFiles, ClassificationType.PUBLIC.stringValue()));
    }

    @Test
    public void testFetchFilesByMultiClassification() throws Exception {
        // Testing Top secret files
        String expected = "name = file4, type = file, size = 40, classification = Secret, checksum = 42\n"+
                "name = file9, type = file, size = 90, classification = Top secret, checksum = 42";
        List<String> classifications = new ArrayList<String>();
        classifications.add(ClassificationType.SECRET.stringValue());
        classifications.add(ClassificationType.TOP_SECRET.stringValue());
        assertEquals(expected, fileParserService.getFilesByMultiClassification(listOfFiles, classifications));
    }


    @Test
    public void testComputeSizeOfFilesByClassification() throws Exception {
        assertEquals(50, fileParserService.calculateSizeOfFilesByClassification(listOfFiles, ClassificationType.PUBLIC.stringValue()));
    }

    @Test
    public void testFetchFilesNotInClassification() throws Exception {
        String expected = "name = file9, type = file, size = 90, classification = Top secret, checksum = 42";
        assertEquals(expected, fileParserService.getFilesNotInClassification(directoryStructure, ClassificationType.FOLDER.stringValue(), ClassificationType.PUBLIC.stringValue()));
        assertTrue(expected.contains(fileParserService.getFilesNotInClassification(directoryStructure, ClassificationType.FOLDER.stringValue(), ClassificationType.PUBLIC.stringValue())));
    }

}
