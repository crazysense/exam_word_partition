package myyuk.exam.option;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OptionBuilder.class)
public class OptionBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNoArgument() {
        OptionBuilder.of(null).build();
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLessArgument() {
        String[] argument = new String[]{"filename", "directory"};
        OptionBuilder.of(argument).build();
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoreArgument() {
        String[] argument = new String[]{"filename", "directory", "10", "more"};
        OptionBuilder.of(argument).build();
        fail();
    }

    @Test
    public void testNonExistFile() {
        PowerMockito.mockStatic(Files.class);
        PowerMockito.when(Files.exists(Paths.get("filename"))).thenReturn(false);

        String[] argument = new String[]{"filename", "directory", "10"};
        Option option = OptionBuilder.of(argument).build();
        assertNull(option);
    }

    @Test
    public void testPartitionNumberLessOrExceed() {
        PowerMockito.mockStatic(Files.class);
        PowerMockito.when(Files.exists(Paths.get("filename"))).thenReturn(true);
        PowerMockito.when(Files.exists(Paths.get("directory"))).thenReturn(true);
        PowerMockito.when(Files.isDirectory(Paths.get("directory"))).thenReturn(true);

        // Exceed
        String[] argument = new String[]{"filename", "directory", "100"};
        Option option = OptionBuilder.of(argument).build();
        assertNull(option);

        // Less
        argument = new String[]{"filename", "directory", "0"};
        option = OptionBuilder.of(argument).build();
        assertNull(option);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreateDirectoryFailure() throws IOException {
        PowerMockito.mockStatic(Files.class);
        PowerMockito.when(Files.exists(Paths.get("filename"))).thenReturn(true);
        PowerMockito.when(Files.exists(Paths.get("directory"))).thenReturn(false);
        PowerMockito.when(Files.createDirectories(Paths.get("directory"))).thenThrow(IOException.class);

        String[] argument = new String[]{"filename", "directory", "10"};
        Option option = OptionBuilder.of(argument).build();
        assertNull(option);
    }

    @Test
    public void testAlreadyExistFile() {
        PowerMockito.mockStatic(Files.class);
        PowerMockito.when(Files.exists(Paths.get("filename"))).thenReturn(true);
        PowerMockito.when(Files.exists(Paths.get("directory"))).thenReturn(true);
        PowerMockito.when(Files.isDirectory(Paths.get("directory"))).thenReturn(false);

        String[] argument = new String[]{"filename", "directory", "10"};
        Option option = OptionBuilder.of(argument).build();
        assertNull(option);
    }

    @Test
    public void testAlreadyExistDirectory() {
        PowerMockito.mockStatic(Files.class);
        PowerMockito.when(Files.exists(Paths.get("filename"))).thenReturn(true);
        PowerMockito.when(Files.exists(Paths.get("directory"))).thenReturn(true);
        PowerMockito.when(Files.isDirectory(Paths.get("directory"))).thenReturn(true);

        String[] argument = new String[]{
                "filename", "directory", "10"
        };
        Option option = OptionBuilder.of(argument).build();

        assertEquals("filename", option.getString(OptionConstants.READ_FILE_PATH));
        assertEquals("directory", option.getString(OptionConstants.WRITE_DIRECTORY_PATH));
        assertEquals((Integer) 10, option.getInteger(OptionConstants.PARTITION_NUMBER));
    }

    @Test
    public void testCreateDirectory() throws Exception {
        PowerMockito.mockStatic(Files.class);
        PowerMockito.when(Files.exists(Paths.get("filename"))).thenReturn(true);
        PowerMockito.when(Files.exists(Paths.get("directory"))).thenReturn(false);
        PowerMockito.when(Files.createDirectories(Paths.get("directory"))).thenReturn(Paths.get("directory"));

        String[] argument = new String[]{
                "filename", "directory", "10"
        };
        Option option = OptionBuilder.of(argument).build();

        assertEquals("filename", option.getString(OptionConstants.READ_FILE_PATH));
        assertEquals("directory", option.getString(OptionConstants.WRITE_DIRECTORY_PATH));
        assertEquals((Integer) 10, option.getInteger(OptionConstants.PARTITION_NUMBER));
    }
}