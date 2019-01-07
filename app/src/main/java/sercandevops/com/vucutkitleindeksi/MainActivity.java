package sercandevops.com.vucutkitleindeksi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity{

    private EditText editText;
    private TextView boy_tv,durum_tv,ideal_tv,kilo_tv;
    private SeekBar seekBar;
    private RadioGroup radioGroup;

    private boolean erkekmi = true;
    private double boy = 0.0;
    private int kilo = 50;
    private RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(checkedId == R.id.radBay){
                erkekmi = true;
            }else if(checkedId == R.id.radBayan){
                erkekmi = false;
            }
            guncelle();
        }
    };

    private SeekBar.OnSeekBarChangeListener seekbarIsleyicisi = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            kilo =30 + progress;
            guncelle();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private TextWatcher EditextOlayIsleyici = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                boy = Double.parseDouble(s.toString())/100.0;

            }catch (NumberFormatException e){
                boy = 0.0;
            }

            guncelle();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.edittext);
        boy_tv = findViewById(R.id.tvboy);
        durum_tv = findViewById(R.id.durum_tv);
        ideal_tv = findViewById(R.id.idealkilo_tv);
        kilo_tv = findViewById(R.id.kilo_tv);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        seekBar = (SeekBar)findViewById(R.id.seekbar);

        editText.addTextChangedListener(EditextOlayIsleyici);
        radioGroup.setOnCheckedChangeListener(radioListener);
        seekBar.setOnSeekBarChangeListener(seekbarIsleyicisi);

        MobileAds.initialize(this, "ca-app-pub-9847952719857217/1636745064");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

        guncelle();

    }

    public void guncelle(){
        kilo_tv.setText(String.valueOf(kilo));
        boy_tv.setText(String.valueOf(boy));

            int ideal_kiloBay = (int)(50+2.3*(boy*100*0.4-60));
            int ideal_kiloBayan = (int)(45.5+2.3*(boy*100*0.4-60));
            double vki = kilo/(boy*boy);
        if(erkekmi){
            ideal_tv.setText(String.valueOf(ideal_kiloBay));

            if(vki <= 20.7){
                durum_tv.setBackgroundResource(R.color.zayif);
                durum_tv.setText(R.string.zayif);
            }else if(20.7<vki && vki <= 26.4){
                durum_tv.setText(R.string.ideal);
                durum_tv.setBackgroundResource(R.color.durum_ideal);
            }else if(26.4<vki && vki <= 27.8){
                durum_tv.setText(R.string.normalden_fazla);
                durum_tv.setBackgroundResource(R.color.durum_idealden_fazla);
            }else if(27.8<vki && vki <= 31.1){
                durum_tv.setText(R.string.fazla_kilolu);
                durum_tv.setBackgroundResource(R.color.durum_fazla_kilolu);
            }else if(31.1<vki && vki <= 34.9){
                durum_tv.setText(R.string.obez);
                durum_tv.setBackgroundResource(R.color.durum_obez);
            }else{
                durum_tv.setText(R.string.doktora);
                durum_tv.setBackgroundResource(R.color.durum_doktora);
            }

        }else{
            ideal_tv.setText(String.valueOf(ideal_kiloBayan));
        }
    }
}
