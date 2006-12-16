/*
 Copyright 2006 Paul Zepernick

 Licensed under the Apache License, Version 2.0 (the "License"); 
 you may not use this file except in compliance with the License. 
 You may obtain a copy of the License at 

 http://www.apache.org/licenses/LICENSE-2.0 

 Unless required by applicable law or agreed to in writing, software distributed 
 under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 CONDITIONS OF ANY KIND, either express or implied. See the License for 
 the specific language governing permissions and limitations under the License.  
 */
package net.sf.pzfilereader.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * This holds a collection of columns and their values along with the actual
 * rownumber they appear in the flat file
 * 
 * @author Paul Zepernick
 * @version 2.0
 */
public class Row {
    /** List to hold all columns that exist in the row */
    private List cols;

    /** Row number in flat file */
    private int rowNumber;

    /** key to the MD for this row, null will indicate it is "detail" MD */
    private String mdkey;
    
    /**
     * Constructs a new Row
     *
     */
    public Row() {
        mdkey = null;
        cols = new ArrayList();
    }

    /**
     * Adds a column to a row
     * 
     * @param colValue -
     *            String value to add to the row
     */
    public void addColumn(final String colValue) {
        cols.add(colValue);
    }

    /**
     * Appends the List of Strings to the existing columns in the row
     * 
     * @param columns -
     *            List of Strings to append to the row
     */
    public void addColumn(final List columns) {
        if (cols == null) {
            cols = new ArrayList();
        }
        cols.addAll(columns);
    }

    /**
     * Returns the value of a column for a specified column name
     * 
     * @param colPosition -
     *            int position of the column in the array
     * @return String value of column
     */
    public String getValue(final int colPosition) {
        return (String) cols.get(colPosition);
    }

    /**
     * Set the value of a column for a specified column name
     * 
     * @param columnIndex -
     *            column number to change
     * @param value -
     *            String column value
     */
    public void setValue(final int columnIndex, final String value) {
        cols.set(columnIndex, value);
    }

    /**
     * Returns the rowNumber.
     * 
     * @return int
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * Sets the rowNumber.
     * 
     * @param rowNumber
     *            The rowNumber to set
     */
    public void setRowNumber(final int rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * Returns the cols for the row.
     * 
     * @return Vector
     */
    public List getCols() {
        return cols;
    }

    /**
     * Set the columns for the row.
     * 
     * @param cols -
     *            Vector of Strings
     */
    public void setCols(final List cols) {
        this.cols = cols;
    }

    /**
     * @return Returns the mdkey.
     */
    public String getMdkey() {
        return mdkey;
    }

    /**
     * @param mdkey
     *            The mdkey to set.
     */
    public void setMdkey(final String mdkey) {
        this.mdkey = mdkey;
    }
}