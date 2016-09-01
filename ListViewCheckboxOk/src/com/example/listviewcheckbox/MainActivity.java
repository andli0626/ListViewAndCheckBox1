package com.example.listviewcheckbox;

import java.util.ArrayList;
import java.util.List;

import com.example.listviewcheckbox.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button show;
	Button select;
	Button deselect;
	ListView lv;
	Context mContext;
	MyListAdapter adapter;

	List<Integer> selected = new ArrayList<Integer>();
	private List<Item> items;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// mContext = getApplicationContext();
		mContext = MainActivity.this;
		select = (Button) findViewById(R.id.select);
		deselect = (Button) findViewById(R.id.deselect);
		show = (Button) findViewById(R.id.show);
		lv = (ListView) findViewById(R.id.lv);

		items = new ArrayList<Item>();

		for (int i = 0; i < 50; i++) {
			Item item = new Item();
			item.name = "wxz" + i;
			item.address = "ZhengZhou";
			item.checked = false;
			items.add(item);
		}

		adapter = new MyListAdapter(items);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				items.get(position).checked = !items.get(position).checked;
				adapter.notifyDataSetChanged();
				Toast.makeText(mContext,
						"单击:" + items.get(position).name + ",id:" + id,
						Toast.LENGTH_SHORT).show();

			}

		});

		select.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int k = items.size();
				for (int i = 0; i < k; i++) {
					items.get(i).checked = true;
				}
				adapter.notifyDataSetChanged();
			}
		});

		deselect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int k = items.size();
				for (int i = 0; i < k; i++) {
					items.get(i).checked = false;
				}
				adapter.notifyDataSetChanged();
			}
		});

		show.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				selected.clear();
				int k = items.size();

				for (int i = 0; i < k; i++) {
					if (items.get(i).checked) {
						selected.add(i);
					}
				}
				k = selected.size();

				if (k == 0) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							MainActivity.this);
					builder1.setMessage("没有选中任何记录");
					builder1.show();

				} else {
					StringBuilder sb = new StringBuilder();

					for (int i = 0; i < k; i++) {
						int j = selected.get(i);
						sb.append("ID=" + (j) + " Name =" + items.get(j).name
								+ "\n");
					}
					AlertDialog.Builder builder2 = new AlertDialog.Builder(
							MainActivity.this);
					builder2.setMessage(sb.toString());
					builder2.show();
				}
			}
		});
	}

	// 自定义ListView适配器
	class MyListAdapter extends BaseAdapter {

		// private Context context;
		LayoutInflater inflater;
		public List<Item> items;

		public MyListAdapter(List<Item> items) {
			this.items = items;

			inflater = LayoutInflater.from(mContext);

		}

		@Override
		public int getCount() {
			// 返回值控制该Adapter将会显示多少个列表项
			return items == null ? 0 : items.size();
		}

		@Override
		public Object getItem(int position) {
			// 返回值决定第position处的列表项的内容
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			// 返回值决定第position处的列表项的ID
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			Item item = items.get(position);
			if (convertView == null) {
				Log.e("MainActivity", "position1 = " + position);
				convertView = inflater.inflate(R.layout.listitem, null);

				holder = new ViewHolder();
				holder.btnDel = (Button) convertView.findViewById(R.id.btnDel);
				holder.cbCheck = (CheckBox) convertView
						.findViewById(R.id.cbCheck);
				holder.tvName = (TextView) convertView
						.findViewById(R.id.tvName);
				holder.tvAddress = (TextView) convertView
						.findViewById(R.id.tvAddress);
				holder.tvName.setText(item.name);
				holder.tvAddress.setText(item.address);

				convertView.setTag(holder);

			} else {
				Log.e("MainActivity", "position2 = " + position);
				holder = (ViewHolder) convertView.getTag();
				holder.cbCheck.setChecked(item.checked);
				holder.tvName.setText(item.name);
				holder.tvAddress.setText(item.address);

			}
			holder.btnDel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 删除list中的数据
					items.remove(position);
					// 通知列表数据修改
					adapter.notifyDataSetChanged();
				}

			});
			holder.cbCheck.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					items.get(position).checked = cb.isChecked();

				}
			});
			return convertView;
		}

	}

	static class ViewHolder {
		public CheckBox cbCheck;
		public TextView tvName;
		public TextView tvAddress;
		public Button btnDel;

	}

	class Item {
		private String name;
		private String address;
		private Boolean checked;
	}
}