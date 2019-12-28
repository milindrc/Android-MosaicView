package com.app.trymosaic.customView1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.app.trymosaic.R;
import com.app.trymosaic.customView1.Draw;
import com.app.trymosaic.databinding.ActivityMainBinding;
import com.matrixdev.mosaic.ItemChooseInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    private Draw draw;
    private float mx, my;
    private float curX, curY;
    private ArrayList<DataModel> models;
    private Runnable onImageLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);



        initUI();
    }

    private void initUI() {
        models = new ArrayList<DataModel>(){{
            add(new DataModel("P&G","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2229/companyLogo.jpg"));
            add(new DataModel("Hindustan Unilever","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc222a/companyLogo.jpg"));
            add(new DataModel("ITC","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc222c/companyLogo.jpg"));
            add(new DataModel("GlaxoSmithKline","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc222d/companyLogo.gif"));
            add(new DataModel("Cadbury","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc222e/companyLogo.jpg"));
            add(new DataModel("Britannia","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc222f/companyLogo.jpg"));
            add(new DataModel("Reckitt Benckiser","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2230/companyLogo.jpg"));
            add(new DataModel("Nestle","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2233/companyLogo.jpg"));
            add(new DataModel("Godrej","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2234/companyLogo.jpg"));
            add(new DataModel("Pepsico","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc223a/companyLogo.jpg"));
            add(new DataModel("Marico","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2241/companyLogo.png"));
            add(new DataModel("Parle","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2243/companyLogo.png"));
            add(new DataModel("Patanjali","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2245/companyLogo.jpg"));
            add(new DataModel("Coca-Cola","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc224f/companyLogo.jpg"));
            add(new DataModel("Dabur","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2250/companyLogo.jpg"));
            add(new DataModel("Johnson & Johnson","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2260/companyLogo.jpg"));
            add(new DataModel("MDH","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2262/companyLogo.jpg"));
            add(new DataModel("Emami","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5745437f4a664b3936bc2265/companyLogo.jpg"));
            add(new DataModel("Abbott Laboratories","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/59db183747941ac511f31afc/companyLogo.jpg"));
            add(new DataModel("PERFETTI VAN MELLE INDIA PVT. LTD","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5a1f9f10606cff7d8c6cef8c/companyLogo.jpg"));
            add(new DataModel("99 Algorithms Pvt Ltd","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5a829be9aa88377092a82ecc/companyLogo.jpg"));
            add(new DataModel("Kapiva Ayurveda","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5b2b4a83200f250bdf26903f/companyLogo.jpg"));
            add(new DataModel("Future Consumer Ltd","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5b7eab38d4e41b23420354bb/companyLogo.jpg"));
            add(new DataModel("HANDS ON TRADES PRIVATE LIMTED","https://s3-ap-southeast-1.amazonaws.com/maxwholesale/company_brands/5bbca40a5a3f1b6954b89096/companyLogo.jpg"));
        }};

        onImageLoad = ()->{
            binding.mosaic.prepare(models, model -> {
                Toast.makeText(MainActivity.this, model.getName(), Toast.LENGTH_SHORT).show();
            });

        };

        for(DataModel model : models){
              model.load(this,onImageLoad);
        }


    }

}
