//package com.example.abba4u_project;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.widget.Toast;
//
//import com.example.abba4u_project.Module.Fragment.CollageFragment;
//import com.xiaopo.flying.sticker.Sticker;
//import com.xiaopo.flying.sticker.StickerIconEvent;
//import com.xiaopo.flying.sticker.StickerView;
//import com.xiaopo.flying.sticker.TextSticker;
//
//import static com.example.abba4u_project.staticData.SelectTextSticker;
//import static com.example.abba4u_project.staticData.selectType;
//
//public class ModifyIconEvent implements StickerIconEvent {
//    @Override
//    public void onActionDown(StickerView stickerView, MotionEvent event) {
//
//    }
//
//    @Override
//    public void onActionMove(StickerView stickerView, MotionEvent event) {
//
//    }
//
//    @Override
//    public void onActionUp(StickerView stickerView, MotionEvent event) {
//        CollageFragment collageFragment = new CollageFragment();
//        Intent intent;
//        if (stickerView.getCurrentSticker() instanceof  TextSticker) {
//            Log.i("텍스트 스티커 진입","ㅇㅇ");
//            intent = new Intent(stickerView.getContext(), ModifyTextStickerActivity.class);
//            intent.putExtra("textValue", collageFragment.getText((TextSticker) stickerView.getCurrentSticker()));
//            collageFragment.startActivityForResult(intent, 630);
//            //630 : 텍스트 이미지 수정 리퀘스트 코드
//        }else if(stickerView.getCurrentSticker() instanceof Sticker){
//        }
//    }
//
////    if (SelectBitmap == null && SelectTextSticker == null) {
////        Toast.makeText(getActivity(), "선택된 스티커가 없습니다.", Toast.LENGTH_SHORT).show();
////    } else {
////        Intent intent;
////        if (selectType.equals("text")) {
////            intent = new Intent(getActivity().getApplicationContext(), ModifyTextStickerActivity.class);
////            intent.putExtra("textValue",SelectTextSticker.getText().toString());
////            startActivityForResult(intent,630);
////            //630 : 텍스트 이미지 수정 리퀘스트 코드
////        } else if (selectType.equals("img")) {
////            intent = new Intent(getActivity().getApplicationContext(), GetImageActivity.class);
////            startActivity(intent);
////        }
////
////    }
//}
