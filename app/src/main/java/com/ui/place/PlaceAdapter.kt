package com.ui.place

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.logic.model.Place
import com.sunnyweather.android.R
import com.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*


class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>)
    : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }
    //定义内部类ViewHolder继承自RecyclerView.ViewHolder，然后通过findViewById获取RecyclerView子项的最外层布局


    //onCreateViewHolder()方法用于创建ViewHolder实例，并把加载出来的布局传入构造函数当中，最后将ViewHolder实例返回
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            //Log.d("tag","跳转")
            val position = holder.bindingAdapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if (activity is WeatherActivity){
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            }else{
                val intent = Intent(parent.context,WeatherActivity::class.java).apply {
                    putExtra("location_lng",place.location.lng)
                    putExtra("location_lat",place.location.lat)
                    putExtra("place_name",place.name)
                }
                fragment.startActivity(intent)
                activity?.finish()
            }
            fragment.viewModel.savePlace(place) //储存城市数据

        }
        return holder
    }

    //onBindViewHolder()方法用于对RecyclerView子项数据进行赋值
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    //返回数据源长度
    override fun getItemCount() = placeList.size

}
