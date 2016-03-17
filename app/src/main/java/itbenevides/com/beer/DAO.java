

package itbenevides.com.beer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DAO extends SQLiteOpenHelper {

    public static interface IDAO {

        public String getTabela();

        public long getId_();

        public void setId_(long id_);

        public ContentValues getContentValues();

    }

    ;

    public static final int VERSAO = 1;
    public static String SISTEMA = "BEERDB";
    private Context context;

    public DAO(Context context) {
        super(context, SISTEMA, null, VERSAO);
        this.context = context;
    }

    private static DAO instance;

    public static synchronized DAO getHelper(Context context) {
        if (instance == null) {
            instance = new DAO(context);
        } else {
            if (!instance.getReadableDatabase().isOpen()) {
                instance.close();
                instance = new DAO(context);

            }

        }


        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqlitedatabase) {

        Beer.onCreate(sqlitedatabase);

    }




    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldVersion, int newVersion) {
        onCreate(sqlitedatabase);

    }

    public void salvar(IDAO idao) {
        try {
             if (idao.getId_() == 0)
            idao.setId_(getWritableDatabase().insert(idao.getTabela(), null, idao.getContentValues()));
              else
                  getWritableDatabase().update(idao.getTabela(), idao.getContentValues(), "id_=?", new String[]{Long.toString(idao.getId_())});
        } catch (Exception e) {

        }
    }




    public void salvar(List<Beer> lista) {
        try {
            if (!lista.isEmpty()) {
                getWritableDatabase().beginTransaction();
                for (IDAO idao : lista) {
                    getWritableDatabase().insert(idao.getTabela(), null, idao.getContentValues());
                }
                getWritableDatabase().setTransactionSuccessful();
                getWritableDatabase().endTransaction();
            }
        }catch (Exception e){

        }

    }















    public void apagar(Context context) {
        context.deleteDatabase(SISTEMA);
    }



}





