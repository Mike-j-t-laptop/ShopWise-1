package mjt.shopwise;

import java.util.ArrayList;
import java.util.Arrays;

import static mjt.shopwise.DBConstants.IDTYPE;
import static mjt.shopwise.DBConstants.PERIOD;
import static mjt.shopwise.DBConstants.STD_ID;
import static mjt.shopwise.DBConstants.TXT;

/**
 * DBProductsTableConstants - Constants for the Products Table
 */
public class DBProductsTableConstants {
    /**************************************************************************
     * PRODUCTS TABLE
     * _ID(INT) Key/Primary Index
     */
    public static final String PRODUCTS_TABLE = "products";
    public static final String PRODUCTS_ID_COL = STD_ID;
    public static final String PRODUCTS_ID_COL_FULL = PRODUCTS_TABLE +
            PERIOD +
            PRODUCTS_ID_COL;
    public static final String PRODUCTS_ALTID_COL = PRODUCTS_TABLE +
            PRODUCTS_ID_COL;
    public static final String PRODUCTS_ALTID_COL_FULL = PRODUCTS_TABLE + PERIOD + PRODUCTS_ALTID_COL;
    public static final String PRODUCTS_ID_TYPE = IDTYPE;
    public static final boolean PRODUCTS_ID_PRIMARY_INDEX = true;
    public static final DBColumn PRODUCTSIDCOL = new DBColumn(PRODUCTS_ID_COL,
            PRODUCTS_ID_TYPE,
            PRODUCTS_ID_PRIMARY_INDEX,
            ""
    );

    public static final String PRODUCTS_NAME_COL = "productname";
    public static final String PRODUCTS_NAME_COL_FULL = PRODUCTS_TABLE +
            PERIOD +
            PRODUCTS_NAME_COL;
    public static final String PRODUCTS_NAME_TYPE = TXT;
    public static final boolean PRODUCTS_NAME_PRIMARY_INDEX = false;
    public static final DBColumn PRODUCTSNAMECOL = new DBColumn(PRODUCTS_NAME_COL,
            PRODUCTS_NAME_TYPE,
            PRODUCTS_NAME_PRIMARY_INDEX,
            ""
    );
    public static final String PRODUCTS_NOTES_COL = "productnotes";
    public static final String PRODUCTS_NOTES_COL_FULL = PRODUCTS_TABLE +
            PERIOD +
            PRODUCTS_NOTES_COL;
    public static final String PRODUCTS_NOTES_TYPE = TXT;
    public static final boolean PRODUCTS_NOTES_PRIMARY_INDEX = false;
    public static final DBColumn PRODUCTSNOTESCOL = new DBColumn(PRODUCTS_NOTES_COL,
            PRODUCTS_NOTES_TYPE,
            PRODUCTS_NOTES_PRIMARY_INDEX,
            ""
    );
    public static final ArrayList<DBColumn> PRODUCTSCOLS = new ArrayList<>(Arrays.asList(PRODUCTSIDCOL,
            PRODUCTSNAMECOL,
            PRODUCTSNOTESCOL));
    public static final DBTable PRODUCTSTABLE = new DBTable(PRODUCTS_TABLE,
            PRODUCTSCOLS);
}