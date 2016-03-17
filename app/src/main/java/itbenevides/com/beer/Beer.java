package itbenevides.com.beer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onnet on 27/10/2015.
 */
public class Beer implements DAO.IDAO{
    public long _id;
    public long product_id;
    public String name;
    public String size;
    public String price;
    public long beer_id;
    public String image_url;
    public String category;
    public String abv;
    public String type;
    public String brewer;
    public String country;
    public String on_sale;




    @Override
    public ContentValues getContentValues() {

        ContentValues contentvalues = new ContentValues();


        contentvalues.put("product_id", product_id);
        contentvalues.put("name", name);
        contentvalues.put("size", size);
        contentvalues.put("price", price);
        contentvalues.put("beer_id", beer_id);
        contentvalues.put("image_url", image_url);
        contentvalues.put("category", category);
        contentvalues.put("abv", abv);
        contentvalues.put("type", type);
        contentvalues.put("brewer", brewer);
        contentvalues.put("country", country);
        contentvalues.put("on_sale", on_sale);




        return contentvalues;
    }


    public static void onCreate(SQLiteDatabase sqlitedatabase) {
        sqlitedatabase.execSQL("drop table if exists beer");
        String sql = "CREATE TABLE beer (_id integer primary key, " +
                "product_id integer not null , " +
                "name text null, " +
                "size text null, " +
                "price text null, " +
                "beer_id integer null," +
                "image_url text null," +
                "category text null," +
                "abv text null," +
                "type text null," +
                "brewer text null," +
                "country text null," +
                "on_sale text null," +
                " unique (product_id, name)) ";


        sqlitedatabase.execSQL(sql);
    }

    public static List<Beer> consultar(DAO dao) {
        List<Beer> list = null;
        Cursor cursor = null;

        try {
            String sql = "select DISTINCT product_id as x,  * from beer ";
            cursor = dao.getWritableDatabase().rawQuery(sql, null);

            list = new ArrayList<Beer>();
            while (cursor.moveToNext()) {
                list.add(carregar(cursor));
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }


    private static Beer carregar(Cursor cursor) {
        Beer beer = new Beer();
        beer.setId_(cursor.getLong(cursor.getColumnIndex("product_id")));
        beer.setProduct_id(cursor.getLong(cursor.getColumnIndex("product_id")));
        beer.setName(cursor.getString(cursor.getColumnIndex("name")));
        beer.setSize(cursor.getString(cursor.getColumnIndex("size")));
        beer.setPrice(cursor.getString(cursor.getColumnIndex("price")));
        beer.setBeer_id(cursor.getLong(cursor.getColumnIndex("beer_id")));
        beer.setImage_url(cursor.getString(cursor.getColumnIndex("image_url")));
        beer.setCategory(cursor.getString(cursor.getColumnIndex("category")));
        beer.setAbv(cursor.getString(cursor.getColumnIndex("abv")));
        beer.setType(cursor.getString(cursor.getColumnIndex("type")));
        beer.setBrewer(cursor.getString(cursor.getColumnIndex("brewer")));
        beer.setCountry(cursor.getString(cursor.getColumnIndex("country")));
        beer.setOn_sale(cursor.getString(cursor.getColumnIndex("on_sale")));

        return beer;
    }



    @Override
    public  String getTabela() {
        return "beer";
    }

    @Override
    public long getId_() {
        return _id;
    }

    @Override
    public void setId_(long id_) {
        this._id =id_;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getBeer_id() {
        return beer_id;
    }

    public void setBeer_id(long beer_id) {
        this.beer_id = beer_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrewer() {
        return brewer;
    }

    public void setBrewer(String brewer) {
        this.brewer = brewer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(String on_sale) {
        this.on_sale = on_sale;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
