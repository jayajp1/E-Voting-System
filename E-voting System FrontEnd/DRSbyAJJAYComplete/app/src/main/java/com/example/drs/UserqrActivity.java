package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class UserqrActivity extends Activity {

    EditText editText;
    ImageView imageView;
    Bitmap bitmap;
    int Adhar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userqr);
        imageView = findViewById(R.id.userqriv);
        GlobalUser globalUser=(GlobalUser) getApplicationContext();
        Adhar=globalUser.getAdharcard();
    }

    public void QRCodeButton(View view){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            String TExt=""+Adhar;
            BitMatrix bitMatrix = qrCodeWriter.encode(TExt, BarcodeFormat.QR_CODE, 200, 200);
            bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
            for (int x = 0; x<200; x++){
                for (int y=0; y<200; y++){
                    bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                }
            }
            imageView.setImageBitmap(bitmap);
            doPhotoPrint();
            Toast.makeText(getApplicationContext(),"QR code generated succesfully", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("Your QRcode", bitmap);
    }
}
