package com.linkeriyo.cybermanger.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.linkeriyo.cybermanger.databinding.ActivityScanQrBinding;
import com.linkeriyo.cybermanger.dialogs.QRScannedDialog;
import com.linkeriyo.cybermanger.utilities.Tags;

import java.io.IOException;

public class ScanQRActivity extends Activity {

    private static final String TAG = "ScanQRActivity";
    private ActivityScanQrBinding binding;

    private CameraSource cameraSource;
    private String qrValue;
    private boolean check = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initializeQRReader();
    }

    public void initLayout() {
        binding = ActivityScanQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btClose.setOnClickListener(v -> finish());
    }

    public void initializeQRReader() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(ScanQRActivity.this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                if (check) {
                    final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                    try {
                        qrValue = qrCodes.valueAt(0).rawValue;
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }

                    if (qrValue != null) {
                        check = false;
                        System.out.println("Leido");
                        checkQr();
                    }
                }
            }
        });

        cameraSource = new CameraSource
                .Builder(ScanQRActivity.this, barcodeDetector)
                .setRequestedPreviewSize(height, width)
                .setAutoFocusEnabled(true)
                .build();

        binding.svCamera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (ContextCompat.checkSelfPermission(ScanQRActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, Tags.RQ_CAM_PERMISSION);
                } else {
                    try {
                        cameraSource.start(binding.svCamera.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }

    private void checkQr() {
        System.out.println("Value: " + qrValue);
        runOnUiThread(() -> {
            QRScannedDialog dialog = new QRScannedDialog(this, qrValue);
            dialog.show();
        });
    }

    public void checkAgain() {
        qrValue = null;
        check = true;
    }
}
