package com.goach.client.ui.city

import android.Manifest
import android.view.View
import com.base.lib.fragment.BaseFragment
import com.data.lib.dto.CityResponse
import com.data.lib.dto.GsonConverter
import com.goach.client.R
import com.google.gson.reflect.TypeToken
import com.tbruyelle.rxpermissions2.RxPermissions
import com.utils.lib.AssetsUtils
import kotlinx.android.synthetic.main.fragment_city.*
import org.jetbrains.anko.onClick

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 *Des:
 */
class CityFragment : BaseFragment() {
    private lateinit var rxPermissions: RxPermissions
    private val mGson by lazy { GsonConverter.mGsonBuilder.create() }
    override fun layoutResID() = R.layout.fragment_city
    override fun initView(view: View) {
        super.initView(view)
        rxPermissions = RxPermissions(activity)
    }
    override fun initListener() {
        super.initListener()
        btnJsonToDB.onClick {
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe({
                        t: Boolean ->
                        if(t){
                            val cityJson = AssetsUtils.getStringForAssets(context,"city.txt")
                            val mCitys = mGson.fromJson<List<CityResponse>>(cityJson,object: TypeToken<List<CityResponse>>(){}.type)
                            val stringBuilder = StringBuilder()
                            //省
                            mCitys.forEachIndexed { index, cityResponse ->
                                stringBuilder.append("$index,${cityResponse.areaId},0,${cityResponse.areaName}\n")
                            }
                            var cityCount = mCitys.size
                            //市
                            mCitys.forEach {
                                val parentId = it.areaId
                                it.cities.forEachIndexed { index, city ->
                                    stringBuilder.append("${cityCount+index},${city.areaId},$parentId,${city.areaName}\n")
                                }
                                cityCount += it.cities.size
                            }
                            //区,县
                            mCitys.forEach {
                                it.cities.forEach {
                                    val parentId = it.areaId
                                    it.counties.forEachIndexed { index, city ->
                                        stringBuilder.append("${cityCount+index},${city.areaId},$parentId,${city.areaName}\n")
                                    }
                                    cityCount += it.counties.size
                                }
                            }
                            AssetsUtils.stringToFile(context,"City.txt",stringBuilder.toString())
                        }
                    })
        }
    }
}
