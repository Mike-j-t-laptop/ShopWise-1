package mjt.shopwise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * DBShopMethods - Database Methods specific to Shop handling
 */
class DBShopMethods {

    private static final String LOGTAG = "SW-DBSM";
    private Context context;        // Context as passed
    private DBDAO dbdao;            // Get db data access object
    private static SQLiteDatabase db;      // get db
    private static long lastshopadded = -1;            // id of the last shop added
    private static boolean lastshopaddok = false;   // state of last insert
    private static boolean lastshopupdateok = false;

    /**
     * Instantiates a new Db shop methods.
     *
     * @param ctxt the ctxt
     */
    DBShopMethods(Context ctxt) {
        context = ctxt;
        dbdao = new DBDAO(context);
        db = dbdao.db;
    }

    /**************************************************************************
     * getShopCount - get the number of shops
     *
     * @return number of Shops
     */
    int getShopCount() {
        return DBCommonMethods.getTableRowCount(db,
                DBShopsTableConstants.SHOPS_TABLE
        );
    }

    /**************************************************************************
     * getLastShopAdded - return the id of the last shop added
     *
     * @return shopid last shop added
     */
    long getLastShopAdded() {
        return lastshopadded;
    }

    /**************************************************************************
     * ifShopAdded - returns status of last shop insert
     *
     * @return true if added ok, else false
     */
    boolean ifShopAdded() {
        return lastshopaddok;
    }

    /**
     * If shop updated boolean.
     *
     * @return the boolean
     */
    boolean ifShopUpdated() { return lastshopupdateok; }

    /**
     * getHighestShopOrder
     * @return  the highest order via MAX(shoporder)
     */
    int getHighestShopOrder() {
        int rv = 0;
        String columns[] = {
               DBConstants.SQLMAX +
                       DBShopsTableConstants.SHOPS_ORDER_COL +
                       DBConstants.SQLMAXCLOSE +
                       DBConstants.SQLAS +
                       DBShopsTableConstants.SHOPSMAXORDERCOLUMN
        };
        Cursor csr = db.query(
                DBShopsTableConstants.SHOPS_TABLE,
                columns,
                null,null,null,null,null);
        if (csr.getCount() > 0 ) {
            csr.moveToFirst();
            rv = csr.getInt(csr.getColumnIndex(
                    DBShopsTableConstants.SHOPSMAXORDERCOLUMN
            ));
        }
        csr.close();
        return rv;
    }

    /**************************************************************************
     * getShops - get shops as a cursor
     *
     * @param filter sql filter string less WHERE
     * @param order  sql sort string less ORDER BY
     * @return cursor containing selected shops
     */
    Cursor getShops(String filter, String order) {
        return DBCommonMethods.getTableRows(db,
                DBShopsTableConstants.SHOPS_TABLE,
                filter,order
        );
    }

    /**************************************************************************
     *
     * @param filter
     * @param order
     * @return
     */
    Cursor getShopsWithAisles(String filter, String order) {
        String sql = DBConstants.SQLSELECT +
                DBShopsTableConstants.SHOPS_ID_COL_FULL + ", " +
                DBShopsTableConstants.SHOPS_ORDER_COL_FULL + ", " +
                DBShopsTableConstants.SHOPS_NAME_COL_FULL + ", " +
                DBShopsTableConstants.SHOPS_CITY_COL_FULL + ", " +
                DBShopsTableConstants.SHOPS_STREET_COL_FULL + ", " +
                DBShopsTableConstants.SHOPS_STATE_COL_FULL + ", " +
                DBShopsTableConstants.SHOPS_NOTES_COL_FULL +
                DBConstants.SQLFROM +
                DBShopsTableConstants.SHOPS_TABLE +
                DBConstants.SQLLEFTJOIN +
                DBAislesTableConstants.AISLES_TABLE +
                DBConstants.SQLON +
                DBShopsTableConstants.SHOPS_ID_COL_FULL + " = " +
                DBAislesTableConstants.AISLES_SHOPREF_COL_FULL +
                DBConstants.SQLWHERE +
                DBAislesTableConstants.AISLES_SHOPREF_COL_FULL +
                " IS NOT NULL " + filter + " " +
                DBConstants.SQLGROUP +
                DBShopsTableConstants.SHOPS_ID_COL_FULL;

        if (order.length() > 0) {
            sql = sql + DBConstants.SQLORDERBY + order;
        }
        return db.rawQuery(sql,null);
    }

