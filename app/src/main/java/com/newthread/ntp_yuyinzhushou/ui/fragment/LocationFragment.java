package com.newthread.ntp_yuyinzhushou.ui.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.newthread.ntp_yuyinzhushou.R;
import com.newthread.ntp_yuyinzhushou.adapter.LocationDetailAdapter;
import com.newthread.ntp_yuyinzhushou.ui.adapter.PoiSearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 张浩 on 2016/10/13.
 */

public class LocationFragment extends BaseFragment implements LocationSource, AMap.OnMarkerClickListener
        , BaseFragment.FragmentVoiceResultListener, LocationDetailAdapter.LocationDetailAdapterImpl, PoiSearchAdapter.RvAdapterImpl {

    public static final int SEARCH_NEARBY = 1;
    public static final int SEARCH_DESTINATION = 2;

    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.rv_fragment_location)
    RecyclerView rvFragmentLocation;
    @BindView(R.id.nsv_fragment_location)
    NestedScrollView nsvFragmentLocation;

    private View view;
    private AMap aMap;
    private AMapLocationClientOption locationClientOption;
    private AMapLocation currentLocation;
    private MarkerOptions currentMakerOptions;
    private PoiSearch poiSearch;
    private BottomSheetBehavior behavior;
    private AMapLocationClient locationClient;
    private LocationDetailAdapter locationDetailAdapter;
    private LocationHandler handler;
    private List<PoiItem> poiItemList;
    private List<Marker> poiMarkers;
    private int range;
    private int lastClicked = -1;
    private int nowClicked = -1;

    private int[] redMarkerIcons = {
            R.drawable.ic_poi_marker_red_1,
            R.drawable.ic_poi_marker_red_2,
            R.drawable.ic_poi_marker_red_3,
            R.drawable.ic_poi_marker_red_4,
            R.drawable.ic_poi_marker_red_5,
            R.drawable.ic_poi_marker_red_6,
            R.drawable.ic_poi_marker_red_7,
            R.drawable.ic_poi_marker_red_8,
            R.drawable.ic_poi_marker_red_9,
            R.drawable.ic_poi_marker_red_10
    };

    private int[] blueMarkerIcons = {
            R.drawable.ic_poi_marker_blue_1,
            R.drawable.ic_poi_marker_blue_2,
            R.drawable.ic_poi_marker_blue_3,
            R.drawable.ic_poi_marker_blue_4,
            R.drawable.ic_poi_marker_blue_5,
            R.drawable.ic_poi_marker_blue_6,
            R.drawable.ic_poi_marker_blue_7,
            R.drawable.ic_poi_marker_blue_8,
            R.drawable.ic_poi_marker_blue_9,
            R.drawable.ic_poi_marker_blue_10,
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.locationview, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        List<String> list = bundle.getStringArrayList("ResultList");
        mapView.onCreate(savedInstanceState);
        if (list != null) {
            locationDetailAdapter = new LocationDetailAdapter(this, list);
        }
        handler = new LocationHandler();
        initCommand();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initView() {
        super.initView();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginSpeak();
            }
        });
    }

    @Override
    protected void init() {
        super.init();
        setFragmentVoiceResultListener(this);
    }

    @Override
    public void onResult(List<String> list) {
        setFragmentChange(list);
    }

    private void initCommand() {
        initMap();
        initBottomSheet();
//        getLocation();
    }

    private void initMap() {
        locationClientOption = new AMapLocationClientOption();
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationClientOption.setNeedAddress(true);
        locationClientOption.setOnceLocationLatest(true);
        AMap gotMap = mapView.getMap();
        gotMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(30.52, 114.31),//新的中心点坐标
                10, // 新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        )));
        if (aMap == null) {
            aMap = gotMap;
        }
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.getUiSettings().setCompassEnabled(true);
        aMap.getUiSettings().setZoomControlsEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.setLocationSource(this);
        aMap.setOnMarkerClickListener(this);
    }

    /**
     * 初始化Bottom
     */
    private void initBottomSheet() {
        behavior = BottomSheetBehavior.from(nsvFragmentLocation);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    /**
     * 初始化底部的RecycleView
     *
     * @param poiItemList 点信息列表
     */
    private void initRecycleView(List<PoiItem> poiItemList, boolean isDistanceVisible) {
        PoiSearchAdapter adapter = new PoiSearchAdapter(getContext(), this, poiItemList, isDistanceVisible);
        rvFragmentLocation.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFragmentLocation.setAdapter(adapter);
    }

    /**
     * 获取当前定位
     */
    private void getLocation() {
        locationClient = new AMapLocationClient(getContext());
        locationClient.setLocationOption(locationClientOption);
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                currentLocation = aMapLocation;
                addCurrentLocationMarker();
                changeMapCenter(currentLocation.getLatitude(), currentLocation.getLongitude(), true);
            }
        });
        locationClient.startLocation();
    }

    /**
     * 改变地图中心位置
     *
     * @param latitude     纬度
     * @param longitude    经度
     * @param isChangeZoom 是否改变缩放程度
     */
    private void changeMapCenter(double latitude, double longitude, boolean isChangeZoom) {
        float zoom = aMap.getCameraPosition().zoom;
        CameraUpdate cameraUpdate;
        if (isChangeZoom) {
            cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    new LatLng(latitude, longitude),//新的中心点坐标
                    16, // 新的缩放级别
                    0, //俯仰角0°~45°（垂直与地图时为0）
                    0  ////偏航角 0~360° (正北方为0)
            ));
        } else {
            cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    new LatLng(latitude, longitude),//新的中心点坐标
                    zoom, // 新的缩放级别
                    0, //俯仰角0°~45°（垂直与地图时为0）
                    0  ////偏航角 0~360° (正北方为0)
            ));
        }
        aMap.animateCamera(cameraUpdate);
    }

    /**
     * 添加当前位置的Marker
     */
    private void addCurrentLocationMarker() {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        currentMakerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(latLng)
                .title("当前位置")
                .snippet("DefaultMarker")
                .infoWindowEnable(true);
        Marker currentMarker = aMap.addMarker(currentMakerOptions);
        currentMarker.setClickable(false);
    }

    /**
     * 将搜索得到的点添加到地图上
     *
     * @param poiItemList 点信息列表
     */
    private void addSearchedMarkersToMap(List<PoiItem> poiItemList) {
        poiMarkers = new ArrayList<Marker>();
        aMap.clear();
        addCurrentLocationMarker();
        for (int i = 0; i < poiItemList.size(); i++) {
            PoiItem poiItem = poiItemList.get(i);
            MarkerOptions markerOptions = new MarkerOptions()
                    .title(poiItem.getTitle())
                    .snippet(poiItem.getSnippet())
                    .position(new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude()))
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), redMarkerIcons[i])));
            Marker marker = aMap.addMarker(markerOptions);
            marker.setObject(poiItem);
            poiMarkers.add(marker);
        }
    }

    /**
     * 获取PoiSearch结果
     *
     * @param poiType Poi搜索类型，可为空
     * @param keyWord Poi搜索关键字，可为空
     * @param latLng  Poi搜索中心点，可为空
     */
    private void doPoiSearch(String poiType, String keyWord, LatLng latLng) {
        range = 1000;
        PoiSearch.Query query = new PoiSearch.Query(keyWord, poiType, currentLocation.getCityCode());
        query.setPageSize(10);
        query.setPageNum(1);
        poiSearch = new PoiSearch(getContext(), query);
        if (latLng == null) {
            //进行当前位置的周边搜索
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(currentLocation.getLongitude(),
                    currentLocation.getLatitude()), range));//设置周边搜索的中心点以及半径
        } else {
            //进行指定地点的周边搜索
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latLng.longitude,
                    latLng.latitude), range));//设置周边搜索的中心点以及半径
        }
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int rCode) {
                if (rCode == 1000) {
                    if (poiResult != null && poiResult.getPois() != null) {
                        poiItemList = poiResult.getPois();
                        if (poiResult.getPois().size() <= 5) {
                            //搜索结果太少则扩大搜索范围
                            if (range > 10000) {
                                if (poiResult.getPois().size() != 0) {
                                    addSearchedMarkersToMap(poiItemList);
                                    initRecycleView(poiItemList, true);
                                    changeMapCenter(poiItemList.get(0).getLatLonPoint().getLatitude(), poiItemList.get(0).getLatLonPoint().getLongitude(), true);
                                } else {
                                    Toast.makeText(getContext(), "附近搜索不到您所需的信息！", Toast.LENGTH_SHORT).show();
                                }
                                return;
                            }
                            range += 500;
                            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(currentLocation.getLongitude(),
                                    currentLocation.getLatitude()), range));//设置周边搜索的中心点以及半径
                            poiSearch.searchPOIAsyn();
                            return;
                        } else {
                            addSearchedMarkersToMap(poiItemList);
                            initRecycleView(poiItemList, true);
                            changeMapCenter(poiItemList.get(0).getLatLonPoint().getLatitude(), poiItemList.get(0).getLatLonPoint().getLongitude(), true);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "搜索失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    /**
     * 进行目的地搜索
     *
     * @param keyWord 搜索关键词
     */
    private void doDestinationSearch(String keyWord) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", currentLocation.getCityCode());
        query.setPageSize(8);
        query.setPageNum(1);
        poiSearch = new PoiSearch(getContext(), query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int rCode) {
                if (rCode == 1000) {
                    if (poiResult != null && poiResult.getPois() != null) {
                        poiItemList = poiResult.getPois();
                        addSearchedMarkersToMap(poiItemList);
                        initRecycleView(poiItemList, false);
                        changeMapCenter(poiItemList.get(0).getLatLonPoint().getLatitude(), poiItemList.get(0).getLatLonPoint().getLongitude(), true);
                    } else {
                        Toast.makeText(getContext(), "搜索不到您所需的信息！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "搜索失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    /**
     * 获取标记点对应的序号
     *
     * @param marker 标记点
     * @return 序号
     */
    private int getMarkerIndex(Marker marker) {
        for (int i = 0; i < poiMarkers.size(); i++) {
            if (poiMarkers.get(i).equals(marker)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 向Handler传递消息执行周边搜索
     *
     * @param poiType poi搜索类型
     * @param keyWord 搜索关键词
     */
    private void sendNearbySearchMessage(String poiType, String keyWord) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("poiType", poiType);
        bundle.putString("keyWord", keyWord);
        message.setData(bundle);
        message.what = SEARCH_NEARBY;
        handler.sendMessage(message);
    }

    /**
     * 向Handler传递消息执行目的地搜索
     *
     * @param keyWord 搜索关键词
     */
    private void sendDestinationSearchMessage(String keyWord) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("keyWord", keyWord);
        message.setData(bundle);
        message.what = SEARCH_DESTINATION;
        handler.sendMessage(message);
    }

    /**
     * 执行Marker点击事件
     *
     * @param position 被点击的Marker序号
     */
    private void doMarkerClickedAction(int position) {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        poiMarkers.get(position).showInfoWindow();
        poiMarkers.get(position).setClickable(false);
        changeMapCenter(poiItemList.get(position).getLatLonPoint().getLatitude(), poiItemList.get(position).getLatLonPoint().getLongitude(), false);
        poiMarkers.get(position).setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), blueMarkerIcons[position])));
        PoiSearchAdapter.ViewHolder nowViewHolder = (PoiSearchAdapter.ViewHolder) rvFragmentLocation.findViewHolderForLayoutPosition(position);
        nowViewHolder.title.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    /**
     * 执行另一Marker被点击的前一Marker事件
     *
     * @param position 前一Marker序号
     */
    private void doMarkerUnClickedAction(int position) {
        poiMarkers.get(position).setClickable(true);
        poiMarkers.get(position).setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), redMarkerIcons[position])));
        PoiSearchAdapter.ViewHolder lastViewHolder = (PoiSearchAdapter.ViewHolder) rvFragmentLocation.findViewHolderForLayoutPosition(position);
        lastViewHolder.title.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        changeMapCenter(currentLocation.getLatitude(), currentLocation.getLongitude(), true);
    }

    @Override
    public void deactivate() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (nowClicked == -1) {
            nowClicked = getMarkerIndex(marker);
        } else {
            lastClicked = nowClicked;
            nowClicked = getMarkerIndex(marker);
        }
        doMarkerClickedAction(nowClicked);
        if (lastClicked != -1) {
            doMarkerUnClickedAction(lastClicked);
        }
        return true;
    }

    @Override
    public void nearbyPoiSearch(final String poiType, final String keyWord) {
        locationClient = new AMapLocationClient(getContext());
        locationClient.setLocationOption(locationClientOption);
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                currentLocation = aMapLocation;
                addCurrentLocationMarker();
                sendNearbySearchMessage(poiType, keyWord);
                locationClient.stopLocation();
            }
        });
        locationClient.startLocation();
    }

    @Override
    public void destinationSearch(final String keyWord) {
        locationClient = new AMapLocationClient(getContext());
        locationClient.setLocationOption(locationClientOption);
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                currentLocation = aMapLocation;
                addCurrentLocationMarker();
                sendDestinationSearchMessage(keyWord);
                locationClient.stopLocation();
            }
        });
        locationClient.startLocation();
    }

    @Override
    public void RvWholeItemClicked(View view, final PoiItem poiItem, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if (nowClicked == -1) {
                    nowClicked = position;
                } else {
                    lastClicked = nowClicked;
                    nowClicked = position;
                }
                changeMapCenter(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude(), false);
                doMarkerClickedAction(nowClicked);
                if (lastClicked != -1) {
                    doMarkerUnClickedAction(lastClicked);
                }

            }
        });
    }

    @Override
    public void RvRougeItemClicked(View view,final PoiItem poiItem, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm= getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                Bundle bundle = new Bundle();
                RougeFragment rf=new RougeFragment();
                Double elatitude=poiItem.getLatLonPoint().getLatitude();
                Double elongtitude=poiItem.getLatLonPoint().getLongitude();
                bundle.putDouble("elatitude",elatitude);
                bundle.putDouble("elongtitude",elongtitude);
                rf.setArguments(bundle);
            }
        });
    }

    @Override
    public void RvNaviItemClicked(View view, PoiItem poiItem, int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void RvDetailItemClicked(View view, PoiItem poiItem, int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private class LocationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SEARCH_NEARBY) {
                Bundle bundle = msg.getData();
                String poiType = bundle.getString("poiType");
                String keyWord = bundle.getString("keyWord");
                doPoiSearch(poiType, keyWord, new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
            }
            if (msg.what == SEARCH_DESTINATION) {
                Bundle bundle = msg.getData();
                String keyWord = bundle.getString("keyWord");
                doDestinationSearch(keyWord);
            }
        }
    }
}
