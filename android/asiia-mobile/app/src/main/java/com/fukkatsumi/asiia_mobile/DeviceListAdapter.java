package com.fukkatsumi.asiia_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fukkatsumi.asiia_mobile.entity.Device;

import java.util.ArrayList;

public class DeviceListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Device> objects;

    DeviceListAdapter(Context context, ArrayList<Device> devices) {
        ctx = context;
        objects = devices;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Device d = getDevice(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.tvDescr)).setText(d.getSn());
        ((TextView) view.findViewById(R.id.tvPrice)).setText(d.getType());

        RadioButton cbBuy = (RadioButton) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        if(d.getStatus().toLowerCase().equals("connected")) {
            cbBuy.setChecked(true);
        } else {
            cbBuy.setChecked(false);
        }
        return view;
    }

    // товар по позиции
    Device getDevice(int position) {
        return ((Device) getItem(position));
    }

    // содержимое корзины
    Device getSelectedDevice() {
        return null;
    }
}
