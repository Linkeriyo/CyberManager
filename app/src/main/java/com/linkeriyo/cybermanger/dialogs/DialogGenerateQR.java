package com.linkeriyo.cybermanger.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.databinding.DialogCheckQrBinding;
import com.linkeriyo.cybermanger.databinding.DialogGenerateQrBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.requests.BusinessRequests;
import com.linkeriyo.cybermanger.requests.UserRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class DialogGenerateQR extends Dialog {

    DialogGenerateQrBinding binding;
    CyberCafe selectedCafe;

    public DialogGenerateQR(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DialogGenerateQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btClose.setOnClickListener(v -> {
            System.out.println("dialog canceled");
            dismiss();
        });
    }

    public void show(CyberCafe cafe) {
        show();
        selectedCafe = cafe;
        Glide.with(getContext())
                .load(encodeAsBitmap(cafe.getBusinessId()))
                .centerCrop()
                .into(binding.elementGenerateQr.civImage);
        binding.elementGenerateQr.tvCode.setText(cafe.getBusinessId());
        binding.elementGenerateQr.tvAdvice.setText(getContext().getString(R.string.scan_to_add, cafe.getName()));
    }

    private static Bitmap encodeAsBitmap(String str) {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 150, 150, null);
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, 150, 0, 0, w, h);
            return bitmap;
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
