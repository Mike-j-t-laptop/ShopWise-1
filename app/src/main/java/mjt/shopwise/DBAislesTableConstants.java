package mjt.shopwise;

import java.util.ArrayList;
import java.util.Arrays;

import static mjt.shopwise.DBConstants.DEFAULTORDER;
import static mjt.shopwise.DBConstants.IDTYPE;
import static mjt.shopwise.DBConstants.INT;
import static mjt.shopwise.DBConstants.PERIOD;
import static mjt.shopwise.DBConstants.STD_ID;
import static mjt.shopwise.DBConstants.TXT;

/**
 * DBAislesTableConstants - Constant values for the Aisles Table
 */

public class DBAislesTableConstants {
    /**
     * AISLES TABLE
     *      ID(INT) KEY/PRIMARY INDEX
     *      AISLESHOPREF(INT) Shop this aisle belongs to
     *      AISLEORDER(INT) Order of the aisle within the shop
     *      AISLENAME(TEXT) NAme of the aisle/location
     */
    public static final String AISLES_TABLE = "aisles";

    /**
     * _id (aka aisles_id)(INT) Key/primary index
     */
    public static final String AISLES_ID_COL = STD_ID;
    public static final String AISLES_ID_COL_FULL = AISLES_TABLE +
            PERIOD +
            AISLES_ID_COL;
    public static final String AISLES_ALTID_COL = AISLES_TABLE + STD_ID;
    public static final String AISLES_ALTID_COL_FULL = AISLES_TABLE +
            PERIOD +
            AISLES_ALTID_COL;
    public static final String AISLES_ID_TYPE = IDTYPE;
    public static final Boolean AISLES_ID_PRIMARY_INDEX = true;
    public static final DBColumn AISLESIDCOL = new DBColumn(AISLES_ID_COL,
            AISLES_ID_TYPE,
            AISLES_ID_PRIMARY_INDEX,
            ""
    );

    /**
     * aisleshopref - shop that owns this aisle (1 owner)
     */
    public static final String AISLES_SHOPREF_COL = "aisleshopref";
    public static final String AISLES_SHOPREF_COL_FULL = AISLES_TABLE +
            PERIOD +
            AISLES_SHOPREF_COL;
    public static final String AISLES_SHOPREF_TYPE = INT;
    public static final Boolean AISLES_SHOPREF_PRIMARY_INDEX = false;
    public static final DBColumn AISLESSHOPREFCOL = new DBColumn(AISLES_SHOPREF_COL,
            AISLES_SHOPREF_TYPE,
            AISLES_ID_PRIMARY_INDEX,
            ""
    );

    /**
     * aisleorder - order of the aisle within the shop
     */
    public static final String AISLES_ORDER_COL = "aisleorder";
    public static final String AISLES_ORDER_COL_FULL = AISLES_TABLE +
            PERIOD +
            AISLES_ORDER_COL;
    public static final String AISLES_ORDER_TYPE = INT;
    public static final Boolean AISLES_ORDER_PRIMARY_INDEX = false;
    public static final DBColumn AISLESORDERCOL = new DBColumn(AISLES_ORDER_COL,
            AISLES_ORDER_TYPE,
            AISLES_ID_PRIMARY_INDEX,
            DEFAULTORDER
    );

    /**
     * aislename - name of the aisle
     */
    public static final String AISLES_NAME_COL = "aislename";
    public static final String AISLES_NAME_COL_FULL = AISLES_TABLE +
            PERIOD +
            AISLES_NAME_COL;
    public static final String AISLES_NAME_TYPE = TXT;
    public static final Boolean AISLES_NAME_PRIMARY_INDEX = false;
    public static final DBColumn AISLESNAMECOL = new DBColumn(AISLES_NAME_COL,
            AISLES_ID_TYPE,
            AISLES_ID_PRIMARY_INDEX,
            ""
    );
    public static final ArrayList<DBColumn> AISLESCOLS = new ArrayList<>(Arrays.asList(AISLESIDCOL,
            AISLESSHOPREFCOL,
            AISLESORDERCOL,
            AISLESNAMECOL));
    public static final DBTable AISLESTABLE = new DBTable(AISLES_TABLE,AISLESCOLS);
}