    /**************************************************************************
     * insertShop add a new Shop
     *
     * @param shopname   name of the shop
     * @param shoporder  order of the shop (lowest first)
     * @param shopstreet street part of the address of the shop
     * @param shopcity   city/location of the shop
     * @param shopstate  state/county of the shop
     * @param shopnotes  motes about the shop
     */
    void insertShop(String shopname,
                           int shoporder,
                           String shopstreet,
                           String shopcity,
                           String shopstate,
                           String shopnotes) {

        long addedid;

        lastshopaddok = false;

        ContentValues cv = new ContentValues();
        cv.put(DBShopsTableConstants.SHOPS_NAME_COL,shopname);
        cv.put(DBShopsTableConstants.SHOPS_ORDER_COL,shoporder);
        cv.put(DBShopsTableConstants.SHOPS_STREET_COL,shopstreet);
        cv.put(DBShopsTableConstants.SHOPS_CITY_COL,shopcity);
        cv.put(DBShopsTableConstants.SHOPS_STATE_COL,shopstate);
        cv.put(DBShopsTableConstants.SHOPS_NOTES_COL,shopnotes);
        addedid =  db.insert(DBShopsTableConstants.SHOPS_TABLE,
                null,
                cv);
        if (addedid >= 0) {
            lastshopadded = addedid;
            lastshopaddok = true;
        }
    }

    /**
     * ------------------------------------------------------------------------
     * alternative insertShop method (notes blank)
     *
     * @param shopname   name of the shop
     * @param shoporder  order of the shop (lowest first)
     * @param shopstreet street part of the address of the shop
     * @param shopcity   city/location of the shop
     * @param shopstate  state/county of the shop
     */
    void insertShop(String shopname,
                           int shoporder,
                           String shopstreet,
                           String shopcity,
                           String shopstate) {
        insertShop(shopname, shoporder, shopstreet, shopcity, shopstate,"");
    }

    /**
     * ------------------------------------------------------------------------
     * alternative insertShop method (notes and shopstate blank)
     *
     * @param shopname   name of the shop
     * @param shoporder  order of the shop (lowest first)
     * @param shopstreet street part of the address of the shop
     * @param shopcity   city/location of the shop
     */
    void insertShop(String shopname,
                           int shoporder,
                           String shopstreet,
                           String shopcity) {
        insertShop(shopname, shoporder, shopstreet, shopcity, "");
    }

    /**
     * ------------------------------------------------------------------------
     * alternative insertShop method (notes, shopstate and hhopcity blank)
     *
     * @param shopname   name of the shop
     * @param shoporder  order of the shop (lowest first)
     * @param shopstreet street part of the address of the shop
     */
    void insertShop(String shopname,
                           int shoporder,
                           String shopstreet) {
        insertShop(shopname, shoporder, shopstreet, "");
    }

    /**
     * ------------------------------------------------------------------------
     * alternative insertShop method (notes, shopstate, shopcity and
     * shopstreet blank)
     *
     * @param shopname  name of the shop
     * @param shoporder order of the shop (lowest first)
     */
    void insertShop(String shopname,
                           int shoporder) {
        insertShop(shopname, shoporder, "");
    }

    /**
     * ------------------------------------------------------------------------
     * alternative insertShop method (notes, shopstate, shopcity and
     * shopstreet  blank, shoporder set to default)
     *
     * @param shopname name of the shop
     */
    void insertShop(String shopname) {
        String orderdflt = DBConstants.DEFAULTORDER;
        insertShop(shopname, Integer.parseInt(DBConstants.DEFAULTORDER));
    }

    /**************************************************************************
     * doesShopExist
     *
     * @param shopid shopid
     * @return true if shopid exists, false if not
     */
    boolean doesShopExist(long shopid) {
        boolean rv = false;
        String filter =  DBShopsTableConstants.SHOPS_ID_COL_FULL +
                    " = " + Long.toString(shopid) +
                DBConstants.SQLENDSTATEMENT;
        Cursor csr = DBCommonMethods.getTableRows(db,
                DBShopsTableConstants.SHOPS_TABLE,
                filter,
                ""
        );
        if (csr.getCount() > 0) {
            rv = true;
        }
        csr.close();
        return rv;
    }

