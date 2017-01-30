package mjt.shopwise;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Database Access Object
 */
public class DBDAO {

    /**
     * The Db.
     */
    protected SQLiteDatabase db;
    private DBHelper dbhelper;
    private Context mContext;
    public static final String THISCLASS = DBDAO.class.getSimpleName();
    private static final String LOGTAG = "SW_DBDAO";

    /**
     * DBAO Constructor
     *
     * @param context Context of the invoking method
     */
    public DBDAO(Context context) {
        String msg = "Constructing";
        String methodname = "Construct";
        LogMsg.LogMsg(LogMsg.LOGTYPE_INFORMATIONAL,LOGTAG,msg,THISCLASS,methodname);
        this.mContext = context;
        dbhelper = DBHelper.getHelper(context);
        db = dbhelper.getWritableDatabase();
    }

    /**
     * Gets table row count.
     *
     * @param tablename table to inspect
     * @return number of rows
     */
    public int getTableRowCount(String tablename) {
        String msg = "Invoked";
        String methodname = new Object(){}.getClass().getEnclosingMethod().getName();
        LogMsg.LogMsg(LogMsg.LOGTYPE_INFORMATIONAL,LOGTAG,msg,THISCLASS,methodname);
        String sql = " SELECT * FROM " + tablename + " ;";
        Cursor csr = db.rawQuery(sql,null);
        int rv = csr.getCount();
        csr.close();
        msg = "Returned " + Integer.toString(rv) +
                " rows from Table=" +tablename;
        methodname = new Object(){}.getClass().getEnclosingMethod().getName();
        LogMsg.LogMsg(LogMsg.LOGTYPE_INFORMATIONAL,LOGTAG,msg,THISCLASS,methodname);
        return rv;
    }

    /**
     * getTableRows - generic get rows from a table
     *
     * @param table      Table name
     * @param joinclause Joing clause, if blank skipped
     * @param filter     Filter clause less WHERE, if blank skipped
     * @param order      Order clause less ORDER BY keywords, skipped if blank
     * @return Cursor with extracted rows, if any.
     */
    public Cursor getTableRows(String table, String joinclause, String filter, String order) {
        String msg = "Invoked";
        String methodname = new Object(){}.getClass().getEnclosingMethod().getName();
        LogMsg.LogMsg(LogMsg.LOGTYPE_INFORMATIONAL,LOGTAG,msg,THISCLASS,methodname);
        String sql = " SELECT * FROM " + table;
        if (joinclause.length() > 0 ) {
            sql = sql + joinclause;
        }
        if (filter.length() > 0 ) {
            sql = sql + " WHERE " + filter;
        }
        if (order.length() > 0) {
            sql = sql + " ORDER BY " + order;
        }
        sql = sql + " ;";
        return db.rawQuery(sql,null);
    }


    /**
     * getShopListCount
     *
     * @return number of ShopList entries
     */
    public int getShoplistCount() {
        return getTableRowCount(DBShopListTableConstants.SHOPLIST_TABLE);
    }

    /**
     * getRuleCount
     *
     * @return number of Rules
     */
    public int getRuleCount() {
        return getTableRowCount(DBRulesTableConstants.RULES_TABLE);
    }

    /**
     * getAppvalueCount
     *
     * @return number of Appvalue entries
     */
    public int getAppvalueCount() {
        return getTableRowCount(DBAppvaluesTableConstants.APPVALUES_TABLE);
    }
}
