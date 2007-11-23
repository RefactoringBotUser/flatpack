package net.sf.flatpack.pzparser;

import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;
import net.sf.flatpack.DataError;
import net.sf.flatpack.DataSet;
import net.sf.flatpack.DefaultParserFactory;
import net.sf.flatpack.Parser;
import net.sf.flatpack.util.FPConstants;
import net.sf.flatpack.util.FPInvalidUsageException;

/**
 * Test the different options that can be 
 * set on the parser
 * 
 * @author Paul Zepernick
 */
public class PZParserOptsTest extends TestCase {

    public void testEmptyToNull() {
        DataSet ds;
        final String cols = "COLUMN1,column2,Column3\r\n value1,,value3";
        Parser p = DefaultParserFactory.getInstance().newDelimitedParser(new StringReader(cols), ',', FPConstants.NO_QUALIFIER);
        p.setNullEmptyStrings(true);
        ds = p.parse();
        ds.next();

        assertEquals("String should be null...", null, ds.getString("column2"));

        p = DefaultParserFactory.getInstance().newDelimitedParser(new StringReader(cols), ',', FPConstants.NO_QUALIFIER);
        p.setNullEmptyStrings(false);
        ds = p.parse();
        ds.next();

        assertEquals("String should be empty...", "", ds.getString("column2"));
    }

    public void testIgnoreWarnings() {
        DataSet ds;
        final String cols = "COLUMN1,column2,Column3\r\n value1,value2";
        Parser p = DefaultParserFactory.getInstance().newDelimitedParser(new StringReader(cols), ',', FPConstants.NO_QUALIFIER);
        p.setHandlingShortLines(true);
        p.setIgnoreParseWarnings(true);
        ds = p.parse();

        assertEquals("Error collection should be empty...", 0, ds.getErrors().size());

        p = DefaultParserFactory.getInstance().newDelimitedParser(new StringReader(cols), ',', FPConstants.NO_QUALIFIER);
        p.setHandlingShortLines(true);
        p.setIgnoreParseWarnings(false);
        ds = p.parse();
        ds.next();

        assertEquals("Error collection should contain warning...", 1, ds.getErrors().size());
    }

    public void testCaseSensitiveMetaData() {
        DataSet ds;
        final String cols = "COLUMN1,column2,Column3\r\n value1,value2,value3";
        Parser p = DefaultParserFactory.getInstance().newDelimitedParser(new StringReader(cols), ',', FPConstants.NO_QUALIFIER);

        //check that column names are case sensitive
        p.setColumnNamesCaseSensitive(true);
        ds = p.parse();
        ds.next();
        try {
            ds.getString("COLUMN2");
            fail("Column was mapped as 'column2' and lookup was 'COLUMN2'...should fail with case sensitivity turned on");
        } catch (final NoSuchElementException e) {
            //this should happen since we are matching case
        }

        //check that column names are NOT case sensitive
        p = DefaultParserFactory.getInstance().newDelimitedParser(new StringReader(cols), ',', FPConstants.NO_QUALIFIER);
        p.setColumnNamesCaseSensitive(false);
        ds = p.parse();
        ds.next();
        try {
            ds.getString("COLUMN2");
        } catch (final NoSuchElementException e) {
            fail("Column was mapped as 'column2' and lookup was 'COLUMN2'...should NOT fail with case sensitivity turned OFF");
        }
    }
    
    public void testEmptyRowCheck() {
        DataSet ds;
        final String cols = "column1,column2,column3\r\n,,";
       
        //check to see if the flag empty rows works
        Parser p = DefaultParserFactory.getInstance().newDelimitedParser(new StringReader(cols), ',', FPConstants.NO_QUALIFIER);
        p.setFlagEmptyRows(true);
        ds = p.parse();
        ds.next();
        assertEquals("Row should return empty...", ds.isRowEmpty(), true);
        
        //do not set to flag empty rows, but make the check anyhow to make sure we get an exception
        p = DefaultParserFactory.getInstance().newDelimitedParser(new StringReader(cols), ',', FPConstants.NO_QUALIFIER);
        ds = p.parse();
        ds.next();
        try {
            ds.isRowEmpty();
            fail("should have got FPInvalidUsageException...");
        } catch(FPInvalidUsageException e){}

    }
    
    public void testStoreRawDataToDataError() {
        DataSet ds;
        final String cols = "column1,column2,column3\r\nVAL1,VAL2,VAL3,VAL4";
        Parser p = DefaultParserFactory.getInstance().newDelimitedParser(new StringReader(cols), ',', FPConstants.NO_QUALIFIER);
        p.setStoreRawDataToDataError(true);
        ds = p.parse();
        Iterator errors = ds.getErrors().iterator();
        DataError de = (DataError)errors.next();
      
        assertNotNull("DataError should contain line data...", de.getRawData());
        
        p = DefaultParserFactory.getInstance().newDelimitedParser(new StringReader(cols), ',', FPConstants.NO_QUALIFIER);
        p.setStoreRawDataToDataError(false);
        ds = p.parse();
        errors = ds.getErrors().iterator();
        de = (DataError)errors.next();
        assertNull("DataError should have <null> line data...", de.getRawData());
    }

    public static void main(final String[] args) {
        junit.textui.TestRunner.run(PZParserOptsTest.class);
    }
}
