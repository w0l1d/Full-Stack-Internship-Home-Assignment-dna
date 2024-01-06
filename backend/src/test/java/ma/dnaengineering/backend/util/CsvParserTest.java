package ma.dnaengineering.backend.util;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    @Test
    void testParseCsv() throws Exception {
        String csvData = "name,age\nJohn,30\nJane,25";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        List<String[]> result = CsvParser.parseCsv(inputStream);

        assertEquals(3, result.size());
        assertArrayEquals(new String[]{"name", "age"}, result.get(0));
        assertArrayEquals(new String[]{"John", "30"}, result.get(1));
        assertArrayEquals(new String[]{"Jane", "25"}, result.get(2));
    }

    @Test
    void testParseCsv_EmptyData() throws Exception {
        String csvData = "";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        List<String[]> result = CsvParser.parseCsv(inputStream);

        assertTrue(result.isEmpty());
    }

    @Test
    void testParseCsv_SingleRow() throws Exception {
        String csvData = "name,age\nJohn,30";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        List<String[]> result = CsvParser.parseCsv(inputStream);

        assertEquals(2, result.size());
        assertArrayEquals(new String[]{"name", "age"}, result.get(0));
        assertArrayEquals(new String[]{"John", "30"}, result.get(1));
    }

    @Test
    void testParseCsv_DifferentDataTypes() throws Exception {
        String csvData = "name,age,score\nJohn,30,99.5\nJane,25,100.0";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        List<String[]> result = CsvParser.parseCsv(inputStream);

        assertEquals(3, result.size());
        assertArrayEquals(new String[]{"name", "age", "score"}, result.get(0));
        assertArrayEquals(new String[]{"John", "30", "99.5"}, result.get(1));
        assertArrayEquals(new String[]{"Jane", "25", "100.0"}, result.get(2));
    }

}