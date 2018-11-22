package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.MainActivity;
import zhaoyufeng.bwie.com.shopapp.activity.SetAddressActivity;
import zhaoyufeng.bwie.com.shopapp.activity.SetNameAcitivity;
import zhaoyufeng.bwie.com.shopapp.activity.ShowActivity;
import zhaoyufeng.bwie.com.shopapp.model.BeanAddress;
import zhaoyufeng.bwie.com.shopapp.model.BeanLogin;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;

import static android.app.Activity.RESULT_OK;

public class ShowActivityPresenter extends AppDelage {

    private SimpleDraweeView img;
    private TextView name,address,mAge;
    private SharedPreferences sp;
    private String token;
    private String uid;
    private static String path = "/sdcard/myHead/";// sd路径
    private Bitmap head;

    @Override
    public int getLayoutId() {
        return R.layout.activity_show;
    }

    @Override
    public void resume() {
        shared();
    }

    @Override
    public void restart() {

    }


    private Context context;
    @Override
    public void getContext(Context context) {
        this.context=context;
    }

    @Override
    public void initData() {
        super.initData();
        init();

    }

    private void init() {
        name = get(R.id.name_show);
        img = get(R.id.img_show);
        address = get(R.id.address_show);
        mAge = get(R.id.age_show);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name();
            }
        },R.id.layout_nickname);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img();
            }
        },R.id.layout_img);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                age();
            }
        },R.id.layout_age);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address();
            }
        },R.id.layout_address);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quit();
            }
        },R.id.quit_show);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ShowActivity)context).finish();
            }
        },R.id.img_mine);
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        uid = sp.getString("uid","");
        token = sp.getString("token", "");
        shared();

    }

    private void quit() {
        sp.edit().clear().commit();
        sp.edit().putBoolean("islogin",false).putBoolean("isfrist",true).commit();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("shopById","3");
        context.startActivity(intent);
    }

    private void shared() {
        Map<String,String> map = new HashMap<>();
        map.put("uid",uid);
        map.put("token",token);
        new HttpUtil().get("/user/getUserInfo",map).result(new HttpUtil.HttpRxListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                String code = beanLogin.getCode();
                if("0".equals(code)){
                    String nickname = (String) beanLogin.getData().getNickname();
                    String icon = (String) beanLogin.getData().getIcon();
                    String age = (String) beanLogin.getData().getAge();
                    name.setText(nickname);
                    if(!TextUtils.isEmpty(icon)){
                        img.setImageURI(icon);
                        sp.edit().putString("icon",icon);
                    }else{
                        img.setImageResource(R.mipmap.logo);

                    }
                    mAge.setText(age);
                }
            }

            @Override
            public void fail(String error) {

            }
        });
        new HttpUtil().get("/user/getDefaultAddr",map).result(new HttpUtil.HttpRxListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                BeanAddress beanAddress = gson.fromJson(data, BeanAddress.class);
                String code = beanAddress.getCode();
                if("0".equals(code)){
                    String addr = beanAddress.getData().getAddr();
                    address.setText(addr);
                }
            }

            @Override
            public void fail(String error) {

            }
        });

    }



    private void age() {
    }

    private void address() {
        Intent intent = new Intent(context, SetAddressActivity.class);
        context.startActivity(intent);
        ((ShowActivity)context).finish();
    }
    private void name() {
        Intent intent = new Intent(context, SetNameAcitivity.class);
        context.startActivity(intent);
        ((ShowActivity)context).finish();
    }
    private void img() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(context, R.layout.popopwindow, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.picture_pop);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.photo_pop);
        TextView tv_select_cancel = (TextView) view.findViewById(R.id.cancel_pop);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                ((ShowActivity)context).startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "head.png")));
                ((ShowActivity)context).startActivityForResult(intent2, 2);//采用ForResult打开
                dialog.dismiss();
            }
        });
        tv_select_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        dialog.setView(view);
        dialog.show();



    }







    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.png");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras == null) {
                        return;
                    }
                    head = extras.getParcelable("data");
                    if (head != null) {

                        String fileName = path + "/head.png";//图片名字
                        setPicToView(head);//保存在SD卡中
                        File file1 = new File(fileName);
                        uploadPic(file1);
                    }
                }
                break;
            default:
                break;

        }
    }

    private void uploadPic(File file1) {
        RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"),file1);
        MultipartBody.Part part=MultipartBody.Part.createFormData("file","head.png",file);
        Map<String,String> map = new HashMap<>();
        map.put("uid",uid);
        new HttpUtil().part("/file/upload",map,part).result(new HttpUtil.HttpRxListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                String code = beanLogin.getCode();
                if("0".equals(code)){
                    Toast.makeText(context, "头像上传成功", Toast.LENGTH_SHORT).show();
                    shared();
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        ((ShowActivity)context).startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.png";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