    /**************************************************************************
     * modifyShop Update a shop
     *
     * @param shopid     shopid of the shop to be updated
     * @param shoporder  order of the shop 0 if to skip
     * @param shopname   new shopname, "" to skip
     * @param shopstreet new street, "" to skip
     * @param shopcity   new city, "" skip
     * @param shopstate  new state, "" to skip
     * @param shopnotes  new notes, "" to skip
     */
    void modifyShop(long shopid,
                    int shoporder,
                    String shopname,
                    String shopstreet,
                    String shopcity,
                    String shopstate,
                    String shopnotes) {
        /**
         * If the shop doesn't exist then return
         */
        if(!doesShopExist(shopid)) {
            return;
        }
        int updatecount = 0;
        ContentValues cv = new ContentValues();
        if (shoporder > 0) {
            cv.put(DBShopsTableConstants.SHOPS_ORDER_COL,shoporder);
            updatecount++;
        }
        if (shopname.length() > 0) {
            cv.put(DBShopsTableConstants.SHOPS_NAME_COL,shopname);
            updatecount++;
        }
        if (shopstreet.length() > 0) {
            cv.put(DBShopsTableConstants.SHOPS_STREET_COL,shopstreet);
            updatecount++;
        }
        if (shopcity.length() > 0) {
            cv.put(DBShopsTableConstants.SHOPS_CITY_COL,shopcity);
            updatecount++;
        }
        if(shopstate.length() > 0) {
            cv.put(DBShopsTableConstants.SHOPS_STATE_COL,shopstate);
            updatecount++;
        }
        if(shopnotes.length() > 0) {
            cv.put(DBShopsTableConstants.SHOPS_NOTES_COL,shopnotes);
            updatecount++;
        }
        /**
         * if nothing to do then return
         */
        if (updatecount < 1 ) {
            return;
        }
        String[] whereargs = {Long.toString(shopid )};
        String whereclause = DBShopsTableConstants.SHOPS_ID_COL + " = ?";
        lastshopupdateok = false;
        if (db.update(DBShopsTableConstants.SHOPS_TABLE,cv,whereclause,whereargs) > 0) {
            lastshopupdateok = true;
        }
    }

    /**************************************************************************
     * deleteShop = Delete a specified shop from the database along with all
     * the underlying "owned" components.
     * aisles are directly owned by a shop
     * aisles own productusages, rules and shoppinglist entries
     *
     * @param shopid        id of the shop to be deleted
     * @param intransaction the intransaction
     * @return number of shops deleted
     */
    int deleteShop(long shopid, boolean intransaction) {
        int rv = 0;

        String filter = DBAislesTableConstants.AISLES_SHOPREF_COL +
                " = " + Long.toString(shopid) +
                DBConstants.SQLENDSTATEMENT;
        Cursor aislescursor = DBCommonMethods.getTableRows(db,
                DBAislesTableConstants.AISLES_TABLE,
                filter,
                "");
        if(!intransaction) {
            db.beginTransaction();
        }
        while(aislescursor.moveToNext()) {
            long aisleid = aislescursor.getLong(aislescursor.getColumnIndex(DBAislesTableConstants.AISLES_ID_COL));
            new DBAisleMethods(context).deleteAisle(aisleid,true);
        }
        aislescursor.close();

        /**
         * Ready for action the deletion of the Shop (should only ever be 1)
         */
        String dlt_shop_whereclause = DBShopsTableConstants.SHOPS_ID_COL + " = ?";
        String dlt_shop_whereargs[] = { Long.toString(shopid) };
        rv =  db.delete(DBShopsTableConstants.SHOPS_TABLE,
                dlt_shop_whereclause,
                dlt_shop_whereargs);
        if(!intransaction) {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        Log.i(LOGTAG,
                Long.toString(rv) + " Shops Deleted for shopid " + Long.toString(shopid));
        return rv;
    }

    /**************************************************************************
     * shopDeleteImpact
     *
     * @param shopid id of the shop to report on
     * @return String ArrayList of table rows that will be deleted
     */
    ArrayList<String> shopDeletedImpact(long shopid) {
        ArrayList<String> rv = new ArrayList<>();

        String shopfilter = DBShopsTableConstants.SHOPS_ID_COL_FULL +
                " = " +
                Long.toString(shopid) +
                DBConstants.SQLENDSTATEMENT;
        String aislefilter = DBAislesTableConstants.AISLES_SHOPREF_COL_FULL +
                " = " +
                Long.toString(shopid) +
                DBConstants.SQLENDSTATEMENT;
        Cursor shopstodelete = DBCommonMethods.getTableRows(db,
                DBShopsTableConstants.SHOPS_TABLE,
                shopfilter,
                ""
        );                ;
        while (shopstodelete.moveToNext()) {
            Cursor aislestodelete = DBCommonMethods.getTableRows(db,
                    DBAislesTableConstants.AISLES_TABLE,
                    aislefilter,
                    "");
            rv.add("SHOP " +
                    shopstodelete.getString(
                            shopstodelete.getColumnIndex(
                                    DBShopsTableConstants.SHOPS_NAME_COL)) +
                    "-" + shopstodelete.getString(
                    shopstodelete.getColumnIndex(
                            DBShopsTableConstants.SHOPS_CITY_COL)) +
                    "-" + shopstodelete.getString(
                    shopstodelete.getColumnIndex(
                            DBShopsTableConstants.SHOPS_STREET_COL)) +
                    " would be deleted.");
            while (aislestodelete.moveToNext()) {
                long aisleid = aislestodelete.getLong(aislestodelete.getColumnIndex(
                        DBAislesTableConstants.AISLES_ID_COL));

                rv.addAll(new DBAisleMethods(context).aisleDeleteImpact(aisleid));
            }
            aislestodelete.close();
        }
        shopstodelete.close();
        return  rv;
    }
}