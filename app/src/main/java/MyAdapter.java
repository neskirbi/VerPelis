
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.verpelis.R;


class Peliculas{
    private String id;
    private String foto;
    private String nombre;

    public Peliculas(String id,String nombre,String foto) {
        this.id=id;
        this.nombre=nombre;
        this.foto=foto;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }


}

class MyAdapter extends ArrayAdapter<Peliculas> {


    public MyAdapter(Context context, int resource) {
        super(context, resource);
    }
}




/*


public class Hijos {
        private String id;
        private String foto;
        private String nombre;

        public Hijos(String id,String nombre,String foto) {
            this.id=id;
            this.nombre=nombre;
            this.foto=foto;
        }

        public String getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getFoto() {
            return foto;
        }


    }


    class AdaptadorPersonas extends ArrayAdapter<Hijos> {

        AppCompatActivity appCompatActivity;

        AdaptadorPersonas(AppCompatActivity context) {
            super(context, R.layout.hijos, hijos);
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.hijos, null);

            TextView textView1 = item.findViewById(R.id.nombre_hijo);
            textView1.setText(hijos.get(position).getNombre());


            ImageView imageView1 = item.findViewById(R.id.foto_hijo);
            imageView1.setId(position);
            imageView1.setImageBitmap( funciones.decodeBase64(hijos.get(position).getFoto()));

            return(item);
        }
    }
*/
