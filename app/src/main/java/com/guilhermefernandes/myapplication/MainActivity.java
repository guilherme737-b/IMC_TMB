package com.guilhermefernandes.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private View btn_Imc;
    private RecyclerView rvMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // btn_Imc = findViewById(R.id.btn_imc);
        rvMain = findViewById(R.id.main_rv);

        List<MainItem> mainItems = new ArrayList<>();
        mainItems.add(new MainItem(1,R.drawable.ic_baseline_wb_sunny_24, R.string.label_imc, Color.GREEN));
        mainItems.add(new MainItem(2,R.drawable.ic_baseline_visibility_24, R.string.label_tmb, Color.YELLOW));


        rvMain.setLayoutManager(new GridLayoutManager(this, 2));
        MainAdapter adapter = new MainAdapter(mainItems);
        adapter.setListener(id -> {
            switch (id){
                case 1:
                    startActivity(new Intent(MainActivity.this, ImcActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, TmbActivity.class));
                    break;
            }
        });
        rvMain.setAdapter(adapter);



    }



    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

        private List<MainItem> mainItems;
        private OnItemClickListener listener;


        public MainAdapter(List<MainItem> mainItems){
            this.mainItems = mainItems;
        }

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        // ele espera como um tipo de retorno uma celula expecifica, neste caso a
        // celula que definimos foi MainViewHolder
        // logo em seguinda precisamos extanciar o layout que criamos (ou que vamos usar)
        // vamos usar Inflater para isso

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainViewHolder(getLayoutInflater().inflate(R.layout.main_item, parent, false));
        }

        // chama o metodo na rolagem, ou seja, criamos o texto de texte, e o bind para usar a posição
        // ou seja toda vez que eu dou uma rolagem ele chama o metodo onBindViewHolder
        // e dentro de onBindViewHolder tem a celula corrente e a posição da rolagem
        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
           MainItem mainItemCurrent = mainItems.get(position);
            holder.bind(mainItemCurrent);

        }

        //abaixo é a quantidade de videos ( seguindo o youtube) capais de ter no Layout
        @Override
        public int getItemCount() {
            return mainItems.size();
        }
        // Entenda como sendo a View da Celula que está dentro do RecyclerView, lembrar
        // do youtube, todos os videos são Holder que estão dentro da recyclerview e quem
        // faz a comunicação entre o Holder e recyclerview é o Adapter
        // lembrando que tem que implementar os modetodos contrutores padroes


        private class MainViewHolder extends RecyclerView.ViewHolder{

            public MainViewHolder(@NonNull View itemView) {
                super(itemView);
            }


            public void bind(MainItem item){
                TextView txtName = itemView.findViewById(R.id.item_txt_name);
                ImageView imgIcon = itemView.findViewById(R.id.item_img_icon);
                LinearLayout btbImc = (LinearLayout) itemView.findViewById(R.id.btn_imc);

                // btn_Imc.setOnClickListener(view -> {
                //   Intent intent = new Intent(MainActivity.this, ImcActivity.class);
                //   startActivity(intent);
                // });

                btbImc.setOnClickListener(view -> {
                    listener.onClick(item.getId());
                });



                txtName.setText(item.getTextStringId());
                imgIcon.setImageResource(item.getDrawableID());
                btbImc.setBackgroundColor(item.getColor());
            }

        }




    }




}
